package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class TatamiBlock extends RotatedPillarBlock {
    public static final BooleanProperty FLIP = BooleanProperty.create("flip");
    public TatamiBlock(Properties p_55926_) {
        super(p_55926_);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(AXIS,FLIP);
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
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag=false;
        Level level=context.getLevel();
        BlockPos pos=context.getClickedPos();
        Direction direction=context.getClickedFace();
        Direction.Axis axis=direction.getAxis();
        if(direction==Direction.NORTH||direction==Direction.EAST||direction==Direction.UP){
            flag=true;
        }
        if(axis== Direction.Axis.X){
            BlockState eastState=level.getBlockState(pos.east());
            BlockState westState=level.getBlockState(pos.west());
            if(eastState.getBlock().equals(this)&&eastState.getValue(AXIS)== Direction.Axis.X){
                flag=!eastState.getValue(FLIP);
            }else if(westState.getBlock().equals(this)&&westState.getValue(AXIS)== Direction.Axis.X){
                flag=!westState.getValue(FLIP);
            }
        }else if(axis== Direction.Axis.Z){
            BlockState northState=level.getBlockState(pos.north());
            BlockState southState=level.getBlockState(pos.south());
            if(northState.getBlock().equals(this)&&northState.getValue(AXIS)== Direction.Axis.Z){
                flag=!northState.getValue(FLIP);
            }else if(southState.getBlock().equals(this)&&southState.getValue(AXIS)== Direction.Axis.Z){
                flag=!southState.getValue(FLIP);
            }
        }else if(axis== Direction.Axis.Y){
            BlockState aboveState=level.getBlockState(pos.above());
            BlockState belowState=level.getBlockState(pos.below());
            if(aboveState.getBlock().equals(this)&&aboveState.getValue(AXIS)== Direction.Axis.Y){
                flag=!aboveState.getValue(FLIP);
            }else if(belowState.getBlock().equals(this)&&belowState.getValue(AXIS)== Direction.Axis.Y){
                flag=!belowState.getValue(FLIP);
            }
        }

        return this.defaultBlockState().setValue(AXIS,axis).setValue(FLIP,flag);
    }
  /*  @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean b) {
        this.updateShape(state,Direction.NORTH,level.getBlockState(pos2),level,pos,pos2);
        super.neighborChanged(state, level, pos, block, pos2, b);
    }
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
        if(state.getBlock() instanceof TatamiBlock){
            boolean flip=state.getValue(FLIP);
            BlockState finalState=state;
            if(state.getValue(RotatedPillarBlock.AXIS)== Direction.Axis.X){
                BlockState eastState=level.getBlockState(pos.east());
                BlockState westState=level.getBlockState(pos.west());
                if(eastState.getBlock().equals(state.getBlock())&&eastState.getValue(AXIS)== Direction.Axis.X){
                    if(flip==eastState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }else if(westState.getBlock().equals(state.getBlock())&&westState.getValue(AXIS)== Direction.Axis.X){
                    if(flip==westState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }

                if(((!(eastState.getBlock().equals(state.getBlock()))||eastState.getBlock().equals(state.getBlock())&&eastState.getValue(AXIS)!=finalState.getValue(AXIS))&&finalState.getValue(FLIP))||((!(westState.getBlock().equals(state.getBlock()))||westState.getBlock().equals(state.getBlock())&&westState.getValue(AXIS)!=finalState.getValue(AXIS))&&!finalState.getValue(FLIP))){
                    finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                }
            }else if(state.getValue(RotatedPillarBlock.AXIS)== Direction.Axis.Z){
                BlockState northState=level.getBlockState(pos.north());
                BlockState southState=level.getBlockState(pos.south());
                if(northState.getBlock().equals(state.getBlock())&&northState.getValue(AXIS)== Direction.Axis.Z){
                    if(flip==northState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }else if(southState.getBlock().equals(state.getBlock())&&southState.getValue(AXIS)== Direction.Axis.Z){
                    if(flip==southState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }

                if(((!(northState.getBlock().equals(state.getBlock()))||northState.getBlock().equals(state.getBlock())&&northState.getValue(AXIS)!=finalState.getValue(AXIS))&&finalState.getValue(FLIP))||((!(southState.getBlock().equals(state.getBlock()))||southState.getBlock().equals(state.getBlock())&&southState.getValue(AXIS)!=finalState.getValue(AXIS))&&!finalState.getValue(FLIP))){
                    finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                }
            }else if(state.getValue(RotatedPillarBlock.AXIS)== Direction.Axis.Y){
                BlockState aboveState=level.getBlockState(pos.above());
                BlockState belowState=level.getBlockState(pos.below());
                if(aboveState.getBlock().equals(state.getBlock())&&aboveState.getValue(AXIS)== Direction.Axis.Y){
                    if(flip==aboveState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }else if(belowState.getBlock().equals(state.getBlock())&&belowState.getValue(AXIS)== Direction.Axis.Y){
                    if(flip==belowState.getValue(FLIP)){
                        finalState= state.setValue(FLIP,!flip);
                    }
                }

                if(((!(aboveState.getBlock().equals(state.getBlock()))||aboveState.getBlock().equals(state.getBlock())&&aboveState.getValue(AXIS)!=finalState.getValue(AXIS))&&finalState.getValue(FLIP))||((!(belowState.getBlock().equals(state.getBlock()))||belowState.getBlock().equals(state.getBlock())&&belowState.getValue(AXIS)!=finalState.getValue(AXIS))&&!finalState.getValue(FLIP))){
                    finalState=finalState.setValue(FLIP,!finalState.getValue(FLIP));
                }
            }
            return finalState;
        }
        return state;
    }
*/

    }
