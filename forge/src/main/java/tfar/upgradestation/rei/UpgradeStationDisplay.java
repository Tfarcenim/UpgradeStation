package tfar.upgradestation.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import tfar.upgradestation.recipe.UpgradeStationRecipe;

import java.util.List;

public class UpgradeStationDisplay extends BasicDisplay {
    protected double baseChance;
    protected int cost;

    public UpgradeStationDisplay(UpgradeStationRecipe recipe) {
        this(List.of(EntryIngredients.ofIngredient(recipe.scroll), EntryIngredients.ofIngredient(recipe.weapon),
                EntryIngredients.ofIngredient(recipe.gem)),List.of(EntryIngredients.of(recipe.getResultItem(BasicDisplay.registryAccess())))
                ,recipe.getCost(),recipe.getBaseChance());
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
