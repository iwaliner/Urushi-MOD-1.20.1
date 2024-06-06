package com.iwaliner.urushi.blockentity;



import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ParticleRegister;
import com.iwaliner.urushi.block.DirtFurnaceBlock;
import com.iwaliner.urushi.block.RiceCauldronBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
 
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public  class SanboBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, StackedContentsCompatible {

    private static final int[] SLOTS_FOR_UP_AND_SIDES = new int[]{1};
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);

    public SanboBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.Sanbo.get(), p_155052_, p_155053_);
    }
    public void load(CompoundTag p_155025_) {
        super.load(p_155025_);
        this.items.clear();
        ContainerHelper.loadAllItems(p_155025_, this.items);
    }

    protected void saveAdditional(CompoundTag p_187452_) {
        super.saveAdditional(p_187452_);
       ContainerHelper.saveAllItems(p_187452_, this.items,true);
    }
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        ContainerHelper.saveAllItems(compoundtag, this.items, true);
        return compoundtag;
    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public ItemStack getDisplayingStack() {
        this.markUpdated();
        return this.getItem(1)==ItemStack.EMPTY? this.getItem(0) : this.getItem(1);
    }
    @Override
    public int getMaxStackSize() {
        return 1;
    }

    protected Component getDefaultName() {
        return Component.translatable("container.sanbo");
    }


    @Override
    protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
        return null;
    }


    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void moveItemToExportSlot() {
      /* this.setItem(1,this.getItem(0));
       this.removeItem(0,-1);
        this.markUpdated();*/

        ItemStack stack=this.getItem(0);
        if(!stack.isEmpty()) {
            this.setItem(1, stack);
            this.setItem(0, ItemStack.EMPTY);
        }
        this.markUpdated();
    }
    public ItemStack pickItem() {
        this.markUpdated();
        boolean slot0IsEmpty=this.getItem(0).isEmpty();
        ItemStack stack=slot0IsEmpty? this.getItem(1).copy() : this.getItem(0).copy();
        this.setItem(slot0IsEmpty? 1 : 0,ItemStack.EMPTY);
        return stack;
    }
    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        return ContainerHelper.removeItem(this.items, i, j);
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(this.items, i);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.markUpdated();
        ItemStack itemstack = this.items.get(slot);
        boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemstack);
        this.items.set(slot, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }




    }

    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }


    public void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
    }


    public static void tick(Level level, BlockPos pos, BlockState bs, SanboBlockEntity blockEntity) {
        ItemStack slot0Stack=blockEntity.items.get(0);
        Item slot0Item=slot0Stack.getItem();
        ItemStack slot1Stack=blockEntity.items.get(1);
        Item slot1Item=slot1Stack.getItem();
        BlockState state=level.getBlockState(pos);
        /*if(!slot0Stack.isEmpty()){
            level.addParticle(ParticleRegister.EarthElement.get(), pos.getX(),pos.getY()+1,pos.getZ(), 0.0D, 0.0D, 0.0D);
        }
        if(!slot1Stack.isEmpty()){
            level.addParticle(ParticleRegister.WaterElement.get(), pos.getX()+1,pos.getY()+1,pos.getZ(), 0.0D, 0.0D, 0.0D);
        }

        if(level.getBlockState(pos.above()).getBlock()== Blocks.GLASS){
            blockEntity.moveItemToExportSlot();
        }*/


    }
    public void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public boolean canPlaceItem(int slot, ItemStack stack) {
        if (slot == 1) {
            return false;
        }  else {
            return this.items.get(0).getCount()==0&&this.items.get(1).getCount()==0;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @org.jetbrains.annotations.Nullable Direction p_19237_) {
        return this.canPlaceItem(i, itemStack);
    }


    public boolean canTakeItemThroughFace(int i, ItemStack stack, Direction direction) {
        if ( direction==Direction.UP) {
            return false;
        }
        return true;

    }

    public int[] getSlotsForFace(Direction direction) {
        if(direction==Direction.UP){
            return SLOTS_FOR_UP;
        }else{
            return SLOTS_FOR_UP_AND_SIDES;
        }
    }
    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else
                return handlers[1].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        for(ItemStack itemstack : this.items) {
            contents.accountStack(itemstack);
        }
    }

}
