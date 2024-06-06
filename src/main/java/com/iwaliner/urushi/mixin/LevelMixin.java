package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.block.SlideDoorBlock;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)

public interface LevelMixin {
    @Accessor("levelData") @Final
    public void setLevelData(WritableLevelData data);

    @Accessor("levelData") @Final
    public WritableLevelData getLevelData();
}
