package com.iwaliner.urushi.world.tree;

import com.iwaliner.urushi.ConfiguredFeatureRegister;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class MandarinTreeGrower extends AbstractTreeGrower {
    public MandarinTreeGrower() {
    }
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource p_204316_, boolean p_204317_) {
        return ConfiguredFeatureRegister.MANDARIN_KEY;
    }

}
