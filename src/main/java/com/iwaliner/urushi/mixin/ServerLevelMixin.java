package com.iwaliner.urushi.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerLevel.class)

public interface ServerLevelMixin {
    @Accessor("serverLevelData") @Final
    public void setServerLevelData(ServerLevelData data);

    @Accessor("serverLevelData") @Final
    public ServerLevelData getServerLevelData();
}
