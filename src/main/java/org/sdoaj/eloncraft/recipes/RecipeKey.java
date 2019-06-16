package org.sdoaj.eloncraft.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

/**
 * maps a one-character key to an ItemStack - good for crafting table recipes
 */
public class RecipeKey {
    private final char key;
    private final Ingredient ingredient;

    public RecipeKey(char key, Ingredient ingredient) {
        this.key = key;
        this.ingredient = ingredient;
    }

    public RecipeKey(char key, Item item) {
        this(key, Ingredient.fromItem(item));
    }

    public RecipeKey(char key, Block block) {
        this(key, Ingredient.fromStacks(new ItemStack(block)));
    }

    public RecipeKey(char key, ItemStack... stacks) {
        this(key, Ingredient.fromStacks(stacks));
    }

    public RecipeKey(char key, String oreDictName) {
        this(key, new OreIngredient(oreDictName));
    }

    public boolean matches(char key) {
        return this.key == key;
    }

    public Ingredient get() {
        return ingredient;
    }
}
