package net.azor.demandingsaplings.util;

import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class TemperatureHandler {
    private static final double tempSubtractor = 0.00125;
    public static float getTemperature(float biomeTemperature, BlockPos position) {
        //En Minecraft, la temperatura base mas alta de un bioma es 2, y la mas baja es -0.7
        //Formula Celsius a Fahrenheit = (Cx(9/5))+32
        //Formula Fahrenheit a Celsius = ((F-32)x5)/9
        //Para convertir la temperatura de minecraft a valores mas entendibles, multiplicaré el valor obtenido por 25, ej: 0.7*25= 17.5°C

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

    public static String getSimpleOutput(double temp) {
        if (temp < -0.4f) {
            return Text.translatable("item.demandingsaplings.thermometer.freezing").getString();
        }
        else if (temp < 0.2f) {
            return Text.translatable("item.demandingsaplings.thermometer.cold").getString();
        }
        else if (temp < 0.8f) {
            return Text.translatable("item.demandingsaplings.thermometer.temper").getString();
        }
        else if (temp < 1.4f) {
            return Text.translatable("item.demandingsaplings.thermometer.hot").getString();
        }
        else {
            return Text.translatable("item.demandingsaplings.thermometer.burning").getString();
        }
    }
}
