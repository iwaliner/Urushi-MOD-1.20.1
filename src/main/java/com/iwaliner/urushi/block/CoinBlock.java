package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CoinBlock extends HorizonalRotateBlock {
    private static final VoxelShape Shape = Block.box(5.5D, 0.0D, 5.5D, 16D-5.5D, 1D, 16D-5.5D);
    public CoinBlock(Properties p_i48377_1_) {
        super(p_i48377_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shape;
    }
}
