package net.azor.demandingsaplings.mixin;

import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.TemperatureHandler;
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

import static net.minecraft.state.property.Properties.STAGE;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlockGrowthMixin {

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfo ci) {

        float biomeTemperature = world.getBiomeAccess().getBiome(pos).value().getTemperature();
        float tempValue = TemperatureHandler.getTemperature(biomeTemperature, pos); //Not Functional yet
        Block sapling = world.getBlockState(pos).getBlock();

        if (state.get(STAGE) == 0) return;
        if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)) {

            if (tempValue >= 1.9 && world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_OVERWORLD)) {
                world.setBlockState(pos, ModBlocks.DEAD_SAPLING.getDefaultState());
                world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, SoundCategory.BLOCKS);
                ci.cancel();
            }
        }
    }
}