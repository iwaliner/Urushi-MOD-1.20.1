package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;

public class FallingAnvilBlock extends Block implements Fallable {
    public FallingAnvilBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean canSurvive(BlockState p_60525_, LevelReader p_60526_, BlockPos p_60527_) {
        return false;
    }

    @Override
    public DamageSource getFallDamageSource(Entity p_253907_) {
        return Fallable.super.getFallDamageSource(p_253907_);
    }
}
