package abeshutt.staracademy.screen;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.world.data.WardrobeData;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class WardrobeScreen extends Screen {

    private static final Identifier TEXTURE = StarAcademyMod.id("textures/gui/wardrobe.png");

    protected int x, y;
    protected int backgroundWidth = 176;
    protected int backgroundHeight = 166;

    public WardrobeScreen() {
        super(Text.literal("Wardrobe"));
    }

//    @Override
//    public boolean shouldPause() {
//        return false;
//    }

    @Override
    protected void init() {
        super.init();
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        WardrobeData.Entry wardrobe = WardrobeData.CLIENT.getOrCreate(player.getUuid());

        draw9Slice(context, TEXTURE, this.x - 50, this.y,
                (int) (this.backgroundWidth * 0.5f), this.backgroundHeight,
                18, 0,
                19, 19,
                256, 256);

        draw9Slice(context, TEXTURE, this.x + (int) (this.backgroundWidth * 0.5f) / 2 + 4, this.y,
                this.backgroundWidth, this.backgroundHeight,
                18, 0,
                19, 19,
                256, 256);

        drawEntity(context, this.x, this.y + backgroundWidth / 2 + 50 + 10, 50,
                (float) (this.x + 51) - mouseX,
                (float) (this.y + 75 - 50) - mouseY, player);


//        render9Slice(context, TEXTURE, 10, 10, 300, 300, 18, 0, 19, 19, 256, 256);
//        render9Slice(context, TEXTURE, 320, 10, 150, 150, 18, 0, 19, 19, 256, 256);
    }

    public static void drawEntity(DrawContext context, int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        float f = (float) Math.atan((double) (mouseX / 40.0F));
        float g = (float) Math.atan((double) (mouseY / 40.0F));
        Quaternionf quaternionf = (new Quaternionf()).rotateZ(3.1415927F);
        Quaternionf quaternionf2 = (new Quaternionf()).rotateX(g * 20.0F * 0.017453292F);
        quaternionf.mul(quaternionf2);
        float h = entity.bodyYaw;
        float i = entity.getYaw();
        float j = entity.getPitch();
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;
        entity.bodyYaw = 180.0F + f * 20.0F;
        entity.setYaw(180.0F + f * 40.0F);
        entity.setPitch(-g * 20.0F);
        entity.headYaw = entity.getYaw();
        entity.prevHeadYaw = entity.getYaw();
        drawEntity(context, x, y, size, quaternionf, quaternionf2, entity);
        entity.bodyYaw = h;
        entity.setYaw(i);
        entity.setPitch(j);
        entity.prevHeadYaw = k;
        entity.headYaw = l;
    }

    public static void drawEntity(DrawContext context, int x, int y, int size, Quaternionf quaternionf, @Nullable Quaternionf quaternionf2, LivingEntity entity) {
        context.getMatrices().push();
        context.getMatrices().translate((double) x, (double) y, 50.0);
        context.getMatrices().multiplyPositionMatrix((new Matrix4f()).scaling((float) size, (float) size, (float) (-size)));
        context.getMatrices().multiply(quaternionf);
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        if (quaternionf2 != null) {
            quaternionf2.conjugate();
            entityRenderDispatcher.setRotation(quaternionf2);
        }

        entityRenderDispatcher.setRenderShadows(false);
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, context.getMatrices(), context.getVertexConsumers(), 15728880);
        });
        context.draw();
        entityRenderDispatcher.setRenderShadows(true);
        context.getMatrices().pop();
        DiffuseLighting.enableGuiDepthLighting();
    }

    // TODO: Move to a helper or smth
    protected void draw9Slice(DrawContext context, Identifier texture,
                              int x, int y,
                              int width, int height,
                              int u, int v,
                              int sliceW, int sliceH,
                              int texW, int texH) {
        int cornerWidth = (sliceW - 1) / 2;
        int cornerHeight = (sliceH - 1) / 2;

        // Corners
        context.drawTexture(texture, x, y, cornerWidth, cornerHeight, u, v, cornerWidth, cornerHeight, texW, texH);
        context.drawTexture(texture, x + width - 1, y, cornerWidth, cornerHeight, u + cornerWidth + 2, v, cornerWidth, cornerHeight, texW, texH);
        context.drawTexture(texture, x + width - 1, y + height - 1, cornerWidth, cornerHeight, u + cornerWidth + 2, v + cornerHeight + 2, cornerWidth, cornerHeight, texW, texH);
        context.drawTexture(texture, x, y + height - 1, cornerWidth, cornerHeight, u, v + cornerHeight + 2, cornerWidth, cornerHeight, texW, texH);

        // Edges
        context.drawTexture(texture, x + cornerWidth - 1, y, width - cornerWidth, cornerHeight, u + cornerWidth, v, 1, cornerHeight, texW, texH);
        context.drawTexture(texture, x, y + cornerHeight - 1, cornerWidth, height - cornerHeight, u, v + cornerHeight, cornerWidth, 1, texW, texH);
        context.drawTexture(texture, x + width - 2, y + cornerHeight - 1, cornerWidth, height - cornerHeight, u + cornerWidth + 1, v + cornerHeight, cornerWidth, 1, texW, texH);
        context.drawTexture(texture, x + cornerWidth - 1, y + height - 1, width - cornerWidth, cornerHeight, u + cornerWidth, v + cornerHeight + 2, 1, cornerHeight, texW, texH);

        // Center
        context.drawTexture(texture, x + cornerWidth - 1, y + cornerHeight - 1, width - cornerWidth, height - cornerHeight, u + cornerWidth, v + cornerHeight, 1, 1, texW, texH);
    }

}
