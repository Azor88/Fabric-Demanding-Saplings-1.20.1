package net.azor.demandingsaplings.util;

import net.minecraft.util.math.BlockPos;

public class TemperatureHandler {
    private static final double tempSubtractor = 0.00125;
    public static float getTemperature(float biomeTemperature, BlockPos position) {
        float posY = position.getY();

        double tempBioma = 0;

        if (posY <= 80) {
            tempBioma = biomeTemperature;
        }
        else if (posY >= 81) {
            int altura = (int)posY - 81;
            double totalResta = altura * tempSubtractor;
            tempBioma = biomeTemperature - totalResta;
        }

        return (float)tempBioma;
    }
}
