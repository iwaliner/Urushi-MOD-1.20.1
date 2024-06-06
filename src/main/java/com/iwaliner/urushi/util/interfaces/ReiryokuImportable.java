package com.iwaliner.urushi.util.interfaces;

import com.iwaliner.urushi.blockentity.AbstractReiryokuStorableBlockEntity;
import com.iwaliner.urushi.util.ElementType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface ReiryokuImportable {


    /**搬入する気*/
    ElementType getImportElementType();




}
