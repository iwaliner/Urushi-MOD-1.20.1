package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.DimensionRegister;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.WeatherCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WeatherCommand.class)

public abstract class WeatherCommandMixin {
    @Shadow
    protected static int getDuration(CommandSourceStack p_265382_, int p_265171_, IntProvider p_265122_) {
        return p_265171_ == -1 ? p_265122_.sample(p_265382_.getLevel().getRandom()) : p_265171_;
    }

    @Inject(method = "setClear",at = @At("HEAD"), cancellable = true)
    private static void setClearInject(CommandSourceStack commandSourceStack, int i, CallbackInfoReturnable<Integer> cir){


        if(commandSourceStack.getLevel().dimension()==DimensionRegister.KakuriyoKey){
            commandSourceStack.getLevel().getServer().getLevel(Level.OVERWORLD).setWeatherParameters(getDuration(commandSourceStack, i, ServerLevel.RAIN_DELAY), 0, false, false);
            commandSourceStack.sendSuccess(() -> {
                return Component.translatable("commands.weather.set.clear");
            }, true);
            cir.setReturnValue(i);
        }
    }

    @Inject(method = "setRain",at = @At("HEAD"), cancellable = true)
    private static void setRainInject(CommandSourceStack commandSourceStack, int i, CallbackInfoReturnable<Integer> cir){

        if(commandSourceStack.getLevel().dimension()==DimensionRegister.KakuriyoKey){
            commandSourceStack.getLevel().getServer().getLevel(Level.OVERWORLD).setWeatherParameters(0, getDuration(commandSourceStack, i, ServerLevel.RAIN_DURATION), true, false);
            commandSourceStack.sendSuccess(() -> {
                return Component.translatable("commands.weather.set.rain");
            }, true);
            cir.setReturnValue(i);
        }
    }

    @Inject(method = "setThunder",at = @At("HEAD"), cancellable = true)
    private static void setsetThunder(CommandSourceStack commandSourceStack, int i, CallbackInfoReturnable<Integer> cir){

        if(commandSourceStack.getLevel().dimension()==DimensionRegister.KakuriyoKey){
            commandSourceStack.getLevel().getServer().getLevel(Level.OVERWORLD).setWeatherParameters(0, getDuration(commandSourceStack, i, ServerLevel.THUNDER_DURATION), true, true);
            commandSourceStack.sendSuccess(() -> {
                return Component.translatable("commands.weather.set.thunder");
            }, true);
            cir.setReturnValue(i);
        }
    }
}
