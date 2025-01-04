package abeshutt.staracademy.item.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class OutfitItemRenderer extends SpecialItemRenderer {

    public static final OutfitItemRenderer INSTANCE = new OutfitItemRenderer();

    @Override
    public boolean render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices,
                          VertexConsumerProvider vertexConsumers, int light, int overlay) {
        this.renderModel();
        return false;
    }

}
