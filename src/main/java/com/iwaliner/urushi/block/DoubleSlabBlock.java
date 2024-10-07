package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DoubleSlabBlock extends HorizonalRotateBlock {
    public static final IntegerProperty UNDER = IntegerProperty.create("under", 0, 70);
    public static final IntegerProperty UPPER = IntegerProperty.create("upper", 0, 70);


    public DoubleSlabBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(UNDER, 0).setValue(UPPER,0).setValue(FACING, Direction.NORTH));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,UNDER,UPPER);
    }
    public static int getIDFromBlock(Block block){
        if (ItemAndBlockRegister.sikkui_slab.get().equals(block)) {
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
            return 2330;
        }
        return 0;
    }
}
