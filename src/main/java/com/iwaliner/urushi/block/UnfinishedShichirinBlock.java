package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class UnfinishedShichirinBlock extends Block {
    private static final VoxelShape UNDER = Block.box(3D, 0D, 3D, 13D, 12D, 13D);
    private static final VoxelShape UPPER = Block.box(1D, 12.0D, 1D, 15D, 15D, 15D);
    private static final VoxelShape SHAPE = Shapes.or(UNDER, UPPER);

    public UnfinishedShichirinBlock(Properties p_49795_) {
        super(p_49795_);
    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }
}
