package com.iwaliner.urushi.util.interfaces;

import com.iwaliner.urushi.block.MirrorBlock;
import com.iwaliner.urushi.util.ComplexDirection;
import com.iwaliner.urushi.util.ElementType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public interface Mirror {

    /**入射角を参照*/
    ComplexDirection getIncidentDirection();

    /**入射角を設定*/
    void setIncidentDirection(ComplexDirection direction);

    /**入射角を設定*/
    void setIncidentDirection(Direction direction);

    boolean getCanReach();
}
