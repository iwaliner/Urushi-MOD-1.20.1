package com.iwaliner.urushi.item.container;

import com.google.common.collect.Lists;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class DrawingBagContainer implements Container, StackedContentsCompatible {
    private final NonNullList<ItemStack> items;
    private final ItemStack bagStack;
    private final int size=54;
    @Nullable
    private List<ContainerListener> listeners;
    public DrawingBagContainer(ItemStack bag) {
        this.bagStack=bag;
        this.items =getItems(bagStack);
    }
    public NonNullList<ItemStack> getItems(ItemStack bagStack){
        CompoundTag tag =bagStack.getTag();
        if(tag==null){
            tag=new CompoundTag();
        }
        NonNullList<ItemStack> items = NonNullList.withSize(54, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
        return items;
    }

    public void addListener(ContainerListener p_19165_) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList();
        }

        this.listeners.add(p_19165_);
    }

    public void removeListener(ContainerListener p_19182_) {
        if (this.listeners != null) {
            this.listeners.remove(p_19182_);
        }

    }
    public ItemStack getItem(int p_19157_) {
        return p_19157_ >= 0 && p_19157_ < this.items.size() ? this.items.get(p_19157_) : ItemStack.EMPTY;
    }


    public ItemStack removeItem(int p_19159_, int p_19160_) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, p_19159_, p_19160_);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    public ItemStack removeItemType(Item p_19171_, int p_19172_) {
        ItemStack itemstack = new ItemStack(p_19171_, 0);

        for(int i = this.size - 1; i >= 0; --i) {
            ItemStack itemstack1 = this.getItem(i);
            if (itemstack1.getItem().equals(p_19171_)) {
                int j = p_19172_ - itemstack.getCount();
                ItemStack itemstack2 = itemstack1.split(j);
                itemstack.grow(itemstack2.getCount());
                if (itemstack.getCount() == p_19172_) {
                    break;
                }
            }
        }

        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }
    public ItemStack removeItemNoUpdate(int p_19180_) {
        ItemStack itemstack = this.items.get(p_19180_);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(p_19180_, ItemStack.EMPTY);
            return itemstack;
        }
    }

    public void setItem(int p_19162_, ItemStack stack) {
            this.items.set(p_19162_, stack);
            if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
                stack.setCount(this.getMaxStackSize());
            }
            this.setChanged();
    }
    public int getContainerSize() {
        return size;
    }

    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void setChanged() {
        if (this.listeners != null) {
            for(ContainerListener containerlistener : this.listeners) {
                containerlistener.containerChanged(this);
            }
        }
        CompoundTag tag =bagStack.getTag();
        if(tag==null){
            tag=new CompoundTag();
        }
        ContainerHelper.saveAllItems(tag, this.items);
        bagStack.setTag(tag);
    }

    public boolean stillValid(Player p_19167_) {
        return true;
    }

    public void clearContent() {
        this.items.clear();
        this.setChanged();
    }

    public void fillStackedContents(StackedContents p_19169_) {
        for(ItemStack itemstack : this.items) {
            p_19169_.accountStack(itemstack);
        }

    }

    public String toString() {
        return this.items.stream().filter((p_19194_) -> {
            return !p_19194_.isEmpty();
        }).collect(Collectors.toList()).toString();
    }

    private void moveItemToEmptySlots(ItemStack p_19190_) {
        for(int i = 0; i < this.size; ++i) {
            ItemStack itemstack = this.getItem(i);
            if (itemstack.isEmpty()) {
                this.setItem(i, p_19190_.copyAndClear());
                return;
            }
        }

    }

    private void moveItemToOccupiedSlotsWithSameType(ItemStack p_19192_) {
        for(int i = 0; i < this.size; ++i) {
            ItemStack itemstack = this.getItem(i);
            if (ItemStack.isSameItemSameTags(itemstack, p_19192_)) {
                this.moveItemsBetweenStacks(p_19192_, itemstack);
                if (p_19192_.isEmpty()) {
                    return;
                }
            }
        }

    }

    private void moveItemsBetweenStacks(ItemStack p_19186_, ItemStack p_19187_) {
        int i = Math.min(this.getMaxStackSize(), p_19187_.getMaxStackSize());
        int j = Math.min(p_19186_.getCount(), i - p_19187_.getCount());
        if (j > 0) {
            p_19187_.grow(j);
            p_19186_.shrink(j);
            this.setChanged();
        }

    }
}
