package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.blockentity.ElementCraftingTableBlockEntity;
import com.iwaliner.urushi.blockentity.EmitterBlockEntity;
import com.iwaliner.urushi.blockentity.TankBlockEntity;
import com.iwaliner.urushi.recipe.AbstractElementCraftingRecipe;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementBlock;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElementCraftingTableBlock extends BaseEntityBlock implements Tiered, ElementBlock {
    private final   ElementType elementType;
    private final int tier;
    private final java.util.function.Supplier<? extends RecipeType<? extends AbstractElementCraftingRecipe>> recipe;
    public static final BooleanProperty LIT=BlockStateProperties.LIT;
    public ElementCraftingTableBlock(int tier, ElementType elementType,java.util.function.Supplier<? extends RecipeType<? extends AbstractElementCraftingRecipe>> recipe, Properties p_49795_) {
        super(p_49795_);
        this.tier=tier;
        this.elementType=elementType;
        this.recipe=recipe;
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, Boolean.valueOf(false)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(LIT);
    }
    @Nullable
    @Override
    public int getTier() {
        return tier;
    }
    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ElementCraftingTableBlockEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list, "element_crafting_table1");
        UrushiUtils.setInfo(list, "element_crafting_table2");
    }

    @Override
    public ElementType getElementType() {
        return elementType;
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return createTickerHelper(p_152162_, BlockEntityRegister.ElementCraftingTable.get(), ElementCraftingTableBlockEntity::tick);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.getBlockEntity(pos) instanceof ElementCraftingTableBlockEntity &&!player.isSuppressingBounce()) {
            ElementCraftingTableBlockEntity blockEntity = (ElementCraftingTableBlockEntity) level.getBlockEntity(pos);
            if(player.getItemInHand(hand).getItem()== Items.BARRIER){
                blockEntity.addStoredReiryoku(100);
            }
            if(!level.isClientSide()) {
                player.displayClientMessage(ElementUtils.getStoredReiryokuDisplayMessage(blockEntity.getStoredReiryoku(), blockEntity.getReiryokuCapacity(), blockEntity.getStoredElementType()), true);
            }
            return InteractionResult.SUCCESS;

        }
        return InteractionResult.FAIL;
    }
    public RecipeType<? extends AbstractElementCraftingRecipe> getRecipeType(){
        return recipe.get();
    }

    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof ElementCraftingTableBlockEntity) {
                ItemStack itemstack = new ItemStack(this);
                BlockItem.setBlockEntityData(itemstack, BlockEntityRegister.ElementCraftingTable.get(), blockentity.saveWithoutMetadata());
                ItemEntity itementity = new ItemEntity(level, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), itemstack);
                itementity.setDefaultPickUpDelay();
                level.addFreshEntity(itementity);
            }
            super.playerWillDestroy(level, pos, state, player);
        }
    }
    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack= super.getCloneItemStack(state, target, level, pos, player);
        level.getBlockEntity(pos, BlockEntityRegister.ElementCraftingTable.get()).ifPresent((blockEntity) -> {
            BlockItem.setBlockEntityData(stack, BlockEntityRegister.ElementCraftingTable.get(), blockEntity.saveWithoutMetadata());
        });
        return stack;
    }
}
