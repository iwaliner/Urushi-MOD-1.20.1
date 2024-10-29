package com.iwaliner.urushi.blockentity.screen;


import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.blockentity.AutoCraftingTableBlockEntity;
import com.iwaliner.urushi.blockentity.menu.AbstractFryerMenu;
import com.iwaliner.urushi.blockentity.menu.AutoCraftingTableMenu;
import com.iwaliner.urushi.blockentity.menu.DoubledWoodenCabinetryMenu;
import com.iwaliner.urushi.blockentity.menu.UrushiHopperMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class AutoCraftingTableScreen extends AbstractContainerScreen<AutoCraftingTableMenu>

{

    private static final ResourceLocation CRAFTING_TABLE_LOCATION = new ResourceLocation("urushi:textures/gui/auto_crafting_table.png");
    private final RecipeBookComponent recipeBookComponent = new RecipeBookComponent();

    public AutoCraftingTableScreen(AutoCraftingTableMenu p_i51104_1_, Inventory p_i51104_3_, Component p_i51104_4_) {
        super( p_i51104_1_, p_i51104_3_, p_i51104_4_);
        this.imageHeight =188;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    protected void init() {
        super.init();
        this.leftPos = this.recipeBookComponent.updateScreenPosition(this.width, this.imageWidth);
        this.titleLabelX = 29;
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    public void containerTick() {
        super.containerTick();
    }

    public void render(GuiGraphics p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        this.renderBg(p_230430_1_, p_230430_4_, p_230430_2_, p_230430_3_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }


    protected void renderBg(GuiGraphics poseStack, float p_98475_, int p_98476_, int p_98477_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CRAFTING_TABLE_LOCATION);
        int i = this.leftPos;
        int j = (this.height - this.imageHeight) / 2;
        // blit(poseStack,表示位置のx成分,表示位置のy成分,テクスチャ位置のx成分,テクスチャ位置のy成分,imageWidth,imageHight)
        poseStack.blit(CRAFTING_TABLE_LOCATION, i, j, 0, 0, this.imageWidth, 105);
        poseStack.blit(CRAFTING_TABLE_LOCATION, i, j +104, 0, 105, this.imageWidth, 100);



    }

    @Override
    protected boolean checkHotbarKeyPressed(int i, int j) {
        return false;
    }
}
