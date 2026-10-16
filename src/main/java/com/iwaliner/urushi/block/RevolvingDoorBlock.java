package com.iwaliner.urushi.block;


import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.mixin.BlockDisplayMixin;
import com.iwaliner.urushi.mixin.DisplayMixin;
import com.iwaliner.urushi.util.ElementType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class RevolvingDoorBlock extends Block {
    public static int duration=45;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty PART = IntegerProperty.create("part",0,3);
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty IS_OPENING = BooleanProperty.create("is_opening");
   public RevolvingDoorBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART, Integer.valueOf(0)).setValue(POWERED, Boolean.valueOf(false)).setValue(IS_OPENING,Boolean.valueOf(false)));

    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if(state.getBlock() instanceof RevolvingDoorBlock){
            return state.getValue(IS_OPENING)? Shapes.empty() : Shapes.block();
        }
        return Shapes.block();
    }
    /*@Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor world, BlockPos pos, BlockPos pos2) {
        if(state.getBlock() instanceof RevolvingDoorBlock){
            RevolvingDoorPart part=RevolvingDoorPart.getType(state.getValue(PART));
            if(state.getValue(FACING).getAxis()== Direction.Axis.Z){ //north, south
            }else{ //east, west

            }
        }
        return super.updateShape(state, direction, state2, world, pos, pos2);
       }*/
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType type) {
        switch(type) {
            case LAND:
                return state.getValue(IS_OPENING);
            case WATER:
                return false;
            case AIR:
                return state.getValue(IS_OPENING);
            default:
                return false;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        if (context.getPlayer() != null) {
            Direction facing = context.getPlayer().getDirection();
            Direction neighborFacing = facing.getClockWise();
            BlockPos posUnderLeft = context.getClickedPos();
            BlockPos posUnderRight = posUnderLeft.relative(neighborFacing);
            BlockPos posUpperLeft = posUnderLeft.above();
            BlockPos posUpperRight = posUnderRight.above();
            if (posUnderLeft.getY() < world.getMaxBuildHeight() - 1 && context.getLevel().getBlockState(posUnderLeft).canBeReplaced(context) && context.getLevel().getBlockState(posUpperLeft).canBeReplaced(context) && context.getLevel().getBlockState(posUnderRight).canBeReplaced(context) && context.getLevel().getBlockState(posUpperRight).canBeReplaced(context)) {
                return this.defaultBlockState().setValue(FACING,facing).setValue(PART, RevolvingDoorPart.UnderLeft.getID()).setValue(POWERED, false);
            }
        }
        return this.defaultBlockState();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack p_49851_) {
       if(state.getBlock() instanceof RevolvingDoorBlock){
           Direction facing = state.getValue(FACING);
           Direction neighborFacing = facing.getClockWise();
           BlockPos posUnderLeft = pos;
           BlockPos posUnderRight = posUnderLeft.relative(neighborFacing);
           BlockPos posUpperLeft = posUnderLeft.above();
           BlockPos posUpperRight = posUnderRight.above();
           level.setBlock(posUnderRight, state.setValue(PART, RevolvingDoorPart.UnderRight.getID()), 3);
           level.setBlock(posUpperLeft, state.setValue(PART, RevolvingDoorPart.UpperLeft.getID()), 3);
           level.setBlock(posUpperRight, state.setValue(PART, RevolvingDoorPart.UpperRight.getID()), 3);
       }
    }


    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
       if(state.getBlock() instanceof RevolvingDoorBlock&&state.getValue(IS_OPENING)) {
           level.setBlock(pos,state.setValue(IS_OPENING,false),82);
       }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(state.getBlock() instanceof RevolvingDoorBlock&&!state.getValue(IS_OPENING)) {
            Direction facing = state.getValue(FACING);
            RevolvingDoorPart clickedPart = RevolvingDoorPart.getType(state.getValue(PART));
            BlockPos posUnderLeft;
            BlockPos posUnderRight;
            BlockPos posUpperLeft;
            BlockPos posUpperRight;
            if (clickedPart == RevolvingDoorPart.UnderLeft) {
                posUnderLeft = pos;
                posUpperLeft = posUnderLeft.above();
                posUnderRight = posUnderLeft.relative(facing.getClockWise());
                posUpperRight = posUnderRight.above();
            } else if (clickedPart == RevolvingDoorPart.UnderRight) {
                posUnderRight = pos;
                posUpperRight = posUnderRight.above();
                posUnderLeft = posUnderRight.relative(facing.getCounterClockWise());
                posUpperLeft = posUnderLeft.above();
            } else if (clickedPart == RevolvingDoorPart.UpperLeft) {
                posUpperLeft = pos;
                posUnderLeft = posUpperLeft.below();
                posUpperRight = posUpperLeft.relative(facing.getClockWise());
                posUnderRight = posUpperRight.below();
            } else {
                posUpperRight = pos;
                posUnderRight = posUpperRight.below();
                posUpperLeft = posUpperRight.relative(facing.getCounterClockWise());
                posUnderLeft = posUpperLeft.below();
            }
            BlockState stateUnderLeft = level.getBlockState(posUnderLeft);
            BlockState stateUnderRight = level.getBlockState(posUnderRight);
            BlockState stateUpperLeft = level.getBlockState(posUpperLeft);
            BlockState stateUpperRight = level.getBlockState(posUpperRight);
            if (stateUnderLeft.getBlock() instanceof RevolvingDoorBlock && stateUnderRight.getBlock() instanceof RevolvingDoorBlock && stateUpperRight.getBlock() instanceof RevolvingDoorBlock && stateUpperLeft.getBlock() instanceof RevolvingDoorBlock) {
                addBlockDisplay(level, posUnderLeft, stateUnderLeft,false);
                addBlockDisplay(level, posUnderRight, stateUnderRight,true);
                addBlockDisplay(level, posUpperLeft, stateUpperLeft,false);
                addBlockDisplay(level, posUpperRight, stateUpperRight,true);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.FAIL;
   }
    private void addBlockDisplay(Level level,BlockPos pos,BlockState state,boolean flag){
        Display.BlockDisplay blockDisplay=new Display.BlockDisplay(EntityType.BLOCK_DISPLAY,level);
        SynchedEntityData entityData1=blockDisplay.getEntityData();
        Direction stateFacing=state.getValue(FACING);
        BlockState displayState= ItemAndBlockRegister.plaster_revolving_door_display.get().defaultBlockState();
        Direction facing=stateFacing;
        if(!flag){
            stateFacing=stateFacing.getOpposite();
        }
        double dx=0D;
        double dz=0D;
            blockDisplay.addTag("revolving_door");
            if(stateFacing==Direction.NORTH){
                dz=0.5D;
                facing=Direction.NORTH;
            }else if(stateFacing==Direction.EAST){
                dx=0.5D;
                facing=Direction.WEST;
            }else if(stateFacing==Direction.SOUTH){
                blockDisplay.setYRot(180f);
                dx=1D;
                dz=0.5D;
                facing=Direction.NORTH;
            }else{ //west
                blockDisplay.setYRot(180f);
                dz=1D;
                dx=0.5D;
                facing=Direction.WEST;
            }
            entityData1.set(BlockDisplayMixin.getData(),displayState.setValue(VerticalSlabBlock.FACING,facing));
        blockDisplay.moveTo(pos.getX()+dx, pos.getY(), pos.getZ()+dz);
        if(!level.isClientSide) {
            level.addFreshEntity(blockDisplay);
        }
        level.setBlockAndUpdate(pos,state.setValue(IS_OPENING,true));
        level.scheduleTick(new BlockPos(pos), this, duration+5);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos pos2, boolean boo) {
        super.neighborChanged(state, level, pos, block, pos2, boo);
        if(state.getBlock() instanceof RevolvingDoorBlock){
            Direction facing = state.getValue(FACING);
            RevolvingDoorPart part = RevolvingDoorPart.getType(state.getValue(PART));
            BlockPos posUnderLeft;
            BlockPos posUnderRight;
            BlockPos posUpperLeft;
            BlockPos posUpperRight;
            if (part == RevolvingDoorPart.UnderLeft) {
                posUnderLeft = pos;
                posUpperLeft = posUnderLeft.above();
                posUnderRight = posUnderLeft.relative(facing.getClockWise());
                posUpperRight = posUnderRight.above();
            } else if (part == RevolvingDoorPart.UnderRight) {
                posUnderRight = pos;
                posUpperRight = posUnderRight.above();
                posUnderLeft = posUnderRight.relative(facing.getCounterClockWise());
                posUpperLeft = posUnderLeft.above();
            } else if (part == RevolvingDoorPart.UpperLeft) {
                posUpperLeft = pos;
                posUnderLeft = posUpperLeft.below();
                posUpperRight = posUpperLeft.relative(facing.getClockWise());
                posUnderRight = posUpperRight.below();
            } else {
                posUpperRight = pos;
                posUnderRight = posUpperRight.below();
                posUpperLeft = posUpperRight.relative(facing.getCounterClockWise());
                posUnderLeft = posUpperLeft.below();
            }
            if(!(level.getBlockState(posUnderLeft).getBlock() instanceof RevolvingDoorBlock)){
                destroyOtherPart(level,posUnderRight);
                destroyOtherPart(level,posUpperLeft);
                destroyOtherPart(level,posUpperRight);
            }else if(!(level.getBlockState(posUnderRight).getBlock() instanceof RevolvingDoorBlock)){
                destroyOtherPart(level,posUnderLeft);
                destroyOtherPart(level,posUpperLeft);
                destroyOtherPart(level,posUpperRight);
            }if(!(level.getBlockState(posUpperLeft).getBlock() instanceof RevolvingDoorBlock)){
                destroyOtherPart(level,posUnderRight);
                destroyOtherPart(level,posUnderLeft);
                destroyOtherPart(level,posUpperRight);
            }if(!(level.getBlockState(posUpperRight).getBlock() instanceof RevolvingDoorBlock)){
                destroyOtherPart(level,posUnderRight);
                destroyOtherPart(level,posUpperLeft);
                destroyOtherPart(level,posUnderLeft);
            }
        }
    }
    private void destroyOtherPart(Level level,BlockPos pos){
       if(level.getBlockState(pos).getBlock() instanceof RevolvingDoorBlock){
           level.destroyBlock(pos,true);
       }
    }

   @Override
   public BlockState rotate(BlockState state, Rotation direction) {
       return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
   }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add( POWERED,PART,IS_OPENING,FACING);
    }

    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return true;
    }

    public  enum RevolvingDoorPart {
        UnderLeft(0),
        UnderRight(1),
        UpperLeft(2),
        UpperRight(3);

        private int id;

        private RevolvingDoorPart(int id) {
            this.id = id;
        }
        public static RevolvingDoorPart getType(int id){
            return switch (id) {
                case 1 -> UnderRight;
                case 2 -> UpperLeft;
                case 3 -> UpperRight;
                default -> UnderLeft;
            };

        }
        public int getID()
        {
            return this.id;
        }
    }
}
