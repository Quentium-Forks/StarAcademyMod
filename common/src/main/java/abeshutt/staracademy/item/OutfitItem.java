package abeshutt.staracademy.item;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.data.adapter.Adapters;
import abeshutt.staracademy.item.renderer.OutfitItemRenderer;
import abeshutt.staracademy.item.renderer.SpecialItemRenderer;
import abeshutt.staracademy.util.ISpecialItemModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Optional;
import java.util.function.Consumer;

public class OutfitItem extends Item implements ISpecialItemModel {

    public OutfitItem() {
        super(new Settings());
    }

    public static Optional<OutfitEntry> getEntry(ItemStack stack) {
        if(stack.getNbt() != null) {
            return Adapters.OUTFIT_ENTRY.readNbt(stack.getNbt().getCompound("entry"));
        }

        return Optional.empty();
    }

    public static void setEntry(ItemStack stack, OutfitEntry entry) {
        Adapters.OUTFIT_ENTRY.writeNbt(entry).ifPresent(tag -> {
            stack.getOrCreateNbt().put("entry", tag);
        });
    }

    @Override
    public void loadModels(Consumer<ModelIdentifier> consumer) {
        //consumer.accept(StarAcademyMod.mid(""));
    }

    @Override
    public SpecialItemRenderer getRenderer() {
        return OutfitItemRenderer.INSTANCE;
    }

}
