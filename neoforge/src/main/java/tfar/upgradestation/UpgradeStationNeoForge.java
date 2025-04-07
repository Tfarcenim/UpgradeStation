package tfar.upgradestation;

import net.minecraft.core.registries.Registries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import tfar.upgradestation.datagen.ModDatagen;

@Mod(UpgradeStation.MOD_ID)
public class UpgradeStationNeoForge {
    
    public UpgradeStationNeoForge(IEventBus bus, ModContainer container, Dist dist) {
                container.registerConfig(ModConfig.Type.SERVER,USConfig.SERVER_SPEC);
        bus.addListener(ModDatagen::gather);
        bus.addListener(this::register);
        bus.addListener(this::setup);
        if (dist.isClient()) {
            ModClientForge.init(bus);
        }
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        UpgradeStation.init();
        
    }

    void setup(FMLCommonSetupEvent event) {

    }

    private void register(RegisterEvent event) {
        event.register(Registries.BLOCK,UpgradeStation.id("upgrade_station"),() -> Init.BLOCK);
        event.register(Registries.ITEM,UpgradeStation.id("upgrade_station"),() -> Init.ITEM);
        event.register(Registries.MENU,UpgradeStation.id("upgrade_station"),() -> Init.MENU_TYPE);

        event.register(Registries.ITEM,UpgradeStation.id("scroll_i"),() -> Init.SCROLL_I);
        event.register(Registries.ITEM,UpgradeStation.id("scroll_ii"),() -> Init.SCROLL_II);
        event.register(Registries.ITEM,UpgradeStation.id("scroll_iii"),() -> Init.SCROLL_III);
        event.register(Registries.ITEM,UpgradeStation.id("scroll_iv"),() -> Init.SCROLL_IV);

        event.register(Registries.ITEM,UpgradeStation.id("scroll_of_protection_i"),() -> Init.SCROLL_OF_PROTECTION_I);
        event.register(Registries.ITEM,UpgradeStation.id("scroll_of_protection_ii"),() -> Init.SCROLL_OF_PROTECTION_II);
        event.register(Registries.ITEM,UpgradeStation.id("scroll_of_protection_iii"),() -> Init.SCROLL_OF_PROTECTION_III);
        event.register(Registries.ITEM,UpgradeStation.id("scroll_of_protection_iv"),() -> Init.SCROLL_OF_PROTECTION_IV);


        event.register(Registries.RECIPE_TYPE,UpgradeStation.id("upgrade_station"),() -> ModRecipeTypes.UPGRADE_STATION);

        event.register(Registries.RECIPE_SERIALIZER,UpgradeStation.id("upgrade_station"),() -> ModRecipeSerializers.UPGRADE_STATION);

        event.register(Registries.SOUND_EVENT,ModSounds.FAIL.getLocation(),() -> ModSounds.FAIL);
        event.register(Registries.SOUND_EVENT,ModSounds.SUCCESS.getLocation(),() -> ModSounds.SUCCESS);

    }
}