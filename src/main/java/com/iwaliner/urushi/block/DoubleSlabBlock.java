package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class DoubleSlabBlock extends HorizonalRotateBlock {
    public static final IntegerProperty UNDER = IntegerProperty.create("under", 0, 140);
    public static final IntegerProperty UPPER = IntegerProperty.create("upper", 0, 140);
    public static List<Block> slabList=new ArrayList<>();

    public DoubleSlabBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(UNDER, 0).setValue(UPPER,0).setValue(FACING, Direction.NORTH));

    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,UNDER,UPPER);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return target.getLocation().y- pos.getY() > 0.5D? new ItemStack( slabList.get(state.getValue(UNDER)) ): new ItemStack(slabList.get(state.getValue(UPPER)));
    }

    public static int getIDFromBlock(Block block){
        return slabList.indexOf(block);

        /*if (ItemAndBlockRegister.sikkui_slab.get().equals(block)) {
            return 0;
        }else if (ItemAndBlockRegister.orange_plaster_slab.get().equals(block)) {
            return 1;
        }else if (ItemAndBlockRegister.magenta_plaster_slab.get().equals(block)) {
            return 2;
        }else if (ItemAndBlockRegister.light_blue_plaster_slab.get().equals(block)) {
            return 3;
        }else if (ItemAndBlockRegister.yellow_plaster_slab.get().equals(block)) {
            return 4;
        }else if (ItemAndBlockRegister.lime_plaster_slab.get().equals(block)) {
            return 5;
        }else if (ItemAndBlockRegister.pink_plaster_slab.get().equals(block)) {
            return 6;
        }else if (ItemAndBlockRegister.gray_plaster_slab.get().equals(block)) {
            return 7;
        }else if (ItemAndBlockRegister.light_gray_plaster_slab.get().equals(block)) {
            return 8;
        }else if (ItemAndBlockRegister.cyan_plaster_slab.get().equals(block)) {
            return 9;
        }else if (ItemAndBlockRegister.purple_plaster_slab.get().equals(block)) {
            return 10;
        }else if (ItemAndBlockRegister.blue_plaster_slab.get().equals(block)) {
            return 11;
        }else if (ItemAndBlockRegister.brown_plaster_slab.get().equals(block)) {
            return 12;
        }else if (ItemAndBlockRegister.green_plaster_slab.get().equals(block)) {
            return 13;
        }else if (ItemAndBlockRegister.red_plaster_slab.get().equals(block)) {
            return 14;
        }else if (ItemAndBlockRegister.black_plaster_slab.get().equals(block)) {
            return 15;
        } else if (ItemAndBlockRegister.green_tatami_slab.get().equals(block)) {
            return 16;
        }else if (ItemAndBlockRegister.brown_tatami_slab.get().equals(block)) {
            return 17;
        }else if (ItemAndBlockRegister.concrete_slab.get().equals(block)) {
            return 18;
        }else if (ItemAndBlockRegister.dirty_concrete_slab.get().equals(block)) {
            return 19;
        }else if (ItemAndBlockRegister.thatched_slab.get().equals(block)) {
            return 20;
        }else if (ItemAndBlockRegister.hiwadabuki_slab.get().equals(block)) {
            return 21;
        }else if (ItemAndBlockRegister.rough_stone_slab.get().equals(block)) {
            return 22;
        }else if (ItemAndBlockRegister.ishigaki_slab.get().equals(block)) {
            return 23;
        }else if (ItemAndBlockRegister.bamboo_slab.get().equals(block)) {
            return 24;
        }else if (ItemAndBlockRegister.japanese_apricot_slab.get().equals(block)) {
            return 25;
        }else if (ItemAndBlockRegister.sakura_slab.get().equals(block)) {
            return 26;
        }else if (ItemAndBlockRegister.cypress_slab.get().equals(block)) {
            return 27;
        }else if (ItemAndBlockRegister.japanese_cedar_slab.get().equals(block)) {
            return 28;
        }else if (ItemAndBlockRegister.red_urushi_slab.get().equals(block)) {
            return 29;
        }else if (ItemAndBlockRegister.black_urushi_slab.get().equals(block)) {
            return 30;
        }else if (ItemAndBlockRegister.smooth_oak_slab.get().equals(block)) {
            return 31;
        }else if (ItemAndBlockRegister.smooth_spruce_slab.get().equals(block)) {
            return 32;
        }else if (ItemAndBlockRegister.smooth_birch_slab.get().equals(block)) {
            return 33;
        }else if (ItemAndBlockRegister.smooth_jungle_slab.get().equals(block)) {
            return 34;
        }else if (ItemAndBlockRegister.smooth_acacia_slab.get().equals(block)) {
            return 35;
        }else if (ItemAndBlockRegister.smooth_dark_oak_slab.get().equals(block)) {
            return 36;
        }else if (ItemAndBlockRegister.smooth_japanese_apricot_slab.get().equals(block)) {
            return 37;
        }else if (ItemAndBlockRegister.smooth_sakura_slab.get().equals(block)) {
            return 38;
        }else if (ItemAndBlockRegister.smooth_cypress_slab.get().equals(block)) {
            return 39;
        }else if (ItemAndBlockRegister.smooth_japanese_cedar_slab.get().equals(block)) {
            return 40;
        }else if (ItemAndBlockRegister.smooth_red_urushi_slab.get().equals(block)) {
            return 41;
        }else if (ItemAndBlockRegister.smooth_black_urushi_slab.get().equals(block)) {
            return 42;
        }
        return 0;*/
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_287732_, LootParams.Builder p_287596_) {
        return super.getDrops(p_287732_, p_287596_);
    }
}
