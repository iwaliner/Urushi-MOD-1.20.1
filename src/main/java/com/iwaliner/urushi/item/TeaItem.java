package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class TeaItem extends PlaceableFoodItem {
    public TeaItem(java.util.function.Supplier<? extends EntityType<?>> p_54694_, Properties p_41383_) {
        super(p_54694_,p_41383_);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        ItemStack itemstack = super.finishUsingItem(stack, level, livingEntity);
        if (livingEntity instanceof Player&&!((Player) livingEntity).getInventory().add(new ItemStack(ItemAndBlockRegister.empty_bamboo_cup.get()))) {
            ((Player) livingEntity).drop(new ItemStack(ItemAndBlockRegister.empty_bamboo_cup.get()), false);
        }
        return  itemstack;
    }
}
