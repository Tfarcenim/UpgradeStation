package tfar.upgradestation.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import tfar.upgradestation.Init;
import tfar.upgradestation.UpgradeStationMenu;

import java.util.List;

public class UpgradeStationCategory implements DisplayCategory<UpgradeStationDisplay> {


    @Override
    public CategoryIdentifier<? extends UpgradeStationDisplay> getCategoryIdentifier() {
        return ReiPlugin.UPGRADE_STATION;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Init.ITEM);
    }

    @Override
    public Component getTitle() {
        return Init.ITEM.getDefaultInstance().getDisplayName();
    }

    @Override
    public List<Widget> setupDisplay(UpgradeStationDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 31, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        int offsetX = 5;
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27 + offsetX, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61 + offsetX, startPoint.y + 5)));


        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4 - 36 + offsetX, startPoint.y + 5)).entries(display.getInputEntries().get(UpgradeStationMenu.WEAPON_SLOT)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4 -18 + offsetX, startPoint.y + 5)).entries(display.getInputEntries().get(UpgradeStationMenu.GEM_SLOT)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4  + offsetX, startPoint.y + 5)).entries(display.getInputEntries().get(UpgradeStationMenu.SCROLL_SLOT)).markInput());

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61 + offsetX, startPoint.y + 5)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());

        int cost = display.cost;

        Component co = Component.literal("Money: "+ cost);

        widgets.add(Widgets.createLabel(new Point(bounds.x + 26, bounds.getMaxY() - 57), co).color(0x404040, 0xbbbbbb).noShadow().leftAligned());

        double chance = display.baseChance;

        Component c = Component.literal("Base Chance: "+ chance);

        widgets.add(Widgets.createLabel(new Point(bounds.x + 26, bounds.getMaxY() - 15), c).color(0x404040, 0xbbbbbb).noShadow().leftAligned());

        return widgets;
    }
}
