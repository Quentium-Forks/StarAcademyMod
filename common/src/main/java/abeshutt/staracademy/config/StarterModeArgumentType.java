package abeshutt.staracademy.config;

import abeshutt.staracademy.world.data.StarterMode;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.server.command.ServerCommandSource;

public class StarterModeArgumentType extends EnumArgumentType<StarterMode> {

    private StarterModeArgumentType() {
        super(StarterMode.CODEC, StarterMode::values);
    }

    public static EnumArgumentType<StarterMode> starterMode() {
        return new StarterModeArgumentType();
    }

    public static StarterMode getStarterMode(CommandContext<ServerCommandSource> context, String id) {
        return context.getArgument(id, StarterMode.class);
    }

}

