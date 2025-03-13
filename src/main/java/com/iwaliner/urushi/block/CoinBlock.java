package com.iwaliner.urushi.block;

import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;

public class CoinBlock extends HorizonalRotateBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty COINS = IntegerProperty.create("coins", 1, 6);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final VoxelShape Shape1 = Block.box(5.5D, 0.0D, 5.5D, 16D-5.5D, 1D, 16D-5.5D);
    private static final VoxelShape Shape4 = Block.box(2.5D, 0.0D, 2.5D, 16D-2.5D, 1D, 16D-2.5D);
    private static final VoxelShape Shape6 = Block.box(0D, 0.0D, 0D, 16D, 1D, 16D);
    public CoinBlock(Properties p_i48377_1_) {
        super(p_i48377_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(COINS, Integer.valueOf(1)).setValue(WATERLOGGED, Boolean.valueOf(true)));
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        int i=state.getValue(COINS);
        if(i==1){
            return Shape1;
        }else if(i<=4){
            return Shape4;
        }
        return Shape6;
    }
    public boolean canBeReplaced(BlockState p_56101_, BlockPlaceContext p_56102_) {
        return !p_56102_.isSecondaryUseActive() && p_56102_.getItemInHand().is(this.asItem()) && p_56101_.getValue(COINS) < 6 ? true : super.canBeReplaced(p_56101_, p_56102_);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext p_56089_) {
        BlockState blockstate = p_56089_.getLevel().getBlockState(p_56089_.getClickedPos());
        if (blockstate.is(this)) {
            return blockstate.setValue(COINS, Integer.valueOf(Math.min(6, blockstate.getValue(COINS) + 1)));
        } else {
            FluidState fluidstate = p_56089_.getLevel().getFluidState(p_56089_.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;
            return super.getStateForPlacement(p_56089_).setValue(WATERLOGGED, Boolean.valueOf(flag)).setValue(FACING, p_56089_.getHorizontalDirection().getOpposite());
        }
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56120_) {
        p_56120_.add(COINS, WATERLOGGED,FACING);
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list,"coin");
    }
}
