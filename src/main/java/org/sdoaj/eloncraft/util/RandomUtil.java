package org.sdoaj.eloncraft.util;

public class RandomUtil {
    private static final java.util.Random random = new java.util.Random();

    public static double nextDouble(double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static double nextDouble(double maxAbsolute) {
        maxAbsolute = Math.abs(maxAbsolute);
        return nextDouble(-maxAbsolute, maxAbsolute);
    }
}
