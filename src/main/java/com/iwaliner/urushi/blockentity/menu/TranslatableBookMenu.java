package com.iwaliner.urushi.blockentity.menu;

import com.iwaliner.urushi.MenuRegister;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TranslatableBookMenu extends AbstractContainerMenu {
    int page;
    public TranslatableBookMenu(MenuType<?> menuType, int p_39230_) {
        super(menuType, p_39230_);
    }
    public TranslatableBookMenu(int windowId) {
        super(MenuRegister.TranslatableBookMenu.get(), windowId);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }

    public boolean clickMenuButton(Player player, int variable) {
        if(variable==0&&getPage()<5){
            setPage(getPage()+1);
            return true;
        }else if(variable==1&&getPage()>0){
            setPage(getPage()-1);
            return true;
        }
        return false;
    }
}
