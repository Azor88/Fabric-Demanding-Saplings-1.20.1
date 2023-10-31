package net.azor.demandingsaplings.datagen;

import net.azor.demandingsaplings.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.FROZEN_BUSH)
                .add(ModBlocks.DEAD_SAPLING);

        getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
                .add(ModBlocks.FROZEN_BUSH)
                .add(ModBlocks.DEAD_SAPLING);
    }
}
