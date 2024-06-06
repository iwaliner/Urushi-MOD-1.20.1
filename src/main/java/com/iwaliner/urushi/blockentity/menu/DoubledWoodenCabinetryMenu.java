package com.iwaliner.urushi.blockentity.menu;

import com.iwaliner.urushi.MenuRegister;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class DoubledWoodenCabinetryMenu extends AbstractContainerMenu {
    private static final int SLOTS_PER_ROW = 13;
    private final Container container;
    private final int containerRows=8;
    public DoubledWoodenCabinetryMenu( int p_39225_, Inventory p_39226_) {
        this(MenuRegister.DoubledWoodenCabinetryMenu.get(), p_39225_, p_39226_, new SimpleContainer(108));
    }
    public static DoubledWoodenCabinetryMenu twRows(int p_39247_, Inventory p_39248_, Container p_39249_) {
        return new DoubledWoodenCabinetryMenu(MenuRegister.DoubledWoodenCabinetryMenu.get(), p_39247_, p_39248_, p_39249_);
    }
    public DoubledWoodenCabinetryMenu(MenuType<?> menuType, int p_39230_, Inventory inventory, Container container) {
        super(menuType, p_39230_);
        checkContainerSize(container, 108);
        this.container = container;
        container.startOpen(inventory.player);
        int i = (this.containerRows - 4) * 18;

        for(int j = 0; j < this.containerRows; ++j) {
            for(int k = 0; k < SLOTS_PER_ROW; ++k) {
                this.addSlot(new Slot(container, k + j * SLOTS_PER_ROW, 8 + (k-0) * 18, 18 + j * 18));
            }
        }
        this.addSlot(new Slot(container, 104, 8 + (12 * 18), 18 + 126+18));
        this.addSlot(new Slot(container, 105, 8 + (12 * 18), 18 + 126+18*2));
        this.addSlot(new Slot(container, 106, 8 + (12 * 18), 18 + 126+18*3));
        this.addSlot(new Slot(container, 107, 8 + (12 * 18), 18 + 126+18*4));

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18+2*18, 103 + l * 18 + i+1));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18+2*18, 161 + i+1));
        }


    }
    public ItemStack quickMoveStack(Player p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ < this.containerRows * SLOTS_PER_ROW+4) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows *SLOTS_PER_ROW+4, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * SLOTS_PER_ROW+4, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(Player p_39251_) {
        super.removed(p_39251_);
        this.container.stopOpen(p_39251_);
    }
    public boolean stillValid(Player p_39242_) {
        return this.container.stillValid(p_39242_);
    }
    public Container getContainer() {
        return this.container;
    }

}
