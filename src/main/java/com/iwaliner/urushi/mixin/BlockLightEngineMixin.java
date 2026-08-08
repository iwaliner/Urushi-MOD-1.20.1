package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.block.SlideDoorBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.BlockLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLightEngine.class)

public abstract class BlockLightEngineMixin {
    @Inject(method = "getEmission",at = @At("HEAD"), cancellable = true)
    private void attachsToInject(long p_285243_, BlockState p_284973_, CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(16711680);
    }
}
