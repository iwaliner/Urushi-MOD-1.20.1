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

public class UrushiHopperMenu extends AbstractContainerMenu {

    private final Container container;

    public UrushiHopperMenu(int p_39225_, Inventory p_39226_) {
        this(MenuRegister.UrushiHopperMenu.get(), p_39225_, p_39226_, new SimpleContainer(1));
    }
    public static UrushiHopperMenu UrushiHopperMenu(int p_39247_, Inventory p_39248_, Container p_39249_) {
        return new UrushiHopperMenu(MenuRegister.UrushiHopperMenu.get(), p_39247_, p_39248_, p_39249_);
    }
    public UrushiHopperMenu(MenuType<?> menuType, int p_39230_, Inventory inventory, Container container) {
        super(menuType, p_39230_);
        checkContainerSize(container, 1);
        this.container = container;
        container.startOpen(inventory.player);


        this.addSlot(new Slot(container, 0, 80, 20));

        for(int l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(inventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 109));
        }


    }
    public ItemStack quickMoveStack(Player p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ < 1) {
                if (!this.moveItemStackTo(itemstack1, 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
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
