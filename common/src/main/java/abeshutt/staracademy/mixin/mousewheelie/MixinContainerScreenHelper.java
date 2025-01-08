package abeshutt.staracademy.mixin.mousewheelie;

import de.siphalor.mousewheelie.client.inventory.ContainerScreenHelper;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Iterator;

@Mixin(ContainerScreenHelper.class)
public class MixinContainerScreenHelper {

    @Inject(method = "restockAllOfAKind(Ljava/util/Iterator;I)V", at = @At("HEAD"), cancellable = true)
    private void restockAllOfAKind(Iterator<Slot> targetSlots, int complementaryScope, CallbackInfo ci) {
        ci.cancel();
    }

}
