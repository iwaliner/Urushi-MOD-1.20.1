package com.iwaliner.urushi.entiity;

import com.iwaliner.urushi.EntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GhostEntity extends Zombie {
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0F;
    private float nextFlap = 1.0F;
    public GhostEntity(EntityType<? extends Zombie> p_34271_, Level p_34272_) {
        super(EntityRegister.Ghost.get(), p_34272_);
        this.moveControl = new FlyingMoveControl(this, 0, false);
    }


    protected SoundEvent getAmbientSound() {
        return SoundEvents.PUFFER_FISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_34327_) {
        return SoundEvents.PUFFER_FISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PUFFER_FISH_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.PUFFER_FISH_FLOP;
    }
    public boolean hurt(DamageSource p_34288_, float p_34289_) {
        if (!super.hurt(p_34288_, p_34289_)) {
            return false;
        } else if (!(this.level() instanceof ServerLevel)) {
            return false;
        } else {
            ServerLevel serverlevel = (ServerLevel)this.level();
            LivingEntity livingentity = this.getTarget();
            if (livingentity == null && p_34288_.getEntity() instanceof LivingEntity) {
                livingentity = (LivingEntity)p_34288_.getEntity();
            }

            int i = Mth.floor(this.getX());
            int j = Mth.floor(this.getY());
            int k = Mth.floor(this.getZ());
            net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent event = net.minecraftforge.event.ForgeEventFactory.fireZombieSummonAid(this, level(), i, j, k, livingentity, this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).getValue());
            if (event.getResult() == net.minecraftforge.eventbus.api.Event.Result.DENY) return true;
            if (event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW  ||
                    livingentity != null && this.level().getDifficulty() == Difficulty.HARD && (double)this.random.nextFloat() < this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).getValue() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)) {
                Zombie zombie = event.getCustomSummonedAid() != null && event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW ? event.getCustomSummonedAid() : EntityRegister.Ghost.get().create(this.level());

                for(int l = 0; l < 50; ++l) {
                    int i1 = i + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    int j1 = j + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    int k1 = k + Mth.nextInt(this.random, 7, 40) * Mth.nextInt(this.random, -1, 1);
                    BlockPos blockpos = new BlockPos(i1, j1, k1);
                    EntityType<?> entitytype = zombie.getType();
                    SpawnPlacements.Type spawnplacements$type = SpawnPlacements.getPlacementType(entitytype);
                    if (NaturalSpawner.isSpawnPositionOk(spawnplacements$type, this.level(), blockpos, entitytype) && SpawnPlacements.checkSpawnRules(entitytype, serverlevel, MobSpawnType.REINFORCEMENT, blockpos, this.level().random)) {
                        zombie.setPos((double)i1, (double)j1, (double)k1);
                        if (!this.level().hasNearbyAlivePlayer((double)i1, (double)j1, (double)k1, 7.0D) && this.level().isUnobstructed(zombie) && this.level().noCollision(zombie) && !this.level().containsAnyLiquid(zombie.getBoundingBox())) {
                            if (livingentity != null)
                                zombie.setTarget(livingentity);
                            zombie.finalizeSpawn(serverlevel, this.level().getCurrentDifficultyAt(zombie.blockPosition()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
                            serverlevel.addFreshEntityWithPassengers(zombie);
                            this.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Zombie reinforcement caller charge", (double)-0.05F, AttributeModifier.Operation.ADDITION));
                            zombie.getAttribute(Attributes.SPAWN_REINFORCEMENTS_CHANCE).addPermanentModifier(new AttributeModifier("Zombie reinforcement callee charge", (double)-0.05F, AttributeModifier.Operation.ADDITION));
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }
    protected ItemStack getSkull() {
        return  ItemStack.EMPTY;
    }

    @Override
    protected boolean isFlapping() {
        return true;
    }
  /*  protected PathNavigation createNavigation(Level p_29417_) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_29417_);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }*/
  private void calculateFlapping() {
      this.oFlap = this.flap;
      this.oFlapSpeed = this.flapSpeed;
      this.flapSpeed += (float)(!this.onGround() && !this.isPassenger() ? 4 : -1) * 0.3F;
      this.flapSpeed = Mth.clamp(this.flapSpeed, 0.0F, 1.0F);
      if (!this.onGround() && this.flapping < 1.0F) {
          this.flapping = 1.0F;
      }

      this.flapping *= 0.9F;
      Vec3 vec3 = this.getDeltaMovement();
      if (!this.onGround() && vec3.y < 0.0D) {
          this.setDeltaMovement(vec3.multiply(1.0D, 0.6D, 1.0D));
      }

      this.flap += this.flapping * 2.0F;
  }

    /*public void aiStep() {
        super.aiStep();
        this.calculateFlapping();
    }*/

    @Override
    protected AABB makeBoundingBox() {
        return super.makeBoundingBox();
    }

    public void killed(ServerLevel p_34281_, LivingEntity p_34282_) {
       if ((p_34281_.getDifficulty() == Difficulty.NORMAL || p_34281_.getDifficulty() == Difficulty.HARD) && p_34282_ instanceof Villager && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(p_34282_, EntityType.ZOMBIE_VILLAGER, (timer) -> {})) {
            if (p_34281_.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
                return;
            }

            Villager villager = (Villager)p_34282_;
            GhostEntity zombievillager = villager.convertTo(EntityRegister.Ghost.get(), false);
            p_34281_.addFreshEntity(zombievillager);
        }

    }

}
