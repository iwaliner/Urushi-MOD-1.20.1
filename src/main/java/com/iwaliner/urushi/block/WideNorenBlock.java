package com.iwaliner.urushi.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

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

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction.getCounterClockWise());
        if(context.getLevel().getBlockState(blockpos1).canBeReplaced(context) ){
            context.getLevel().setBlockAndUpdate(blockpos1,this.defaultBlockState().setValue(FACING,direction).setValue(VARIANT,Boolean.valueOf(true)));
            //context.getLevel().setBlockAndUpdate(blockpos,this.defaultBlockState().setValue(FACING,direction).setValue(VARIANT,Boolean.valueOf(false)));
            return this.defaultBlockState().setValue(FACING,direction).setValue(VARIANT,Boolean.valueOf(false));
        }else{
            return  null;
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
