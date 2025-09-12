package it.vrad.motivational.telegram.bot.infrastructure.job.producer;

import it.vrad.motivational.telegram.bot.config.properties.PhraseProperties;
import it.vrad.motivational.telegram.bot.core.service.phrase.history.PhraseSentHistoryService;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseService;
import it.vrad.motivational.telegram.bot.core.service.phrase.user.UserPhraseService;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;
import it.vrad.motivational.telegram.bot.integration.telegram.api.TelegramIntegrationApi;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.PhraseDto;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserDao;
import it.vrad.motivational.telegram.bot.integration.telegram.util.MessageUtility;
import it.vrad.motivational.telegram.bot.core.util.UserDtoUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Service responsible for producing phrase related jobs.
 * <p>
 * Handles the logic for sending random phrases to all valid users
 */
@Slf4j
@Service
public class PhraseJobProducer {
    private final UserDao userDao;
    private final UserPhraseService userPhraseService;
    private final PhraseService phraseService;
    private final TelegramIntegrationApi telegramIntegrationApi;
    private final PhraseSentHistoryService phraseSentHistoryService;

    private final int maxRetryDuration;
    private final int retryInterval;

    public PhraseJobProducer(UserDao userDao,
                             UserPhraseService userPhraseService,
                             PhraseService phraseService,
                             TelegramIntegrationApi telegramIntegrationApi,
                             PhraseSentHistoryService phraseSentHistoryService,
                             PhraseProperties phraseProperties) {

        this.userDao = userDao;
        this.userPhraseService = userPhraseService;
        this.phraseService = phraseService;
        this.telegramIntegrationApi = telegramIntegrationApi;
        this.phraseSentHistoryService = phraseSentHistoryService;

        this.maxRetryDuration = phraseProperties.getSchedulerMaxRetryDuration();
        this.retryInterval = phraseProperties.getSchedulerRetryInterval();
    }


    /**
     * Creates a notification job as a {@link Runnable} that processes all users.
     *
     * @return a Runnable for the notification job
     */
    public Runnable createNotificationJob() {
        return this::processAllUsers;
    }

    /**
     * Processes all valid users by sending them a random phrase.
     * Catches and logs any exceptions per user.
     */
    private void processAllUsers() {
        log.info("Start processing all valid users to send random phrase via notification job");

        List<UserDto> userDtos = userDao.findAllValidUsers();
        if(CollectionUtils.isEmpty(userDtos)){
            log.warn("No users found");
        }
        Integer retryOffset = 0;

        for (UserDto user : userDtos) {
            try {
                processSingleUser(user, retryOffset);
            } catch (Exception ex) {
                log.error("An error occurred during phraseScheduler for user: {}", user, ex);
            }
        }
    }

    /**
     * Processes a single user by sending a random phrase and updating read status.
     *
     * @param user        the user to process
     * @param retryOffset the current retry offset if an {@link IntegrationApiException} occurs
     */
    private void processSingleUser(UserDto user, Integer retryOffset) {
        Long userId = user.getId();
        Optional<PhraseDto> phraseOpt = phraseService.getRandomPhrase(userId);

        if (phraseOpt.isEmpty()) {
            log.warn("No phrases available for user: {}", user);
            return;
        }

        PhraseDto phrase = phraseOpt.get();

        sendPhraseToUser(user, phrase, retryOffset);
        userPhraseService.updateUserPhraseReadStatus(userId, phrase.getId());
    }

    /**
     * Sends a phrase to a user via Telegram and saves the history.
     * Retries sending if an IntegrationApiException occurs, up to the configured max retry duration.
     *
     * @param userDto     the user to send the phrase to
     * @param phraseDto   the phrase to send
     * @param retryOffset the current retry offset if an {@link IntegrationApiException} occurs
     */
    private void sendPhraseToUser(UserDto userDto, PhraseDto phraseDto, Integer retryOffset) {
        try {
            telegramIntegrationApi.sendSimpleMessage(UserDtoUtility.getChatId(userDto), MessageUtility.formatPhrase(phraseDto));
            phraseSentHistoryService.save(phraseDto, userDto);
        } catch (IntegrationApiException ex) {
            log.error("Failed to send phrase id '{}' to user id '{}'. Retrying (offset: {}): {}",
                    phraseDto.getId(), userDto.getId(), retryOffset, ex, ex);

            if (retryOffset < maxRetryDuration) {
                retryOffset += retryInterval;
                sleep(retryOffset);
            } else {
                throw ex;
            }
        }
    }

    /**
     * Sleeps for the specified retry offset in seconds.
     *
     * @param retryOffset the number of seconds to sleep
     */
    private static void sleep(Integer retryOffset) {
        try {
            Thread.sleep(Duration.ofSeconds(retryOffset));
        } catch (InterruptedException interruptedException) {
            log.error("Error occurred during thread sleeping", interruptedException);
        }
    }


}
