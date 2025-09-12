package it.vrad.motivational.telegram.bot.config;

import it.vrad.motivational.telegram.bot.config.properties.PhraseProperties;
import it.vrad.motivational.telegram.bot.infrastructure.job.PhraseNotificationJob;
import it.vrad.motivational.telegram.bot.infrastructure.util.DateUtility;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Quartz job configuration for scheduling phrase notification jobs.
 * Sets up job details, triggers, and the scheduler factory bean for phrase notifications.
 */
@Configuration
public class QuartzJobConfig {
    private final PhraseProperties phraseProperties;

    public QuartzJobConfig(PhraseProperties phraseProperties) {
        this.phraseProperties = phraseProperties;
    }

    /**
     * Configures and provides the {@link SchedulerFactoryBean} for Quartz.
     * Registers the application context, job factory, triggers, and job details.
     *
     * @param applicationContext          the Spring application context
     * @param phraseNotificationJobDetail the job detail for phrase notifications
     * @param phraseNotificationTriggers  the triggers for phrase notifications
     * @return the configured {@link SchedulerFactoryBean}
     */
    @Bean
    public SchedulerFactoryBean schedulerFactory(ApplicationContext applicationContext, JobDetail phraseNotificationJobDetail,
                                                 List<Trigger> phraseNotificationTriggers) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        schedulerFactoryBean.setApplicationContext(applicationContext);
        schedulerFactoryBean.setJobFactory(new SpringBeanJobFactory());
        schedulerFactoryBean.setJobDetails(phraseNotificationJobDetail);
        schedulerFactoryBean.setTriggers(phraseNotificationTriggers.toArray(new Trigger[0]));

        return schedulerFactoryBean;
    }

    /**
     * Creates a list of triggers for the phrase notification job, one for each sending time.
     *
     * @param phraseNotificationJobDetail the job detail for phrase notifications
     * @return a list of Quartz triggers for phrase notifications
     */
    @Bean
    public List<Trigger> phraseNotificationTriggers(JobDetail phraseNotificationJobDetail) {
        List<LocalTime> sendingTimes = phraseProperties.getSendingTimes();
        TimeZone timeZone = TimeZone.getTimeZone(phraseProperties.getTimeZone());

        List<Trigger> triggers = new ArrayList<>();
        // Create a trigger for each configured sending time
        for (int i = 0; i < sendingTimes.size(); i++) {
            String cronExpression = DateUtility.createDailyCronExpression(sendingTimes.get(i));
            triggers.add(
                    createPhraseNotificationTrigger(i, cronExpression, timeZone, phraseNotificationJobDetail)
            );
        }

        return triggers;
    }

    /**
     * Creates a Quartz trigger for the phrase notification job.
     *
     * @param index          the index of the trigger
     * @param cronExpression the cron expression for the trigger
     * @param timeZone       the time zone for the trigger
     * @param jobDetail      the job detail to associate with the trigger
     * @return the configured Quartz trigger
     */
    private Trigger createPhraseNotificationTrigger(int index, String cronExpression, TimeZone timeZone, JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(PhraseNotificationJob.TRIGGER_NAME + "_" + index)
                .withSchedule(
                        CronScheduleBuilder
                                .cronSchedule(cronExpression)
                                .inTimeZone(timeZone)
                ).build();
    }

    /**
     * Creates the job detail for the phrase notification job.
     *
     * @return the configured {@link JobDetail}
     */
    @Bean
    public JobDetail phraseNotificationJobDetail() {
        return JobBuilder.newJob(PhraseNotificationJob.class)
                .withIdentity(PhraseNotificationJob.JOB_NAME)
                .storeDurably()
                .build();
    }

}
