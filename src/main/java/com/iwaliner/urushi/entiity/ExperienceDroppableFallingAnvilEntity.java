package com.iwaliner.urushi.entiity;

import com.iwaliner.urushi.EntityRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class ExperienceDroppableFallingAnvilEntity extends Entity {

    private static final EntityDataAccessor<Optional<UUID>> UUID_DATA = SynchedEntityData.defineId(ExperienceDroppableFallingAnvilEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public ExperienceDroppableFallingAnvilEntity(EntityType<? extends ExperienceDroppableFallingAnvilEntity> p_i48580_1_, Level p_i48580_2_) {
        super(EntityRegister.ExperienceDroppableFallingAnvil.get(), p_i48580_2_);
    }
    public ExperienceDroppableFallingAnvilEntity(Level level, double x, double y, double z, Player player) {
        this(EntityRegister.ExperienceDroppableFallingAnvil.get(), level);
        this.setPos(x, y, z);
       /* this.xo = x;
        this.yo = y;
        this.zo = z;*/
        if(player!=null) {
            entityData.set(UUID_DATA, Optional.of(player.getUUID()));
        }
    }

    public Player getPlayer() {
        if(entityData.get(UUID_DATA).isPresent()){
            Player player=level().getPlayerByUUID(entityData.get(UUID_DATA).get());
            return player;
        }
        return null;
    }
    @Override
    protected void defineSynchedData() {
        this.entityData.define(UUID_DATA,Optional.empty());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
       entityData.set(UUID_DATA,Optional.of(tag.getUUID("playerUUID")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if(entityData.get(UUID_DATA).isPresent()) {
            tag.putUUID("playerUUID", entityData.get(UUID_DATA).get());
        }
    }

    @Override
    public void tick() {
        super.tick();
        //if (!this.isNoGravity()) {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        this.move(MoverType.SELF, this.getDeltaMovement());
       // }
        if(onGround()){
            level().playSound((Player) null,blockPosition(), SoundEvents.ANVIL_LAND, SoundSource.BLOCKS,0.2F,1F);
            discard();
        }
    }

    @Override
    public boolean causeFallDamage(float p_149643_, float p_146829_, DamageSource p_146830_) {
            int i = Mth.ceil(p_149643_ - 1.0F);
            if (i < 0) {
                return false;
            } else {
                Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
                this.level().getEntities(this, this.getBoundingBox(), predicate).forEach((p_149649_) -> {
                    if(getPlayer()!=null) {
                        p_149649_.hurt(p_149649_.damageSources().playerAttack(getPlayer()), 10F);
                    }
                });

                return false;
            }
    }
}
