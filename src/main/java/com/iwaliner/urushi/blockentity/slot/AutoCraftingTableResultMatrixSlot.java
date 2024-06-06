package com.iwaliner.urushi.blockentity.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AutoCraftingTableResultMatrixSlot extends Slot {


    public AutoCraftingTableResultMatrixSlot(Container p_39521_, int p_39522_, int p_39523_, int p_39524_) {
        super(p_39521_, p_39522_, p_39523_, p_39524_);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    @NotNull
    public ItemStack remove(int amount)
    {
        //マウスでアイテムをスロットから取り出すときの動作

        ItemStack slotStack=this.getItem().copy();
        slotStack.shrink(amount);
        this.set(slotStack);
        return ItemStack.EMPTY;
    }
    public int getMaxStackSize(ItemStack p_39528_) {
        return 1;
    }



}
