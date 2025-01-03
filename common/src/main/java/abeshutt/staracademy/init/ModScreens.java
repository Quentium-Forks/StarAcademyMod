package abeshutt.staracademy.init;

import dev.architectury.event.events.client.ClientLifecycleEvent;

public class ModScreens extends ModRegistries {

    public static void register() {
        ClientLifecycleEvent.CLIENT_SETUP.register(minecraft -> {
            //MenuRegistry.registerScreenFactory(ModScreenHandlers.DAILY_SHOP.get(), DailyShopScreen::new);
        });
    }

}
