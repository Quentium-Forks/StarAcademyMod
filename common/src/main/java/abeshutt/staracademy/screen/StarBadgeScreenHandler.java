package abeshutt.staracademy.screen;

import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;

import java.util.ArrayList;
import java.util.List;

public class StarBadgeScreenHandler {

    private final ScreenHandler parent;
    private final Inventory inventory;
    private final List<StarBadgeSlot> slots;
    private boolean enabled;

    public StarBadgeScreenHandler(ScreenHandler parent, Inventory inventory) {
        this.parent = parent;
        this.inventory = inventory;
        this.slots = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            StarBadgeSlot slot = new StarBadgeSlot(inventory, i, i * 18 - 9 + 8, -34 + 8);
            this.parent.addSlot(slot);
            this.slots.add(slot);
        }

        this.setEnabled(false);
    }

    public List<StarBadgeSlot> getSlots() {
        return this.slots;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        for(StarBadgeSlot slot : this.slots) {
            slot.setEnabled(this.enabled);
        }
    }

}
