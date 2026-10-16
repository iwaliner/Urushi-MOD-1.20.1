package com.iwaliner.urushi.util;

import com.mojang.text2speech.Narrator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.LanguageSelectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MemoryScreen extends Screen {
    private static final Component ONBOARDING_NARRATOR_MESSAGE2 = Component.translatable("accessibility.onboarding.screen.narrator");
    private static final Component ONBOARDING_NARRATOR_MESSAGE1 = Component.translatable("accessibility.urushi.not_enough_memory.narrator");
    private static final MutableComponent TEXT1 = Component.translatable("info.urushi.not_enough_memory_title1").withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.BOLD);
    private static final Component TEXT2 = Component.translatable("info.urushi.not_enough_memory_title2").withStyle(ChatFormatting.WHITE);
    private static final MutableComponent TEXT3 = Component.translatable("info.urushi.not_enough_memory_howto1").withStyle(ChatFormatting.GRAY);
    private static final Component TEXT4 = Component.translatable("info.urushi.not_enough_memory_howto2").withStyle(ChatFormatting.GRAY);
    private static final Component titleText=TEXT1.append(TEXT2);
    private final PanoramaRenderer panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
    private final LogoRenderer logoRenderer;
    private final Options options;
    private final boolean narratorAvailable;
    private boolean hasNarrated;
    private float timer;
    private boolean showHowTo;
    private boolean firstTime;
    @Nullable
    private AccessibilityOnboardingTextWidget textWidget;

    public MemoryScreen(Options p_265483_,boolean flag,boolean flag2) {
        super(titleText);
        this.options = p_265483_;
        this.logoRenderer = new LogoRenderer(true);
        this.narratorAvailable = Minecraft.getInstance().getNarrator().isActive();
        this.showHowTo=flag;
        this.firstTime=flag2;
    }
    public void init() {
        int i = this.initTitleYPos();
        FrameLayout framelayout = new FrameLayout(this.width, this.height - i);
        framelayout.defaultChildLayoutSetting().alignVerticallyTop().padding(2);
        GridLayout gridlayout = framelayout.addChild(new GridLayout());
        gridlayout.defaultCellSetting().alignHorizontallyCenter().padding(2);
        GridLayout.RowHelper gridlayout$rowhelper = gridlayout.createRowHelper(1);
        gridlayout$rowhelper.defaultCellSetting().padding(5);
        this.textWidget = new AccessibilityOnboardingTextWidget(this.font, this.title, this.width);
        gridlayout$rowhelper.addChild(this.textWidget, gridlayout$rowhelper.newCellSettings().paddingBottom(0));
        Component buttonShowHowToName=showHowTo? Component.translatable("info.urushi.close_howto") : Component.translatable("info.urushi.show_howto");
        gridlayout$rowhelper.addChild(Button.builder(buttonShowHowToName, (p_267841_) -> {
            this.closeAndSetScreen(new MemoryScreen(Minecraft.getInstance().options, !showHowTo,false));
        }).width(200).build(), framelayout.newChildLayoutSettings().alignVerticallyMiddle().padding(6));

        if(showHowTo) {
            AccessibilityOnboardingTextWidget textWidget2 = new AccessibilityOnboardingTextWidget(this.font, TEXT3, this.width - 40);
            textWidget2.setCentered(false);
            gridlayout$rowhelper.addChild(textWidget2, gridlayout$rowhelper.newCellSettings().paddingBottom(5));
            AccessibilityOnboardingTextWidget textWidget3 = new AccessibilityOnboardingTextWidget(this.font, TEXT4, this.width - 40);
            textWidget3.setCentered(false);
            gridlayout$rowhelper.addChild(textWidget3, gridlayout$rowhelper.newCellSettings().paddingBottom(10));
        }else{
            AbstractWidget abstractwidget = this.options.narrator().createButton(this.options, 0, 0, 150);
            abstractwidget.active = this.narratorAvailable;
            gridlayout$rowhelper.addChild(abstractwidget);
            if (this.narratorAvailable) {
                this.setInitialFocus(abstractwidget);
            }
            gridlayout$rowhelper.addChild(CommonButtons.languageTextAndImage((p_280781_) -> {
                this.closeAndSetScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager()));
            }));
            gridlayout$rowhelper.addChild(Button.builder(Component.translatable("info.urushi.close_screen"), (p_267841_) -> {
                this.onClose();
            }).build(), framelayout.newChildLayoutSettings().alignVerticallyMiddle().padding(0));
        }

        framelayout.arrangeElements();
        FrameLayout.alignInRectangle(framelayout, 0, i, this.width, this.height, 0.5F, 0.0F);
        framelayout.visitWidgets(this::addRenderableWidget);
    }

    private int initTitleYPos() {
        return  30;
    }

    public void onClose() {
        this.closeAndSetScreen(new TitleScreen(true, this.logoRenderer));
    }

    private void closeAndSetScreen(Screen p_272914_) {
        this.options.onboardAccessibility = false;
        this.options.save();
        Narrator.getNarrator().clear();
        this.minecraft.setScreen(p_272914_);
    }

    public void render(GuiGraphics p_282353_, int p_265135_, int p_265032_, float p_265387_) {
        if(firstTime) {
            this.handleInitialNarrationDelay();
        }
        this.panorama.render(0.0F, 1.0F);
        p_282353_.fill(0, 0, this.width, this.height, -1877995504);
        // this.logoRenderer.renderLogo(p_282353_, this.width, 1.0F);
        if (this.textWidget != null) {
            this.textWidget.render(p_282353_, p_265135_, p_265032_, p_265387_);
        }

        super.render(p_282353_, p_265135_, p_265032_, p_265387_);
    }

    private void handleInitialNarrationDelay() {
        if (!this.hasNarrated && this.narratorAvailable) {
            if (this.timer < 40.0F) {
                ++this.timer;
            } else if (this.minecraft.isWindowActive()) {
                Narrator.getNarrator().say(ONBOARDING_NARRATOR_MESSAGE1.getString(), true);
                Narrator.getNarrator().say(ONBOARDING_NARRATOR_MESSAGE2.getString(), true);
                this.hasNarrated = true;
            }
        }

    }
}
