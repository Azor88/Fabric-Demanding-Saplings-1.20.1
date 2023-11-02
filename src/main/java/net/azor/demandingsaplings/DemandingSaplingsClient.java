package net.azor.demandingsaplings;

import net.azor.demandingsaplings.block.ModBlocks;
<<<<<<< HEAD
=======
import net.azor.demandingsaplings.util.ModModelPredicateProvider;
>>>>>>> 84a60fe (Thermometer now tells you the temperature value of its given biome location, it also now updated its texture, although not completely functional.)
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class DemandingSaplingsClient  implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FROZEN_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_FROZEN_BUSH, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEAD_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_DEAD_SAPLING, RenderLayer.getCutout());
<<<<<<< HEAD
=======

        ModModelPredicateProvider.registerModModels();
>>>>>>> 84a60fe (Thermometer now tells you the temperature value of its given biome location, it also now updated its texture, although not completely functional.)
    }
}
