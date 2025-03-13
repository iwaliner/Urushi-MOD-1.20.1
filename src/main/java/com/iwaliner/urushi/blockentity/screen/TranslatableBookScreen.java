package com.iwaliner.urushi.blockentity.screen;

import com.ibm.icu.lang.CharacterProperties;
import com.ibm.icu.lang.UProperty;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.blockentity.menu.DoubledWoodenCabinetryMenu;
import com.iwaliner.urushi.blockentity.menu.TranslatableBookMenu;
import com.iwaliner.urushi.util.UrushiUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import static com.ibm.icu.lang.UCharacter.DecompositionType.NARROW;
import static com.ibm.icu.lang.UCharacter.DecompositionType.WIDE;
import static com.ibm.icu.lang.UCharacter.EastAsianWidth.FULLWIDTH;
import static com.ibm.icu.lang.UCharacter.EastAsianWidth.HALFWIDTH;
import static com.ibm.icu.lang.UCharacter.LineBreak.AMBIGUOUS;

@OnlyIn(Dist.CLIENT)
public class TranslatableBookScreen extends AbstractContainerScreen<TranslatableBookMenu> {
    public static final ResourceLocation BOOK_LOCATION = new ResourceLocation(ModCoreUrushi.ModID,"textures/gui/book.png");


    public TranslatableBookScreen(TranslatableBookMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super(p_97741_, p_97742_, p_97743_);
        this.imageHeight=512;
        this.imageWidth=512;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i01, int i02, float b0) {
        //this.renderBackground(guiGraphics);
        this.renderBg(guiGraphics, b0, i01, i02);
        //super.render(guiGraphics, i01, i02, b0);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f1, int f2, int f3) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BOOK_LOCATION);

        int i = (this.width - 280) / 2;
        int j = (this.height - 180) / 2;
        guiGraphics.blit(BOOK_LOCATION, i, j, 0, 0, 280, 280, 280, 280);

        //guiGraphics.blit(BOOK_LOCATION, i, 2, 65, 0, 192, 192);
        //guiGraphics.blit(BOOK_LOCATION, i+60, 2, 65, 0, 192, 192);
        int page=getMenu().getPage();
        if(page==0){
            String string1=Component.translatable("book.urushi.chronicles1").getString();
            String[] strings2 = string1.split("<br>");
            int i1=0;
            for(int l1=0;l1<strings2.length;l1++){
                String string2=strings2[l1];
                int string2LengthLimit=13;
                if (string2.matches("^[0-9a-zA-Z]+$")) { //半角のみで構成されている場合
                    string2LengthLimit=19;
                }
                int string2Length=string2.length();
                int l=Mth.floor((float) string2Length /(float) string2LengthLimit);
                for(int l2=0;l2< l;l2++) {
                    String string3 = string2.substring(l2*string2LengthLimit+0, l2*string2LengthLimit+string2LengthLimit);
                    if(i1>14) {
                        guiGraphics.drawString(this.font, string3, i + 149, j+15+(i1-15)*10, 0, false);
                    }else{
                        guiGraphics.drawString(this.font, string3, i + 17, j+15+i1*10, 0, false);
                    }
                    i1+=1;
                }
                String string4=string2.substring(l*string2LengthLimit+0, string2Length);
                if(i1>14) {
                    guiGraphics.drawString(this.font, string4, i + 149, j+15+(i1-15)*10, 0, false);
                }else {
                    guiGraphics.drawString(this.font, string4, i + 17, j + 15 + i1 * 10, 0, false);
                }
                i1+=1;
            }

         }
    }

    public boolean mouseClicked(double x, double y, int ii) {
        if(isButtonA(x,y)) {
            if (this.menu.clickMenuButton(this.minecraft.player, 0)) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
                return true;
            }
        }else if(isButtonB(x,y)) {
            if (this.menu.clickMenuButton(this.minecraft.player, 1)) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 1);
                return true;
            }
        }
        return super.mouseClicked(x, y, ii);
    }
    private boolean isButtonA(double x, double y) {
        return x<this.leftPos+294&&x>this.leftPos+224&&y>this.topPos +126&&y<this.topPos +142;
    }
    private boolean isButtonB(double x, double y) {
        return x < this.leftPos + 76 && x > this.leftPos + 53 && y > this.topPos +26 && y < this.topPos + 41;
    }
}
