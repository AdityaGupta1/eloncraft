package org.sdoaj.items.util;

import org.sdoaj.util.Random;

public class DropRange {
    private final double min;
    private final double max;

    // both inclusive
    public DropRange(double min, double max) {
        this.min = min;
        this.max = max + 1;
    }

    public int getRandom() {
        return getRandom(0);
    }

    public int getRandom(double add) {
        return (int) Random.nextDouble(min, max + add);
    }
}
