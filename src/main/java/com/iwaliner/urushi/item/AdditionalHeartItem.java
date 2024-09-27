package com.iwaliner.urushi.item;

import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.network.AdditionalHeartProvider;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.Objects;

public class AdditionalHeartItem extends Item {
    public AdditionalHeartItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.getCapability(AdditionalHeartProvider.ADDITIONAL_HEART).ifPresent(data -> {
            data.increaseHeart();
            Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(player.getAttribute(Attributes.MAX_HEALTH).getBaseValue()+2);
            stack.shrink(1);
        });
        return InteractionResultHolder.success(stack);
    }
}
