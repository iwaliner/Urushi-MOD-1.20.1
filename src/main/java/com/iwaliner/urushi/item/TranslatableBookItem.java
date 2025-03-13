package com.iwaliner.urushi.item;

import com.iwaliner.urushi.MenuRegister;
import com.iwaliner.urushi.blockentity.menu.DoubledWoodenCabinetryMenu;
import com.iwaliner.urushi.blockentity.menu.TranslatableBookMenu;
import com.iwaliner.urushi.blockentity.menu.UrushiHopperMenu;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;

public class TranslatableBookItem extends Item {
    public TranslatableBookItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            player.openMenu(new SimpleMenuProvider((w, p, pl) -> new TranslatableBookMenu(w), stack.getHoverName()));
        }
        return InteractionResultHolder.success(stack);
    }
}
