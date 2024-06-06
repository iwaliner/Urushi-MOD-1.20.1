package com.iwaliner.urushi.util.interfaces;

import com.iwaliner.urushi.block.MirrorBlock;
import com.iwaliner.urushi.util.ElementType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface ReiryokuStorable {
    /**霊力の最大貯蔵容量*/
    int getReiryokuCapacity();

    /**霊力の現在の貯蔵量*/
    int getStoredReiryoku();

    /**霊力の貯蔵量を増加*/
    void addStoredReiryoku(int i);

    /**霊力の貯蔵量を増加させる容量があるか*/
    boolean canAddReiryoku(int i);

    /**霊力の貯蔵量を減少*/
    void decreaseStoredReiryoku(int i);

    /**霊力の貯蔵量を減少させられるか*/
    boolean canDecreaseReiryoku(int i);


    /**貯蔵する元素*/
    ElementType getStoredElementType();

    /**tick更新ごとに、霊力を受信可能なら受信する*/

    void recieveReiryoku(Level level, BlockPos pos);


    /**パーティクルが届いて霊力が増加するまでのtick数*/
     int getReceiveWaitingTime();

     /**パーティクルが届いて霊力が増加するまでのtick数を指定*/
    void setReceiveWaitingTime(int i);

    /**非稼働状態ならtrue*/
     boolean isIdle();

     /**受け取る元素*/
     ElementType getReceiveElementType();

     /**受け取る元素を指定*/
    void setReceiveElementType(ElementType type);

    /**次に霊力が増加するときの増加量*/
    int getReceiveAmount();

    /**受け渡す霊力の量*/
    void setReceiveAmount(int i);

    /**更新させる*/
    void markUpdated();


}
