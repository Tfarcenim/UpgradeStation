package tfar.upgradestation;

import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.RegisterEvent;
import tfar.upgradestation.datagen.ModDatagen;

@Mod(UpgradeStation.MOD_ID)
public class UpgradeStationForge {
    
    public UpgradeStationForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::gather);
        bus.addListener(this::register);
        if (FMLEnvironment.dist.isClient()) {
            ModClientForge.init(bus);
        }
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        UpgradeStation.init();
        
    }

    private void register(RegisterEvent event) {
        event.register(Registries.BLOCK,UpgradeStation.id("upgrade_station"),() -> Init.BLOCK);
        event.register(Registries.ITEM,UpgradeStation.id("upgrade_station"),() -> Init.ITEM);
        event.register(Registries.MENU,UpgradeStation.id("upgrade_station"),() -> Init.MENU_TYPE);
        event.register(Registries.ITEM,UpgradeStation.id("upgrade_scroll"),() -> Init.UPGRADE_SCROLL);
        event.register(Registries.RECIPE_TYPE,UpgradeStation.id("upgrade_station"),() -> ModRecipeTypes.UPGRADE_STATION);

        event.register(Registries.RECIPE_SERIALIZER,UpgradeStation.id("upgrade_station"),() -> ModRecipeSerializers.UPGRADE_STATION);

    }
}