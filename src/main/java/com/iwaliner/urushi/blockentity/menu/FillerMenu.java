package com.iwaliner.urushi.blockentity.menu;

import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.blockentity.slot.FillerMagatamaSlot;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class FillerMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;

    public FillerMenu(int p_38963_, Inventory p_38964_) {
        this(MenuRegister.FillerMenu.get(), p_38963_, p_38964_, new SimpleContainer(30), new SimpleContainerData(4));
    }
    public FillerMenu(int p_38963_, Inventory p_38964_,Container container,ContainerData containerData) {
        this(MenuRegister.FillerMenu.get(), p_38963_, p_38964_, container,containerData);
    }

    public FillerMenu(MenuType<?> menuType, int p_39230_, Inventory inventory, Container container,ContainerData containerData) {
        super(menuType, p_39230_);
        checkContainerSize(container, 30);
        checkContainerDataCount(containerData, 1);
        this.container = container;
        this.data=containerData;
        container.startOpen(inventory.player);
        for(int k = 0; k < 3; ++k) {
            this.addSlot(new FillerMagatamaSlot(container, k, 153, 7+k*18));
        }
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(container, 3 + j1 + l * 9, 8 + j1 * 18, 65 + l * 18+11));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 133 + l * 18+11 ));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 191+11));
        }
        this.addDataSlots(containerData);

    }

    public int getSize() {
        return 30;
    }

    public boolean stillValid(Player p_38974_) {
        return this.container.stillValid(p_38974_);
    }

    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (i < 3) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 3, false)) {
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

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
    public int getIndex(){
       return data.get(0);
    }
    public void setIndex(int j){
        data.set(0,j);
    }
    public boolean clickMenuButton(Player player, int variable) {
        setIndex(variable);
            return true;
    }
}

