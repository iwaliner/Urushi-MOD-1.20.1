package com.iwaliner.urushi.block;


import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class HorizontalBarsBlock extends HorizonalRotateBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    private static final VoxelShape BASE_NS = Block.box(7D, 7.0D, 0D, 9D, 9D, 16D);
    private static final VoxelShape BASE_EW = Block.box(0D, 7.0D, 7D, 16D, 9D, 9D);
    private static final VoxelShape SIDE_NS_EAST = Block.box(9D, 7D, 0D, 16D, 9D, 16D);
    private static final VoxelShape SIDE_NS_WEST = Block.box(0D, 7D, 0D, 7D, 9D, 16D);
    private static final VoxelShape SIDE_NS_UP = Block.box(7D, 9D, 0D, 9D, 16D, 16D);
    private static final VoxelShape SIDE_NS_DOWN = Block.box(7D, 0D, 0D, 9D, 7D, 16D);
    private static final VoxelShape SIDE_EW_NORTH = Block.box(0D, 7D, 0D, 16D, 9D, 7D);
    private static final VoxelShape SIDE_EW_SOUTH = Block.box(0D, 7D, 9D, 16D, 9D, 16D);
    private static final VoxelShape SIDE_EW_UP = Block.box(0D, 9D, 7D, 16D, 16D, 9D);
    private static final VoxelShape SIDE_EW_DOWN = Block.box(0D, 0D, 7D, 16D, 7D, 9D);

    public HorizontalBarsBlock(Properties p_i48377_1_) {
        super(p_i48377_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED,false).setValue(NORTH,false).setValue(EAST,false).setValue(SOUTH,false).setValue(WEST,false).setValue(UP,false).setValue(DOWN,false));

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if (state.getValue(FACING).getAxis() == Direction.Axis.Z) {
            return Shapes.or(BASE_NS, !state.getValue(EAST) ? BASE_NS : SIDE_NS_EAST, !state.getValue(WEST) ? BASE_NS : SIDE_NS_WEST, !state.getValue(UP) ? BASE_NS : SIDE_NS_UP, !state.getValue(DOWN) ? BASE_NS : SIDE_NS_DOWN);
        } else {


            return Shapes.or(BASE_EW, !state.getValue(NORTH) ? BASE_EW : SIDE_EW_NORTH, !state.getValue(SOUTH) ? BASE_EW : SIDE_EW_SOUTH, !state.getValue(UP) ? BASE_EW : SIDE_EW_UP, !state.getValue(DOWN) ? BASE_EW : SIDE_EW_DOWN);
        }
    }
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }



    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.isFireSource((LevelReader) level,pos,direction);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return state.isFireSource((LevelReader) level,pos,direction)?60:0;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(NORTH,EAST,SOUTH,WEST,UP,DOWN,WATERLOGGED,FACING);
    }
    public boolean connectsToByFacing(BlockState thisState, Direction direction, LevelAccessor world, BlockPos pos) {

        BlockState nextState=world.getBlockState(pos.relative(direction));

        return nextState.getBlock() instanceof HorizontalBarsBlock;
    }
    private BooleanProperty getProperty(Direction direction){
        switch (direction){
            case NORTH : return NORTH;
            case EAST : return EAST;
            case SOUTH : return SOUTH;
            case WEST : return WEST;
            case UP : return UP;
            case DOWN : return DOWN;
        }
        return null;
    }
    @Override
    public BlockState updateShape(BlockState state1, Direction facing, BlockState state2, LevelAccessor world, BlockPos pos1, BlockPos pos2) {
        VoxelShape shape = state2.getCollisionShape(world, pos2).optimize();
        if(!shape.isEmpty()) {
            return super.updateShape(state1, facing, state2, world, pos1, pos2).setValue(getProperty(facing), true);
        }
        return super.updateShape(state1, facing, state2, world, pos1, pos2);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState state = iblockreader.getBlockState(blockpos);
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());


        Direction facing=context.getClickedFace().getOpposite();
        BooleanProperty d1=NORTH;
        switch (facing){
            case NORTH : d1=NORTH; break;
            case EAST : d1=EAST; break;
            case SOUTH: d1=SOUTH; break;
            case WEST: d1=WEST;break;
            case UP: d1=UP;break;
            case DOWN: d1=DOWN;break;

        }

        return this.defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER))
                .setValue(FACING, context.getHorizontalDirection())
                .setValue(NORTH, this.connectsToByFacing(state, Direction.NORTH, iblockreader, blockpos))
                .setValue(SOUTH, this.connectsToByFacing(state, Direction.SOUTH, iblockreader, blockpos))
                .setValue(WEST, this.connectsToByFacing(state, Direction.WEST, iblockreader, blockpos))
                .setValue(EAST, this.connectsToByFacing(state, Direction.EAST, iblockreader, blockpos))
                .setValue(UP, this.connectsToByFacing(state, Direction.UP, iblockreader, blockpos))
                .setValue(DOWN, this.connectsToByFacing(state, Direction.DOWN, iblockreader, blockpos)).setValue(d1,true);

    }

}
