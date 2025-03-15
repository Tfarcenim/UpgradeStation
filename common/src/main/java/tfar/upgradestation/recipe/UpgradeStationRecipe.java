package tfar.upgradestation.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tfar.upgradestation.ModRecipeSerializers;
import tfar.upgradestation.ModRecipeTypes;
import tfar.upgradestation.Strings;
import tfar.upgradestation.UpgradeStationMenu;

public class UpgradeStationRecipe implements Recipe<Container> {

    protected final ResourceLocation id;
    public final Ingredient weapon;//stone sword 0
    public final Ingredient gem;//diamond 1
    public final Ingredient scroll;//upgrade scroll 2
    final int cost;
    final double baseChance;

    final ItemStack result;//netherite sword

    public UpgradeStationRecipe(ResourceLocation id, Ingredient weapon, Ingredient gem, Ingredient scroll, int cost, double baseChance, ItemStack result) {
        this.id = id;
        this.scroll = scroll;
        this.weapon = weapon;
        this.gem = gem;
        this.cost = cost;
        this.baseChance = baseChance;
        this.result = result;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return isWeapon(container.getItem(UpgradeStationMenu.WEAPON_SLOT)) && isGem(container.getItem(UpgradeStationMenu.GEM_SLOT));
    }

    public boolean isWeapon(ItemStack stack) {
        return weapon.test(stack);
    }

    public boolean isGem(ItemStack stack) {
        return gem.test(stack);
    }

    public boolean isScroll(ItemStack stack){
        return scroll.test(stack);
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 1;
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

    public int getCost() {
        return cost;
    }

    public double getBaseChance() {
        return baseChance;
    }

    public double getActualChance(ItemStack scroll) {
        return Mth.clamp(baseChance * (1 + scroll.getCount() /4d),0,1);
    }

    public static class Serializer implements RecipeSerializer<UpgradeStationRecipe> {

        @Override
        public UpgradeStationRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            Ingredient weapon = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, Strings.WEAPON));
            Ingredient gem = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, Strings.GEM));
            Ingredient scroll = Ingredient.fromJson(GsonHelper.getNonNull(jsonObject, Strings.SCROLL));
            int cost = GsonHelper.getAsInt(jsonObject,"cost",0);
            double chance = GsonHelper.getAsDouble(jsonObject,"base_chance",1);
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            return new UpgradeStationRecipe(resourceLocation,weapon,gem,scroll,cost,chance,itemstack);
        }

        @Override
        public UpgradeStationRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            Ingredient ingredient = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient ingredient1 = Ingredient.fromNetwork(friendlyByteBuf);
            Ingredient ingredient2 = Ingredient.fromNetwork(friendlyByteBuf);

            int cost = friendlyByteBuf.readInt();
            double chance = friendlyByteBuf.readDouble();

            ItemStack itemstack = friendlyByteBuf.readItem();
            return new UpgradeStationRecipe(resourceLocation, ingredient, ingredient1, ingredient2,cost,chance, itemstack);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, UpgradeStationRecipe upgradeStationRecipe) {
            upgradeStationRecipe.scroll.toNetwork(friendlyByteBuf);
            upgradeStationRecipe.weapon.toNetwork(friendlyByteBuf);
            upgradeStationRecipe.gem.toNetwork(friendlyByteBuf);

            friendlyByteBuf.writeInt(upgradeStationRecipe.cost);
            friendlyByteBuf.writeDouble(upgradeStationRecipe.baseChance);

            friendlyByteBuf.writeItem(upgradeStationRecipe.result);
        }
    }
}
