package abeshutt.staracademy.screen;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.init.ModItems;
import abeshutt.staracademy.init.ModNetwork;
import abeshutt.staracademy.net.UpdateOutfitC2SPacket;
import abeshutt.staracademy.screen.helper.Texture9SliceRegion;
import abeshutt.staracademy.world.data.WardrobeData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ScrollableWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Set;

public class WardrobeOutfitsWidget extends ScrollableWidget {

    private static final Identifier TEXTURE = StarAcademyMod.id("textures/gui/wardrobe.png");

    protected static Texture9SliceRegion OUTFIT_BG = new Texture9SliceRegion(19, 0, 18, 19, 256, 256);
    protected static Texture9SliceRegion OUTFIT_BG_HOVER = new Texture9SliceRegion(38, 0, 19, 19, 256, 256);

    protected int entryHeight = 25;
    protected int gap = 5;

    public WardrobeOutfitsWidget(int x, int y, int w, int h, Text text) {
        super(x, y, w, h, text);
    }

    @Override
    public boolean canFocus(FocusSource source) {
        return false;
    }

    protected WardrobeData.Entry getWardrobe() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        return WardrobeData.CLIENT.getOrCreate(player.getUuid());
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    @Override
    protected int getContentsHeight() {
        WardrobeData.Entry wardrobe = getWardrobe();
        Set<String> unlocked = wardrobe.getUnlocked();
        return unlocked.size() * this.entryHeight + unlocked.size() * this.gap;
    }

    @Override
    protected double getDeltaYPerScroll() {
        return 9;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean clicked = super.mouseClicked(mouseX, mouseY, button);

        int pointerX = (int) mouseX;
        int pointerY = (int) (mouseY + getScrollY());

        WardrobeData.Entry wardrobe = getWardrobe();
        Set<String> unlocked = wardrobe.getUnlocked();
        Set<String> equipped = wardrobe.getEquipped();

        int i = 0;
        for (String outfitId : unlocked) {
            int x = getX() + this.gap;
            int y = getY() + (i + 1) * this.gap + i * this.entryHeight;
            int w = width - 2 * gap + 2;
            int h = entryHeight + gap;

            if ((x <= pointerX && pointerX <= x + w)
                    && (y <= pointerY && pointerY <= y + h)) {
                ModNetwork.CHANNEL.sendToServer(new UpdateOutfitC2SPacket(outfitId, !equipped.contains(outfitId)));
                break;
            }

            i++;
        }

        return clicked;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void renderContents(DrawContext context, int mouseX, int mouseY, float delta) {
        int pointerX = mouseX;
        int pointerY = (int) (mouseY + getScrollY());

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        WardrobeData.Entry wardrobe = getWardrobe();
        Set<String> unlocked = wardrobe.getUnlocked();
        Set<String> equipped = wardrobe.getEquipped();

        int i = 0;
        for (String outfitId : unlocked) {
            int x = getX() + this.gap;
            int y = getY() + (i + 1) * this.gap + i * this.entryHeight;
            int w = width - 2 * gap + 2;
            int h = entryHeight + gap;

            Texture9SliceRegion outfitBg = (x <= pointerX && pointerX <= x + w)
                    && (y <= pointerY && pointerY <= y + h) ? OUTFIT_BG_HOVER : OUTFIT_BG;

            outfitBg.draw(context, TEXTURE, x, y, w, h);

            ItemStack itemStack = new ItemStack(ModItems.OUTFIT.get());
            NbtCompound nbt = itemStack.getOrCreateNbt();

            NbtCompound entryNbt = new NbtCompound();
            entryNbt.putString("type", "value");
            entryNbt.putString("id", outfitId);
            nbt.put("entry", entryNbt);

            context.drawItem(itemStack, x + 6, y + 7);

            context.getMatrices().push();
            float scale = 0.75f;
            context.getMatrices().translate(x + 24, y + 11, 0);
            context.getMatrices().scale(scale, scale, scale);
            context.drawText(textRenderer,
                    Text.translatable("item.academy.outfit." + outfitId),
                    0, 0, 0xFF_FFFFFF, false);
            context.getMatrices().pop();

            if (equipped.contains(outfitId)) {
                context.drawTexture(TEXTURE, x + width - 26, y + 11,
                        0, 39, 7, 6);
            }

            i++;
        }
    }

}
