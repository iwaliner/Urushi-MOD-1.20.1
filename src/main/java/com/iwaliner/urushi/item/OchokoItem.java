package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class OchokoItem extends PlaceableFoodItem {
    public OchokoItem(java.util.function.Supplier<? extends EntityType<?>> p_54694_, Properties p_41383_) {
        super(p_54694_,p_41383_);
    }
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        stack.shrink(1);
        int slowdown=livingEntity.getEffect(MobEffects.MOVEMENT_SLOWDOWN)==null? -1 : Objects.requireNonNull(livingEntity.getEffect(MobEffects.MOVEMENT_SLOWDOWN)).getAmplifier();
        livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20*(10+8*slowdown),slowdown+1));
        if(slowdown>4) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 20 * (5+3*slowdown), 0));
        }
        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,slowdown<20? 20*(10+5*slowdown) : 20*(10+3*20), Math.min(Mth.floor(slowdown / 2f), 3)));


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
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        UrushiUtils.setInfo(list,"placeablefood");
        UrushiUtils.setInfo(list,"ochoko_1");
        UrushiUtils.setInfo(list,"ochoko_2");
    }
}
