package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.block.SlideDoorBlock;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Display.BlockDisplay.class)

public interface BlockDisplayMixin {

    @Accessor("DATA_BLOCK_STATE_ID")
    public static EntityDataAccessor<BlockState> getData() {
        throw new AssertionError();
    }


}
