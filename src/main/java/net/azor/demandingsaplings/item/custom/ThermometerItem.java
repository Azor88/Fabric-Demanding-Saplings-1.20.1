package net.azor.demandingsaplings.item.custom;

import net.azor.demandingsaplings.util.TemperatureHandler;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class ThermometerItem extends Item {
    public ThermometerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient() && hand == Hand.MAIN_HAND && !user.isSneaking()) {
            if (getThermometerModeNBTData(stack).isEmpty()) {
                setThermometerModeNBTData(stack, THERMOMETERMODES.SIMPLE.toString());
            }

            String temp = getTemperature(world, user, hand);
            outputTemperature(user, temp);

            user.getItemCooldownManager().set(this, 3);
        }
        else if (!world.isClient() && hand == Hand.MAIN_HAND && user.isSneaking()) { //Change the temperature reading mode
            if (getThermometerModeNBTData(stack).isEmpty()) {
                setThermometerModeNBTData(stack, THERMOMETERMODES.SIMPLE.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.simple").getString()), true);
            }

            if (getThermometerModeNBTData(stack).equals(THERMOMETERMODES.SIMPLE.toString())) {
                setThermometerModeNBTData(stack, THERMOMETERMODES.PRECISE_CELSIUS.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.precise_c").getString()), true);
            }
            else if (getThermometerModeNBTData(stack).equals(THERMOMETERMODES.PRECISE_CELSIUS.toString())) {
                setThermometerModeNBTData(stack, THERMOMETERMODES.PRECISE_FAHRENHEIT.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.precise_f").getString()), true);
            }
            else if (getThermometerModeNBTData(stack).equals(THERMOMETERMODES.PRECISE_FAHRENHEIT.toString())) {
                setThermometerModeNBTData(stack, THERMOMETERMODES.PRECISE_BOTH.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.precise_c_f").getString()), true);
            }
            else if (getThermometerModeNBTData(stack).equals(THERMOMETERMODES.PRECISE_BOTH.toString())) {
                setThermometerModeNBTData(stack, THERMOMETERMODES.SIMPLE.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.simple").getString()), true);
            }

            user.getItemCooldownManager().set(this, 2);
        }

        return super.use(world, user, hand);
    }

    private void setThermometerModeNBTData(ItemStack stack, String key) {
        stack.getOrCreateNbt().putString("THERMOMETER_MODE_KEY", key);
    }

    private static String getThermometerModeNBTData(ItemStack stack) {
        if (!stack.getOrCreateNbt().contains("THERMOMETER_MODE_KEY")) {
            return "";
        }
        else
            return stack.getOrCreateNbt().getString("THERMOMETER_MODE_KEY");
    }

    private void outputTemperature(PlayerEntity player, String temp) {
        player.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.reading").getString() + temp), true);
    }

    private String getTemperature(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getStackInHand(hand);
        DecimalFormat df = new DecimalFormat("#.##");

        Biome bioma = world.getBiomeAccess().getBiome(player.getBlockPos()).value();
        float tempBase = bioma.getTemperature();
        double tempBioma = TemperatureHandler.getTemperature(tempBase, player.getBlockPos());

        //En Minecraft, la temperatura base mas alta de un bioma es 2, y la mas baja es -0.7
        //Formula Celsius a Fahrenheit = (Cx(9/5))+32
        //Formula Fahrenheit a Celsius = ((F-32)x5)/9
        //Para convertir la temperatura de minecraft a valores mas entendibles, multiplicaré el valor obtenido por 25, ej: 0.7*25= 17.5°C

        double tempCelsius = tempBioma * 25;
        double tempFahrenheit = (tempCelsius*1.8)+32;

        if (getThermometerModeNBTData(stack).equals(THERMOMETERMODES.SIMPLE.toString())) {
            return TemperatureHandler.getSimpleOutput(tempBioma);
        }

        if (getThermometerModeNBTData(stack).equals(THERMOMETERMODES.PRECISE_BOTH.toString())) {
            return df.format(tempCelsius) + "°C/" + df.format(tempFahrenheit) + "°F";
        }
        else if (getThermometerModeNBTData(stack).equals(THERMOMETERMODES.PRECISE_CELSIUS.toString())) {
            return df.format(tempCelsius) + "°C";
        }
        else if (getThermometerModeNBTData(stack).equals(THERMOMETERMODES.PRECISE_FAHRENHEIT.toString())) {
            return df.format(tempFahrenheit) + "°F";
        }
        else return "";
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.demandingsaplings.thermometer.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public enum THERMOMETERMODES {
        SIMPLE,
        PRECISE_CELSIUS,
        PRECISE_FAHRENHEIT,
        PRECISE_BOTH
    }
}
