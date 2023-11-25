package net.azor.demandingsaplings.mixin;

import net.azor.demandingsaplings.DemandingSaplings;
import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.init.ConfigInit;
import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.SaplingKiller;
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
        float tempValue = TemperatureHandler.getTemperature(biomeTemperature, pos);
        Block sapling = world.getBlockState(pos).getBlock();

        if (state.get(STAGE) == 0) return;
        if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)  && world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_OVERWORLD)) {

            float[] tempRange = getTemperatureRange(sapling);

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
                        SaplingKiller.killSapling(world, pos, 0, 0.25f, true);
                        ci.cancel();
                    }
                }
                else {
                    SaplingKiller.killSapling(world, pos, tempValue, minTemp, true);
                    ci.cancel();
                }
            }

            if (tempValue > maxTemp) {
                difference = Math.abs(tempValue - maxTemp);
                if (difference < 0.25) { //Chance of the sapling to still grow even if the temperature is outside its compatible range
                    dies = SaplingKiller.getChance(difference);
                    if (dies) {
                        SaplingKiller.killSapling(world, pos, 0, 0.25f, false);
                        ci.cancel();
                    }
                }
                else {
                    SaplingKiller.killSapling(world, pos, tempValue, maxTemp, false);
                    ci.cancel();
                }
            }
        }
        else if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)  && world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_END)) {
            SaplingKiller.killSapling(world, pos, 0, -1, true);
            ci.cancel();
        }
        else if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)  && world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_NETHER)) {
            SaplingKiller.killSapling(world, pos, 0, 2.5f, false);
            ci.cancel();
        }
    }

    @Unique
    private static float[] getTemperatureRange(Block sapling) {
        float[] tempRange = {};
        if (sapling == Blocks.ACACIA_SAPLING) {
            tempRange = ConfigInit.CONFIG.ACACIARANGE;
        } else if (sapling == Blocks.BIRCH_SAPLING) {
            tempRange = ConfigInit.CONFIG.BIRCHRANGE;
        } else if (sapling == Blocks.CHERRY_SAPLING) {
            tempRange = ConfigInit.CONFIG.CHERRYRANGE;
        } else if (sapling == Blocks.DARK_OAK_SAPLING) {
            tempRange = ConfigInit.CONFIG.DARKOAKRANGE;
        } else if (sapling == Blocks.JUNGLE_SAPLING) {
            tempRange = ConfigInit.CONFIG.JUNGLERANGE;
        } else if (sapling == Blocks.MANGROVE_PROPAGULE) {
            tempRange = ConfigInit.CONFIG.MANGROVERANGE;
        } else if (sapling == Blocks.OAK_SAPLING) {
            tempRange = ConfigInit.CONFIG.OAKRANGE;
        } else if (sapling == Blocks.SPRUCE_SAPLING) {
            tempRange = ConfigInit.CONFIG.SPRUCERANGE;
        }
        else {
            tempRange = ConfigInit.CONFIG.DEFAULTRANGE;
        }
        return tempRange;
    }
}