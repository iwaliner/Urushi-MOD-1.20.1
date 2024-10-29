package com.iwaliner.urushi.item;


import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.block.SenbakokiBlock;
import com.iwaliner.urushi.recipe.SenbakokiRecipe;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.entiity.food.FoodEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class PlaceableFoodItem extends Item {

private java.util.function.Supplier<? extends EntityType<?>> entityType;
    public PlaceableFoodItem(java.util.function.Supplier<? extends EntityType<?>> p_54694_, Properties p_i48487_1_) {
        super(p_i48487_1_);
        entityType=p_54694_;
        DispenserBlock.registerBehavior(this, DISPENSE_ITEM_BEHAVIOR);
    }
    public static final DispenseItemBehavior DISPENSE_ITEM_BEHAVIOR = new DefaultDispenseItemBehavior() {
        protected ItemStack execute(BlockSource source, ItemStack stack) {
            if(stack.getItem() instanceof PlaceableFoodItem){
            Level level = source.getLevel();
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos blockpos = source.getPos().relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            PlaceableFoodItem foodItem= (PlaceableFoodItem) stack.getItem();
            EntityType<?> entity=foodItem.entityType.get();
                     FoodEntity foodEntity = (FoodEntity) entity.create((ServerLevel) level);
                    foodEntity.moveTo((double)blockpos.getX()+0.5D, (double)blockpos.getY()+0.01D, (double)blockpos.getZ()+0.5D, direction.toYRot(), 0.0F);

                   level.addFreshEntity(foodEntity);

                    level.playSound((Player) null, blockpos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1F);
                    stack.shrink(1);
                    return stack;

            }
            return super.execute(source, stack);
        }
    };
    public InteractionResult useOn(UseOnContext context) {
        EntityType<?> entity=entityType.get();
        BlockState state=context.getLevel().getBlockState(context.getClickedPos());
        if(!context.getPlayer().isSuppressingBounce()){
            if(state.getBlock()== ItemAndBlockRegister.sushi_conveyor.get()&&!context.getLevel().isClientSide()){

                Vec3 vector3d = Vec3.atBottomCenterOf(context.getClickedPos());
                AABB axisalignedbb = entity.getDimensions().makeBoundingBox(vector3d.x(), vector3d.y(), vector3d.z());
                if (context.getLevel().getEntities((Entity) null, axisalignedbb).isEmpty()) {
                    FoodEntity foodEntity = (FoodEntity) entity.create((ServerLevel) context.getLevel());
                    foodEntity.moveTo((double)context.getClickedPos().getX()+0.5D, (double)context.getClickedPos().getY()+1.001D, (double)context.getClickedPos().getZ()+0.5D, context.getRotation(), 0.0F);

                    context.getLevel().addFreshEntity(foodEntity);
                    context.getItemInHand().shrink(1);
                    context.getLevel().playSound((Player) null, context.getClickedPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1F);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        }

        if ((context.getLevel().isClientSide)) {
            return InteractionResult.SUCCESS;
        }

        if(context.getPlayer().isSuppressingBounce()) {
            Vec3 vector3d = Vec3.atBottomCenterOf(context.getClickedPos());
            AABB axisalignedbb = entity.getDimensions().makeBoundingBox(vector3d.x(), vector3d.y(), vector3d.z());
            if (context.getLevel().getEntities((Entity) null, axisalignedbb).isEmpty()) {
                FoodEntity foodEntity = (FoodEntity) entity.create((ServerLevel) context.getLevel());
                if(state.getBlock()== ItemAndBlockRegister.sushi_conveyor.get()){
                    foodEntity.moveTo((double)context.getClickedPos().getX()+0.5D, (double)context.getClickedPos().getY()+1.001D, (double)context.getClickedPos().getZ()+0.5D, context.getRotation(), 0.0F);
                }else {
                    foodEntity.moveTo(context.getClickLocation().x, context.getClickLocation().y, context.getClickLocation().z, context.getRotation(), 0.0F);
                }
                context.getLevel().addFreshEntity(foodEntity);
                context.getItemInHand().shrink(1);
                context.getLevel().playSound((Player) null, context.getClickedPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1F);
                return InteractionResult.SUCCESS;
            }
        }else if(state.getBlock()== ItemAndBlockRegister.sushi_conveyor.get()){
            Vec3 vector3d = Vec3.atBottomCenterOf(context.getClickedPos());
            AABB axisalignedbb = entity.getDimensions().makeBoundingBox(vector3d.x(), vector3d.y(), vector3d.z());
            if (context.getLevel().getEntities((Entity) null, axisalignedbb).isEmpty()) {
                FoodEntity foodEntity = (FoodEntity) entity.create((ServerLevel) context.getLevel());
                foodEntity.moveTo((double)context.getClickedPos().getX()+0.5D, (double)context.getClickedPos().getY()+1.001D, (double)context.getClickedPos().getZ()+0.5D, context.getRotation(), 0.0F);

                context.getLevel().addFreshEntity(foodEntity);
                context.getItemInHand().shrink(1);
                context.getLevel().playSound((Player) null, context.getClickedPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        UrushiUtils.setInfo(list,"placeablefood");
    }
}
