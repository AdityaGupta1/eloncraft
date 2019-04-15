// based on GuiWtfMojang (interesting name) from Actually Additions

package org.sdoaj.blocks.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public abstract class GuiBase extends GuiContainer {
    public GuiBase(Container inventorySlots) {
        super(inventorySlots);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}