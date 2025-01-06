package abeshutt.staracademy.command;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.init.ModWorldData;
import abeshutt.staracademy.world.data.SafariData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static net.minecraft.server.command.CommandManager.literal;

public class SafariCommand extends Command {

    @Override
    public void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal(StarAcademyMod.ID)
                .then(literal("safari")
                    .requires(source -> source.hasPermissionLevel(4))
                    .then(literal("pause")
                        .executes(this::onPause))
                    .then(literal("unpause")
                        .executes(this::onUnpause))));
    }

    private int onPause(CommandContext<ServerCommandSource> context) {
        SafariData data = ModWorldData.SAFARI.getGlobal(context.getSource().getServer());

        if(data.setPaused(true)) {
            context.getSource().sendFeedback(() -> {
                return Text.empty().append(Text.literal("The Safari is now ").formatted(Formatting.GRAY))
                        .append(Text.literal("paused").formatted(Formatting.RED)
                        .append(Text.literal(".").formatted(Formatting.GRAY)));
            }, true);
        } else {
            context.getSource().sendFeedback(() -> {
                return Text.empty().append(Text.literal("The Safari is already ").formatted(Formatting.GRAY))
                        .append(Text.literal("paused").formatted(Formatting.RED)
                        .append(Text.literal(".").formatted(Formatting.GRAY)));
            }, true);
        }

        return 0;
    }

    private int onUnpause(CommandContext<ServerCommandSource> context) {
        SafariData data = ModWorldData.SAFARI.getGlobal(context.getSource().getServer());

        if(data.setPaused(false)) {
            context.getSource().sendFeedback(() -> {
                return Text.empty().append(Text.literal("The Safari is now ").formatted(Formatting.GRAY))
                        .append(Text.literal("unpaused").formatted(Formatting.GREEN)
                        .append(Text.literal(".").formatted(Formatting.GRAY)));
            }, true);
        } else {
            context.getSource().sendFeedback(() -> {
                return Text.empty().append(Text.literal("The Safari is already ").formatted(Formatting.GRAY))
                        .append(Text.literal("unpaused").formatted(Formatting.GREEN)
                        .append(Text.literal(".").formatted(Formatting.GRAY)));
            }, true);
        }

        return 0;
    }

}
