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
        /*if(direction==Direction.NORTH||direction==Direction.EAST||direction==Direction.UP){
            flag=true;
        }*/
        if(direction.getAxis()== Direction.Axis.X){
            BlockState eastState=level.getBlockState(pos.east());
            BlockState westState=level.getBlockState(pos.west());
            if(eastState.getBlock() instanceof TatamiCarpetBlock&&eastState.getValue(FACING).getAxis()== Direction.Axis.X){
                flag=!eastState.getValue(FLIP);
            }else if(westState.getBlock() instanceof TatamiCarpetBlock&&westState.getValue(FACING).getAxis()== Direction.Axis.X){
                flag=!westState.getValue(FLIP);
            }
        }else if(direction.getAxis()== Direction.Axis.Z){
            BlockState northState=level.getBlockState(pos.north());
            BlockState southState=level.getBlockState(pos.south());
            if(northState.getBlock() instanceof TatamiCarpetBlock&&northState.getValue(FACING).getAxis()== Direction.Axis.Z){
                flag=!northState.getValue(FLIP);
            }else if(southState.getBlock() instanceof TatamiCarpetBlock&&southState.getValue(FACING).getAxis()== Direction.Axis.Z){
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
        BlockState finalState = super.updateShape(state, facing, state2, level, pos, pos2);
        if (finalState.getBlock() instanceof TatamiCarpetBlock) {
             boolean flip=finalState.getValue(FLIP);
            if(finalState.getValue(FACING).getAxis()== Direction.Axis.X){
                BlockState eastState=level.getBlockState(pos.east());
                BlockState westState=level.getBlockState(pos.west());
                if(eastState.getBlock() instanceof TatamiCarpetBlock&&eastState.getValue(FACING).getAxis()== Direction.Axis.X){
                    if(flip==eastState.getValue(FLIP)){
                        finalState= finalState.setValue(FLIP,!flip);
                    }
                }else if(westState.getBlock() instanceof TatamiCarpetBlock&&westState.getValue(FACING).getAxis()== Direction.Axis.X){
                    if(flip==westState.getValue(FLIP)){
                        finalState= finalState.setValue(FLIP,!flip);
                    }
                }else if(((!(eastState.getBlock() instanceof TatamiCarpetBlock)||eastState.getBlock()instanceof TatamiCarpetBlock&&eastState.getValue(FACING).getAxis()!=finalState.getValue(FACING).getAxis())&&finalState.getValue(FLIP))||((!(westState.getBlock() instanceof TatamiCarpetBlock)||westState.getBlock()instanceof TatamiCarpetBlock&&westState.getValue(FACING).getAxis()!=finalState.getValue(FACING).getAxis())&&!finalState.getValue(FLIP))){
                    finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                    /*if(finalState.getValue(FACING)==Direction.NORTH||finalState.getValue(FACING)==Direction.EAST||finalState.getValue(FACING)==Direction.UP){
                        finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                    }*/
                }
            }else if(finalState.getValue(FACING).getAxis()== Direction.Axis.Z){
                BlockState northState=level.getBlockState(pos.north());
                BlockState southState=level.getBlockState(pos.south());
                if(northState.getBlock() instanceof TatamiCarpetBlock&&northState.getValue(FACING).getAxis()== Direction.Axis.Z){
                    if(flip==northState.getValue(FLIP)){
                        finalState= finalState.setValue(FLIP,!flip);
                    }
                }else if(southState.getBlock() instanceof TatamiCarpetBlock&&southState.getValue(FACING).getAxis()== Direction.Axis.Z){
                    if(flip==southState.getValue(FLIP)){
                        finalState= finalState.setValue(FLIP,!flip);
                    }
                }else if(((!(northState.getBlock() instanceof TatamiCarpetBlock)||northState.getBlock()instanceof TatamiCarpetBlock&&northState.getValue(FACING).getAxis()!=finalState.getValue(FACING).getAxis())&&finalState.getValue(FLIP))||((!(southState.getBlock() instanceof TatamiCarpetBlock)||southState.getBlock()instanceof TatamiCarpetBlock&&southState.getValue(FACING).getAxis()!=finalState.getValue(FACING).getAxis())&&!finalState.getValue(FLIP))){
                    finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                    /*if(finalState.getValue(FACING)==Direction.NORTH||finalState.getValue(FACING)==Direction.EAST||finalState.getValue(FACING)==Direction.UP){
                        finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                    }*/
                }
            }
            /*if(finalState.getValue(FACING)==Direction.NORTH||finalState.getValue(FACING)==Direction.EAST||finalState.getValue(FACING)==Direction.UP){
                finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
            }*/
            return finalState;
        }


        return super.updateShape(state, facing, state2, level, pos, pos2);
    }
}
