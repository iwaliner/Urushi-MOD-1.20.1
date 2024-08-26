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

public class InvisibleLeverBlockEntity extends BlockEntity {
    public int time;

    public InvisibleLeverBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.InvisibleLever.get(), p_155550_, p_155551_);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.time = tag.getInt("time");
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("time", this.time);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InvisibleLeverBlockEntity blockEntity) {
        if (state.getBlock() instanceof InvisibleLeverBlock) {
            --blockEntity.time;

        if (!level.isClientSide() && blockEntity.time <= 0) {
            level.setBlock(pos, ItemAndBlockRegister.hidden_invisible_lever.get().defaultBlockState().setValue(HiddenInvisibleLeverBlock.POWERED, state.getValue(InvisibleLeverBlock.POWERED)).setValue(HiddenInvisibleLeverBlock.FACING, state.getValue(InvisibleLeverBlock.FACING)).setValue(HiddenInvisibleLeverBlock.FACE, state.getValue(InvisibleLeverBlock.FACE)), 2);
        }
    }
}

    }
