package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LongChochinBlock extends HorizonalRotateBlock {
    protected static final VoxelShape SHAPE_TOP = Block.box(5.0D, 14.0D, 5D, 11.0D, 16.0D, 11.0D);
    protected static final VoxelShape SHAPE_CENTER = Block.box(3.0D, 2.0D, 3D, 13.0D, 14.0D, 13.0D);
    protected static final VoxelShape SHAPE_BOTTOM = Block.box(5.0D, 0.0D, 5D, 11.0D, 2.0D, 11.0D);

    public LongChochinBlock(Properties p_49795_) {
        super(p_49795_);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return Shapes.or(SHAPE_BOTTOM,SHAPE_CENTER,SHAPE_TOP);
    }
}
