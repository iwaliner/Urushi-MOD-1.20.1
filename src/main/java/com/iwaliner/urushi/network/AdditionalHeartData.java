package com.iwaliner.urushi.network;

import net.minecraft.nbt.CompoundTag;

public class AdditionalHeartData {
    private int additionalHeart;


    public int getAdditionalHeartValue() {
        return additionalHeart;
    }

    public void increaseHeart() {
        ++this.additionalHeart;
    }

    public void decreaseHeart() {
        if(additionalHeart>0) {
            --this.additionalHeart;
        }else{
            additionalHeart=0;
        }
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("UrushiAdditionalHeart", additionalHeart);
    }

    public void loadNBTData(CompoundTag nbt) {
        additionalHeart = nbt.getInt("UrushiAdditionalHeart");
    }
}
