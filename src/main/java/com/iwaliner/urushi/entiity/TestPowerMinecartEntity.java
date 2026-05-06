package com.iwaliner.urushi.entiity;

import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.item.CushionItem;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.MinecartFurnace;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.*;

public class TestPowerMinecartEntity extends AbstractMinecart {
    public ArrayList<UUID> linkedCartList=new ArrayList<>();
    public double xPush;
    public double zPush;
     public TestPowerMinecartEntity(EntityType<? extends TestPowerMinecartEntity> p_i48580_1_, Level p_i48580_2_) {
        super(EntityRegister.TestPowerMinecart.get(), p_i48580_2_);
    }
    public TestPowerMinecartEntity(Level p_i1705_1_, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
        this(EntityRegister.TestPowerMinecart.get(), p_i1705_1_);
        this.setPos(p_i1705_2_, p_i1705_4_, p_i1705_6_);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = p_i1705_2_;
        this.yo = p_i1705_4_;
        this.zo = p_i1705_6_;
    }
    public int getCartNumber(AbstractMinecart minecart) {
       return getCartNumber(minecart.getUUID());
    }
    public int getCartNumber(UUID uuid){
         if(linkedCartList.contains(uuid)){
             return linkedCartList.indexOf(uuid);
         }
        return -1;
    }
    public boolean isLinkedMinecart(AbstractMinecart cart){
         return getCartNumber(cart)!=-1;
    }

    public UUID getLinkedCartUUID(int i){
         if(i<linkedCartList.size()){
             return linkedCartList.get(i);
         }
         return null;
    }
    public AbstractMinecart getLinkedCartEntity(int i){
         UUID uuid=getLinkedCartUUID(i);
         if(uuid!=null){
             Entity entity= ((ServerLevel)this.level()).getEntity(uuid);
             if(entity instanceof AbstractMinecart){
                 return (AbstractMinecart) entity;
             }
         }
         return null;
    }
    public int getMaxLinkSie(){
         return 20;
    }
    public void addLinkedCart(AbstractMinecart cart){
        if(linkedCartList.size()<getMaxLinkSie()) {
            linkedCartList.add(cart.getUUID());
        }
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected Item getDropItem() {
        return Items.APPLE;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.xPush = tag.getDouble("PushX");
        this.zPush = tag.getDouble("PushZ");
        for(int i=0;i<getMaxLinkSie();i++){
            if(tag.hasUUID("linkedCart"+i)){
               linkedCartList.set(i,tag.getUUID("linkedCart"+i));
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putDouble("PushX", this.xPush);
        tag.putDouble("PushZ", this.zPush);
        for(int i=0;i<getMaxLinkSie();i++){
            if(linkedCartList.get(i)!=null){
                tag.putUUID("linkedCart"+i, linkedCartList.get(i));
            }
        }
    }

    @Override
    public Type getMinecartType() {
        return Type.CHEST;
    }

    @Override
    public boolean isPoweredCart() {
        return false;
    }
    protected double getMaxSpeed() {
        return (this.isInWater() ? 3.0D : 4.0D) / 20.0D;
    }

    protected void moveAlongTrack(BlockPos p_38569_, BlockState p_38570_) {
        double d0 = 1.0E-4D;
        double d1 = 0.001D;
        super.moveAlongTrack(p_38569_, p_38570_);
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = vec3.horizontalDistanceSqr();
        double d3 = this.xPush * this.xPush + this.zPush * this.zPush;
        if (d3 > 1.0E-4D && d2 > 0.001D) {
            double d4 = Math.sqrt(d2);
            double d5 = Math.sqrt(d3);
            this.xPush = vec3.x / d4 * d5;
            this.zPush = vec3.z / d4 * d5;
        }

    }
    protected void applyNaturalSlowdown() {
        double d0 = this.xPush * this.xPush + this.zPush * this.zPush;
        if (d0 > 1.0E-7D) {
            d0 = Math.sqrt(d0);
            this.xPush /= d0;
            this.zPush /= d0;
            Vec3 vec3 = this.getDeltaMovement().multiply(0.8D, 0.0D, 0.8D).add(this.xPush, 0.0D, this.zPush);
            if (this.isInWater()) {
                vec3 = vec3.scale(0.1D);
            }

            this.setDeltaMovement(vec3);
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.98D, 0.0D, 0.98D));
        }

        super.applyNaturalSlowdown();
    }
    @Override
    public float getMaxCartSpeedOnRail() {
        return 0.2f;
    }
    public BlockState getDefaultDisplayBlockState() {
        return Blocks.REDSTONE_BLOCK.defaultBlockState();
    }
    public InteractionResult interact(Player p_38562_, InteractionHand p_38563_) {
        InteractionResult ret = super.interact(p_38562_, p_38563_);
        if (ret.consumesAction()) return ret;
            this.xPush = this.getX() - p_38562_.getX();
            this.zPush = this.getZ() - p_38562_.getZ();

        AABB axisalignedbb =this.getBoundingBox() .inflate(4.0D, 4.0D, 4.0D);
        List<AbstractMinecart> list = this.level().getEntitiesOfClass(AbstractMinecart.class, axisalignedbb);
        if(!list.isEmpty()) {
            for (AbstractMinecart cart : list) {
                if(!isLinkedMinecart(cart)&&cart!=this){
                    addLinkedCart(cart);
                    cart.setDeltaMovement(this.xPush>0f? 10f:-10f,0f,this.zPush>0f?10f:-10f);
                }
            }
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }
    public void tick() {
        super.tick();
        this.setDeltaMovement(getDeltaMovement().x*10D,getDeltaMovement().y,getDeltaMovement().z*10D);
        if (!this.level().isClientSide()) {
            if(!linkedCartList.isEmpty()) {
                for (int i = 0; i < getMaxLinkSie(); i++) {
                    AbstractMinecart linkedCart = getLinkedCartEntity(i);
                    if (linkedCart != null) {
                        linkedCart.setDeltaMovement(linkedCart.getDeltaMovement().x*10D,linkedCart.getDeltaMovement().y,linkedCart.getDeltaMovement().z*10D);
                        //linkedCart.setDeltaMovement(this.getDeltaMovemenet());
                        //RailShape railshape = ((BaseRailBlock)getBlockStateOn().getBlock()).getRailDirection(getBlockStateOn(), this.level(), getOnPos(), linkedCart);
                        /*switch (railshape) {
                            case NORTH_EAST:
                                linkedCart.setDeltaMovement(vec31.add(-d3, 0.0D, 0.0D));
                                ++d1;
                                break;
                            case ASCENDING_WEST:
                                this.setDeltaMovement(vec31.add(d3, 0.0D, 0.0D));
                                ++d1;
                                break;
                            case ASCENDING_NORTH:
                                this.setDeltaMovement(vec31.add(0.0D, 0.0D, d3));
                                ++d1;
                                break;
                            case ASCENDING_SOUTH:
                                this.setDeltaMovement(vec31.add(0.0D, 0.0D, -d3));
                                ++d1;
                        }*/
                    }
                }
            }
        }

    }
}
