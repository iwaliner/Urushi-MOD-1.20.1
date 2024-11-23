package com.iwaliner.urushi.block;



import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class Roof225Block extends HorizonalRotateSlabBlock {
    private static final VoxelShape UNDER = Block.box(0D, 0D, 0D, 16D, 8D, 16D);
    private static final VoxelShape UPPER = Block.box(0D, -8D, 0D, 16D, 0D, 16D);
    private static final VoxelShape EXTEND_UPPER = Block.box(0D, -8D, 0D, 16D, 8D, 16D);

    public Roof225Block(Properties p_i48331_1_) {
        super(p_i48331_1_);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        if (state.getValue(TYPE) == SlabType.TOP) {
            return UPPER;
        }  else {
            return UNDER;
        }
    }


    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        SlabType slabtype = p_60555_.getValue(TYPE);
        return slabtype==SlabType.BOTTOM?BOTTOM_AABB:EXTEND_UPPER;
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level world = context.getLevel();
        BlockState blockstate = context.getLevel().getBlockState(blockpos);
        Direction direction = context.getHorizontalDirection();
        Direction.Axis direction$axis = direction.getAxis();
        boolean flag1 = direction$axis == Direction.Axis.Z && (world.getBlockState(blockpos.west()).getBlock() instanceof Roof225Block || world.getBlockState(blockpos.east()).getBlock() instanceof Roof225Block) || direction$axis == Direction.Axis.X && (world.getBlockState(blockpos.north()).getBlock() instanceof Roof225Block || world.getBlockState(blockpos.south()).getBlock() instanceof Roof225Block);
        BlockState blockstateClicked = null;


        if (direction$axis == Direction.Axis.Z) {
            if (world.getBlockState(blockpos.west()).getBlock() instanceof Roof225Block) {
                blockstateClicked = world.getBlockState(blockpos.west());
            } else if (world.getBlockState(blockpos.east()).getBlock() instanceof Roof225Block) {
                blockstateClicked = world.getBlockState(blockpos.east());
            }
        } else if (direction$axis == Direction.Axis.X) {
            if (world.getBlockState(blockpos.north()).getBlock() instanceof Roof225Block) {
                blockstateClicked = world.getBlockState(blockpos.north());
            } else if (world.getBlockState(blockpos.south()).getBlock() instanceof Roof225Block) {
                blockstateClicked = world.getBlockState(blockpos.south());
            }
        }

         if (this.isSlab(world.getBlockState(blockpos.below())) && world.getBlockState(blockpos.below()).getValue(TYPE) != SlabType.DOUBLE) {
            if (world.getBlockState(blockpos.below()).getValue(TYPE) == SlabType.TOP) {
                return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(false));
            } else if (world.getBlockState(blockpos.below()).getValue(TYPE) == SlabType.BOTTOM) {
                return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(TYPE, SlabType.TOP).setValue(WATERLOGGED, Boolean.valueOf(false));
            } else {
                return this.defaultBlockState();
            }
        } else if(world.getBlockState(blockpos.below()).getCollisionShape(world,blockpos.below()).max(Direction.Axis.Y)==1D){
             return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(false));
         }else if (flag1 && blockstateClicked != null) {
            return this.defaultBlockState().setValue(FACING, blockstateClicked.getValue(FACING)).setValue(TYPE, blockstateClicked.getValue(TYPE)).setValue(WATERLOGGED, Boolean.valueOf(false));

        } else {
            BlockState blockstate1 = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(TYPE, SlabType.BOTTOM).setValue(WATERLOGGED, Boolean.valueOf(false));
            Direction direction2 = context.getClickedFace();
            return direction2 != Direction.DOWN && (direction2 == Direction.UP || !(context.getClickLocation().y - (double) blockpos.getY() > 0.5D)) ? blockstate1 : blockstate1.setValue(TYPE, SlabType.TOP);

        }
    }


    private boolean isSlab(BlockState state){
        if(state.getBlock() instanceof SlabBlock||state.getBlock()instanceof HorizonalRotateSlabBlock){
            return true;
        }
        return false;
    }

}
