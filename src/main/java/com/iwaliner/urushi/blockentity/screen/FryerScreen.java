package com.iwaliner.urushi.blockentity.screen;

import com.iwaliner.urushi.blockentity.menu.FryerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FryerScreen extends AbstractFryerScreen<FryerMenu>{
    private static final ResourceLocation TEXTURE = new ResourceLocation("urushi:textures/gui/fryer.png");

    public FryerScreen(FryerMenu p_i51089_1_, Inventory p_i51089_2_, Component p_i51089_3_) {
        super(p_i51089_1_, p_i51089_2_, p_i51089_3_, TEXTURE);
    }

}
