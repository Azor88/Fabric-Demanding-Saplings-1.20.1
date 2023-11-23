package net.azor.demandingsaplings.init;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.azor.demandingsaplings.config.DemandingSaplingsConfig;

public class ConfigInit {
    public static DemandingSaplingsConfig CONFIG = new DemandingSaplingsConfig();

    public static void init() {
        AutoConfig.register(DemandingSaplingsConfig.class, Toml4jConfigSerializer::new);  // GSON or any other serializer if you want to use other files like json5...
        CONFIG = AutoConfig.getConfigHolder(DemandingSaplingsConfig.class).getConfig();
    }
}
