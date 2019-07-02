package org.sdoaj.eloncraft.jei;

import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.util.StringUtil;

public abstract class RecipeCategory<T extends IRecipeWrapper> implements IRecipeCategory<T> {
    @Override
    public String getTitle() {
        return StringUtil.localize("container.jei." + getUid() + ".name");
    }

    @Override
    public String getModName() {
        return Eloncraft.NAME;
    }
}
