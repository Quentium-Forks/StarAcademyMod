package abeshutt.staracademy.init;

import abeshutt.staracademy.entity.StarBadgeEntity;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.api.EnvType;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModEntities extends ModRegistries {

    public static RegistrySupplier<EntityType<StarBadgeEntity>> STAR_BADGE;

    public static void register() {
        STAR_BADGE = register("star_badge", StarBadgeEntity::new, SpawnGroup.MISC,
                builder -> builder.setDimensions(0.98F, 0.7F).maxTrackingRange(128));

        if(Platform.getEnv() == EnvType.CLIENT) {
            ClientLifecycleEvent.CLIENT_SETUP.register(minecraft -> {
                EntityRenderers.register(STAR_BADGE.get(), FlyingItemEntityRenderer::new);
            });
        }
    }

    public static <V extends Entity> RegistrySupplier<EntityType<V>> register(Identifier id, EntityType.EntityFactory<V> factory,
                                                                              SpawnGroup group, Consumer<EntityType.Builder<V>> config) {
        return register(ENTITIES, id, () -> {
            EntityType.Builder<V> builder = EntityType.Builder.create(factory, group);
            config.accept(builder);
            return builder.build(id.getPath());
        });
    }

    public static <V extends Entity> RegistrySupplier<EntityType<V>> register(String name, EntityType.EntityFactory<V> factory,
                                                                              SpawnGroup group, Consumer<EntityType.Builder<V>> config) {
        return register(ENTITIES, name, () -> {
            EntityType.Builder<V> builder = EntityType.Builder.create(factory, group);
            config.accept(builder);
            return builder.build(name);
        });
    }

}
