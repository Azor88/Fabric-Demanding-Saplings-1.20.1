package net.azor.demandingsaplings.util;

import net.azor.demandingsaplings.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class SaplingKiller {
    public static void killSapling(ServerWorld world, BlockPos pos, float tempValue, float limitTemp, boolean colder) {
        float difference = Math.abs(tempValue - limitTemp);
        boolean dies = true;
        if (difference > 0.75f && colder) { //Absolute death of sapling
            world.setBlockState(pos, ModBlocks.FROZEN_BUSH.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, SoundCategory.BLOCKS);
        }
        else if (difference > 0.75f) { //Absolute death of sapling
            world.setBlockState(pos, Blocks.DEAD_BUSH.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, SoundCategory.BLOCKS);
        }
        else { //Absolute death of sapling
            world.setBlockState(pos, ModBlocks.DEAD_SAPLING.getDefaultState());
            world.playSound(null, pos, SoundEvents.BLOCK_CHERRY_SAPLING_BREAK, SoundCategory.BLOCKS);
        }
    }

    public static boolean getChance(float difference) {
        /*
        At a 0.05 difference, it has a 20% chance to die
        At a 0.10 difference, it has a 40% chance to die
        At a 0.15 difference, it has a 60% chance to die
        At a 0.20 difference, it has an 80% chance to die
        At a 0.25 difference, it has a 100% chance to die
        */
        Random rand = new Random();
        int output = rand.nextInt(100) + 1;
        int chance = 0;

        if (difference < 0.05f) {
            chance = 20;
        }
        else if (difference < 0.10f) {
            chance = 40;
        }
        else if (difference < 0.15f) {
            chance = 60;
        }
        else if (difference < 0.20f) {
            chance = 80;
        }
        else if (difference <= 0.25f) {
            chance = 99;
        }

        if (output <= chance) {
            return true;
        }
        else {
            return false;
        }
    }
}
