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


public class BlockReplaceFeature extends Feature<BlockReplaceFeature.Configuration> {

    public BlockReplaceFeature(Codec<BlockReplaceFeature.Configuration> p_65137_) {
        super(p_65137_);
    }

    public boolean place(FeaturePlaceContext<BlockReplaceFeature.Configuration> p_159438_) {
        int n = 0;
        BlockPos blockpos = p_159438_.origin();
        WorldGenLevel worldgenlevel = p_159438_.level();
        RandomSource random = p_159438_.random();
        BlockReplaceFeature.Configuration probabilityfeatureconfiguration = p_159438_.config();

        BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable();
       // BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos.mutable();
        if (worldgenlevel.isEmptyBlock(blockpos$mutableblockpos)) {
            if (worldgenlevel.getBlockState(blockpos$mutableblockpos.below())==probabilityfeatureconfiguration.base().getState(random,blockpos$mutableblockpos.below())) {
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


                    if(!worldgenlevel.getBlockState(blockpos$mutableblockpos.north()).isAir()&&!worldgenlevel.getBlockState(blockpos$mutableblockpos.east()).isAir()&&!worldgenlevel.getBlockState(blockpos$mutableblockpos.south()).isAir()&&!worldgenlevel.getBlockState(blockpos$mutableblockpos.west()).isAir()) {
                        if (worldgenlevel.getBlockState(blockpos$mutableblockpos) == probabilityfeatureconfiguration.base.getState(random, blockpos$mutableblockpos)) {
                            worldgenlevel.setBlock(blockpos$mutableblockpos, probabilityfeatureconfiguration.replacer().getState(random, blockpos$mutableblockpos), 2);
                        }
                    }


            }

            ++n;
        }

        return n > 0;
    }

    public static record Configuration(BlockStateProvider base,BlockStateProvider replacer) implements FeatureConfiguration {
        public static final Codec<BlockReplaceFeature.Configuration> CODEC = RecordCodecBuilder.create((p_190962_) -> {
            return p_190962_.group(BlockStateProvider.CODEC.fieldOf("base").forGetter(BlockReplaceFeature.Configuration::base),BlockStateProvider.CODEC.fieldOf("replacer").forGetter(BlockReplaceFeature.Configuration::replacer)).apply(p_190962_, BlockReplaceFeature.Configuration::new);
        });
    }






}
