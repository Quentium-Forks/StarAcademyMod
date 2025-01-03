package abeshutt.staracademy.net;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.bit.BitBuffer;
import abeshutt.staracademy.world.data.SafariData;
import abeshutt.staracademy.world.data.SafariData.Entry;
import net.minecraft.client.network.ClientPlayNetworkHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UpdateSafariS2CPacket extends ModPacket<ClientPlayNetworkHandler> {

    private long timeLeft;
    private Map<UUID, Entry> entries;

    public UpdateSafariS2CPacket() {

    }

    public UpdateSafariS2CPacket(long timeLeft, Map<UUID, Entry> entries) {
        this.timeLeft = timeLeft;
        this.entries = entries;
    }

    public UpdateSafariS2CPacket(long timeLeft, UUID uuid, Entry profile) {
        this.timeLeft = timeLeft;
        this.entries = new HashMap<>();
        this.entries.put(uuid, profile);
    }

    @Override
    public void onReceive(ClientPlayNetworkHandler listener) {
        SafariData.CLIENT.setTimeLeft(this.timeLeft);
        Map<UUID, Entry> entries = SafariData.CLIENT.getEntries();

        if(this.entries == null) {
            entries.clear();
        } else {
            this.entries.forEach((uuid, profile) -> {
                if(profile == null) {
                    entries.remove(uuid);
                } else {
                    entries.put(uuid, profile);
                }
            });
        }
    }

    @Override
    public void writeBits(BitBuffer buffer) {
        Adapters.LONG.writeBits(this.timeLeft, buffer);
        Adapters.BOOLEAN.writeBits(this.entries == null, buffer);

        if(this.entries != null) {
            Adapters.INT_SEGMENTED_3.writeBits(this.entries.size(), buffer);

            this.entries.forEach((uuid, entry) -> {
                Adapters.UUID.writeBits(uuid, buffer);
                entry.writeBits(buffer);
            });
        }
    }

    @Override
    public void readBits(BitBuffer buffer) {
        this.timeLeft = Adapters.LONG.readBits(buffer).orElseThrow();

        if(Adapters.BOOLEAN.readBits(buffer).orElseThrow()) {
            this.entries = null;
        } else {
            this.entries = new HashMap<>();
            int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();
            UUID uuid = Adapters.UUID.readBits(buffer).orElseThrow();
            Entry entry = new Entry();
            entry.readBits(buffer);

            for(int i = 0; i < size; i++) {
                this.entries.put(uuid, entry);
            }
        }
    }

}
