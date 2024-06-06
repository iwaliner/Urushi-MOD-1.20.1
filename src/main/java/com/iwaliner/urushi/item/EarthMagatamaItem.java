package com.iwaliner.urushi.item;


import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.interfaces.HasReiryokuItem;
import org.jetbrains.annotations.Nullable;

public class EarthMagatamaItem extends AbstractMagatamaItem {
    public EarthMagatamaItem(Properties p_41383_) {
        super(p_41383_);
    }
    @Nullable
    @Override
    public ElementType getElementType() {
        return ElementType.EarthElement;
    }

}
