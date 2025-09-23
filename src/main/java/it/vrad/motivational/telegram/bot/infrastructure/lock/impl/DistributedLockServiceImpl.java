package it.vrad.motivational.telegram.bot.infrastructure.lock.impl;

import it.vrad.motivational.telegram.bot.infrastructure.exception.util.ExceptionUtility;
import it.vrad.motivational.telegram.bot.infrastructure.lock.DistributedLockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.UncheckedInterruptedException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Implementation of {@link DistributedLockService} using Redisson for distributed locking.
 */
@Slf4j
@Service
public class DistributedLockServiceImpl implements DistributedLockService {
    private final RedissonClient redissonClient;

    /**
     * Constructs a DistributedLockServiceImpl with the provided RedissonClient.
     *
     * @param redissonClient the Redisson client for distributed locking
     */
    public DistributedLockServiceImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * {@inheritDoc}
     *
     * @param key           the lock key
     * @param task          the task to execute while the lock is held
     * @param lockWaitTime  the maximum time (in seconds) to wait for acquiring the lock
     * @param lockLeaseTime the duration (in seconds) to hold the lock after acquisition
     * @throws org.apache.commons.lang3.exception.UncheckedInterruptedException if interrupted while waiting for the lock
     */
    public void executeWithLock(String key, Runnable task, long lockWaitTime, long lockLeaseTime) {
        ExceptionUtility.requireNonBlank(key);

        // Obtain the distributed lock for the given key
        RLock lock = redissonClient.getLock(key);
        try {
            log.info("Attempting to acquire distributed lock for key '{}'", key);
            // Try to acquire the lock
            if (lock.tryLock(lockWaitTime, lockLeaseTime, TimeUnit.SECONDS)) {
                log.info("Distributed lock acquired for key '{}'", key);
                runTask(task, lock, key);
            } else {
                log.info("Distributed lock NOT acquired for key '{}'", key);
            }
        } catch (InterruptedException e) {
            throw new UncheckedInterruptedException(e);
        }
    }

    /**
     * Runs the provided task and ensures the lock is released afterward.
     *
     * @param task the task to run
     * @param lock the acquired lock
     */
    private static void runTask(Runnable task, RLock lock, String key) {
        try {
            task.run();
        } finally {
            // Always unlock after task execution
            lock.unlock();
            log.info("Distributed lock released for key '{}'", key);
        }
    }

}
