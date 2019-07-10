package org.sdoaj.eloncraft.recipes;

import java.util.ArrayList;
import java.util.List;

public abstract class MachineRecipes {
    private static final List<MachineRecipes> recipes = new ArrayList<>();

    protected MachineRecipes() {
        recipes.add(this);
    }

    protected abstract void initRecipes();

    public static void init() {
        recipes.forEach(MachineRecipes::initRecipes);
    }
}
