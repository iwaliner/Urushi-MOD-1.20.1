package com.iwaliner.urushi.entiity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.SoundRegister;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.KakuriyoVillagerProfessionType;
import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.StructureTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Map;
import java.util.UUID;
public class KakuriyoVillagerEntity extends AbstractVillager {
    private static final EntityDataAccessor<Integer> PROFESSION = SynchedEntityData.defineId(KakuriyoVillagerEntity.class, EntityDataSerializers.INT);
  public static final Map<KakuriyoVillagerProfessionType, Int2ObjectMap<VillagerTrades.ItemListing[]>> TRADES = Util.make(Maps.newHashMap(), (p_35633_) -> {
        p_35633_.put(KakuriyoVillagerProfessionType.Miner, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
                        new Sell(Blocks.TORCH, 24, 2),
                        new Sell(ItemAndBlockRegister.rice_ball.get(), 20, 2),
                        new Buy(Blocks.GRAVEL, 1, 20,  1),
                        new Buy(Blocks.SAND, 1, 20,  1),
                        new Buy(Blocks.RED_SAND, 1, 20,  1),
                        new Buy(Blocks.COBBLESTONE, 1, 24,  1),
                        new Buy(Blocks.STONE, 1, 16,  1),
                        new Buy(Blocks.GRANITE, 1, 16,  1),
                        new Buy(Blocks.DIORITE, 1, 16,  1),
                        new Buy(Blocks.ANDESITE, 1, 16,  1),
                        new Buy(Blocks.COBBLED_DEEPSLATE, 1, 16,  1),
                        new Buy(Blocks.BASALT, 1, 16,  1),
                        new Buy(Blocks.BLACKSTONE, 1, 16,  1),
                        new Buy(ItemAndBlockRegister.cobbled_yomi_stone.get(), 1, 16,  1),/*

                        new BuySkull(Blocks.PLAYER_HEAD, Blocks.GRASS_BLOCK.getName().getString(),"cake","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZiYjlmYjk3YmE4N2NiNzI3Y2QwZmY0NzdmNzY5MzcwYmVhMTljY2JmYWZiNTgxNjI5Y2Q1NjM5ZjJmZWMyYiJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.SHROOMLIGHT.getName().getString(),"PandaClod","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDgwNjhhM2U5ZWQwZTY4ODc2OGNmZTAxN2VhZGZmZmM5MjEwNjhmYjRhOGExMGJhYmFkY2U3NWNmNTcyYWFhMyJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.SEA_LANTERN.getName().getString(),"Kiaria","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODI0YzZmZjE3MTRlYjJjM2I4NDRkNDZkMmU1ZWEyZjI2ZDI3M2EzM2VhYWE3NDRhYmY2NDViMDYwYjQ3ZDcifX19"),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.COAL_BLOCK.getName().getString(),"TomReal", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjAzYTUyNTE3YTgxNDMwMzViZTVjOGI4MWZkMjAxOTI4ZDY0MDc2Zjk2YjVkMjc2MDRjY2NjMzcwODVmMTdlNyJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.COPPER_BLOCK.getName().getString(),"RyanUwU", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ1NzI0YTc5ZjkwZmQxZTU0NzY1ZDc1OTY5MjgxNDZkY2I5ZTUwZmRiODg1OTYyMDFjNGEyMzVjNGE2ZjRlZSJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.IRON_BLOCK.getName().getString(), "TomReal","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWExMDU3YWViNTcxYjIzYzkwNzI4NGY5MDdmNTFhMWYxODUzMGQ3MzFhOGFkYWMwMmM3OTFhYTIwNWI1NmMxZSJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.LAPIS_BLOCK.getName().getString(), "TomReal","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjljMTQ5Mzc5YmZmMzcxNGYwYTE0NmE0OTc1YTkyMzE1NmUxZDU1NjMyOGVkYjg5MjEzNTE4MmJhM2FhM2M4MyJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.REDSTONE_BLOCK.getName().getString(), "TomReal","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY3NGY3OWY1MGM2NGMyMzk2MmQyNzkyMmQ5MGNiMDk1M2E1NzRiMjgzMmEyYjcwYmI1YjFjM2M4M2Q5MGZiYyJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.GOLD_BLOCK.getName().getString(), "TomReal","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmVkM2UxMzcyZDYyMDc4Zjk0MDRkODdmMDUzODEzMTFkZDg3MDcyODczMTIwZDQ3MTVlNjMzNWQ1YmY3ZWM0ZiJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.EMERALD_BLOCK.getName().getString(),"TomReal","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTk2MGQ2ZmZhZjQ0ZThhZmNiZGY4YjI5YTc3ZDg0Y2UyMmM3MWQwMGM2NGJmZDk5YWYzNDBhNjk1MzViZmQ3In19fQ=="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.DIAMOND_BLOCK.getName().getString(),"TomReal", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJmZDJkZTUzZWIxYmZlOTJmNWNjMTc4MGEyNzI3MzBlYmU3NTk0NzNiYjI1YTUzNjQ3ZDM3MTJlZTVmNjA5NSJ9fX0="),
                new BuySkull(Blocks.PLAYER_HEAD, Blocks.NETHERITE_BLOCK.getName().getString(), "TomReal","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGY0NjJjMDU2ZjFjN2QzMWE5MzFjYTg2ZTlkNDgxNGU4NzcyYjE4YzJlOWM5NTA2ZGJiOWUyNzJiMmNiMTI1ZCJ9fX0=")
      */  }
        )));
      p_35633_.put(KakuriyoVillagerProfessionType.RiceDealer, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
              new Sell(ItemAndBlockRegister.rice_crop.get(), 24,2),
              new Buy(ItemAndBlockRegister.dirt_furnace.get(), 1,1,1),
              new Buy(ItemAndBlockRegister.rice_cauldron.get(), 3,1,1),
              new Buy(ItemAndBlockRegister.rice_malt.get(), 1,6,1),
              new Buy(ItemAndBlockRegister.roasted_rice_cake.get(), 1,6,1)
      })));
      p_35633_.put(KakuriyoVillagerProfessionType.Fisherman, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
              new Sell(Items.STRING, 20,2),
              new Sell(ItemAndBlockRegister.silkworm.get(), 22,2),
              new Buy(Items.COD, 1,8,1),
              new Buy(Items.SALMON, 1,8,1),
              new Buy(ItemAndBlockRegister.tsuna.get(), 1,8,1),
              new Buy(ItemAndBlockRegister.shrimp.get(), 1,8,1),
              new Buy(ItemAndBlockRegister.sweetfish.get(), 1,8,1),
              new Buy(Items.SEAGRASS, 1,16,1),
              new Buy(Items.LILY_PAD, 1,12,1),
              new Buy(Items.EXPERIENCE_BOTTLE, 1,2,1)
      })));
      p_35633_.put(KakuriyoVillagerProfessionType.Lumberjack, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
              new Sell(Items.STICK, 32,2),
              new Sell(ItemAndBlockRegister.japanese_timber_bamboo.get(), 20,2),
              new Sell(Items.IRON_AXE, 1,2),
              new Buy(ItemAndBlockRegister.raw_urushi_ball.get(), 1,4,1),
              new Buy(Blocks.OAK_LOG, 1,4,1),
              new Buy(Blocks.SPRUCE_LOG, 1,4,1),
              new Buy(Blocks.BIRCH_LOG, 1,4,1),
              new Buy(Blocks.JUNGLE_LOG, 1,4,1),
              new Buy(Blocks.ACACIA_LOG, 1,4,1),
              new Buy(Blocks.DARK_OAK_LOG, 1,4,1),
              new Buy(Blocks.MANGROVE_LOG, 1,4,1),
              new Buy(Blocks.CHERRY_LOG, 1,4,1),
              new Buy(Blocks.BAMBOO_BLOCK, 1,4,1),
              new Buy(Blocks.CRIMSON_STEM, 1,4,1),
              new Buy(Blocks.WARPED_STEM, 1,4,1),
              new Buy(ItemAndBlockRegister.japanese_apricot_log.get(), 1,4,1),
              new Buy(ItemAndBlockRegister.sakura_log.get(), 1,4,1),
              new Buy(ItemAndBlockRegister.cypress_log.get(), 1,4,1),
              new Buy(ItemAndBlockRegister.japanese_cedar_log.get(), 1,4,1),
              new Buy(ItemAndBlockRegister.petrified_log.get(), 1,4,1)/*,
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"LordRazen","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWRiNTMyYjVjY2VkNDZiNGI1MzVlY2UxNmVjZWQ3YmJjNWNhYzU1NTk0ZDYxZThiOGY4ZWFjNDI5OWM5ZmMifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTY3ZDgxM2FlN2ZmZTViZTk1MWE0ZjQxZjJhYTYxOWE1ZTM4OTRlODVlYTVkNDk4NmY4NDk0OWM2M2Q3NjcyZSJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBjMWI1ODRmMTM5ODdiNDY2MTM5Mjg1YjJmM2YyOGRmNjc4NzEyM2QwYjMyMjgzZDg3OTRlMzM3NGUyMyJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJlOTgzZWM0NzgwMjRlYzZmZDA0NmZjZGZhNDg0MjY3NjkzOTU1MWI0NzM1MDQ0N2M3N2MxM2FmMThlNmYifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE5M2RjMGQ0YzVlODBmZjlhOGEwNWQyZmNmZTI2OTUzOWNiMzkyNzE5MGJhYzE5ZGEyZmNlNjFkNzEifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJiMjczN2VjYmY5MTBlZmUzYjI2N2RiN2Q0YjMyN2YzNjBhYmM3MzJjNzdiZDBlNGVmZjFkNTEwY2RlZiJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjE4M2JhYjUwYTMyMjQwMjQ4ODZmMjUyNTFkMjRiNmRiOTNkNzNjMjQzMjU1OWZmNDllNDU5YjRjZDZhIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWNhM2YzMjRiZWVlZmI2YTBlMmM1YjNjNDZhYmM5MWNhOTFjMTRlYmE0MTlmYTQ3NjhhYzMwMjNkYmI0YjIifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFmMzQ2MmE0NzM1NDlmMTQ2OWY4OTdmODRhOGQ0MTE5YmM3MWQ0YTVkODUyZTg1YzI2YjU4OGE1YzBjNzJmIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDYxNzhhZDUxZmQ1MmIxOWQwYTM4ODg3MTBiZDkyMDY4ZTkzMzI1MmFhYzZiMTNjNzZlN2U2ZWE1ZDMyMjYifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2E3OWRiOTkyMzg2N2U2OWMxZGJmMTcxNTFlNmY0YWQ5MmNlNjgxYmNlZGQzOTc3ZWViYmM0NGMyMDZmNDkifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ2MWIzOGM4ZTQ1NzgyYWRhNTlkMTYxMzJhNDIyMmMxOTM3NzhlN2Q3MGM0NTQyYzk1MzYzNzZmMzdiZTQyIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzE5ZjUwYjQzMmQ4NjhhZTM1OGUxNmY2MmVjMjZmMzU0MzdhZWI5NDkyYmNlMTM1NmM5YWE2YmIxOWEzODYifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDljNDVhMjRhYWFiZjQ5ZTIxN2MxNTQ4MzIwNDg0OGE3MzU4MmFiYTdmYWUxMGVlMmM1N2JkYjc2NDgyZiJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzViOGIzZDhjNzdkZmI4ZmJkMjQ5NWM4NDJlYWM5NGZmZmE2ZjU5M2JmMTVhMjU3NGQ4NTRkZmYzOTI4In19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDExZGUxY2FkYjJhZGU2MTE0OWU1ZGVkMWJkODg1ZWRmMGRmNjI1OTI1NWIzM2I1ODdhOTZmOTgzYjJhMSJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTBhNzk4OWI1ZDZlNjIxYTEyMWVlZGFlNmY0NzZkMzUxOTNjOTdjMWE3Y2I4ZWNkNDM2MjJhNDg1ZGMyZTkxMiJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM2MDlmMWZhZjgxZWQ0OWM1ODk0YWMxNGM5NGJhNTI5ODlmZGE0ZTFkMmE1MmZkOTQ1YTU1ZWQ3MTllZDQifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTVjZWQ5OTMxYWNlMjNhZmMzNTEzNzEzNzliZjA1YzYzNWFkMTg2OTQzYmMxMzY0NzRlNGU1MTU2YzRjMzcifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U0MWM2MDU3MmM1MzNlOTNjYTQyMTIyODkyOWU1NGQ2Yzg1NjUyOTQ1OTI0OWMyNWMzMmJhMzNhMWIxNTE3In19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTU2MmU4YzFkNjZiMjFlNDU5YmU5YTI0ZTVjMDI3YTM0ZDI2OWJkY2U0ZmJlZTJmNzY3OGQyZDNlZTQ3MTgifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA3ZmJjMzM5ZmYyNDFhYzNkNjYxOWJjYjY4MjUzZGZjM2M5ODc4MmJhZjNmMWY0ZWZkYjk1NGY5YzI2In19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M5YTEzODYzOGZlZGI1MzRkNzk5Mjg4NzZiYWJhMjYxYzdhNjRiYTc5YzQyNGRjYmFmY2M5YmFjNzAxMGI4In19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjY5YWQxYTg4ZWQyYjA3NGUxMzAzYTEyOWY5NGU0YjcxMGNmM2U1YjRkOTk1MTYzNTY3ZjY4NzE5YzNkOTc5MiJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWE2Nzg3YmEzMjU2NGU3YzJmM2EwY2U2NDQ5OGVjYmIyM2I4OTg0NWU1YTY2YjVjZWM3NzM2ZjcyOWVkMzcifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzUyZmIzODhlMzMyMTJhMjQ3OGI1ZTE1YTk2ZjI3YWNhNmM2MmFjNzE5ZTFlNWY4N2ExY2YwZGU3YjE1ZTkxOCJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTA1ODJiOWI1ZDk3OTc0YjExNDYxZDYzZWNlZDg1ZjQzOGEzZWVmNWRjMzI3OWY5YzQ3ZTFlMzhlYTU0YWU4ZCJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNkOWVlZWU4ODM0Njg4ODFkODM4NDhhNDZiZjMwMTI0ODVjMjNmNzU3NTNiOGZiZTg0ODczNDE0MTk4NDcifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ0ZWFlMTM5MzM4NjBhNmRmNWU4ZTk1NTY5M2I5NWE4YzNiMTVjMzZiOGI1ODc1MzJhYzA5OTZiYzM3ZTUifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDJlNzhmYjIyNDI0MjMyZGMyN2I4MWZiY2I0N2ZkMjRjMWFjZjc2MDk4NzUzZjJkOWMyODU5ODI4N2RiNSJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ1N2UzYmM4OGE2NTczMGUzMWExNGUzZjQxZTAzOGE1ZWNmMDg5MWE2YzI0MzY0M2I4ZTU0NzZhZTIifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzM0YjM2ZGU3ZDY3OWI4YmJjNzI1NDk5YWRhZWYyNGRjNTE4ZjVhZTIzZTcxNjk4MWUxZGNjNmIyNzIwYWIifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmRiNmViMjVkMWZhYWJlMzBjZjQ0NGRjNjMzYjU4MzI0NzVlMzgwOTZiN2UyNDAyYTNlYzQ3NmRkN2I5In19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTkxOTQ5NzNhM2YxN2JkYTk5NzhlZDYyNzMzODM5OTcyMjI3NzRiNDU0Mzg2YzgzMTljMDRmMWY0Zjc0YzJiNSJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY3Y2FmNzU5MWIzOGUxMjVhODAxN2Q1OGNmYzY0MzNiZmFmODRjZDQ5OWQ3OTRmNDFkMTBiZmYyZTViODQwIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.letter").getString(),"","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMGViZTdlNTIxNTE2OWE2OTlhY2M2Y2VmYTdiNzNmZGIxMDhkYjg3YmI2ZGFlMjg0OWZiZTI0NzE0YjI3In19fQ==")
*/


      })));
p_35633_.put(KakuriyoVillagerProfessionType.Cook, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
        new Sell(ItemAndBlockRegister.matured_japanese_apricot_fruit.get(), 16,2),
        new Sell(ItemAndBlockRegister.green_onion_crop.get(), 20,2),
        new Buy(ItemAndBlockRegister.sakura_mochi.get(), 1, 8,  1),
        new Buy(ItemAndBlockRegister.color_dango.get(), 1, 8,  1),
        new Buy(ItemAndBlockRegister.baked_mochocho.get(), 1, 8,  1),
        new Buy(ItemAndBlockRegister.gravel_sushi.get(), 1, 16,  1),
        new Buy(ItemAndBlockRegister.soy_source_ramen.get(), 2, 1,  1)/*,
        new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.burger").getString(),"TheUnderground11", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E5Yzg3NTM3ODBlYmMzOWMzNTFkYThlZmQ5MWJjZTkwYmQ4Y2NhN2I1MTFmOTNlNzhkZjc1ZjY2MTVjNzlhNiJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.CHICKEN).getHoverName().getString(),"PandaClod", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDEyYzE5YjliODRiNGY1OTQ1NjA1ODA4NmM3NTIzYThkYWQ0YWM5MDcxOWZhMjQyYjIwN2RiMzJiYmFlOGY1ZCJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.COOKED_CHICKEN).getHoverName().getString(), "PandaClod","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDNlMjBhMjZjYmI1NzQwYTE1OGRhOTkxZWY5NGRjZDMyZDQ0N2U5YWMwM2FhMGU4ZjgyOWE0OTgzMDYxOWExMCJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.pancakes").getString(), "PandaClod","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTU4YzA5ZGEzMmZkM2Y0MTg3ZGY3MzU3ZGFkODdkNjkzMjdlNzIzZGU0ODcxNmM4MDRjYTVkNzM3MTY4YmI2YSJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.birthday_cake").getString(),"LyeraHeads", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFmNTgwZGRhNTdhNWE5YTE5M2NjMmVjNWVhYjdlMjJiZThkOTQ2NTViOWRjMWY2NDFhZGU2Y2QzYWM4ZDZkIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.kagami_mochi").getString(), "keikeikaikai0","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjkzNTUzYmU2ODMwMjJmODU5NTljNmMyMTdiNGE1NmJmMzNkZWZiZjUyYTZhODRjNjlkMmNiZGI1MTY0M2IifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.fries").getString(), "","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTA1ODI2YjM3YTdmN2IyM2MzYWM5ZGRhOTdmNDc0NGQxMjAzNDBkNmZhNDRmOTM3ZTJiNzlmOGQzOTVmIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.takoyaki").getString(),"", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmRiNzVjZGUzOWVlMjBlOGQ4YjQ3ZjNiYTMxNTdjYWVmNzdkNTYwOWI2ZGQxNGUxMDQzNGFkZjJiNGE1MjhiIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.sandwich").getString(),"LordRazen", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTQ5NjU4OWZiNWMxZjY5Mzg3YjdmYjE3ZDkyMzEyMDU4ZmY2ZThlYmViM2ViODllNGY3M2U3ODE5NjExM2IifX19"),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.jam_jar").getString(),"Kiaria, LordRazen", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzBiOGI1ODg5ZWUxYzYzODhkYzZjMmM1ZGJkNzBiNjk4NGFlZmU1NDMxOWEwOTVlNjRkYjc2MzgwOTdiODIxIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.BREAD).getHoverName().getString(),"Fipoki", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE5OTdkYTY0MDQzYjI4NDgyMjExNTY0M2E2NTRmZGM0ZThhNzIyNjY2NGI0OGE0ZTFkYmI1NTdiNWMwZmUxNCJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.cheese").getString(),"Fipoki" ,"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzZjMDFiZmZlY2ZkYWI2ZDNjMGYxYTdjNmRmNmFhMTkzNmYyYWE3YTUxYjU0YTRkMzIzZTFjYWNiYzUzOSJ9fX0="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.popcorn").getString(),"Kiaria, LordRazen, Merlegaming", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ5N2IxNDdjZmFlNTIyMDU1OTdmNzJlM2M0ZWY1MjUxMmU5Njc3MDIwZTRiNGZhNzUxMmMzYzZhY2RkOGMxIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.flour").getString(), "Birgan","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjA2YmUyZGYyMTIyMzQ0YmRhNDc5ZmVlY2UzNjVlZTBlOWQ1ZGEyNzZhZmEwZThjZThkODQ4ZjM3M2RkMTMxIn19fQ=="),
              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.pepper_cellar").getString(),"mothbee", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzdkOWUxN2Q3ODZiNTViZjk1MjA5NjM4ODQ4ODdmMGM1MmZlMmYzZmZhN2QwMGUxNGU1ODYzNGU5MWQ2OTExZCJ9fX0="),
        new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.salt_cellar").getString(),"mothbee", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTk0Y2M3MjVlM2VjNDJkOTc0NjQwNDMxODBiNmEyYTZiNmYwZGU4OGNkZjY0NmM2NDk0NTIwODM2YTQ4YThiNyJ9fX0="),
       // new BuySkull(Blocks.PLAYER_HEAD, 1, 1,  1,Component.translatable("skull.urushi.").getString(), ""),
        new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.can").getString(),"Queen_Of_Bunnies","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY2ZDg5MGUwZGE3NTkxNWYzNTNjNWU3OTQ0NzU0OTBmNDNhMjZmZDdkMDIxYjk2N2I3MzlhNGViNjIxYmMyNyJ9fX0=")
      */})));
//      p_35633_.put(KakuriyoVillagerProfessionType.KnickKnackDealer, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
//              new Buy(ItemAndBlockRegister.doubled_red_urushi_wooden_cabinetry.get(), 2, 1,  1),
//              new Buy(ItemAndBlockRegister.pendant_light.get(), 1, 4,  1),
//              new Buy(ItemAndBlockRegister.goldfish_bowl.get(), 1, 4,  1)/*,
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.clock").getString(), "LordRazen","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ3N2RhZmM4YzllYTA3OTk2MzMzODE3OTM4NjZkMTQ2YzlhMzlmYWQ0YzY2ODRlNzExN2Q5N2U5YjZjMyJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.mailbox").getString(),"LordRazen", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjRiZDlkZDEyOGM5NGMxMGM5NDVlYWRhYTM0MmZjNmQ5NzY1ZjM3YjNkZjJlMzhmN2IwNTZkYzdjOTI3ZWQifX19"),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.teddy_bear").getString(),"", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDVmNWJmODUxMGZmY2QzYTVlOWQ3ODI1YjY0MzMzYTEyMWQ1NjFmZTJjZGQ3NjdjN2UxOGI4Y2M1MjFiNiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.bag").getString(),"Elsweyr", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNiM2FjZGMxMWNhNzQ3YmY3MTBlNTlmNGM4ZTliM2Q5NDlmZGQzNjRjNjg2OTgzMWNhODc4ZjA3NjNkMTc4NyJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.water_filled_cauldron").getString(),"LordRazen", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ4YTE5Y2RmNDJkNzQ4YjQxYjcyZmI0Mzc2YWUzZjYzYzExNjVkMmRjZTA2NTE3MzNkZjI2MzQ0NmM3N2JhNiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.crate").getString(),"nikolanoxy", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk5MmU0YThmNDNjNzdhYzJlMmQ3OTJkOTYwNGY2MWYzNDM4N2M4NzdhYmEzNzEyZTQwNDdiNzQwYzU3OCJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.bird_house").getString(),"Marjory_", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTdmODJhY2ViOThmZTA2OWU4YzE2NmNlZDAwMjQyYTc2NjYwYmJlMDcwOTFjOTJjZGRlNTRjNmVkMTBkY2ZmOSJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.BUCKET).getHoverName().getString(),"Dzekin", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTE3ZDg2ZTNmODFjYzE4MzdjNzBmMTBiZTQ4ODk4MzRjMDRmNTdhN2U5OGUwZGQwYjRiMjIzYjUwYzdhOGY5MCJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.WATER_BUCKET).getHoverName().getString(),"Dzekin", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ0MjE3ODJmYTllMDQxY2E4Y2NlNmFhYzNhMGIzMzdlY2MxYjk3MWIxNjhlY2JlZWY2OGI4ODEzZWUzOTZkYiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.LAVA_BUCKET).getHoverName().getString(),"Dzekin",  "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODkxMTgzZWZiOTQxYzU3YzZhMzdmNjFjOTRlMjViNDRiNjdhODA0NDUyZTAyYTBkMjk5NmRlZGE2ZDIwZDdjNiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Blocks.CRAFTING_TABLE.getName().getString(),"PandaClod",  "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmNkYzBmZWI3MDAxZTJjMTBmZDUwNjZlNTAxYjg3ZTNkNjQ3OTMwOTJiODVhNTBjODU2ZDk2MmY4YmU5MmM3OCJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Blocks.FURNACE.getName().getString(),"PandaClod", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTNiZjBiODg1OWExZTU3ZjNhYmQ2MjljMGM3MzZlNjQ0ZTgxNjUxZDRkZTAzNGZlZWE0OWY4ODNmMDBlODJiMCJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Blocks.SMITHING_TABLE.getName().getString(),"PandaClod", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcyZDFiZTQ4OGQyNjdlY2JlY2Y3ZmJjMzE0MzkwNjM2ZTk0MmI2NDYwNjI4YjAyMTA0YTgzODE4NGI3ZTczNyJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Blocks.BARREL.getName().getString(),"PandaClod", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzc1MDJjNmE2ODg1MmViZjEwODdhMmVkMmE2M2Y4ZDg1ODk4YTcwNDlkNTE0YjJjNjc1ZmEwOTE1YmY0NzI0NiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Blocks.BARREL.getName().getString(),"PandaClod", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmM0OGY3M2U5ZDIyMjQ4ZDg5YjJlOWYyNjE1Zjk4MGNjNjA4MjdlZDNiNmQzOTVlNTNiNTdhODJkNGVhNWZlIn19fQ=="),
//              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.BOOK).getHoverName().getString(), "takatalvi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFhMGQ3YmNhNThlM2YyZGY0MjA2YjE5ZDg0NWVhYmY4MWIxMWZhZDIxYzAwN2U5ZWE3YzJjNjM5Yzc1MjAxZCJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.BOOK).getHoverName().getString(), "GoodBook1","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjMzNTk4NDM3ZTMxMzMyOWViMTQxYTEzZTkyZDliMDM0OWFhYmU1YzY0ODJhNWRkZTdiNzM3NTM2MzRhYmEifX19"),
//              new BuySkull(Blocks.PLAYER_HEAD, new ItemStack(Items.ENCHANTED_BOOK).getHoverName().getString(), "CraftyGamer_","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjYyNjUxODc5ZDg3MDQ5OWRhNTBlMzQwMzY4MDBkZGZmZDUyZjNlNGUxOTkzYzVmYzBmYzgyNWQwMzQ0NmQ4YiJ9fX0="),
//              // new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.").getString(), ""),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.present_box").getString(),"FELIXCAT_3", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjFiYzlkNDJiMDA0MWU4Zjk1Y2I5YjI2NjI4ZmRhZjUwY2QwZTM2ZjdiYjlkNmIzYTRkMmFmMzk0OWRhOTdkNiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.present_box").getString(),"FELIXCAT_3","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmIxZWM3ZGM3NTMwNjFjYTE3NDQyNGVhNDVjZjk0OTBiMzljZDVkY2NhNDc3ZDEzOGE2MDNlNmJlNzU1ZWM3MiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJkMjI5NGY1NGMwZDM4OTRiYWY2MDkyZDA1OTVkY2NlZGIxMjNjYmY1MGVkMzg3MGJkM2Q3ZDQ4MTkwYTRmNCJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWZjYzA2ZGUxOTEzY2VkNTgzYTdjMDJkNTkzOGFkYmM3NTE2MDdjZDM3ODM2YzhiZTVkNmI0OTc0NzM4YTQ2NiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmU5NjdhNDhkNmIzZTc3NmMwNTI0MzhmMDMyNzk4NjVmMDZhYjc3NjFkMTMwZmI5YzZmMDYxYTE3MzQ1ZTY4In19fQ=="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmJhYjAzYzZlYjgzOGY2N2VmM2IyNTk4YzEyZjg0NzM1MzRjMzY2ZTY3YjQxZTJmMWY4OTNhZWIwNDQwZTQ5NCJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWFhYmFiYzMyMTljNjFkZWZhYWNiNjdiMGUxNWU2OWIyMzdmYmM4ZGI1Y2E0ZTE4ZjliMjM3ZmVkZWNlOWZiMCJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDE0ZDM2ZGJiYjMzYjQyNjgwMjA2NTc1NDFiMTFmZWYzYjAwYzkwYzgwOTg1MGM2MjMzN2U5NzE4Y2FmNzU5NiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjgxZjBlNDQ2YmJmOWJlM2U3YWQzMTY0NTdiZDJiM2I2ODliYmVmMjdmZTM2NjViYzhkNGQ0MzE5MDk2N2NkMyJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQ5ZDFhNTAwZTg2NGI0NDdkMDUwMjM3YmNlNWY3MGU2MDVlYTUzNTYxOTMyZGIzODNhYWJlY2Q4ZGY1NjA3MSJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYxNTgyODE2MGFmNzI4YTU3ZmRkMWNkMGU3YTU1ODNkMDY0ZTIxYmU0MDVkYTY4ZDVhZDM2YjM5ZDJhMTg0NiJ9fX0="),
//              new BuySkull(Blocks.PLAYER_HEAD, Component.translatable("skull.urushi.traffic_light").getString(),"kohonayoshi","eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODY3YmU5MjU0MzIwM2VjM2MzZDlmMGM1YjI5OGNlNWY5ZGMwN2QwZDViY2I3NjdjMDc4YjEzNTA3MjM0Y2M0NyJ9fX0=")
//*/
//
//
//
//
//
//
//      })));
         });


    public KakuriyoVillagerEntity(EntityType<? extends AbstractVillager> p_34271_, Level level) {
        super(EntityRegister.KakuriyoVillager.get(), level);
        this.setProfessionType(KakuriyoVillagerProfessionType.getType(level.getRandom().nextInt(6)));
    }
    public KakuriyoVillagerEntity(Level p_i1705_1_, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_) {
        this(EntityRegister.KakuriyoVillager.get(), p_i1705_1_);
        this.setPos(p_i1705_2_, p_i1705_4_, p_i1705_6_);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = p_i1705_2_;
        this.yo = p_i1705_4_;
        this.zo = p_i1705_6_;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PROFESSION, -1);
    }
    public KakuriyoVillagerProfessionType getProfessionType() {
        return KakuriyoVillagerProfessionType.getType(this.entityData.get(PROFESSION));
    }
    public void setProfessionType(KakuriyoVillagerProfessionType professionType) {
        this.entityData.set(PROFESSION, professionType.getID());
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> toIntMap(ImmutableMap<Integer, VillagerTrades.ItemListing[]> p_35631_) {
        return new Int2ObjectOpenHashMap<>(p_35631_);
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.5D));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35D));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35D));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel p_150046_, AgeableMob p_150047_) {
        return null;
    }

    public boolean showProgressBar() {
        return false;
    }


    public InteractionResult mobInteract(Player p_35856_, InteractionHand p_35857_) {
        ItemStack itemstack = p_35856_.getItemInHand(p_35857_);
        if (!itemstack.is(ItemAndBlockRegister.kakuriyo_villager_spawn_egg.get()) && this.isAlive() && !this.isTrading() && !this.isBaby()&&this.getProfessionType()!=KakuriyoVillagerProfessionType.Jobless) {
            if (p_35857_ == InteractionHand.MAIN_HAND) {
                p_35856_.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else {
                if (!this.level().isClientSide) {
                    this.setTradingPlayer(p_35856_);
                    this.openTradingScreen(p_35856_, this.getDisplayName(), 1);
                }

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        } else {
            return super.mobInteract(p_35856_, p_35857_);
        }
    }

    protected void updateTrades() {
        if(this.getProfessionType()!=KakuriyoVillagerProfessionType.Jobless) {
            VillagerTrades.ItemListing[] avillagertrades$itemlisting = TRADES.get(this.getProfessionType()).get(1);
            //  VillagerTrades.ItemListing[] avillagertrades$itemlisting1 = VillagerTrades.WANDERING_TRADER_TRADES.get(2);
            if (avillagertrades$itemlisting != null /*&& avillagertrades$itemlisting1 != null*/) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addOffersFromItemListings(merchantoffers, avillagertrades$itemlisting, 80);
            /*int i = this.random.nextInt(avillagertrades$itemlisting1.length);
            VillagerTrades.ItemListing villagertrades$itemlisting = avillagertrades$itemlisting1[i];
            MerchantOffer merchantoffer = villagertrades$itemlisting.getOffer(this, this.random);
            if (merchantoffer != null) {
                merchantoffers.add(merchantoffer);
            }*/

            }
        }
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("profession", this.getProfessionType().getID());
    }

    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setProfessionType(KakuriyoVillagerProfessionType.getType(tag.getInt("profession")));
        this.setAge(Math.max(0, this.getAge()));
    }

    public boolean removeWhenFarAway(double p_35886_) {
        return false;
    }

    protected void rewardTradeXp(MerchantOffer p_35859_) {
        if (p_35859_.shouldRewardExp()) {
            int i = 3 + this.random.nextInt(4);
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), i));
        }

    }

    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource p_35870_) {
        return null;
    }

    protected SoundEvent getDeathSound() {
        return null;
    }

    protected SoundEvent getTradeUpdatedSound(boolean p_35890_) {
        return null;
    }

    public SoundEvent getNotifyTradeSound() {
        return null;
    }


    public void aiStep() {
        super.aiStep();
    }
    public void notifyTrade(MerchantOffer offer) {
        if(offer.getUses()>0){
            offer.resetUses();
        }
        this.ambientSoundTime = -this.getAmbientSoundInterval();
        this.rewardTradeXp(offer);
        if (getTradingPlayer() instanceof ServerPlayer) {
            CriteriaTriggers.TRADE.trigger((ServerPlayer)getTradingPlayer(), this, offer.getResult());
        }

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.TradeWithVillagerEvent(getTradingPlayer(), offer, this));
    }
    static class Buy implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int villagerXp;
        private final float priceMultiplier;

        public Buy(Block block, int cost, int numberOfItems,  int xp) {
            this(new ItemStack(block), cost, numberOfItems, xp);
        }
        public Buy(Item item, int cost, int numberOfItems, int xp) {
            this(new ItemStack(item), cost, numberOfItems, xp);
        }

        public Buy(ItemStack stack, int cost, int numberOfItems, int xp) {
            this(stack, cost, numberOfItems, xp, 0.05F);
        }

        public Buy(ItemStack stack, int cost, int numberOfItems, int xp, float priceMultiplier) {
            this.itemStack = stack;
            this.emeraldCost = cost;
            this.numberOfItems = numberOfItems;
            this.villagerXp = xp;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity p_219699_, RandomSource p_219700_) {
            return new MerchantOffer(new ItemStack(ItemAndBlockRegister.coin.get(), this.emeraldCost), new ItemStack(this.itemStack.getItem(), this.numberOfItems), 3000, this.villagerXp, this.priceMultiplier);
        }
    }
    static class Sell implements VillagerTrades.ItemListing {
        private final Item item;
        private final int cost;
        private final int villagerXp;
        private final float priceMultiplier;

        public Sell(ItemLike item, int cost, int xp) {
            this.item = item.asItem();
            this.cost = cost;
            this.villagerXp = xp;
            this.priceMultiplier = 0.05F;
        }

        public MerchantOffer getOffer(Entity p_219682_, RandomSource p_219683_) {
            ItemStack itemstack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(itemstack, new ItemStack(ItemAndBlockRegister.coin.get()), 3000, this.villagerXp, this.priceMultiplier);
        }
    }
     static class BuySkull implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int villagerXp;
        private final float priceMultiplier;
        private final String name;
        private final String creator;

         private final String value;


        public BuySkull(Block block,String name,String creator,String value) {
            this(new ItemStack(block), 1, 1, 1,0.05F,name,creator,value);
        }
        public BuySkull(Item item,String name,String creator,String value) {
            this(new ItemStack(item), 1, 1, 1,0.05F,name,creator,value);
        }

        public BuySkull(ItemStack stack,String name,String creator,String value) {
            this(stack, 1, 1, 1, 0.05F,name,creator,value);
        }

        public BuySkull(ItemStack stack, int cost, int numberOfItems, int xp, float priceMultiplier,String name,String creator,String value) {
            this.itemStack = stack;
            this.emeraldCost = cost;
            this.numberOfItems = numberOfItems;
            this.villagerXp = xp;
            this.priceMultiplier = priceMultiplier;
            this.name=name;
            this.creator=creator;
            this.value=value;
        }
        public MerchantOffer getOffer(Entity p_219699_, RandomSource p_219700_) {
            ItemStack stack=new ItemStack(this.itemStack.getItem(), this.numberOfItems);
            if(stack.getTag()==null){
                stack.setTag(new CompoundTag());
            }
            CompoundTag tag=stack.getTag();
               byte[]i={(byte) 2080793942, (byte) -524468218, (byte) -1541115779, (byte)1949756395};
               //tag.putString("SkullOwner","MHF_Pumpkin");
           CompoundTag compoundtag1 = new CompoundTag();
            compoundtag1.putUUID("Id", UUID.nameUUIDFromBytes(i));
            CompoundTag compoundtag2 = new CompoundTag();
            ListTag listtag = new ListTag();
            CompoundTag compoundtag3 = new CompoundTag();
            compoundtag3.putString("Value", this.value);
            listtag.add(compoundtag3);
            compoundtag2.put("textures", listtag);
            compoundtag1.put("Properties", compoundtag2);
            compoundtag1.putString("Name", this.name);
            tag.put("SkullOwner",compoundtag1);
            tag.putBoolean("MinecraftHeadsCredit",true);
            tag.putString("HeadsCreator",creator);
            stack.setTag(tag);
            return new MerchantOffer(new ItemStack(ItemAndBlockRegister.coin.get(), this.emeraldCost), stack, 3000, this.villagerXp, this.priceMultiplier);
        }
    }
 }
