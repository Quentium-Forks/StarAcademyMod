package abeshutt.staracademy.init;

import abeshutt.staracademy.config.EntityGroupsConfig;
import abeshutt.staracademy.config.ItemGroupsConfig;
import abeshutt.staracademy.config.StarterRaffleConfig;
import abeshutt.staracademy.config.TileGroupsConfig;
import dev.architectury.event.events.common.LifecycleEvent;

import java.util.ArrayList;
import java.util.List;

public class ModConfigs extends ModRegistries {

    public static List<Runnable> POST_LOAD = new ArrayList<>();

    public static TileGroupsConfig TILE_GROUPS;
    public static EntityGroupsConfig ENTITY_GROUPS;
    public static ItemGroupsConfig ITEM_GROUPS;

    public static StarterRaffleConfig STARTER_RAFFLE;

    public static void register(boolean initialization) {
        STARTER_RAFFLE = new StarterRaffleConfig().read();

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
