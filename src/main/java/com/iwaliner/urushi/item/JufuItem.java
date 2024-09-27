package com.iwaliner.urushi.item;


import com.iwaliner.urushi.entiity.JufuEntity;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JufuItem extends Item implements ElementItem {
   private final String name;
   private final ElementType elementType;
   private final int cost;
   private final int textRow;

   public JufuItem(ElementType element,int cost,int textRow,String name,Properties p_i48466_1_) {
      super(p_i48466_1_);
      this.name=name;
      elementType=element;
      this.cost=cost;
      this.textRow=textRow;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      ItemStack magatama= ElementUtils.getMagatamaInInventory(player, elementType);
      if(stack.getItem() instanceof JufuItem&&magatama!=ItemStack.EMPTY&&ElementUtils.willBeInDomain(magatama,-cost)) {

         level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 0.5F, 1F);
         if (!level.isClientSide) {
            JufuEntity entity = new JufuEntity(level, player);
            entity.setItem(new ItemStack(stack.getItem()));
            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
            level.addFreshEntity(entity);
            player.getCooldowns().addCooldown(this, 15);
         }

         player.awardStat(Stats.ITEM_USED.get(this));
         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
            ElementUtils.increaseStoredReiryokuAmount(magatama,-cost);
         }


         return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
      }
      return InteractionResultHolder.fail(stack);
   }

   @Override
   public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
      for(int i=1;i<=this.textRow;i++) {
         UrushiUtils.setInfo(list, name+i);
      }
      list.add((Component.translatable("info.urushi.reiryoku_cost").append(" "+cost)).withStyle(ChatFormatting.GRAY));
   }

   @Override
   public ElementType getElementType() {
      return elementType;
   }
}