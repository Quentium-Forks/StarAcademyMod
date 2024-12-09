package abeshutt.staracademy.init;

import dev.architectury.registry.registries.RegistrySupplier;
import abeshutt.staracademy.item.StarBadgeItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModItems extends ModRegistries {

    public static RegistrySupplier<Item> STAR_BADGE;

    public static void register() {
        STAR_BADGE = register("star_badge", StarBadgeItem::new);
    }

    public static <V extends Item> RegistrySupplier<V> register(Identifier id, Supplier<V> item) {
        return register(ITEMS, id, item);
    }

    public static <V extends Item> RegistrySupplier<V> register(String name, Supplier<V> item) {
        return register(ITEMS, name, item);
    }

}

