package abeshutt.staracademy.entity;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.init.ModWorldData;
import abeshutt.staracademy.world.data.SafariData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.UUID;

public class SafariNPCEntity extends HumanEntity {

    public SafariNPCEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(2, new LookAroundGoal(this));
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        SafariData data = ModWorldData.SAFARI.getGlobal(player.getWorld());

        if(data.isPaused()) {
            player.sendMessage(Text.empty()
                    .append(Text.literal("Sorry, Trainer, but the Safari is off-limits at the moment. Please come back later!")));
        } else {
            player.sendMessage(Text.empty()
                    .append(Text.literal("Hello, Trainer! The Safari is open for exploration. Step right through and enjoy the hunt!")));
        }

        return super.interactMob(player, hand);
    }

    @Override
    public Identifier getSkinTexture() {
        return StarAcademyMod.id("textures/entity/safari_npc.png");
    }

}

