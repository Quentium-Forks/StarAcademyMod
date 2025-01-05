package abeshutt.staracademy.outfit.models;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.core.OutfitTexture;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

public class GengarHatOutfit extends OutfitPiece {

    public GengarHatOutfit(String id) {
        super(id);
    }

    @Override
    protected void buildMesh(ModelPartData modelPartData) {
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(1.0F))
                .uv(8, 24).cuboid(-1.0F, -13.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(16, 24).cuboid(-3.0F, -12.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(22, 24).cuboid(2.0F, -12.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(0, 18).cuboid(-4.0F, 1.0F, 6.0F, 8.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -7.75F, -5.5F, -1.1781F, 0.0F, 0.0F));

        ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(0, 24).cuboid(0.25F, -1.5F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(18, 18).cuboid(-3.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -13.0F, 0.0F, 0.0F, 0.0F, -0.3927F));
    }

    @Override
    protected OutfitTexture buildTexture() {
        return new OutfitTexture(32, 32,
                StarAcademyMod.id("textures/entity/outfit/gengar_hat.png"),
                StarAcademyMod.mid("outfit/gengar_hat", "inventory")
        );
    }

}