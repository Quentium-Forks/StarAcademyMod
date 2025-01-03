package abeshutt.staracademy.fabric;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.modsupport.enhancedcelestials.EnhancedCelestialsCompat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class StarAcademyFabricMod implements ModInitializer {

    @Override
    public void onInitialize() {
        StarAcademyMod.init();
    }

}
