package abeshutt.staracademy.init;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;

import java.util.Map;

public class ModRenderers {

    public static class BlockEntities extends ModRenderers {
        public static void register(Map<BlockEntityType<?>, BlockEntityRendererFactory<?>> registry) {
        }
    }

    public static <T extends BlockEntity> BlockEntityRendererFactory<T> register(
            Map<BlockEntityType<?>, BlockEntityRendererFactory<?>> registry,
            BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> renderer) {
        registry.put(type, renderer);
        return renderer;
    }

}
