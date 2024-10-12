package com.iwaliner.urushi.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import javax.annotation.Nullable;

public class WideNorenBlock extends NorenBlock{
    public static final BooleanProperty VARIANT = BooleanProperty.create("variant");
    public WideNorenBlock(Properties p_i48377_1_) {
        super(p_i48377_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(VARIANT, Boolean.valueOf(false)));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,VARIANT);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction.getCounterClockWise());
        Level level = context.getLevel();
        return level.getBlockState(blockpos1).canBeReplaced(context) && level.getWorldBorder().isWithinBounds(blockpos1) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity livingEntity, ItemStack stack) {
        super.setPlacedBy(level, pos, state, livingEntity, stack);
        if (!level.isClientSide) {
            BlockPos blockpos = pos.relative(state.getValue(FACING).getCounterClockWise());
            level.setBlock(blockpos, state.setValue(VARIANT, true), 3);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);
        }

    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
        if(state.getBlock() instanceof WideNorenBlock) {
            Direction facing=state.getValue(FACING);
            if(direction!=facing.UP&&direction!=facing.DOWN) {
                if (state.getValue(VARIANT) == Boolean.valueOf(true)) {
                    BlockState nextBlockState = world.getBlockState(pos.relative(facing.getClockWise()));
                    if (nextBlockState.getBlock() instanceof WideNorenBlock) {
                        return super.updateShape(state, direction, state2, world, pos, pos2);
                    } else {
                        return Blocks.AIR.defaultBlockState();
                    }
                } else {
                    BlockState nextBlockState = world.getBlockState(pos.relative(facing.getCounterClockWise()));
                    if (nextBlockState.getBlock() instanceof WideNorenBlock) {
                        return super.updateShape(state, direction, state2, world, pos, pos2);
                    } else {
                        return Blocks.AIR.defaultBlockState();
                    }
                }
            }
        }
        return super.updateShape(state, direction, state2, world, pos, pos2);
    }


}
