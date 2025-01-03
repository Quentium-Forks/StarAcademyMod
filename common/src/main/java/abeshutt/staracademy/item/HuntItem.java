package abeshutt.staracademy.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class HuntItem extends Item {

    public HuntItem() {
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

}
