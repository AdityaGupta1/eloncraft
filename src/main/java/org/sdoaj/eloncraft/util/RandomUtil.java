package org.sdoaj.eloncraft.util;

import java.util.Random;

public class RandomUtil {
    private static final java.util.Random random = new java.util.Random();

    public static double nextDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static double nextDouble(double maxAbsolute) {
        maxAbsolute = Math.abs(maxAbsolute);
        return nextDouble(-maxAbsolute, maxAbsolute);
    }

    // https://stackoverflow.com/questions/7213469/
    public static double randomFromXZ(int x, int z) {
        int n = x + z * 57;
        n = n << 13 ^ n;
        double value = 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
        return (value + 1.0) / 2.0;
    }

    public static boolean randomBooleanFromXZ(long seed, int x, int z, double chance) {
        final double value = randomFromXZ(x, z);

        final double random = new Random(seed).nextDouble();
        double min = random - chance / 2;
        double max = random + chance / 2;

        return (value > min && value < max) || (value - 1 > min && value - 1 < max);
    }
}
