package abeshutt.staracademy.world.data;

import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ShootingStarData extends WorldData {

    private Map<UUID, ShootingStar> stars;

    public ShootingStarData() {
        this.stars = new HashMap<>();
    }

    @Override
    public Optional<NbtCompound> writeNbt() {
        return Optional.of(new NbtCompound()).map(nbt -> {
            return nbt;
        });
    }

    @Override
    public void readNbt(NbtCompound nbt) {

    }

}
