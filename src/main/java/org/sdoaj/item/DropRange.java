package org.sdoaj.item;

import org.sdoaj.util.Random;

public class DropRange {
    private final double min;
    private final double max;

    // both inclusive
    public DropRange(double min, double max) {
        this.min = min;
        this.max = max + 1;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getRandom() {
        return getRandom(0);
    }

    public double getRandom(double add) {
        return Random.nextDouble(min, max + add);
    }
}
