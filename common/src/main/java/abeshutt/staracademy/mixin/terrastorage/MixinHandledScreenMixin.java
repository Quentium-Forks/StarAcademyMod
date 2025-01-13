package abeshutt.staracademy.mixin.terrastorage;

import abeshutt.staracademy.compat.terrastorage.TerraStorageCompat;
import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HandledScreen.class, priority = 15000)
public class MixinHandledScreenMixin {

    @TargetHandler(mixin = "me.timvinci.mixin.client.HandledScreenMixin", name = "onInit")
    @Inject(method = "@MixinSquared:Handler", at = @At("HEAD"), cancellable = true)
    public void onInit(CallbackInfo ci) {
        System.out.println("==================== MIXIN THE MIXIN ====================");

        if(TerraStorageCompat.shouldBlock(this)) {
            ci.cancel();
        }
    }

}
