package com.iwaliner.urushi;

import com.google.common.collect.ImmutableList;

import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class PlacedFeatureRegister {
  public static final DeferredRegister<PlacedFeature> PlacedFeatures = DeferredRegister.create(Registries.PLACED_FEATURE, ModCoreUrushi.ModID);

  public static final ResourceKey<PlacedFeature> KAKURIYO_PORTAL_OVERWORLD_KEY = registerKey("overworld_kakuriyo_portal");
  public static final ResourceKey<PlacedFeature> FOREST_BAMBOO_KEY = registerKey("forest_japanese_timber_bamboo");
  public static final ResourceKey<PlacedFeature> FOREST_APRICOT_KEY = registerKey("forest_japanese_apricot");
  public static final ResourceKey<PlacedFeature> FOREST_SAKURA_KEY = registerKey("forest_sakura");
  public static final ResourceKey<PlacedFeature> SAKURA_FOREST_SAKURA_KEY = registerKey("sakura_forest_sakura");
  public static final ResourceKey<PlacedFeature> HILL_CYPRESS_KEY = registerKey("hill_cypress");
  public static final ResourceKey<PlacedFeature> CEDAR_FOREST_CYPRESS_KEY = registerKey("cedar_forest_cypress");
  public static final ResourceKey<PlacedFeature> HILL_CEDAR_KEY = registerKey("hill_japanese_cedar");
  public static final ResourceKey<PlacedFeature> FOREST_LACQUER_KEY = registerKey("forest_lacquer");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_RED_KEY = registerKey("autumn_forest_red");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_ORANGE_KEY = registerKey("autumn_forest_orange");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_YELLOW_KEY = registerKey("autumn_forest_yellow");
  public static final ResourceKey<PlacedFeature> KAKURIYO_CEDAR_FOREST_CEDAR_KEY = registerKey("kakuriyo_cedar_forest_cedar");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_SHIITAKE_KEY = registerKey("autumn_forest_shiitake");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_WALL_SHIITAKE_N_KEY = registerKey("autumn_forest_wall_shiitake_n");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_WALL_SHIITAKE_E_KEY = registerKey("autumn_forest_wall_shiitake_e");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_WALL_SHIITAKE_S_KEY = registerKey("autumn_forest_wall_shiitake_s");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_WALL_SHIITAKE_W_KEY = registerKey("autumn_forest_wall_shiitake_w");
  public static final ResourceKey<PlacedFeature> EULALIA_PLAINS_EULALIA_KEY = registerKey("eulalia_plains_eulalia");
  public static final ResourceKey<PlacedFeature> EULALIA_PLAINS_TALL_EULALIA_KEY = registerKey("eulalia_plains_tall_eulalia");
  public static final ResourceKey<PlacedFeature> KAKURIYO_LYCORIS_KEY = registerKey("kakuriyo_lycoris");
  public static final ResourceKey<PlacedFeature> SWAMP_AJISAI_KEY = registerKey("swamp_ajisai");
  public static final ResourceKey<PlacedFeature> SAKURA_FOREST_FALLEN_SAKURA_LEAVES_KEY = registerKey("sakura_forest_fallen_sakura_leaves");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_FALLEN_RED_LEAVES_KEY = registerKey("autumn_forest_fallen_red_leaves");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_FALLEN_ORANGE_LEAVES_KEY = registerKey("autumn_forest_fallen_orange_leaves");
  public static final ResourceKey<PlacedFeature> AUTUMN_FOREST_FALLEN_YELLOW_LEAVES_KEY = registerKey("autumn_forest_fallen_yellow_leaves");
  public static final ResourceKey<PlacedFeature> ORE_IRONSAND_OVERWORLD_KEY = registerKey("overworld_ore_ironsand");
  public static final ResourceKey<PlacedFeature> ORE_JADEITE_KAKURIYO_KEY = registerKey("kakuriyo_ore_jadeite");
  public static final ResourceKey<PlacedFeature> ORE_YOMI_IRON_KAKURIYO_KEY = registerKey("kakuriyo_ore_limonite");
  public static final ResourceKey<PlacedFeature> ORE_YOMI_GOLD_KAKURIYO_KEY = registerKey("kakuriyo_ore_yomi_gold");
  public static final ResourceKey<PlacedFeature> ORE_YOMI_COPPER_KAKURIYO_KEY = registerKey("kakuriyo_ore_chalcopyrite");
  public static final ResourceKey<PlacedFeature> KAKURIYO_QUARTZ_CLUSTER_KEY = registerKey("kakuriyo_quartz_cluster");
  public static final ResourceKey<PlacedFeature> KAKURIYO_LANTERN_PLANT_KEY = registerKey("kakuriyo_lantern_plant");
  public static final ResourceKey<PlacedFeature> KAKURIYO_YOMI_VINES_KEY = registerKey("kakuriyo_yomi_vines");
  public static final ResourceKey<PlacedFeature> KAKURIYO_HOT_SPRING_KEY = registerKey("kakuriyo_hot_spring");
  public static final ResourceKey<PlacedFeature> KAKURIYO_DISK_MUD_KEY = registerKey("kakuriyo_disk_mud");


  public static void bootstrap(BootstapContext<PlacedFeature> context) {
    HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
/*

    register(context, KAKURIYO_PORTAL_OVERWORLD_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.KAKURIYO_PORTAL_OVERWORLD_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1),ItemAndBlockRegister.red_kakuriyo_portal_frame.get()));
    register(context, FOREST_BAMBOO_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.BAMBOO_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1),ItemAndBlockRegister.japanese_timber_bamboo.get()));
    register(context, FOREST_APRICOT_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.APRICOT_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1),ItemAndBlockRegister.japanese_apricot_sapling.get()));
    register(context, FOREST_SAKURA_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.SAKURA_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1),ItemAndBlockRegister.sakura_sapling.get()));
    register(context, SAKURA_FOREST_SAKURA_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.FANCY_SAKURA_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(5, 0.5f, 1),ItemAndBlockRegister.big_sakura_sapling.get()));
    register(context, HILL_CYPRESS_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.CYPRESS_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1),ItemAndBlockRegister.cypress_sapling.get()));
    register(context, CEDAR_FOREST_CYPRESS_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.CYPRESS_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.2f, 1),ItemAndBlockRegister.cypress_sapling.get()));
    register(context, HILL_CEDAR_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.CEDAR_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1),ItemAndBlockRegister.japanese_cedar_sapling.get()));
    register(context, FOREST_LACQUER_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.LACQUER_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1f, 1),ItemAndBlockRegister.lacquer_sapling.get()));
    register(context, AUTUMN_FOREST_RED_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.RED_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.5f, 1),ItemAndBlockRegister.red_sapling.get()));
    register(context, AUTUMN_FOREST_ORANGE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.ORANGE_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.5f, 1),ItemAndBlockRegister.orange_sapling.get()));
    register(context, AUTUMN_FOREST_YELLOW_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.YELLOW_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.5f, 1),ItemAndBlockRegister.yellow_sapling.get()));
    register(context, KAKURIYO_CEDAR_FOREST_CEDAR_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.KAKURIYO_MEGA_CEDAR_KEY),
            VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.2f, 1),ItemAndBlockRegister.japanese_cedar_sapling.get()));
    register(context, AUTUMN_FOREST_SHIITAKE_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.SHIITAKE_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(1));
    register(context, AUTUMN_FOREST_WALL_SHIITAKE_N_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.WALL_SHIITAKE_N_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(1));
    register(context, AUTUMN_FOREST_WALL_SHIITAKE_E_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.WALL_SHIITAKE_E_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(1));
    register(context, AUTUMN_FOREST_WALL_SHIITAKE_S_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.WALL_SHIITAKE_S_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(1));
    register(context, AUTUMN_FOREST_WALL_SHIITAKE_W_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.WALL_SHIITAKE_W_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(1));
    register(context, EULALIA_PLAINS_EULALIA_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.EULALIA_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(10));
    register(context, EULALIA_PLAINS_TALL_EULALIA_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.TALL_EULALIA_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(10));
    register(context, KAKURIYO_LYCORIS_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.LYCORIS_KEY),
            fullRangeSquaredWithCount(1));
    register(context, SWAMP_AJISAI_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.AJISAI_KEY),
            fullRangeSquaredWithCount(2));
    register(context, SAKURA_FOREST_FALLEN_SAKURA_LEAVES_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.FALLEN_SAKUEA_LEAVES_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(10));
    register(context, AUTUMN_FOREST_FALLEN_RED_LEAVES_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.FALLEN_RED_LEAVES_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(10));
    register(context, AUTUMN_FOREST_FALLEN_ORANGE_LEAVES_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.FALLEN_ORANGE_LEAVES_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(10));
    register(context, AUTUMN_FOREST_FALLEN_YELLOW_LEAVES_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.FALLEN_YELLOW_LEAVES_KEY),
            VegetationPlacements.worldSurfaceSquaredWithCount(10));
    register(context, ORE_IRONSAND_OVERWORLD_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.ORE_IRONSAND_KEY),
            commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(200))));
    register(context, ORE_JADEITE_KAKURIYO_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.ORE_JADEITE_KEY),
            commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(120))));
    register(context, ORE_YOMI_IRON_KAKURIYO_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.ORE_IRON_KEY),
            commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(120))));
    register(context, ORE_YOMI_GOLD_KAKURIYO_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.ORE_GOLD_KEY),
            commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(120))));
    register(context, ORE_YOMI_COPPER_KAKURIYO_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.ORE_COPPER_KEY),
            commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(120))));
    register(context, KAKURIYO_QUARTZ_CLUSTER_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.QUARTZ_CLUSTER_KEY),
            fullRangeSquaredWithCount(10));
    register(context, KAKURIYO_LANTERN_PLANT_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.LANTERN_PLANT_KEY),
            fullRangeSquaredWithCount(3));
    register(context, KAKURIYO_YOMI_VINES_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.YOMI_VINES_KEY),
            fullRangeSquaredWithCount(30));
    register(context, KAKURIYO_HOT_SPRING_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.HOT_SPRING_KEY),
            fullRangePlacement(PlacementUtils.countExtra(0, 0.1f, 1)));
    register(context, KAKURIYO_DISK_MUD_KEY, configuredFeatures.getOrThrow(ConfiguredFeatureRegister.KAKURIYO_DISK_MUD_KEY),
            fullRangePlacement(PlacementUtils.countExtra(0, 0.1f, 2)));
*/

  }

     private static ImmutableList.Builder<PlacementModifier> fullRangePlacementBase(PlacementModifier p_195485_) {
        return ImmutableList.<PlacementModifier>builder().add(p_195485_).add(InSquarePlacement.spread()).add(SurfaceWaterDepthFilter.forMaxDepth(0)).add(PlacementUtils.HEIGHTMAP).add(BiomeFilter.biome());
    }
    public static List<PlacementModifier> fullRangePlacement(PlacementModifier p_195480_) {
        return fullRangePlacementBase(p_195480_).build();
    }
    public static List<PlacementModifier> fullRangeSquaredWithCount(int p_195475_) {
        return List.of(CountPlacement.of(p_195475_), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
    }
    public static List<PlacementModifier> seagrassPlacement(int p_195234_) {
        return List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(p_195234_), BiomeFilter.biome());
    }
    public static void register(IEventBus eventBus) {
        PlacedFeatures.register(eventBus);
    }
  private static ResourceKey<PlacedFeature> registerKey(String name) {
    return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ModCoreUrushi.ModID, name));
  }

  private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                               List<PlacementModifier> modifiers) {
    context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
  }

  private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
    return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
  }

  private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
    return orePlacement(CountPlacement.of(p_195344_), p_195345_);
  }
}
