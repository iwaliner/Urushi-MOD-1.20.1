package com.iwaliner.urushi.mixin;

import com.iwaliner.urushi.RecipeTypeRegister;
import com.iwaliner.urushi.block.SlideDoorBlock;
import com.iwaliner.urushi.recipe.SenbakokiRecipe;
import com.iwaliner.urushi.recipe.ThrowingInRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ItemEntity.class)

public abstract class ItemEntityMixin {
    @Shadow public abstract ItemStack getItem();

    @Shadow public abstract ItemEntity copy();

    @Shadow public abstract void setItem(ItemStack p_32046_);

    @Inject(method = "tick",at = @At("HEAD"), cancellable = true,remap = false)
    private void tickInject(CallbackInfo ci){
        ItemStack stack=this.getItem();
        Level level=this.copy().level();
        BlockPos pos=this.copy().blockPosition();
        if(level.getFluidState(pos).is(Fluids.WATER)||level.getFluidState(pos).is(Fluids.FLOWING_WATER)) {
            Optional<ThrowingInRecipe> recipe = Optional.of(level.getRecipeManager())
                    .flatMap(manager -> manager.getRecipeFor(RecipeTypeRegister.ThrowingInRecipe, new SimpleContainer(stack), level));
            if (recipe.isPresent()) {
                this.setItem(recipe.get().getResultItem());
                level.playSound((Player) null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.NEUTRAL, 1F, 1F);

            }
        }
        }
}
