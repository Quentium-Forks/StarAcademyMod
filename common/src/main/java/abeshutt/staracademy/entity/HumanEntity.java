package abeshutt.staracademy.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class HumanEntity extends PathAwareEntity implements IDefaultedAttributes {

    protected Vec3d lastVelocity;

    protected HumanEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public DefaultAttributeContainer getDefaultAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0D)
                .build();
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    public abstract Identifier getSkinTexture();

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public void tick() {
        this.lastVelocity = this.getVelocity();
        super.tick();
    }

    public Vec3d lerpVelocity(float tickDelta) {
        return this.lastVelocity.lerp(this.getVelocity(), (double)tickDelta);
    }

}
