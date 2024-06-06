package com.iwaliner.urushi.block;



import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import net.minecraft.util.RandomSource;

public class SlideDoorBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty OPEN = IntegerProperty.create("open",0,13);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty IS_OPENING = BooleanProperty.create("is_opening");
    protected static final VoxelShape SOUTH_CLOSE = Block.box(0.0D, 0.0D, 8D, 16.0D, 16.0D, 10D);
    protected static final VoxelShape NORTH_CLOSE = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 8.0D);
    protected static final VoxelShape WEST_CLOSE = Block.box(6.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_CLOSE = Block.box(8.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    protected static final VoxelShape NORTH_CLOSE_INV = Block.box(0.0D, 0.0D, 6D, 16.0D, 16.0D, 8D);
    protected static final VoxelShape SOUTH_CLOSE_INV = Block.box(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 10.0D);
    protected static final VoxelShape EAST_CLOSE_INV = Block.box(8.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    protected static final VoxelShape WEST_CLOSE_INV = Block.box(6.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape NORTH_OPEN = Block.box(-13D, 0.0D, 6D, 3.0D, 16.0D, 8D);
    protected static final VoxelShape SOUTH_OPEN = Block.box(13.0D, 0.0D, 8.0D, 29D, 16.0D, 10.0D);
    protected static final VoxelShape EAST_OPEN = Block.box(8.0D, 0.0D, -13D, 10.0D, 16.0D, 3.0D);
    protected static final VoxelShape WEST_OPEN = Block.box(6.0D, 0.0D, 13.0D, 8.0D, 16.0D, 29D);
    protected static final VoxelShape SOUTH_OPEN_INV = Block.box(-13D, 0.0D, 8D, 3.0D, 16.0D, 10D);
    protected static final VoxelShape NORTH_OPEN_INV = Block.box(13.0D, 0.0D, 6.0D, 29D, 16.0D, 8.0D);
    protected static final VoxelShape WEST_OPEN_INV = Block.box(6.0D, 0.0D, -13D, 8.0D, 16.0D, 3.0D);
    protected static final VoxelShape EAST_OPEN_INV = Block.box(8.0D, 0.0D, 13.0D, 10.0D, 16.0D, 29D);

      public SlideDoorBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Integer.valueOf(0)).setValue(POWERED, Boolean.valueOf(false)).setValue(HALF, DoubleBlockHalf.LOWER).setValue(INVERTED, Boolean.valueOf(false)).setValue(IS_OPENING,Boolean.valueOf(false)));

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        /*VoxelShape NORTH[]= new VoxelShape[14];
        VoxelShape SOUTH[]= new VoxelShape[14];
        VoxelShape EAST[]= new VoxelShape[14];
        VoxelShape WEST[]= new VoxelShape[14];
        VoxelShape NORTH_INV[]= new VoxelShape[14];
        VoxelShape SOUTH_INV[]= new VoxelShape[14];
        VoxelShape EAST_INV[]= new VoxelShape[14];
        VoxelShape WEST_INV[]= new VoxelShape[14];
        for(int i=0;i<14;i++){
            NORTH[i] = Block.box(0D-(double) i, 0.0D, 6D, 16D-(double) i, 16.0D, 8D);
            SOUTH[i] = Block.box(0D+(double) i, 0.0D, 8D, 16D+(double) i, 16.0D, 10D);
            EAST[i] = Block.box(8D, 0.0D, 0D-(double) i, 10D, 16.0D, 16D-(double) i);
            WEST[i] = Block.box(6D, 0D, 0D+(double) i, 8D, 16.0D, 16D+(double) i);
            NORTH_INV[i] = Block.box(0D+(double) i, 0.0D, 6D, 16D+(double) i, 16.0D, 8D);
            SOUTH_INV[i] = Block.box(0D-(double) i, 0.0D, 8D, 16D-(double) i, 16.0D, 10D);
            EAST_INV[i] = Block.box(8D, 0.0D, 0D+(double) i, 10D, 16.0D, 16D+(double) i);
            WEST_INV[i] = Block.box(6D, 0D, 0D-(double) i, 8D, 16.0D, 16D-(double) i);

        }

        Direction direction = state.getValue(FACING);
       boolean invert = !state.getValue(INVERTED);
        int open=state.getValue(OPEN);
        switch(direction) {
            case EAST:
            default:
                return  invert? EAST_INV[open]:EAST[open];
            case SOUTH:
                return  invert? SOUTH_INV[open]:SOUTH[open];
            case WEST:
                return  invert? WEST_INV[open]:WEST[open];
            case NORTH:
                return  invert? NORTH_INV[open]:NORTH[open];
        }*/

        Direction direction = state.getValue(FACING);
        boolean invert = !state.getValue(INVERTED);
        int open=state.getValue(OPEN);
        boolean isOpening=state.getValue(IS_OPENING);
        boolean isMoving=isMoving(state);


            if(!isMoving&&open==0){
                switch(direction) {
                    case EAST:
                        return  invert? EAST_CLOSE_INV:EAST_CLOSE;
                    case SOUTH:
                        return  invert? SOUTH_CLOSE_INV:SOUTH_CLOSE;
                    case WEST:
                        return  invert? WEST_CLOSE_INV:WEST_CLOSE;
                    case NORTH:
                        return  invert? NORTH_CLOSE_INV:NORTH_CLOSE;
                }
            }else if((!isMoving&&open==13)||isMoving){
                switch(direction) {
                    case EAST:
                        return  invert? EAST_OPEN_INV:EAST_OPEN;
                    case SOUTH:
                        return  invert? SOUTH_OPEN_INV:SOUTH_OPEN;
                    case WEST:
                        return  invert? WEST_OPEN_INV:WEST_OPEN;
                    case NORTH:
                        return  invert? NORTH_OPEN_INV:NORTH_OPEN;
                }
            }
            return Shapes.block();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (direction == Direction.UP)) {
            return state2.is(this) && state2.getValue(HALF) != doubleblockhalf ? state.setValue(FACING, state2.getValue(FACING)).setValue(OPEN, state2.getValue(OPEN)).setValue(POWERED, state2.getValue(POWERED)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state2, world, pos, pos2);
        } }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player entity) {
        if (!world.isClientSide && entity.isCreative()) {
            this.preventCreativeDropFromBottomPart(world, pos, state, entity);
        }

        super.playerWillDestroy(world, pos, state, entity); }


    protected static void preventCreativeDropFromBottomPart(Level world, BlockPos pos, BlockState state, Player entity) {
        DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(entity, 2001, blockpos, Block.getId(blockstate));
            }
        }

    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType type) {
        switch(type) {
            case LAND:
                return isOpen(state);
            case WATER:
                return false;
            case AIR:
                return isOpen(state);
            default:
                return false;
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            Direction facing = context.getHorizontalDirection();
            if (blockpos.getY() < world.getMaxBuildHeight() - 1 && context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) {
                boolean flag = world.hasNeighborSignal(blockpos) || world.hasNeighborSignal(blockpos.above());
                boolean invert = false;
                Direction direction = context.getHorizontalDirection();
                BlockState clockwiseState = world.getBlockState(blockpos.above().relative(facing.getClockWise()));
                BlockState counterclockwiseState = world.getBlockState(blockpos.above().relative(facing.getCounterClockWise()));
                if (clockwiseState.getBlock() instanceof SlideDoorBlock) {
                        if (!clockwiseState.getValue(INVERTED)) {
                            invert = facing==clockwiseState.getValue(FACING);
                            direction = facing;
                        } else {
                            invert = facing==clockwiseState.getValue(FACING);
                            direction = facing.getOpposite();
                        }
                }
                if (counterclockwiseState.getBlock() instanceof SlideDoorBlock) {
                    if (!counterclockwiseState.getValue(INVERTED)) {
                        invert = facing==counterclockwiseState.getValue(FACING).getOpposite();
                        direction = facing.getOpposite();
                    } else {
                        invert = facing==counterclockwiseState.getValue(FACING).getOpposite();
                        direction = facing;
                    }
                }
                return this.defaultBlockState().setValue(FACING,  direction).setValue(POWERED, Boolean.valueOf(flag)).setValue(OPEN, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.LOWER).setValue(INVERTED, Boolean.valueOf(invert));
            }

            return this.defaultBlockState();
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
        world.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int openProcess=state.getValue(OPEN);
        boolean isOpening=state.getValue(IS_OPENING);
        if(openProcess!=0&&openProcess!=13){
            int nextProcess= isOpening? ++openProcess : --openProcess;
           level.setBlockAndUpdate(pos,state.setValue(OPEN,nextProcess));
           level.scheduleTick(new BlockPos(pos), this, 1);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (ConfigUrushi.instantlySlidingDoor.get()) {
            if (state.getValue(OPEN) == 0 ) {
                world.setBlock(pos, state.setValue(OPEN, 13).setValue(IS_OPENING, state.getValue(OPEN) == 0), 10);
                world.playSound(player, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.sidedSuccess(world.isClientSide);
            }else if (state.getValue(OPEN) == 13) {
                world.setBlock(pos, state.setValue(OPEN, 0).setValue(IS_OPENING, state.getValue(OPEN) == 0), 10);
                world.playSound(player, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.sidedSuccess(world.isClientSide);
            }else{
                return InteractionResult.FAIL;
            }
        } else {
            if (state.getValue(OPEN) == 0 || state.getValue(OPEN) == 13) {
                world.setBlock(pos, state.setValue(OPEN, Integer.valueOf(state.getValue(OPEN) == 0 ? 1 : 12)).setValue(IS_OPENING, state.getValue(OPEN) == 0), 10);
                world.playSound(player, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);

                world.scheduleTick(new BlockPos(pos), this, 1);


                return InteractionResult.sidedSuccess(world.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }





    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos pos2, boolean boo) {
        boolean flag = world.hasNeighborSignal(pos) || world.hasNeighborSignal(pos.relative(state.getValue(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
        BlockPos anotherPos = state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos.above() : pos.below();
        BlockState anotherState=world.getBlockState(anotherPos);
        if (block != this/*&& flag != state.getValue(POWERED)*/) {
            if(ConfigUrushi.instantlySlidingDoor.get()){
                if (state.getValue(OPEN) == 0 || state.getValue(OPEN) == 13) {
                    if (flag == isClose(state)) {
                        world.playSound((Player) null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);

                    }
                    if(flag&&state.getValue(OPEN) == 0){
                        world.setBlock(pos, state.setValue(POWERED, false).setValue(OPEN, 13).setValue(IS_OPENING, flag), 2);
                       // world.setBlock(anotherPos, anotherState.setValue(POWERED, flag).setValue(OPEN, 13).setValue(IS_OPENING, flag), 2);
                        world.scheduleTick(new BlockPos(pos), this, 1);
                    //    world.scheduleTick(new BlockPos(anotherPos), this, 1);
                    }else if(!flag&&state.getValue(OPEN) == 13) {
                        world.setBlock(pos, state.setValue(POWERED, false).setValue(OPEN, 0).setValue(IS_OPENING, flag), 2);
                  //      world.setBlock(anotherPos, anotherState.setValue(POWERED, flag).setValue(OPEN, 0).setValue(IS_OPENING, flag), 2);
                        world.scheduleTick(new BlockPos(pos), this, 1);
                      //  world.scheduleTick(new BlockPos(anotherPos), this, 1);
                    }
                }
            }else {
                if (state.getValue(OPEN) == 0 || state.getValue(OPEN) == 13) {
                    if (flag == isClose(state)) {
                        world.playSound((Player) null, pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);

                    }
                    if(flag&&state.getValue(OPEN) == 0){
                        world.setBlock(pos, state.setValue(POWERED, false).setValue(OPEN, 1).setValue(IS_OPENING, flag), 2);
                       // world.setBlock(anotherPos, anotherState.setValue(POWERED, flag).setValue(OPEN, 1).setValue(IS_OPENING, flag), 2);
                        world.scheduleTick(new BlockPos(pos), this, 1);
                      //  world.scheduleTick(new BlockPos(anotherPos), this, 1);
                    }else if(!flag&&state.getValue(OPEN) == 13) {
                        world.setBlock(pos, state.setValue(POWERED, false).setValue(OPEN, 12).setValue(IS_OPENING, flag), 2);
                       // world.setBlock(anotherPos, anotherState.setValue(POWERED, flag).setValue(OPEN, 12).setValue(IS_OPENING, flag), 2);
                        world.scheduleTick(new BlockPos(pos), this, 1);
                      //  world.scheduleTick(new BlockPos(anotherPos), this, 1);
                    }
                   // world.setBlock(pos, state.setValue(POWERED, Boolean.valueOf(flag)).setValue(OPEN, state.getValue(OPEN) == 0 ? 1 : 12).setValue(IS_OPENING, state.getValue(OPEN) == 0), 2);
                   // world.scheduleTick(new BlockPos(pos), this, 1);
                }
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos2) {
        BlockPos blockpos = pos2.below();
        BlockState blockstate = reader.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(reader, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return mirror == Mirror.NONE ? state : state.rotate(mirror.getRotation(state.getValue(FACING)));
    }



    @OnlyIn(Dist.CLIENT)
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, FACING, OPEN, POWERED,INVERTED,IS_OPENING);
    }

    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return true;
    }
    private boolean classicalOpenFlag(BlockState state){
        if(isOpen(state)){
            return true;
        }else if(isClose(state)){
            return false;
        }
        return false;
    }
    private boolean isOpen(BlockState state){
        return state.getValue(OPEN)==13;
    }
    private boolean isClose(BlockState state){
        return state.getValue(OPEN)==0;
    }
    private boolean isMoving(BlockState state){
        return state.getValue(OPEN)!=0&&state.getValue(OPEN)!=13;
    }


}
