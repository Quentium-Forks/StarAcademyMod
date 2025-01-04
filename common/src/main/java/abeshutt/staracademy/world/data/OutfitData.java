package abeshutt.staracademy.world.data;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.bit.BitBuffer;
import abeshutt.staracademy.data.serializable.ISerializable;
import abeshutt.staracademy.init.ModNetwork;
import abeshutt.staracademy.init.ModWorldData;
import abeshutt.staracademy.net.UpdateOutfitS2CPacket;
import com.google.gson.JsonObject;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;

public class OutfitData extends WorldData {

    public static final OutfitData CLIENT = new OutfitData();

    private final Map<UUID, Entry> entries;

    public OutfitData() {
        this.entries = new HashMap<>();
    }

    public Map<UUID, Entry> getEntries() {
        return this.entries;
    }

    public Optional<Entry> get(UUID uuid) {
        return Optional.ofNullable(this.entries.get(uuid));
    }

    public Entry getOrCreate(UUID uuid) {
        return this.entries.computeIfAbsent(uuid, key -> new Entry());
    }

    public boolean isUnlocked(UUID uuid, Identifier id) {
        return this.get(uuid).map(entry -> entry.getUnlocked().contains(id)).orElse(false);
    }

    public boolean isEquipped(UUID uuid, Identifier id) {
        return this.get(uuid).map(entry -> entry.getEquipped().contains(id)).orElse(false);
    }

    public boolean setUnlocked(UUID uuid, Identifier id, boolean unlocked) {
        Entry entry = this.getOrCreate(uuid);

        if((unlocked && entry.getUnlocked().add(id)) || (!unlocked && entry.getUnlocked().remove(id))) {
            entry.setDirty(true);
            this.setDirty(true);
            return true;
        }

        return false;
    }

    public boolean setEquipped(UUID uuid, Identifier id, boolean equipped) {
        Entry entry = this.getOrCreate(uuid);

        if((equipped && entry.getEquipped().add(id)) || (!equipped && entry.getEquipped().remove(id))) {
            entry.setDirty(true);
            this.setDirty(true);
            return true;
        }

        return false;
    }

    private void onJoin(ServerPlayerEntity player) {
        ModNetwork.CHANNEL.sendToPlayer(player, new UpdateOutfitS2CPacket(player.getUuid(), this.getOrCreate(player.getUuid())));
    }

    public void onTick(MinecraftServer server) {
        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            this.get(player.getUuid()).ifPresent(entry -> {
                if(!entry.isDirty()) return;
                ModNetwork.CHANNEL.sendToPlayer(player, new UpdateOutfitS2CPacket(player.getUuid(), entry));
                entry.setDirty(true);
            });
        }
    }

    @Override
    public Optional<NbtCompound> writeNbt() {
        return Optional.of(new NbtCompound()).map(nbt -> {
            NbtCompound entries = new NbtCompound();

            this.entries.forEach((uuid, entry) -> {
                entry.writeNbt().ifPresent(tag -> entries.put(uuid.toString(), tag));
            });

            nbt.put("entries", entries);
            return nbt;
        });
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        this.entries.clear();
        NbtCompound entries = nbt.getCompound("entries");

        for(String key : entries.getKeys()) {
           Entry entry = new Entry();
           entry.readNbt(entries.getCompound(key));
           this.entries.put(UUID.fromString(key), entry);
        }
    }

    public static class Entry implements ISerializable<NbtCompound, JsonObject> {
        private final Set<Identifier> unlocked;
        private final Set<Identifier> equipped;
        private boolean dirty;

        public Entry() {
            this.unlocked = new HashSet<>();
            this.equipped = new HashSet<>();
        }

        public Set<Identifier> getUnlocked() {
            return this.unlocked;
        }

        public Set<Identifier> getEquipped() {
            return this.equipped;
        }

        public boolean isDirty() {
            return this.dirty;
        }

        public void setDirty(boolean dirty) {
            this.dirty = dirty;
        }

        @Override
        public void writeBits(BitBuffer buffer) {
            Adapters.INT_SEGMENTED_7.writeBits(this.unlocked.size(), buffer);

            for(Identifier id : this.unlocked) {
               Adapters.IDENTIFIER.writeBits(id, buffer);
            }

            Adapters.INT_SEGMENTED_7.writeBits(this.equipped.size(), buffer);

            for(Identifier id : this.equipped) {
                Adapters.IDENTIFIER.writeBits(id, buffer);
            }
        }

        @Override
        public void readBits(BitBuffer buffer) {
            this.unlocked.clear();
            int size = Adapters.INT_SEGMENTED_7.readBits(buffer).orElseThrow();

            for(int i = 0; i < size; i++) {
               Adapters.IDENTIFIER.readBits(buffer).ifPresent(this.unlocked::add);
            }

            this.equipped.clear();
            size = Adapters.INT_SEGMENTED_7.readBits(buffer).orElseThrow();

            for(int i = 0; i < size; i++) {
                Adapters.IDENTIFIER.readBits(buffer).ifPresent(this.equipped::add);
            }
        }

        @Override
        public Optional<NbtCompound> writeNbt() {
            return Optional.of(new NbtCompound()).map(nbt -> {
                NbtList unlocked = new NbtList();

                for(Identifier id : this.unlocked) {
                   Adapters.IDENTIFIER.writeNbt(id).ifPresent(unlocked::add);
                }

                nbt.put("unlocked", unlocked);
                NbtList equipped = new NbtList();

                for(Identifier id : this.equipped) {
                    Adapters.IDENTIFIER.writeNbt(id).ifPresent(equipped::add);
                }

                nbt.put("equipped", equipped);
                return nbt;
            });
        }

        @Override
        public void readNbt(NbtCompound nbt) {
            this.unlocked.clear();

            if(nbt.get("unlocked") instanceof NbtList unlocked) {
                for(NbtElement element : unlocked) {
                    Adapters.IDENTIFIER.readNbt(element).ifPresent(this.unlocked::add);
                }
            }

            this.equipped.clear();

            if(nbt.get("unlocked") instanceof NbtList equipped) {
                for(NbtElement element : equipped) {
                    Adapters.IDENTIFIER.readNbt(element).ifPresent(this.equipped::add);
                }
            }
        }
    }

    public static void init() {
        PlayerEvent.PLAYER_JOIN.register(player -> {
            OutfitData data = ModWorldData.OUTFIT.getGlobal(player.getWorld());
            data.onJoin(player);
        });

        TickEvent.SERVER_POST.register(server -> {
            OutfitData data = ModWorldData.OUTFIT.getGlobal(server);
            data.onTick(server);
        });
    }

}
