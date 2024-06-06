package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.lighting.LightEngine;

public class KakuriyoGrassBlock extends Block {
    public KakuriyoGrassBlock(Properties p_49795_) {
        super(p_49795_);
    }
    private static boolean canBeGrass(BlockState p_56824_, LevelReader level, BlockPos p_56826_) {
        BlockPos blockpos = p_56826_.above();
        BlockState blockstate = level.getBlockState(blockpos);
         if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
             int i = LightEngine.getLightBlockInto(level, p_56824_, p_56826_, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(level, blockpos));
             return i < level.getMaxLightLevel();
         }
    }

    private static boolean canPropagate(BlockState p_56828_, LevelReader p_56829_, BlockPos p_56830_) {
        BlockPos blockpos = p_56830_.above();
        return canBeGrass(p_56828_, p_56829_, p_56830_) && !p_56829_.getFluidState(blockpos).is(FluidTags.WATER);
    }

    public void randomTick(BlockState p_56819_, ServerLevel p_56820_, BlockPos p_56821_, RandomSource p_56822_) {
        if (!canBeGrass(p_56819_, p_56820_, p_56821_)) {
            if (!p_56820_.isAreaLoaded(p_56821_, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            p_56820_.setBlockAndUpdate(p_56821_, ItemAndBlockRegister.kakuriyo_dirt.get().defaultBlockState());
        } else {
            if (!p_56820_.isAreaLoaded(p_56821_, 3)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            if (p_56820_.getMaxLocalRawBrightness(p_56821_.above()) >= 9) {
                BlockState blockstate = this.defaultBlockState();
                for(int i = 0; i < 4; ++i) {
                    BlockPos blockpos = p_56821_.offset(p_56822_.nextInt(3) - 1, p_56822_.nextInt(5) - 3, p_56822_.nextInt(3) - 1);
                    if (p_56820_.getBlockState(blockpos).is(ItemAndBlockRegister.kakuriyo_dirt.get()) && canPropagate(blockstate, p_56820_, blockpos)) {
                        p_56820_.setBlockAndUpdate(blockpos, blockstate);
                    }
                }
            }

        }
    }
}
