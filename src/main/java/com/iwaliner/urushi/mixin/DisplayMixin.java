package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.block.SlideDoorBlock;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Display.class)

public interface DisplayMixin {


    @Accessor("DATA_INTERPOLATION_START_DELTA_TICKS_ID")
    public static EntityDataAccessor<Integer> getStartTickData() {
        throw new AssertionError();
    }
    @Accessor("DATA_INTERPOLATION_DURATION_ID")
    public static EntityDataAccessor<Integer> getDurationData() {
        throw new AssertionError();
    }
    @Accessor("DATA_TRANSLATION_ID")
    public static EntityDataAccessor<Vector3f> getTranslationData() {
        throw new AssertionError();
    }


}
