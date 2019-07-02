// based on AssetUtil from Actually Additions

package org.sdoaj.eloncraft.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;

public final class AssetUtil {
    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("inventory");

    public static ResourceLocation getGuiLocation(String file) {
        return new ResourceLocation(Eloncraft.MODID, "textures/gui/" + file + ".png");
    }

    @SideOnly(Side.CLIENT)
    public static void displayNameString(FontRenderer fontRenderer, int xSize, int yPositionOfMachineText, String text) {
        fontRenderer.drawString(text, xSize / 2 - fontRenderer.getStringWidth(text) / 2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @SideOnly(Side.CLIENT)
    public static void displayNameString(FontRenderer fontRenderer, int xSize, int yPositionOfMachineText, TileEntityBase tile) {
        displayNameString(fontRenderer, xSize, yPositionOfMachineText, StringUtil.localize(tile.getNameForTranslation()));
    }

    // for all getColor methods with a parameter `float pos`, pos should be some number x % 256

    private static final double energyColorPeriod = 2;

    public static float[] getEnergyColor(float pos) {
        float blue = (float) (191 + 0.5 * (255 - 191) + 32 * Math.sin(energyColorPeriod * 2 * Math.PI * pos / 256));
        return new float[]{0.0f, 0.0f, blue};
    }
}