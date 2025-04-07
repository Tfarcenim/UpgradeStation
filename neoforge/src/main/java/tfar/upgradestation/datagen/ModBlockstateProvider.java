package tfar.upgradestation.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.LecternBlock;

import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import tfar.upgradestation.Init;
import tfar.upgradestation.UpgradeStation;

public class ModBlockstateProvider extends BlockStateProvider {


    public ModBlockstateProvider(PackOutput pOutput, ExistingFileHelper helper) {
        super(pOutput, UpgradeStation.MOD_ID,helper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile.ExistingModelFile modelFile = models().getExistingFile(modLoc("block/upgrade_station"));
        getVariantBuilder(Init.BLOCK).forAllStates(state -> {
            Direction direction = state.getValue(LecternBlock.FACING);
            return ConfiguredModel.builder().modelFile(modelFile).rotationY((int) direction.toYRot()).build();
        });
    }
}
