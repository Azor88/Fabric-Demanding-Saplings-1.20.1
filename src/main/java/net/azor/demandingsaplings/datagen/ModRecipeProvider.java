package net.azor.demandingsaplings.datagen;

import net.azor.demandingsaplings.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.THERMOMETER, 1)
                .pattern("  G")
                .pattern("GG ")
                .pattern("RG ")
                .input('G', Items.GLASS_PANE)
                .input('R', Items.REDSTONE)
                .criterion(hasItem(Items.GLASS_PANE), conditionsFromItem(Items.GLASS_PANE))
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.THERMOMETER)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.SAPLINGSCANNER, 1)
                .pattern("SGI")
                .pattern("tRO")
                .pattern("OTO")
                .input('G', Items.GLASS_PANE)
                .input('S', Items.STRING)
                .input('I', Items.IRON_INGOT)
                .input('t', Items.TRIPWIRE_HOOK)
                .input('R', Items.REDSTONE)
                .input('O', Items.GOLD_INGOT)
                .input('T', ModItems.THERMOMETER)
                .criterion(hasItem(Items.GLASS_PANE), conditionsFromItem(Items.GLASS_PANE))
                .criterion(hasItem(Items.STRING), conditionsFromItem(Items.STRING))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .criterion(hasItem(Items.TRIPWIRE_HOOK), conditionsFromItem(Items.TRIPWIRE_HOOK))
                .criterion(hasItem(Items.REDSTONE), conditionsFromItem(Items.REDSTONE))
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .criterion(hasItem(ModItems.THERMOMETER), conditionsFromItem(ModItems.THERMOMETER))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.SAPLINGSCANNER)));

    }
}
