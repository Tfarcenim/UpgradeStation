package tfar.upgradestation.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import tfar.upgradestation.USConfig;
import tfar.upgradestation.recipe.UpgradeStationRecipe;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UpgradeStationDisplay extends BasicDisplay {
    protected double baseChance;
    protected int cost;

    public UpgradeStationDisplay(UpgradeStationRecipe recipe) {
        this(List.of(scrolls(), EntryIngredients.ofIngredient(recipe.weapon),
                EntryIngredients.ofIngredient(recipe.gem)),List.of(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess())))
                ,recipe.getCost(),recipe.getBaseChance());
    }

    static EntryIngredient scrolls() {
        return EntryIngredients.ofItems((Set<ItemLike>)(Object)USConfig.CONFIG.scroll_map.get().keySet());
    }

    public UpgradeStationDisplay(List<EntryIngredient> input, List<EntryIngredient> output, int money, double baseChance) {
        super(input, output);
        this.cost = money;
        this.baseChance = baseChance;
    }
    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ReiPlugin.UPGRADE_STATION;
    }

}
