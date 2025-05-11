package com.iwaliner.urushi.item;

import com.iwaliner.urushi.block.EmitterBlock;
import com.iwaliner.urushi.block.MirrorBlock;
import com.iwaliner.urushi.blockentity.EmitterBlockEntity;
import com.iwaliner.urushi.blockentity.MirrorBlockEntity;
import com.iwaliner.urushi.util.ComplexDirection;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class VisualizationGogglesItem extends Item implements Equipable, IForgeItem {
    private final String string;
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected ItemStack execute(BlockSource p_40408_, ItemStack p_40409_) {
            return ArmorItem.dispenseArmor(p_40408_, p_40409_) ? p_40409_ : super.execute(p_40408_, p_40409_);
        }
    };

    public VisualizationGogglesItem(String s, Properties p_41383_) {
        super(p_41383_);
        string = s;
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }

    public InteractionResultHolder<ItemStack> use(Level p_41137_, Player p_41138_, InteractionHand p_41139_) {
        ItemStack itemstack = p_41138_.getItemInHand(p_41139_);
        EquipmentSlot equipmentslot = EquipmentSlot.HEAD;
        ItemStack itemstack1 = p_41138_.getItemBySlot(equipmentslot);
        if (itemstack1.isEmpty()) {
            p_41138_.setItemSlot(equipmentslot, itemstack.copy());
            if (!p_41137_.isClientSide()) {
                p_41138_.awardStat(Stats.ITEM_USED.get(this));
            }

            itemstack.setCount(0);
            return InteractionResultHolder.sidedSuccess(itemstack, p_41137_.isClientSide());
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }

    @Nullable
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_LEATHER;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public EquipmentSlot getEquipmentSlot(ItemStack stack) {
        return EquipmentSlot.HEAD;
    }

    @Override
    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @org.jetbrains.annotations.Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        UrushiUtils.setInfo(list, string);
    }

    private double getParticleSpeed(Tiered tiered) {
        if (tiered.getTier() == 1) {
            return 0.2D;
        } else if (tiered.getTier() == 2) {
            return 0.3D;
        }
        return 0.2D;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int i0, boolean b2) {
        if (!(entity instanceof Player player)) {
            return;
        }
        if (!player.getItemBySlot(EquipmentSlot.HEAD).is(stack.getItem())) {
            super.inventoryTick(stack, level, entity, i0, b2);
            return;
        }

        int tickrange = 25;
        double corner = 6D;
        for (int xx = -tickrange; xx <= tickrange; xx++) {
            for (int yy = -tickrange; yy <= tickrange; yy++) {
                for (int zz = -tickrange; zz <= tickrange; zz++) {
                    BlockPos pos = entity.blockPosition().offset(xx, yy, zz);

                    BlockState state = level.getBlockState(pos);
                    Block block = state.getBlock();
                    if (!(block instanceof EmitterBlock || block instanceof MirrorBlock)) {
                        continue;
                    }

                    BlockEntity blockEntity = level.getBlockEntity(pos);
                    if (block instanceof EmitterBlock emitterBlock && blockEntity instanceof EmitterBlockEntity) {
                        int range = Mth.floor(this.getParticleSpeed(emitterBlock) * 80 - 0.25D);
                        Direction relativePosValue = state.getValue(EmitterBlock.FACING);

                        for (int j = 0; j < range; j++) {
                            BlockPos offsetPos = pos.relative(relativePosValue, j);
                            BlockState offsetState = level.getBlockState(offsetPos);
                            VoxelShape shape = offsetState.getCollisionShape(level, offsetPos).optimize();

                            VoxelShape particleShape = Block.box(corner, corner, corner, 16D - corner, 16D - corner, 16D - corner);
                            level.addParticle(ParticleTypes.COMPOSTER, offsetPos.getX() + 0.5D, offsetPos.getY() + 0.5D, offsetPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                            if (offsetState.getBlock() instanceof MirrorBlock && level.getBlockEntity(offsetPos) instanceof MirrorBlockEntity mirrorBlockEntity) {
                                mirrorBlockEntity.setIncidentDirection(relativePosValue.getOpposite());
                            }
                            if (Shapes.joinIsNotEmpty(shape, particleShape, BooleanOp.AND)) {
                                break;
                            }

                        }
                    } else if (block instanceof MirrorBlock mirrorBlock && blockEntity instanceof MirrorBlockEntity mirrorBlockEntity) {
                        ComplexDirection incidentDirection = Objects.requireNonNull(mirrorBlockEntity).reflectedDirection(MirrorBlockEntity.getDirectionFromID(state.getValue(MirrorBlock.DIRECTION)), mirrorBlockEntity.getIncidentDirection());

                        boolean b1 = ComplexDirection.isNEWS(incidentDirection);
                        int range = b1 ? Mth.floor(this.getParticleSpeed(mirrorBlock) * 80 + 0.6D) : Mth.floor((this.getParticleSpeed(mirrorBlock) * 80 + 0.6D) / Math.sqrt(2));

                        Direction[] complexDirection = MirrorBlockEntity.getDirectionFromComplexDirection(incidentDirection);

                        if (complexDirection == null) {
                            continue;
                        }
                        VoxelShape particleShape = Block.box(corner, corner, corner, 16D - corner, 16D - corner, 16D - corner);

                        for (int j = 1; j < range; j++) {

                            BlockPos offsetPos = complexDirection[1] == null ?
                                pos.relative(complexDirection[0], j) :
                                pos.relative(complexDirection[0], j)
                                    .relative(complexDirection[1], j);
                            BlockState offsetState = level.getBlockState(offsetPos);
                            VoxelShape shape = offsetState.getCollisionShape(level, offsetPos).optimize();
                            level.addParticle(ParticleTypes.COMPOSTER, offsetPos.getX() + 0.5D, offsetPos.getY() + 0.5D, offsetPos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                            if (offsetState.getBlock() instanceof MirrorBlock && level.getBlockEntity(offsetPos) instanceof MirrorBlockEntity mirrorBlockEntity2) {
                                mirrorBlockEntity2.setIncidentDirection(MirrorBlockEntity.getOppositeDirection(incidentDirection));
                            }
                            if (Shapes.joinIsNotEmpty(shape, particleShape, BooleanOp.AND)) {
                                break;
                            }

                        }
                    }
                }
            }


            super.inventoryTick(stack, level, entity, i0, b2);
        }
    }
}


