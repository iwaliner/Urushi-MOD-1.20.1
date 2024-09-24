package com.iwaliner.urushi.block;

import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.blockentity.HokoraBlockEntity;
import com.iwaliner.urushi.blockentity.TankBlockEntity;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.ElementUtils;
import com.iwaliner.urushi.util.UrushiUtils;
import com.iwaliner.urushi.util.interfaces.ElementBlock;
import com.iwaliner.urushi.util.interfaces.Tiered;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class HokoraBlock extends BaseEntityBlock implements Tiered, ElementBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape NORTH_BOX = Block.box(1D, 0.0D, 1D, 14D, 16D, 14D);
    private static final VoxelShape SOUTH_BOX = Block.box(2D, 0.0D, 2D, 15D, 16D, 15D);
    private static final VoxelShape EAST_BOX = Block.box(2D, 0.0D, 1D, 15D, 16D, 14D);
    private static final VoxelShape WEST_BOX = Block.box(1D, 0.0D, 2D, 14D, 16D, 15D);
    private final ElementType elementType;
    private final String string;
    public HokoraBlock(ElementType type,String s,Properties p_i48377_1_) {
        super(p_i48377_1_);
        elementType=type;
        string=s;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public int getTier() {
        return 2;    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        if(state.getValue(FACING)==Direction.EAST){
            return EAST_BOX;
        }else if(state.getValue(FACING)==Direction.WEST){
            return WEST_BOX;
        }else if(state.getValue(FACING)==Direction.SOUTH){
            return SOUTH_BOX;
        }else {
            return NORTH_BOX;
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return   new HokoraBlockEntity(pos,state);
    }

    public RenderShape getRenderShape(BlockState p_49090_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING);
    }
    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }


    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list,"hokora");
        UrushiUtils.setInfoWithColor(list,string, ChatFormatting.YELLOW);
    }

    @Override
    public ElementType getElementType() {
        return elementType;
    }
    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level p_152160_, BlockState p_152161_, BlockEntityType<T> p_152162_) {
        return createTickerHelper(p_152162_, BlockEntityRegister.Hokora.get(), HokoraBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(world.getBlockEntity(pos)instanceof HokoraBlockEntity) {
            if(!player.isSuppressingBounce()){
                HokoraBlockEntity  tileEntity= (HokoraBlockEntity) world.getBlockEntity(pos);
                ItemStack heldStack=player.getItemInHand(hand);
                ItemStack insertStack=heldStack.copy();
                insertStack.setCount(1);
                if(Objects.requireNonNull(tileEntity).canPlaceItem(0,insertStack)){
                    tileEntity.setItem(0,insertStack);
                    tileEntity.markUpdated();
                    heldStack.shrink(1);
                    world.playSound((Player) null,pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS,30F,10F);
                    return InteractionResult.SUCCESS;
                }else if(!tileEntity.isEmpty()){
                    ItemStack pickedStack = tileEntity.pickItem().copy();
                    if (heldStack.isEmpty()) {
                        tileEntity.markUpdated();
                        player.setItemInHand(hand, pickedStack);
                        world.playSound((Player) null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 30F, 10F);
                        return InteractionResult.SUCCESS;
                    } else if (!player.getInventory().add(pickedStack)) {
                        tileEntity.markUpdated();
                        player.drop(pickedStack, false);
                        world.playSound((Player) null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 30F, 10F);
                        return InteractionResult.SUCCESS;
                    }
                }else if(!world.isClientSide()) {
                        player.displayClientMessage(ElementUtils.getStoredReiryokuDisplayMessage(tileEntity.getStoredReiryoku(), tileEntity.getReiryokuCapacity(), tileEntity.getStoredElementType()), true);
                    }
                    return InteractionResult.SUCCESS;
                }


        }
        return InteractionResult.SUCCESS;
    }
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof HokoraBlockEntity) {
                ItemStack itemstack = new ItemStack(this);
                BlockItem.setBlockEntityData(itemstack, BlockEntityRegister.Hokora.get(), blockentity.saveWithoutMetadata());
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
        level.getBlockEntity(pos, BlockEntityRegister.Hokora.get()).ifPresent((blockEntity) -> {
            BlockItem.setBlockEntityData(stack, BlockEntityRegister.Hokora.get(), blockEntity.saveWithoutMetadata());
        });
        return stack;
    }
}
