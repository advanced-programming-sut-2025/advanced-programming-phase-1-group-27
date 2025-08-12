package org.example.server.models;

import org.example.common.models.Time;

public class ServerTimeTracker implements Runnable {
    private Time time;
    private long startTime;
    private boolean isRunning;
    private boolean isPaused;
    private final Object pauseLock = new Object();

    public ServerTimeTracker(Time time) {
        this.time = time;
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
        this.isPaused = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            synchronized (pauseLock) {
                if (isPaused) {
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                    if (!isRunning) {
                        break;
                    }
                }
            }

            try {
                Thread.sleep(10_000);
                time.passAnHour();
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void stop() {
        isRunning = false;
        resume();
    }

    public void pause() {
        isPaused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            isPaused = false;
            pauseLock.notifyAll();
        }
    }
}