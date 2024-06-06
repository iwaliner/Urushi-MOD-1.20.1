package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class FermentationBarrelBlock extends Block {
    private final java.util.function.Supplier<? extends Block> fermented_block;
    private final java.util.function.Supplier<? extends ItemStack> stack;
    private static final VoxelShape BOX_A = Block.box(0D, 0.0D, 0D, 16D, 16D, 2D);
    private static final VoxelShape BOX_B = Block.box(0D, 0.0D, 14D, 16D, 16D, 16D);
    private static final VoxelShape BOX_C = Block.box(0D, 0.0D, 0D, 2D, 16D, 16D);
    private static final VoxelShape BOX_D = Block.box(14D, 0.0D, 0D, 16D, 16D, 16D);
    private static final VoxelShape BOX_BASE = Block.box(0D, 1.0D, 0D, 16D, 3D, 16D);



    public FermentationBarrelBlock(java.util.function.Supplier<? extends Block> block,java.util.function.Supplier<? extends ItemStack> stack,Properties p_49795_) {
        super(p_49795_);
        this.fermented_block=block;
        this.stack=stack;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        return Shapes.or(BOX_BASE,BOX_A,BOX_B,BOX_C,BOX_D);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
            if(random.nextInt(5)==0){
                level.setBlockAndUpdate(pos,fermented_block.get().defaultBlockState());
            }
        }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        if(randomSource.nextInt(5)==0&&!state.getBlock().equals(ItemAndBlockRegister.fermentation_barrel.get()) && fermented_block.get().equals(Blocks.AIR)) {
            double d0 = (double) pos.getX() + 0.1D * randomSource.nextInt(11);
            double d1 = (double) pos.getY() + 1.0D;
            double d2 = (double) pos.getZ() + 0.1D * randomSource.nextInt(11);
            level.addParticle(ParticleTypes.EFFECT, d0, d1, d2, 0.0D, 0D, 0.0D);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(state.getBlock()!=ItemAndBlockRegister.fermentation_barrel.get()&&state.getBlock() instanceof FermentationBarrelBlock){
            level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
           if (player.getItemInHand(hand).isEmpty()) {
                player.setItemInHand(hand, stack.get());
            } else if (!player.getInventory().add(stack.get())) {
                player.drop(stack.get(), false);
            }
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.fermentation_barrel.get().defaultBlockState());
            return InteractionResult.SUCCESS;
        }else if(state.getBlock()==ItemAndBlockRegister.fermentation_barrel.get()){
            if(player.getItemInHand(hand).getItem()== ItemAndBlockRegister.rice.get()&&player.getItemInHand(hand).getCount()>=8){
                player.getItemInHand(hand).shrink(8);
                level.playSound((Player) null,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1F,1F);
                level.setBlockAndUpdate(pos,ItemAndBlockRegister.fermentation_barrel_with_rice.get().defaultBlockState());
                return InteractionResult.SUCCESS;
            }else if(player.getItemInHand(hand).getItem()== ItemAndBlockRegister.rice_malt.get()&&player.getItemInHand(hand).getCount()>=8){
                player.getItemInHand(hand).shrink(8);
                level.playSound((Player) null,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1F,1F);
                level.setBlockAndUpdate(pos,ItemAndBlockRegister.fermentation_barrel_with_rice_malt.get().defaultBlockState());
                return InteractionResult.SUCCESS;
            }else if(player.getItemInHand(hand).getItem()== ItemAndBlockRegister.shikomi_miso.get()&&player.getItemInHand(hand).getCount()>=8){
                player.getItemInHand(hand).shrink(8);
                level.playSound((Player) null,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1F,1F);
                level.setBlockAndUpdate(pos,ItemAndBlockRegister.fermentation_barrel_with_shikomi_miso.get().defaultBlockState());
                return InteractionResult.SUCCESS;
            }else if(player.getItemInHand(hand).getItem()== ItemAndBlockRegister.miso.get()&&player.getItemInHand(hand).getCount()>=8){
                player.getItemInHand(hand).shrink(8);
                level.playSound((Player) null,pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS,1F,1F);
                level.setBlockAndUpdate(pos,ItemAndBlockRegister.fermentation_barrel_with_miso.get().defaultBlockState());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }
    @Override
    public void appendHoverText(ItemStack stack, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        if(!fermented_block.get().equals(Blocks.AIR)) {
            UrushiUtils.setInfo(list, "fermentation_barrel");
            UrushiUtils.setInfo(list, "fermentation_barrel2");
        }
    }
}
