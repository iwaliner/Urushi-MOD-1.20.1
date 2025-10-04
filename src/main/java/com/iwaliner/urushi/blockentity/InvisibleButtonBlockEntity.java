package com.iwaliner.urushi.blockentity;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.HiddenInvisibleButtonBlock;
import com.iwaliner.urushi.block.InvisibleButtonBlock;
import com.iwaliner.urushi.block.RiceCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InvisibleButtonBlockEntity extends AbstractInvisibleBlockEntity {

    public InvisibleButtonBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.InvisibleButton.get(), p_155550_, p_155551_);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, InvisibleButtonBlockEntity blockEntity) {
        tick(level, pos, state, blockEntity, ItemAndBlockRegister.invisible_button.get(), () -> {
            BlockState newState = ItemAndBlockRegister.hidden_invisible_button.get().defaultBlockState()
                .setValue(HiddenInvisibleButtonBlock.POWERED, state.getValue(InvisibleButtonBlock.POWERED))
                .setValue(HiddenInvisibleButtonBlock.FACING, state.getValue(InvisibleButtonBlock.FACING))
                .setValue(HiddenInvisibleButtonBlock.FACE, state.getValue(InvisibleButtonBlock.FACE));
            level.setBlock(pos, newState, 2);
        });
    }

}
