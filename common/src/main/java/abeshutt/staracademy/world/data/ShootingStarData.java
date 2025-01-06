package abeshutt.staracademy.world.data;

import net.minecraft.nbt.NbtCompound;

import java.util.Optional;

public class ShootingStarData extends WorldData {

    @Override
    public Optional<NbtCompound> writeNbt() {
        return Optional.empty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {

    }

}
