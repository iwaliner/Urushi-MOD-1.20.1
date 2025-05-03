package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RiceBallWithFillingItem extends Item {

    public RiceBallWithFillingItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
       if(stack.getTag() != null &&stack.getTag().contains("effect")){
           UrushiUtils.setInfo(list,"rice_ball_with_filling");
           if(stack.getTag().getString("effect").equals("random")){
               UrushiUtils.setInfoWithColor(list,"rice_ball_with_filling_random", ChatFormatting.YELLOW);
           }
       }else{
           UrushiUtils.setInfo(list,"rice_ball");
       }
    }

    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack itemstack = super.finishUsingItem(stack, level, entity);
        CompoundTag compoundtag = itemstack.getTag();
        if (compoundtag != null && compoundtag.contains("effect")) {
            String id=compoundtag.getString("effect");
            if(id.equals("levitation")){
                entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION,20*5,0));
            }else if(id.equals("ignite")){
                entity.setSecondsOnFire(5);
            }else if(id.equals("strength")){
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,20*10,0));
            }else if(id.equals("glow")){
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING,20*10,0));
            }else if(id.equals("slow_fall")){
                entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING,20*10,0));
            }else if(id.equals("water_breathing")){
                entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,20*10,0));
            }else if(id.equals("explode")){
                level.explode(entity,entity.getX(),entity.getY()+1D,entity.getZ(),1.5f,false, Level.ExplosionInteraction.TNT);
            }else if(id.equals("jump")){
                entity.addEffect(new MobEffectInstance(MobEffects.JUMP,20*10,3));
            }else if(id.equals("nausea")){
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION,20*15,0));
            }else if(id.equals("slowness")){
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,20*10,2));
            }else if(id.equals("poison")){
                entity.addEffect(new MobEffectInstance(MobEffects.POISON,20*10,0));
            }else if(id.equals("freeze")){
                if(level.getBlockState(entity.blockPosition()).canBeReplaced()){
                    level.setBlockAndUpdate(entity.blockPosition(), Blocks.POWDER_SNOW.defaultBlockState());
                    if(level.getBlockState(entity.blockPosition().above()).canBeReplaced()){
                        level.setBlockAndUpdate(entity.blockPosition().above(), Blocks.POWDER_SNOW.defaultBlockState());
                    }
                }else if(level.getBlockState(entity.blockPosition().above()).canBeReplaced()){
                    level.setBlockAndUpdate(entity.blockPosition().above(), Blocks.POWDER_SNOW.defaultBlockState());

                }
                entity.setIsInPowderSnow(true);
                entity.setTicksFrozen(600);


            }
        }
        return  itemstack;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i1, boolean b1) {
        super.inventoryTick(stack, level, entity, i1, b1);
        if(stack.is(ItemAndBlockRegister.rice_ball.get())){
            CompoundTag tag=stack.getTag();
            if(tag!=null&&tag.contains("effect")&&tag.getString("effect").equals("random")){
              ItemStack stack2= UrushiUtils.getRandomRiceBall(stack.getCount(),level.getRandom());
              stack.setTag(stack2.getTag());
            }
        }
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        //UrushiUtils.onCraftingRiceBall(ItemAndBlockRegister.pickled_japanese_apricot.get(),stack);
        CompoundTag tag=new CompoundTag();
        tag.putBoolean("containsFilling",true);
        //stack.setTag(tag);
    }
}
