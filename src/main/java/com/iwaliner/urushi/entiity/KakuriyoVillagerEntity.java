package com.iwaliner.urushi.entiity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.iwaliner.urushi.EntityRegister;
import com.iwaliner.urushi.ItemAndBlockRegister;
import com.iwaliner.urushi.SoundRegister;
import com.iwaliner.urushi.util.ElementType;
import com.iwaliner.urushi.util.KakuriyoVillagerProfessionType;
import com.iwaliner.urushi.util.UrushiUtils;
import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.sounds.SoundSource;
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
import java.util.Random;
import java.util.UUID;
public class KakuriyoVillagerEntity extends AbstractVillager {
    private static final EntityDataAccessor<Integer> PROFESSION = SynchedEntityData.defineId(KakuriyoVillagerEntity.class, EntityDataSerializers.INT);
  public static final Map<KakuriyoVillagerProfessionType, Int2ObjectMap<VillagerTrades.ItemListing[]>> TRADES = Util.make(Maps.newHashMap(), (p_35633_) -> {
        p_35633_.put(KakuriyoVillagerProfessionType.Miner, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
                        new Sell(Blocks.TORCH, 24, 2),
                        new Sell(Items.IRON_PICKAXE, 1,2),
                        new Buy(Blocks.GRAVEL, 1, 20,  1),
                        new Buy(Blocks.SAND, 1, 20,  1),
                        new Buy(Blocks.RED_SAND, 1, 20,  1),
                new Buy(Items.CLAY_BALL, 1, 16,  1),
                        new Buy(Blocks.COBBLESTONE, 1, 24,  1),
                        new Buy(Blocks.STONE, 1, 16,  1),
                        new Buy(Blocks.GRANITE, 1, 16,  1),
                        new Buy(Blocks.DIORITE, 1, 16,  1),
                        new Buy(Blocks.ANDESITE, 1, 16,  1),
                        new Buy(Blocks.COBBLED_DEEPSLATE, 1, 16,  1),
                new Buy(Blocks.TUFF, 1, 16,  1),
                new Buy(Blocks.CALCITE, 1, 16,  1),
                        new Buy(Blocks.BASALT, 1, 16,  1),
                        new Buy(Blocks.BLACKSTONE, 1, 16,  1),
                        new Buy(ItemAndBlockRegister.cobbled_yomi_stone.get(), 1, 16,  1),
                new Buy(Items.REDSTONE, 1, 6,  1),
                new Buy(Items.LAPIS_LAZULI, 1, 6,  1),
                new Buy(Items.QUARTZ, 1, 12,  1),
                }
        )));
      p_35633_.put(KakuriyoVillagerProfessionType.RiceDealer, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
              new Sell(ItemAndBlockRegister.rice_crop.get(), 24,2),
              new Buy(ItemAndBlockRegister.raw_rice.get(), 1,6,1),
              new Buy(ItemAndBlockRegister.dirt_furnace.get(), 2,1,1),
              new Buy(ItemAndBlockRegister.rice_cauldron.get(), 3,1,1),
              new Buy(ItemAndBlockRegister.rice_malt.get(), 1,6,1),
              new Buy(ItemAndBlockRegister.roasted_rice_cake.get(), 1,6,1),
              //new Buy(getRandomRiceBall(6), 1,6,1)
              new BuyRiceBall( 1,6,1,0.05F)
      })));
      p_35633_.put(KakuriyoVillagerProfessionType.Fisherman, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
              new Sell(Items.STRING, 20,2),
              new Sell(ItemAndBlockRegister.silkworm.get(), 12,2),
              new Buy(Items.COD, 1,8,1),
              new Buy(Items.SALMON, 1,8,1),
              new Buy(ItemAndBlockRegister.tsuna.get(), 1,2,1),
              new Buy(ItemAndBlockRegister.shrimp.get(), 1,12,1),
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
              new Buy(ItemAndBlockRegister.petrified_log.get(), 1,4,1)
      })));
p_35633_.put(KakuriyoVillagerProfessionType.Cook, toIntMap(ImmutableMap.of(1, new VillagerTrades.ItemListing[]{
        new Sell(ItemAndBlockRegister.matured_japanese_apricot_fruit.get(), 16,2),
        new Sell(ItemAndBlockRegister.green_onion_crop.get(), 20,2),
        new Buy(ItemAndBlockRegister.tea_bush.get(), 1, 1,  1),
        new Buy(ItemAndBlockRegister.salt.get(), 1, 12,  1),
        new Buy(ItemAndBlockRegister.milk_bamboo_cup.get(), 1, 4,  1),
        new Buy(ItemAndBlockRegister.soy_source_cup.get(), 1, 4,  1),
        new Buy(ItemAndBlockRegister.sakura_mochi.get(), 1, 8,  1),
        new Buy(ItemAndBlockRegister.color_dango.get(), 1, 8,  1),
        new Buy(ItemAndBlockRegister.baked_mochocho.get(), 1, 8,  1),
        new Buy(ItemAndBlockRegister.gravel_sushi.get(), 1, 16,  1),
        new Buy(ItemAndBlockRegister.fried_shrimp.get(), 1, 8,  1)
       })));
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


    public InteractionResult mobInteract(Player player, InteractionHand p_35857_) {
        ItemStack itemstack = player.getItemInHand(p_35857_);
        if(itemstack.is(ItemAndBlockRegister.ghost_core.get())){
            GhostEntity ghostEntity=new GhostEntity(level(),position().x,position().y,position().z);
            if(!level().isClientSide()){
                level().addFreshEntity(ghostEntity);
            }
            level().playSound(player,blockPosition(),SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.BLOCKS,1f,1f);
            level().addParticle(ParticleTypes.EXPLOSION, position().x, position().y+1D, position().z, 0.0D, 0.0D, 0.0D);
            discard();
            itemstack.shrink(1);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }else if (!itemstack.is(ItemAndBlockRegister.kakuriyo_villager_spawn_egg.get()) && this.isAlive() && !this.isTrading() && !this.isBaby()&&this.getProfessionType()!=KakuriyoVillagerProfessionType.Jobless) {
            if (p_35857_ == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (this.getOffers().isEmpty()) {
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            } else {
                if (!this.level().isClientSide) {
                    this.setTradingPlayer(player);
                    this.openTradingScreen(player,getProfessionComponent(), 1);
                }

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        } else {
            if(this.getProfessionType()==KakuriyoVillagerProfessionType.Jobless){
                player.displayClientMessage(Component.translatable("info.urushi.kakuriyo_villager_jobless").withStyle(ChatFormatting.YELLOW), true);

            }
            return super.mobInteract(player, p_35857_);
        }
    }
    private Component getProfessionComponent(){
        KakuriyoVillagerProfessionType professionType=getProfessionType();
        switch (professionType){
            case Cook : return Component.translatable("container.urushi.kakuriyo_villager_cook");
            case Miner: return Component.translatable("container.urushi.kakuriyo_villager_miner");
            case Fisherman: return Component.translatable("container.urushi.kakuriyo_villager_fisherman");
            case Lumberjack: return Component.translatable("container.urushi.kakuriyo_villager_lumberjack");
            case RiceDealer: return Component.translatable("container.urushi.kakuriyo_villager_rice_dealer");
         }
        return Component.translatable("container.urushi.kakuriyo_villager_jobless");
    }

    protected void updateTrades() {
        if(this.getProfessionType()!=KakuriyoVillagerProfessionType.Jobless) {
            VillagerTrades.ItemListing[] avillagertrades$itemlisting = TRADES.get(this.getProfessionType()).get(1);
            if (avillagertrades$itemlisting != null /*&& avillagertrades$itemlisting1 != null*/) {
                MerchantOffers merchantoffers = this.getOffers();
                this.addOffersFromItemListings(merchantoffers, avillagertrades$itemlisting, 80);

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

    @Override
    public double getMyRidingOffset() {
        return super.getMyRidingOffset()-0.45D;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset();
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
        return SoundEvents.UI_STONECUTTER_SELECT_RECIPE;
    }

    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.UI_STONECUTTER_SELECT_RECIPE;
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

    @Override
    public ItemStack getMainHandItem() {
        switch (getProfessionType()){
            case Cook : return new ItemStack(ItemAndBlockRegister.color_dango.get());
            case Miner: return new ItemStack(Items.IRON_PICKAXE);
            case Fisherman: return new ItemStack(Items.FISHING_ROD);
            case Lumberjack: return new ItemStack(Items.IRON_AXE);
            case RiceDealer: return new ItemStack(ItemAndBlockRegister.raw_rice.get());
        }
        return ItemStack.EMPTY;
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
    static class BuyRiceBall implements VillagerTrades.ItemListing {
         private final int emeraldCost;
        private final int numberOfItems;
        private final int villagerXp;
        private final float priceMultiplier;

        public BuyRiceBall(int cost, int numberOfItems, int xp, float priceMultiplier) {
            this.emeraldCost = cost;
            this.numberOfItems = numberOfItems;
            this.villagerXp = xp;
            this.priceMultiplier = priceMultiplier;
        }

        public MerchantOffer getOffer(Entity entity, RandomSource p_219700_) {
          //  ItemStack stack=UrushiUtils.getRandomRiceBall(this.numberOfItems,randomSource);
            ItemStack stack=new ItemStack(ItemAndBlockRegister.rice_ball.get(), this.numberOfItems);
            if(stack.getTag()==null){
                stack.setTag(new CompoundTag());
            }
            CompoundTag tag=stack.getTag();
            tag.putString("effect","random");
            stack.setTag(tag);
            return new MerchantOffer(new ItemStack(ItemAndBlockRegister.coin.get(), this.emeraldCost), stack, 3000, this.villagerXp, this.priceMultiplier);
        }
    }
    private static ItemStack getRandomRiceBall( int count){
        ItemStack stack=new ItemStack(ItemAndBlockRegister.rice_ball.get(),count);
        if(stack.getTag()==null){
            stack.setTag(new CompoundTag());
        }
        CompoundTag tag=stack.getTag();
        tag.putString("effect","random");
        stack.setTag(tag);
        return stack;
    }
 }
