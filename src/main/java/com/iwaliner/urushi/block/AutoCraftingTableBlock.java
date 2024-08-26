package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.blockentity.AbstractFryerBlockEntity;
import com.iwaliner.urushi.blockentity.AutoCraftingTableBlockEntity;
import com.iwaliner.urushi.blockentity.ElementCraftingTableBlockEntity;
import com.iwaliner.urushi.blockentity.FryerBlockEntity;
import com.iwaliner.urushi.recipe.AbstractElementCraftingRecipe;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementBlock;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AutoCraftingTableBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = DirectionalBlock.FACING;

    public AutoCraftingTableBlock( Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
       }
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }
    public BlockState rotate(BlockState p_52716_, Rotation p_52717_) {
        return p_52716_.setValue(FACING, p_52717_.rotate(p_52716_.getValue(FACING)));
    }

    public BlockState mirror(BlockState p_52713_, Mirror p_52714_) {
        return p_52713_.rotate(p_52714_.getRotation(p_52713_.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_52719_) {
        p_52719_.add(FACING);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AutoCraftingTableBlockEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }


    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return p_152160_.isClientSide ? null : createTickerHelper(p_152162_, BlockEntityRegister.AutoCraftingTable.get(), AutoCraftingTableBlockEntity::tick);
    }
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, pos, player);
            return InteractionResult.CONSUME;
        }
    }
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof AutoCraftingTableBlockEntity) {
            player.openMenu((MenuProvider)blockentity);
        }
    }
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state2, boolean flag) {
        if (!state.is(state2.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof AutoCraftingTableBlockEntity) {
                if (level instanceof ServerLevel) {
                    for(int i = 10; i < ((AutoCraftingTableBlockEntity) blockentity).getContainerSize(); ++i) {
                        Containers.dropItemStack(level,  (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), ((AutoCraftingTableBlockEntity) blockentity).getItem(i));
                    }

                }

                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, state2, flag);
        }
    }
    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable BlockGetter getter, List<Component> list, TooltipFlag flag) {
        if(stack.getItem().equals(ItemAndBlockRegister.advanced_auto_crafting_table.get())){
            UrushiUtils.setInfo(list, "advanced_auto_crafting_table");
        }
        UrushiUtils.setInfo(list, "auto_crafting_table");
        UrushiUtils.setInfo(list, "auto_crafting_table2");

    }
    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return getRedstoneSignalFromContainer((Container) level.getBlockEntity(pos));
    }

    public boolean hasAnalogOutputSignal(BlockState p_149740_1_) {
        return true;
    }


    public static int getRedstoneSignalFromContainer(@javax.annotation.Nullable Container container) {
        if (container == null) {
            return 0;
        } else {
            int i = 0;
            float f = 0.0F;


                ItemStack itemstack = container.getItem(10);
                if (!itemstack.isEmpty()) {
                    f += (float)itemstack.getCount() / (float)itemstack.getMaxStackSize();
                    ++i;
            }


            return Mth.floor(f * 14.0F) + (i > 0 ? 1 : 0);
        }
    }
}
