package abeshutt.staracademy.mixin;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Trinket.class)
public class MixinTrinket {

    @Inject(method = "onBreak", at = @At("HEAD"), cancellable = true)
    void onBreak(ItemStack stack, SlotReference slot, LivingEntity entity, CallbackInfo ci) {
        if(Platform.getEnvironment() == Env.SERVER) {
            ci.cancel();
        }
    }

}
