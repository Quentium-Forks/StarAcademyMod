package abeshutt.staracademy.init;

import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.utils.Env;
import abeshutt.staracademy.StarAcademyMod;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModRegistries {

    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(StarAcademyMod.ID, RegistryKeys.ITEM);
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(StarAcademyMod.ID, RegistryKeys.BLOCK);
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(StarAcademyMod.ID, RegistryKeys.ENTITY_TYPE);
    public static DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(StarAcademyMod.ID, RegistryKeys.BLOCK_ENTITY_TYPE);
    public static DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLERS = DeferredRegister.create(StarAcademyMod.ID, RegistryKeys.SCREEN_HANDLER);

    public static void register() {
        ModItems.register();
        ModBlocks.register();
        ModBlocks.Entities.register();
        ModEntities.register();
        ModScreenHandlers.register();
        ModNetwork.register();

        BLOCKS.register();
        ITEMS.register();
        ENTITIES.register();
        BLOCK_ENTITY_TYPES.register();
        SCREEN_HANDLERS.register();

        if(Platform.getEnvironment() == Env.CLIENT) {
            ModScreens.register();
        }

        ModWorldData.register();
        ModConfigs.register(true);
    }

    public static <T, V extends T> RegistrySupplier<V> register(DeferredRegister<T> registry, Identifier id, Supplier<V> value) {
        return registry.register(id, value);
    }

    public static <T, V extends T> RegistrySupplier<V> register(DeferredRegister<T> registry, String name, Supplier<V> value) {
        return registry.register(name, value);
    }

}
