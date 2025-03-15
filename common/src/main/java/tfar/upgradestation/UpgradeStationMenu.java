package tfar.upgradestation;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import tfar.upgradestation.recipe.UpgradeStationRecipe;

import java.util.List;

public class UpgradeStationMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final Container craftSlots = new SimpleContainer(3);
    private final ResultContainer resultSlot = new ResultContainer();
    private final Player player;
    private final Level level;
    private final List<UpgradeStationRecipe> recipes;
    public UpgradeStationRecipe current;
    private final DataSlot dataSlot = DataSlot.standalone();
    private final DataSlot canPickup = DataSlot.standalone();

    public UpgradeStationMenu(int id, Inventory $$1) {
        this(id, $$1, ContainerLevelAccess.NULL);
    }


    protected UpgradeStationMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        super(Init.MENU_TYPE, id);
        this.level = inventory.player.level();
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.UPGRADE_STATION);
        this.access = access;
        this.player = inventory.player;

        this.addSlot(new Slot( this.resultSlot, 0, 80, 18){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }


            @Override
            public boolean mayPickup(Player player) {
                return current != null && current.matches(craftSlots, level);
            }
        });

            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftSlots, j, 46 + j * 34, 81));
            }

            int y1= 140;

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, y1+ + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 58 + y1));
        }
        dataSlot.set(-1);
        addDataSlot(dataSlot);
        addDataSlot(canPickup);
    }


    /**
     * Determines whether supplied player can use this container
     */
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer,Init.BLOCK);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void slotsChanged(Container pInventory) {
        this.access.execute((p_39386_, p_39387_) -> {
        });
    }


    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 0) {
                this.access.execute((level, pos) -> itemstack1.getItem().onCraftedBy(itemstack1, level, pPlayer));
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (pIndex >= 10 && pIndex < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (pIndex < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
            if (pIndex == 0) {
                pPlayer.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    public int getXPRequired() {
        return dataSlot.get();
    }

    public boolean canPickup() {
        if (player.level().isClientSide) {
            return canPickup.get() != 0;
        }
        return current != null;
    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((p_39371_, p_39372_) -> this.clearContainer(pPlayer, this.craftSlots));
    }
}
