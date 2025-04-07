package tfar.upgradestation.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import tfar.upgradestation.ModSounds;
import tfar.upgradestation.UpgradeStation;

public class ModSoundDefinitionProvider extends SoundDefinitionsProvider {
    protected ModSoundDefinitionProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, UpgradeStation.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(ModSounds.FAIL, SoundDefinition.definition().with(sound(UpgradeStation.id("fail"))));
        add(ModSounds.SUCCESS, SoundDefinition.definition().with(sound(UpgradeStation.id("success"))));
    }
}
