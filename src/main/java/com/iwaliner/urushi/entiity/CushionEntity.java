package com.iwaliner.urushi.entiity;

import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.item.CushionItem;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;

public class CushionEntity extends Entity {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE = SynchedEntityData.defineId(CushionEntity.class, EntityDataSerializers.INT);
    public CushionEntity(EntityType<? extends CushionEntity> p_i48580_1_, Level p_i48580_2_) {
        super(EntityRegister.Cushion.get(), p_i48580_2_);
    }
    public CushionEntity(Level p_i1705_1_, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
        this(EntityRegister.Cushion.get(), p_i1705_1_);
        this.setPos(p_i1705_2_, p_i1705_4_, p_i1705_6_);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = p_i1705_2_;
        this.yo = p_i1705_4_;
        this.zo = p_i1705_6_;
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(DATA_ID_TYPE, DyeColor.WHITE.ordinal());
    }
    public DyeColor getCushionType() {
        return DyeColor.byId(this.entityData.get(DATA_ID_TYPE));
    }
    public void setType(DyeColor p_184458_1_) {
        this.entityData.set(DATA_ID_TYPE, p_184458_1_.ordinal());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.setType(DyeColor.byId(tag.getInt("Type")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Type", this.getCushionType().ordinal());
    }


    /**プレイヤーがエンティティを殴れるかどうか。ItemEntityならfalseだし、ShulkerBulletならtrue。*/
    public boolean isPickable() {
        return true;
    }

    public boolean canBeCollidedWith() {
        return false;
    }
    /**目線の高さ。0.0Fだと当たり判定の底面部分。*/
    @Override
    protected float getEyeHeight(Pose p_19976_, EntityDimensions p_19977_) {
        return 0.0F;
    }

    public double getPassengersRidingOffset() {
        if(getFirstPassenger() instanceof CushionEntity){
            return 0.2D;
        }else {
            return -0.05D;
        }
    }
    public Item getDropItem() {
        switch(this.getCushionType()) {
            case WHITE:
            default:
                return ItemAndBlockRegister.white_cushion.get();
            case ORANGE:
                return ItemAndBlockRegister.orange_cushion.get();
            case MAGENTA:
                return ItemAndBlockRegister.magenta_cushion.get();
            case LIGHT_BLUE:
                return ItemAndBlockRegister.light_blue_cushion.get();
            case YELLOW:
                return ItemAndBlockRegister.yellow_cushion.get();
            case LIME:
                return ItemAndBlockRegister.lime_cushion.get();
            case PINK:
                return ItemAndBlockRegister.pink_cushion.get();
            case GRAY:
                return ItemAndBlockRegister.gray_cushion.get();
            case LIGHT_GRAY:
                return ItemAndBlockRegister.light_gray_cushion.get();
            case CYAN:
                return ItemAndBlockRegister.cyan_cushion.get();
            case PURPLE:
                return ItemAndBlockRegister.purple_cushion.get();
            case BLUE:
                return ItemAndBlockRegister.blue_cushion.get();
            case BROWN:
                return ItemAndBlockRegister.brown_cushion.get();
            case GREEN:
                return ItemAndBlockRegister.green_cushion.get();
            case RED:
                return ItemAndBlockRegister.red_cushion.get();
            case BLACK:
                return ItemAndBlockRegister.black_cushion.get();
        }
    }



    /**殴られたときの処理*/
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isInvulnerableTo(p_70097_1_)) {
            return false;
        } else {
            if (!this.isRemoved() && !this.level().isClientSide) {
                this.discard();
                this.markHurt();
                this.playSound(SoundEvents.WOOL_BREAK, 1.0F, 1.0F);
                ItemStack itemStack=new ItemStack(getDropItem());
                this.spawnAtLocation(itemStack);
            }

            return true;
        }
    }
    @Override
    public boolean canRiderInteract() {
        return true;
    }
    private void searchRiderCushion(CushionEntity cushionEntity,List<Entity> list,boolean flag){
        if(cushionEntity.getFirstPassenger() instanceof CushionEntity entity2){
            searchRiderCushion(entity2,list,flag);
        }else{
            list.add(cushionEntity);
            if(cushionEntity.getFirstPassenger()!=null&&flag){
                cushionEntity.getFirstPassenger().stopRiding();
            }
        }
    }
    /**右クリック時の処理*/
    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if(hand==InteractionHand.OFF_HAND){
            return InteractionResult.FAIL;
        }
        if(!this.level().isClientSide()&&player.getItemInHand(hand).getItem() instanceof CushionItem cushionItem){
            /*CushionItem cushionItem= (CushionItem) player.getItemInHand(hand).getItem();
            CushionEntity entity = new CushionEntity(level(), this.getX(), this.getY(),  this.getZ());
            entity.moveTo(this.getX(), this.getY()+0.2D,  this.getZ(),player.getYRot(), 0.0F);
            entity.setType(cushionItem.getColor());
            level().addFreshEntity(entity);
            player.getItemInHand(hand).shrink(1);
            level().playSound((Player) null, this.blockPosition(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

            return InteractionResult.SUCCESS;*/
            player.getItemInHand(hand).shrink(1);
            level().playSound((Player) null, this.blockPosition(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if(!level().isClientSide()) {
                CushionEntity entity = new CushionEntity(level(), this.getX(), this.getY(), this.getZ());
                entity.moveTo(this.getX(), this.getY() + 0.2D, this.getZ(), player.getYRot(), 0.0F);
                entity.setType(cushionItem.getColor());
                level().addFreshEntity(entity);
                List<Entity> list=new ArrayList<>();
                searchRiderCushion(this,list,true);
                if (!list.isEmpty()) {
                    Entity top = list.get(0);
                    entity.startRiding(top);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (!this.level().isClientSide())
        {
            List<Entity> list=new ArrayList<>();
            searchRiderCushion(this,list,false);
            if(!list.isEmpty()) {
                player.startRiding(list.get(0));
                level().playSound(null, this.blockPosition(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }


    @Override
    public void tick() {

        this.checkBelowWorld();
        if(!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }
        this.move(MoverType.SELF, this.getDeltaMovement()); //移動(落下)

        if(!level().isClientSide()&&this.getPassengers().isEmpty()){
            AABB axisalignedbb =this.getBoundingBox().inflate(0.55D, 0.1D, 0.55D);
            List<LivingEntity> list =level().getEntitiesOfClass(LivingEntity.class, axisalignedbb);
            if(!list.isEmpty()) {
                for (LivingEntity entity : list) {
                    if (!(entity instanceof Player)&&!entity.isPassenger()) {
                        entity.startRiding(this);
                       // level().playSound((Player) null, this.blockPosition(), SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        break;
                    }
                }
            }
        }
        if(UrushiUtils.isAprilFoolsDay()) {
            long gametime = level().getGameTime() % 10;
            if (gametime == 0) {
                this.move(MoverType.SELF, this.getDeltaMovement().add(0.0D, 0.4D, 0.0D));
                this.move(MoverType.SELF, this.getDeltaMovement());
            }
        }
    }

   /**このメソッドがないとエンティティの見た目どころかF3+B時の当たり判定すら反映されない*/
    /*@Override
    public  Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }*/

}
