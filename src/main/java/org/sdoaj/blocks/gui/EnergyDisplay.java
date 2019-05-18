/*
 * This file ("EnergyDisplay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package org.sdoaj.blocks.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.blocks.tileentities.CustomEnergyStorage;
import org.sdoaj.util.AssetUtil;
import org.sdoaj.util.StringUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class EnergyDisplay extends Gui {
    private CustomEnergyStorage storage;
    private int x;
    private int y;
    private boolean outline;
    private boolean drawTextNextTo;

    public static final int WIDTH_OUTLINE = 26;
    public static final int WIDTH_NO_OUTLINE = 18;
    public static final int HEIGHT_OUTLINE = 93;
    public static final int HEIGHT_NO_OUTLINE = 85;

    public EnergyDisplay(int x, int y, CustomEnergyStorage storage, boolean outline, boolean drawTextNextTo) {
        this.x = x;
        this.y = y;
        this.storage = storage;
        this.outline = outline;
        this.drawTextNextTo = drawTextNextTo;
    }

    public EnergyDisplay(int x, int y, CustomEnergyStorage storage) {
        this(x, y, storage, false, false);
    }

    public void draw() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);

        int barX = this.x;
        int barY = this.y;

        if (this.outline) {
            this.drawTexturedModalRect(this.x, this.y, 52, 163, WIDTH_OUTLINE, HEIGHT_OUTLINE);

            barX += 4;
            barY += 4;
        }
        this.drawTexturedModalRect(barX, barY, 18, 171, WIDTH_NO_OUTLINE, HEIGHT_NO_OUTLINE);

        if (this.storage.getEnergyStored() > 0) {
            int i = this.storage.getEnergyStored() * 83 / this.storage.getMaxEnergyStored();

            float[] color = AssetUtil.getEnergyColor(mc.world.getTotalWorldTime() % 256);
            GlStateManager.color(color[0] / 255F, color[1] / 255F, color[2] / 255F);
            this.drawTexturedModalRect(barX + 1, barY + 84 - i, 36, 172, 16, i);
            GlStateManager.color(1F, 1F, 1F);
        }

        if (this.drawTextNextTo) {
            this.drawString(mc.fontRenderer, this.getOverlayText(), barX + 25, barY + 78, StringUtil.DECIMAL_COLOR_WHITE);
        }
    }

    public void drawOverlay(int mouseX, int mouseY) {
        if (this.isMouseOver(mouseX, mouseY)) {
            Minecraft mc = Minecraft.getMinecraft();

            List<String> text = new ArrayList<>();
            text.add(this.getOverlayText());
            GuiUtils.drawHoveringText(text, mouseX, mouseY, mc.displayWidth, mc.displayHeight, -1, mc.fontRenderer);
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseY >= this.y && mouseX < this.x + (this.outline ? WIDTH_OUTLINE : WIDTH_NO_OUTLINE) && mouseY < this.y + (this.outline ? HEIGHT_OUTLINE : HEIGHT_NO_OUTLINE);
    }

    private String getOverlayText() {
        NumberFormat format = NumberFormat.getInstance();
        return String.format("%s/%s Pirate-Ninjas", format.format(this.storage.getEnergyStored()), format.format(this.storage.getMaxEnergyStored()));
    }
}