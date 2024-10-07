package com.iwaliner.urushi.block;


import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class HorizonalRotateSlabBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
  //  public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<SlabType> TYPE = BlockStateProperties.SLAB_TYPE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape TOP_AABB = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public HorizonalRotateSlabBlock(Properties p_i48377_1_) {
        super(p_i48377_1_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(false)));

    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState p_60576_) {
        return p_60576_.getValue(TYPE) != SlabType.DOUBLE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,TYPE,WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        SlabType slabtype = p_60555_.getValue(TYPE);
        switch(slabtype) {
            case DOUBLE:
                return Shapes.block();
            case TOP:
                return TOP_AABB;
            default:
                return BOTTOM_AABB;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);
        BlockState oldState=context.getLevel().getBlockState(blockpos.relative(context.getClickedFace()));
        if (blockstate.is(this)) {
            return blockstate.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(FACING,context.getHorizontalDirection().getOpposite());
        }else if(isSlab(blockstate.getBlock())&& (UrushiUtils.isMinecraftObject(blockstate.getBlock().getDescriptionId())||UrushiUtils.isUrushiObject(blockstate.getBlock().getDescriptionId()))&&(UrushiUtils.isMinecraftObject(this.getDescriptionId())||UrushiUtils.isUrushiObject(this.getDescriptionId()))){
            Direction direction = context.getClickedFace();
            if(direction == Direction.UP || !(context.getClickLocation().y - (double) blockpos.getY() <= 0.5D)) {
                return ItemAndBlockRegister.double_slab.get().defaultBlockState().setValue(DoubleSlabBlock.FACING, context.getHorizontalDirection().getOpposite()).setValue(DoubleSlabBlock.UPPER, DoubleSlabBlock.getIDFromBlock(this)).setValue(DoubleSlabBlock.UNDER,DoubleSlabBlock.getIDFromBlock(blockstate.getBlock()));
            }else{
                return ItemAndBlockRegister.double_slab.get().defaultBlockState().setValue(DoubleSlabBlock.FACING, context.getHorizontalDirection().getOpposite()).setValue(DoubleSlabBlock.UNDER, DoubleSlabBlock.getIDFromBlock(this)).setValue(DoubleSlabBlock.UPPER,DoubleSlabBlock.getIDFromBlock(blockstate.getBlock()));
            }
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(blockpos);
            BlockState blockstate1 = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)).setValue(FACING,context.getHorizontalDirection().getOpposite());
            Direction direction = context.getClickedFace();
            return direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double)blockpos.getY() > 0.5D)) ? blockstate1 : blockstate1.setValue(TYPE, SlabType.TOP);
        }
    }
    private boolean isSlab(Block block){
        return block instanceof SlabBlock||block instanceof HorizonalRotateSlabBlock;
    }

    public boolean canBeReplaced(BlockState p_56373_, BlockPlaceContext context) {
        ItemStack itemstack = context.getItemInHand();
        SlabType slabtype = p_56373_.getValue(TYPE);
        if (slabtype != SlabType.DOUBLE &&isSlab(Block.byItem(itemstack.getItem()))&& (UrushiUtils.isMinecraftObject(Block.byItem(itemstack.getItem()).getDescriptionId())||UrushiUtils.isUrushiObject(Block.byItem(itemstack.getItem()).getDescriptionId()))&&(UrushiUtils.isMinecraftObject(this.getDescriptionId())||UrushiUtils.isUrushiObject(this.getDescriptionId()))) {
            if (context.replacingClickedOnBlock()) {
                boolean flag = context.getClickLocation().y - (double)context.getClickedPos().getY() > 0.5D;
                Direction direction = context.getClickedFace();
                if (slabtype == SlabType.BOTTOM) {
                    return direction == Direction.UP || flag && direction.getAxis().isHorizontal();
                } else {
                    return direction == Direction.DOWN || !flag && direction.getAxis().isHorizontal();
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    public FluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_204507_1_);
    }

    @Override
    public boolean placeLiquid(LevelAccessor p_56306_, BlockPos p_56307_, BlockState p_56308_, FluidState p_56309_) {
        return p_56308_.getValue(TYPE) != SlabType.DOUBLE ? SimpleWaterloggedBlock.super.placeLiquid(p_56306_, p_56307_, p_56308_, p_56309_) : false;
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter p_56301_, BlockPos p_56302_, BlockState p_56303_, Fluid p_56304_) {
        return p_56303_.getValue(TYPE) != SlabType.DOUBLE ? SimpleWaterloggedBlock.super.canPlaceLiquid(p_56301_, p_56302_, p_56303_, p_56304_) : false;
    }

    @Override
    public BlockState updateShape(BlockState p_60541_, Direction p_60542_, BlockState p_60543_, LevelAccessor p_60544_, BlockPos p_60545_, BlockPos p_60546_) {
        if (p_60541_.getValue(WATERLOGGED)) {
            p_60544_.scheduleTick(p_60545_, Fluids.WATER, Fluids.WATER.getTickDelay(p_60544_));
        }

        return super.updateShape(p_60541_, p_60542_, p_60543_, p_60544_, p_60545_, p_60546_);
    }

    @Override
    public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        switch(p_60478_) {
            case LAND:
                return false;
            case WATER:
                return p_60476_.getFluidState(p_60477_).is(FluidTags.WATER);
            case AIR:
                return false;
            default:
                return false;
        } }


}
