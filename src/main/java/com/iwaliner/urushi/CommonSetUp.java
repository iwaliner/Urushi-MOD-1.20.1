package com.iwaliner.urushi;

import com.iwaliner.urushi.block.ChiseledLacquerLogBlock;
import com.iwaliner.urushi.block.SenbakokiBlock;
import com.iwaliner.urushi.network.NetworkAccess;
import com.iwaliner.urushi.recipe.SenbakokiRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.VersionChecker;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.forgespi.language.IConfigurable;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.locating.ForgeFeature;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Mod.EventBusSubscriber(modid = ModCoreUrushi.ModID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetUp {

    @SubscribeEvent
    public static void CommonSetUpEvent(FMLCommonSetupEvent event) {
        SpawnPlacements.register(EntityRegister.Ghost.get(),SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        NetworkAccess.register();


        DispenserBlock.registerBehavior(ItemAndBlockRegister.empty_bamboo_cup.get().asItem(), new OptionalDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            private ItemStack takeLiquid(BlockSource p_123447_, ItemStack p_123448_, ItemStack p_123449_) {
                p_123448_.shrink(1);
                if (p_123448_.isEmpty()) {
                    p_123447_.getLevel().gameEvent((Entity)null, GameEvent.FLUID_PICKUP, p_123447_.getPos());
                    return p_123449_.copy();
                } else {
                    if (p_123447_.<DispenserBlockEntity>getEntity().addItem(p_123449_.copy()) < 0) {
                        this.defaultDispenseItemBehavior.dispense(p_123447_, p_123449_.copy());
                    }

                    return p_123448_;
                }
            }

            public ItemStack execute(BlockSource p_123444_, ItemStack p_123445_) {
                this.setSuccess(false);
                ServerLevel serverlevel = p_123444_.getLevel();
                BlockPos blockpos = p_123444_.getPos().relative(p_123444_.getBlockState().getValue(DispenserBlock.FACING));
                BlockState blockstate = serverlevel.getBlockState(blockpos);
                if (serverlevel.getFluidState(blockpos).is(FluidTags.WATER)) {
                    this.setSuccess(true);
                    return this.takeLiquid(p_123444_, p_123445_,new ItemStack(ItemAndBlockRegister.water_bamboo_cup.get()));
                } else {
                    return super.execute(p_123444_, p_123445_);
                }
            }
        });
        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
        DispenserBlock.registerBehavior(Items.BOWL, new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level level = source.getLevel();
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = source.getPos().relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.getBlock() instanceof ChiseledLacquerLogBlock) {
                    if (blockstate.getValue(ChiseledLacquerLogBlock.FILLED)) {
                        this.setSuccess(true);
                        stack.shrink(1);
                        level.setBlockAndUpdate(blockpos, ItemAndBlockRegister.chiseled_lacquer_log.get().defaultBlockState().setValue(ChiseledLacquerLogBlock.FILLED, Boolean.valueOf(false)).setValue(ChiseledLacquerLogBlock.FACING, blockstate.getValue(ChiseledLacquerLogBlock.FACING)));
                        level.gameEvent(null, GameEvent.BLOCK_PLACE, blockpos);
                        defaultDispenseItemBehavior.dispense(source, new ItemStack(ItemAndBlockRegister.raw_urushi_ball.get()).copy());

                    }
                    return stack;
                }
                return super.execute(source, stack);
            }
        });


        DispenserBlock.registerBehavior(ItemAndBlockRegister.rice_crop.get(), new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource source, ItemStack stack) {
                Level level = source.getLevel();
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos blockpos = source.getPos().relative(direction);
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.getBlock() instanceof SenbakokiBlock) {
                    Optional<SenbakokiRecipe> recipe = Optional.of(level.getRecipeManager() )
                            .flatMap(manager -> manager.getRecipeFor(RecipeTypeRegister.SenbakokiRecipe, new SimpleContainer(stack), level));
                    if (recipe.isPresent()) {
                        this.setSuccess(true);
                        stack.shrink(1);
                        defaultDispenseItemBehavior.dispense(source, recipe.get().getResultItem().copy());
                        for(int i=0;i<recipe.get().getSubResultItems().size();i++) {
                            defaultDispenseItemBehavior.dispense(source, recipe.get().getSubResultItems().get(i).copy());
                        }
                        return stack;
                    }
                }
                return super.execute(source, stack);
            }
        });




    }


}
