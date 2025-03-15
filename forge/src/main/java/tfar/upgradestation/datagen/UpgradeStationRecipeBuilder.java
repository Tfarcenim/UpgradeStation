package tfar.upgradestation.datagen;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import tfar.upgradestation.ModRecipeSerializers;

import java.util.function.Consumer;
import javax.annotation.Nullable;

public class UpgradeStationRecipeBuilder {
    private final Ingredient weapon;
    private final Ingredient gem;
    private final Ingredient scroll;
    private final RecipeCategory category;

    private int money;
    private double baseChance;
    private final Item result;
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final RecipeSerializer<?> type;

    public UpgradeStationRecipeBuilder(RecipeSerializer<?> type, Ingredient weapon, Ingredient gem, Ingredient scroll, RecipeCategory category, Item result) {
        this.category = category;
        this.type = type;
        this.weapon = weapon;
        this.gem = gem;
        this.scroll = scroll;
        this.result = result;
    }

    public static UpgradeStationRecipeBuilder create(Ingredient template, Ingredient base, Ingredient addition, RecipeCategory category, Item result) {
        return new UpgradeStationRecipeBuilder(ModRecipeSerializers.UPGRADE_STATION, template, base, addition, category, result);
    }

    public UpgradeStationRecipeBuilder money(int money) {
        this.money = money;
        return this;
    }

    public UpgradeStationRecipeBuilder chance(double baseChance) {
        this.baseChance = baseChance;
        return this;
    }

    public UpgradeStationRecipeBuilder unlocks(String key, CriterionTriggerInstance criterion) {
        this.advancement.addCriterion(key, criterion);
        return this;
    }

    public void save(Consumer<FinishedRecipe> recipeConsumer, String location) {
        this.save(recipeConsumer, new ResourceLocation(location));
    }

    public void save(Consumer<FinishedRecipe> recipeConsumer, ResourceLocation location) {
        this.ensureValid(location);
        this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(location)).rewards(AdvancementRewards.Builder.recipe(location)).requirements(RequirementsStrategy.OR);
        recipeConsumer.accept(new UpgradeStationRecipeBuilder.Result(location, this.type, this.weapon, this.gem, this.scroll,money,baseChance, this.result, this.advancement, location.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation location) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + location);
        }
    }

    public record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient weapon, Ingredient gem, Ingredient scroll, int money, double baseChance, Item result, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
        public void serializeRecipeData(JsonObject json) {
            json.add("weapon", this.weapon.toJson());
            json.add("gem", this.gem.toJson());
            json.add("scroll", this.scroll.toJson());

            json.addProperty("cost",money);
            json.addProperty("base_chance",baseChance);

            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            json.add("result", jsonobject);
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return this.type;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}

