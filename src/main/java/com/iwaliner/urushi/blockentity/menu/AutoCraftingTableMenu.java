package com.iwaliner.urushi.blockentity.menu;

import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.blockentity.AutoCraftingTableBlockEntity;
import com.iwaliner.urushi.blockentity.slot.*;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class AutoCraftingTableMenu extends RecipeBookMenu<Container> {

    private final Container container;
    private final Player player;


    public AutoCraftingTableMenu(int s, Inventory inventory) {
        this( s, inventory, new SimpleContainer(20));
    }

    public AutoCraftingTableMenu(int s, Inventory inventory,Container c) {
        super(MenuRegister.AutoCraftingTableMenu.get(), s);
        checkContainerSize(c, 20);
        this.container=c;
        this.player = inventory.player;

        container.startOpen(player);
        this.addSlot(new AutoCraftingTableResultSlot(container, 10, 125, 36));
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new AutoCraftingTableIngredientsSlot(this.container, i +11, 8 + i * 18, 79-5));
        }
        this.addSlot(new AutoCraftingTableResultMatrixSlot(container, 0, 87, 36));
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new AutoCraftingTableIngredientsMatrixSlot(this.container, j + i * 3+1, 26 + j * 18, 17 + i * 18-6+7));
            }
        }

       for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, 103 + k * 18+2));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 161+2));
        }
    }



    public void fillCraftSlotsStackedContents(StackedContents p_38976_) {
        if (this.container instanceof StackedContentsCompatible) {
            ((StackedContentsCompatible)this.container).fillStackedContents(p_38976_);
        }

    }

    public void clearCraftingContent() {
        this.container.clearContent();
    }

    public boolean recipeMatches(Recipe<? super Container> recipe) {
        CraftingContainer craftingcontainer = new TransientCraftingContainer(this,3,3);
        for (int i = 0; i < 9; i++) {
            craftingcontainer.setItem(i, container.getItem(i+1));
        }
        return recipe.matches(craftingcontainer, this.player.level());
    }

    public int getResultSlotIndex() {
        return 0;
    }

    public int getGridWidth() {
        return 1;
    }

    public int getGridHeight() {
        return 1;
    }

    public int getSize() {
        return 20;
    }


    public boolean stillValid(Player p_38974_) {
        return this.container.stillValid(p_38974_);
    }

    //プレイヤーがスロットをシフトクリックしてアイテムを瞬時に移動させるやつ
    public ItemStack quickMoveStack(Player player, int slotNumber) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotNumber);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            int playerInvFirst=20;
            if (slotNumber<10) {
                //「moveItemStackTo」は、第一引数のアイテムスタックを他のスロットに移動させる。
                // 最後の引数がfalseのとき、最初に第二引数のスロットへの移動を試行し、無理であれば隣のスロットへの移動を試行する。試行は第三引数のスロットの一個前まで行われる。
                //最後の引数がtrueのとき、逆順で試行を行う。
                if (!this.moveItemStackTo(itemstack1, playerInvFirst, playerInvFirst+36, true)) {
                    return ItemStack.EMPTY;
                }

            } else  if(slotNumber>19){
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
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

    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public boolean shouldMoveToInventory(int s) {
        return true;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack stack, int slot1, int slot2, boolean b) {
        return slot1 >= 10 && slot2 >= 10 && super.moveItemStackTo(stack, slot1, slot2, b);
    }
    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot instanceof AutoCraftingTableIngredientsSlot|| slot instanceof AutoCraftingTableResultSlot;
    }

    @Override
    public boolean canDragTo(Slot slot) {
        return slot instanceof AutoCraftingTableIngredientsSlot|| slot instanceof AutoCraftingTableResultSlot;
    }
}

