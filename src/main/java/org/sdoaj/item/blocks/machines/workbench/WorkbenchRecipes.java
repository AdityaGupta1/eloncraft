package org.sdoaj.item.blocks.machines.workbench;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.sdoaj.item.blocks.ModBlocks;
import org.sdoaj.item.blocks.machines.RecipeKey;
import org.sdoaj.item.items.ModItems;

import java.util.ArrayList;
import java.util.List;

public final class WorkbenchRecipes {
    private static final List<WorkbenchRecipe> recipes = new ArrayList<>();

    public static void addRecipe(WorkbenchRecipe recipe) {
        recipes.add(recipe);
    }

    public static WorkbenchRecipe getRecipeFromInput(ItemStack[][] input) {
        for (WorkbenchRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public static void init() {
        addRecipe(new WorkbenchRecipe(new String[]{
                "p p p",
                " ppp ",
                "pptpp",
                " ppp ",
                "p p p"
        }, new ItemStack(ModItems.TITANIUM_FAN),
                new RecipeKey('t', ModItems.TITANIUM_INGOT),
                new RecipeKey('p', ModItems.TITANIUM_PLATE)));



        addRecipe(new WorkbenchRecipe(new String[]{
                "ttttt",
                "tPPPt",
                "tbbbt",
                "t p t",
                "BBBBB"
        }, new ItemStack(ModBlocks.METAL_ROLLER),
                new RecipeKey('t', ModItems.TITANIUM_INGOT),
                new RecipeKey('P', Blocks.STICKY_PISTON),
                new RecipeKey('b', Blocks.IRON_BLOCK),
                new RecipeKey('B', Blocks.OBSIDIAN),
                new RecipeKey('p', Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE)));

        addRecipe(new WorkbenchRecipe(new String[]{
                "ttttt",
                "tPfPt",
                "tf ft",
                "tPfPt",
                "BBBBB"
        }, new ItemStack(ModBlocks.CRUSHER),
                new RecipeKey('t', ModItems.TITANIUM_INGOT),
                new RecipeKey('f', Items.FLINT),
                new RecipeKey('P', Blocks.PISTON),
                new RecipeKey('B', Blocks.OBSIDIAN)));

        addRecipe(new WorkbenchRecipe(new String[]{
                "cst   tsc",
                "cst   tsc",
                "cst   tsc",
                "cst   tsc",
                "cst   tsc",
                "TstttttsT",
                "TsssssssT",
                "TTTTTTTTT"
        }, new ItemStack(ModBlocks.ALLOY_FURNACE),
                new RecipeKey('t', Blocks.HARDENED_CLAY),
                new RecipeKey('s', ModItems.STEEL_PLATE),
                new RecipeKey('c', ModItems.COPPER_INGOT),
                new RecipeKey('T', ModItems.TITANIUM_PLATE)));



        addRecipe(new WorkbenchRecipe(new String[]{
                "  tcr     ",
                "  nnn     ",
                " n   pPp  ",
                " n   n I  ",
                " n   nPp  ",
                "  n n p   ",
                "  n n pAAA",
                " n   n pPA",
                "ppppppppIA",
                "n     n   ",
                "n     n   ",
                "n     n   "
        }, new ItemStack(ModItems.MERLIN_ENGINE),
                new RecipeKey('n', ModItems.NIOBIUM_C103_PLATE),
                new RecipeKey('p', ModItems.FUEL_PIPE),
                new RecipeKey('P', ModItems.TITANIUM_FAN),
                new RecipeKey('A', ModItems.ALUMINUM_2198_PLATE),
                new RecipeKey('I', ModItems.INCONEL_PLATE),
                new RecipeKey('t', Blocks.REDSTONE_TORCH),
                new RecipeKey('c', Items.COMPARATOR),
                new RecipeKey('r', Items.REPEATER)));

        addRecipe(new WorkbenchRecipe(new String[]{
                "    ppppp    ",
                "  ppppppppp  ",
                " pppppeppppp ",
                " ppepppppepp ",
                "ppppppppppppp",
                "ppppppppppppp",
                "ppepppepppepp",
                "ppppppppppppp",
                "ppppppppppppp",
                " ppepppppepp ",
                " pppppeppppp ",
                "  ppppppppp  ",
                "    ppppp    "
        }, new ItemStack(ModItems.OCTAWEB),
                new RecipeKey('p', ModItems.ALUMINUM_7XXX_PLATE),
                new RecipeKey('e', ModItems.MERLIN_ENGINE)));

        addRecipe(new WorkbenchRecipe(new String[]{
                "ccc        ",
                "caPa       ",
                "cac ac     ",
                "cac cac    ",
                "cac  cac   ",
                "cac   cac  ",
                "cac    cac ",
                "ccc     ccc"
        }, new ItemStack(ModItems.LANDING_LEG),
                new RecipeKey('c', ModItems.CARBON_FIBER_PLATE),
                new RecipeKey('a', ModItems.ALUMINUM_PLATE),
                new RecipeKey('P', Blocks.PISTON)));

        addRecipe(new WorkbenchRecipe(new String[]{
                "p  ppp  p",
                "p pp pp p",
                "ppp   ppp",
                "pp     pp",
                "pp     pp",
                "pp     pp",
                "pp     pp",
                "pp     pp",
                "pp     pp",
                "ppp   ppp",
                "p pp pp p",
                "p  ppp  p"
        }, new ItemStack(ModItems.FUEL_TANK_LOX_1),
                new RecipeKey('p', ModItems.ALUMINUM_2198_PLATE)));

        addRecipe(new WorkbenchRecipe(new String[]{
                "p  ppP  p",
                "p pp Pp p",
                "ppp  Pppp",
                "pp   P pp",
                "pp   P pp",
                "pp   P pp",
                "pp  P  pp",
                "pp  P  pp",
                "pp  P  pp",
                "ppp P ppp",
                "p ppPpp p",
                "p  pPp  p"
        }, new ItemStack(ModItems.FUEL_TANK_RP1_1),
                new RecipeKey('p', ModItems.ALUMINUM_2198_PLATE),
                new RecipeKey('P', ModItems.FUEL_PIPE)));

        addRecipe(new WorkbenchRecipe(new String[]{
                "  ipi ",
                " iiiii",
                "ip p pi",
                "i p p i",
                "ip p pi",
                "i p p i",
                "iiiiiii",
        }, new ItemStack(ModItems.GRID_FIN, 2),
                new RecipeKey('i', ModItems.TITANIUM_INGOT),
                new RecipeKey('p', ModItems.TITANIUM_PLATE)));

        addRecipe(new WorkbenchRecipe(new String[]{
                "  ipi ",
                " iiiii",
                "i p p i",
                "ip p pi",
                "i p p i",
                "ip p pi",
                "iiiiiii",
        }, new ItemStack(ModItems.GRID_FIN, 2),
                new RecipeKey('i', ModItems.TITANIUM_INGOT),
                new RecipeKey('p', ModItems.TITANIUM_PLATE)));

        addRecipe(new WorkbenchRecipe(new String[]{
                " cp     pc ",
                " cp     pc ",
                " cp     pc ",
                " cp     pc ",
                "fcpP   Ppcf",
                "fcpP   Ppcf"
        }, new ItemStack(ModItems.INTERSTAGE),
                new RecipeKey('p', ModItems.ALUMINUM_2198_PLATE),
                new RecipeKey('c', ModItems.CARBON_FIBER_PLATE),
                new RecipeKey('P', Blocks.PISTON),
                new RecipeKey('f', ModItems.GRID_FIN)));
    }
}