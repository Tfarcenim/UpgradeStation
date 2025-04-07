package tfar.upgradestation.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginCommon;
import tfar.upgradestation.Init;
import tfar.upgradestation.ModRecipeTypes;
import tfar.upgradestation.UpgradeStationMenu;
import tfar.upgradestation.UpgradeStationMenuScreen;
import tfar.upgradestation.recipe.UpgradeStationRecipe;


@REIPluginCommon
public class ReiClientPlugin implements REIClientPlugin {

  @Override
  public void registerCategories(CategoryRegistry registry) {

    registry.add(new UpgradeStationCategory());
            registry.addWorkstations(ReiPlugin.UPGRADE_STATION, EntryStacks.of(Init.ITEM));
  }

  @Override
  public void registerTransferHandlers(TransferHandlerRegistry registry) {
    registry.register(SimpleTransferHandler.create(UpgradeStationMenu.class, ReiPlugin.UPGRADE_STATION,
            new SimpleTransferHandler.IntRange(1, 10)));
  }

  @Override
  public void registerScreens(ScreenRegistry registry) {
    registry.registerContainerClickArea(new Rectangle(88, 32, 28, 23), UpgradeStationMenuScreen.class, ReiPlugin.UPGRADE_STATION);
  }

  @Override
  public void registerDisplays(DisplayRegistry registry) {
    registry.registerRecipeFiller(UpgradeStationRecipe.class, ModRecipeTypes.UPGRADE_STATION, upgradeStationRecipeRecipeHolder -> new UpgradeStationDisplay(upgradeStationRecipeRecipeHolder.value()));
  }
}
