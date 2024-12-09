package abeshutt.staracademy.entity;

import abeshutt.staracademy.init.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class StarBadgeEntity extends ThrownItemEntity {

    public StarBadgeEntity(EntityType<StarBadgeEntity> type, World world) {
        super(type, world);
    }

    public StarBadgeEntity(World world, LivingEntity thrower, ItemStack stack) {
        super(ModEntities.STAR_BADGE.get(), thrower, world);
        this.getDataTracker().set(ITEM, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.AIR;
    }

    protected void onEntityHit(EntityHitResult result) {
        super.onEntityHit(result);

        if(result.getEntity() instanceof LivingEntity entity) {
            entity.takeKnockback(1.0D, -this.getVelocity().x, -this.getVelocity().z);
            this.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                    SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
    }

    protected void onCollision(HitResult result) {
        super.onCollision(result);
        if(!this.getWorld().isClient) {
            Vec3d velocity = this.getVelocity().multiply(0.18D);
            velocity = velocity.add(0.0D, -velocity.getY() + 0.4D, 0.0D);

            ItemEntity item = new ItemEntity(this.getWorld(), result.getPos().x, result.getPos().y, result.getPos().z,
                    this.getDataTracker().get(ITEM).copy(), velocity.x, velocity.y, velocity.z);
            this.getWorld().spawnEntity(item);
            this.discard();
        }
    }

}
