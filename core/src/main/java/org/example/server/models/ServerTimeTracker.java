package org.example.server.models;

import org.example.common.models.Time;

import java.util.concurrent.atomic.AtomicBoolean;

public class ServerTimeTracker implements Runnable {
    private Time time;
    private long startTime;
    private AtomicBoolean isRunning;
    private AtomicBoolean isPaused;
    private final Object pauseLock = new Object();

    public ServerTimeTracker(Time time) {
        this.time = time;
        this.startTime = System.currentTimeMillis();
        this.isRunning = new AtomicBoolean(true);
        this.isPaused = new AtomicBoolean(false);
    }

    @Override
    public void run() {
        while (isRunning.get()) {
            synchronized (pauseLock) {
                if (isPaused.get()) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                    if (!isRunning.get()) {
                        break;
                    }
                }
            }

            try {
                Thread.sleep(10_000);
                if (isRunning.get() && !isPaused.get())
                    time.passAnHour();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void stop() {
        isRunning.set(false);
        resume();
    }

    public void pause() {
        isPaused.set(true);
    }

    public void resume() {
        synchronized (pauseLock) {
            isPaused.set(false);
            pauseLock.notifyAll();
        }
    }

    public boolean isRunning() {
        return isRunning.get() && !isPaused.get();
    }
}