// based on AssetUtil from Actually Additions

package org.sdoaj.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.items.blocks.machines.TileEntityBase;

public final class AssetUtil {
    public static final ResourceLocation GUI_INVENTORY_LOCATION = getGuiLocation("gui_inventory");

    public static ResourceLocation getGuiLocation(String file) {
        return new ResourceLocation(Main.MODID, "textures/gui/" + file + ".png");
    }

    @SideOnly(Side.CLIENT)
    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, String text) {
        font.drawString(text, xSize / 2 - font.getStringWidth(text) / 2, yPositionOfMachineText, StringUtil.DECIMAL_COLOR_WHITE);
    }

    @SideOnly(Side.CLIENT)
    public static void displayNameString(FontRenderer font, int xSize, int yPositionOfMachineText, TileEntityBase tile) {
        displayNameString(font, xSize, yPositionOfMachineText, StringUtil.localize(tile.getNameForTranslation()));
    }
}