package abeshutt.staracademy.outfit.models;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.core.OutfitTexture;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;

public class PokemonHatOutfit {

    public static class Gengar extends OutfitPiece {

        public Gengar(String id) {
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

    public static class Goomy extends OutfitPiece {

        public Goomy(String id) {
            super(id);
        }

        @Override
        protected void buildMesh(ModelPartData modelPartData) {
            ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

            ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(26, 19).cuboid(-5.0F, 0.0F, -1.5F, 10.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.06F, -4.25F, 6.06F, -0.7854F, 0.0F, 0.0F));

            ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(26, 16).cuboid(-5.0F, 1.0F, -1.5F, 10.0F, 0.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.06F, -4.9571F, -6.7671F, 0.7854F, 0.0F, 0.0F));

            ModelPartData cube_r3 = head.addChild("cube_r3", ModelPartBuilder.create().uv(0, 26).cuboid(-1.5F, 0.0F, -5.0F, 3.0F, 0.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(6.0F, -4.25F, 0.0F, 0.0F, 0.0F, 0.7854F));

            ModelPartData cube_r4 = head.addChild("cube_r4", ModelPartBuilder.create().uv(0, 16).cuboid(-1.5F, 0.0F, -5.0F, 3.0F, 0.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(-6.1199F, -4.25F, 0.0F, 0.0F, 0.0F, -0.7854F));

            ModelPartData cube_r5 = head.addChild("cube_r5", ModelPartBuilder.create().uv(26, 22).cuboid(2.0F, -2.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F))
                    .uv(26, 30).cuboid(-4.0F, -2.0F, 1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -12.0F, 1.0F, -0.7854F, 0.0F, 0.0F));

            ModelPartData cube_r6 = head.addChild("cube_r6", ModelPartBuilder.create().uv(32, 5).cuboid(2.0F, -0.5F, 0.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F))
                    .uv(32, 0).cuboid(-4.0F, -0.5F, 0.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -10.5F, -3.0F, -0.3927F, 0.0F, 0.0F));
        }

        @Override
        protected OutfitTexture buildTexture() {
            return new OutfitTexture(64, 64,
                    StarAcademyMod.id("textures/entity/outfit/goomy_hat.png"),
                    StarAcademyMod.mid("outfit/goomy_hat", "inventory")
            );
        }

    }

    public static class Pikachu extends OutfitPiece {

        public Pikachu(String id) {
            super(id);
        }

        @Override
        protected void buildMesh(ModelPartData modelPartData) {
            ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(22, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.0F))
                    .uv(0, 0).cuboid(0.0F, -20.0F, 5.0F, 0.0F, 20.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

            ModelPartData cube_r1 = head.addChild("cube_r1", ModelPartBuilder.create().uv(22, 27).cuboid(0.5F, -4.0F, -1.5F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.75F, -12.0F, -0.5F, 0.0F, 0.0F, 0.3927F));

            ModelPartData cube_r2 = head.addChild("cube_r2", ModelPartBuilder.create().uv(22, 16).cuboid(-3.5F, -4.0F, -1.5F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-2.75F, -12.0F, -0.5F, 0.0F, 0.0F, -0.3927F));
        }

        @Override
        protected OutfitTexture buildTexture() {
            return new OutfitTexture(64, 64,
                    StarAcademyMod.id("textures/entity/outfit/goomy_hat.png"),
                    StarAcademyMod.mid("outfit/goomy_hat", "inventory")
            );
        }

    }

}
