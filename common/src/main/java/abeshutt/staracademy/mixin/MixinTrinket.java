package abeshutt.staracademy.mixin;

import com.tiviacz.cloudboots.TrinketsCompat;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TrinketsCompat.GoldenFeatherTrinket .class)
public class MixinTrinket {

    @Redirect(method = "lambda$tick$0", at = @At(value = "HEAD"))
    void onBreak(ItemStack stack, SlotReference slot, LivingEntity entity, CallbackInfo ci) {
        entity.playEquipmentBreakEffects(stack);
    }

}
