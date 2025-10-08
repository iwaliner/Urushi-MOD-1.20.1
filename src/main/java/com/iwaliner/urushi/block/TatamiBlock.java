package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
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
    public BlockState rotate(BlockState p_55930_, Rotation p_55931_) {
        return rotatePillar(p_55930_, p_55931_);
    }

    public static BlockState rotatePillar(BlockState state, Rotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch ((Direction.Axis)state.getValue(AXIS)) {
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z).setValue(FLIP,rotation==Rotation.CLOCKWISE_90? !state.getValue(FLIP) : state.getValue(FLIP));
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X).setValue(FLIP,rotation==Rotation.COUNTERCLOCKWISE_90? !state.getValue(FLIP) : state.getValue(FLIP));
                    default:
                        return state;
                }
            default:
                return state.setValue(FLIP,rotation==Rotation.CLOCKWISE_180? !state.getValue(FLIP) : state.getValue(FLIP));
        }
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
    }
