package abeshutt.staracademy.command;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.init.ModWorldData;
import abeshutt.staracademy.world.data.PokemonStarterData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class StarterCommand extends Command {

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal(StarAcademyMod.ID)
                .then(literal("starter_raffle")
                    .requires(source -> source.hasPermissionLevel(4))
                    .then(literal("pause")
                        .executes(this::onPause))
                    .then(literal("resume")
                        .executes(this::onResume))
                    .then(literal("set_interval")
                        .then(argument("interval", TimeArgumentType.time())
                            .executes(this::onSetInterval)))));
    }

    private int onPause(CommandContext<ServerCommandSource> context) {
        MinecraftServer server = context.getSource().getServer();
        PokemonStarterData data = ModWorldData.POKEMON_STARTER.getGlobal(server);

        if(data.setPaused(true)) {
            context.getSource().sendFeedback(() -> Text.empty()
                .append(Text.literal("The starter raffle is now ").formatted(Formatting.GRAY))
                .append(Text.literal("paused").formatted(Formatting.RED))
                .append(Text.literal(".").formatted(Formatting.GRAY)), true);
        } else {
            context.getSource().sendFeedback(() -> Text.empty()
                .append(Text.literal("The starter raffle is already ").formatted(Formatting.GRAY))
                .append(Text.literal("paused").formatted(Formatting.RED))
                .append(Text.literal(".").formatted(Formatting.GRAY)), true);
        }

        return 0;
    }

    private int onResume(CommandContext<ServerCommandSource> context) {
        MinecraftServer server = context.getSource().getServer();
        PokemonStarterData data = ModWorldData.POKEMON_STARTER.getGlobal(server);

        if(data.setPaused(false)) {
            context.getSource().sendFeedback(() -> Text.empty()
                .append(Text.literal("The starter raffle is now ").formatted(Formatting.GRAY))
                .append(Text.literal("unpaused").formatted(Formatting.GREEN))
                .append(Text.literal(".").formatted(Formatting.GRAY)), true);
        } else {
            context.getSource().sendFeedback(() -> Text.empty()
                .append(Text.literal("The starter raffle is already ").formatted(Formatting.GRAY))
                .append(Text.literal("unpaused").formatted(Formatting.GREEN))
                .append(Text.literal(".").formatted(Formatting.GRAY)), true);
        }

        return 0;
    }

    private int onSetInterval(CommandContext<ServerCommandSource> context) {
        long interval = context.getArgument("interval", Integer.class);
        MinecraftServer server = context.getSource().getServer();
        PokemonStarterData data = ModWorldData.POKEMON_STARTER.getGlobal(server);
        data.setTimeInterval(interval);
        context.getSource().sendFeedback(() -> Text.empty()
                .append(Text.literal("The starter raffle interval has been updated to ").formatted(Formatting.GRAY))
                .append(Text.literal(String.valueOf(interval)).formatted(Formatting.AQUA))
                .append(Text.literal(" ticks.").formatted(Formatting.GRAY)), true);
        return 0;
    }

}
