package com.iwaliner.urushi.util.interfaces;

import com.iwaliner.urushi.util.ElementType;

import javax.annotation.Nullable;

public interface HasReiryokuItem {
    @Nullable
    int getReiryokuCapacity();


    @Nullable
    ElementType getElementType();
}
