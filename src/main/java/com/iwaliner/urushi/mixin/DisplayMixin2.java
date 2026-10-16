package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.block.RevolvingDoorBlock;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Display;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
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
        }else if(((Entity) (Object)this).getTags().contains("revolving_door")){
            SynchedEntityData entityData=((Entity) (Object)this).getEntityData();
                entityData.set(DisplayMixin.getLeftRotationData(),new Quaternionf(new AxisAngle4f(3.14f,0f,1f,0f)));
            entityData.set(DisplayMixin.getDurationData(), RevolvingDoorBlock.duration);
            entityData.set(DisplayMixin.getStartTickData(),1);
            if(((Entity) (Object)this).tickCount>RevolvingDoorBlock.duration+5){
                ((Entity) (Object)this).discard();
            }
        }
    }
}
