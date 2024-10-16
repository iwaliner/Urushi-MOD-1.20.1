package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RopeBlock extends FallingBlock implements SimpleWaterloggedBlock {
    protected static final VoxelShape SHAPE_Y = Block.box(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
    protected static final VoxelShape SHAPE_X = Block.box(0.0, 7.0, 7.0, 16.0, 9.0, 9.0);
    protected static final VoxelShape SHAPE_Z = Block.box(7.0, 7.0, 0.0, 9.0, 9.0, 16.0);
    protected static final VoxelShape SHAPE_HANG = Block.box(7.0, 0.0, 7.0, 9.0, 7.0, 9.0);
    public static final EnumProperty<Direction.Axis> AXIS=BlockStateProperties.AXIS;
    public static final BooleanProperty WATERLOGGED=BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty HANGING=BlockStateProperties.HANGING;
    public static final BooleanProperty GRAVITY=BooleanProperty.create("gravity");

    public RopeBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(AXIS, Direction.Axis.Y).setValue(GRAVITY, Boolean.valueOf(false)).setValue(HANGING, Boolean.valueOf(false)));

    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if (state.getValue(AXIS) == Direction.Axis.X) {
            return (Boolean)state.getValue(HANGING) ? Shapes.or(SHAPE_X, SHAPE_HANG) : SHAPE_X;
        } else if (state.getValue(AXIS) == Direction.Axis.Z) {
            return (Boolean)state.getValue(HANGING) ? Shapes.or(SHAPE_Z, SHAPE_HANG) : SHAPE_Z;
        } else {
            return SHAPE_Y;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56051_) {
        p_56051_.add( HANGING,AXIS,GRAVITY,WATERLOGGED);
    }
    public FluidState getFluidState(BlockState p_56073_) {
        return p_56073_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_56073_);
    }
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
      entity.setDeltaMovement(new Vec3(0D, vec3.y,0D));
        super.entityInside(state, world, pos, entity);
    }
    private boolean canHang(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        VoxelShape shape = state.getShape(level, pos).optimize();
        return shape.max(Direction.Axis.Y) >= 1.0;
    }
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
        boolean flag = false;
        if ((Boolean)state.getValue(HANGING) && !this.canHang(level, pos.below())) {
            flag = false;
        } else if (!(Boolean)state.getValue(HANGING) && this.canHang(level, pos.below())) {
            flag = true;
        } else if ((Boolean)state.getValue(HANGING)) {
            flag = true;
        }

        return (BlockState)super.updateShape(state, direction, state2, level, pos, pos2).setValue(HANGING, flag);
    }
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, level, pos, neighbor);
        if ((Boolean)state.getValue(HANGING) && !this.canHang(level, pos.below())) {
            state.setValue(HANGING, false);
        } else if (!(Boolean)state.getValue(HANGING) && this.canHang(level, pos.below())) {
            state.setValue(HANGING, true);
        }

    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return (BlockState)((BlockState)super.getStateForPlacement(context).setValue(AXIS, context.getClickedFace().getAxis())).setValue(HANGING, this.canHang(level, pos.below()));
    }
    public void animateTick(BlockState p_53221_, Level p_53222_, BlockPos p_53223_, RandomSource p_53224_) {

    }
    public void tick(BlockState p_53216_, ServerLevel p_53217_, BlockPos p_53218_, RandomSource p_53219_) {
        if (isFree(p_53217_.getBlockState(p_53218_.below())) && p_53218_.getY() >= p_53217_.getMinBuildHeight()&&p_53216_.getValue(GRAVITY)) {
            FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(p_53217_, p_53218_, p_53216_);
            this.falling(fallingblockentity);
        }
    }

    public void onPlace(BlockState p_53233_, Level p_53234_, BlockPos p_53235_, BlockState p_53236_, boolean p_53237_) {
        if(p_53233_.getValue(GRAVITY))
        p_53234_.scheduleTick(p_53235_, this, this.getDelayAfterPlace());
    }

    public BlockState rotate(BlockState p_55930_, Rotation p_55931_) {
        return rotatePillar(p_55930_, p_55931_);
    }

    public static BlockState rotatePillar(BlockState p_154377_, Rotation p_154378_) {
        switch (p_154378_) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch ((Direction.Axis)p_154377_.getValue(AXIS)) {
                    case X:
                        return p_154377_.setValue(AXIS, Direction.Axis.Z);
                    case Z:
                        return p_154377_.setValue(AXIS, Direction.Axis.X);
                    default:
                        return p_154377_;
                }
            default:
                return p_154377_;
        }
    }
}
