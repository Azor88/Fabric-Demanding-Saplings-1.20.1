package net.azor.demandingsaplings;

import net.azor.demandingsaplings.block.Modblocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class DemandingSaplingsClient  implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(Modblocks.FROZEN_BUSH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblocks.POTTED_FROZEN_BUSH, RenderLayer.getCutout());
    }
}
