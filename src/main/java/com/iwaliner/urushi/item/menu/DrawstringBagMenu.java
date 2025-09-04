package com.iwaliner.urushi.item.menu;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.MenuRegister;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DrawstringBagMenu extends AbstractContainerMenu {
    private final Container container;
    private final int containerRows;
    public DrawstringBagMenu(int p_39225_, Inventory p_39226_) {
        this(MenuRegister.DrawstringBagMenu.get(), p_39225_, p_39226_, 6);
    }
    private DrawstringBagMenu(MenuType<?> p_39224_, int p_39225_, Inventory p_39226_, int p_39227_) {
        this(MenuRegister.DrawstringBagMenu.get(), p_39225_, p_39226_, new SimpleContainer(9 * p_39227_), p_39227_);
    }
    public static DrawstringBagMenu sixRows(int p_39247_, Inventory p_39248_, Container p_39249_) {
        return new DrawstringBagMenu(MenuRegister.DrawstringBagMenu.get(), p_39247_, p_39248_, p_39249_, 6);
    }
    public DrawstringBagMenu(MenuType<?> p_39229_, int p_39230_, Inventory p_39231_, Container p_39232_, int p_39233_) {
        super(MenuRegister.DrawstringBagMenu.get(), p_39230_);
        checkContainerSize(p_39232_, p_39233_ * 9);
        this.container = p_39232_;
        this.containerRows = p_39233_;
        p_39232_.startOpen(p_39231_.player);
        int i = (this.containerRows - 4) * 18;

        for(int j = 0; j < this.containerRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new ShulkerBoxSlot(p_39232_, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(p_39231_, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(p_39231_, i1, 8 + i1 * 18, 161 + i));
        }

    }
    public boolean stillValid(Player p_39242_) {
        return this.container.stillValid(p_39242_);
    }


    public ItemStack quickMoveStack(Player p_39253_, int p_39254_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39254_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_39254_ < this.containerRows * 9) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
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

    public Container getContainer() {
        return this.container;
    }

    public int getRowCount() {
        return this.containerRows;
    }
}
