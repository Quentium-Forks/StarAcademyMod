package abeshutt.staracademy.screen;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.init.ModItems;
import abeshutt.staracademy.init.ModNetwork;
import abeshutt.staracademy.init.ModOutfits;
import abeshutt.staracademy.net.UpdateOutfitC2SPacket;
import abeshutt.staracademy.outfit.core.OutfitPiece;
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
import net.minecraft.util.math.MathHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WardrobeOutfitsWidget extends ScrollableWidget {

    private static final Identifier TEXTURE = StarAcademyMod.id("textures/gui/wardrobe.png");

    protected static Texture9SliceRegion OUTFIT_BG = new Texture9SliceRegion(19, 0, 18, 19, 256, 256);
    protected static Texture9SliceRegion OUTFIT_BG_HOVER = new Texture9SliceRegion(38, 0, 19, 19, 256, 256);

    protected int entryHeight = 25;
    protected int gap = 5;

    protected Map<String, ItemStack> outfitStackCache = new HashMap<>();
    protected List<String> unlockedOutfits;

    public WardrobeOutfitsWidget(int x, int y, int w, int h, Text text) {
        super(x, y, w, h, text);

        WardrobeData.Entry wardrobe = getWardrobe();
        Set<String> unlocked = wardrobe.getUnlocked();
        this.unlockedOutfits = unlocked.stream().sorted((id1, id2) -> {
            OutfitPiece outfit1 = ModOutfits.REGISTRY.get(id1);
            OutfitPiece outfit2 = ModOutfits.REGISTRY.get(id2);
            return Integer.compare(outfit1.getOrder(), outfit2.getOrder());
        }).toList();
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
        Set<String> equipped = wardrobe.getEquipped();

        int i = 0;
        for (String outfitId : this.unlockedOutfits) {
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
    protected void renderOverlay(DrawContext context) {
        if (this.overflows()) {
            this.drawScrollbar(context);
        }
    }

    private void drawScrollbar(DrawContext context) {
//        int i = this.getScrollbarThumbHeight();
        int contentsHeight = this.getContentsHeight() + 4;
        int i = MathHelper.clamp((int) ((float) (this.height * this.height) / (float) contentsHeight), 32, this.height);
        int j = this.getX() + this.width;
        int k = this.getX() + this.width + 8;
        int l = Math.max(this.getY(), (int) this.getScrollY() * (this.height - i) / this.getMaxScrollY() + this.getY());
        int m = l + i;
        context.fill(j, l, k, m, -8355712);
        context.fill(j, l, k - 1, m - 1, -4144960);
    }

    @Override
    protected void renderContents(DrawContext context, int mouseX, int mouseY, float delta) {
        int pointerX = mouseX;
        int pointerY = (int) (mouseY + getScrollY());

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        WardrobeData.Entry wardrobe = getWardrobe();
        Set<String> equipped = wardrobe.getEquipped();

        int i = 0;
        for (String outfitId : this.unlockedOutfits) {
            int x = getX() + this.gap;
            int y = getY() + (i + 1) * this.gap + i * this.entryHeight;
            int w = width - 2 * gap + 2;
            int h = entryHeight + gap;

            Texture9SliceRegion outfitBg = (x <= pointerX && pointerX <= x + w)
                    && (y <= pointerY && pointerY <= y + h) ? OUTFIT_BG_HOVER : OUTFIT_BG;

            outfitBg.draw(context, TEXTURE, x, y, w, h);

            ItemStack outfitStack = outfitStackCache.computeIfAbsent(outfitId, id -> {
                ItemStack itemStack = new ItemStack(ModItems.OUTFIT.get());

                NbtCompound nbt = itemStack.getOrCreateNbt();
                NbtCompound entryNbt = new NbtCompound();
                entryNbt.putString("type", "value");
                entryNbt.putString("id", outfitId);
                nbt.put("entry", entryNbt);

                return itemStack;
            });

            context.drawItem(outfitStack, x + 6, y + 7);

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
