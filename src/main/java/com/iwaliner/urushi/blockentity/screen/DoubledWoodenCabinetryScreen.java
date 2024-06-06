package com.iwaliner.urushi.blockentity.screen;


import com.iwaliner.urushi.blockentity.menu.DoubledWoodenCabinetryMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DoubledWoodenCabinetryScreen  extends AbstractContainerScreen<DoubledWoodenCabinetryMenu>

{

    private boolean widthTooNarrow;
    private final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("urushi:textures/gui/doubled_wooden_cabinetry.png");
    private final int containerRows=8;

    public DoubledWoodenCabinetryScreen(DoubledWoodenCabinetryMenu p_i51104_1_, Inventory p_i51104_3_, Component p_i51104_4_) {
        super(p_i51104_1_, p_i51104_3_, p_i51104_4_);
        this.imageWidth+=4*18;
        this.imageHeight+=2*18;
        int i = 222;
        int j = 114;
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void init() {
        super.init();
   }

    public void containerTick() {
        super.containerTick();

    }

    public void render(GuiGraphics p_99249_, int p_99250_, int p_99251_, float p_99252_) {
        this.renderBackground(p_99249_);
        super.render(p_99249_, p_99250_, p_99251_, p_99252_);
        this.renderTooltip(p_99249_, p_99250_, p_99251_);
       }

    protected void renderBg(GuiGraphics p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.CONTAINER_BACKGROUND);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        p_230450_1_.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
        p_230450_1_.blit(CONTAINER_BACKGROUND, i, j + this.containerRows * 18 + 17-(2*18), 0, 126-1, this.imageWidth, 96+(2*18)-2);
    }


}
