package com.iwaliner.urushi.blockentity;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ParticleRegister;
import com.iwaliner.urushi.block.EmitterBlock;
import com.iwaliner.urushi.block.SacredRockBlock;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.interfaces.ReiryokuExportable;
import com.iwaliner.urushi.util.interfaces.ReiryokuImportable;
import com.iwaliner.urushi.util.interfaces.ReiryokuStorable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TankBlockEntity extends AbstractReiryokuStorableBlockEntity implements ReiryokuImportable,ReiryokuExportable {
    public TankBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.Tank.get(),6000, p_155550_, p_155551_);
    }


    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        this.putBaseTag(compoundtag);
        return compoundtag;
    }



    public static void tick(Level level, BlockPos pos, BlockState state, TankBlockEntity blockEntity) {
       blockEntity.recieveReiryoku(level,pos);
    }



    @Override
    public ElementType getExportElementType() {
        return this.getStoredElementType();
    }

    @Override
    public ElementType getImportElementType() {
        return this.getStoredElementType();
    }

}
