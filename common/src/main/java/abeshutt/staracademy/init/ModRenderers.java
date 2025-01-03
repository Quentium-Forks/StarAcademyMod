package abeshutt.staracademy.init;

import abeshutt.staracademy.block.entity.BetterStructureBlockEntity;
import abeshutt.staracademy.block.entity.renderer.BetterStructureBlockEntityRenderer;
import abeshutt.staracademy.entity.renderer.HumanEntityRenderer;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

import java.util.Map;

public class ModRenderers extends ModRegistries {

    public static class Entities extends ModRenderers {
        public static void register() {
            ClientLifecycleEvent.CLIENT_SETUP.register(minecraft -> {
                EntityRenderers.register(ModEntities.STAR_BADGE.get(), FlyingItemEntityRenderer::new);
                EntityRenderers.register(ModEntities.PARTNER_NPC.get(), ctx -> new HumanEntityRenderer<>(ctx, false));
            });
        }
    }

    public static class BlockEntities extends ModRenderers {
        public static BlockEntityRendererFactory<BetterStructureBlockEntity> STRUCTURE_BLOCK;

        public static void register(Map<BlockEntityType<?>, BlockEntityRendererFactory<?>> registry) {
            try {
                STRUCTURE_BLOCK = register(registry, ModBlocks.Entities.STRUCTURE_BLOCK.get(), BetterStructureBlockEntityRenderer::new);
            } catch(Exception e) {
                ClientLifecycleEvent.CLIENT_SETUP.register(minecraft -> {
                    STRUCTURE_BLOCK = register(registry, ModBlocks.Entities.STRUCTURE_BLOCK.get(), BetterStructureBlockEntityRenderer::new);
                });
            }
        }
    }

    public static <T extends BlockEntity> BlockEntityRendererFactory<T> register(
            Map<BlockEntityType<?>, BlockEntityRendererFactory<?>> registry,
            BlockEntityType<? extends T> type, BlockEntityRendererFactory<T> renderer) {
        registry.put(type, renderer);
        return renderer;
    }

}
