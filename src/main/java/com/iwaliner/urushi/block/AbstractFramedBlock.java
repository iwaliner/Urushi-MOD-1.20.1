package com.iwaliner.urushi.block;

import com.google.common.collect.Maps;

import com.iwaliner.urushi.ClientSetUp;
import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.packet.ClientKeyPressPacket;
import com.iwaliner.urushi.packet.ServerKeyPressPacket;
import com.iwaliner.urushi.util.UrushiUtils;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;


import java.util.List;
import java.util.Map;


public class AbstractFramedBlock extends Block {
    private static final Direction[] DIRECTIONS = Direction.values();
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static final BooleanProperty VARIANT = BooleanProperty.create("variant");
    public static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = Util.make(Maps.newEnumMap(Direction.class), (p_203421_0_) -> {
        p_203421_0_.put(Direction.NORTH, NORTH);
        p_203421_0_.put(Direction.EAST, EAST);
        p_203421_0_.put(Direction.SOUTH, SOUTH);
        p_203421_0_.put(Direction.WEST, WEST);
        p_203421_0_.put(Direction.UP, UP);
        p_203421_0_.put(Direction.DOWN, DOWN);
    });


    public AbstractFramedBlock( BlockBehaviour.Properties p_i48355_2_) {
        super(p_i48355_2_);
    }


    public boolean connectsTo(BlockState thisState,BlockState nextState) {
        if(nextState.getBlock() instanceof AbstractFramedBlock&&thisState.getBlock() instanceof AbstractFramedBlock) {
            if(nextState.getValue(VARIANT)==thisState.getValue(VARIANT)) {
                return thisState.getBlock() == nextState.getBlock();
            }else{
                return false;
            }
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

        if(world.getBlockState(offsetPos).getBlock() instanceof AbstractFramedBlock) {
            if(world.getBlockState(offsetPos).getValue(VARIANT)==thisState.getValue(VARIANT)) {
                return thisState.getBlock() == world.getBlockState(offsetPos).getBlock();
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        switch(direction) {
            case CLOCKWISE_180:
                return state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
            case CLOCKWISE_90:
                return state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
            default:
                return state;
        }
    }

    @Override
    public BlockState mirror(BlockState p_185471_1_, Mirror p_60529_) {
        switch(p_60529_) {
            case LEFT_RIGHT:
                return p_185471_1_.setValue(NORTH, p_185471_1_.getValue(SOUTH)).setValue(SOUTH, p_185471_1_.getValue(NORTH));
            case FRONT_BACK:
                return p_185471_1_.setValue(EAST, p_185471_1_.getValue(WEST)).setValue(WEST, p_185471_1_.getValue(EAST));
            default:
                return super.mirror(p_185471_1_, p_60529_);
        }
    }

    @Override
    public BlockState updateShape(BlockState state1, Direction facing, BlockState state2, LevelAccessor world, BlockPos pos1, BlockPos pos2) {
        return state1.setValue(NORTH, Boolean.valueOf(this.connectsToByFacing(state1,Direction.NORTH,world,pos1)))
                .setValue(EAST, Boolean.valueOf(this.connectsToByFacing(state1,Direction.EAST,world,pos1)))
                .setValue(SOUTH, Boolean.valueOf(this.connectsToByFacing(state1,Direction.SOUTH,world,pos1)))
                .setValue(WEST, Boolean.valueOf(this.connectsToByFacing(state1,Direction.WEST,world,pos1)))
                .setValue(UP, Boolean.valueOf(this.connectsToByFacing(state1,Direction.UP,world,pos1)))
                .setValue(DOWN, Boolean.valueOf(this.connectsToByFacing(state1,Direction.DOWN,world,pos1)));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean b) {
        this.updateShape(state,Direction.NORTH,level.getBlockState(pos2),level,pos,pos2);
        super.neighborChanged(state, level, pos, block, pos2, b);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
        BlockState thisState=level.getBlockState(pos);
        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.east();
        BlockPos blockpos3 = pos.south();
        BlockPos blockpos4 = pos.west();
        BlockPos blockpos5 = pos.above();
        BlockPos blockpos6 = pos.below();
        BlockState nState = level.getBlockState(blockpos1);
        BlockState eState = level.getBlockState(blockpos2);
        BlockState sState = level.getBlockState(blockpos3);
        BlockState wState = level.getBlockState(blockpos4);
        BlockState aState = level.getBlockState(blockpos5);
        BlockState bState = level.getBlockState(blockpos6);
        BlockState newState=this.defaultBlockState().setValue(NORTH, Boolean.valueOf(this.connectsTo(thisState, nState)))
                .setValue(SOUTH, Boolean.valueOf(this.connectsTo(thisState, sState)))
                .setValue(WEST, Boolean.valueOf(this.connectsTo(thisState, wState)))
                .setValue(EAST, Boolean.valueOf(this.connectsTo(thisState, eState)))
                .setValue(UP, Boolean.valueOf(this.connectsTo(thisState, aState)))
                .setValue(DOWN, Boolean.valueOf(this.connectsTo(thisState, bState)))
                .setValue(VARIANT,state.getValue(VARIANT) )
                ;
        level.setBlock(pos,newState,2);
       // level.blockUpdated(pos,state.getBlock());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(NORTH, EAST, WEST, SOUTH,UP,DOWN,VARIANT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level iblockreader =context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState thisState=iblockreader.getBlockState(blockpos);
        BlockPos blockpos1 = blockpos.north();
        BlockPos blockpos2 = blockpos.east();
        BlockPos blockpos3 = blockpos.south();
        BlockPos blockpos4 = blockpos.west();
        BlockPos blockpos5 = blockpos.above();
        BlockPos blockpos6 = blockpos.below();
        BlockState nState = iblockreader.getBlockState(blockpos1);
        BlockState eState = iblockreader.getBlockState(blockpos2);
        BlockState sState = iblockreader.getBlockState(blockpos3);
        BlockState wState = iblockreader.getBlockState(blockpos4);
        BlockState aState = iblockreader.getBlockState(blockpos5);
        BlockState bState = iblockreader.getBlockState(blockpos6);

try {

    BlockState newState=this.defaultBlockState().setValue(NORTH, Boolean.valueOf(this.connectsTo(thisState, nState)))
            .setValue(SOUTH, Boolean.valueOf(this.connectsTo(thisState, sState)))
            .setValue(WEST, Boolean.valueOf(this.connectsTo(thisState, wState)))
            .setValue(EAST, Boolean.valueOf(this.connectsTo(thisState, eState)))
            .setValue(UP, Boolean.valueOf(this.connectsTo(thisState, aState)))
            .setValue(DOWN, Boolean.valueOf(this.connectsTo(thisState, bState)))
            .setValue(VARIANT,
                    ClientSetUp.connectionKey.isDown() )
            ;

    return newState;
}catch (Exception e){
    BlockState newState=this.defaultBlockState().setValue(NORTH, Boolean.valueOf(this.connectsTo(thisState, nState)))
            .setValue(SOUTH, Boolean.valueOf(this.connectsTo(thisState, sState)))
            .setValue(WEST, Boolean.valueOf(this.connectsTo(thisState, wState)))
            .setValue(EAST, Boolean.valueOf(this.connectsTo(thisState, eState)))
            .setValue(UP, Boolean.valueOf(this.connectsTo(thisState, aState)))
            .setValue(DOWN, Boolean.valueOf(this.connectsTo(thisState, bState)))
            .setValue(VARIANT, context.getPlayer().isSuppressingBounce())
            ;

    return newState;
}



    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean b) {
        this.updateShape(state,Direction.NORTH,state2,level,pos,pos);

    }
}
