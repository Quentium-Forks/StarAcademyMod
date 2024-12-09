package abeshutt.staracademy.net;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.bit.BitBuffer;
import abeshutt.staracademy.world.StarterEntry;
import abeshutt.staracademy.world.data.PokemonStarterData;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.util.Identifier;

import java.util.*;

public class StarterRaffleUpdateS2CPacket extends ModPacket<ClientPlayNetworkHandler> {

    private Set<Identifier> starters;
    private Map<UUID, StarterEntry> entries;
    private long timeInterval;
    private long timeLeft;
    private boolean paused;
    private int selectionCooldown;

    public StarterRaffleUpdateS2CPacket() {

    }

    public StarterRaffleUpdateS2CPacket(Set<Identifier> starters, Map<UUID, StarterEntry> entries, long timeInterval,
                                        long timeLeft, boolean paused, int selectionCooldown) {
        this.starters = starters;
        this.entries = entries;
        this.timeInterval = timeInterval;
        this.timeLeft = timeLeft;
        this.paused = paused;
        this.selectionCooldown = selectionCooldown;
    }

    @Override
    public void onReceive(ClientPlayNetworkHandler listener) {
        if(this.starters != null) {
            PokemonStarterData.CLIENT.getStarters().clear();
            PokemonStarterData.CLIENT.getStarters().addAll(this.starters);
        }

        Map<UUID, StarterEntry> entries = PokemonStarterData.CLIENT.getEntries();

        if(this.entries == null) {
            entries.clear();
        } else {
            this.entries.forEach((uuid, pick) -> {
                if(pick == null) {
                    entries.remove(uuid);
                } else {
                    entries.put(uuid, pick);
                }
            });
        }

        PokemonStarterData.CLIENT.setTimeInterval(this.timeInterval);
        PokemonStarterData.CLIENT.setTimeLeft(this.timeLeft);
        PokemonStarterData.CLIENT.setPaused(this.paused);
        PokemonStarterData.CLIENT.setSelectionCooldown(this.selectionCooldown);
    }

    @Override
    public void writeBits(BitBuffer buffer) {
        Adapters.BOOLEAN.writeBits(this.starters == null, buffer);

        if(this.starters != null) {
            Adapters.INT_SEGMENTED_3.writeBits(this.starters.size(), buffer);
            this.starters.forEach(species -> Adapters.IDENTIFIER.writeBits(species, buffer));
        }

        Adapters.BOOLEAN.writeBits(this.entries == null, buffer);

        if(this.entries != null) {
            Adapters.INT_SEGMENTED_3.writeBits(this.entries.size(), buffer);

            this.entries.forEach((uuid, profile) -> {
                Adapters.UUID.writeBits(uuid, buffer);
                profile.writeBits(buffer);
            });
        }

        Adapters.LONG.writeBits(this.timeInterval, buffer);
        Adapters.LONG.writeBits(this.timeLeft, buffer);
        Adapters.BOOLEAN.writeBits(this.paused, buffer);
        Adapters.INT_SEGMENTED_3.writeBits(this.selectionCooldown, buffer);
    }

    @Override
    public void readBits(BitBuffer buffer) {
        if(Adapters.BOOLEAN.readBits(buffer).orElseThrow()) {
            this.starters = null;
        } else {
            this.starters = new LinkedHashSet<>();
            int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();

            for(int i = 0; i < size; i++) {
                this.starters.add(Adapters.IDENTIFIER.readBits(buffer).orElseThrow());
            }
        }

        if(Adapters.BOOLEAN.readBits(buffer).orElseThrow()) {
            this.entries = null;
        } else {
            this.entries = new HashMap<>();
            int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();

            for(int i = 0; i < size; i++) {
                UUID uuid = Adapters.UUID.readBits(buffer).orElseThrow();
                StarterEntry entry = new StarterEntry();
                entry.readBits(buffer);
                this.entries.put(uuid, entry);
            }
        }

        this.timeInterval = Adapters.LONG.readBits(buffer).orElseThrow();
        this.timeLeft = Adapters.LONG.readBits(buffer).orElseThrow();
        this.paused = Adapters.BOOLEAN.readBits(buffer).orElseThrow();
        this.selectionCooldown = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();
    }

}
