package abeshutt.staracademy.item;

import abeshutt.staracademy.data.adapter.basic.TypeSupplierAdapter;
import abeshutt.staracademy.data.serializable.ISerializable;
import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;

import java.util.Optional;

public class OutfitEntry implements ISerializable<NbtCompound, JsonObject> {

    @Override
    public Optional<NbtCompound> writeNbt() {
        return Optional.of(new NbtCompound());
    }

    @Override
    public void readNbt(NbtCompound nbt) {

    }

    @Override
    public Optional<JsonObject> writeJson() {
        return Optional.of(new JsonObject());
    }

    @Override
    public void readJson(JsonObject json) {

    }

    public static class Adapter extends TypeSupplierAdapter<OutfitEntry> {
        public Adapter() {
            super("type", true);
        }
    }

}
