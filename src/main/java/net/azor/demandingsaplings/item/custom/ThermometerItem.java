package net.azor.demandingsaplings.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class ThermometerItem extends Item {
    public ThermometerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && hand == Hand.MAIN_HAND)
        {
            String temp = getTemperature(world, user);
            outputTemperature(user, temp);

            user.getItemCooldownManager().set(this, 5);
        }
        float celsius, fahrenheit;
        String temperature;

        return super.use(world, user, hand);
    }

    private void outputTemperature(PlayerEntity player, String temp)
    {
        player.sendMessage(Text.literal("Temperature is: " + temp));
    }

    private String getTemperature(World world, PlayerEntity player)
    {
        DecimalFormat df = new DecimalFormat("#.##");

        float posX = (float)player.getX();
        float posY = (float)player.getY();
        float posZ = (float)player.getZ();

        String coords = df.format(posX) + " - " + df.format(posY) + " - " + df.format(posZ);
        System.out.println(posX + " - " + posY + " - " + posZ);

        return coords;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.demandingsaplings.thermometer.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
