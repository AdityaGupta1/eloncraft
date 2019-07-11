package org.sdoaj.eloncraft.util;

public class MathUtil {
    public static double random(double maxAbs) {
        return (Math.random() - 0.5) * 2 * maxAbs;
    }
}
