package tfar.upgradestation.datagen.data;

import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import tfar.upgradestation.Init;
import tfar.upgradestation.UpgradeStation;

public class ModBlockLoot extends VanillaBlockLoot {


    @Override
    protected void generate() {
        dropSelf(Init.BLOCK);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return UpgradeStation.getKnownBlocks().toList();
    }
}
