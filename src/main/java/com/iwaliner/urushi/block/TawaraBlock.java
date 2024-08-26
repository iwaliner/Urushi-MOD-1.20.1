package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TawaraBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");
    protected static final VoxelShape BOX_N = Block.box(8D, 0.0D, 0.0D, 24.0D, 16.0D, 16.0D);
    protected static final VoxelShape BOX_S = Block.box(-8D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape BOX_E = Block.box(0.0D, 0.0D, 8D, 16.0D, 16.0D, 24.0D);
    protected static final VoxelShape BOX_W = Block.box(0.0D, 0.0D, -8D, 16.0D, 16.0D, 8.0D);
    public TawaraBlock(Properties p_i48377_1_) {
        super(p_i48377_1_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(TRIGGERED, Boolean.valueOf(false)));

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if(state.getValue(TRIGGERED)){
            switch (state.getValue(FACING)){
                case NORTH -> {
                    return BOX_N;
                }
                case SOUTH -> {
                    return BOX_S;
                }
                case EAST -> {
                    return BOX_E;
                }
                case WEST -> {
                    return BOX_W;
                }
            }
        }
        return  Shapes.block();
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
        p_49915_.add(FACING,TRIGGERED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState underState=context.getLevel().getBlockState(context.getClickedPos().below());
        if(underState.getBlock() instanceof TawaraBlock && !underState.getValue(TRIGGERED)){
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(TRIGGERED,Boolean.TRUE);

        }
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
