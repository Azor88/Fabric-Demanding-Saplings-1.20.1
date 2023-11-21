package net.azor.demandingsaplings.mixin;

import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.TemperatureHandler;
import net.minecraft.block.AzaleaBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(AzaleaBlock.class)
public abstract class AzaleaBlockGrowthMixin {

    @Inject(at = @At("HEAD"), method = "grow", cancellable = true)
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {

        float biomeTemperature = world.getBiomeAccess().getBiome(pos).value().getTemperature();
        float tempValue = TemperatureHandler.getTemperature(biomeTemperature, pos); //Not Functional yet
        Block sapling = world.getBlockState(pos).getBlock();

        if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)) {

            if (world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_OVERWORLD)) {
                world.setBlockState(pos, ModBlocks.DEAD_SAPLING.getDefaultState());
                world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, SoundCategory.BLOCKS);
                ci.cancel();
            }
        }
    }
}