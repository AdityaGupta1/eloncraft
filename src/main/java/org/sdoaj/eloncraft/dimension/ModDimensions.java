package org.sdoaj.eloncraft.dimension;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.DimensionManager;
import org.sdoaj.eloncraft.dimension.moon.WorldProviderMoon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModDimensions {
    private static final Map<String, DimensionType> dimensions = new HashMap<>();

    public static DimensionType MOON;

    private static final int firstId = -33;

    public static void init() {
        dimensions.put("earth", DimensionManager.getProviderType(0));

        int id = firstId;

        MOON = registerDimension("moon", id++, WorldProviderMoon.class);
    }

    private static DimensionType registerDimension(String name, int id, Class<? extends WorldProvider> worldProvider) {
        DimensionType dimension = DimensionType.register(name, "_" + name, id, worldProvider, false);
        DimensionManager.registerDimension(id, dimension);
        dimensions.put(name, dimension);
        return dimension;
    }

    public static DimensionType getByName(String name) {
        return dimensions.get(name);
    }

    public static List<String> getDimensionNames() {
        return new ArrayList<>(dimensions.keySet());
    }
}