package tfar.upgradestation.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;

import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.upgradestation.Init;
import tfar.upgradestation.UpgradeStation;

public class ModeItemModelProvider extends ItemModelProvider {
    public ModeItemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, UpgradeStation.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        makeSimpleBlockItem(Init.ITEM);
        ResourceLocation scroll = modLoc("item/scroll");

        generatedItem(Init.SCROLL_I,scroll);
        generatedItem(Init.SCROLL_II,scroll);
        generatedItem(Init.SCROLL_III,scroll);
        generatedItem(Init.SCROLL_IV,scroll);

        generatedItem(Init.SCROLL_OF_PROTECTION_I,scroll);
        generatedItem(Init.SCROLL_OF_PROTECTION_II,scroll);
        generatedItem(Init.SCROLL_OF_PROTECTION_III,scroll);
        generatedItem(Init.SCROLL_OF_PROTECTION_IV,scroll);

    }

    protected void makeSimpleBlockItem(Item item, ResourceLocation loc) {
        String s = BuiltInRegistries.ITEM.getKey(item).toString();
        getBuilder(s)
                .parent(getExistingFile(loc));
    }

    protected void makeSimpleBlockItem(Item item) {
        makeSimpleBlockItem(item,UpgradeStation.id("block/" + BuiltInRegistries.ITEM.getKey(item).getPath()));
    }


    private void generatedItem(Item item ,ResourceLocation texture) {
        String path = BuiltInRegistries.ITEM.getKey(item).getPath();
        singleTexture(path, mcLoc("item/generated"),
                "layer0", texture);
    }

    private void generatedItem(Item item) {
        generatedItem(item,modLoc("item/"+BuiltInRegistries.ITEM.getKey(item).getPath()));
    }
}
