package tfar.upgradestation;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

import java.text.DecimalFormat;

public class UpgradeStationMenuScreen extends AbstractContainerScreen<UpgradeStationMenu> {
    private static final ResourceLocation CRAFTING_TABLE_LOCATION = UpgradeStation.id("textures/gui/upgrade_station.png");

    public UpgradeStationMenuScreen(UpgradeStationMenu $$0, Inventory $$1, Component $$2) {
        super($$0, $$1, $$2);
        this.titleLabelX = 29;
        imageWidth = 175;
        imageHeight = 221;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBlurredBackground(pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    public static final DecimalFormat decimalFormat = new DecimalFormat("##.##");

    @Override
    protected void renderLabels(GuiGraphics matrices, int $$1, int $$2) {
        //super.renderLabels(matrices, $$1, $$2);

        if (menu.selectedRecipe != null && !menu.resultSlot.getItem(0).isEmpty()) {
            int cost = menu.selectedRecipe.value().getCost();
            if (cost > 0) {
                Component component = Component.literal("Cost: " + cost);
                int x = this.imageWidth - 86 - this.font.width(component) / 2;
                matrices.drawString(font, component, x, 113, menu.hasMoney() ? 0x80ff20 :  0xff6060);
            }

            double chance = menu.getActualChance();

            String format = decimalFormat.format(chance * 100)+"%";
            Component component = Component.literal(format);
            int x = this.imageWidth - 75 - this.font.width(component) - 2;
            matrices.drawString(font, component, x, 54, getColor(chance));
        }

    }

    protected int getColor(double chance) {
        float f = (float) Mth.clamp(chance,0,1);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CRAFTING_TABLE_LOCATION);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        pGuiGraphics.blit(CRAFTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

}
