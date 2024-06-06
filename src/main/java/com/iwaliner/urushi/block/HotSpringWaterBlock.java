package com.iwaliner.urushi.block;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

import net.minecraft.util.RandomSource;

public class HotSpringWaterBlock extends LiquidBlock {

    public HotSpringWaterBlock(java.util.function.Supplier<? extends FlowingFluid> p_54694_, BlockBehaviour.Properties p_54695_) {
        super(p_54694_,p_54695_);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if(!level.getFluidState(pos.above()).is(Fluids.WATER)&&random.nextInt(40)==0) {
            double d0 = (double) pos.above().getX() + random.nextInt(10) * 0.1D;
            double d1 = (double) pos.above().getY() + random.nextInt(5) * 0.1D;
            double d2 = (double) pos.above().getZ() + random.nextInt(10) * 0.1D;
            level.addParticle(ParticleTypes.CLOUD, d0, d1, d2, 0.0D, 0.1D, 0.0D);
        }
    }
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (entity instanceof LivingEntity) {
            if (((LivingEntity) entity).getEffect(MobEffects.REGENERATION)==null) {
            //      ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 3 * 20, 0), entity);
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.REGENERATION, 10 * 20, 0), entity);
        }
        }else if(entity instanceof ItemEntity){
            ItemEntity itemEntity= (ItemEntity) entity;
            if(itemEntity.getItem().getItem()== Items.EGG){
                itemEntity.setItem(new ItemStack(ItemAndBlockRegister.onsen_egg.get(),itemEntity.getItem().getCount()));
            }
        }

    }


}
