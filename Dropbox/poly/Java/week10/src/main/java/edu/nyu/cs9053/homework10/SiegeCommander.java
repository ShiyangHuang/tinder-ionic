package edu.nyu.cs9053.homework10;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * User: blangel
 * Date: 4/15/15
 * Time: 7:48 AM
 */
public class SiegeCommander {
	
    private final DoSDefense doSDefense;

    private final int concurrencyFactor;

    private final int attachSize;

    private final AtomicInteger running;

    private final Random random;

    private final ScheduledExecutorService executor;

    private final AtomicLong requestCounter;

    public SiegeCommander(DoSDefense doSDefense, int concurrencyFactor) {
        this.doSDefense = doSDefense;
        this.concurrencyFactor = concurrencyFactor;
        this.running = new AtomicInteger(0);
        this.random = new Random();
        long attachSizeLong = (concurrencyFactor * 100L);
        if (attachSizeLong > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }
        this.attachSize = (int) attachSizeLong;
        this.executor = Executors.newScheduledThreadPool(attachSize, new ThreadFactory() {
            private final AtomicInteger poolNumber = new AtomicInteger(1);
            private final ThreadGroup group;
            private final AtomicInteger threadNumber = new AtomicInteger(1);
            private final String namePrefix;
            {
                SecurityManager s = System.getSecurityManager();
                group = (s != null) ? s.getThreadGroup() :
                        Thread.currentThread().getThreadGroup();
                namePrefix = "attacker-" +
                        poolNumber.getAndIncrement() +
                        "-thread-";
            }
            public Thread newThread(Runnable work) {
                Thread thread = new Thread(group, work, namePrefix + threadNumber.getAndIncrement(), 0);
                if (thread.isDaemon()) {
                    thread.setDaemon(false);
                }
                if (thread.getPriority() != Thread.NORM_PRIORITY) {
                    thread.setPriority(Thread.NORM_PRIORITY);
                }
                return thread;
            }
        });
        this.requestCounter = new AtomicLong(0);
    }

    public void start() {
        System.out.printf("Starting siege, hopefully you're fortified...%n");
        executor.scheduleWithFixedDelay(new Runnable() {
            @Override public void run() {
                int currentlyRunning = running.get();
                boolean tooMany = (currentlyRunning > concurrencyFactor);
                if (tooMany || (currentlyRunning < Math.max(1, concurrencyFactor / 2))) {
                	System.out.println("currentlyRunning"+ currentlyRunning);
                	System.out.println("concurrencyFactor"+ concurrencyFactor);
                    System.out.printf("FAIL! You just got PWNED (%s)!%n", (tooMany ? "DoS system overwhelmed" : "DoS too few requests being processed"));
                    doSDefense.stop();
                    executor.shutdownNow();
                }
            }
        }, 1L, 1L, TimeUnit.SECONDS);
        for (int i = 0; i < attachSize; i++) {
            executor.submit(new Runnable() {
                @Override public void run() {
                    process(this);
                }
            });
        }
    }

    private void process(final Runnable runnable) {
        doSDefense.processRequest(new RequestHandler() {
            @Override public void handleRequest() {
                running.incrementAndGet();
                try {
                    System.out.printf("Processing request %d%n", requestCounter.incrementAndGet());
                    Thread.sleep(random.nextInt(5) * 1000L);
                    executor.submit(runnable);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException();
                } finally {
                    running.decrementAndGet();
                }
            }
        });
    }

}
