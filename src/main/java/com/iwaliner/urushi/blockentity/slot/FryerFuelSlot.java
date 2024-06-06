package com.iwaliner.urushi.blockentity.slot;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.blockentity.menu.AbstractFryerMenu;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractFurnaceMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class FryerFuelSlot extends Slot {
    private final AbstractFryerMenu menu;



    public FryerFuelSlot(AbstractFryerMenu p_39520_, Container p_39521_, int p_39522_, int p_39523_, int p_39524_) {
        super(p_39521_, p_39522_, p_39523_, p_39524_);
        this.menu = p_39520_;
    }

    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.getItem()== ItemAndBlockRegister.vegetable_oil.get();
    }

    public int getMaxStackSize(ItemStack p_39528_) {
        return isBucket(p_39528_) ? 1 : super.getMaxStackSize(p_39528_);
    }

    public static boolean isBucket(ItemStack p_39530_) {
        return p_39530_.is(Items.BUCKET);
    }
}
