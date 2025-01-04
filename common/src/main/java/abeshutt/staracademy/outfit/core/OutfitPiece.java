package abeshutt.staracademy.outfit.core;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.util.Identifier;

public abstract class OutfitPiece {

    protected Identifier id;
    protected boolean thinArms;

    public OutfitPiece(Identifier id, boolean thinArms) {
        this.id = id;
        this.thinArms = thinArms;
    }

    public abstract TexturedModelData getTexturedModelData();

    public OutfitPieceModel buildModel() {
        TexturedModelData texturedModelData = getTexturedModelData();
        ModelPart root = texturedModelData.createModel();
        return new OutfitPieceModel(root, this.thinArms);
    }

    public static class OutfitPieceModel extends PlayerEntityModel<AbstractClientPlayerEntity> {

        public OutfitPieceModel(ModelPart root, boolean thinArms) {
            super(root, thinArms);
        }

    }

}