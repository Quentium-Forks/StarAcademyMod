package abeshutt.staracademy.config;

import abeshutt.staracademy.world.roll.IntRoll;
import com.google.gson.annotations.Expose;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class PokemonSpawnConfig extends FileConfig {

    @Expose private double spawnProtectionDistance;
    @Expose private Map<Double, IntRoll> distanceToLevel;

    @Override
    public String getPath() {
        return "pokemon_spawn";
    }

    public double getSpawnProtectionDistance() {
        return this.spawnProtectionDistance;
    }

    public Optional<IntRoll> getLevel(double distance) {
        IntRoll roll = null;

        for(Map.Entry<Double, IntRoll> entry : this.distanceToLevel.entrySet()) {
           if(distance >= entry.getKey()) {
               roll = entry.getValue();
           } else {
               break;
           }
        }

        return Optional.ofNullable(roll);
    }

    @Override
    protected void reset() {
        this.spawnProtectionDistance = 100.0D;
        this.distanceToLevel = new LinkedHashMap<>();
        this.distanceToLevel.put(0.0D, IntRoll.ofUniform(0, 2));
        this.distanceToLevel.put(100.0D, IntRoll.ofUniform(0, 4));
        this.distanceToLevel.put(250.0D, IntRoll.ofUniform(0, 6));
    }

    @Override
    public <T extends Config> T read() {
        PokemonSpawnConfig config = super.read();
        Map<Double, IntRoll> ordered = new TreeMap<>(Double::compare);
        ordered.putAll(config.distanceToLevel);
        config.distanceToLevel = ordered;
        return (T)config;
    }

}
