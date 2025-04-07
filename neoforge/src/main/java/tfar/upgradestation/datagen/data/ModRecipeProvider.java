package tfar.upgradestation.datagen.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import tfar.upgradestation.UpgradeStation;
import tfar.upgradestation.datagen.UpgradeStationRecipeBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {


    public static final boolean ENABLE = false;

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput writer) {
        if (ENABLE) {
            UpgradeStationRecipeBuilder.create(Ingredient.of(Items.STONE_SWORD), Ingredient.of(Items.DIAMOND), RecipeCategory.MISC, Items.NETHERITE_SWORD)
                    .chance(.125)
                    .money(1)
                    .save(writer, UpgradeStation.id("netherite_sword"));
        }
    }
}
