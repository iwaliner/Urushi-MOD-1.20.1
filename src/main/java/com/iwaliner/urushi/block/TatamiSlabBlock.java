package com.iwaliner.urushi.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class TatamiSlabBlock extends HorizonalRotateSlabBlock {
    public static final BooleanProperty FLIP = BooleanProperty.create("flip");
    public TatamiSlabBlock(Properties p_i48377_1_) {
        super(p_i48377_1_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FLIP,false));
    }
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,TYPE,WATERLOGGED,FLIP);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level=context.getLevel();
        BlockPos pos=context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(pos);
        BlockState blockstate1 = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)).setValue(FACING,context.getHorizontalDirection().getOpposite());
        BlockState state= context.getClickedFace() != Direction.DOWN && (context.getClickedFace() == Direction.UP || !(context.getClickLocation().y - (double)pos.getY() > 0.5D)) ? blockstate1 : blockstate1.setValue(TYPE, SlabType.TOP);
        boolean flag=false;
        Direction direction=state.getValue(FACING);
        if(direction.getAxis()== Direction.Axis.X){
            BlockState eastState=level.getBlockState(pos.east());
            BlockState westState=level.getBlockState(pos.west());
            if(eastState.getBlock().equals(state.getBlock())&&eastState.getValue(FACING)== direction){
                flag=!eastState.getValue(FLIP);
            }else if(westState.getBlock().equals(state.getBlock())&&westState.getValue(FACING)== direction){
                flag=!westState.getValue(FLIP);
            }
        }else if(direction.getAxis()== Direction.Axis.Z){
            BlockState northState=level.getBlockState(pos.north());
            BlockState southState=level.getBlockState(pos.south());
            if(northState.getBlock().equals(state.getBlock())&&northState.getValue(FACING)== direction){
                flag=!northState.getValue(FLIP);
            }else if(southState.getBlock().equals(state.getBlock())&&southState.getValue(FACING)== direction){
                flag=!southState.getValue(FLIP);
            }
        }
       /* if(context.getHorizontalDirection()==Direction.NORTH||context.getHorizontalDirection()==Direction.EAST){
            state= state.setValue(FACING, context.getHorizontalDirection());
            return state.setValue(FLIP, !flag);
        }else {*/
            return state.setValue(FLIP, flag);
       // }
    }
}
