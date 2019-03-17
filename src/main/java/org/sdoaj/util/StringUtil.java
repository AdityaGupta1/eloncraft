// based on StringUtil from Actually Additions

package org.sdoaj.util;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class StringUtil {
    public static final int DECIMAL_COLOR_WHITE = 16777215;

    /**
     * Localizes a given String
     */
    @SideOnly(Side.CLIENT)
    public static String localize(String text) {
        return I18n.format(text);
    }
}