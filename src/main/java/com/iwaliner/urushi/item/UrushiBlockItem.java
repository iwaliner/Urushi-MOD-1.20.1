package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class UrushiBlockItem extends BlockItem {
    public UrushiBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int a, boolean b) {
        super.inventoryTick(stack, level, entity, a, b);
       ItemStack mainStack=ItemStack.EMPTY;
       ItemStack offStack=ItemStack.EMPTY;
       boolean isPlayer=false;
        if(entity instanceof LivingEntity){
           LivingEntity livingEntity= (LivingEntity) entity;
           mainStack=livingEntity.getMainHandItem();
            offStack=livingEntity.getOffhandItem();
        }
        if(entity instanceof Player){
            isPlayer=true;
        }

    }
}
