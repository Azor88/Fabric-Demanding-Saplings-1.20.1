package net.azor.demandingsaplings.mixin;

import net.azor.demandingsaplings.DemandingSaplings;
import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.init.ConfigInit;
import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.SaplingKiller;
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
            float[] tempRange = ConfigInit.CONFIG.AZALEASRANGE;

            float min = Math.min(tempRange[0], tempRange[1]);
            float max = Math.max(tempRange[0], tempRange[1]);

            float minTemp = Math.max(min, -1f);
            float maxTemp = Math.min(max, 2.5f);
            float difference;
            boolean dies;

            if (tempValue < minTemp) {
                difference = Math.abs(tempValue - minTemp);
                if (difference < 0.25) { //Chance of the sapling to still grow even if the temperature is outside its compatible range
                    dies = SaplingKiller.getChance(difference);
                    if (dies) {
                        SaplingKiller.killSapling(world, pos, 0, 0.25f, true, false);
                        ci.cancel();
                    }
                }
                else {
                    SaplingKiller.killSapling(world, pos, tempValue, minTemp, true, false);
                    ci.cancel();
                }
            }

            if (tempValue > maxTemp) {
                difference = Math.abs(tempValue - maxTemp);
                if (difference < 0.25) { //Chance of the sapling to still grow even if the temperature is outside its compatible range
                    dies = SaplingKiller.getChance(difference);
                    if (dies) {
                        SaplingKiller.killSapling(world, pos, 0, 0.25f, false, false);
                        ci.cancel();
                    }
                }
                else {
                    SaplingKiller.killSapling(world, pos, tempValue, maxTemp, false, false);
                    ci.cancel();
                }
            }
        }

        else if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)  && world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_END)) {
            SaplingKiller.killSapling(world, pos, 0, -1, true, false);
            ci.cancel();
        }
        else if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)  && world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_NETHER)) {
            SaplingKiller.killSapling(world, pos, 0, 2.5f, false, false);
            ci.cancel();
        }
    }
}