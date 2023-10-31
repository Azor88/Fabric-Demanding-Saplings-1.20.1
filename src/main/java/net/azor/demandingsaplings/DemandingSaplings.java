package net.azor.demandingsaplings;

import net.azor.demandingsaplings.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemandingSaplings implements ModInitializer {
	public static final String MOD_ID = "demandingsaplings";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		LOGGER.info("Hello Fabric world!");
	}
}