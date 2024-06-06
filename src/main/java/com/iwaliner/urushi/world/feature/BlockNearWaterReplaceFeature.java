package com.iwaliner.urushi.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;


import java.awt.*;

public class BlockNearWaterReplaceFeature extends Feature<BlockNearWaterReplaceFeature.Configuration> {

    public BlockNearWaterReplaceFeature(Codec<BlockNearWaterReplaceFeature.Configuration> p_65137_) {
        super(p_65137_);
    }

    public boolean place(FeaturePlaceContext<BlockNearWaterReplaceFeature.Configuration> p_159438_) {
        int n = 0;
        BlockPos blockpos = p_159438_.origin();
        WorldGenLevel worldgenlevel = p_159438_.level();
        RandomSource random = p_159438_.random();
        BlockNearWaterReplaceFeature.Configuration probabilityfeatureconfiguration = p_159438_.config();

        BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable();
         int range=3;
        boolean isNearWater=false;
         loop:for(int xx = -range; xx<=range; xx++){
            for(int yy=-range;yy<=range;yy++){
                for(int zz=-range;zz<=range;zz++){
                    if(worldgenlevel.getFluidState(blockpos.offset(xx,yy,zz)).is(Fluids.WATER)){
                        isNearWater=true;
                        break loop;
                    }
                }
            }
        }
        if (isNearWater) {
            if (worldgenlevel.getBlockState(blockpos$mutableblockpos.below())==probabilityfeatureconfiguration.base().getState(random,blockpos$mutableblockpos.below())||
                    worldgenlevel.getBlockState(blockpos$mutableblockpos.below())==probabilityfeatureconfiguration.base2().getState(random,blockpos$mutableblockpos.below())) {
             //   int m = random.nextInt(3) + 4;
            //    if (random.nextInt(2) ==0) {
                    int k = random.nextInt(4) + 1;

                    for(int l = blockpos.getX() - k; l <= blockpos.getX() + k; ++l) {
                        for(int i1 = blockpos.getZ() - k; i1 <= blockpos.getZ() + k; ++i1) {
                            int j1 = l - blockpos.getX();
                            int k1 = i1 - blockpos.getZ();
                            if (j1 * j1 + k1 * k1 <= k * k) {
                                blockpos$mutableblockpos.set(l, worldgenlevel.getHeight(Heightmap.Types.WORLD_SURFACE, l, i1) - 1, i1);

                            }
                        }
                    //}
                }

                if (worldgenlevel.getBlockState(blockpos$mutableblockpos) == probabilityfeatureconfiguration.base.getState(random, blockpos$mutableblockpos)||
                        worldgenlevel.getBlockState(blockpos$mutableblockpos) == probabilityfeatureconfiguration.base2.getState(random, blockpos$mutableblockpos)) {

                    worldgenlevel.setBlock(blockpos$mutableblockpos, probabilityfeatureconfiguration.replacer().getState(random, blockpos$mutableblockpos), 2);
                }


            }

            ++n;
        }

        return n > 0;
    }

    public static record Configuration(BlockStateProvider base,BlockStateProvider base2,BlockStateProvider replacer) implements FeatureConfiguration {
        public static final Codec<BlockNearWaterReplaceFeature.Configuration> CODEC = RecordCodecBuilder.create((p_190962_) -> {
            return p_190962_.group(BlockStateProvider.CODEC.fieldOf("base").forGetter(BlockNearWaterReplaceFeature.Configuration::base),BlockStateProvider.CODEC.fieldOf("base2").forGetter(BlockNearWaterReplaceFeature.Configuration::base2),BlockStateProvider.CODEC.fieldOf("replacer").forGetter(BlockNearWaterReplaceFeature.Configuration::replacer)).apply(p_190962_, BlockNearWaterReplaceFeature.Configuration::new);
        });
    }






}
