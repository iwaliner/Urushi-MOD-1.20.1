package com.iwaliner.urushi.item;


import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class WaterCraftingItem extends BlockItem {
    private java.util.function.Supplier<? extends ItemLike> result;
  public WaterCraftingItem(java.util.function.Supplier<? extends ItemLike> result,Block block, Properties p_i48527_2_) {
        super(block, p_i48527_2_);
        this.result=result;
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(entity.isInWater()){
            entity.setItem(new ItemStack(result.get(),stack.getCount()));
            entity.level().playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1F, 1F);
            return true;
        }
        return false;
    }


}
