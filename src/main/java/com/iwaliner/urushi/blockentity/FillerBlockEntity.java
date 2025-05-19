package com.iwaliner.urushi.blockentity;


import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.block.WoodenCabinetrySlabBlock;
import com.iwaliner.urushi.blockentity.menu.DoubledWoodenCabinetryMenu;
import com.iwaliner.urushi.blockentity.menu.FillerMenu;
import com.iwaliner.urushi.util.ElementUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FillerBlockEntity extends RandomizableContainerBlockEntity {
    public BlockPos posOrigin=getBlockPos();
    public int rangeX;
    public int rangeY;
    public int rangeZ;
    public int modeIndex;

    private NonNullList<ItemStack> items = NonNullList.withSize(30, ItemStack.EMPTY);
    protected final ContainerData containerDataAccess = new ContainerData() {
        public int get(int i) {
            if (i == 0) {
                return FillerBlockEntity.this.modeIndex;
            }
            return 0;
        }
        public void set(int i, int j) {
            if (i == 0) {
                FillerBlockEntity.this.modeIndex =  j;
            }
        }
        public int getCount() {
            return 1;
        }
    };
    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level p_155062_, BlockPos p_155063_, BlockState p_155064_) {
          //  FillerBlockEntity.this.playSound(p_155064_, SoundEvents.BARREL_OPEN);
           // FillerBlockEntity.this.updateBlockState(p_155064_, true);
        }

        protected void onClose(Level p_155072_, BlockPos p_155073_, BlockState p_155074_) {
          //  FillerBlockEntity.this.playSound(p_155074_, SoundEvents.BARREL_CLOSE);
          //  FillerBlockEntity.this.updateBlockState(p_155074_, false);
        }

        protected void openerCountChanged(Level p_155066_, BlockPos p_155067_, BlockState p_155068_, int p_155069_, int p_155070_) {
        }

        protected boolean isOwnContainer(Player p_155060_) {
            if (p_155060_.containerMenu instanceof DoubledWoodenCabinetryMenu) {
                Container container = ((DoubledWoodenCabinetryMenu)p_155060_.containerMenu).getContainer();
                return container == FillerBlockEntity.this;
            } else {
                return false;
            }
        }
    };
    public FillerBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.Filler.get(), p_155052_, p_155053_);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }
        if(posOrigin!=null) {
            tag.put("posOrigin", NbtUtils.writeBlockPos(posOrigin));
        }
        tag.putInt("rangeX",rangeX);
        tag.putInt("rangeY",rangeY);
        tag.putInt("rangeZ",rangeZ);
    }

    @Override
    public void setItem(int i, ItemStack stack) {
        setChanged();
        super.setItem(i, stack);
    }
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        ContainerHelper.saveAllItems(compoundtag, this.items, true);
        if(posOrigin!=null) {
            compoundtag.put("posOrigin", NbtUtils.writeBlockPos(posOrigin));
        }
        compoundtag.putInt("rangeX",rangeX);
        compoundtag.putInt("rangeY",rangeY);
        compoundtag.putInt("rangeZ",rangeZ);
        return compoundtag;
    }
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
        if(tag.contains("posOrigin")) {
            this.posOrigin = NbtUtils.readBlockPos(tag.getCompound("posOrigin"));
        }
        rangeX=tag.getInt("rangeX");
        rangeY=tag.getInt("rangeY");
        rangeZ=tag.getInt("rangeZ");

    }
    public static void tick(Level level, BlockPos pos, BlockState bs, FillerBlockEntity blockEntity) {
        ItemStack magatamaStack=ItemStack.EMPTY;
        if(blockEntity.getItem(0).is(ItemAndBlockRegister.earth_element_magatama.get())&& ElementUtils.willBeInDomain(blockEntity.getItem(0),-10)){
            magatamaStack=blockEntity.getItem(0);
        }else if(blockEntity.getItem(1).is(ItemAndBlockRegister.earth_element_magatama.get())&& ElementUtils.willBeInDomain(blockEntity.getItem(1),-10)){
            magatamaStack=blockEntity.getItem(1);
        }if(blockEntity.getItem(2).is(ItemAndBlockRegister.earth_element_magatama.get())&& ElementUtils.willBeInDomain(blockEntity.getItem(2),-10)){
            magatamaStack=blockEntity.getItem(2);
        }
        if(!magatamaStack.equals(ItemStack.EMPTY)){
            int index=blockEntity.modeIndex;
            BlockPos originPos=blockEntity.posOrigin;
            int xRange=blockEntity.rangeX;
            int yRange=blockEntity.rangeY;
            int zRange=blockEntity.rangeZ;
            if(index==1){

            }
        }

    }

    public int getContainerSize() {
        return 30;
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(NonNullList<ItemStack> p_199721_1_) {
        this.items = p_199721_1_;
    }

    protected Component getDefaultName() {
        return Component.translatable("container.urushi.filler");
    }

    protected AbstractContainerMenu createMenu(int p_58598_, Inventory p_58599_) {
        return new FillerMenu(p_58598_, p_58599_,this,containerDataAccess);
    }

    public void startOpen(Player p_58616_) {
        if (!this.remove && !p_58616_.isSpectator()) {
            this.openersCounter.incrementOpeners(p_58616_, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void stopOpen(Player p_58614_) {
        if (!this.remove && !p_58614_.isSpectator()) {
            this.openersCounter.decrementOpeners(p_58614_, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    /*void updateBlockState(BlockState p_58607_, boolean p_58608_) {
        this.level.setBlock(this.getBlockPos(), p_58607_.setValue(WoodenCabinetrySlabBlock.OPEN, Boolean.valueOf(p_58608_)), 3);
    }

    void playSound(BlockState p_58601_, SoundEvent p_58602_) {
        Vec3i vec3i = p_58601_.getValue(WoodenCabinetrySlabBlock.FACING).getNormal();
        double d0 = (double)this.worldPosition.getX() + 0.5D + (double)vec3i.getX() / 2.0D;
        double d1 = (double)this.worldPosition.getY() + 0.5D + (double)vec3i.getY() / 2.0D;
        double d2 = (double)this.worldPosition.getZ() + 0.5D + (double)vec3i.getZ() / 2.0D;
        this.level.playSound((Player)null, d0, d1, d2, p_58602_, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }*/
    public ItemStack getDisplayStack(){
        this.setChanged();
        return getItem(0);
    }
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
