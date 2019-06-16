package org.sdoaj.eloncraft.util;

public class Random {
    private static final java.util.Random random = new java.util.Random();

    public static double nextDouble(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min is greater than max");
        }

        return random.nextDouble() * (max - min) + min;
    }
}
