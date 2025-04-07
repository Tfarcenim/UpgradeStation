package tfar.upgradestation;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import tfar.upgradestation.platform.Services;
import tfar.upgradestation.recipe.UpgradeStationRecipe;

import java.util.List;
import java.util.Optional;

public class UpgradeStationMenu extends AbstractContainerMenu {

    public static final int WEAPON_SLOT = 0;
    public static final int GEM_SLOT = 1;
    public static final int SCROLL_SLOT = 2;
    public static final int RESULT_SLOT = 3;

    private final ContainerLevelAccess access;
    private final SimpleContainer craftSlots = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            slotsChanged(this);
        }
    };
    final ResultContainer resultSlot = new ResultContainer();
    private final Player player;
    private final Level level;
    private final List<RecipeHolder<UpgradeStationRecipe>> recipes;
    RecipeHolder<UpgradeStationRecipe> selectedRecipe;

    public UpgradeStationMenu(int id, Inventory $$1) {
        this(id, $$1, ContainerLevelAccess.NULL);
    }


    protected UpgradeStationMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        super(Init.MENU_TYPE, id);
        this.level = inventory.player.level();
        this.recipes = this.level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.UPGRADE_STATION);
        this.access = access;
        this.player = inventory.player;


        this.addSlot(new Slot(this.craftSlots, WEAPON_SLOT, 46, 81){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && recipes.stream().anyMatch(upgradeStationRecipe -> upgradeStationRecipe.value().isWeapon(stack));
            }
        });
        this.addSlot(new Slot(this.craftSlots, GEM_SLOT, 46 + 34, 81){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && recipes.stream().anyMatch(upgradeStationRecipe -> upgradeStationRecipe.value().isGem(stack));
            }
        });
        this.addSlot(new Slot(this.craftSlots, SCROLL_SLOT, 46 + 2 * 34, 81){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && USConfig.getScrollData(stack.getItem()) != null;
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });


        this.addSlot(new Slot( this.resultSlot, 0, 80, 18){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                UpgradeStationMenu.this.onTake(player, stack);
            }

            @Override
            public boolean mayPickup(Player player) {
                return selectedRecipe != null && selectedRecipe.value().matches(of(craftSlots), level) && hasMoney();
            }
        });

        int y1= 140;

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, y1+ + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 58 + y1));
        }

    }

    public boolean hasMoney() {
        return Services.PLATFORM.hasAtLeast(player,selectedRecipe != null ? selectedRecipe.value().getCost() : 0);
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
    public void slotsChanged(Container inventory) {
        super.slotsChanged(inventory);
        if (inventory == this.craftSlots) {
            this.createResult();
        }
    }

    public void createResult() {
        List<RecipeHolder<UpgradeStationRecipe>> list = this.level.getRecipeManager().getRecipesFor(ModRecipeTypes.UPGRADE_STATION, of(craftSlots), this.level);
        if (list.isEmpty()) {
            this.resultSlot.setItem(0, ItemStack.EMPTY);
        } else {
            RecipeHolder<UpgradeStationRecipe> smithingrecipe = list.getFirst();
            ItemStack itemstack = smithingrecipe.value().assemble(of(craftSlots), this.level.registryAccess());
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.selectedRecipe = smithingrecipe;
                this.resultSlot.setRecipeUsed(smithingrecipe);
                this.resultSlot.setItem(0, itemstack);
            }
        }
    }

    public static RecipeInput of(SimpleContainer container) {
        return new RecipeInput() {
            @Override
            public ItemStack getItem(int index) {
                return container.getItem(index);
            }

            @Override
            public int size() {
                return container.getContainerSize();
            }
        };
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int i = this.getInventorySlotStart();
            int j = this.getUseRowEnd();
            if (index == this.getResultSlot()) {
                if (!this.moveItemStackTo(itemstack1, i, j, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index < craftSlots.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, i, j, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.canMoveIntoInputSlots(itemstack1) && index >= this.getInventorySlotStart() && index < this.getUseRowEnd()) {
                int k = this.getSlotToQuickMoveTo(itemstack);
                if (!this.moveItemStackTo(itemstack1, k, this.getResultSlot(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= this.getInventorySlotStart() && index < this.getInventorySlotEnd()) {
                if (!this.moveItemStackTo(itemstack1, this.getUseRowStart(), this.getUseRowEnd(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= this.getUseRowStart() && index < this.getUseRowEnd() && !this.moveItemStackTo(itemstack1, this.getInventorySlotStart(), this.getInventorySlotEnd(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    protected void onTake(Player player, ItemStack stack) {
        boolean worked = player.getRandom().nextDouble() < getActualChance();
        ItemStack scroll = craftSlots.getItem(SCROLL_SLOT);

        boolean protectIfFailed = !scroll.isEmpty() && USConfig.getScrollData(scroll.getItem()).protectsWeapon();

        if (worked) {
            stack.onCraftedBy(player.level(), player, stack.getCount());
            this.resultSlot.awardUsedRecipes(player, this.getRelevantItems());
        } else {
            stack.shrink(1);
        }

        this.access.execute((p_40263_, p_40264_) -> {
            Services.PLATFORM.takeMoney(player,selectedRecipe.value().getCost());

            if (!protectIfFailed || worked) {
                this.shrinkStackInSlot(0);
                this.shrinkStackInSlot(1);
            }
            this.shrinkStackInSlot(2);
            SoundEvent soundEvent = worked ? ModSounds.SUCCESS : ModSounds.FAIL;
            ((ServerPlayer)player).serverLevel().playSound(null,p_40264_,soundEvent, SoundSource.PLAYERS,1,1);
            //p_40263_.levelEvent(LevelEvent.SOUND_SMITHING_TABLE_USED, p_40264_, 0);

        });
    }

    private void shrinkStackInSlot(int index) {
        ItemStack itemstack = this.craftSlots.getItem(index);
        if (!itemstack.isEmpty()) {
            itemstack.shrink(1);
            this.craftSlots.setItem(index, itemstack);
        }
    }

    private List<ItemStack> getRelevantItems() {
        return List.of(this.craftSlots.getItem(0), this.craftSlots.getItem(1), this.craftSlots.getItem(2));
    }

    public int getResultSlot() {
        return this.craftSlots.getContainerSize();
    }

    private int getInventorySlotStart() {
        return this.getResultSlot() + 1;
    }

    private int getInventorySlotEnd() {
        return this.getInventorySlotStart() + 27;
    }

    private int getUseRowStart() {
        return this.getInventorySlotEnd();
    }

    private int getUseRowEnd() {
        return this.getUseRowStart() + 9;
    }

    public double getActualChance(){
        ItemStack stack = craftSlots.getItem(SCROLL_SLOT);
        return selectedRecipe != null ? selectedRecipe.value().getActualChance(stack) : 0;
    }


    public int getSlotToQuickMoveTo(ItemStack stack) {
        return this.recipes.stream().map((p_266640_) -> {
            return findSlotMatchingIngredient(p_266640_.value(), stack);
        }).filter(Optional::isPresent).findFirst().orElse(Optional.of(0)).get();
    }

    private static Optional<Integer> findSlotMatchingIngredient(UpgradeStationRecipe recipe, ItemStack stack) {
        if (recipe.isWeapon(stack)) {
            return Optional.of(WEAPON_SLOT);
        } else if (recipe.isGem(stack)) {
            return Optional.of(GEM_SLOT);
        } else {
            return USConfig.getScrollData(stack.getItem()) != null ? Optional.of(SCROLL_SLOT) : Optional.empty();
        }
    }

    /**
     * Called to determine if the current slot is valid for the stack merging (double-click) code. The stack passed in is null for the initial slot that was double-clicked.
     */
    @Override
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.craftSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public boolean canMoveIntoInputSlots(ItemStack stack) {
        return this.recipes.stream().map((p_266647_) -> findSlotMatchingIngredient(p_266647_.value(), stack)).anyMatch(Optional::isPresent);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((p_39371_, p_39372_) -> this.clearContainer(pPlayer, this.craftSlots));
    }
}
