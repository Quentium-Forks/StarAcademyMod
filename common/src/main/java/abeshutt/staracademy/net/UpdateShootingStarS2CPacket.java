package abeshutt.staracademy.net;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.data.bit.BitBuffer;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class UpdateShootingStarS2CPacket extends ModPacket<ClientPlayNetworkHandler> {

    private int entityId;
    private Vec3d pos;

    public UpdateShootingStarS2CPacket() {

    }

    public UpdateShootingStarS2CPacket(int entityId, Vec3d pos) {
        this.entityId = entityId;
        this.pos = pos;
    }

    @Override
    public void onReceive(ClientPlayNetworkHandler listener) {
        if(listener == null) return;
        Entity entity = listener.getWorld().getEntityById(this.entityId);

        if(entity != null) {
            entity.prevX = this.pos.x;
            entity.prevY = this.pos.y;
            entity.prevZ = this.pos.z;
        }
    }

    @Override
    public void writeBits(BitBuffer buffer) {
        Adapters.INT_SEGMENTED_7.writeBits(this.entityId, buffer);
        Adapters.VEC_3D.writeBits(this.pos, buffer);
    }

    @Override
    public void readBits(BitBuffer buffer) {
        this.entityId = Adapters.INT_SEGMENTED_7.readBits(buffer).orElseThrow();
        this.pos = Adapters.VEC_3D.readBits(buffer).orElseThrow();
    }

}
