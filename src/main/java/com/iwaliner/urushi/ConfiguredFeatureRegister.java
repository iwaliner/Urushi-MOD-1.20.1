package com.iwaliner.urushi;

import com.google.common.collect.ImmutableList;
import com.iwaliner.urushi.block.LanternPlantBlock;
import com.iwaliner.urushi.block.WallShiitakeBlock;
import com.iwaliner.urushi.world.feature.JapaneseTimberBambooFeature;
import com.iwaliner.urushi.world.feature.KakuriyoPortalFeature;
import com.iwaliner.urushi.world.feature.KakuriyoTreeConfigration;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.List;
import java.util.OptionalInt;

public class ConfiguredFeatureRegister {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> ConfiguredFeatures = DeferredRegister.create(Registries.CONFIGURED_FEATURE, ModCoreUrushi.ModID);

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ModCoreUrushi.ModID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static final ResourceKey<ConfiguredFeature<?, ?>> KAKURIYO_DISK_MUD_KEY = registerKey("kakuriyo_disk_mud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOT_SPRING_KEY = registerKey("hot_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BAMBOO_KEY = registerKey("japanese_timber_bamboo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> KAKURIYO_PORTAL_OVERWORLD_KEY = registerKey("kakuriyo_portal_overworld");
    public static final ResourceKey<ConfiguredFeature<?, ?>> APRICOT_KEY = registerKey("japanese_apricot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_APRICOT_KEY = registerKey("glowing_japanese_apricot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAKURA_KEY = registerKey("sakura");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_SAKURA_KEY = registerKey("fancy_sakura");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_SAKURA_KEY = registerKey("glowing_sakura");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWING_FANCY_SAKURA_KEY = registerKey("glowing_fancy_sakura");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_KEY = registerKey("red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_KEY = registerKey("orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_KEY = registerKey("yellow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LACQUER_KEY = registerKey("lacquer");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_KEY = registerKey("cypress");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_CYPRESS_KEY = registerKey("mega_cypress");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CEDAR_KEY = registerKey("japanese_cedar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_CEDAR_KEY = registerKey("mega_japanese_cedar");
    public static final ResourceKey<ConfiguredFeature<?, ?>> KAKURIYO_MEGA_CEDAR_KEY = registerKey("mega_japanese_cedar_in_kakuriyo");

    public static final ResourceKey<ConfiguredFeature<?, ?>> YOMI_VINES_KEY = registerKey("yomi_vines");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRONSAND_KEY = registerKey("ore_ironsand");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_JADEITE_KEY = registerKey("ore_jadeite");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON_KEY = registerKey("ore_limonite");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD_KEY = registerKey("ore_yomi_gold");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COPPER_KEY = registerKey("ore_chalcopyrite");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SHIITAKE_KEY = registerKey("shiitake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_SHIITAKE_N_KEY = registerKey("wall_shiitake_n");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_SHIITAKE_E_KEY = registerKey("wall_shiitake_e");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_SHIITAKE_S_KEY = registerKey("wall_shiitake_s");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WALL_SHIITAKE_W_KEY = registerKey("wall_shiitake_w");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EULALIA_KEY = registerKey("eulalia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_EULALIA_KEY = registerKey("tall_eulalia");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LYCORIS_KEY = registerKey("lycoris");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AJISAI_KEY = registerKey("ajisai");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_CLUSTER_KEY = registerKey("quartz_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LANTERN_PLANT_KEY = registerKey("lantern_plant");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SAKUEA_LEAVES_KEY = registerKey("fallen_sakura_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_RED_LEAVES_KEY = registerKey("fallen_red_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_ORANGE_LEAVES_KEY = registerKey("fallen_orange_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_YELLOW_LEAVES_KEY = registerKey("fallen_yellow_leaves");






    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
      /*  register(context, KAKURIYO_DISK_MUD_KEY, Feature.DISK, new DiskConfiguration(RuleBasedBlockStateProvider.simple(Blocks.PACKED_MUD), BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.SAND,ItemAndBlockRegister.kakuriyo_dirt.get(),ItemAndBlockRegister.kakuriyo_grass_block.get())), UniformInt.of(2, 6), 2));
        register(context, HOT_SPRING_KEY, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(ItemAndBlockRegister.HotSpringBlock.get()),BlockStateProvider.simple(Blocks.TUFF)));
        register(context, BAMBOO_KEY,FeatureRegister.Bamboo.get(), new JapaneseTimberBambooFeature.Configuration(BlockStateProvider.simple(ItemAndBlockRegister.japanese_timber_bamboo.get())));
        register(context, KAKURIYO_PORTAL_OVERWORLD_KEY, FeatureRegister.KakuriyoPortal.get(), new KakuriyoPortalFeature.Configuration(BlockStateProvider.simple(ItemAndBlockRegister.ghost_red_kakuriyo_portal_frame.get())));
        register(context, APRICOT_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.japanese_apricot_log.get()), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ItemAndBlockRegister.japanese_apricot_leaves.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build());
        register(context, GLOWING_APRICOT_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.japanese_apricot_log.get()), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ItemAndBlockRegister.glowing_japanese_apricot_leaves.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build());
        register(context, SAKURA_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.sakura_log.get()), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ItemAndBlockRegister.sakura_leaves.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build());
        register(context, FANCY_SAKURA_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.sakura_log.get()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(ItemAndBlockRegister.sakura_leaves.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).build());
        register(context, GLOWING_SAKURA_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.sakura_log.get()), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ItemAndBlockRegister.glowing_sakura_leaves.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build());
        register(context, GLOWING_FANCY_SAKURA_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.sakura_log.get()), new FancyTrunkPlacer(3, 11, 0), BlockStateProvider.simple(ItemAndBlockRegister.glowing_sakura_leaves.get()), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).build());
        register(context, RED_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ItemAndBlockRegister.red_leaves.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build());
        register(context, ORANGE_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ItemAndBlockRegister.orange_leaves.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build());
        register(context, YELLOW_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(Blocks.OAK_LOG), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ItemAndBlockRegister.yellow_leaves.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build());
        register(context, LACQUER_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.lacquer_log.get()), new StraightTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ItemAndBlockRegister.lacquer_leaves.get()), new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3), new TwoLayersFeatureSize(1, 0, 1)).build());
        register(context, CYPRESS_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.cypress_log.get()), new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.simple(ItemAndBlockRegister.cypress_leaves.get()), new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)), new TwoLayersFeatureSize(2, 0, 2)).build());
        register(context, MEGA_CYPRESS_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.cypress_log.get()), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(ItemAndBlockRegister.cypress_leaves.get()), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).build());
        register(context, CEDAR_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_log.get()), new StraightTrunkPlacer(5, 2, 1), BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_leaves.get()), new SpruceFoliagePlacer(UniformInt.of(2, 3), UniformInt.of(0, 2), UniformInt.of(1, 2)), new TwoLayersFeatureSize(2, 0, 2)).build());
        register(context, MEGA_CEDAR_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_log.get()), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_leaves.get()), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).build());
        register(context, KAKURIYO_MEGA_CEDAR_KEY, Feature.TREE, new KakuriyoTreeConfigration.KakuriyoTreeConfigurationBuilder(BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_log.get()), new GiantTrunkPlacer(13, 2, 14), BlockStateProvider.simple(ItemAndBlockRegister.japanese_cedar_leaves.get()), new MegaPineFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), UniformInt.of(13, 17)), new TwoLayersFeatureSize(1, 1, 2)).build());
        register(context, YOMI_VINES_KEY, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(ImmutableList.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(800, 1000), 2).add(UniformInt.of(1, 2), 3).add(UniformInt.of(1, 6), 30).build()), new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ItemAndBlockRegister.yomi_vines_plant.get().defaultBlockState(), 4).add(ItemAndBlockRegister.yomi_vines_plant.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1))), BlockColumnConfiguration.layer(ConstantInt.of(1),new RandomizedIntStateProvider(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(ItemAndBlockRegister.yomi_vines.get().defaultBlockState(), 4).add(ItemAndBlockRegister.yomi_vines.get().defaultBlockState().setValue(CaveVines.BERRIES, Boolean.valueOf(true)), 1)), CaveVinesBlock.AGE, UniformInt.of(23, 25)))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
        register(context, ORE_IRONSAND_KEY, Feature.ORE, new OreConfiguration(ImmutableList.of(OreConfiguration.target(NATURAL_SAND, ItemAndBlockRegister.ironsand_ore.get().defaultBlockState())), 9));
        register(context, ORE_JADEITE_KEY, Feature.ORE, new OreConfiguration(ImmutableList.of(OreConfiguration.target(NATURAL_YOMI_STONE, ItemAndBlockRegister.jadeite_ore.get().defaultBlockState())), 9));
        register(context, ORE_IRON_KEY, Feature.ORE, new OreConfiguration(ImmutableList.of(OreConfiguration.target(NATURAL_YOMI_STONE, ItemAndBlockRegister.yomi_iron_ore.get().defaultBlockState())), 9));
        register(context, ORE_GOLD_KEY, Feature.ORE, new OreConfiguration(ImmutableList.of(OreConfiguration.target(NATURAL_YOMI_STONE, ItemAndBlockRegister.yomi_gold_ore.get().defaultBlockState())), 9));
        register(context, ORE_COPPER_KEY, Feature.ORE, new OreConfiguration(ImmutableList.of(OreConfiguration.target(NATURAL_YOMI_STONE, ItemAndBlockRegister.yomi_copper_ore.get().defaultBlockState())), 9));
        register(context, SHIITAKE_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.shiitake.get()), 50));
        register(context, WALL_SHIITAKE_N_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.wall_shiitake.get().defaultBlockState().setValue(WallShiitakeBlock.FACING,Direction.NORTH)), 30));
        register(context, WALL_SHIITAKE_E_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.wall_shiitake.get().defaultBlockState().setValue(WallShiitakeBlock.FACING,Direction.EAST)), 30));
        register(context, WALL_SHIITAKE_S_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.wall_shiitake.get().defaultBlockState().setValue(WallShiitakeBlock.FACING,Direction.SOUTH)), 30));
        register(context, WALL_SHIITAKE_W_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.wall_shiitake.get().defaultBlockState().setValue(WallShiitakeBlock.FACING,Direction.WEST)), 30));
        register(context, EULALIA_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.eulalia.get()), 60));
        register(context, TALL_EULALIA_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.double_eulalia.get()), 60));
        register(context, LYCORIS_KEY, Feature.SIMPLE_RANDOM_SELECTOR,  new SimpleRandomFeatureConfiguration(HolderSet.direct( PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ItemAndBlockRegister.lycoris.get())))))));
        register(context, AJISAI_KEY, Feature.SIMPLE_RANDOM_SELECTOR,  new SimpleRandomFeatureConfiguration(HolderSet.direct( PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ItemAndBlockRegister.ajisai.get())))))));
        register(context, QUARTZ_CLUSTER_KEY, Feature.SIMPLE_RANDOM_SELECTOR,  new SimpleRandomFeatureConfiguration(HolderSet.direct( PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ItemAndBlockRegister.quartz_cluster.get())))))));
        register(context, LANTERN_PLANT_KEY, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct( PlacementUtils.inlinePlaced(Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ItemAndBlockRegister.lantern_plant.get().defaultBlockState().setValue(LanternPlantBlock.AGE,Integer.valueOf(1)))))))));
        register(context, FALLEN_SAKUEA_LEAVES_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.fallen_sakura_leaves.get()), 20));
        register(context, FALLEN_RED_LEAVES_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.fallen_red_leaves.get()), 30));
        register(context, FALLEN_ORANGE_LEAVES_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.fallen_orange_leaves.get()), 30));
        register(context, FALLEN_YELLOW_LEAVES_KEY, Feature.RANDOM_PATCH, grassPatch(BlockStateProvider.simple(ItemAndBlockRegister.fallen_yellow_leaves.get()), 20));
*/
    }

    public static final RuleTest NATURAL_SAND = new TagMatchTest(BlockTags.SAND);
    public static final RuleTest NATURAL_YOMI_STONE = new TagMatchTest(TagUrushi.YOMI_STONE);
 private static RandomPatchConfiguration grassPatch(BlockStateProvider p_195203_, int p_195204_) {
        return FeatureUtils.simpleRandomPatchConfiguration(p_195204_, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(p_195203_)));
    }

    static void register(IEventBus eventBus) {
        ConfiguredFeatures.register(eventBus);
    }
}
