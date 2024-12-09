package abeshutt.staracademy.init;

import com.mojang.datafixers.types.Type;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.BlockEntityType.BlockEntityFactory;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks extends ModRegistries {

    public static void register() {

    }

    public static class Entities extends ModBlocks {
        public static void register() {

        }
    }

    public static <V extends Block> RegistrySupplier<V> register(Identifier id, Supplier<V> block) {
        return register(id, block);
    }

    public static <V extends Block> RegistrySupplier<V> register(String name, Supplier<V> block) {
        return register(name, block);
    }

    public static <V extends Block> RegistrySupplier<V> register(Identifier id, Supplier<V> block, Function<RegistrySupplier<V>, BlockItem> item) {
        return register(id, block, id, item);
    }

    public static <V extends Block> RegistrySupplier<V> register(String name, Supplier<V> block, Function<RegistrySupplier<V>, BlockItem> item) {
        return register(name, block, name, item);
    }

    public static <V extends Block> RegistrySupplier<V> register(Identifier id, Supplier<V> block, Identifier itemId, Function<RegistrySupplier<V>, BlockItem> item) {
        RegistrySupplier<V> entry = register(BLOCKS, id, block);

        if(item != null) {
            Supplier<BlockItem> value = () -> item.apply(entry);
            ModItems.register(itemId, value);
        }

        return entry;
    }

    public static <V extends Block> RegistrySupplier<V> register(String name, Supplier<V> block, String itemName, Function<RegistrySupplier<V>, BlockItem> item) {
        RegistrySupplier<V> entry = register(BLOCKS, name, block);

        if(item != null) {
            Supplier<BlockItem> value = () -> item.apply(entry);
            ModItems.register(itemName, value);
        }

        return entry;
    }

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(Identifier id, BlockEntityFactory<T> blockEntity, RegistrySupplier<Block>... blocks) {
        return register(id, blockEntity, Util.getChoiceType(TypeReferences.BLOCK_ENTITY, null), blocks);
    }

    public static <T extends BlockEntity>RegistrySupplier<BlockEntityType<T>> register(Identifier id, BlockEntityFactory<T>blockEntity, String typeId, RegistrySupplier<Block>... blocks) {
        return register(id, blockEntity, Util.getChoiceType(TypeReferences.BLOCK_ENTITY, typeId), blocks);
    }

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(Identifier id, BlockEntityFactory<T> blockEntity, Type<?> type, RegistrySupplier<Block>... blocks) {
        return register(BLOCK_ENTITY_TYPES, id, () -> {
            Block[] values = new Block[blocks.length];
            for(int i = 0; i < blocks.length; i++) values[i] = blocks[i].get();
            return BlockEntityType.Builder.create(blockEntity, values).build(type);
        });
    }

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, BlockEntityFactory<T> blockEntity, RegistrySupplier<? extends Block>... blocks) {
        return register(name, blockEntity, (Type<?>)null, blocks);
    }

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, BlockEntityFactory<T> blockEntity, String typeId, RegistrySupplier<? extends Block>... blocks) {
        return register(name, blockEntity, Util.getChoiceType(TypeReferences.BLOCK_ENTITY, typeId), blocks);
    }

    public static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(String name, BlockEntityFactory<T> blockEntity, Type<?> type, RegistrySupplier<? extends Block>... blocks) {
        return register(BLOCK_ENTITY_TYPES, name, () -> {
            Block[] values = new Block[blocks.length];
            for(int i = 0; i < blocks.length; i++) values[i] = blocks[i].get();
            return BlockEntityType.Builder.create(blockEntity, values).build(type);
        });
    }

}
