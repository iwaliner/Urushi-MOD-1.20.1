package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.block.SlideDoorBlock;
import com.iwaliner.urushi.util.MemoryScreen;
import com.mojang.realmsclient.client.RealmsClient;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.quickplay.QuickPlay;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)

public abstract class MinecraftMixin {
    @Inject(method = "setInitialScreen",at = @At("HEAD"), cancellable = true)
    private void setInitialScreenInject(RealmsClient realmsClient, ReloadInstance reloadInstance, GameConfig.QuickPlayData quickPlayData, CallbackInfo ci){
        long maxMemory   = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        long totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        long freeMemory  = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        long usedMemory  = totalMemory - freeMemory;
        if (quickPlayData.isEnabled()) {
            QuickPlay.connect(((Minecraft) (Object) this), quickPlayData, reloadInstance, realmsClient);
        }else if(maxMemory<=2048){
                ((Minecraft) (Object) this).setScreen(new MemoryScreen(((Minecraft) (Object) this).options));
        } else if (((Minecraft) (Object)this).options.onboardAccessibility) {
            ((Minecraft) (Object) this).setScreen(new AccessibilityOnboardingScreen(((Minecraft) (Object) this).options));
        } else {
            ((Minecraft) (Object)this).setScreen(new TitleScreen(true));
        }
        ci.cancel();
    }
}
