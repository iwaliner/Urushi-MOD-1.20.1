package com.iwaliner.urushi.block;

import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.util.ElementType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class ElementPuzzleControllerBlock extends HorizonalRotateBlock {
    protected static final VoxelShape SHAPEA = Block.box(0D, 0.0D, 5D, 16D, 0.1D, 11D);
    protected static final VoxelShape SHAPEB = Block.box(5D, 0.0D, 0D, 11D, 0.1D, 16D);

    public ElementPuzzleControllerBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if(state.getValue(FACING)== Direction.NORTH||state.getValue(FACING)==Direction.SOUTH){
            return SHAPEB;
        }else{
            return SHAPEA;
        }
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
      BlockPos rootPos=pos;
      outer:  for(int i=-5;i<6;i++){
                for(int j=-5;j<6;j++){
                    BlockPos pos2=pos.offset(i,-1,j);
                    if(level.getBlockState(pos2).getBlock()==ItemAndBlockRegister.wood_element_puzzle_block.get()||
                            level.getBlockState(pos2).getBlock()==ItemAndBlockRegister.fire_element_puzzle_block.get()||
                            level.getBlockState(pos2).getBlock()==ItemAndBlockRegister.earth_element_puzzle_block.get()||
                            level.getBlockState(pos2).getBlock()==ItemAndBlockRegister.metal_element_puzzle_block.get()||
                            level.getBlockState(pos2).getBlock()==ItemAndBlockRegister.water_element_puzzle_block.get()){
                        rootPos=pos2;
                        break outer;
                    }
            }
        }
        if(this==ItemAndBlockRegister.element_puzzle_controller_A.get()){
            changeElement(level,rootPos);
            changeElement(level,rootPos.above(1));
            changeElement(level,rootPos.above(2));
        }else if(this==ItemAndBlockRegister.element_puzzle_controller_B.get()){
            changeElement(level,rootPos.above(2));
            changeElement(level,rootPos.above(3));
        }else if(this==ItemAndBlockRegister.element_puzzle_controller_C.get()){
            changeElement(level,rootPos);
            changeElement(level,rootPos.above(3));
        }
        checkGameClear(level,rootPos,pos);
        return InteractionResult.SUCCESS;
    }
    private void changeElement(Level level,BlockPos pos){
        Block block=level.getBlockState(pos).getBlock();
        for(int i=0; i<20;i++) {
            level.addParticle(ParticleTypes.WAX_ON, pos.getX() - 0.0625D * 2 + 0.0625D * level.getRandom().nextInt(20), pos.getY() + 0.0625D * level.getRandom().nextInt(16), pos.getZ() - 0.0625D * 2 + 0.0625D * level.getRandom().nextInt(20), 0.0D, 0.0D, 0.0D);
        }
        level.playSound((Player) null, pos, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS, 1F, 1F);

        if(block==ItemAndBlockRegister.wood_element_puzzle_block.get()){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.fire_element_puzzle_block.get().defaultBlockState());
        }else  if(block==ItemAndBlockRegister.fire_element_puzzle_block.get()){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.earth_element_puzzle_block.get().defaultBlockState());
        }else  if(block==ItemAndBlockRegister.earth_element_puzzle_block.get()){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.metal_element_puzzle_block.get().defaultBlockState());
        }else  if(block==ItemAndBlockRegister.metal_element_puzzle_block.get()){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.water_element_puzzle_block.get().defaultBlockState());
        }else  if(block==ItemAndBlockRegister.water_element_puzzle_block.get()){
            level.setBlockAndUpdate(pos,ItemAndBlockRegister.wood_element_puzzle_block.get().defaultBlockState());
        }
    }
    private void checkGameClear(Level level,BlockPos pos,BlockPos controllerPos){
        Block block1=level.getBlockState(pos).getBlock();
        Block block2=level.getBlockState(pos.above()).getBlock();
        Block block3=level.getBlockState(pos.above(2)).getBlock();
        Block block4=level.getBlockState(pos.above(3)).getBlock();
        if(block1==block2&&block1==block3&&block1==block4){
            for(int i=0; i<80;i++) {
                level.addParticle(ParticleTypes.END_ROD, pos.getX() - 0.0625D * 2 + 0.0625D * level.getRandom().nextInt(20), pos.getY()+4D + 0.0625D * level.getRandom().nextInt(16), pos.getZ() - 0.0625D * 2 + 0.0625D * level.getRandom().nextInt(20), 0.0D, 0.0D, 0.0D);
            }
            level.playSound((Player) null, pos, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 1F, 1F);
            if(block1==ItemAndBlockRegister.wood_element_puzzle_block.get()){
               List<Item> jufuList=new ArrayList<>();
               jufuList.add(ItemAndBlockRegister.liana_jufu_stamp.get());
                jufuList.add(ItemAndBlockRegister.knockback_jufu_stamp.get());
                jufuList.add(ItemAndBlockRegister.jump_jufu_stamp.get());



                ItemEntity itemEntity=new ItemEntity(level,(double) pos.getX()+1.5D,(double) pos.getY()+4.5D,(double) pos.getZ()+0.5D,new ItemStack(jufuList.get(level.getRandom().nextInt(jufuList.size()))));
                level.addFreshEntity(itemEntity);
            }else if(block1==ItemAndBlockRegister.fire_element_puzzle_block.get()){
                List<Item> jufuList=new ArrayList<>();
                jufuList.add(ItemAndBlockRegister.lava_generation_jufu_stamp.get());
                jufuList.add(ItemAndBlockRegister.explosion_jufu_stamp.get());

                ItemEntity itemEntity=new ItemEntity(level,(double) pos.getX()+1.5D,(double) pos.getY()+4.5D,(double) pos.getZ()+0.5D,new ItemStack(jufuList.get(level.getRandom().nextInt(jufuList.size()))));
                level.addFreshEntity(itemEntity);
            }if(block1==ItemAndBlockRegister.earth_element_puzzle_block.get()){
                List<Item> jufuList=new ArrayList<>();
                jufuList.add(ItemAndBlockRegister.growth_jufu_stamp.get());
                jufuList.add(ItemAndBlockRegister.mountain_creation_jufu_stamp.get());
                jufuList.add(ItemAndBlockRegister.fluid_erasion_jufu_stamp.get());

                ItemEntity itemEntity=new ItemEntity(level,(double) pos.getX()+1.5D,(double) pos.getY()+4.5D,(double) pos.getZ()+0.5D,new ItemStack(jufuList.get(level.getRandom().nextInt(jufuList.size()))));
                level.addFreshEntity(itemEntity);
            }if(block1==ItemAndBlockRegister.metal_element_puzzle_block.get()){
                List<Item> jufuList=new ArrayList<>();
                jufuList.add(ItemAndBlockRegister.spike_jufu_stamp.get());
                jufuList.add(ItemAndBlockRegister.crush_jufu_stamp.get());

                ItemEntity itemEntity=new ItemEntity(level,(double) pos.getX()+1.5D,(double) pos.getY()+4.5D,(double) pos.getZ()+0.5D,new ItemStack(jufuList.get(level.getRandom().nextInt(jufuList.size()))));
                level.addFreshEntity(itemEntity);
            }if(block1==ItemAndBlockRegister.water_element_puzzle_block.get()){
                List<Item> jufuList=new ArrayList<>();
                jufuList.add(ItemAndBlockRegister.freezing_jufu_stamp.get());

                ItemEntity itemEntity=new ItemEntity(level,(double) pos.getX()+1.5D,(double) pos.getY()+4.5D,(double) pos.getZ()+0.5D,new ItemStack(jufuList.get(level.getRandom().nextInt(jufuList.size()))));
                level.addFreshEntity(itemEntity);
            }
            for(int i=-5;i<6;i++){
                for(int j=-5;j<6;j++){
                    BlockPos pos2=controllerPos.offset(i,0,j);

                        if (level.getBlockState(pos2).getBlock() instanceof ElementPuzzleControllerBlock) {
                            level.setBlockAndUpdate(pos2, Blocks.AIR.defaultBlockState());

                    }
                }
            }

        }
    }

}
