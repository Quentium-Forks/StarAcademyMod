package abeshutt.staracademy.outfit.models;

import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.core.OutfitSlot;
import net.minecraft.client.model.*;

public class ClassyOutfit {

    public static class Hat extends OutfitPiece {

        public Hat(ModelPart root, boolean thinArms) {
            super(root, thinArms);
        }

        @Override
        public OutfitSlot getOutfitSlot() {
            return OutfitSlot.HEAD;
        }

        public static TexturedModelData getTexturedModelData() {
            ModelData modelData = new ModelData();
            ModelPartData modelPartData = modelData.getRoot();
            ModelPartData Head = modelPartData.addChild("Head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

            ModelPartData cube_r1 = Head.addChild("cube_r1", ModelPartBuilder.create().uv(48, 32).cuboid(-0.5F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-5.5F, -7.5F, -2.5F, 0.7854F, 0.0F, 0.0F));

            ModelPartData cube_r2 = Head.addChild("cube_r2", ModelPartBuilder.create().uv(48, 38).cuboid(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-6.5F, -7.0F, -1.0F, 1.5708F, 0.0F, 0.0F));

            ModelPartData cube_r3 = Head.addChild("cube_r3", ModelPartBuilder.create().uv(56, 44).cuboid(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.5F, -8.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

            ModelPartData cube_r4 = Head.addChild("cube_r4", ModelPartBuilder.create().uv(0, 19).cuboid(0.0F, -5.0F, -5.0F, 10.0F, 4.0F, 10.0F, new Dilation(0.0F))
                    .uv(0, 0).cuboid(-3.0F, -1.0F, -9.0F, 16.0F, 1.0F, 18.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -4.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

            return TexturedModelData.of(modelData, 128, 128);
        }

    }

}

