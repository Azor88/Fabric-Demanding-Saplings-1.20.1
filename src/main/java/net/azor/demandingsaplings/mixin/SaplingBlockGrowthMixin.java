package net.azor.demandingsaplings.mixin;

import net.azor.demandingsaplings.DemandingSaplings;
import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.TemperatureHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
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

import static net.minecraft.state.property.Properties.STAGE;

@Mixin(SaplingBlock.class)
public abstract class SaplingBlockGrowthMixin {

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(ServerWorld world, BlockPos pos, BlockState state, Random random, CallbackInfo ci) {

        float biomeTemperature = world.getBiomeAccess().getBiome(pos).value().getTemperature();
        float tempValue = TemperatureHandler.getTemperature(biomeTemperature, pos); //Not Functional yet
        Block sapling = world.getBlockState(pos).getBlock();

        if (state.get(STAGE) == 0) return;
        if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)  && world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_OVERWORLD)) {

            float[] tempRange = getTemperatureRange(sapling);

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
    private static float[] getTemperatureRange(Block sapling) {
        float[] tempRange = {};
        if (sapling == Blocks.ACACIA_SAPLING) {
            tempRange = DemandingSaplings.CONFIG.ACACIARANGE;
        } else if (sapling == Blocks.BIRCH_SAPLING) {
            tempRange = DemandingSaplings.CONFIG.BIRCHRANGE;
        } else if (sapling == Blocks.CHERRY_SAPLING) {
            tempRange = DemandingSaplings.CONFIG.CHERRYRANGE;
        } else if (sapling == Blocks.DARK_OAK_SAPLING) {
            tempRange = DemandingSaplings.CONFIG.DARKOAKRANGE;
        } else if (sapling == Blocks.JUNGLE_SAPLING) {
            tempRange = DemandingSaplings.CONFIG.JUNGLERANGE;
        } else if (sapling == Blocks.MANGROVE_PROPAGULE) {
            tempRange = DemandingSaplings.CONFIG.MANGROVERANGE;
        } else if (sapling == Blocks.OAK_SAPLING) {
            tempRange = DemandingSaplings.CONFIG.OAKRANGE;
        } else if (sapling == Blocks.SPRUCE_SAPLING) {
            tempRange = DemandingSaplings.CONFIG.SPRUCERANGE;
        }
        else {
            tempRange = DemandingSaplings.CONFIG.DEFAULTRANGE;
        }
        return tempRange;
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