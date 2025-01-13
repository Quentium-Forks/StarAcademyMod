package abeshutt.staracademy.item;

import abeshutt.staracademy.item.renderer.SpecialItemRenderer;
import abeshutt.staracademy.util.ISpecialItemModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class SafariTicketItem extends Item implements ISpecialItemModel {

    public SafariTicketItem() {
        super(new Settings().maxCount(1).fireproof());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.getWorld().isClient() || user.getServer() == null) {
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        user.getServer().getCommandManager().executeWithPrefix(user.getCommandSource(), "/hunt");
        return TypedActionResult.success(user.getStackInHand(hand));
    }

    @Override
    public void loadModels(Consumer<ModelIdentifier> consumer) {

    }

    @Override
    public SpecialItemRenderer getRenderer() {
        return null;
    }

}
