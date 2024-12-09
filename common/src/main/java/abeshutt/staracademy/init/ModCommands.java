package abeshutt.staracademy.init;

import com.mojang.brigadier.CommandDispatcher;
import abeshutt.staracademy.command.Command;
import abeshutt.staracademy.command.ReloadCommand;
import abeshutt.staracademy.command.StarterCommand;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.function.Supplier;

public class ModCommands {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        new ReloadCommand().register(dispatcher, access, environment);
        new StarterCommand().register(dispatcher, access, environment);
    }

    private static <T extends Command> T register(Supplier<T> supplier, CommandDispatcher<ServerCommandSource> dispatcher,
                                                  CommandRegistryAccess access, CommandManager.RegistrationEnvironment environment) {
        T command = supplier.get();
        command.register(dispatcher, access, environment);
        return command;
    }

}
