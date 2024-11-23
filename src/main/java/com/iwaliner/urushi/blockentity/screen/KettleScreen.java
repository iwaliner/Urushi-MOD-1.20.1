package com.iwaliner.urushi.blockentity.screen;

import com.iwaliner.urushi.blockentity.menu.KettleMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KettleScreen extends AbstractContainerScreen<KettleMenu>

{
    private boolean widthTooNarrow;
    private final ResourceLocation texture;

    public KettleScreen(KettleMenu p_i51104_1_, Inventory p_i51104_3_, Component p_i51104_4_) {
        super( p_i51104_1_, p_i51104_3_, p_i51104_4_);
        this.texture = new ResourceLocation("urushi:textures/gui/silkworm_farm.png");
    }

    public void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    public void containerTick() {
        super.containerTick();

    }


    public void render(GuiGraphics p_282573_, int p_97859_, int p_97860_, float p_97861_) {
        this.renderBackground(p_282573_);
        this.renderBg(p_282573_, p_97861_, p_97859_, p_97860_);
        super.render(p_282573_, p_97859_, p_97860_, p_97861_);
        this.renderTooltip(p_282573_, p_97859_, p_97860_);
    }
    protected void renderBg(GuiGraphics p_282928_, float p_281631_, int p_281252_, int p_281891_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);
        int i = this.leftPos;
        int j = this.topPos;
        p_282928_.blit(this.texture, i, j, 0, 0, this.imageWidth, this.imageHeight);

        int l = this.menu.getBurnProgress();
        p_282928_.blit(this.texture, i + 79, j + 34, 176, 31, l + 1, 16);
    }


    protected void slotClicked(Slot p_184098_1_, int p_184098_2_, int p_184098_3_, ClickType p_184098_4_) {
        super.slotClicked(p_184098_1_, p_184098_2_, p_184098_3_, p_184098_4_);
    }


    public void removed() {
        super.removed();
    }
}
