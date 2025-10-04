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

public class InvisiblePressurePlateBlockEntity extends AbstractInvisibleBlockEntity {

    public InvisiblePressurePlateBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.InvisiblePressurePlate.get(), pos, state);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, InvisiblePressurePlateBlockEntity blockEntity) {
        tick(level, pos, state, blockEntity, ItemAndBlockRegister.invisible_pressure_plate.get(), () -> {
            level.setBlock(pos, ItemAndBlockRegister.hidden_invisible_pressure_plate.get().defaultBlockState(), 2);
        });
    }


}
