package com.iwaliner.urushi.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MemoryScreen extends Screen {
    public MemoryScreen(Component p_96550_) {
        super(p_96550_);
    }
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 30, 16777215);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }
}
