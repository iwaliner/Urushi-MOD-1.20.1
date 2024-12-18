package com.iwaliner.urushi.blockentity.menu;

import com.iwaliner.urushi.MenuRegister;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class DoubledWoodenCabinetryMenu extends AbstractContainerMenu {
    //private static final int SLOTS_PER_ROW = 18;
    private static final int SLOTS_PER_ROW = 16;
    private final Container container;
    private final int containerRows=6;
    public DoubledWoodenCabinetryMenu( int p_39225_, Inventory p_39226_) {
        this(MenuRegister.DoubledWoodenCabinetryMenu.get(), p_39225_, p_39226_, new SimpleContainer(108));
    }
    public static DoubledWoodenCabinetryMenu twRows(int p_39247_, Inventory p_39248_, Container p_39249_) {
        return new DoubledWoodenCabinetryMenu(MenuRegister.DoubledWoodenCabinetryMenu.get(), p_39247_, p_39248_, p_39249_);
    }
    public DoubledWoodenCabinetryMenu(MenuType<?> menuType, int p_39230_, Inventory inventory, Container container) {
        super(menuType, p_39230_);
        checkContainerSize(container, 108);
        this.container = container;
        container.startOpen(inventory.player);
        //int i = 2*18;
        int i = 3*18;

        for(int j = 0; j < this.containerRows; ++j) {
            for(int k = 0; k < SLOTS_PER_ROW; ++k) {
                this.addSlot(new Slot(container, k + j * SLOTS_PER_ROW, 8 + (k-0) * 18, 18 + j * 18));
            }
        }
        for(int k = 0; k < 12; ++k) {
            this.addSlot(new Slot(container, k + this.containerRows * SLOTS_PER_ROW, 8 + (k+0) * 18, 18 + containerRows * 18));
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + l * 9 + 9, 8 + j1 * 18+1*18 +45, 103 + l * 18 + i+1));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18+1*18 +45, 161 + i+1));
        }


    }
    public ItemStack quickMoveStack(Player p_82846_1_, int slotNumber) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotNumber);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (slotNumber < this.containerRows * SLOTS_PER_ROW+12) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows *SLOTS_PER_ROW+12, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * SLOTS_PER_ROW+12, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(Player p_39251_) {
        super.removed(p_39251_);
        this.container.stopOpen(p_39251_);
    }
    public boolean stillValid(Player p_39242_) {
        return this.container.stillValid(p_39242_);
    }
    public Container getContainer() {
        return this.container;
    }

    public boolean clickMenuButton(Player player, int p) {
        int startNumber=1;
        NonNullList<ItemStack> list1=NonNullList.withSize(108,ItemStack.EMPTY);

        List<Integer> idList=new ArrayList<>();
        for(int i=0;i<108;i++){
            ItemStack stack=this.slots.get(i).getItem();
            if(i<startNumber) {
                list1.set(i, ItemStack.EMPTY);
                idList.add(Item.getId(stack.getItem()));
            }else {
                list1.set(i, stack.copy());
                idList.add(Item.getId(stack.getItem()));
            }
        }


        LinkedHashMap<Integer, int[]> map = new LinkedHashMap<>();
        for(int i=0;i<108;i++){
            int id=idList.get(i);
            if (map.containsKey(id)) {
                int[] a=map.get(id);
                for(int j=0;j<108;j++){
                    if(a[j]==-2){
                        a[j]=i;
                        break;
                    }
                }
                map.put(id, a);
            } else {
                int[] a=new int[108];
                for(int j=0;j<108;j++){
                    a[j]=-2;
                }
                    a[0] = i;

                map.put(id, a);
            }
        }

        List<int[]> mapIdList = new ArrayList<>(map.values());
        int n=0;

        NonNullList<ItemStack> list2=NonNullList.withSize(108*108,ItemStack.EMPTY);
        NonNullList<ItemStack> list3=NonNullList.withSize(108*108,ItemStack.EMPTY);
        NonNullList<ItemStack> list4=NonNullList.withSize(108*108,ItemStack.EMPTY);

        for(int j=0;j<map.size();j++) {
            int[] sameItemSlots=mapIdList.get(j);
            int i0=sameItemSlots[0];
            int count=list1.get(i0).copy().getCount();
            ItemStack stack=list1.get(i0).copy();
            for(int i=1;i<sameItemSlots.length;i++) {
                if (sameItemSlots[i] != -2) {
                    boolean flag = ItemStack.isSameItemSameTags(stack, list1.get(sameItemSlots[i]).copy());
                    if (flag && !stack.isEmpty()) {
                        count += list1.get(sameItemSlots[i]).copy().getCount();
                    } else if (!flag) {
                        ++n;
                        list2.set(map.size() + n, list1.get(sameItemSlots[i]).copy());
                        list1.set(sameItemSlots[i], ItemStack.EMPTY);
                    }
                }
            }
            int i2=count/stack.getMaxStackSize();
            while (i2>0){
                ++n;
                ItemStack stack2=stack.copy();
                stack2.setCount(stack2.getMaxStackSize());
                list2.set(map.size()+n,stack2);
                --i2;
            }
            int i3=count%stack.getMaxStackSize();
            ++n;
            ItemStack stack3=stack.copy();
            stack3.setCount(i3);
            list2.set(j,stack3);
            list1.set(i0,ItemStack.EMPTY);
        }

        List<Integer> idList2=new ArrayList<>();
        for(int i=0;i<list2.size();i++){
            ItemStack stack=list2.get(i);
            idList2.add(Item.getId(stack.getItem()));
        }

        Collections.sort(idList2);

        for(int i=0;i<list2.size();i++) {

            int id = idList2.get(i);
            for (int j = 0; j < list2.size(); j++) {
                ItemStack stack = list2.get(j).copy();
                if (id == Item.getId(stack.getItem())) {
                    list3.set(i, stack);
                    list2.set(j, ItemStack.EMPTY);
                    break;
                }
            }

        }

        int emptyAmount=0;
        for(int i=0;i<list3.size();i++) {
            if(!list3.get(i).isEmpty()){
                emptyAmount=i;
                break;
            }
        }

        for(int i=0;i<list3.size()-emptyAmount;i++) {
            list4.set(i,list3.get(i+emptyAmount));
            list3.set(i,ItemStack.EMPTY);
            list3.set(i+emptyAmount,ItemStack.EMPTY);
        }


        for(int i=startNumber;i<108;i++){
            if(i<list4.size()){
                this.slots.get(i).set(list4.get(i-startNumber));
            }else{
                this.slots.get(i).set(ItemStack.EMPTY);
            }

        }
        return true;
    }
}
