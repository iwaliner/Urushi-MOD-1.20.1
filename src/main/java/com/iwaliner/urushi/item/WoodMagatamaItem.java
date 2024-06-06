package com.iwaliner.urushi.item;


import com.iwaliner.urushi.util.ElementType;
import org.jetbrains.annotations.Nullable;

public class WoodMagatamaItem extends AbstractMagatamaItem{
    public WoodMagatamaItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Nullable
    @Override
    public ElementType getElementType() {
        return ElementType.WoodElement;
    }
}
