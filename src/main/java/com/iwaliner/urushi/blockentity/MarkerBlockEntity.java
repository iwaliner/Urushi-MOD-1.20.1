package com.iwaliner.urushi.blockentity;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.HiddenInvisibleLeverBlock;
import com.iwaliner.urushi.block.InvisibleLeverBlock;
import com.iwaliner.urushi.block.MarkerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MarkerBlockEntity extends BlockEntity {
    public BlockPos posMarkerX;
    public BlockPos posMarkerY;
    public BlockPos posMarkerZ;

    public MarkerBlockEntity(BlockPos p_155550_, BlockState p_155551_) {
        super(BlockEntityRegister.Marker.get(), p_155550_, p_155551_);
    }

    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if(tag.contains("posMarkerX")) {
            this.posMarkerX = NbtUtils.readBlockPos(tag.getCompound("posMarkerX"));
        }
        if(tag.contains("posMarkerY")) {
            this.posMarkerY = NbtUtils.readBlockPos(tag.getCompound("posMarkerY"));
        }
        if(tag.contains("posMarkerZ")) {
            this.posMarkerZ = NbtUtils.readBlockPos(tag.getCompound("posMarkerZ"));
        }
    }

    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        if(posMarkerX!=null) {
            tag.put("posMarkerX", NbtUtils.writeBlockPos(posMarkerX));
        }
        if(posMarkerY!=null) {
            tag.put("posMarkerY", NbtUtils.writeBlockPos(posMarkerY));
        }
        if(posMarkerZ!=null) {
            tag.put("posMarkerZ", NbtUtils.writeBlockPos(posMarkerZ));
        }
    }
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if(posMarkerX!=null) {
            tag.put("posMarkerX", NbtUtils.writeBlockPos(posMarkerX));
        }
        if(posMarkerY!=null) {
            tag.put("posMarkerY", NbtUtils.writeBlockPos(posMarkerY));
        }
        if(posMarkerZ!=null) {
            tag.put("posMarkerZ", NbtUtils.writeBlockPos(posMarkerZ));
        }
        return tag;
    }
    public boolean hasRange(){
        return posMarkerX!=null&&posMarkerY!=null&&posMarkerZ!=null;
    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
