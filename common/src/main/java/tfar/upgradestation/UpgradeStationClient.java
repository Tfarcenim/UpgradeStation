package tfar.upgradestation;

import net.minecraft.client.gui.screens.MenuScreens;

public class UpgradeStationClient {

    public static void setup() {
        MenuScreens.register(Init.MENU_TYPE, UpgradeStationMenuScreen::new);
    }

}
