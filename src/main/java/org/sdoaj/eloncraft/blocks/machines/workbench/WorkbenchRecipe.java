package org.sdoaj.eloncraft.blocks.machines.workbench;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.sdoaj.eloncraft.recipes.RecipeKey;
import org.sdoaj.eloncraft.util.StackUtil;

import java.util.Arrays;

public class WorkbenchRecipe {
    private Ingredient[][] inputs; // [y][x]
    private ItemStack output;

    public WorkbenchRecipe(String[] grid, ItemStack output, RecipeKey... keys) {
        int maxLength = Arrays.stream(grid).mapToInt(String::length).max().getAsInt();

        Ingredient[][] ingredients = new Ingredient[maxLength][grid.length];

        for (int i = 0; i < grid.length; i++) {
            String row = grid[i];

            for (int j = 0; j < maxLength; j++) {
                Ingredient ingredient = null;

                if (j < row.length()) {
                    char c = row.charAt(j);

                    if (c != ' ') {
                        for (RecipeKey key : keys) {
                            if (key.matches(c)) {
                                ingredient = key.get();
                            }
                        }

                        if (ingredient == null) {
                            throw new IllegalArgumentException("missing key for character '" + c + "' in " + output + " recipe");
                        }
                    }
                }

                ingredients[j][i] = ingredient;
            }
        }

        this.inputs = ingredients;
        System.out.println(Arrays.deepToString(inputs).replace("],", "]\n"));
        this.output = output.copy();
    }

    public boolean matches(ItemStack[][] stacks) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        boolean sizeMatches = false;
        for (int i = 0; i < stacks.length; i++) {
            for (int j = 0; j < stacks[i].length; j++) {
                if (!StackUtil.isValid(stacks[i][j])) {
                    continue;
                }

                sizeMatches = true;

                minX = Math.min(minX, i);
                maxX = Math.max(maxX, i);
                minY = Math.min(minY, j);
                maxY = Math.max(maxY, j);
            }
        }

        if (!sizeMatches) {
            return false;
        }

        if (maxX - minX + 1 != inputs.length || maxY - minY + 1 != inputs[0].length) {
            return false;
        }

        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[i].length; j++) {
                ItemStack stack = stacks[minX + i][minY + j];

                if (inputs[i][j] == null) {
                    if (StackUtil.isValid(stack)) {
                       return false;
                    }
                } else {
                    if (!StackUtil.isValid(stack)) {
                        return false;
                    }

                    if (!inputs[i][j].apply(stack)) {
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