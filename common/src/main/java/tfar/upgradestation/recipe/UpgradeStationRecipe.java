package tfar.upgradestation.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import tfar.upgradestation.ModRecipeSerializers;
import tfar.upgradestation.ModRecipeTypes;

public class UpgradeStationRecipe implements Recipe<Container> {

    protected final ResourceLocation id;
    final Ingredient template;//upgrade scroll
    final Ingredient base;//stone sword
    final Ingredient addition;//diamond

    final int moneyCost;
    final double baseChance;

    final ItemStack result;//netherite sword

    public UpgradeStationRecipe(ResourceLocation id, Ingredient template, Ingredient base, Ingredient addition, int moneyCost, double baseChance, ItemStack result) {
        this.id = id;
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.moneyCost = moneyCost;
        this.baseChance = baseChance;
        this.result = result;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.template.test(container.getItem(0)) && this.base.test(container.getItem(1)) && this.addition.test(container.getItem(2));
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.UPGRADE_STATION;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.UPGRADE_STATION;
    }

    public static class Serializer implements RecipeSerializer<UpgradeStationRecipe> {
        @Override
        public UpgradeStationRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return null;
        }

        @Override
        public UpgradeStationRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return null;
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, UpgradeStationRecipe upgradeStationRecipe) {

        }
    }
}
