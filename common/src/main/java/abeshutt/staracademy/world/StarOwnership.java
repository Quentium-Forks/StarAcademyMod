package abeshutt.staracademy.world;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.serializable.INbtSerializable;
import net.minecraft.nbt.NbtCompound;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class StarOwnership implements INbtSerializable<NbtCompound> {

    private UUID uuid;
    private long time;

    private StarOwnership() {

    }

    private StarOwnership(UUID uuid, long time) {
        this.uuid = uuid;
        this.time = time;
    }

    public static StarOwnership ofNow(UUID uuid) {
        long time = Instant.now().toEpochMilli();
        return new StarOwnership(uuid, time);
    }

    public static StarOwnership parseNbt(NbtCompound nbt) {
        StarOwnership value = new StarOwnership();
        value.readNbt(nbt);
        return value;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public long getTime() {
        return this.time;
    }

    @Override
    public Optional<NbtCompound> writeNbt() {
        return Optional.of(new NbtCompound()).map(nbt -> {
            Adapters.UUID.writeNbt(this.uuid).ifPresent(tag -> nbt.put("uuid", tag));
            Adapters.LONG.writeNbt(this.time).ifPresent(tag -> nbt.put("time", tag));
            return nbt;
        });
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.uuid = Adapters.UUID.readNbt(nbt.get("uuid")).orElseThrow();
        this.time = Adapters.LONG.readNbt(nbt.get("time")).orElseThrow();
    }

}
