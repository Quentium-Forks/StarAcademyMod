package abeshutt.staracademy.screen;

import abeshutt.staracademy.init.ModItems;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class StarBadgeSlot extends Slot {

    private boolean enabled;

    public StarBadgeSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.setEnabled(true);
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.isOf(ModItems.STAR_BADGE.get());
    }

}
