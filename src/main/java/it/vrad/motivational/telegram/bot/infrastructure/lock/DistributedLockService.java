package it.vrad.motivational.telegram.bot.infrastructure.lock;

/**
 * Service interface for executing tasks with distributed locking.
 */
public interface DistributedLockService {

    /**
     * Executes the given task with a distributed lock on the specified key.
     * Attempts to acquire the lock within the given wait time, and if successful,
     * holds the lock for the specified lease time while executing the task.
     *
     * @param key           the lock key
     * @param task          the task to execute while the lock is held
     * @param lockWaitTime  the maximum time (in seconds) to wait for acquiring the lock
     * @param lockLeaseTime the duration (in seconds) to hold the lock after acquisition
     * @throws org.apache.commons.lang3.exception.UncheckedInterruptedException if interrupted while waiting for the lock
     */
    void executeWithLock(String key, Runnable task, long lockWaitTime, long lockLeaseTime);
}
