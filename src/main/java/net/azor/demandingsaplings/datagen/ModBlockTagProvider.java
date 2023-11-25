package net.azor.demandingsaplings.datagen;

import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
                .add(ModBlocks.DEAD_SAPLING)
                .add(ModBlocks.DEAD_FUNGUS);

        getOrCreateTagBuilder(BlockTags.FLOWER_POTS)
                .add(ModBlocks.FROZEN_BUSH)
                .add(ModBlocks.DEAD_SAPLING)
                .add(ModBlocks.DEAD_FUNGUS);

        getOrCreateTagBuilder(ModTags.Blocks.TEMPERATURE_DEPENDANT)
                .forceAddTag(BlockTags.SAPLINGS)
                .add(Blocks.CRIMSON_FUNGUS)
                .add(Blocks.WARPED_FUNGUS);

        getOrCreateTagBuilder(BlockTags.DEAD_BUSH_MAY_PLACE_ON)
                .add(Blocks.CRIMSON_NYLIUM)
                .add(Blocks.WARPED_NYLIUM)
                .add(Blocks.NETHERRACK);
    }
}
