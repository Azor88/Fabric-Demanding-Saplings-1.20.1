package net.azor.demandingsaplings.item.custom;

import net.azor.demandingsaplings.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class ThermometerItem extends Item {
    public ThermometerItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            BlockPos pos = context.getBlockPos();
            PlayerEntity player = context.getPlayer();

            BlockState state = context.getWorld().getBlockState(pos);

            if (SaplingFound(state)) {
                outputSaplingData(state.getBlock(), player);
                player.getItemCooldownManager().set(this, 10);
            }
            else {
                String temp = getTemperature(context.getWorld(), player);
                outputTemperature(player, temp);
                player.getItemCooldownManager().set(this, 5);
            }
        }

        return ActionResult.SUCCESS;
    }

    private boolean SaplingFound(BlockState state) {
        return state.isIn(ModTags.Blocks.TEMPERATURE_DEPENDANT);
    }

    private void outputSaplingData(Block block, PlayerEntity player) {
        player.sendMessage(Text.literal(block.asItem().getName().getString()));
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
        player.sendMessage(Text.literal("Temperature is: " + temp), true);
    }

    private String getTemperature(World world, PlayerEntity player) {
        DecimalFormat df = new DecimalFormat("#.##");

        float posX = (float)player.getX();
        float posY = (float)player.getY();
        float posZ = (float)player.getZ();

        Biome bioma = world.getBiomeAccess().getBiome(player.getBlockPos()).value();


        float tempBase = bioma.getTemperature();

        double tempBioma = 0;
        double tempRestador = 0.00125;

        if (posY <= 80) {
            tempBioma = tempBase;
        }
        else if (posY >= 81) {
            int altura = (int)posY - 81;
            double totalResta = altura * tempRestador;
            tempBioma = tempBase - totalResta;
        }

        //En Minecraft, la temperatura base mas alta de un bioma es 2, y la mas baja es -0.7
        //Formula Celsius a Fahrenheit = (Cx(9/5))+32
        //Formula Fahrenheit a Celsius = ((F-32)x5)/9
        //Para convertir la temperatura de minecraft a valores mas entendibles, multiplicaré el valor obtenido por 25, ej: 0.7*25= 17.5°C

        double tempCelsius = tempBioma * 25;
        double tempFahrenheit = (tempCelsius*1.8)+32;

        System.out.println(tempBioma + "=" + tempCelsius + " " + tempFahrenheit);

        //String coords = df.format(posX) + " " + df.format(posY) + " " + df.format(posZ);
        //String temperatura = "" + df.format(tempBioma);
        String temperatura = "" + df.format(tempCelsius) + "°C/" + df.format(tempFahrenheit) + "°F";

        return temperatura;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.demandingsaplings.thermometer.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
