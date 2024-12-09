package abeshutt.staracademy.net;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.bit.BitBuffer;
import abeshutt.staracademy.world.data.StarBadgeData;
import abeshutt.staracademy.world.inventory.BaseInventory;
import net.minecraft.client.network.ClientPlayNetworkHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StarBadgeUpdateS2CPacket extends ModPacket<ClientPlayNetworkHandler> {

    private Map<UUID, BaseInventory> inventories;

    public StarBadgeUpdateS2CPacket() {

    }

    public StarBadgeUpdateS2CPacket(Map<UUID, BaseInventory> profiles) {
        this.inventories = profiles;
    }

    public StarBadgeUpdateS2CPacket(UUID uuid, BaseInventory inventory) {
        this.inventories = new HashMap<>();
        this.inventories.put(uuid, inventory);
    }

    @Override
    public void onReceive(ClientPlayNetworkHandler listener) {
        Map<UUID, BaseInventory> profiles = StarBadgeData.CLIENT.getInventories();

        if(this.inventories == null) {
            profiles.clear();
        } else {
            this.inventories.forEach((uuid, profile) -> {
                if(profile == null) {
                    profiles.remove(uuid);
                } else {
                    profiles.put(uuid, profile);
                }
            });
        }
    }

    @Override
    public void writeBits(BitBuffer buffer) {
        Adapters.BOOLEAN.writeBits(this.inventories == null, buffer);

        if(this.inventories != null) {
            Adapters.INT_SEGMENTED_3.writeBits(this.inventories.size(), buffer);

            this.inventories.forEach((uuid, inventory) -> {
                Adapters.UUID.writeBits(uuid, buffer);
                Adapters.COMPOUND_NBT.asNullable().writeBits(inventory == null ? null :
                        inventory.writeNbt().orElse(null), buffer);
            });
        }
    }

    @Override
    public void readBits(BitBuffer buffer) {
        if(Adapters.BOOLEAN.readBits(buffer).orElseThrow()) {
            this.inventories = null;
        } else {
            this.inventories = new HashMap<>();
            int size = Adapters.INT_SEGMENTED_3.readBits(buffer).orElseThrow();

            for(int i = 0; i < size; i++) {
                UUID uuid = Adapters.UUID.readBits(buffer).orElseThrow();
                BaseInventory inventory = Adapters.COMPOUND_NBT.asNullable().readBits(buffer).map(tag -> {
                    BaseInventory value = new BaseInventory();
                    value.readNbt(tag);
                    return value;
                }).orElse(null);

               this.inventories.put(uuid, inventory);
            }
        }
    }

}
