package com.iwaliner.urushi.world.tree;

import com.iwaliner.urushi.ConfiguredFeatureRegister;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import java.util.Random;

public class CedarTreeGrower extends AbstractMegaTreeGrower {
    public CedarTreeGrower() {
    }
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_204316_, boolean p_204317_) {
       return ConfiguredFeatureRegister.CEDAR_KEY;
    }
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource p_204332_) {
      return ConfiguredFeatureRegister.MEGA_CEDAR_KEY;
    }
}
