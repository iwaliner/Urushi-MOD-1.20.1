package com.iwaliner.urushi.entiity;

import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraftforge.network.NetworkHooks;


import javax.annotation.Nonnull;

public class KitsunebiEntity extends ThrowableItemProjectile implements ItemSupplier {


    public KitsunebiEntity(Level p_i1774_1_, LivingEntity p_i1774_2_) {
        super(EntityRegister.KitsunebiEntity.get(), p_i1774_2_, p_i1774_1_);
    }

    public KitsunebiEntity(Level p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
        super(EntityRegister.KitsunebiEntity.get(), p_i1775_2_, p_i1775_4_, p_i1775_6_, p_i1775_1_);
    }

    public KitsunebiEntity(EntityType<? extends KitsunebiEntity> entityEntityType, Level world) {
        super(EntityRegister.KitsunebiEntity.get(), world);
    }



    /**これがないと完全無色透明なエンティティになってしまう。このメソッドを書いた後はClientSetup.classでrenderを登録。*/
   /* @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }*/

    protected Item getDefaultItem() {
        return ItemAndBlockRegister.kitsunebiItem.get();
    }








    protected void onHitEntity(EntityHitResult result) {
        if (!this.level().isClientSide) {
            ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY(),this.getZ(), new ItemStack(ItemAndBlockRegister.kitsunebiItem.get()));
           this.level().addFreshEntity(itemEntity);
            this.discard();
        }
    }



    protected void onHit(HitResult result) {
        super.onHit(result);
        if (ItemAndBlockRegister.kitsunebiBlock.isPresent()) {
            BlockPos offsetPos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ()));
            //  BlockPos offsetPos = new BlockPos(Math.floor(result.getLocation().x), Math.floor(result.getLocation().y), Math.floor(result.getLocation().z));
            if(this.level().getBlockState(offsetPos).isAir()||this.level().getBlockState(offsetPos).getBlock() instanceof BushBlock) {
                this.level().setBlockAndUpdate(offsetPos, ItemAndBlockRegister.kitsunebiBlock.get().defaultBlockState());

            }else{
               if (!this.level().isClientSide) {
                    ItemEntity itemEntity = new ItemEntity(this.level(), offsetPos.getX()+0.5D,offsetPos.getX()+0.5D, offsetPos.getX()+0.5D, new ItemStack(ItemAndBlockRegister.kitsunebiItem.get()));
                  this.level().addFreshEntity(itemEntity);

                }
            }
            this.discard();
        }
    }
}
