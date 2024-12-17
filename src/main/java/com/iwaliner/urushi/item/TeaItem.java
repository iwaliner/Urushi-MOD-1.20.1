package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class TeaItem extends Item {
    public TeaItem(Properties p_41383_) {
        super(p_41383_);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack itemstack = super.finishUsingItem(stack, level, livingEntity);
        return livingEntity instanceof Player && ((Player)livingEntity).getAbilities().instabuild ? itemstack : new ItemStack(ItemAndBlockRegister.empty_bamboo_cup.get());
    }
}
