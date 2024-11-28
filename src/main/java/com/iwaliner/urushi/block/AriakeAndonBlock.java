package com.iwaliner.urushi.block;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ModCoreUrushi;
import com.iwaliner.urushi.ParticleRegister;
import com.mojang.authlib.minecraft.client.ObjectMapper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.util.RandomSource;
import net.minecraftforge.fml.loading.FMLPaths;
import org.openjdk.nashorn.internal.parser.JSONParser;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class AriakeAndonBlock extends HorizonalRotateBlock {
    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 12.0D, 13.0D);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public AriakeAndonBlock(Properties p_i48377_1_) {

        super(p_i48377_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(OPEN, Boolean.valueOf(false)));

    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
        p_49915_.add(FACING,OPEN);
    }
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
       // level.addParticle(ParticleRegister.Test.get(), pos.getX(), pos.getY()+2, pos.getZ(), 0.0D, 0D, 0.0D);
        //Minecraft.getInstance().gameRenderer.displayItemActivation(player.getItemInHand(hand));



        //Minecraft.getInstance().mouseHandler.isMiddlePressed();


       // level.addDestroyBlockEffect(blockpos2, blockstate1);

//Minecraft.getInstance().font=new Font(new Function<ResourceLocation, FontSet>(new ResourceLocation(ModCoreUrushi.ModID,""),new FontSet(Minecraft.getInstance().textureManager,new ResourceLocation(ModCoreUrushi.ModID,""))) ;

        if(state.getValue(OPEN)){
           level.setBlockAndUpdate(pos,state.setValue(OPEN,Boolean.valueOf(false)));
           level.playSound((Player) null,pos, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS,1F,1F);

       }else{
           level.setBlockAndUpdate(pos,state.setValue(OPEN,Boolean.valueOf(true)));
           level.playSound((Player) null,pos, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS,1F,1F);
       }
        return InteractionResult.SUCCESS;
    }
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 60;
    }


}
