package net.azor.demandingsaplings.mixin;

import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.SaplingKiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FungusBlock;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.state.property.Properties.STAGE;

@Mixin(FungusBlock.class)
public abstract class FungusBlockGrowthMixin {
    @Inject(at = @At("HEAD"), method = "grow", cancellable = true)
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {

        Block fungus = world.getBlockState(pos).getBlock();

        if (fungus.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT)  && (world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_OVERWORLD) || world.getBiomeAccess().getBiome(pos).isIn(BiomeTags.IS_END))) {
            SaplingKiller.killSapling(world, pos, 0, -1, true, true);
            ci.cancel();
        }
    }
}
