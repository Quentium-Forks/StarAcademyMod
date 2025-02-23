package abeshutt.staracademy.mixin.cobblemon;

import abeshutt.staracademy.StarAcademyMod;
import com.cobblemon.mod.common.CobblemonItems;
import com.cobblemon.mod.common.item.PokeBallItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PokeBallItem.class)
public abstract class MixinPokeBallItem {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void throwPokeBall(World world, PlayerEntity player, Hand usedHand, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        if((Object)this != CobblemonItems.SAFARI_BALL && world.getRegistryKey() == StarAcademyMod.SAFARI) {
            ci.setReturnValue(TypedActionResult.success(player.getStackInHand(usedHand), world.isClient));
        }
    }

}
