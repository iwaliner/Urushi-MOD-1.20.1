package com.iwaliner.urushi.item;

import com.iwaliner.urushi.item.container.DrawingBagContainer;
import com.iwaliner.urushi.item.menu.DrawstringBagMenu;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DrawstringBagItem extends Item implements DyeableLeatherItem {

    public DrawstringBagItem(Item.Properties p_43445_) {
        super(p_43445_);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        UrushiUtils.setInfo(list,"drawstring_bag");
        UrushiUtils.setInfo(list,"dyeable");
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {

            player.openMenu(new SimpleMenuProvider((w, p, pl) ->  DrawstringBagMenu.sixRows(w, p, getContainer(stack)), stack.getHoverName()));
        }
        return InteractionResultHolder.success(stack);
    }
    private Container getContainer(ItemStack bagStack){
        Container container=new DrawingBagContainer(bagStack);
        return container;
    }
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
