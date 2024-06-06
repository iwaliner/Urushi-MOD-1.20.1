package com.iwaliner.urushi.world.feature;

import com.google.common.collect.ImmutableList;
import com.iwaliner.urushi.ItemAndBlockRegister;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import java.util.List;
import java.util.Optional;

public class KakuriyoTreeConfigration extends TreeConfiguration {
    protected KakuriyoTreeConfigration(BlockStateProvider p_191337_, TrunkPlacer p_191338_, BlockStateProvider p_191339_, FoliagePlacer p_191340_, Optional<RootPlacer> p_225461_, BlockStateProvider p_191341_, FeatureSize p_191342_, List<TreeDecorator> p_191343_, boolean p_191344_, boolean p_191345_) {
        super(p_191337_, p_191338_, p_191339_, p_191340_,p_225461_, p_191341_, p_191342_, p_191343_, p_191344_, p_191345_);
    }
    public static class KakuriyoTreeConfigurationBuilder {
        public final BlockStateProvider trunkProvider;
        private final TrunkPlacer trunkPlacer;
        public final BlockStateProvider foliageProvider;
        private final FoliagePlacer foliagePlacer;
        private BlockStateProvider dirtProvider;
        private final FeatureSize minimumSize;
        private List<TreeDecorator> decorators = ImmutableList.of();
        private boolean ignoreVines;
        private boolean forceDirt;
        public final Optional<RootPlacer> rootPlacer;

        public KakuriyoTreeConfigurationBuilder(BlockStateProvider p_191359_, TrunkPlacer p_191360_, BlockStateProvider p_191361_, FoliagePlacer p_191362_, FeatureSize p_191363_) {
            this.trunkProvider = p_191359_;
            this.trunkPlacer = p_191360_;
            this.foliageProvider = p_191361_;
            this.dirtProvider = BlockStateProvider.simple(ItemAndBlockRegister.kakuriyo_dirt.get());
            this.foliagePlacer = p_191362_;
            this.minimumSize = p_191363_;
            this.rootPlacer = Optional.empty();
        }

        public KakuriyoTreeConfigurationBuilder dirt(BlockStateProvider p_161261_) {
            this.dirtProvider = p_161261_;
            return this;
        }

        public KakuriyoTreeConfigurationBuilder decorators(List<TreeDecorator> p_68250_) {
            this.decorators = p_68250_;
            return this;
        }

        public KakuriyoTreeConfigurationBuilder ignoreVines() {
            this.ignoreVines = true;
            return this;
        }

        public KakuriyoTreeConfigurationBuilder forceDirt() {
            this.forceDirt = true;
            return this;
        }

        public KakuriyoTreeConfigration build() {
            return new KakuriyoTreeConfigration(this.trunkProvider, this.trunkPlacer, this.foliageProvider, this.foliagePlacer,this.rootPlacer, this.dirtProvider, this.minimumSize, this.decorators, this.ignoreVines, this.forceDirt);
        }
    }
}
