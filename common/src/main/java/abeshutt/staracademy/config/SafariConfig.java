package abeshutt.staracademy.config;

import abeshutt.staracademy.StarAcademyMod;
import com.google.gson.annotations.Expose;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SafariConfig extends FileConfig {

    @Expose private Identifier structure;
    @Expose private BlockPos placementOffset;
    @Expose private BlockPos spawnPositionRelative;
    @Expose private float spawnYaw;
    @Expose private float spawnPitch;
    @Expose private long safariDuration;
    @Expose private long playerDuration;
    @Expose private int providedSafariBalls;

    @Override
    public String getPath() {
        return "safari";
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

    public long getSafariDuration() {
        return this.safariDuration;
    }

    public long getPlayerDuration() {
        return this.playerDuration;
    }

    public int getProvidedSafariBalls() {
        return this.providedSafariBalls;
    }

    @Override
    protected void reset() {
        this.structure = StarAcademyMod.id("test");
        this.placementOffset = new BlockPos(0, 128, 0);
        this.spawnPositionRelative = new BlockPos(0, 2, 0);
        this.spawnYaw = 0.0F;
        this.spawnPitch = 0.0F;
        this.safariDuration = 24 * 60 * 60 * 20;
        this.playerDuration = 30 * 60 * 20;
        this.providedSafariBalls = 16;
    }

}
