package abeshutt.staracademy.config;

import com.google.gson.annotations.Expose;

public class StarterRaffleConfig extends FileConfig {

    @Expose private long timeInterval;
    @Expose private boolean paused;
    @Expose private int selectionCooldown;

    @Override
    public String getPath() {
        return "starter_raffle";
    }

    public long getTimeInterval() {
        return this.timeInterval;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public int getSelectionCooldown() {
        return this.selectionCooldown;
    }

    @Override
    protected void reset() {
        this.timeInterval = 20 * 60 * 2;
        this.paused = true;
        this.selectionCooldown = 2;
    }

}
