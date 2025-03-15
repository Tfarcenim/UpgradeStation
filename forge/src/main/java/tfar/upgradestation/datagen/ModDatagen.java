package tfar.upgradestation.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import tfar.upgradestation.datagen.data.ModBlockTagsProvider;
import tfar.upgradestation.datagen.data.ModLootTableProvider;
import tfar.upgradestation.datagen.data.ModRecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModDatagen {

    public static void gather(GatherDataEvent event) {
        boolean client = event.includeClient();
        DataGenerator dataGenerator = event.getGenerator();
        PackOutput dataGeneratorPackOutput = dataGenerator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        dataGenerator.addProvider(client,new ModBlockstateProvider(dataGeneratorPackOutput,existingFileHelper));
        dataGenerator.addProvider(client,new ModLangProvider(dataGeneratorPackOutput));
        dataGenerator.addProvider(true, ModLootTableProvider.create(dataGeneratorPackOutput));
        dataGenerator.addProvider(true,new ModBlockTagsProvider(dataGeneratorPackOutput,lookupProvider,existingFileHelper));
        dataGenerator.addProvider(true,new ModRecipeProvider(dataGeneratorPackOutput));
        dataGenerator.addProvider(client,new ModeItemModelProvider(dataGeneratorPackOutput,existingFileHelper));
        dataGenerator.addProvider(client,new ModSoundDefinitionProvider(dataGeneratorPackOutput,existingFileHelper));
    }
}
