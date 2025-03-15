package tfar.upgradestation.datagen.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tfar.upgradestation.Init;
import tfar.upgradestation.UpgradeStation;
import tfar.upgradestation.datagen.UpgradeStationRecipeBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        /*UpgradeStationRecipeBuilder.create(Ingredient.of(Items.STONE_SWORD),Ingredient.of(Items.DIAMOND),Ingredient.of(Init.UPGRADE_SCROLL), RecipeCategory.MISC,Items.NETHERITE_SWORD)
                .chance(.125)
                .money(1)
                .unlocks("has_diamond",has(Items.DIAMOND))
                .save(writer, UpgradeStation.id("netherite_sword"));*/
    }
}
