package tfar.upgradestation;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class Init {

    public static final Block BLOCK = new UpgradeStationBlock(BlockBehaviour.Properties.of().strength(2.5f,5));
    public static final Item ITEM = new BlockItem(BLOCK,new Item.Properties());
    public static final Item UPGRADE_SCROLL = new Item(new Item.Properties());
    public static final MenuType<UpgradeStationMenu> MENU_TYPE = new MenuType<>(UpgradeStationMenu::new, FeatureFlags.VANILLA_SET);


}
