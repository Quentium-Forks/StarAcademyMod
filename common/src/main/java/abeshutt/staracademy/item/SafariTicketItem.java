package abeshutt.staracademy.item;

import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.init.ModWorldData;
import abeshutt.staracademy.item.renderer.NullSpecialItemRenderer;
import abeshutt.staracademy.item.renderer.SpecialItemRenderer;
import abeshutt.staracademy.util.ISpecialItemModel;
import abeshutt.staracademy.world.data.SafariData;
import abeshutt.staracademy.world.random.JavaRandom;
import abeshutt.staracademy.world.roll.IntRoll;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class SafariTicketItem extends Item implements ISpecialItemModel {

    public SafariTicketItem() {
        super(new Settings());
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(user.getWorld().isClient() || user.getServer() == null || world.getServer() == null) {
            return TypedActionResult.success(stack);
        }

        SafariData data = ModWorldData.SAFARI.getGlobal(world.getServer());

        SafariTicketItem.getRoll(stack).ifPresent(roll -> {
            int time = roll.get(JavaRandom.ofNanoTime());
            SafariData.Entry entry = data.getOrCreate(user.getUuid());
            entry.setTimeLeft(entry.getTimeLeft() + time);
            user.sendMessage(SafariTicketItem.getTimeMessage(user, time, true));

            if(!user.isCreative()) {
                stack.decrement(1);
            }
        });

        return TypedActionResult.success(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        SafariTicketItem.getRoll(stack).ifPresent(roll -> {
            int min = IntRoll.getMin(roll);
            int max = IntRoll.getMax(roll);

            if(min == max) {
                tooltip.add(Text.literal("Adds " + formatTimeString(min) + " to your Safari timer.")
                        .formatted(Formatting.GRAY, Formatting.ITALIC));
            } else {
                tooltip.add(Text.literal("Adds between " + formatTimeString(min) + " and "
                        + formatTimeString(max) + " to your Safari timer.")
                        .formatted(Formatting.GRAY, Formatting.ITALIC));
            }
        });
    }

    public static Optional<IntRoll> getRoll(ItemStack stack) {
        if(stack.getNbt() == null) {
            return Optional.empty();
        }

        return Adapters.INT_ROLL.readNbt(stack.getNbt().get("roll"));
    }

    public static void setRoll(ItemStack stack, IntRoll roll) {
        Adapters.INT_ROLL.writeNbt(roll).ifPresent(tag -> {
            stack.getOrCreateNbt().put("roll", tag);
        });
    }

    @Override
    public void loadModels(Consumer<ModelIdentifier> consumer) {

    }

    @Override
    public SpecialItemRenderer getRenderer() {
        return NullSpecialItemRenderer.INSTANCE;
    }

    public static Text getTimeMessage(PlayerEntity target, long ticks, boolean personal) {
        if(ticks >= 0) {
            return Text.empty()
                .append(Text.literal("Added ").formatted(Formatting.GREEN))
                .append(Text.literal(formatTimeString(ticks)).formatted(Formatting.WHITE))
                .append(Text.literal(" to ").formatted(Formatting.GRAY))
                .append(personal ? Text.literal("your").formatted(Formatting.GRAY) : target.getName())
                .append(Text.literal(personal ? " Safari." : "'s Safari timer.").formatted(Formatting.GRAY));
        } else {
            return Text.empty()
                .append(Text.literal("Removed ").formatted(Formatting.RED))
                .append(Text.literal(formatTimeString(-ticks)).formatted(Formatting.WHITE))
                .append(Text.literal(" from ").formatted(Formatting.GRAY))
                .append(personal ? Text.literal("your").formatted(Formatting.GRAY) : target.getName())
                .append(Text.literal(personal ? " Safari." : "'s Safari timer.").formatted(Formatting.GRAY));
        }
    }

    protected static String formatTimeString(long remainingTicks) {
        long seconds = (remainingTicks / 20) % 60;
        long minutes = ((remainingTicks / 20) / 60) % 60;
        long hours = ((remainingTicks / 20) / 60) / 60;
        return hours > 0
                ? String.format("%02d:%02d:%02d", hours, minutes, seconds)
                : String.format("%02d:%02d", minutes, seconds);
    }

}
