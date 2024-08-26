package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AbstractHorizontalRotateHighBlock extends HorizonalRotateBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public AbstractHorizontalRotateHighBlock(Properties p_49795_) {
        super(p_49795_);
    }
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
    public boolean canSurvive(BlockState state, LevelReader reader, BlockPos pos2) {
        BlockPos blockpos = pos2.below();
        BlockState blockstate = reader.getBlockState(blockpos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(reader, blockpos, Direction.UP) : blockstate.is(this);
    }
    @OnlyIn(Dist.CLIENT)
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }
}
