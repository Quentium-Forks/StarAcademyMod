package abeshutt.staracademy.outfit.models;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.core.OutfitTexture;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

public class Classy1ShirtOutfit extends OutfitPiece {

    public Classy1ShirtOutfit(String id) {
        super(id);
    }

    @Override
    protected void buildMesh(ModelPartData root) {
        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(1.01F))
                .uv(0, 48).cuboid(-1.5F, 1.0F, -4.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(48, 5).cuboid(-1.0F, 3.0F, -3.5F, 2.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_arm = root.addChild("right_arm", ModelPartBuilder.create().uv(24, 16).cuboid(-4.5F, 1.0F, -3.5F, 5.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData left_arm = root.addChild("left_arm", ModelPartBuilder.create().uv(24, 25).cuboid(-0.5F, 1.0F, -3.5F, 5.0F, 2.0F, 7.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(1.0F)), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
    }

    @Override
    protected OutfitTexture buildTexture() {
        return new OutfitTexture(128, 128,
                StarAcademyMod.id("textures/entity/outfit/classy1.png"),
                StarAcademyMod.mid("outfit/classy1_shirt", "#inventory"));
    }

}