package org.sdoaj.util;

public class MathUtil {
    public static double limit(double x, double min, double max) {
        return Math.min(Math.max(x, min), max);
    }
}
