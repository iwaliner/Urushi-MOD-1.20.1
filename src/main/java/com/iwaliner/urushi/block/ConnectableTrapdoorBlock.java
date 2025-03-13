package com.iwaliner.urushi.block;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Half;

import java.util.Map;

public class ConnectableTrapdoorBlock extends TrapDoorBlock {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), (p_203421_0_) -> {
        p_203421_0_.put(Direction.NORTH, NORTH);
        p_203421_0_.put(Direction.EAST, EAST);
        p_203421_0_.put(Direction.SOUTH, SOUTH);
        p_203421_0_.put(Direction.WEST, WEST);
        p_203421_0_.put(Direction.UP, UP);
        p_203421_0_.put(Direction.DOWN, DOWN);
    });
    public ConnectableTrapdoorBlock(Properties p_i48307_1_) {
        super(p_i48307_1_, BlockSetType.OAK);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)).setValue(HALF, Half.BOTTOM).setValue(POWERED, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(UP,Boolean.valueOf(false)).setValue(DOWN,Boolean.valueOf(false)).setValue(NORTH,Boolean.valueOf(false)).setValue(SOUTH,Boolean.valueOf(false)).setValue(WEST,Boolean.valueOf(false)).setValue(EAST,Boolean.valueOf(false)));

    }
    public boolean connectsTo(BlockState thisState, BlockState nextState) {
        if(nextState.getBlock() instanceof ConnectableTrapdoorBlock&&thisState.getBlock() instanceof ConnectableTrapdoorBlock) {
            return thisState.getBlock() == nextState.getBlock();
        }else{
            return false;
        }
    }

    public boolean connectsToByFacing(BlockState thisState, Direction direction, LevelAccessor world, BlockPos pos) {
        BlockPos offsetPos = pos;

        if (direction== Direction.NORTH) {
            offsetPos = pos.north();
        } else if (direction == Direction.SOUTH) {
            offsetPos = pos.south();
        } else if (direction == Direction.WEST) {
            offsetPos = pos.west();
        } else if (direction == Direction.EAST) {
            offsetPos = pos.east();
        }else if (direction == Direction.UP) {
            offsetPos = pos.above();
        }else if (direction == Direction.DOWN) {
            offsetPos = pos.below();
        }

        if(world.getBlockState(offsetPos).getBlock() instanceof ConnectableTrapdoorBlock) {
            return thisState.getBlock() == world.getBlockState(offsetPos).getBlock();
        }else{
            return false;
        }
    }



    public BlockState updateShape(BlockState state1, Direction facing, BlockState state2, LevelAccessor world, BlockPos pos1, BlockPos pos2) {
        return state1.setValue(PROPERTY_BY_DIRECTION.get(facing), Boolean.valueOf(this.connectsToByFacing(state1, facing, world, pos1)));

    }



    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState>  p_206840_1_) {
        p_206840_1_.add(FACING, OPEN, HALF, POWERED, WATERLOGGED,UP,DOWN,NORTH,SOUTH,EAST,WEST);
    }



    public BlockState getStateForPlacement(BlockPlaceContext p_196258_1_) {
        Level iblockreader = p_196258_1_.getLevel();
        BlockPos blockpos = p_196258_1_.getClickedPos();
        BlockState thisState=iblockreader.getBlockState(blockpos);
        BlockState aState = iblockreader.getBlockState(blockpos.above());
        BlockState bState = iblockreader.getBlockState(blockpos.below());
        BlockState cState = iblockreader.getBlockState(blockpos.north());
        BlockState dState = iblockreader.getBlockState(blockpos.south());
        BlockState eState = iblockreader.getBlockState(blockpos.west());
        BlockState fState = iblockreader.getBlockState(blockpos.east());

        return super.getStateForPlacement(p_196258_1_)
                .setValue(UP, Boolean.valueOf(this.connectsTo(thisState, aState)))
                .setValue(DOWN, Boolean.valueOf(this.connectsTo(thisState, bState)))
                .setValue(NORTH, Boolean.valueOf(this.connectsTo(thisState, cState)))
                .setValue(SOUTH, Boolean.valueOf(this.connectsTo(thisState, dState)))
                .setValue(WEST, Boolean.valueOf(this.connectsTo(thisState, eState)))
                .setValue(EAST, Boolean.valueOf(this.connectsTo(thisState, fState)))
                ;
    }
    public BlockState rotate(BlockState state, Rotation direction) {
        switch(direction) {
            case CLOCKWISE_180:
                return state.setValue(FACING, direction.rotate(state.getValue(FACING))).setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.setValue(FACING, direction.rotate(state.getValue(FACING))).setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.setValue(FACING, direction.rotate(state.getValue(FACING))).setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default:
                return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
        }
    }

    @Override
    public BlockState mirror(BlockState p_185471_1_, Mirror p_60529_) {
        switch(p_60529_) {
            case LEFT_RIGHT:
                return p_185471_1_.rotate(p_60529_.getRotation(p_185471_1_.getValue(FACING))).setValue(NORTH, p_185471_1_.getValue(SOUTH)).setValue(SOUTH, p_185471_1_.getValue(NORTH));
            case FRONT_BACK:
                return p_185471_1_.rotate(p_60529_.getRotation(p_185471_1_.getValue(FACING))).setValue(EAST, p_185471_1_.getValue(WEST)).setValue(WEST, p_185471_1_.getValue(EAST));
            default:
                return super.mirror(p_185471_1_, p_60529_);
        }
    }
}
