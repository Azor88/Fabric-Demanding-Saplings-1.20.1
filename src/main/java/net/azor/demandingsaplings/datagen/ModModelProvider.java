package net.azor.demandingsaplings.datagen;

import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.FROZEN_BUSH, ModBlocks.POTTED_FROZEN_BUSH, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerFlowerPotPlant(ModBlocks.DEAD_SAPLING, ModBlocks.POTTED_DEAD_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
<<<<<<< HEAD
        itemModelGenerator.register(ModItems.THERMOMETER, Models.GENERATED);
=======
        //itemModelGenerator.register(ModItems.THERMOMETER, Models.GENERATED);
>>>>>>> 84a60fe (Thermometer now tells you the temperature value of its given biome location, it also now updated its texture, although not completely functional.)
    }
}
