package abeshutt.staracademy.outfit.core;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public abstract class OutfitPiece extends PlayerEntityModel<AbstractClientPlayerEntity> {

    public OutfitPiece(ModelPart root, boolean thinArms) {
        super(root, thinArms);
    }

    public abstract OutfitSlot getOutfitSlot();



}
