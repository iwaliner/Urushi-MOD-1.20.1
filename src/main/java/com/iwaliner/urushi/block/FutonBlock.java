package com.iwaliner.urushi.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FutonBlock extends BedBlock {
    protected static final VoxelShape BOX = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2D, 16.0D);
    protected static final VoxelShape HEAD_BASE = Block.box(1D, 0.0D, 1D, 15.0D, 2D, 15.0D);
    public FutonBlock(DyeColor p_i48442_1_, Properties p_i48442_2_) {
        super(p_i48442_1_, p_i48442_2_);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_49548_, BlockPos p_49549_, CollisionContext p_49550_) {
        if(state.getValue(PART)== BedPart.HEAD){
            return HEAD_BASE;
        }else{
            return BOX;
        }
    }
    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
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
