package abeshutt.staracademy.fabric.mixin.terrastorage;

import abeshutt.staracademy.compat.terrastorage.TerraStorageCompat;
import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InventoryScreen.class, priority = 1500)
public class MixinInventoryScreenMixin {

    @TargetHandler(mixin = "me.timvinci.mixin.client.InventoryScreenMixin", name = "onInit")
    @Inject(method = "@MixinSquared:Handler", at = @At("HEAD"))
    public void onInit(CallbackInfo oci, CallbackInfo ci) {
        if(TerraStorageCompat.shouldBlock(this)) {
            oci.cancel();
        }
    }

}
