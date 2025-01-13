package abeshutt.staracademy.init;

import abeshutt.staracademy.item.*;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModItems extends ModRegistries {

    public static RegistrySupplier<Item> STAR_BADGE;
    public static RegistrySupplier<HuntItem> HUNT;
    public static RegistrySupplier<DuelingGloveItem> DUELING_GLOVE;
    public static RegistrySupplier<OutfitItem> OUTFIT;
    public static RegistrySupplier<SafariTicketItem> SAFARI_TICKET;

    public static void register() {
        STAR_BADGE = register("star_badge", StarBadgeItem::new);
        HUNT = register("hunt", HuntItem::new);
        DUELING_GLOVE = register("dueling_glove", DuelingGloveItem::new);
        OUTFIT = register("outfit", OutfitItem::new);
        SAFARI_TICKET = register("safari_ticket", SafariTicketItem::new);
    }

    public static <V extends Item> RegistrySupplier<V> register(Identifier id, Supplier<V> item) {
        return register(ITEMS, id, item);
    }

    public static <V extends Item> RegistrySupplier<V> register(String name, Supplier<V> item) {
        return register(ITEMS, name, item);
    }

}

