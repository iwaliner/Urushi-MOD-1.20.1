package com.iwaliner.urushi.blockentity.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemStackHandlerSlot extends Slot {


    public ItemStackHandlerSlot(Container p_39521_, int p_39522_, int p_39523_, int p_39524_) {
        super(p_39521_, p_39522_, p_39523_, p_39524_);
    }





    @Override
    @NotNull
    public ItemStack remove(int amount)
    {
        //マウスでアイテムをスロットから取り出すときの動作
        ItemStack pickedStack=this.getItem().copy();
        pickedStack.setCount(amount);
        ItemStack slotStack=this.getItem().copy();
        slotStack.shrink(amount);
        this.set(slotStack);
        return pickedStack;
    }


}
