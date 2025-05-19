package com.iwaliner.urushi.blockentity.screen;


import com.iwaliner.urushi.blockentity.menu.DoubledWoodenCabinetryMenu;
import com.iwaliner.urushi.blockentity.menu.FillerMenu;
import com.iwaliner.urushi.util.UrushiUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FillerScreen extends AbstractContainerScreen<FillerMenu>

{


    private final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("urushi:textures/gui/filler.png");

    public FillerScreen(FillerMenu p_i51104_1_, Inventory p_i51104_3_, Component p_i51104_4_) {
        super(p_i51104_1_, p_i51104_3_, p_i51104_4_);
        this.imageWidth=176;
        this.imageHeight = 215+11;
        this.inventoryLabelY = 120+11;
    }

    public void init() {
        super.init();
   }

    public void containerTick() {
        super.containerTick();

    }

    public void render(GuiGraphics guiGraphics, int p_99250_, int p_99251_, float p_99252_) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, p_99250_, p_99251_, p_99252_);
        this.renderTooltip(guiGraphics, p_99250_, p_99251_);
       }

    protected void renderBg(GuiGraphics guiGraphics, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.CONTAINER_BACKGROUND);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 256);
        int index= menu.getIndex();
        if(index!=0){
            int ii = (index-1)%7;
            int jj = (index-1)/7;
            guiGraphics.blit(CONTAINER_BACKGROUND, i+8+20*ii, j+14+20*jj, 0, 215+11, 20, 20, 256, 256);
        }
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10, j+16, 176, 0, 16, 16, 256, 256);
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10+20*1, j+16, 240, 0, 16, 16, 256, 256);
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10+20*2, j+16, 192, 0, 16, 16, 256, 256);
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10+20*3, j+16, 208, 0, 16, 16, 256, 256);
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10+20*4, j+16, 224, 0, 16, 16, 256, 256);
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10, j+16+20, 176, 16, 16, 16, 256, 256);
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10+20*1, j+16+20, 208, 16, 16, 16, 256, 256);
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10+20*2, j+16+20, 192, 16, 16, 16, 256, 256);
        guiGraphics.blit(CONTAINER_BACKGROUND, i+10+20*3, j+16+20, 224, 16, 16, 16, 256, 256);
    }
    protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
        int index= menu.getIndex();
        guiGraphics.drawCenteredString(this.font, Component.translatable("info.urushi.filler_mode_"+index), 78, 55, 16755200);
        guiGraphics.drawCenteredString(this.font, Component.translatable("info.urushi.filler_mode_"+index+"_description"), 89, 65, 16777215);
        super.renderLabels(guiGraphics,i,j);
    }
    public boolean mouseClicked(double x, double y, int ii) {
        int variable=0;
        for(int i=0;i<7;i++){
            for(int j=0;j<2;j++){
                if(isModeButton(x,y,i,j)){
                    variable = 1 + j * 7 + i ;
                }
            }
        }
        if(variable!=0) {
            if (this.menu.clickMenuButton(this.minecraft.player, variable)) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, variable);
                return true;
            }
        }
        return super.mouseClicked(x, y, ii);
    }
    @Override
    public boolean isMouseOver(double x, double y) {
        for(int i=0;i<7;i++){
            for(int j=0;j<2;j++){
                if(isModeButton(x,y,i,j)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isModeButton(double x, double y,int i,int j) {
        return x < this.leftPos + 28 + i * 20 && x > this.leftPos + 8 + i * 20 && y > this.topPos +14 + j * 20 && y < this.topPos + 34 + j * 20;
    }

}
