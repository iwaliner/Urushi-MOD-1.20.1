package com.iwaliner.urushi.blockentity.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AutoCraftingTableIngredientsMatrixSlot extends Slot {


    public AutoCraftingTableIngredientsMatrixSlot(Container p_39521_, int p_39522_, int p_39523_, int p_39524_) {
        super(p_39521_, p_39522_, p_39523_, p_39524_);
    }

    public ItemStack safeInsert(ItemStack stack, int a) {
        //  アイテムをスロットに設置するときに実際に設置するのではなくコピーを行う
        if (!stack.isEmpty() && this.mayPlace(stack)) {
            ItemStack imitation=stack.copy();
            imitation.setCount(1);
            if(this.getItem().isEmpty()){
                this.set(imitation);
            }else{
                this.set(ItemStack.EMPTY);
            }

            return stack;
        } else {
            return stack;
        }
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
