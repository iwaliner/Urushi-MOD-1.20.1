package com.iwaliner.urushi.blockentity.screen;


import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.blockentity.menu.DoubledWoodenCabinetryMenu;
import com.iwaliner.urushi.util.UrushiUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DoubledWoodenCabinetryScreen  extends AbstractContainerScreen<DoubledWoodenCabinetryMenu>

{


    private final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("urushi:textures/gui/doubled_wooden_cabinetry.png");

    public DoubledWoodenCabinetryScreen(DoubledWoodenCabinetryMenu p_i51104_1_, Inventory p_i51104_3_, Component p_i51104_4_) {
        super(p_i51104_1_, p_i51104_3_, p_i51104_4_);
        this.imageWidth=304;
        int i = 222;
        int j = 114;
        this.imageHeight = 242;
        this.inventoryLabelY = 147;
        this.inventoryLabelX += 62;

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
        p_230450_1_.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, this.imageHeight, 512, 512);
        if (isMouseOver(p_230450_3_, p_230450_4_)) {
            p_230450_1_.blit(CONTAINER_BACKGROUND, i + 224, j + 126, 0, 247, 70, 263, 512, 512);
        }
        if (UrushiUtils.isJapanese()) {
            if (UrushiUtils.isShogatsu()) {
                p_230450_1_.blit(CONTAINER_BACKGROUND, i + 248, j + 145, 310, 0, 32, 89, 512, 512);
            } else if (UrushiUtils.isSetsubun()) {
                p_230450_1_.blit(CONTAINER_BACKGROUND, i + 248, j + 145, 343, 0, 32, 89, 512, 512);
            } else if (UrushiUtils.isHigan()) {
                p_230450_1_.blit(CONTAINER_BACKGROUND, i + 248, j + 145, 376, 0, 32, 89, 512, 512);
            } else if (UrushiUtils.isTanabata()) {
                p_230450_1_.blit(CONTAINER_BACKGROUND, i + 248, j + 145, 409, 0, 32, 89, 512, 512);
            }
        }
    }
    public boolean mouseClicked(double x, double y, int p_99320_) {
     int l=0;
     if(x<this.leftPos+294&&x>this.leftPos+224&&y>this.topPos +126&&y<this.topPos +142) {
         if (this.menu.clickMenuButton(this.minecraft.player, l)) {
             Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
             this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, l);
             return true;
         }
     }
        return super.mouseClicked(x, y, p_99320_);
    }


    protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
        guiGraphics.drawCenteredString(this.font, Component.translatable("info.urushi.doubled_wooden_cabinetry_sort_button"), 259, 130, 14737632);
        super.renderLabels(guiGraphics,i,j);
    }


    @Override
    public boolean isMouseOver(double x, double y) {
        return x < this.leftPos + 294 && x > this.leftPos + 224 && y > this.topPos + 126 && y < this.topPos + 142;
    }
}
