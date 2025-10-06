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
        if(direction==Direction.NORTH||direction==Direction.EAST||direction==Direction.UP){
            flag=true;
        }
        if(direction.getAxis()== Direction.Axis.X){
            BlockState eastState=level.getBlockState(pos.east());
            BlockState westState=level.getBlockState(pos.west());
            if(eastState.getBlock() instanceof TatamiSlabBlock&&eastState.getValue(FACING).getAxis()== Direction.Axis.X){
                flag=!eastState.getValue(FLIP);
            }else if(westState.getBlock() instanceof TatamiSlabBlock&&westState.getValue(FACING).getAxis()== Direction.Axis.X){
                flag=!westState.getValue(FLIP);
            }
        }else if(direction.getAxis()== Direction.Axis.Z){
            BlockState northState=level.getBlockState(pos.north());
            BlockState southState=level.getBlockState(pos.south());
            if(northState.getBlock() instanceof TatamiSlabBlock&&northState.getValue(FACING).getAxis()== Direction.Axis.Z){
                flag=!northState.getValue(FLIP);
            }else if(southState.getBlock() instanceof TatamiSlabBlock&&southState.getValue(FACING).getAxis()== Direction.Axis.Z){
                flag=!southState.getValue(FLIP);
            }
        }
        return state.setValue(FLIP,flag);
    }
    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean b) {
        this.updateShape(state,Direction.NORTH,level.getBlockState(pos2),level,pos,pos2);
        super.neighborChanged(state, level, pos, block, pos2, b);
    }
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
        if (state.getBlock() instanceof TatamiSlabBlock) {
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
            BlockState finalState = super.updateShape(state, facing, state2, level, pos, pos2);
            boolean flip=finalState.getValue(FLIP);
            if(state.getValue(FACING).getAxis()== Direction.Axis.X){
                BlockState eastState=level.getBlockState(pos.east());
                BlockState westState=level.getBlockState(pos.west());
                if(eastState.getBlock() instanceof TatamiSlabBlock&&eastState.getValue(FACING).getAxis()== Direction.Axis.X){
                    if(flip==eastState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }else if(westState.getBlock() instanceof TatamiSlabBlock&&westState.getValue(FACING).getAxis()== Direction.Axis.X){
                    if(flip==westState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }

                if(((!(eastState.getBlock() instanceof TatamiSlabBlock)||eastState.getBlock()instanceof TatamiSlabBlock&&eastState.getValue(FACING).getAxis()!=finalState.getValue(FACING).getAxis())&&finalState.getValue(FLIP))||((!(westState.getBlock() instanceof TatamiSlabBlock)||westState.getBlock()instanceof TatamiSlabBlock&&westState.getValue(FACING).getAxis()!=finalState.getValue(FACING).getAxis())&&!finalState.getValue(FLIP))){
                    finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                }
            }else if(state.getValue(FACING).getAxis()== Direction.Axis.Z){
                BlockState northState=level.getBlockState(pos.north());
                BlockState southState=level.getBlockState(pos.south());
                if(northState.getBlock() instanceof TatamiSlabBlock&&northState.getValue(FACING).getAxis()== Direction.Axis.Z){
                    if(flip==northState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }else if(southState.getBlock() instanceof TatamiSlabBlock&&southState.getValue(FACING).getAxis()== Direction.Axis.Z){
                    if(flip==southState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }

                if(((!(northState.getBlock() instanceof TatamiSlabBlock)||northState.getBlock()instanceof TatamiSlabBlock&&northState.getValue(FACING).getAxis()!=finalState.getValue(FACING).getAxis())&&finalState.getValue(FLIP))||((!(southState.getBlock() instanceof TatamiSlabBlock)||southState.getBlock()instanceof TatamiSlabBlock&&southState.getValue(FACING).getAxis()!=finalState.getValue(FACING).getAxis())&&!finalState.getValue(FLIP))){
                    finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                }
            }
            return finalState;
        }


        return super.updateShape(state, facing, state2, level, pos, pos2);
    }



}
