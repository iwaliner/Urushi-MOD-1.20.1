package com.iwaliner.urushi.block;

import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.recipe.SandpaperPolishingRecipe;
import com.iwaliner.urushi.recipe.SenbakokiRecipe;
import com.iwaliner.urushi.util.UrushiUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;


public class SandpaperBlock extends Block {
     public SandpaperBlock(Properties p_52591_) {
        super(p_52591_);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state2, boolean b) {
         if(!level.isClientSide()) {
             for (int i = 0; i < 6; i++) {
                 BlockState neighbor = level.getBlockState(pos.relative(UrushiUtils.getDirectionFromInt(i)));
                 BlockState defaultNeighbor=neighbor.getBlock().defaultBlockState();
                 Optional<SandpaperPolishingRecipe> recipe = Optional.of(level.getRecipeManager())
                         .flatMap(manager -> manager.getRecipeFor(RecipeTypeRegister.SandpaperPolishingRecipe,  new SimpleContainer(new ItemStack(Item.byBlock(defaultNeighbor.getBlock()))), level));
                 if (recipe.isPresent()) {
                    // Block ingredientBlock=Block.byItem(recipe.get().getIngredient().get(0).getItems()[0].getItem());
                     Block resultBlock=Block.byItem(recipe.get().getResultItem().getItem());
                     if( neighbor.getBlock() instanceof SlabBlock&&resultBlock instanceof SlabBlock){
                         level.setBlockAndUpdate(pos.relative(UrushiUtils.getDirectionFromInt(i)), resultBlock.defaultBlockState().setValue(SlabBlock.TYPE,neighbor.getValue(SlabBlock.TYPE)));
                     }else if( resultBlock instanceof StairBlock&&neighbor.getBlock() instanceof StairBlock){
                         level.setBlockAndUpdate(pos.relative(UrushiUtils.getDirectionFromInt(i)), resultBlock.defaultBlockState().setValue(StairBlock.HALF,neighbor.getValue(StairBlock.HALF)).setValue(StairBlock.FACING,neighbor.getValue(StairBlock.FACING)));
                     }else {
                         level.setBlockAndUpdate(pos.relative(UrushiUtils.getDirectionFromInt(i)), resultBlock.defaultBlockState());
                     }
                 }
              /*   if (TagUrushi.fileMap.containsKey(defaultNeighbor)) {
                   //  if (level.random.nextInt(5) == 0) {
                     if( TagUrushi.fileMap.get(defaultNeighbor).getBlock() instanceof SlabBlock&&neighbor.getBlock() instanceof SlabBlock){
                         level.setBlockAndUpdate(pos.relative(UrushiUtils.getDirectionFromInt(i)), TagUrushi.fileMap.get(defaultNeighbor).setValue(SlabBlock.TYPE,neighbor.getValue(SlabBlock.TYPE)));
                     }else if( TagUrushi.fileMap.get(defaultNeighbor).getBlock() instanceof StairBlock&&neighbor.getBlock() instanceof StairBlock){
                         level.setBlockAndUpdate(pos.relative(UrushiUtils.getDirectionFromInt(i)), TagUrushi.fileMap.get(defaultNeighbor).setValue(StairBlock.HALF,neighbor.getValue(StairBlock.HALF)).setValue(StairBlock.FACING,neighbor.getValue(StairBlock.FACING)));
                     }else {
                         level.setBlockAndUpdate(pos.relative(UrushiUtils.getDirectionFromInt(i)), TagUrushi.fileMap.get(defaultNeighbor));
                     }
                    // }
                 }*/
             }
             level.playSound((Player) null,pos, SoundEvents.VILLAGER_WORK_CARTOGRAPHER, SoundSource.BLOCKS,1F,1F);
         }
    }
    @Override
    public void appendHoverText(ItemStack p_49816_, @org.jetbrains.annotations.Nullable BlockGetter p_49817_, List<Component> list, TooltipFlag p_49819_) {
        UrushiUtils.setInfo(list,"sandpaper_block");
    }

}
