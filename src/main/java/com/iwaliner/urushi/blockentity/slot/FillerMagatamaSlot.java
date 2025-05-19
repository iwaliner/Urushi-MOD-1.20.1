package com.iwaliner.urushi.blockentity.slot;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FillerMagatamaSlot extends Slot {
    public FillerMagatamaSlot(Container p_39521_, int p_39522_, int p_39523_, int p_39524_) {
        super(p_39521_, p_39522_, p_39523_, p_39524_);
    }

    public boolean mayPlace(ItemStack itemStack) {
        return itemStack.getItem()== ItemAndBlockRegister.earth_element_magatama.get();
    }

    public int getMaxStackSize(ItemStack p_39528_) {
        return 1;
    }
}
