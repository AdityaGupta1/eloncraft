package org.sdoaj.eloncraft.blocks.machines.workbench;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import org.sdoaj.eloncraft.recipes.RecipeKey;
import org.sdoaj.eloncraft.util.StackUtil;

import java.util.Arrays;

public class WorkbenchRecipe {
    private final Ingredient[][] inputs; // [y][x]
    private final ItemStack output;

    public WorkbenchRecipe(String[] grid, ItemStack output, RecipeKey... keys) {
        int maxLength = Arrays.stream(grid).mapToInt(String::length).max().getAsInt();

        Ingredient[][] ingredients = new Ingredient[maxLength][grid.length];

        for (int y = 0; y < grid.length; y++) {
            String row = grid[y];

            for (int x = 0; x < maxLength; x++) {
                Ingredient ingredient = null;

                if (x < row.length()) {
                    char c = row.charAt(x);

                    if (c != ' ') {
                        for (RecipeKey key : keys) {
                            if (key.matches(c)) {
                                ingredient = key.get();
                                break;
                            }
                        }

                        if (ingredient == null) {
                            throw new IllegalArgumentException("missing key for character '" + c + "' in " + output + " recipe");
                        }
                    }
                }

                ingredients[x][y] = ingredient;
            }
        }

        this.inputs = ingredients;
        this.output = output.copy();
    }

    WorkbenchRecipe(ItemStack output) {
        this.inputs = null;
        this.output = output;
    }

    static WorkbenchRecipe[] createMultiple(String[][] grids, ItemStack output, RecipeKey... keys) {
        return Arrays.stream(grids).map(grid -> new WorkbenchRecipe(grid, output, keys)).toArray(WorkbenchRecipe[]::new);
    }

    static WorkbenchRecipe[] createMultiple(GridWithOutput[] grids, RecipeKey... keys) {
        return Arrays.stream(grids).map(grid -> new WorkbenchRecipe(grid.grid, grid.output, keys)).toArray(WorkbenchRecipe[]::new);
    }

    public boolean matches(ItemStack[][] stacks) {
        if (inputs == null) {
            return false;
        }

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        boolean sizeMatches = false;
        for (int x = 0; x < stacks.length; x++) {
            for (int y = 0; y < stacks[x].length; y++) {
                if (!StackUtil.isValid(stacks[x][y])) {
                    continue;
                }

                sizeMatches = true;

                minX = Math.min(minX, x);
                maxX = Math.max(maxX, x);
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }
        }

        if (!sizeMatches) {
            return false;
        }

        if (maxX - minX + 1 != inputs.length || maxY - minY + 1 != inputs[0].length) {
            return false;
        }

        for (int x = 0; x < inputs.length; x++) {
            for (int y = 0; y < inputs[x].length; y++) {
                ItemStack stack = stacks[minX + x][minY + y];

                if (inputs[x][y] == null) {
                    if (StackUtil.isValid(stack)) {
                       return false;
                    }
                } else {
                    if (!StackUtil.isValid(stack)) {
                        return false;
                    }

                    if (!inputs[x][y].apply(stack)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public Ingredient[][] getInputs() {
        return Arrays.stream(this.inputs).map(Ingredient[]::clone).toArray(Ingredient[][]::new);
    }

    public int getHeight() {
        return getInputs()[0].length;
    }

    public int getWidth() {
        return getInputs().length;
    }

    public ItemStack getOutput() {
        return this.output.copy();
    }
}

class GridWithOutput {
    String[] grid;
    ItemStack output;

    public GridWithOutput(ItemStack output, String... grid) {
        this.grid = grid;
        this.output = output;
    }
}