package net.azor.demandingsaplings.datagen;

import net.azor.demandingsaplings.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addPottedPlantDrops(ModBlocks.POTTED_FROZEN_BUSH);
        addPottedPlantDrops(ModBlocks.POTTED_DEAD_SAPLING);
        addPottedPlantDrops(ModBlocks.POTTED_DEAD_FUNGUS);
    }
}
