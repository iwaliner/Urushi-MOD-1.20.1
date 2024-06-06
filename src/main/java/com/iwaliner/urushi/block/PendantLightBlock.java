package com.iwaliner.urushi.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PendantLightBlock extends Block {
    private static final VoxelShape MAIN = Block.box(2D, 0.0D, 2D, 14D, 5D, 14D);
    private static final VoxelShape ROPE = Block.box(7D, 4D, 7D, 9D, 16D, 9D);
    private static final VoxelShape SHAPE = Shapes.or(MAIN, ROPE);
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public PendantLightBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, Boolean.valueOf(true)));

    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        level.setBlockAndUpdate(pos,state.cycle(LIT));
        level.playSound(player,pos, SoundEvents.DISPENSER_DISPENSE, SoundSource.BLOCKS,1F,1F);
        return InteractionResult.SUCCESS;
    }
}
