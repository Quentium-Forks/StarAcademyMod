package abeshutt.staracademy;

import abeshutt.staracademy.init.ModRegistries;
import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.spawning.SpawnCause;
import kotlin.Unit;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class StarAcademyMod {

    public static final String ID = "academy";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static void init() {
        Cobblemon.INSTANCE.setStarterHandler(new GameStarterHandler());
        ModRegistries.register();

        CobblemonEvents.POKEMON_ENTITY_SPAWN.subscribe(Priority.NORMAL, event -> {
            MinecraftServer server = event.getEntity().getWorld().getServer();
            if(server == null) return Unit.INSTANCE;

            if(event.getEntity().getPokemon().isLegendary() && event.getEntity().getPokemon().getShiny()) {
                for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    player.sendMessage(Text.empty()
                        .append(Text.literal("A ").formatted(Formatting.BOLD))
                        .append(event.getEntity().getDisplayName().copy().formatted(Formatting.BOLD))
                        .append(Text.literal(" has spawned near someone!").formatted(Formatting.BOLD)));
                }
            }

            return Unit.INSTANCE;
        });
    }

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

}
