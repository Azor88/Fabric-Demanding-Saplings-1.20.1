package net.azor.demandingsaplings.item;

import net.azor.demandingsaplings.DemandingSaplings;
import net.azor.demandingsaplings.block.Modblocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static final ItemGroup DEMANDING_SAPLINGS = Registry.register(Registries.ITEM_GROUP, new Identifier(DemandingSaplings.MOD_ID, "frozen_bush"),
            FabricItemGroup.builder().displayName(
                    Text.translatable("itemgroup.demanding_saplings")).icon(() -> new ItemStack(
                            Modblocks.FROZEN_BUSH)).entries((displayContext, entries) -> {
                                //entries.add(ModItems.FROZEN_BUSH);
                                entries.add(ModItems.DEAD_SAPLING);

                                entries.add(Modblocks.FROZEN_BUSH);
                                entries.add(Modblocks.POTTED_FROZEN_BUSH);
            }).build());

    public static void registerItemGroup()
    {
        DemandingSaplings.LOGGER.info("Registering ItemGroup for " + DemandingSaplings.MOD_ID);
    }
}
