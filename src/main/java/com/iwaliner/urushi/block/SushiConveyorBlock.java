package com.iwaliner.urushi.block;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SushiConveyorBlock extends HorizonalRotateBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    private static final VoxelShape BASE = Block.box(0D, 0.0D, 0D, 16D, 13D, 16D);
    private static final VoxelShape CORNER1 = Block.box(0D, 13.0D, 0D, 2D, 15D, 2D);
    private static final VoxelShape CORNER2 = Block.box(0D, 13.0D, 14D, 2D, 15D, 16D);
    private static final VoxelShape CORNER3 = Block.box(14D, 13.0D, 0D, 16D, 15D, 2D);
    private static final VoxelShape CORNER4 = Block.box(14D, 13.0D, 14D, 16D, 15D, 16D);
    private static final VoxelShape END_WEST = Block.box(0D, 13D, 2D, 2D, 15D, 14D);
    private static final VoxelShape END_EAST = Block.box(14D, 13D, 2D, 16D, 15D, 14D);
    private static final VoxelShape END_NORTH = Block.box(2D, 13D, 0D, 14D, 15D, 2D);
    private static final VoxelShape END_SOUTH = Block.box(2D, 13D, 14D, 14D, 15D, 16D);
    private static final VoxelShape MATERIAL = Shapes.or(BASE, CORNER1, CORNER2,CORNER3,CORNER4);


    public SushiConveyorBlock( Properties p_i48377_1_) {
        super(p_i48377_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED,false).setValue(NORTH,false).setValue(EAST,false).setValue(SOUTH,false).setValue(WEST,false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
          return Shapes.or(MATERIAL,state.getValue(NORTH)? MATERIAL:END_NORTH,state.getValue(EAST)? MATERIAL:END_EAST,state.getValue(SOUTH)? MATERIAL:END_SOUTH,state.getValue(WEST)? MATERIAL:END_WEST);


    }
    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(NORTH,EAST,SOUTH,WEST,WATERLOGGED,FACING);
    }

    @Override
    public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        return true;
    }
    private boolean isConveyor(BlockState state){
        return state.getBlock() instanceof SushiConveyorBlock;
    }

    public boolean connectsToByFacing(BlockState thisState, Direction direction, LevelAccessor world, BlockPos pos) {

        BlockState nextState=world.getBlockState(pos.relative(direction));
        if(isConveyor(nextState)) {
            /*Direction nextStateFacing=nextState.getValue(OldGrooveBlock.FACING);
            if(nextStateFacing==direction||nextStateFacing.getOpposite()==direction) {
                return true;
            }else{
                return false;
            }*/
            return true;
        }else{
            return false;
        }
    }
    @Override
    public BlockState updateShape(BlockState state1, Direction facing, BlockState state2, LevelAccessor world, BlockPos pos1, BlockPos pos2) {

        return super.updateShape(state1, facing, state2, world, pos1, pos2).setValue(NORTH, this.connectsToByFacing(state1, Direction.NORTH, world, pos1))
                .setValue(EAST, this.connectsToByFacing(state1, Direction.EAST, world, pos1))
                .setValue(SOUTH, this.connectsToByFacing(state1, Direction.SOUTH, world, pos1))
                .setValue(WEST, this.connectsToByFacing(state1, Direction.WEST, world, pos1));

    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level iblockreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState thisState = iblockreader.getBlockState(blockpos);


        return this.defaultBlockState().setValue(NORTH, this.connectsToByFacing(thisState, Direction.NORTH, iblockreader, blockpos))
                .setValue(SOUTH, this.connectsToByFacing(thisState, Direction.SOUTH, iblockreader, blockpos))
                .setValue(WEST, this.connectsToByFacing(thisState, Direction.WEST, iblockreader, blockpos))
                .setValue(EAST, this.connectsToByFacing(thisState, Direction.EAST, iblockreader, blockpos))
                .setValue(FACING,context.getHorizontalDirection().getOpposite())
                ;

    }
    public BlockState rotate(BlockState p_52341_, Rotation p_52342_) {
        switch(p_52342_) {
            case CLOCKWISE_180:
                return p_52341_.setValue(NORTH, p_52341_.getValue(SOUTH)).setValue(EAST, p_52341_.getValue(WEST)).setValue(SOUTH, p_52341_.getValue(NORTH)).setValue(WEST, p_52341_.getValue(EAST));
            case COUNTERCLOCKWISE_90:
                return p_52341_.setValue(NORTH, p_52341_.getValue(EAST)).setValue(EAST, p_52341_.getValue(SOUTH)).setValue(SOUTH, p_52341_.getValue(WEST)).setValue(WEST, p_52341_.getValue(NORTH));
            case CLOCKWISE_90:
                return p_52341_.setValue(NORTH, p_52341_.getValue(WEST)).setValue(EAST, p_52341_.getValue(NORTH)).setValue(SOUTH, p_52341_.getValue(EAST)).setValue(WEST, p_52341_.getValue(SOUTH));
            default:
                return p_52341_;
        }
    }

    public BlockState mirror(BlockState p_52338_, Mirror p_52339_) {
        switch(p_52339_) {
            case LEFT_RIGHT:
                return p_52338_.setValue(NORTH, p_52338_.getValue(SOUTH)).setValue(SOUTH, p_52338_.getValue(NORTH));
            case FRONT_BACK:
                return p_52338_.setValue(EAST, p_52338_.getValue(WEST)).setValue(WEST, p_52338_.getValue(EAST));
            default:
                return super.mirror(p_52338_, p_52339_);
        }
    }
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      //  if(!level.isClientSide()) {
            Direction direction = state.getValue(FACING);

            boolean isCorner = false;
            if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                if (state.getValue(EAST) || state.getValue(WEST)) {
                    isCorner = true;
                }
            } else {
                if (state.getValue(NORTH) || state.getValue(SOUTH)) {
                    isCorner = true;
                }
            }
            double speed = isCorner ? 0.0212d : 0.03d;
            Vec3 vec3 = entity.getDeltaMovement();
            if (entity.getX() > (double) pos.getX() + 0.125D && entity.getX() < (double) pos.getX() + 1D - 0.125D) {
                if (direction == Direction.SOUTH) {
                    entity.setDeltaMovement(new Vec3(vec3.x, 0D, speed));
                } else if (direction == Direction.NORTH) {
                    entity.setDeltaMovement(new Vec3(vec3.x, 0D, -speed));
                }
            }
            if (entity.getZ() > (double) pos.getZ() + 0.125D && entity.getZ() < (double) pos.getZ() + 1D - 0.125D) {
                if (direction == Direction.EAST) {
                    entity.setDeltaMovement(new Vec3(speed, 0D, vec3.z));
                } else if (direction == Direction.WEST) {
                    entity.setDeltaMovement(new Vec3(-speed, 0D, vec3.z));
                }
            }

       // }
        super.entityInside(state,level,pos,entity);
    }
}
