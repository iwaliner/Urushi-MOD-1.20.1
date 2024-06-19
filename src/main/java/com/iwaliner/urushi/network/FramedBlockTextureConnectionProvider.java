package com.iwaliner.urushi.network;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class FramedBlockTextureConnectionProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<FramedBlockTextureConnectionData> FRAMED_BLOCK_TEXTURE_CONNECTION = CapabilityManager.get(new CapabilityToken<FramedBlockTextureConnectionData>() { });

    private FramedBlockTextureConnectionData data = null;
    private final LazyOptional<FramedBlockTextureConnectionData> optional = LazyOptional.of(this::createData);

    private FramedBlockTextureConnectionData createData() {
        if(this.data == null) {
            this.data = new FramedBlockTextureConnectionData();
        }

        return this.data;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == FRAMED_BLOCK_TEXTURE_CONNECTION) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createData().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createData().loadNBTData(nbt);
    }
}
