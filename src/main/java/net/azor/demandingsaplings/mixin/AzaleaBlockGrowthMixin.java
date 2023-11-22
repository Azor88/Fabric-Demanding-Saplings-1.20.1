package net.azor.demandingsaplings.mixin;

import net.azor.demandingsaplings.DemandingSaplings;
import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.TemperatureHandler;
import net.minecraft.block.*;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(AzaleaBlock.class)
public abstract class AzaleaBlockGrowthMixin {

    @Inject(at = @At("HEAD"), method = "grow", cancellable = true)
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {

        float biomeTemperature = world.getBiomeAccess().getBiome(pos).value().getTemperature();
        float tempValue = TemperatureHandler.getTemperature(biomeTemperature, pos);
        Block sapling = world.getBlockState(pos).getBlock();

        if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT) && world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_OVERWORLD)) {
            float[] tempRange = DemandingSaplings.CONFIG.AZALEASRANGE;

            float min = Math.min(tempRange[0], tempRange[1]);
            float max = Math.max(tempRange[0], tempRange[1]);

            float minTemp = Math.max(min, -1f);
            float maxTemp = Math.min(max, 2.5f);

            if (tempValue < minTemp) {
                killSapling(world, pos, tempValue, minTemp, true);
                ci.cancel();
            }

            if (tempValue > maxTemp) {
                killSapling(world, pos, tempValue, maxTemp, false);
                ci.cancel();
            }
        }
    }

    @Unique
    public void killSapling(ServerWorld world, BlockPos pos, float tempValue, float limitTemp, boolean colder) {
        float difference = Math.abs(tempValue - limitTemp);
        if (difference > 0.5f && colder) {
            world.setBlockState(pos, ModBlocks.FROZEN_BUSH.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, SoundCategory.BLOCKS);
        }
        else if (difference > 0.5f) {
            world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, SoundCategory.BLOCKS);
        }
        else {
            world.setBlockState(pos, ModBlocks.DEAD_SAPLING.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, SoundCategory.BLOCKS);
        }
    }
}