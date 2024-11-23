package com.iwaliner.urushi.mixin;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Display;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Display.class)
public class DisplayMixin2 {

    @Inject(method = "tick",at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci){
        if(((Entity) (Object)this).getTags().contains("slide_door_move_for_east")){
            SynchedEntityData entityData=((Entity) (Object)this).getEntityData();
            entityData.set(DisplayMixin.getTranslationData(),new Vector3f(0.8125f,0.0001f,0.0001f));
            entityData.set(DisplayMixin.getDurationData(),8);
            entityData.set(DisplayMixin.getStartTickData(),1);
            if(((Entity) (Object)this).tickCount>13){
                ((Entity) (Object)this).discard();
            }
        }else if(((Entity) (Object)this).getTags().contains("slide_door_move_for_west")){
            SynchedEntityData entityData=((Entity) (Object)this).getEntityData();
            entityData.set(DisplayMixin.getTranslationData(),new Vector3f(-0.8125f,0.0001f,0.0001f));
            entityData.set(DisplayMixin.getDurationData(),8);
            entityData.set(DisplayMixin.getStartTickData(),1);
            if(((Entity) (Object)this).tickCount>13){
                ((Entity) (Object)this).discard();
            }
        }else if(((Entity) (Object)this).getTags().contains("slide_door_move_for_south")){
            SynchedEntityData entityData=((Entity) (Object)this).getEntityData();
            entityData.set(DisplayMixin.getTranslationData(),new Vector3f(0.0001f,0.0001f,0.8125f));
            entityData.set(DisplayMixin.getDurationData(),8);
            entityData.set(DisplayMixin.getStartTickData(),1);
            if(((Entity) (Object)this).tickCount>13){
                ((Entity) (Object)this).discard();
            }
        }else if(((Entity) (Object)this).getTags().contains("slide_door_move_for_north")){
            SynchedEntityData entityData=((Entity) (Object)this).getEntityData();
            entityData.set(DisplayMixin.getTranslationData(),new Vector3f(0.0001f,0.0001f,-0.8125f));
            entityData.set(DisplayMixin.getDurationData(),8);
            entityData.set(DisplayMixin.getStartTickData(),1);
            if(((Entity) (Object)this).tickCount>13){
                ((Entity) (Object)this).discard();
            }
        }
    }
}
