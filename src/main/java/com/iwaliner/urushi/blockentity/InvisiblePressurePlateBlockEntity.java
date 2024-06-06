package com.iwaliner.urushi.blockentity;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.HiddenInvisibleButtonBlock;
import com.iwaliner.urushi.block.HiddenInvisiblePressurePlateBlock;
import com.iwaliner.urushi.block.InvisibleButtonBlock;
import com.iwaliner.urushi.block.InvisiblePressurePlateBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InvisiblePressurePlateBlockEntity extends BlockEntity {
    public int time;

    public InvisiblePressurePlateBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.InvisiblePressurePlate.get(), p_155550_, p_155551_);
    }
    public void load(CompoundTag tag) {
        super.load(tag);
        this.time = tag.getInt("time");
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("time", this.time);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, InvisiblePressurePlateBlockEntity blockEntity) {
        --blockEntity.time;
        if(!level.isClientSide()&&blockEntity.time<=0) {
            level.setBlock(pos, ItemAndBlockRegister.hidden_invisible_pressure_plate.get().defaultBlockState(), 2);
        }
    }


    }
