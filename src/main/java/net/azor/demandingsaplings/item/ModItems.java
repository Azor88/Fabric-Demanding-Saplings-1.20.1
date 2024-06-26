package net.azor.demandingsaplings.item;

import net.azor.demandingsaplings.DemandingSaplings;
import net.azor.demandingsaplings.item.custom.SaplingScannerItem;
import net.azor.demandingsaplings.item.custom.ThermometerItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item THERMOMETER = registerItem("thermometer", new ThermometerItem(new FabricItemSettings().maxCount(1)));
    public static final Item SAPLINGSCANNER = registerItem("sapling_scanner", new SaplingScannerItem(new FabricItemSettings().maxCount(1)));

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, new Identifier(DemandingSaplings.MOD_ID, name), item);
    }

    public static void registerModItems()
    {
        DemandingSaplings.LOGGER.info("Registering ModItems for " + DemandingSaplings.MOD_ID);
    }
}
