package com.iwaliner.urushi.blockentity;


import com.iwaliner.urushi.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public  class SpikeBlockEntity extends BlockEntity {
    private Player player;


    public SpikeBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.Spike.get(), p_155052_, p_155053_);
    }
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        if(level!=null) {
            player = level.getPlayerByUUID(tag.getUUID("playerUUID"));
        }
    }

    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putUUID("playerUUID",player.getUUID());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
