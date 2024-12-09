package abeshutt.staracademy.mixin.mythsandlegends;

import com.github.d0ctorleon.mythsandlegends.events.PlayerEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEvents.class)
public class MixinPlayerEvents {

    @Redirect(method = "onPlayerJoin", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;sendMessageToClient(Lnet/minecraft/text/Text;Z)V"))
    private void onPlayerJoin(ServerPlayerEntity instance, Text message, boolean overlay) {

    }

}
