package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.blockentity.KettleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class KettleBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D);
    public static final BooleanProperty HOOK=BooleanProperty.create("hook");

    public KettleBlock(Properties p_i48377_1_) {

        super(p_i48377_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HOOK, Boolean.valueOf(false)));

    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,HOOK);
    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }
    private boolean canHang(LevelReader level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        VoxelShape shape = state.getShape(level, pos).optimize();
        return shape.min(Direction.Axis.Y) <= 0.0D;
    }
    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state2, LevelAccessor level, BlockPos pos, BlockPos pos2) {
        boolean flag = false;
        if ((Boolean)state.getValue(HOOK) && !this.canHang(level, pos.above())) {
            flag = false;
        } else if (!(Boolean)state.getValue(HOOK) && this.canHang(level, pos.above())) {
            flag = true;
        } else if ((Boolean)state.getValue(HOOK)) {
            flag = true;
        }

        return (BlockState)super.updateShape(state, direction, state2, level, pos, pos2).setValue(HOOK, flag);
    }
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, level, pos, neighbor);
        if ((Boolean)state.getValue(HOOK) && !this.canHang(level, pos.above())) {
            state.setValue(HOOK, false);
        } else if (!(Boolean)state.getValue(HOOK) && this.canHang(level, pos.above())) {
            state.setValue(HOOK, true);
        }

    }


    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return (BlockState)((BlockState)super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getClockWise())).setValue(HOOK, this.canHang(level, pos.above()));
    }
    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return   new KettleBlockEntity(pos,state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return level.isClientSide ? null : createTickerHelper(p_152162_, BlockEntityRegister.Kettle.get(), KettleBlockEntity::serverTick);
    }

    public InteractionResult use(BlockState p_48706_, Level p_48707_, BlockPos p_48708_, Player p_48709_, InteractionHand p_48710_, BlockHitResult p_48711_) {
        if (p_48707_.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(p_48707_, p_48708_, p_48709_);
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof KettleBlockEntity) {
            player.openMenu((MenuProvider)blockentity);
        }
    }
    public void setPlacedBy(Level p_48694_, BlockPos p_48695_, BlockState p_48696_, LivingEntity p_48697_, ItemStack p_48698_) {
        if (p_48698_.hasCustomHoverName()) {
            BlockEntity blockentity = p_48694_.getBlockEntity(p_48695_);
            if (blockentity instanceof KettleBlockEntity) {
                ((KettleBlockEntity)blockentity).setCustomName(p_48698_.getHoverName());
            }
        }

    }

    public void onRemove(BlockState p_48713_, Level p_48714_, BlockPos p_48715_, BlockState p_48716_, boolean p_48717_) {
        if (!p_48713_.is(p_48716_.getBlock())) {
            BlockEntity blockentity = p_48714_.getBlockEntity(p_48715_);
            if (blockentity instanceof KettleBlockEntity) {
                if (p_48714_ instanceof ServerLevel) {
                    Containers.dropContents(p_48714_, p_48715_, (KettleBlockEntity)blockentity);
                }
            }

            super.onRemove(p_48713_, p_48714_, p_48715_, p_48716_, p_48717_);
        }
    }
    public BlockState rotate(BlockState p_52716_, Rotation p_52717_) {
        return p_52716_.setValue(FACING, p_52717_.rotate(p_52716_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_52713_, Mirror p_52714_) {
        return p_52713_.rotate(p_52714_.getRotation(p_52713_.getValue(FACING)));
    }

}
