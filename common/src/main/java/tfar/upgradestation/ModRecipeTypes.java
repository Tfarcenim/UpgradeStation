package tfar.upgradestation;

import net.minecraft.world.item.crafting.RecipeType;
import tfar.upgradestation.recipe.UpgradeStationRecipe;

public class ModRecipeTypes {
    public static final RecipeType<UpgradeStationRecipe> UPGRADE_STATION = new RecipeType<>() {
        @Override
        public String toString() {
            return UpgradeStation.MOD_ID+":upgrade_station";
        }
    };
}
