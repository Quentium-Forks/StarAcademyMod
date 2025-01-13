package abeshutt.staracademy.outfit.models;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.core.OutfitTexture;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

public class CarrotHeadBandOutfit extends OutfitPiece {

    public CarrotHeadBandOutfit(String id) {
        super(id);
    }

    @Override
    protected void buildMesh(ModelPartData modelPartData) {
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.75F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(0, 24).cuboid(-4.0F, 0.0F, -4.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(4.391F, -12.5615F, -1.25F, -1.5708F, 0.0F, -0.3927F));

        ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(0, 16).cuboid(4.0F, 0.0F, -4.0F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F))
                .uv(32, 0).cuboid(-6.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -9.5F, -1.25F, 0.0F, 0.0F, -0.3927F));
    }

    @Override
    protected OutfitTexture buildTexture() {
        return new OutfitTexture(32, 32,
                StarAcademyMod.id("textures/entity/outfit/carrot_head_band.png"),
                StarAcademyMod.mid("outfit/carrot_head_band", "inventory")
        );
    }

}


