package com.iwaliner.urushi.entiity.food;


import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

public abstract class FoodEntity extends Entity {
    private Item itemContains;
    public FoodEntity(Item item, EntityType<?> p_i48580_1_, Level p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
        itemContains=item;
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
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.isInvulnerableTo(p_70097_1_)) {
            return false;
        } else {
            if ( !this.level().isClientSide) {
                this.discard();  //このエンティティを抹消
                this.markHurt();
                this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
                ItemStack itemStack=new ItemStack(itemContains);
                this.spawnAtLocation(itemStack);  //ItemEntityをスポーン
            }

            return true;
        }
    }


    /**右クリック時の処理*/

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        this.discard();
        this.markHurt();
        this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
        ItemStack itemStack=new ItemStack(itemContains);
        this.spawnAtLocation(itemStack);
        return  InteractionResult.SUCCESS;
    }
    @Override
    public void tick() {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        this.setDeltaMovement(new Vec3(this.getDeltaMovement().x*0.99D,this.getDeltaMovement().y-0.04D,this.getDeltaMovement().z*0.99D));
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

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {

    }

    /**このメソッドがないとエンティティの見た目どころかF3+B時の当たり判定すら反映されない*/
   /* @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
*/
}
