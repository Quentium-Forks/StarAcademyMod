package abeshutt.staracademy.block.entity;

import abeshutt.staracademy.init.ModBlocks;
import abeshutt.staracademy.init.ModWorldData;
import abeshutt.staracademy.world.data.SafariData;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class SafariPortalBlockEntity extends BlockEntity {

    public SafariPortalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.Entities.SAFARI_PORTAL.get(), pos, state);
    }

    public void tick() {
        if(this.world == null || this.world.isClient()) return;
        SafariData data = ModWorldData.SAFARI.getGlobal(this.world);
        data.addPortal(this.world, this.pos);
    }

}
