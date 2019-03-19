// based on AssetUtil from Actually Additions

package org.sdoaj.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.items.blocks.tileentities.TileEntityBase;

public final class AssetUtil {
    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("gui_inventory");

    public static ResourceLocation getGuiLocation(String file) {
        return new ResourceLocation(Main.MODID, "textures/gui/" + file + ".png");
    }

    @SideOnly(Side.CLIENT)
    public static void displayNameString(FontRenderer fontRenderer, int xSize, int yPositionOfMachineText, String text) {
        fontRenderer.drawString(text, xSize / 2 - fontRenderer.getStringWidth(text) / 2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @SideOnly(Side.CLIENT)
    public static void displayNameString(FontRenderer fontRenderer, int xSize, int yPositionOfMachineText, TileEntityBase tile) {
        displayNameString(fontRenderer, xSize, yPositionOfMachineText, StringUtil.localize(tile.getNameForTranslation()));
    }

    // for all getColor methods with a paramater `float pos`, pos should be some number x % 256

    public static float[] getWheelColor(float pos) {
        if (pos < 85.0f) {
            return new float[]{pos * 3.0F, 255.0f - pos * 3.0f, 0.0f};
        }

        if (pos < 170.0f) {
            return new float[]{255.0f - (pos -= 85.0f) * 3.0f, 0.0f, pos * 3.0f};
        }

        return new float[]{0.0f, (pos -= 170.0f) * 3.0f, 255.0f - pos * 3.0f};
    }

    private static final double energyColorPeriod = 2;

    public static float[] getEnergyColor(float pos) {
        float blue = (float) (191 + 0.5 * (255 - 191) + 32 * Math.sin(energyColorPeriod * 2 * Math.PI * pos / 256));
        return new float[]{0.0f, 0.0f, blue};
    }
}