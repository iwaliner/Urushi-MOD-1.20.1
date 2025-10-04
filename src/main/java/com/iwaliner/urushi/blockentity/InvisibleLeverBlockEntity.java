package com.iwaliner.urushi.blockentity;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.HiddenInvisibleButtonBlock;
import com.iwaliner.urushi.block.HiddenInvisibleLeverBlock;
import com.iwaliner.urushi.block.InvisibleButtonBlock;
import com.iwaliner.urushi.block.InvisibleLeverBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InvisibleLeverBlockEntity extends AbstractInvisibleBlockEntity {

    public InvisibleLeverBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.InvisibleLever.get(), p_155550_, p_155551_);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, InvisibleLeverBlockEntity blockEntity) {
        tick(level, pos, state, blockEntity, ItemAndBlockRegister.invisible_lever.get(), () -> {
            BlockState newState = ItemAndBlockRegister.hidden_invisible_lever.get().defaultBlockState()
                .setValue(HiddenInvisibleButtonBlock.POWERED, state.getValue(InvisibleButtonBlock.POWERED))
                .setValue(HiddenInvisibleButtonBlock.FACING, state.getValue(InvisibleButtonBlock.FACING))
                .setValue(HiddenInvisibleButtonBlock.FACE, state.getValue(InvisibleButtonBlock.FACE));
            level.setBlock(pos, newState, 2);
        });
    }
}
