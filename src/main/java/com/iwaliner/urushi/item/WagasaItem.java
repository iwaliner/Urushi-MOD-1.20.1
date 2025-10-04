package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementItem;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WagasaItem extends Item implements ElementItem {
    boolean isOpen;
    public WagasaItem(boolean b,Properties p_41383_) {
        super(p_41383_);
        isOpen=b;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
       if(isOpen){
           player.setItemInHand(hand,new ItemStack(ItemAndBlockRegister.close_wagasa.get()));
           level.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS,1F,1F);
           //player.setInvisible(true);
           //player.setGlowingTag(true);
           //player.getAbilities().mayfly=false;
           return InteractionResultHolder.success(player.getItemInHand(hand));
       }else{
           player.setItemInHand(hand,new ItemStack(ItemAndBlockRegister.open_wagasa.get()));
           level.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS,1F,1F);
           //player.setInvisible(false);
           //player.setGlowingTag(false);
           //player.getAbilities().mayfly=true;
           return InteractionResultHolder.success(player.getItemInHand(hand));
       }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean b) {
        if(isOpen){

            if(entity instanceof Player){
                Player player= (Player) entity;
                ItemStack magatama= ElementUtils.getMagatamaInInventory(player, ElementType.WoodElement);
                if(magatama!=ItemStack.EMPTY&&ElementUtils.willBeInDomain(magatama,-1)) {

                    if (player.getOffhandItem().getItem() == this || player.getMainHandItem().getItem() == this) {
                        Vec3 vec3 = entity.getDeltaMovement();
                        if (vec3.y < 0D) {
                            entity.setDeltaMovement(new Vec3(vec3.x, vec3.y * 0.6D, vec3.z));
                            if(!player.onGround()&&!level.isClientSide()&&level.random.nextInt(20)==0) {
                                ElementUtils.increaseStoredReiryokuAmount(magatama, -1);
                            }
                        }
                        entity.fallDistance = 0f;
                    }
                }else{
                    InteractionHand hand= player.getMainHandItem().getItem()==this? InteractionHand.MAIN_HAND : player.getOffhandItem().getItem()==this? InteractionHand.OFF_HAND : null;
                    if(hand!=null) {
                        player.setItemInHand(hand, new ItemStack(ItemAndBlockRegister.close_wagasa.get()));
                        level.playSound(player,player.getX(),player.getY(),player.getZ(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS,1F,1F);
                    }

                }
            }else{
                Vec3 vec3 = entity.getDeltaMovement();
                if(vec3.y<0D){
                    entity.setDeltaMovement(new Vec3(vec3.x,vec3.y*0.6D,vec3.z));
                }
                entity.fallDistance=0f;
            }
        }
    }
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
    UrushiUtils.setInfo(list,"wagasa");
        UrushiUtils.setInfo(list,"wagasa2");
    }

    @Override
    public ElementType getElementType() {
        return ElementType.WoodElement;
    }
}
