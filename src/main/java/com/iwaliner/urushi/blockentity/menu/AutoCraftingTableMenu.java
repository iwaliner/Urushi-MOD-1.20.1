package com.iwaliner.urushi.blockentity.menu;

import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.blockentity.slot.AutoCraftingTableIngredientsMatrixSlot;
import com.iwaliner.urushi.blockentity.slot.AutoCraftingTableIngredientsSlot;
import com.iwaliner.urushi.blockentity.slot.AutoCraftingTableResultMatrixSlot;
import com.iwaliner.urushi.blockentity.slot.AutoCraftingTableResultSlot;
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
    private final ContainerData containerData;


    public AutoCraftingTableMenu(int s, Inventory inventory) {
        this( s, inventory, new SimpleContainer(20),new SimpleContainerData(2));
    }

    public AutoCraftingTableMenu(int s, Inventory inventory,Container c,ContainerData data) {
        super(MenuRegister.AutoCraftingTableMenu.get(), s);
        checkContainerSize(c, 20);
        this.container=c;
        checkContainerDataCount(data, 2);
        this.containerData=data;

        this.player = inventory.player;
        this.addDataSlots(data);

        //container.startOpen(player);
        this.addSlot(new AutoCraftingTableResultSlot(container, 10, 144, 93));//搬出スロット(完成品)
        for(int i = 0; i < 9; ++i) {
            this.addSlot(new AutoCraftingTableIngredientsSlot(this.container, i +11, 50 + (i%3) * 18, 75+(i/3)*18));//材料スロット
        }
        this.addSlot(new AutoCraftingTableResultMatrixSlot(container, 0, 144, 35)); //見本の完成品スロット

        for(int i = 0; i < 9; ++i) {
            this.addSlot(new AutoCraftingTableIngredientsMatrixSlot(this.container, i +1, 50 + (i%3) * 18, 17+(i/3)*18));//見本の材料スロット
        }

       for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, 143 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(inventory, l, 8 + l * 18, 201));
        }
    }

    public int getCoolTime(){
        return containerData.get(0);
    }
    public int getMaxCoolTime(){
        return containerData.get(1);
    }
    public int getCraftingProgress() {
        int i = this.containerData.get(0);
        int j = this.containerData.get(1);
        return j != 0 && i != 0 ? i * 24 / j : 0;
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

