package com.iwaliner.urushi.item;


import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.HiddenInvisibleButtonBlock;
import com.iwaliner.urushi.block.InvisibleButtonBlock;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.entiity.KitsunebiEntity;
import com.iwaliner.urushi.util.interfaces.ElementItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class KitsunebiItem extends Item implements ElementItem {
   public KitsunebiItem(Properties p_i48466_1_) {
      super(p_i48466_1_);
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      ItemStack magatama= ElementUtils.getMagatamaInInventory(player, ElementType.FireElement);
      if(magatama!=ItemStack.EMPTY&&ElementUtils.willBeInDomain(magatama,-10)) {

         world.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.5F, 1F);
         if (!world.isClientSide) {
            KitsunebiEntity entity = new KitsunebiEntity(world, player);
            entity.setItem(new ItemStack(ItemAndBlockRegister.kitsunebiItem.get()));
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(entity);
         }

         player.awardStat(Stats.ITEM_USED.get(this));
         if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
            ElementUtils.increaseStoredReiryokuAmount(magatama,-10);
         }

         return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
      }
      return InteractionResultHolder.fail(itemstack);
   }



   @Override
   public void inventoryTick(ItemStack stack, Level world, Entity entity, int a, boolean b) {
     if(entity instanceof LivingEntity){
        int range=15;
        LivingEntity player= (LivingEntity) entity;
        BlockPos pos=new BlockPos(Mth.floor(entity.getX()),Mth.floor(entity.getY()),Mth.floor(entity.getZ()));
        if (player.getOffhandItem() == stack || player.getMainHandItem() == stack) {
           for(int i=-range; i<range+1;i++) {
              for(int j=-range; j<range+1;j++) {
                 for(int k=-range; k<range+1;k++) {
                    if( world.getBlockState(pos.offset(i,j,k))== ItemAndBlockRegister.kitsunebiBlock.get().defaultBlockState()) {
                       world.setBlockAndUpdate(pos.offset(i, j, k), ItemAndBlockRegister.VisibleKitsunebiBlock.get().defaultBlockState());
                    }else if(world.getBlockState(pos.offset(i,j,k))== ItemAndBlockRegister.VisibleKitsunebiBlock.get().defaultBlockState()){
                       Random rand=new Random();
                       double d0 = (double)pos.getX()+(double)i + rand.nextDouble();
                       double d1 = (double)pos.getY() +(double)j+ rand.nextDouble();
                       double d2 = (double)pos.getZ() +(double)k+ rand.nextDouble();

                       if(rand.nextInt(8)==0) {
                          world.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);

                       }
                    }
                 }
              }
           }


        }
     }
   }



   @Override
   public InteractionResult useOn(UseOnContext context) {
      Level world = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      blockpos = blockpos.relative(context.getClickedFace());
      ItemStack magatama= ElementUtils.getMagatamaInInventory(Objects.requireNonNull(context.getPlayer()), ElementType.FireElement);
      if (world.getBlockState(blockpos).canBeReplaced()&&magatama!=ItemStack.EMPTY&&ElementUtils.willBeInDomain(magatama,-10)) {
         world.playSound((Player) null, context.getPlayer().getX(), context.getPlayer().getY(), context.getPlayer().getZ(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.5F, 1F);
         world.setBlockAndUpdate(blockpos, ItemAndBlockRegister.kitsunebiBlock.get().defaultBlockState());
         ElementUtils.increaseStoredReiryokuAmount(magatama,-10);
         context.getItemInHand().shrink(1);
       return  InteractionResult.SUCCESS;
      }else{
         return  InteractionResult.FAIL;
      }
   }
   @Override
   public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
      UrushiUtils.setInfo(list,"kitsunebi");
      UrushiUtils.setInfo(list,"kitsunebi2");
      UrushiUtils.setInfo(list,"kitsunebi3");
   }
     /**エンチャ金リンゴやエンチャアイテムみたいに輝く*/
   public boolean isFoil(ItemStack p_77636_1_) {
      return true;
   }

   @Override
   public ElementType getElementType() {
      return ElementType.FireElement;
   }
}