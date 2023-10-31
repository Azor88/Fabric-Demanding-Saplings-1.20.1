package net.azor.demandingsaplings.item;

import net.azor.demandingsaplings.DemandingSaplings;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item FROZEN_BUSH = registerItem("frozen_bush", new Item(new FabricItemSettings()));
    public static final Item DEAD_SAPLING = registerItem("dead_sapling", new Item(new FabricItemSettings()));

    private static void addItemsToIngredientTabItemGroup(FabricItemGroupEntries entries)
    {
        entries.add(FROZEN_BUSH);
        entries.add(DEAD_SAPLING);
    }

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(DemandingSaplings.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        DemandingSaplings.LOGGER.info("Registering Items for " + DemandingSaplings.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModItems::addItemsToIngredientTabItemGroup);
    }
}
