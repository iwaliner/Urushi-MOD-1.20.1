package com.iwaliner.urushi.util;

import com.mojang.text2speech.Narrator;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class MemoryScreen extends Screen {
    private static final Component ONBOARDING_NARRATOR_MESSAGE2 = Component.translatable("accessibility.onboarding.screen.narrator");
    private static final Component ONBOARDING_NARRATOR_MESSAGE1 = Component.translatable("accessibility.urushi.max_memory_is_not_enough.narrator");
    private static final int PADDING = 4;
    private static final int TITLE_PADDING = 16;
    private final PanoramaRenderer panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
    private final LogoRenderer logoRenderer;
    private final Options options;
    private final boolean narratorAvailable;
    private boolean hasNarrated;
    private float timer;
    @Nullable
    private AccessibilityOnboardingTextWidget textWidget;

    public MemoryScreen(Options p_265483_) {
        super(Component.translatable("info.urushi.max_memory_is_not_enough"));
        this.options = p_265483_;
        this.logoRenderer = new LogoRenderer(true);
        this.narratorAvailable = Minecraft.getInstance().getNarrator().isActive();
    }

    public void init() {
        int i = this.initTitleYPos();
        FrameLayout framelayout = new FrameLayout(this.width, this.height - i);
        framelayout.defaultChildLayoutSetting().alignVerticallyTop().padding(4);
        GridLayout gridlayout = framelayout.addChild(new GridLayout());
        gridlayout.defaultCellSetting().alignHorizontallyCenter().padding(4);
        GridLayout.RowHelper gridlayout$rowhelper = gridlayout.createRowHelper(1);
        gridlayout$rowhelper.defaultCellSetting().padding(2);
        this.textWidget = new AccessibilityOnboardingTextWidget(this.font, this.title, this.width);
        gridlayout$rowhelper.addChild(this.textWidget, gridlayout$rowhelper.newCellSettings().paddingBottom(16));
        AbstractWidget abstractwidget = this.options.narrator().createButton(this.options, 0, 0, 150);
        abstractwidget.active = this.narratorAvailable;
        gridlayout$rowhelper.addChild(abstractwidget);
        if (this.narratorAvailable) {
            this.setInitialFocus(abstractwidget);
        }

        /*gridlayout$rowhelper.addChild(CommonButtons.accessibilityTextAndImage((p_280782_) -> {
            this.closeAndSetScreen(new AccessibilityOptionsScreen(this, this.minecraft.options));
        }));
        gridlayout$rowhelper.addChild(CommonButtons.languageTextAndImage((p_280781_) -> {
            this.closeAndSetScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager()));
        }));*/
        framelayout.addChild(Button.builder(CommonComponents.GUI_CONTINUE, (p_267841_) -> {
            this.onClose();
        }).build(), framelayout.newChildLayoutSettings().alignVerticallyMiddle().padding(8));
        framelayout.arrangeElements();
        FrameLayout.alignInRectangle(framelayout, 0, i, this.width, this.height, 0.5F, 0.0F);
        framelayout.visitWidgets(this::addRenderableWidget);
    }

    private int initTitleYPos() {
        //return 90;
        return  50;
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
        this.handleInitialNarrationDelay();
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
