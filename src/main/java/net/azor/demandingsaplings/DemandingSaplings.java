package net.azor.demandingsaplings;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.azor.demandingsaplings.block.ModBlocks;
import net.azor.demandingsaplings.config.DemandingSaplingsConfig;
import net.azor.demandingsaplings.item.ModItemGroups;
import net.azor.demandingsaplings.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemandingSaplings implements ModInitializer {
	public static final String MOD_ID = "demandingsaplings";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static DemandingSaplingsConfig CONFIG = new DemandingSaplingsConfig();

	@Override
	public void onInitialize() {

		AutoConfig.register(DemandingSaplingsConfig.class, GsonConfigSerializer::new);  // GSON or any other serializer if you want to use other files like json5...
		CONFIG = AutoConfig.getConfigHolder(DemandingSaplingsConfig.class).getConfig();

		ModItemGroups.registerItemGroup();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		FuelRegistry.INSTANCE.add(ModBlocks.FROZEN_BUSH, 25);
		FuelRegistry.INSTANCE.add(ModBlocks.DEAD_SAPLING, 50);


	}
}