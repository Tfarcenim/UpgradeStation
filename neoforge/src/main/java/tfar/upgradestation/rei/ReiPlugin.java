package tfar.upgradestation.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.forge.REIPluginCommon;
import tfar.upgradestation.UpgradeStation;

@REIPluginCommon
public class ReiPlugin implements REIServerPlugin {
    public static final CategoryIdentifier<UpgradeStationDisplay> UPGRADE_STATION = CategoryIdentifier.of(UpgradeStation.MOD_ID, "upgrade_station");

}
