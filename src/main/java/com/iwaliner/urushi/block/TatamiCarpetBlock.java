package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class TatamiCarpetBlock extends TwoDirectionShapedBlock{
    public static final BooleanProperty FLIP = BooleanProperty.create("flip");
    public TatamiCarpetBlock(double d1, double d2, double d3, double d4, double d5, double d6, boolean canSurvive, Properties p_i48377_1_) {
        super(d1, d2, d3, d4, d5, d6,canSurvive, p_i48377_1_);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,FLIP);
    }
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level=context.getLevel();
        BlockPos pos=context.getClickedPos();
       BlockState state= this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
            if(northState.getBlock().equals(state.getBlock())&&northState.getValue(FACING)==direction){
                flag=!northState.getValue(FLIP);
            }else if(southState.getBlock().equals(state.getBlock())&&southState.getValue(FACING)== direction){
                flag=!southState.getValue(FLIP);
            }
        }
       /* if(context.getHorizontalDirection()==Direction.NORTH||context.getHorizontalDirection()==Direction.EAST){
            state= state.setValue(FACING, context.getHorizontalDirection());
            return state.setValue(FLIP,!flag);
        }else{*/
            return state.setValue(FLIP,flag);
       // }
    }
}
