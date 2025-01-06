package abeshutt.staracademy.entity.renderer;

import abeshutt.staracademy.entity.ShootingStarEntity;
import abeshutt.staracademy.entity.model.ShootingStarModel;
import abeshutt.staracademy.entity.model.StarBadgeBeltModel;
import abeshutt.staracademy.init.ModItems;
import abeshutt.staracademy.world.data.StarBadgeData;
import abeshutt.staracademy.world.inventory.BaseInventory;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class ShootingStarRenderer extends EntityRenderer<ShootingStarEntity> {

    private final ShootingStarModel model;

    public ShootingStarRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new ShootingStarModel();
    }

    @Override
    public void render(ShootingStarEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        DiffuseLighting.enableGuiDepthLighting();
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();

        matrices.push();
        matrices.scale(5.0F, 5.0F, 5.0F);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(ShootingStarModel.TEXTURE));
        this.model.render(matrices, vertexConsumer, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();

        DiffuseLighting.disableGuiDepthLighting();
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
    }

    @Override
    public Identifier getTexture(ShootingStarEntity entity) {
        return ShootingStarModel.TEXTURE;
    }

    @Override
    public boolean shouldRender(ShootingStarEntity entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

}
