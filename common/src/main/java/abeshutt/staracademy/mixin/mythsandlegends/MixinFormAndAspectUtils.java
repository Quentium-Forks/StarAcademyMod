package abeshutt.staracademy.mixin.mythsandlegends;

import com.github.d0ctorleon.mythsandlegends.utils.FormAndAspectUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FormAndAspectUtils.class)
public class MixinFormAndAspectUtils {

    @Redirect(method = "processPlayerData", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;sendMessageToClient(Lnet/minecraft/text/Text;Z)V"))
    private static void processPlayerData(ServerPlayerEntity instance, Text message, boolean overlay) {

    }

}
