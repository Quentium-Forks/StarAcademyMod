package abeshutt.staracademy.outfit.core;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.outfit.models.ClassyOutfit;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OutfitFeatureRenderer<
        M extends PlayerEntityModel<AbstractClientPlayerEntity>
        > extends FeatureRenderer<AbstractClientPlayerEntity, M> {

    public OutfitFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // TODO: For each equipped outfit pieces
        for (int i = 0; i < 1; i++) {
            // TODO: Get equipped outfit piece models one by one
            OutfitPiece HAT_PIECE = new ClassyOutfit.Hat(StarAcademyMod.id("classy.hat"), false);
            OutfitPiece.OutfitPieceModel model = HAT_PIECE.buildModel();


            // TODO: Fetch texture for current outfit piece model
            String id = "textures/models/armor/" + "gold" + "_layer_"
                    + 1 // + (secondLayer ? 2 : 1)
                    + "" // + (overlay == null ? "" : "_" + overlay)
                    + ".png";
            VertexConsumer vertices = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(
                    new Identifier(id)
            ));

            // TODO: Copy rotations & render the model
            this.getContextModel().copyBipedStateTo(model);
            model.render(matrices, vertices, light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        }
    }

}
