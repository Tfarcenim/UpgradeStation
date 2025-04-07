package tfar.upgradestation.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import tfar.upgradestation.ModRecipeSerializers;
import tfar.upgradestation.recipe.UpgradeStationRecipe;

public class UpgradeStationRecipeBuilder {
    private final Ingredient weapon;
    private final Ingredient gem;
    private final RecipeCategory category;

    private int money;
    private double baseChance;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<?> type;

    public UpgradeStationRecipeBuilder(RecipeSerializer<?> type, Ingredient weapon, Ingredient gem, RecipeCategory category, Item result) {
        this.category = category;
        this.type = type;
        this.weapon = weapon;
        this.gem = gem;
        this.result = result;
    }

    public static UpgradeStationRecipeBuilder create(Ingredient template, Ingredient base, RecipeCategory category, Item result) {
        return new UpgradeStationRecipeBuilder(ModRecipeSerializers.UPGRADE_STATION, template, base,category, result);
    }

    public UpgradeStationRecipeBuilder money(int money) {
        this.money = money;
        return this;
    }

    public UpgradeStationRecipeBuilder chance(double baseChance) {
        this.baseChance = baseChance;
        return this;
    }

    public void save(RecipeOutput recipeConsumer, String location) {
        this.save(recipeConsumer, ResourceLocation.parse(location));
    }

    public void save(RecipeOutput recipeConsumer, ResourceLocation location) {
        UpgradeStationRecipe upgradeStationRecipe = new UpgradeStationRecipe(this.weapon, this.gem, money,baseChance, new ItemStack(this.result));
        recipeConsumer.accept(location,upgradeStationRecipe,null);
    }
}

