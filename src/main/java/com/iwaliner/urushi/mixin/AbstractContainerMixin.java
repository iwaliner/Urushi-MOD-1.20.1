package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.block.SlideDoorBlock;
import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Set;

/*@Mixin(AbstractContainerScreen.class)*/

public abstract class AbstractContainerMixin {
  /*  @Shadow @Final protected AbstractContainerMenu menu;
    @Shadow @Nullable private Slot clickedSlot;

    @Shadow private ItemStack draggingItem;

    @Shadow private boolean isSplittingStack;

    @Shadow @Final protected Set<Slot> quickCraftSlots;

    @Shadow protected boolean isQuickCrafting;

    @Shadow private int quickCraftingType;

    @Shadow protected abstract void recalculateQuickCraftRemaining();

    @Shadow protected int imageWidth;


    @Shadow protected int leftPos;

    @Shadow protected int topPos;


    @Inject(method = "render",at = @At("TAIL"), cancellable = true)
    private void renderInject(GuiGraphics graphics, int ii0, int ii1, float f1, CallbackInfo ci){
          int i = this.leftPos;
            int j = this.topPos;
            graphics.pose().pushPose();
            graphics.pose().translate((float) i, (float) j, 0.0F);
            for (int k = 0; k < menu.slots.size(); ++k) {
                Slot slot = this.menu.slots.get(k);

                if (slot.isActive()) {
                    renderSlotNew(graphics, slot);
                }

            }
            graphics.pose().popPose();
        }

    private void renderSlotNew(GuiGraphics graphics, Slot slot) {
        int i = slot.x;
        int j = slot.y;
        ItemStack itemstack = slot.getItem();
        boolean flag = false;
        boolean flag1 = slot == this.clickedSlot && !this.draggingItem.isEmpty() && !this.isSplittingStack;
        ItemStack itemstack1 = this.menu.getCarried();
        String s = null;
        if (slot == this.clickedSlot && !this.draggingItem.isEmpty() && this.isSplittingStack && !itemstack.isEmpty()) {
            itemstack = itemstack.copyWithCount(itemstack.getCount() / 2);
        } else if (this.isQuickCrafting && this.quickCraftSlots.contains(slot) && !itemstack1.isEmpty()) {
            if (this.quickCraftSlots.size() == 1) {
                return;
            }

            if (AbstractContainerMenu.canItemQuickReplace(slot, itemstack1, true) && this.menu.canDragTo(slot)) {
                flag = true;
                int k = Math.min(itemstack1.getMaxStackSize(), slot.getMaxStackSize(itemstack1));
                int l = slot.getItem().isEmpty() ? 0 : slot.getItem().getCount();
                int i1 = AbstractContainerMenu.getQuickCraftPlaceCount(this.quickCraftSlots, this.quickCraftingType, itemstack1) + l;
                if (i1 > k) {
                    i1 = k;
                    s = ChatFormatting.YELLOW.toString() + k;
                }

                itemstack = itemstack1.copyWithCount(i1);
            } else {
                this.quickCraftSlots.remove(slot);
                this.recalculateQuickCraftRemaining();
            }
        }

        graphics.pose().pushPose();
        graphics.pose().translate(0.0F, 0.0F, 100.0F);
        if (itemstack.isEmpty() && slot.isActive()) {
            Pair<ResourceLocation, ResourceLocation> pair = slot.getNoItemIcon();
            if (pair != null) {
                TextureAtlasSprite textureatlassprite = Minecraft.getInstance().getTextureAtlas(pair.getFirst()).apply(pair.getSecond());
                graphics.blit(i, j, 0, 16, 16, textureatlassprite);
                flag1 = true;
            }
        }

        if (!flag1) {
            if (flag) {
                graphics.fill(i, j, i + 16, j + 16, -2130706433);
            }

            graphics.renderItem(itemstack, i, j, slot.x + slot.y * this.imageWidth);
            graphics.renderItemDecorations(Minecraft.getInstance().font, itemstack, i, j, s);
        }

        graphics.pose().popPose();
    }
   *//* @Inject(method = "renderSlot",at = @At("HEAD"), cancellable = true)
    private void renderSlotInject(GuiGraphics p_281607_, Slot p_282613_, CallbackInfo ci){
        ci.cancel();
    }
*/
}
