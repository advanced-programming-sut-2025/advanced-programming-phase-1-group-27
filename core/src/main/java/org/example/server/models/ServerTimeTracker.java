package org.example.server.models;

import org.example.common.models.Time;

public class ServerTimeTracker implements Runnable {
    private Time time;
    private long startTime;
    private boolean isRunning;

    public ServerTimeTracker(Time time) {
        this.time = time;
        this.startTime = System.currentTimeMillis();
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(5_000); // each 30 seconds an hour passes
                time.passAnHour();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}
