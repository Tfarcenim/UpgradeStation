package tfar.upgradestation.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import tfar.upgradestation.*;

public class UpgradeStationRecipe implements Recipe<RecipeInput> {

    public final Ingredient weapon;//stone sword 0
    public final Ingredient gem;//diamond 1
    final int cost;
    final double baseChance;
    final ItemStack result;//netherite sword

    public UpgradeStationRecipe(Ingredient weapon, Ingredient gem, int cost, double baseChance, ItemStack result) {
        this.weapon = weapon;
        this.gem = gem;
        this.cost = cost;
        this.baseChance = baseChance;
        this.result = result;
    }

    @Override
    public boolean matches(RecipeInput container, Level level) {
        return isWeapon(container.getItem(UpgradeStationMenu.WEAPON_SLOT)) && isGem(container.getItem(UpgradeStationMenu.GEM_SLOT));
    }

    public boolean isWeapon(ItemStack stack) {
        return weapon.test(stack);
    }

    public boolean isGem(ItemStack stack) {
        return gem.test(stack);
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 3 && height >= 1;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return result;
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

        ScrollData scrollData = USConfig.getScrollData(scroll.getItem());

        if (scrollData == null) return baseChance;


        return Mth.clamp(baseChance * (1 + scrollData.chanceMultiplier()), 0, 1);
    }

    public static class Serializer implements RecipeSerializer<UpgradeStationRecipe> {

        public static final MapCodec<UpgradeStationRecipe> CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                                Ingredient.CODEC.fieldOf(Strings.WEAPON).forGetter(upgradeStationRecipe -> upgradeStationRecipe.weapon),
                                Ingredient.CODEC.fieldOf(Strings.GEM).forGetter(upgradeStationRecipe -> upgradeStationRecipe.gem),
                                Codec.INT.fieldOf("cost").forGetter(UpgradeStationRecipe::getCost),
                                Codec.DOUBLE.fieldOf("base_chance").forGetter(UpgradeStationRecipe::getBaseChance),
                        ItemStack.CODEC.fieldOf("result").forGetter(upgradeStationRecipe -> upgradeStationRecipe.result)
                        )
                        .apply(instance, UpgradeStationRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf,UpgradeStationRecipe> STREAM_CODEC =StreamCodec.of(
                Serializer::toNetwork, Serializer::fromNetwork
        );

        @Override
        public MapCodec<UpgradeStationRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, UpgradeStationRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static UpgradeStationRecipe fromNetwork(RegistryFriendlyByteBuf friendlyByteBuf) {
            Ingredient ingredient = Ingredient.CONTENTS_STREAM_CODEC.decode(friendlyByteBuf);
            Ingredient ingredient1 = Ingredient.CONTENTS_STREAM_CODEC.decode(friendlyByteBuf);

            int cost = friendlyByteBuf.readInt();
            double chance = friendlyByteBuf.readDouble();

            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(friendlyByteBuf);
            return new UpgradeStationRecipe(ingredient, ingredient1, cost, chance, itemstack);
        }

        public static void toNetwork(RegistryFriendlyByteBuf friendlyByteBuf, UpgradeStationRecipe upgradeStationRecipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(friendlyByteBuf,upgradeStationRecipe.weapon);
            Ingredient.CONTENTS_STREAM_CODEC.encode(friendlyByteBuf,upgradeStationRecipe.gem);

            friendlyByteBuf.writeInt(upgradeStationRecipe.cost);
            friendlyByteBuf.writeDouble(upgradeStationRecipe.baseChance);

            ItemStack.STREAM_CODEC.encode(friendlyByteBuf,upgradeStationRecipe.result);
        }
    }
}
