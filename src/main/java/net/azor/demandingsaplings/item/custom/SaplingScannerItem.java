package net.azor.demandingsaplings.item.custom;

import net.azor.demandingsaplings.init.ConfigInit;
import net.azor.demandingsaplings.util.ModTags;
import net.azor.demandingsaplings.util.SaplingKiller;
import net.azor.demandingsaplings.util.TemperatureHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class SaplingScannerItem extends Item {
    public SaplingScannerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if (!context.getWorld().isClient) {
            BlockPos clickPosition = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            Block sapling = context.getWorld().getBlockState(clickPosition).getBlock();

            ItemStack stack = player.getMainHandStack();

            float[] tempRange = getTemperatureRange(sapling);
            float min = Math.min(tempRange[0], tempRange[1]);
            float max = Math.max(tempRange[0], tempRange[1]);

            float minTemp = Math.max(min, -1f);
            float maxTemp = Math.min(max, 2.5f);

            if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT) && !player.isSneaking()) {

                if (getScannerModeNBTData(stack).isEmpty()) {
                    setscannerModeNBTData(stack, SaplingScannerItem.SCANNERMODES.SIMPLE.toString());
                }

                String minTempString = getInfo(minTemp, player, stack);
                String maxTempString = getInfo(maxTemp, player, stack);

                String temperatureString = minTempString + Text.translatable("item.demandingsaplings.sapling_scanner.reading_p3").getString() + maxTempString;

                outputTemperature(player, sapling, temperatureString);
                player.getItemCooldownManager().set(this, 2);
            }
            else if (sapling.getDefaultState().isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT) && player.isSneaking()) {
                //For chance detection
                float biomeTemperature = context.getWorld().getBiomeAccess().getBiome(clickPosition).value().getTemperature();
                float tempValue = TemperatureHandler.getTemperature(biomeTemperature, clickPosition);
                float difference;
                String chanceString;

                if (tempValue < minTemp) {
                    difference = Math.abs(tempValue - minTemp);
                    if (difference < 0.25) { //Chance of the sapling to still grow even if the temperature is outside its compatible range

                        chanceString = Text.translatable("item.demandingsaplings.sapling_scanner.maybe_chance").getString() +
                                Text.translatable("item.demandingsaplings.sapling_scanner.will_freeze").getString();

                    } else {
                        chanceString = Text.translatable("item.demandingsaplings.sapling_scanner.yes_chance").getString() +
                                Text.translatable("item.demandingsaplings.sapling_scanner.will_freeze").getString();
                    }
                    player.sendMessage(Text.literal(chanceString), true);
                }
                else if (tempValue > maxTemp) {
                    difference = Math.abs(tempValue - maxTemp);
                    if (difference < 0.25) { //Chance of the sapling to still grow even if the temperature is outside its compatible range

                        chanceString = Text.translatable("item.demandingsaplings.sapling_scanner.maybe_chance").getString() +
                                Text.translatable("item.demandingsaplings.sapling_scanner.will_burn").getString();

                    } else {
                        chanceString = Text.translatable("item.demandingsaplings.sapling_scanner.yes_chance").getString() +
                                Text.translatable("item.demandingsaplings.sapling_scanner.will_burn").getString();
                    }
                    player.sendMessage(Text.literal(chanceString), true);
                }
                else {
                    chanceString = Text.translatable("item.demandingsaplings.sapling_scanner.yes_chance").getString() +
                            Text.translatable("item.demandingsaplings.sapling_scanner.will_grow").getString();

                    player.sendMessage(Text.literal(chanceString), true);
                }
            }
        }

        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!world.isClient() && hand == Hand.MAIN_HAND && user.isSneaking()) { //Change the temperature reading mode
            if (getScannerModeNBTData(stack).isEmpty()) {
                setscannerModeNBTData(stack, SaplingScannerItem.SCANNERMODES.SIMPLE.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.simple").getString()), true);
            }

            if (getScannerModeNBTData(stack).equals(SaplingScannerItem.SCANNERMODES.SIMPLE.toString())) {
                setscannerModeNBTData(stack, SaplingScannerItem.SCANNERMODES.PRECISE_CELSIUS.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.precise_c").getString()), true);
            } else if (getScannerModeNBTData(stack).equals(SaplingScannerItem.SCANNERMODES.PRECISE_CELSIUS.toString())) {
                setscannerModeNBTData(stack, SaplingScannerItem.SCANNERMODES.PRECISE_FAHRENHEIT.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.precise_f").getString()), true);
            } else if (getScannerModeNBTData(stack).equals(SaplingScannerItem.SCANNERMODES.PRECISE_FAHRENHEIT.toString())) {
                setscannerModeNBTData(stack, SaplingScannerItem.SCANNERMODES.PRECISE_BOTH.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.precise_c_f").getString()), true);
            } else if (getScannerModeNBTData(stack).equals(SaplingScannerItem.SCANNERMODES.PRECISE_BOTH.toString())) {
                setscannerModeNBTData(stack, SaplingScannerItem.SCANNERMODES.SIMPLE.toString());
                user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.thermometer.simple").getString()), true);
            }

            user.getItemCooldownManager().set(this, 2);
        }
        else if (!world.isClient() && hand == Hand.MAIN_HAND && !user.isSneaking()) {
            user.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.sapling_scanner.tip_to_use").getString()), true);
            user.getItemCooldownManager().set(this, 2);
        }
        return super.use(world, user, hand);
    }

    private void outputTemperature(PlayerEntity player, Block sapling , String temp) {
        player.sendMessage(Text.literal(Text.translatable("item.demandingsaplings.sapling_scanner.reading_p1").getString()
                + sapling.getName().getString()
                + Text.translatable("item.demandingsaplings.sapling_scanner.reading_p2").getString() + temp), true);
    }

    private String getInfo(float temp, PlayerEntity player, ItemStack stack) {

        DecimalFormat df = new DecimalFormat("#.##");
        //En Minecraft, la temperatura base mas alta de un bioma es 2, y la mas baja es -0.7
        //Formula Celsius a Fahrenheit = (Cx(9/5))+32
        //Formula Fahrenheit a Celsius = ((F-32)x5)/9
        //Para convertir la temperatura de minecraft a valores mas entendibles, multiplicaré el valor obtenido por 25, ej: 0.7*25= 17.5°C

        double tempCelsius = temp * 25;
        double tempFahrenheit = (tempCelsius*1.8)+32;

        //ALE AQUI AÑADÍ PARA QUE LA TEMPERATURA QUE APAREZCA CORRECTAMENTE DEPENDIENDO DEL MODO DEL ESCÁNER, ASI COMO CON EL TERMÓMETRO,
        //NAMÁS COPIA Y PEGA :V

        if (getScannerModeNBTData(stack).equals(SaplingScannerItem.SCANNERMODES.SIMPLE.toString())) {
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

        if (getScannerModeNBTData(stack).equals(SaplingScannerItem.SCANNERMODES.PRECISE_BOTH.toString())) {
            return df.format(tempCelsius) + "°C/" + df.format(tempFahrenheit) + "°F";
        }
        else if (getScannerModeNBTData(stack).equals(SaplingScannerItem.SCANNERMODES.PRECISE_CELSIUS.toString())) {
            return df.format(tempCelsius) + "°C";
        }
        else if (getScannerModeNBTData(stack).equals(SaplingScannerItem.SCANNERMODES.PRECISE_FAHRENHEIT.toString())) {
            return df.format(tempFahrenheit) + "°F";
        }
        else return "";
    }

    private void setscannerModeNBTData(ItemStack stack, String key) {
        stack.getOrCreateNbt().putString("SAPLINGSCANNER_MODE_KEY", key);
    }

    private static String getScannerModeNBTData(ItemStack stack) {
        if (!stack.getOrCreateNbt().contains("SAPLINGSCANNER_MODE_KEY")) {
            return "";
        }
        else
            return stack.getOrCreateNbt().getString("SAPLINGSCANNER_MODE_KEY");
    }
    private float[] getTemperatureRange(Block sapling) {

        float[] tempRange = {};
        if (sapling == Blocks.ACACIA_SAPLING) {
            tempRange = ConfigInit.CONFIG.ACACIARANGE;
        } else if (sapling == Blocks.BIRCH_SAPLING) {
            tempRange = ConfigInit.CONFIG.BIRCHRANGE;
        } else if (sapling == Blocks.CHERRY_SAPLING) {
            tempRange = ConfigInit.CONFIG.CHERRYRANGE;
        } else if (sapling == Blocks.DARK_OAK_SAPLING) {
            tempRange = ConfigInit.CONFIG.DARKOAKRANGE;
        } else if (sapling == Blocks.JUNGLE_SAPLING) {
            tempRange = ConfigInit.CONFIG.JUNGLERANGE;
        } else if (sapling == Blocks.MANGROVE_PROPAGULE) {
            tempRange = ConfigInit.CONFIG.MANGROVERANGE;
        } else if (sapling == Blocks.OAK_SAPLING) {
            tempRange = ConfigInit.CONFIG.OAKRANGE;
        } else if (sapling == Blocks.SPRUCE_SAPLING) {
            tempRange = ConfigInit.CONFIG.SPRUCERANGE;
        }
        else {
            tempRange = ConfigInit.CONFIG.DEFAULTRANGE;
        }
        return tempRange;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.demandingsaplings.sapling_scanner.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    public enum SCANNERMODES {
        SIMPLE,
        PRECISE_CELSIUS,
        PRECISE_FAHRENHEIT,
        PRECISE_BOTH
    }
}
