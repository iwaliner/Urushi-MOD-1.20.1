package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;

public class KakuriyoPortalCoreBlock extends HorizonalRotateBlock {
    public KakuriyoPortalCoreBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_55673_) {
        p_55673_.add(FACING);
    }
    @Override
    public PushReaction getPistonPushReaction(BlockState p_60584_) {
        return PushReaction.DESTROY;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean boo) {
        if (state.getBlock() instanceof KakuriyoPortalCoreBlock) {
            if (state.getValue(FACING).getAxis() == Direction.Axis.Z) {
                removePortalBlock(level, pos.offset(1, 0, 0));
                removePortalBlock(level, pos.offset(-1, 0, 0));
                for (int i = -1; i < 2; i++) {
                    for (int j = 2; j < 6; j++) {
                        removePortalBlock(level, pos.offset(i, -j, 0));
                    }
                }
            }else{
                removePortalBlock(level, pos.offset(0, 0, 1));
                removePortalBlock(level, pos.offset(0, 0, -1));
                for (int i = -1; i < 2; i++) {
                    for (int j = 2; j < 6; j++) {
                        removePortalBlock(level, pos.offset(0, -j, i));
                    }
                }
            }
        }
    }
    private void removePortalBlock(Level level,BlockPos pos){
        if(level.getBlockState(pos).getBlock() instanceof KakuriyoPortalBlock){
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            level.playSound((Player) null,pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS,0.5F,1F);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean boo) {
        if(state.getBlock() instanceof KakuriyoPortalCoreBlock) {
            if (state.getValue(FACING).getAxis() == Direction.Axis.Z) {
                placePortalBlock(state.getValue(FACING), level, pos.offset(1, 0, 0));
                placePortalBlock(state.getValue(FACING), level, pos.offset(-1, 0, 0));
                for (int i = -1; i < 2; i++) {
                    for (int j = 2; j < 6; j++) {
                        placePortalBlock(state.getValue(FACING), level, pos.offset(i, -j, 0));
                    }
                }
            }else{
                placePortalBlock(state.getValue(FACING), level, pos.offset(0, 0, 1));
                placePortalBlock(state.getValue(FACING), level, pos.offset(0, 0, -1));
                for (int i = -1; i < 2; i++) {
                    for (int j = 2; j < 6; j++) {
                        placePortalBlock(state.getValue(FACING), level, pos.offset(0, -j,i));
                    }
                }
            }
        }
    }
    private void placePortalBlock(Direction direction,Level level,BlockPos pos){
        if(level.getBlockState(pos).canBeReplaced()||level.getFluidState(pos).is(Fluids.WATER)){
            level.setBlockAndUpdate(pos, ItemAndBlockRegister.kakuriyo_portal.get().defaultBlockState().setValue(FACING,direction));
            level.playSound((Player) null,pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS,0.5F,1F);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING,context.getHorizontalDirection());
    }
}
