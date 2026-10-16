package com.iwaliner.urushi.entiity.food;


import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public abstract class FoodEntity extends Entity {
    private Item itemContains;
    public double xPower;
    public double yPower;
    public double zPower;
    public FoodEntity(Item item, EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
        itemContains=item;
        this.blocksBuilding=true;
    }
    public boolean canBeCollidedWith() {
        return true;
    }

    public boolean isPushable() {
        return true;
    }
    /**プレイヤーがエンティティを殴れるかどうか。ItemEntityならfalseだし、ShulkerBulletならtrue。*/
    public boolean isPickable() {
        return true;
    }


    /**目線の高さ。0.0Fだと当たり判定の底面部分。*/
    @Override
    protected float getEyeHeight(Pose p_19976_, EntityDimensions p_19977_) {
        return 0.0F;
    }



    /**殴られたときの処理*/
    public boolean hurt(DamageSource damageSource, float p_70097_2_) {
        if (this.isInvulnerableTo(damageSource)) {
            return false;
        } else {
            if ( !this.level().isClientSide) {
                this.discard();
                this.markHurt();
                this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                ItemStack itemStack=new ItemStack(itemContains);
                this.spawnAtLocation(itemStack);
            }

            return true;
        }
    }


    /**右クリック時の処理*/

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {

       /* this.discard();
        this.markHurt();
        this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
        ItemStack itemStack=new ItemStack(itemContains);
        this.spawnAtLocation(itemStack);
        return  InteractionResult.SUCCESS;*/
        if (player != null) {
           // if (!this.level().isClientSide) {
                Vec3 vec3 = player.getLookAngle();
                this.setDeltaMovement(vec3);
                this.xPower = vec3.x * 0.1D;
                this.yPower = vec3.y * 0.1D;
                this.zPower = vec3.z * 0.1D;
           // }
            this.setDeltaMovement(this.getDeltaMovement().add(this.xPower, this.yPower, this.zPower)/*.scale((double)f)*/);
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }

    @Override
    public void tick() {
        double f=0.95D;
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        this.setDeltaMovement(new Vec3(this.getDeltaMovement().x*f,this.getDeltaMovement().y-0.04D,this.getDeltaMovement().z*f));
        this.move(MoverType.SELF, this.getDeltaMovement()); //自由落下

        if(UrushiUtils.isAprilFoolsDay()) {
            long gametime = level().getGameTime() % 10;
            if (gametime == 0) {
                this.move(MoverType.SELF, this.getDeltaMovement().add(0.0D, 0.4D, 0.0D));
                this.move(MoverType.SELF, this.getDeltaMovement());
            }
        }


    }
    @Override
    protected void defineSynchedData() {

    }

    public void addAdditionalSaveData(CompoundTag p_36848_) {
        p_36848_.put("power", this.newDoubleList(new double[]{this.xPower, this.yPower, this.zPower}));
    }

    public void readAdditionalSaveData(CompoundTag p_36844_) {
        if (p_36844_.contains("power", 9)) {
            ListTag listtag = p_36844_.getList("power", 6);
            if (listtag.size() == 3) {
                this.xPower = listtag.getDouble(0);
                this.yPower = listtag.getDouble(1);
                this.zPower = listtag.getDouble(2);
            }
        }

    }


}
