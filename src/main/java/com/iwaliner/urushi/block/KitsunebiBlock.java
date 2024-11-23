package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.blockentity.InvisibleButtonBlockEntity;
import com.iwaliner.urushi.blockentity.KitsunebiBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class KitsunebiBlock extends BaseEntityBlock {
    public KitsunebiBlock(Properties p_49092_) {
        super(p_49092_);
    }
    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return   new KitsunebiBlockEntity(pos,state);
    }
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return createTickerHelper(p_152162_, BlockEntityRegister.Kitsunebi.get(), KitsunebiBlockEntity::tick);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean b) {
        BlockEntity baseBlockEntity=level.getBlockEntity(pos);
        if(baseBlockEntity instanceof KitsunebiBlockEntity){
            KitsunebiBlockEntity blockentity= (KitsunebiBlockEntity) baseBlockEntity;
            ((KitsunebiBlockEntity) baseBlockEntity).time=20*5;
        }
    }
    public ItemStack getCloneItemStack(BlockGetter p_152966_, BlockPos p_152967_, BlockState p_152968_) {
        return new ItemStack(ItemAndBlockRegister.kitsunebiItem.get());
    }

    public boolean triggerEvent(BlockState p_49226_, Level p_49227_, BlockPos p_49228_, int p_49229_, int p_49230_) {
        super.triggerEvent(p_49226_, p_49227_, p_49228_, p_49229_, p_49230_);
        BlockEntity blockentity = p_49227_.getBlockEntity(p_49228_);
        return blockentity == null ? false : blockentity.triggerEvent(p_49229_, p_49230_);
    }

    @Nullable
    public MenuProvider getMenuProvider(BlockState p_49234_, Level p_49235_, BlockPos p_49236_) {
      return  null;
    }

    @Nullable
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }
    public boolean propagatesSkylightDown(BlockState p_49100_, BlockGetter p_49101_, BlockPos p_49102_) {
        return true;
    }

    public RenderShape getRenderShape(BlockState p_49098_) {
        return RenderShape.INVISIBLE;
    }

    public float getShadeBrightness(BlockState p_49094_, BlockGetter p_49095_, BlockPos p_49096_) {
        return 1.0F;
    }
}
