package tfar.upgradestation.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import tfar.upgradestation.UpgradeStation;

public class ModSoundDefinitionProvider extends SoundDefinitionsProvider {
    /**
     * Creates a new instance of this data provider.
     *
     * @param output The {@linkplain PackOutput} instance provided by the data generator.
     * @param modId  The mod ID of the current mod.
     * @param helper The existing file helper provided by the event you are initializing this provider in.
     */
    protected ModSoundDefinitionProvider(PackOutput output,ExistingFileHelper helper) {
        super(output, UpgradeStation.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {

    }
}
