package net.azor.demandingsaplings.util;

import net.azor.demandingsaplings.item.ModItems;
import net.minecraft.client.item.ClampedModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ModModelPredicateProvider {
    public static void registerModModels()
    {
        registerThermometer();
    }

    private static void registerThermometer()
    {
        ModelPredicateProviderRegistry.register(ModItems.THERMOMETER, new Identifier("temp_measurement"), (stack, world, livingEntity, seed) -> {
            Entity entity = livingEntity != null ? livingEntity : stack.getHolder();
            if (entity == null)
            {
                return 0.0f;
            }
            else
            {
                if (world == null && ((Entity)entity).getWorld() instanceof ClientWorld)
                {
                    world = (ClientWorld)((Entity)entity).getWorld();
                }

                if (world == null || livingEntity == null)
                {
                    return 0.0f;
                }
                else
                {
                    float posY = entity.getBlockY();
                    float temp = world.getBiomeAccess().getBiome(entity.getBlockPos()).value().getTemperature();


                    double tempBioma = 0;
                    double tempRestador = 0.00125;

                    if (posY <= 80)
                    {
                        tempBioma = temp;
                    }
                    else if (posY >= 81)
                    {
                        int altura = (int)posY - 81;
                        double totalResta = altura * tempRestador;
                        tempBioma = temp - totalResta;
                    }

                    if (tempBioma < -0.4)
                    {
                        System.out.println("congelado");
                        return 0;
                    }
                    else if (tempBioma >= -0.4 && tempBioma < 0.2)
                    {
                        System.out.println("frio");
                        return 1;
                    }
                    else if (tempBioma >= 0.2 && tempBioma < 0.8)
                    {
                        System.out.println("normal");
                        return 2;
                    }
                    else if (tempBioma >= 0.8 && tempBioma < 1.4)
                    {
                        System.out.println("caliente");
                        return 3;
                    }
                    else
                    {
                        System.out.println("ardiendo");
                        return 4;
                    }
                }
            }
        });
    }
}