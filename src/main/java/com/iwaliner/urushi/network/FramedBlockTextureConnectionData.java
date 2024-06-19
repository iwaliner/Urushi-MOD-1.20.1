package com.iwaliner.urushi.network;

import net.minecraft.nbt.CompoundTag;

public class FramedBlockTextureConnectionData {
    private boolean isPressed;


    public boolean isPressed() {
        return isPressed;
    }
    public void setPressed(boolean isPressed) {
        this.isPressed=isPressed;
    }

    public void toggle() {
        this.isPressed=!this.isPressed;
    }

    public void copyFrom(FramedBlockTextureConnectionData source) {
        this.isPressed = source.isPressed;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("FramedBlockTextureConnectionKeyIsPressed", isPressed);
    }

    public void loadNBTData(CompoundTag nbt) {
        isPressed = nbt.getBoolean("FramedBlockTextureConnectionKeyIsPressed");
    }
}
