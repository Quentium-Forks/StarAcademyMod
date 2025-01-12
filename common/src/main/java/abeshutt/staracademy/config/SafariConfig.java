package abeshutt.staracademy.config;

import abeshutt.staracademy.StarAcademyMod;
import com.google.gson.annotations.Expose;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SafariConfig extends FileConfig {

    @Expose private long seed;
    @Expose private Identifier structure;
    @Expose private BlockPos placementOffset;
    @Expose private BlockPos spawnPositionRelative;
    @Expose private float spawnYaw;
    @Expose private float spawnPitch;
    @Expose private long playerDuration;
    @Expose private int providedSafariBalls;
    @Expose private long startEpoch;
    @Expose private long restartDelay;
    @Expose private boolean paused;

    @Override
    public String getPath() {
        return "safari";
    }

    public long getSeed() {
        return this.seed;
    }

    public Identifier getStructure() {
        return this.structure;
    }

    public BlockPos getPlacementOffset() {
        return this.placementOffset;
    }

    public BlockPos getRelativeSpawnPosition() {
        return this.spawnPositionRelative;
    }

    public float getSpawnYaw() {
        return this.spawnYaw;
    }

    public float getSpawnPitch() {
        return this.spawnPitch;
    }

    public long getPlayerDuration() {
        return this.playerDuration;
    }

    public int getProvidedSafariBalls() {
        return this.providedSafariBalls;
    }

    public boolean isPaused() {
        return this.paused;
    }

    public long getTimeLeft(long lastUpdated) {
        long refreshes = (lastUpdated - this.startEpoch) / this.restartDelay;
        long nextRefresh = this.startEpoch + (refreshes + 1) * this.restartDelay;
        return nextRefresh - System.currentTimeMillis();
    }

    @Override
    protected void reset() {
        this.seed = -4534904328483650727L;
        this.structure = StarAcademyMod.id("test");
        this.placementOffset = new BlockPos(0, 128, 0);
        this.spawnPositionRelative = new BlockPos(0, 2, 0);
        this.spawnYaw = 0.0F;
        this.spawnPitch = 0.0F;
        this.playerDuration = 30 * 60 * 20;
        this.providedSafariBalls = 16;
        this.startEpoch = 0;
        this.restartDelay = 1000 * 60 * 60 * 24;
        this.paused = false;
    }

}
