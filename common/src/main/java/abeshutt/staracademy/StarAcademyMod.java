package abeshutt.staracademy;

import abeshutt.staracademy.init.ModRegistries;
import com.cobblemon.mod.common.Cobblemon;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class StarAcademyMod {

    public static final String ID = "academy";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static void init() {
        Cobblemon.INSTANCE.setStarterHandler(new GameStarterHandler());
        ModRegistries.register();
    }

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

}
