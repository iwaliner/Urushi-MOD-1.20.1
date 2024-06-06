package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.block.SlideDoorBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronBarsBlock.class)

public abstract class IronBarsMixin {
    @Inject(method = "attachsTo",at = @At("HEAD"), cancellable = true,remap = false)
    private void attachsToInject(BlockState state, boolean p_54219_, CallbackInfoReturnable<Boolean> cir){
        if(state.getBlock() instanceof SlideDoorBlock){
           cir.setReturnValue(true);
        }
    }
}
