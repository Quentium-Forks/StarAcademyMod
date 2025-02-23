package abeshutt.staracademy.item;

import abeshutt.staracademy.config.SafariConfig;
import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.init.ModConfigs;
import abeshutt.staracademy.init.ModWorldData;
import abeshutt.staracademy.item.renderer.SafariTicketItemRenderer;
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
import net.minecraft.text.Style;
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
    public Text getName(ItemStack stack) {
        return SafariTicketItem.getEntry(stack, true).map(entry -> {
            return (Text)Text.literal(entry.getName()).setStyle(Style.EMPTY.withColor(entry.getColor()));
        }).orElseGet(() -> super.getName(stack));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if(user.getWorld().isClient() || user.getServer() == null || world.getServer() == null) {
            return TypedActionResult.success(stack);
        }

        SafariData data = ModWorldData.SAFARI.getGlobal(world.getServer());

        SafariTicketItem.getEntry(stack, false).ifPresent(value -> {
            int time = value.getTime().get(JavaRandom.ofNanoTime());
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

        SafariTicketItem.getEntry(stack, world.isClient()).ifPresent(entry -> {
            int min = IntRoll.getMin(entry.getTime());
            int max = IntRoll.getMax(entry.getTime());

            if(min == max) {
                tooltip.add(Text.literal("Adds " + formatTimeString(min) + " to your Safari timer.")
                        .formatted(Formatting.GRAY));
            } else {
                tooltip.add(Text.literal("Adds between " + formatTimeString(min) + " and "
                        + formatTimeString(max) + " to your Safari timer.")
                        .formatted(Formatting.GRAY));
            }
        });
    }

    public static Optional<SafariTicketEntry> getEntry(ItemStack stack, boolean client) {
        if(stack.getNbt() == null) {
            return Optional.empty();
        }

        return Adapters.UTF_8.readNbt(stack.getNbt().get("id")).flatMap(id -> {
            return client ? SafariConfig.CLIENT.getTicket(id) : ModConfigs.SAFARI.getTicket(id);
        });
    }

    @Override
    public void loadModels(Consumer<ModelIdentifier> consumer) {
        ModConfigs.SAFARI.getTickets().forEach((id, entry) -> {
            consumer.accept(entry.getModelId());
        });
    }

    @Override
    public SpecialItemRenderer getRenderer() {
        return SafariTicketItemRenderer.INSTANCE;
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
