package de.stream.processing.g6.util;

import java.util.Random;

public class RandomHelper {
    private static Random random = new Random();

    public static int getInteger(int min, int max){
        return random.nextInt(max - min + 1) + min;
    }

    public static double getDouble(double min, double max){
        return ((max - min) * random.nextDouble()) + min;
    }

    public static float getFloat(float min, float max){
        return ((max - min) * random.nextFloat()) + min;
    }

}
