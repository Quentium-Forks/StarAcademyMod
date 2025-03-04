package abeshutt.staracademy.world;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.bit.BitBuffer;
import abeshutt.staracademy.data.serializable.ISerializable;
import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class StarterEntry implements ISerializable<NbtCompound, JsonObject> {

    private Identifier pick;
    private final Map<Identifier, Integer> cooldowns;
    private Identifier granted;
    private boolean available;
    private boolean changed;

    public StarterEntry() {
        this.cooldowns = new HashMap<>();
        this.available = false;
        this.changed = true;
    }

    public Identifier getPick() {
        return this.pick;
    }

    public void setPick(Identifier pick) {
        this.pick = pick;
        this.setChanged(true);
    }

    public boolean isOnCooldown(Identifier species) {
        return this.cooldowns.getOrDefault(species, 0) > 0;
    }

    public void putOnCooldown(Identifier species, int rounds) {
        this.cooldowns.put(species, Math.max(this.cooldowns.getOrDefault(species, 0), rounds));
        this.setChanged(true);
    }

    public Identifier getGranted() {
        return this.granted;
    }

    public void setGranted(Identifier granted) {
        this.granted = granted;
        this.setChanged(true);
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
        this.setChanged(true);
    }

    public void onCompleteRound(int cooldown) {
        if(this.pick != null) {
            this.cooldowns.put(this.pick, this.cooldowns.getOrDefault(this.pick, 0) + cooldown + 1);
        }

        Iterator<Map.Entry<Identifier, Integer>> iterator = this.cooldowns.entrySet().iterator();

        while(iterator.hasNext()) {
            Map.Entry<Identifier, Integer> entry = iterator.next();
            entry.setValue(entry.getValue() - 1);

            if(entry.getValue() <= 0) {
                iterator.remove();
            }
        }

        this.pick = null;
        this.setChanged(true);
    }

    public boolean isChanged() {
        return this.changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public void writeBits(BitBuffer buffer) {
        Adapters.IDENTIFIER.asNullable().writeBits(this.pick, buffer);
        Adapters.INT_SEGMENTED_3.writeBits(this.cooldowns.size(), buffer);

        this.cooldowns.forEach((species, rounds) -> {
            Adapters.IDENTIFIER.writeBits(species, buffer);
            Adapters.INT_SEGMENTED_3.writeBits(rounds, buffer);
        });

        Adapters.IDENTIFIER.asNullable().writeBits(this.granted, buffer);
        Adapters.BOOLEAN.writeBits(this.available, buffer);
    }

    @Override
    public void readBits(BitBuffer buffer) {
        this.pick = Adapters.IDENTIFIER.asNullable().readBits(buffer).orElse(null);
        int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();
        this.cooldowns.clear();

        for(int i = 0; i < size; i++) {
            this.cooldowns.put(
                Adapters.IDENTIFIER.readBits(buffer).orElseThrow(),
                Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow()
            );
        }

        this.granted = Adapters.IDENTIFIER.asNullable().readBits(buffer).orElse(null);
        this.available = Adapters.BOOLEAN.readBits(buffer).orElseThrow();
    }

    @Override
    public Optional<NbtCompound> writeNbt() {
        return Optional.of(new NbtCompound()).map(nbt -> {
            Adapters.IDENTIFIER.writeNbt(this.pick).ifPresent(tag -> nbt.put("pick", tag));
            NbtCompound cooldowns = new NbtCompound();

            this.cooldowns.forEach((species, rounds) -> {
                Adapters.INT.writeNbt(rounds).ifPresent(tag -> cooldowns.put(species.toString(), tag));
            });

            nbt.put("cooldowns", cooldowns);
            Adapters.IDENTIFIER.writeNbt(this.granted).ifPresent(tag -> nbt.put("granted", tag));
            return nbt;
        });
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.pick = Adapters.IDENTIFIER.readNbt(nbt.get("pick")).orElse(null);
        NbtCompound cooldowns = nbt.getCompound("cooldowns");
        this.cooldowns.clear();

        for(String key : cooldowns.getKeys()) {
           Adapters.INT.readNbt(cooldowns.getCompound(key)).ifPresent(tag -> this.cooldowns.put(Identifier.tryParse(key), tag));
        }

        this.granted = Adapters.IDENTIFIER.readNbt(nbt.get("granted")).orElse(null);
    }

}
