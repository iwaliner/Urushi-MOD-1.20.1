package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class TeaItem extends PlaceableFoodItem {
    public TeaItem(java.util.function.Supplier<? extends EntityType<?>> p_54694_, Properties p_41383_) {
        super(p_54694_,p_41383_);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20*60*1, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20*60*2, 0));
        stack.shrink(1);
        ItemStack itemstack = super.finishUsingItem(stack, level, livingEntity);
        if (livingEntity instanceof Player&&!((Player) livingEntity).getInventory().add(new ItemStack(ItemAndBlockRegister.empty_bamboo_cup.get()))) {
            ((Player) livingEntity).drop(new ItemStack(ItemAndBlockRegister.empty_bamboo_cup.get()), false);
        }
        return  stack;
    }
    public int getUseDuration(ItemStack p_43001_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42997_) {
        return UseAnim.DRINK;
    }

    public InteractionResultHolder<ItemStack> use(Level p_42993_, Player p_42994_, InteractionHand p_42995_) {
        return ItemUtils.startUsingInstantly(p_42993_, p_42994_, p_42995_);
    }
}
