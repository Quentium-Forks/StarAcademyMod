package abeshutt.staracademy.outfit.core;

import net.minecraft.client.model.*;
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

    protected ModelData getBaseModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        String[] partNames = {
                "head",
                "hat",
                "body",
                "right_arm",
                "right_leg",
                "right_sleeve",
                "right_pants",
                "left_arm",
                "left_leg",
                "left_sleeve",
                "left_pants",
                "ear",
                "cloak",
                "jacket",
        };

        for (String partName : partNames) {
            modelPartData.addChild(partName,
                    ModelPartBuilder.create(),
                    ModelTransform.pivot(0.0F, 0.0F, 0.0F)
            );
        }

        return modelData;
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