package abeshutt.staracademy.mixin;

import abeshutt.staracademy.entity.IDefaultedAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeRegistry;get(Lnet/minecraft/entity/EntityType;)Lnet/minecraft/entity/attribute/DefaultAttributeContainer;"))
    public DefaultAttributeContainer getAttributes(EntityType<? extends LivingEntity> type) {
        if(this instanceof IDefaultedAttributes entity) {
            return entity.getDefaultAttributes();
        }

        return DefaultAttributeRegistry.get(type);
    }

}
