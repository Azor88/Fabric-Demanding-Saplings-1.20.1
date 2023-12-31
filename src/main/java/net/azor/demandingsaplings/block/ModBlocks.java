package net.azor.demandingsaplings.block;

import net.azor.demandingsaplings.DemandingSaplings;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block FROZEN_BUSH = registerBlock("frozen_bush", new DeadBushBlock(FabricBlockSettings.copyOf(Blocks.DEAD_BUSH).sounds(BlockSoundGroup.CHERRY_SAPLING).nonOpaque().noCollision()));
    public static final Block DEAD_SAPLING = registerBlock("dead_sapling", new DeadBushBlock(FabricBlockSettings.copyOf(Blocks.DEAD_BUSH).sounds(BlockSoundGroup.CHERRY_SAPLING).nonOpaque().noCollision()));
    public static final Block DEAD_FUNGUS = registerBlock("dead_fungus", new DeadBushBlock(FabricBlockSettings.copyOf(Blocks.CRIMSON_FUNGUS).sounds(BlockSoundGroup.FUNGUS).nonOpaque().noCollision()));
    public static final Block POTTED_FROZEN_BUSH = Registry.register(Registries.BLOCK, new Identifier(DemandingSaplings.MOD_ID, "potted_frozen_bush"),
            new FlowerPotBlock(FROZEN_BUSH, FabricBlockSettings.copyOf(Blocks.POTTED_DEAD_BUSH).nonOpaque()));
    public static final Block POTTED_DEAD_SAPLING = Registry.register(Registries.BLOCK, new Identifier(DemandingSaplings.MOD_ID, "potted_dead_sapling"),
            new FlowerPotBlock(DEAD_SAPLING, FabricBlockSettings.copyOf(Blocks.POTTED_DEAD_BUSH).nonOpaque()));
    public static final Block POTTED_DEAD_FUNGUS = Registry.register(Registries.BLOCK, new Identifier(DemandingSaplings.MOD_ID, "potted_dead_fungus"),
            new FlowerPotBlock(DEAD_FUNGUS, FabricBlockSettings.copyOf(Blocks.POTTED_CRIMSON_FUNGUS).nonOpaque()));


    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(DemandingSaplings.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block)
    {
        return Registry.register(Registries.ITEM, new Identifier(DemandingSaplings.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks()
    {
        DemandingSaplings.LOGGER.info("Registering ModBlocks for " + DemandingSaplings.MOD_ID);
    }
}
