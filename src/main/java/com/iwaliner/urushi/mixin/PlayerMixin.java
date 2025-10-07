package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    /*@Inject(method = "tick",at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci){
        if(!((Player) (Object)this).isCreative()&&((Player) (Object)this).getAbilities().mayfly
                &&!((Player) (Object)this).getInventory().contains(new ItemStack(ItemAndBlockRegister.open_wagasa.get()))){
           ((Player) (Object)this).getAbilities().mayfly=false;
        }
    }*/
}
