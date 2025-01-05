package abeshutt.staracademy.init;

import abeshutt.staracademy.entity.DuelingGloveEntity;
import abeshutt.staracademy.entity.ProfessorHansEntity;
import abeshutt.staracademy.entity.StarBadgeEntity;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModEntities extends ModRegistries {

    public static RegistrySupplier<EntityType<StarBadgeEntity>> STAR_BADGE;
    public static RegistrySupplier<EntityType<DuelingGloveEntity>> DUELING_GLOVE;
    public static RegistrySupplier<EntityType<ProfessorHansEntity>> PARTNER_NPC;

    public static void register() {
        STAR_BADGE = register("star_badge", StarBadgeEntity::new, SpawnGroup.MISC,
                builder -> builder.setDimensions(0.98F, 0.7F).maxTrackingRange(128));

        DUELING_GLOVE = register("dueling_glove", DuelingGloveEntity::new, SpawnGroup.MISC,
                builder -> builder.setDimensions(0.98F, 0.7F).maxTrackingRange(128));

        PARTNER_NPC = register("partner_npc", ProfessorHansEntity::new, SpawnGroup.MISC,
                builder -> builder.setDimensions(0.6F, 1.8F).maxTrackingRange(128));
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
