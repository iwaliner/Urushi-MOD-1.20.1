package com.iwaliner.urushi.item;

import com.iwaliner.urushi.util.ElementType;
import org.jetbrains.annotations.Nullable;

public class WaterMagatamaItem extends AbstractMagatamaItem  {
    public WaterMagatamaItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Nullable
    @Override
    public ElementType getElementType() {
        return ElementType.WaterElement;
    }
}
