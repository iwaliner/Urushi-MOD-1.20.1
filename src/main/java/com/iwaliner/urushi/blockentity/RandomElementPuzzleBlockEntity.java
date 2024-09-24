package com.iwaliner.urushi.blockentity;



import com.iwaliner.urushi.BlockEntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public  class RandomElementPuzzleBlockEntity extends BlockEntity {

    public RandomElementPuzzleBlockEntity(BlockPos p_155052_, BlockState p_155053_) {
        super(BlockEntityRegister.RandomElementPuzzleBlock.get(), p_155052_, p_155053_);
    }


    public static void tick(Level level, BlockPos pos, BlockState bs, RandomElementPuzzleBlockEntity blockEntity) {
        if(!level.isClientSide()) {

            switch (level.getRandom().nextInt(5)) {
                case 0:
                    level.setBlock(pos, ItemAndBlockRegister.wood_element_puzzle_block.get().defaultBlockState(), 3);
                    break;
                case 1:
                    level.setBlock(pos, ItemAndBlockRegister.fire_element_puzzle_block.get().defaultBlockState(), 3);
                    break;
                case 2:
                    level.setBlock(pos, ItemAndBlockRegister.earth_element_puzzle_block.get().defaultBlockState(), 3);
                    break;
                case 3:
                    level.setBlock(pos, ItemAndBlockRegister.metal_element_puzzle_block.get().defaultBlockState(), 3);
                    break;
                case 4:
                    level.setBlock(pos, ItemAndBlockRegister.water_element_puzzle_block.get().defaultBlockState(), 3);
                    break;
            }
        }

    }




}
