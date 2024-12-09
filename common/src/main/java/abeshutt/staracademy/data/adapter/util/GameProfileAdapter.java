package abeshutt.staracademy.data.adapter.util;

import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.adapter.ISimpleAdapter;
import abeshutt.staracademy.data.bit.BitBuffer;
import abeshutt.staracademy.util.ProxyGameProfile;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

import java.util.Optional;

public class GameProfileAdapter implements ISimpleAdapter<GameProfile, NbtElement, JsonElement> {

    private final boolean nullable;

    public GameProfileAdapter(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public GameProfileAdapter asNullable() {
        return new GameProfileAdapter(true);
    }

    @Override
    public void writeBits(GameProfile value, BitBuffer buffer) {
        if(this.nullable) {
            buffer.writeBoolean(value == null);
        }

        if(value != null) {
            Adapters.UUID.asNullable().writeBits(value.getId(), buffer);
            Adapters.UTF_8.asNullable().writeBits(value.getName(), buffer);
            Adapters.BOOLEAN.writeBits(value.getProperties() == null || value.getProperties().isEmpty(), buffer);

            if(value.getProperties() != null && !value.getProperties().isEmpty()) {
                Adapters.INT_SEGMENTED_3.writeBits(value.getProperties().size(), buffer);

                value.getProperties().forEach((key, property) -> {
                    Adapters.UTF_8.asNullable().writeBits(key, buffer);
                    Adapters.UTF_8.asNullable().writeBits(property.getName(), buffer);
                    Adapters.UTF_8.asNullable().writeBits(property.getValue(), buffer);
                    Adapters.UTF_8.asNullable().writeBits(property.getSignature(), buffer);
                });
            }

            Adapters.BOOLEAN.writeBits(value.isLegacy(), buffer);
        }
    }

    @Override
    public Optional<GameProfile> readBits(BitBuffer buffer) {
        if(this.nullable && buffer.readBoolean()) {
            return Optional.empty();
        }

        GameProfile profile = new GameProfile(
            Adapters.UUID.asNullable().readBits(buffer).orElse(null),
            Adapters.UTF_8.asNullable().readBits(buffer).orElse(null)
        );

        if(!Adapters.BOOLEAN.readBits(buffer).orElseThrow()) {
            int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();

            for(int i = 0; i < size; i++) {
                String key = Adapters.UTF_8.asNullable().readBits(buffer).orElse(null);
                String name = Adapters.UTF_8.asNullable().readBits(buffer).orElse(null);
                String value = Adapters.UTF_8.asNullable().readBits(buffer).orElse(null);
                String signature = Adapters.UTF_8.asNullable().readBits(buffer).orElse(null);
                profile.getProperties().put(key, new Property(name, value, signature));
            }
        }

        ProxyGameProfile.of(profile).ifPresent(proxy -> {
            proxy.setLegacy(Adapters.BOOLEAN.readBits(buffer).orElseThrow());
        });

        return Optional.of(profile);
    }

    @Override
    public Optional<NbtElement> writeNbt(GameProfile value) {
        if(value.getProperties() == null && !value.isLegacy()) {
            if(value.getId() != null && value.getName() == null) {
                return Adapters.UUID.writeNbt(value.getId());
            } else if(value.getId() == null && value.getName() != null) {
                return Adapters.UTF_8.writeNbt(value.getName());
            }
        }

        return Optional.of(new NbtCompound()).map(nbt -> {
            if(value.getId() != null) {
                Adapters.UUID.writeNbt(value.getId()).ifPresent(tag -> nbt.put("id", tag));
            }

            if(value.getName() != null) {
                Adapters.UTF_8.writeNbt(value.getName()).ifPresent(tag -> nbt.put("name", tag));
            }

            if(value.getProperties() != null && !value.getProperties().isEmpty()) {
                NbtCompound properties = new NbtCompound();

                value.getProperties().forEach((key, property) -> {
                    NbtCompound entry = new NbtCompound();
                    Adapters.UTF_8.writeNbt( property.getName()).ifPresent(tag -> entry.put("name", tag));
                    Adapters.UTF_8.writeNbt( property.getValue()).ifPresent(tag -> entry.put("value", tag));
                    Adapters.UTF_8.writeNbt( property.getSignature()).ifPresent(tag -> entry.put("signature", tag));
                    properties.put(key, entry);
                });

                nbt.put("properties", properties);
            }

            if(value.isLegacy()) {
                nbt.putBoolean("legacy", true);
            }

            return nbt;
        });
    }

    @Override
    public Optional<GameProfile> readNbt(NbtElement nbt) {
        return Adapters.UUID.readNbt(nbt).map(uuid -> new GameProfile(uuid, null)).or(() -> {
                return Adapters.UTF_8.readNbt(nbt).map(name -> new GameProfile(null, name)).or(() -> {
                    if(!(nbt instanceof NbtCompound value)) {
                        return Optional.empty();
                    }

                    GameProfile profile = new GameProfile(
                        Adapters.UUID.readNbt(value.get("id")).orElse(null),
                        Adapters.UTF_8.readNbt(value.get("name")).orElse(null)
                    );

                    NbtCompound properties = value.getCompound("properties");

                    for(String key : properties.getKeys()) {
                        NbtCompound entry = properties.getCompound(key);

                        profile.getProperties().put(key, new Property(
                            Adapters.UTF_8.readNbt(entry.get("name")).orElse(null),
                            Adapters.UTF_8.readNbt(entry.get("value")).orElse(null),
                            Adapters.UTF_8.readNbt(entry.get("signature")).orElse(null)
                        ));
                    }

                    ProxyGameProfile.of(profile).ifPresent(proxy -> {
                        proxy.setLegacy(Adapters.BOOLEAN.readNbt(value.get("legacy")).orElse(false));
                    });

                    return Optional.of(profile);
                });
            });
    }

}
