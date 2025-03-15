package tfar.upgradestation;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

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
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics matrices, int $$1, int $$2) {
        //super.renderLabels(matrices, $$1, $$2);
        int xp = menu.getXPRequired();
        if (xp > -1) {
            Component component = Component.translatable("container.repair.cost",xp);
            int $$8 = this.imageWidth - 8 - this.font.width(component) - 2;
            matrices.drawString(font,component, $$8, 72, menu.canPickup() ? 0x80ff20 : 0xff6060);
        }
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
