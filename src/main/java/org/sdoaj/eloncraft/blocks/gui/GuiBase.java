// based on GuiWtfMojang (interesting name) from Actually Additions

package org.sdoaj.eloncraft.blocks.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.sdoaj.eloncraft.blocks.tileentities.MessageButtonPressed;
import org.sdoaj.eloncraft.util.PacketHandler;

import java.util.Arrays;

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

    protected void addButtons(GuiButton... buttons) {
        this.buttonList.addAll(Arrays.asList(buttons));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        PacketHandler.sendToServer(new MessageButtonPressed(button.id));
    }
}