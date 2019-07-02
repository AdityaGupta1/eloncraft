package org.sdoaj.eloncraft.blocks.machines.alloyfurnace;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.sdoaj.eloncraft.items.ModItems;
import org.sdoaj.eloncraft.recipes.IngredientStack;
import org.sdoaj.eloncraft.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public final class AlloyFurnaceRecipes {
    private static final List<AlloyFurnaceRecipe> recipes = new ArrayList<>();

    public static void addRecipe(AlloyFurnaceRecipe recipe) {
        recipes.add(recipe);
    }

    public static AlloyFurnaceRecipe getRecipeFromInput(List<ItemStack> input) {
        for (AlloyFurnaceRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    private static String[] ingotOrDust(String material) {
        String capitalized = StringUtil.capitalizeFirstLetter(material);
        return new String[]{"ingot" + capitalized, "dust" + capitalized};
    }

    public static void init() {
        addRecipe(new AlloyFurnaceRecipe(new IngredientStack[]{
                new IngredientStack(Blocks.COAL_BLOCK, 1),
                new IngredientStack(9, ingotOrDust("iron"))
        }, new ItemStack(ModItems.STEEL_INGOT, 9)));



        addRecipe(new AlloyFurnaceRecipe(new IngredientStack[]{
                new IngredientStack(7, ingotOrDust("nickel")),
                new IngredientStack(2, ingotOrDust("chromium")),
                new IngredientStack(1, ingotOrDust("iron"))
        }, new ItemStack(ModItems.INCONEL_BARS, 10)));

        addRecipe(new AlloyFurnaceRecipe(new IngredientStack[]{
                new IngredientStack(8, ingotOrDust("aluminum")),
                new IngredientStack(1, "nuggetCopper"),
                new IngredientStack(1, "nuggetLithium")
        }, new ItemStack(ModItems.ALUMINUM_2198_INGOT, 8)));

        addRecipe(new AlloyFurnaceRecipe(new IngredientStack[]{
                new IngredientStack(8, ingotOrDust("aluminum")),
                new IngredientStack(1, ingotOrDust("zinc")),
                new IngredientStack(1, "nuggetMagnesium")
        }, new ItemStack(ModItems.ALUMINUM_7XXX_INGOT, 9)));

        addRecipe(new AlloyFurnaceRecipe(new IngredientStack[]{
                new IngredientStack(8, ingotOrDust("niobium")),
                new IngredientStack(1, ingotOrDust("hafnium")),
                new IngredientStack(1, "nuggetTitanium")
        }, new ItemStack(ModItems.NIOBIUM_C103_INGOT, 9)));



        addRecipe(new AlloyFurnaceRecipe(new IngredientStack[]{
                new IngredientStack(Blocks.QUARTZ_BLOCK, 3),
                new IngredientStack(Items.BLAZE_POWDER, 2),
                new IngredientStack(Items.CHORUS_FRUIT_POPPED, 2),
                new IngredientStack(Blocks.OBSIDIAN, 3)
        }, new ItemStack(ModItems.HEAT_SHIELD, 7)));
    }

    public static List<AlloyFurnaceRecipe> getRecipes() {
        return new ArrayList<>(recipes);
    }
}