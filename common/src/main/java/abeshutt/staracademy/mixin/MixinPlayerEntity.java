package abeshutt.staracademy.mixin;

import com.cobblemon.mod.common.util.PlayerExtensionsKt;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerExtensionsKt.class)
public class MixinPlayerEntity {

    @Inject(method = "giveOrDropItemStack", at = @At("HEAD"))
    private static void giveOrDropItemStack(PlayerEntity player, ItemStack stack, boolean playSound, CallbackInfo ci) {

    }

}
