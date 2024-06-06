package com.iwaliner.urushi.item;


import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HotKatanaBladeItem extends Item {
    private Item item;
  public HotKatanaBladeItem(Item i, Properties p_i48527_2_) {
        super( p_i48527_2_);
        item=i;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(entity.isInWater()){
            entity.setItem(new ItemStack(item,stack.getCount()));
            entity.level().playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1F, 1F);
            return true;
        }
        return false;
    }


}
