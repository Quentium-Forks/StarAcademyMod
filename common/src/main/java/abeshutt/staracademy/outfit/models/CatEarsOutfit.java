package abeshutt.staracademy.outfit.models;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.core.OutfitTexture;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

public class CatEarsOutfit extends OutfitPiece {

    public CatEarsOutfit(String id) {
        super(id);
    }

    @Override
    protected void buildMesh(ModelPartData modelPartData) {
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.75F))
                .uv(0, 16).cuboid(-3.0F, -11.75F, -3.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(6, 16).cuboid(1.0F, -11.75F, -3.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(16, 16).cuboid(3.0F, -10.75F, -3.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
                .uv(12, 16).cuboid(-4.0F, -10.75F, -3.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
    }

    @Override
    protected OutfitTexture buildTexture() {
        return new OutfitTexture(32, 32,
                StarAcademyMod.id("textures/entity/outfit/cat_ears.png"),
                StarAcademyMod.mid("outfit/cat_ears", "inventory")
        );
    }

}


