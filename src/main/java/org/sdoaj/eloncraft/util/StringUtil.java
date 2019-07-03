// based on StringUtil from Actually Additions

package org.sdoaj.eloncraft.util;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class StringUtil {
    public static final int DECIMAL_COLOR_WHITE = 16777215;

    /**
     * localizes a given String
     */
    @SideOnly(Side.CLIENT)
    public static String localize(String text) {
        return I18n.format(text);
    }

    public static String capitalizeFirstLetter(String string) {
        if (string.length() == 0) {
            return string;
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}