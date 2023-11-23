package net.azor.demandingsaplings.item.custom;

import net.azor.demandingsaplings.DemandingSaplings;
import net.azor.demandingsaplings.init.ConfigInit;
import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.TemperatureHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
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
        if (!world.isClient() && hand == Hand.MAIN_HAND) {
            String temp = getTemperature(world, user);
            outputTemperature(user, temp);

            user.getItemCooldownManager().set(this, 5);
        }

        return super.use(world, user, hand);
    }

    private void outputTemperature(PlayerEntity player, String temp) {
        player.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.reading").getString() + temp), true);
    }

    private String getTemperature(World world, PlayerEntity player) {
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

        if (ConfigInit.CONFIG.THERMOMETERDATA.getPreciseReading()) {
            if (tempBioma < -0.4f) {
                return Text.translatable("item.demandingsaplings.thermometer.freezing").getString();
            }
            else if (tempBioma < 0.2f) {
                return Text.translatable("item.demandingsaplings.thermometer.cold").getString();
            }
            else if (tempBioma < 0.8f) {
                return Text.translatable("item.demandingsaplings.thermometer.temper").getString();
            }
            else if (tempBioma < 1.4f) {
                return Text.translatable("item.demandingsaplings.thermometer.hot").getString();
            }
            else {
                return Text.translatable("item.demandingsaplings.thermometer.burning").getString();
            }
        }

        System.out.println(tempBioma + "=" + tempCelsius + " " + tempFahrenheit);

        switch (ConfigInit.CONFIG.THERMOMETERDATA.getReadingMode()) {
            case BOTH:
                return df.format(tempCelsius) + "°C/" + df.format(tempFahrenheit) + "°F";
            case CELSIUS:
                return df.format(tempCelsius) + "°C";
            case FAHRENHEIT:
                return df.format(tempFahrenheit) + "°F";
            default:
                return "";
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.demandingsaplings.thermometer.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
