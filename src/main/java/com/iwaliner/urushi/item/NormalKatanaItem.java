package com.iwaliner.urushi.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


import java.util.List;

public class NormalKatanaItem extends SwordItem {
    private final float attackDamage;

    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public NormalKatanaItem(Tier tier,int i,float f, Properties properties) {
        super(tier,i,f,  properties);
        this.attackDamage = (float)i + tier.getAttackDamageBonus();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", (double)this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", (double)f, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    public float getDamage() {
        return this.attackDamage;
    }

    @Override
    public boolean canAttackBlock(BlockState p_41441_, Level p_41442_, BlockPos p_41443_, Player player) {
        return !player.isCreative();
    }
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        UrushiUtils.setInfo(list,"katana1");
        UrushiUtils.setInfo(list,"katana2");
    }
    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        }
        return 1.0F;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity living, LivingEntity entity) {
        stack.hurtAndBreak(1, entity, (p_220045_0_) -> {
            p_220045_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity living) {
        if (state.getDestroySpeed(level, pos) != 0.0F) {
            stack.hurtAndBreak(2, living, (p_220044_0_) -> {
                p_220044_0_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }

        return true; }


    public boolean isCorrectToolForDrops(BlockState p_150897_1_) {
        return p_150897_1_.is(Blocks.COBWEB);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(slot);
    }

   @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        boolean flag=player.onGround();
        float a=flag?2F:1F;
        double f = -Math.sin(player.getYRot() * ((float)Math.PI / 180F)) * Math.cos(player.getXRot() * ((float)Math.PI / 180F));
        double f1 = -Math.sin((player.getXRot() + 0f) * ((float)Math.PI / 180F));
        double f2 = Math.cos(player.getYRot() * ((float)Math.PI / 180F)) * Math.cos(player.getXRot() * ((float)Math.PI / 180F));
        Vec3 vector3d = (new Vec3((double)f*a, (double)f1*a*0.4D, (double)f2*a));
        player.getCooldowns().addCooldown(this, 10);
        player.startUsingItem(hand);
        player.setDeltaMovement(vector3d);
        player.getItemInHand(hand).hurtAndBreak(1, player, (x) -> {
            x.broadcastBreakEvent(hand);
        });
        AABB axisalignedbb =player.getBoundingBox() .inflate(4.0D, 4.0D, 4.0D);
        List<LivingEntity> list = player.level().getEntitiesOfClass(LivingEntity.class, axisalignedbb);
        if(!list.isEmpty()) {
            for (LivingEntity entity : list) {
                if(entity instanceof Player) {
                }else{
                    entity.hurt(entity.damageSources().playerAttack(player), (EnchantmentHelper.getDamageBonus(player.getItemInHand(hand),entity.getMobType())+(float) player.getAttributeValue(Attributes.ATTACK_DAMAGE))*0.5F);
                    player.level().playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 1.5F, 1F);
                    int i = EnchantmentHelper.getFireAspect(player);
                    if (i > 0) {
                        entity.setSecondsOnFire(i * 4);
                    }
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }



    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity living, InteractionHand hand) {
        living.hurt(living.damageSources().playerAttack(player), (EnchantmentHelper.getDamageBonus(player.getItemInHand(hand),living.getMobType())+(float) player.getAttributeValue(Attributes.ATTACK_DAMAGE))*0.5F);
        this.use(player.level(),player,hand);
            player.level().playSound((Player) null, living.getX(), living.getY(), living.getZ(), SoundEvents.PLAYER_ATTACK_KNOCKBACK, SoundSource.PLAYERS, 1.5F, 1F);
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }



}
