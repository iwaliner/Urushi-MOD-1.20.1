package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class UrushiSlabBlock extends SlabBlock {
    public UrushiSlabBlock(Properties p_56359_) {
        super(p_56359_);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);
        BlockState oldState=context.getLevel().getBlockState(blockpos.relative(context.getClickedFace()));
        if (blockstate.is(this)) {
            return blockstate.setValue(TYPE, SlabType.DOUBLE).setValue(WATERLOGGED, Boolean.valueOf(false));
        }else if(blockstate.getBlock() instanceof UrushiSlabBlock){
            Direction direction = context.getClickedFace();
            if(direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double)blockpos.getY() > 0.5D))) {
                return ItemAndBlockRegister.double_slab.get().defaultBlockState().setValue(DoubleSlabBlock.FACING, context.getHorizontalDirection().getOpposite()).setValue(DoubleSlabBlock.UPPER, DoubleSlabBlock.getIDFromBlock(this)).setValue(DoubleSlabBlock.UNDER,DoubleSlabBlock.getIDFromBlock(blockstate.getBlock()));
            }else{
                return ItemAndBlockRegister.double_slab.get().defaultBlockState().setValue(DoubleSlabBlock.FACING, context.getHorizontalDirection().getOpposite()).setValue(DoubleSlabBlock.UNDER, DoubleSlabBlock.getIDFromBlock(this)).setValue(DoubleSlabBlock.UPPER,DoubleSlabBlock.getIDFromBlock(blockstate.getBlock()));
            }
        } else {
            FluidState fluidstate = context.getLevel().getFluidState(blockpos);
            BlockState blockstate1 = this.defaultBlockState().setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
            Direction direction = context.getClickedFace();
            return direction != Direction.DOWN && (direction == Direction.UP || !(context.getClickLocation().y - (double)blockpos.getY() > 0.5D)) ? blockstate1 : blockstate1.setValue(TYPE, SlabType.TOP);
        }
    }
    public boolean canBeReplaced(BlockState p_56373_, BlockPlaceContext context) {
        ItemStack itemstack = context.getItemInHand();
        SlabType slabtype = p_56373_.getValue(TYPE);
     if (slabtype != SlabType.DOUBLE && Block.byItem(itemstack.getItem()) instanceof UrushiSlabBlock) {
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
}
