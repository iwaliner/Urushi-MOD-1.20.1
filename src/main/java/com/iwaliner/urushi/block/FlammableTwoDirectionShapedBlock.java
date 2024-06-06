package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public class FlammableTwoDirectionShapedBlock extends TwoDirectionShapedBlock{
    public FlammableTwoDirectionShapedBlock(double d1, double d2, double d3, double d4, double d5, double d6, Properties p_i48377_1_) {
        super(d1, d2, d3, d4, d5, d6, p_i48377_1_);
    }
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }
}
