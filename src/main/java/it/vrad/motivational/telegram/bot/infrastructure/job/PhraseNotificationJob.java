package it.vrad.motivational.telegram.bot.infrastructure.job;

import it.vrad.motivational.telegram.bot.config.properties.PhraseProperties;
import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionLogMessageHelper;
import it.vrad.motivational.telegram.bot.infrastructure.job.producer.PhraseNotificationJobProducer;
import it.vrad.motivational.telegram.bot.infrastructure.lock.DistributedLockService;
import it.vrad.motivational.telegram.bot.infrastructure.lock.LockConstants;
import it.vrad.motivational.telegram.bot.infrastructure.logging.constants.MdcConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Quartz job responsible for sending phrase notifications.
 * <p>
 * Use a distributed lock to ensure that only one instance executes the job.
 * The notification logic is delegated to {@link PhraseNotificationJobProducer#createNotificationJob()}.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PhraseNotificationJob implements Job {
    public static final String JOB_NAME = "phraseNotificationJob";
    public static final String TRIGGER_NAME = "phraseNotificationJobTrigger";

    private final PhraseNotificationJobProducer phraseNotificationJobProducer;
    private final DistributedLockService distributedLockService;
    private final PhraseProperties phraseProperties;

    /**
     * Executes the phrase notification job.
     * <p>
     * Acquires a distributed lock before running the notification logic to prevent concurrent execution.
     * If an exception occurs, logs the error and wraps it in a {@link JobExecutionException}.
     *
     * @param jobExecutionContext the Quartz job execution context
     * @throws JobExecutionException if any exception occurs during job execution
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            MDC.put(MdcConstants.REQUEST_ID, UUID.randomUUID().toString());

            // Acquire distributed lock and execute the notification job
            distributedLockService.executeWithLock(
                    LockConstants.NOTIFICATION_PHRASE_JOB_LOCK_KEY,
                    phraseNotificationJobProducer.createNotificationJob(),
                    0,
                    phraseProperties.getSchedulerLockLeaseTime()
            );
        } catch (Exception ex) {
            // Log and wrap any exception in a JobExecutionException
            log.error(ExceptionLogMessageHelper.getJobExecutionMessage(JOB_NAME), ex);
            throw new JobExecutionException(ex);
        } finally {
            MDC.remove(MdcConstants.REQUEST_ID);
        }
    }
}
