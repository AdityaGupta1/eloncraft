// based on GuiGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.refinery;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.blocks.gui.EnergyDisplay;
import org.sdoaj.eloncraft.blocks.gui.FluidDisplay;
import org.sdoaj.eloncraft.blocks.gui.GuiBase;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;
import org.sdoaj.eloncraft.util.AssetUtil;

@SideOnly(Side.CLIENT)
public class GuiRefinery extends GuiBase {
    private static final ResourceLocation resourceLocation = AssetUtil.getGuiLocation("gui_refinery");
    private final TileEntityRefinery tileEntity;

    private EnergyDisplay energy;

    private FluidDisplay inputDisplay;
    private FluidDisplay outputDisplay;

    public GuiRefinery(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerRefinery(inventory, tile));
        this.tileEntity = (TileEntityRefinery) tile;
        this.xSize = 176;
        this.ySize = tileEntity.guiTopHeight + 86;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.energy = new EnergyDisplay(this.guiLeft - EnergyDisplay.WIDTH_OUTLINE, this.guiTop,
                this.tileEntity.getCustomEnergyStorage(), true, false);

        this.inputDisplay = new FluidDisplay(this.guiLeft + 55, this.guiTop + 8, tileEntity.inputTank);
        this.outputDisplay = new FluidDisplay(this.guiLeft + 103, this.guiTop + 8, tileEntity.outputTank);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        this.energy.drawOverlay(x, y);

        this.inputDisplay.drawOverlay(x, y);
        this.outputDisplay.drawOverlay(x, y);
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.tileEntity);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + tileEntity.guiTopHeight, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resourceLocation);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, tileEntity.guiTopHeight);

        if (this.tileEntity.guiShowProgress()) {
            this.drawTexturedModalRect(this.guiLeft + 73, this.guiTop + 8, 176, 0, 30, 85);
        }

        this.energy.draw();

        this.inputDisplay.draw();
        this.outputDisplay.draw();
    }
}