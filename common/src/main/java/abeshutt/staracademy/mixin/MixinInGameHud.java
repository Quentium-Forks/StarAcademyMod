package abeshutt.staracademy.mixin;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;
import abeshutt.staracademy.screen.StarterSelectionWidget;
import abeshutt.staracademy.util.ClientScheduler;
import abeshutt.staracademy.world.StarterEntry;
import abeshutt.staracademy.world.data.PokemonStarterData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    @Inject(method = "render", at = @At("TAIL"))
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(player == null) return;
        StarterEntry entry = PokemonStarterData.CLIENT.getEntries().get(player.getUuid());
        if(entry.getGranted() != null) return;

        Identifier pick = PokemonStarterData.CLIENT.getPick(player.getUuid());
        Species species = pick == null ? null : PokemonSpecies.INSTANCE.getByIdentifier(pick);

        StarterSelectionWidget widget = new StarterSelectionWidget(species, PokemonStarterData.CLIENT.getTimeLeft(),
                PokemonStarterData.CLIENT.isPaused());
        widget.render(context, 0, 0, ClientScheduler.getTick(tickDelta));
    }

}
