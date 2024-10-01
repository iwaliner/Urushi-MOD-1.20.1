package com.iwaliner.urushi.world.feature;

import com.iwaliner.urushi.ConfigUrushi;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.TagUrushi;
import com.iwaliner.urushi.block.JapaneseTimberBambooBlock;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Random;

public class KakuriyoPortalFeature extends Feature<KakuriyoPortalFeature.Configuration> {

    public KakuriyoPortalFeature(Codec<KakuriyoPortalFeature.Configuration> p_65137_) {
        super(p_65137_);
    }

    public boolean place(FeaturePlaceContext<KakuriyoPortalFeature.Configuration> p_159438_) {
         BlockState Portal = ItemAndBlockRegister.kakuriyo_portal.get().defaultBlockState();
         BlockState RedFrame = ItemAndBlockRegister.ghost_red_kakuriyo_portal_frame.get().defaultBlockState();
         BlockState RedFrameHorizontal = ItemAndBlockRegister.ghost_red_kakuriyo_portal_frame.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.X);
         BlockState BlackFrame = ItemAndBlockRegister.ghost_black_kakuriyo_portal_frame.get().defaultBlockState();
         BlockState Core = ItemAndBlockRegister.ghost_kakuriyo_portal_core.get().defaultBlockState();

        RandomSource random = p_159438_.random();
        if(random.nextInt(ConfigUrushi.generateKakuriyoPortalProbability.get()*2)!=0){
            return false;
        }
        try {
            int n = 0;
            BlockPos blockpos = p_159438_.origin();
            WorldGenLevel worldgenlevel = p_159438_.level();
            KakuriyoPortalFeature.Configuration probabilityfeatureconfiguration = p_159438_.config();

            BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable();
            BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos.mutable();

            for (int i = -4; i < 5; i++) {
                int toSurfaceEach = 0;
                BlockState surfaceState = null;
                for (int s = 0; s < 370; s++) {
                    if (worldgenlevel.getBlockState(blockpos.offset(i, -s, 0)).isSolidRender(worldgenlevel, blockpos.offset(i, -s, 0))) {
                        toSurfaceEach = s;
                        surfaceState = worldgenlevel.getBlockState(blockpos.offset(i, -s, 0));
                        break;
                    }
                }
                for (int k = 1; k < toSurfaceEach; k++) {
                    if (surfaceState.getBlock() != Blocks.GRASS_BLOCK) {
                        worldgenlevel.setBlock(blockpos.offset(i, -k, 0), surfaceState, 2);
                    } else {
                        if (k == 1) {
                            worldgenlevel.setBlock(blockpos.offset(i, -k, 0), surfaceState, 2);
                        } else {
                            worldgenlevel.setBlock(blockpos.offset(i, -k, 0), Blocks.DIRT.defaultBlockState(), 2);
                        }
                    }
                }
            }

            //鳥居の基礎
            worldgenlevel.setBlock(blockpos.offset(2, 0, 0), BlackFrame, 2);
            worldgenlevel.setBlock(blockpos.offset(-2, 0, 0), BlackFrame, 2);
            //鳥居の左右の柱
            for (int i = 1; i < 6; i++) {
                worldgenlevel.setBlock(blockpos.offset(2, i, 0), RedFrame, 2);
                worldgenlevel.setBlock(blockpos.offset(-2, i, 0), RedFrame, 2);
            }
            //鳥居の横の柱
            for (int i = -1; i < 2; i++) {
                worldgenlevel.setBlock(blockpos.offset(i, 4, 0), RedFrameHorizontal, 2);
            }
            //鳥居の横の柱
            for (int i = -3; i < 4; i++) {
                worldgenlevel.setBlock(blockpos.offset(i, 6, 0), RedFrameHorizontal, 2);
            }
            worldgenlevel.setBlock(blockpos.offset(3, 4, 0), RedFrameHorizontal, 2);
            worldgenlevel.setBlock(blockpos.offset(4, 6, 0), RedFrameHorizontal, 2);
            worldgenlevel.setBlock(blockpos.offset(-3, 4, 0), RedFrameHorizontal, 2);
            worldgenlevel.setBlock(blockpos.offset(-4, 6, 0), RedFrameHorizontal, 2);
            worldgenlevel.setBlock(blockpos.offset(0, 5, 0), Core, 2);
            worldgenlevel.setBlock(blockpos.offset(1, 5, 0), Portal, 2);
            worldgenlevel.setBlock(blockpos.offset(-1, 5, 0), Portal, 2);
            //ポータル本体
            for (int i = -1; i < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    worldgenlevel.setBlock(blockpos.offset(i, j, 0), Portal, 2);
                }
            }

            //周りに空気を設置
            for (int i = -4; i < 5; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = -3; k <4; k++) {
                        Block block = worldgenlevel.getBlockState(blockpos.offset(i, j, k)).getBlock();
                        if (block != Portal.getBlock() && block != RedFrame.getBlock() && block != BlackFrame.getBlock() && block != Core.getBlock())
                            worldgenlevel.destroyBlock(blockpos.offset(i, j, k), false);
                    }
                }
            }

            n++;
            return n > 0;
        }catch (Exception e){
            return false;
        }
    }

    public static record Configuration(BlockStateProvider stateProvider) implements FeatureConfiguration {
        public static final Codec<KakuriyoPortalFeature.Configuration> CODEC = RecordCodecBuilder.create((p_190962_) -> {
            return p_190962_.group(BlockStateProvider.CODEC.fieldOf("kakuriyo_portal").forGetter(KakuriyoPortalFeature.Configuration::stateProvider)).apply(p_190962_, KakuriyoPortalFeature.Configuration::new);
        });
    }
}
