package abeshutt.staracademy.init;

import dev.architectury.event.events.client.ClientLifecycleEvent;

public class ModScreens extends ModRegistries {

    public static void register() {
        ClientLifecycleEvent.CLIENT_SETUP.register(minecraft -> {
            //MenuRegistry.registerScreenFactory(ModScreenHandlers.DAILY_SHOP.get(), DailyShopScreen::new);
        });

        /*
        Layers.add(

                StarBadgeLayerContainer::new,
                new PurseLayerElement<>((instance, component) -> {
                    instance.aggressivePositioning = true;
                    ((LayerInstanceAccessor) instance).numismatic$getLayoutUpdaters().add(() -> {
                        if (instance.screen.isInventoryTabSelected()) {
                            component.positioning(Positioning.absolute(
                                    ((HandledScreenAccessor) instance.screen).owo$getRootX() + 38 + NumismaticOverhaul.CONFIG.purseOffsets.creativeX() ,
                                    ((HandledScreenAccessor) instance.screen).owo$getRootY() + 4 + NumismaticOverhaul.CONFIG.purseOffsets.creativeY()
                            ));
                        } else {
                            component.positioning(Positioning.absolute(-50, -50));
                        }
                    });
                }),
                CreativeInventoryScreen.class
        );

        Layers.add(
                StarBadgeLayerContainer::new,
                new PurseLayerElement<>((instance, component) -> {
                    instance.aggressivePositioning = true;
                    instance.alignComponentToHandledScreenCoordinates(
                            component,
                            160 + NumismaticOverhaul.CONFIG.purseOffsets.survivalX(),
                            5 + NumismaticOverhaul.CONFIG.purseOffsets.survivalY()
                    );
                }),
                InventoryScreen.class
        );*/
    }

}
