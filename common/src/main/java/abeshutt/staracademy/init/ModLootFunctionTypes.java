package abeshutt.staracademy.init;

import abeshutt.staracademy.world.loot.SetMoneyBagLootFunction;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class ModLootFunctionTypes extends ModRegistries {

    public static RegistrySupplier<LootFunctionType> SET_MONEY_BAG;

    public static void register() {
        SET_MONEY_BAG = register("set_money_bag", () -> new LootFunctionType(new SetMoneyBagLootFunction.Serializer()));
    }

    public static <V extends LootFunctionType> RegistrySupplier<V> register(Identifier id, Supplier<V> item) {
        return register(LOOT_FUNCTION_TYPES, id, item);
    }

    public static <V extends LootFunctionType> RegistrySupplier<V> register(String name, Supplier<V> item) {
        return register(LOOT_FUNCTION_TYPES, name, item);
    }

}

