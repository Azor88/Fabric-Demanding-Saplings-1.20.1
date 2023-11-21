package net.azor.demandingsaplings.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.azor.demandingsaplings.config.datatype.ThermometerData;

@Config(name = "demandingsaplings")  // <-- name of config file
@Config.Gui.Background("minecraft:textures/block/dirt.png")  // for ingame config editing screen...
public class DemandingSaplingsConfig implements ConfigData {

    @Comment("Thermometer will display simple temperature readings(TRUE) or precise ones using Celsius and/or Fahrenheit(FALSE)")
    public ThermometerData THERMOMETERDATA = new ThermometerData(true, ThermometerData.ReadingMode.BOTH);

    @Comment("Temperature range of the sapling, minimum = -1, maximum = 2.5")
    public float[] DEFAULTRANGE = new float[]{-0.2f, 1f};
    public float[] ACACIARANGE = new float[]{1.8f, 2.2f};
    public float[] AZALEASRANGE = new float[]{0.45f, 0.55f};
    public float[] BIRCHRANGE = new float[]{0.4f, 0.8f};
    public float[] CHERRYRANGE = new float[]{0.4f, 0.55f};
    public float[] DARKOAKRANGE = new float[]{0.65f, 0.8f};
    public float[] JUNGLERANGE = new float[]{0.9f, 1f};
    public float[] MANGROVERANGE = new float[]{0.7f, 0.9f};
    public float[] OAKRANGE = new float[]{0.4f, 2.1f};
    public float[] SPRUCERANGE = new float[]{-0.35f, 0.75f};

}