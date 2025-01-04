package abeshutt.staracademy.init;

import abeshutt.staracademy.config.*;
import dev.architectury.event.events.common.LifecycleEvent;

import java.util.ArrayList;
import java.util.List;

public class ModConfigs extends ModRegistries {

    public static List<Runnable> POST_LOAD = new ArrayList<>();

    public static TileGroupsConfig TILE_GROUPS;
    public static EntityGroupsConfig ENTITY_GROUPS;
    public static ItemGroupsConfig ITEM_GROUPS;

    public static StarterRaffleConfig STARTER_RAFFLE;
    public static PokemonSpawnConfig POKEMON_SPAWN;
    public static ECCobblemonConfig ENHANCED_CELESTIALS_COBBLEMON_CONFIG;
    public static SafariConfig SAFARI;
    public static WardrobeConfig WARDROBE;

    public static void register(boolean initialization) {
        STARTER_RAFFLE = new StarterRaffleConfig().read();
        POKEMON_SPAWN = new PokemonSpawnConfig().read();
        ENHANCED_CELESTIALS_COBBLEMON_CONFIG = new ECCobblemonConfig().read();
        SAFARI = new SafariConfig().read();
        WARDROBE = new WardrobeConfig().read();

        if(!initialization) {
            POST_LOAD.forEach(Runnable::run);
            POST_LOAD.clear();
        } else {
            LifecycleEvent.SETUP.register(() -> {
                POST_LOAD.forEach(Runnable::run);
                POST_LOAD.clear();
            });
        }
    }

}
