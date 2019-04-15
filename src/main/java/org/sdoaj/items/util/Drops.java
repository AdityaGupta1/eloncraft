package org.sdoaj.items.util;

import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Drops {
    private final Drop[] drops;

    public Drops(Drop... drops) {
        this.drops = drops;
    }

    public List<ItemStack> getDrops(int fortune) {
        return Arrays.stream(drops).map(drop -> drop.getRandomDrop(fortune)).collect(Collectors.toList());
    }
}
