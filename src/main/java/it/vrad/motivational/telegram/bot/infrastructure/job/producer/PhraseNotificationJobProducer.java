package it.vrad.motivational.telegram.bot.infrastructure.job.producer;

import it.vrad.motivational.telegram.bot.config.properties.PhraseProperties;
import it.vrad.motivational.telegram.bot.core.exception.NoSuchPhraseException;
import it.vrad.motivational.telegram.bot.core.model.dto.persistence.UserDto;
import it.vrad.motivational.telegram.bot.core.service.phrase.PhraseDeliveryManager;
import it.vrad.motivational.telegram.bot.infrastructure.persistence.dao.UserDao;
import it.vrad.motivational.telegram.bot.shared.backoff.BackoffStrategy;
import it.vrad.motivational.telegram.bot.shared.backoff.impl.IncrementalBackoff;
import it.vrad.motivational.telegram.bot.integration.telegram.exception.IntegrationApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsible for producing and managing phrase notification jobs.
 * <p>
 * Handles the logic for sending random phrases to all valid users, including retry logic and delay handling.
 */
@Slf4j
@Service
public class PhraseNotificationJobProducer {
    // === Log Messages ===
    private static final String LOG_MAX_DELAY_REACHED =
            "Maximum delay of {} seconds reached after repeated integration errors. Stopping notification job.";
    private static final String LOG_FAILED_SEND_AND_WAIT =
            "Failed to send random phrase to user id '{}'. Wait before next user attempt.";

    // === Dependencies ===
    private final UserDao userDao;
    private final PhraseDeliveryManager phraseDeliveryManager;

    // === Configuration ===
    private final int maxDelay;
    private final int delayIncrement;


    public PhraseNotificationJobProducer(
            UserDao userDao,
            PhraseDeliveryManager phraseDeliveryManager,
            PhraseProperties phraseProperties
    ) {
        this.userDao = userDao;
        this.phraseDeliveryManager = phraseDeliveryManager;

        this.maxDelay = phraseProperties.getSchedulerMaxDelay();
        this.delayIncrement = phraseProperties.getSchedulerDelayIncrement();
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
     * If an IntegrationApiException occurs for a user, a delay is applied before attempting the next user.
     * The delay increases with each failure, up to a maximum, and is reset after a successful send.
     * Catches and logs any exceptions per user.
     */
    private void processAllUsers() {
        log.info("Start processing all valid users to send random phrase via notification job");

        List<UserDto> userDtos = userDao.findAllValidUsers();
        if (CollectionUtils.isEmpty(userDtos)) {
            log.warn("No users found");
            return;
        }

        BackoffStrategy nextUserDelay = new IncrementalBackoff(0, delayIncrement, maxDelay);
        userDtos.forEach(user -> processSingleUser(user, nextUserDelay));
    }

    /**
     * Processes a single user by attempting to deliver a random phrase. Handles and logs exceptions per user.
     * <p>
     * If a {@link NoSuchPhraseException} occurs, logs a warning and continues. If an {@link IntegrationApiException}
     * occurs, logs an error and throws the exception to stop further processing. Any other exceptions are logged as errors.
     *
     * @param userDto       the user to send the phrase to
     * @param nextUserDelay the delay handler to apply before the next user if an integration error occurs
     */
    private void processSingleUser(UserDto userDto, BackoffStrategy nextUserDelay) {
        try {
            deliverRandomPhraseWithDelay(userDto, nextUserDelay);
        } catch (NoSuchPhraseException ex) {
            log.warn("No phrase available for user: {}", userDto.getId(), ex);
        } catch (IntegrationApiException ex) {
            log.error(LOG_MAX_DELAY_REACHED, maxDelay, ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error during phraseScheduler for user: {}", userDto, ex);
        }
    }

    /**
     * Attempts to deliver a random phrase to the specified user. If an {@link IntegrationApiException} occurs,
     * applies a delay before the next user attempt using the provided delay handler. The delay is not a retry for the current user.
     * The delay increases with each failure, up to a maximum, and is reset after a successful send.
     *
     * @param userDto       the user to send the phrase to
     * @param nextUserDelay the delay handler to apply before the next user if an integration error occurs
     * @throws NoSuchPhraseException   if no phrase is available for the user
     * @throws IntegrationApiException if integration fails and the maximum delay is reached
     */
    private void deliverRandomPhraseWithDelay(UserDto userDto, BackoffStrategy nextUserDelay) throws NoSuchPhraseException {
        try {
            phraseDeliveryManager.deliverRandomPhrase(userDto);
            nextUserDelay.resetValue(); // reset the delay after a successful delivery
        } catch (IntegrationApiException ex) {
            log.error(LOG_FAILED_SEND_AND_WAIT, userDto.getId(), ex);
            nextUserDelay.wait(ex);
        }
    }

}
