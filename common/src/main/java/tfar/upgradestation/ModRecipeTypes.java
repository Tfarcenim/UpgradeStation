package tfar.upgradestation;

import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final RecipeType<CraftingRecipe> UPGRADE_STATION = new RecipeType<>() {
        @Override
        public String toString() {
            return UpgradeStation.MOD_ID+":upgrade_station";
        }
    };
}
