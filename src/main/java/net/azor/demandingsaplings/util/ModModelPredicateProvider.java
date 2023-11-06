package net.azor.demandingsaplings.util;

import net.azor.demandingsaplings.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModModelPredicateProvider {
    public static void registerModModels()
    {
        registerThermometer();
    }

    private static void registerThermometer() {
        ModelPredicateProviderRegistry.register(ModItems.THERMOMETER, new Identifier("temp_measurement"), (stack, world, livingEntity, seed) -> {

            Entity entity = livingEntity != null ? livingEntity : stack.getHolder();
            if (entity == null) {
                return 0.0f;
            }

            if (world == null && ((Entity)entity).getWorld() instanceof ClientWorld) {
                world = (ClientWorld)((Entity)entity).getWorld();
            }

            if (world == null) {
                return 0.0f;
            }
            else {
                return getTempValue(world, entity);
            }
        });
    }

    private static float getTempValue(World world, Entity entity) {
        float posY = entity.getBlockY();
        float temp = world.getBiomeAccess().getBiome(entity.getBlockPos()).value().getTemperature();

        double tempBioma = 0;
        double tempRestador = 0.00125;

        if (posY <= 80) {
            tempBioma = temp;
        }
        else if (posY >= 81) {
            int altura = (int)posY - 81;
            double totalResta = altura * tempRestador;
            tempBioma = temp - totalResta;
        }

        if (tempBioma < -0.4f) {
            return 0.0f;
        }
        else if (tempBioma < 0.2f) {
            return 0.2f;
        }
        else if (tempBioma < 0.8f) {
            return 0.4f;
        }
        else if (tempBioma < 1.4f) {
            return 0.6f;
        }
        else {
            return 0.8f;
        }
    }
}
