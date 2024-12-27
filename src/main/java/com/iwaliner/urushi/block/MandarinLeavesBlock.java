package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;

public class MandarinLeavesBlock extends LeavesBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    public MandarinLeavesBlock(Properties p_54422_) {
        super(p_54422_);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));
    }
    public void randomTick(BlockState p_222563_, ServerLevel p_222564_, BlockPos p_222565_, RandomSource p_222566_) {
        int i = p_222563_.getValue(AGE);
        if (i < 1 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_222564_, p_222565_, p_222563_, p_222566_.nextInt(5) == 0)) {
            BlockState blockstate = p_222563_.setValue(AGE, Integer.valueOf(i + 1));
            p_222564_.setBlock(p_222565_, blockstate, 2);
            p_222564_.gameEvent(GameEvent.BLOCK_CHANGE, p_222565_, GameEvent.Context.of(blockstate));
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_222564_, p_222565_, p_222563_);
        }

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT, WATERLOGGED,AGE);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }
}
