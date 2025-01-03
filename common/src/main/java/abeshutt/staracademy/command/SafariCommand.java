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
                .requires(source -> source.hasPermissionLevel(4))
                .then(literal("safari")
                    .then(literal("restart")
                        .executes(this::onRestart))));
    }

    private int onRestart(CommandContext<ServerCommandSource> context) {
        SafariData data = ModWorldData.SAFARI.getGlobal(context.getSource().getServer());
        data.onStart(context.getSource().getServer());
        context.getSource().sendFeedback(() -> Text.literal("Started safari event successfully.").formatted(Formatting.GRAY), true);
        return 0;
    }

}
