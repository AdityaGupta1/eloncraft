package org.sdoaj.eloncraft.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableStatic;
import org.sdoaj.eloncraft.util.AssetUtil;

public class Drawables {
    private static Drawables instance;

    static void init(IGuiHelper helper) {
        instance = new Drawables(helper);
    }

    public static Drawables getInstance() {
        return instance;
    }

    public final IDrawableStatic FLUID_LINES;

    Drawables(IGuiHelper helper) {
        FLUID_LINES = helper.createDrawable(AssetUtil.getGuiLocation("inventory"), 1, 87, 16, 83);
    }
}
