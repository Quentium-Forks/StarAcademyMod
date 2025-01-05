package abeshutt.staracademy.entity;

import abeshutt.staracademy.StarAcademyMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PartnerNPCEntity extends HumanEntity {

    private final Map<UUID, PartnerNPCConversation> conversations;

    public PartnerNPCEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
        this.conversations = new HashMap<>();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, Ingredient.ofItems(Items.WHEAT), false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
    }

    @Override
    public Identifier getSkinTexture() {
        return StarAcademyMod.id("textures/entity/partner_npc.png");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtCompound conversations = new NbtCompound();

        this.conversations.forEach((uuid, conversation) -> {
            conversation.writeNbt().ifPresent(tag -> conversations.put(uuid.toString(), tag));
        });
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        this.conversations.clear();
        NbtCompound conversations = nbt.getCompound("conversations");

        for(String key : conversations.getKeys()) {
            UUID uuid = UUID.fromString(key);
            PartnerNPCConversation conversation = new PartnerNPCConversation(uuid);
            conversation.readNbt(nbt.getCompound(key));
            this.conversations.put(uuid, conversation);
        }
    }

}
