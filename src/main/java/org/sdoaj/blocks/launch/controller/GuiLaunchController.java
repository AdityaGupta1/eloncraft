// based on GuiGrinder from Actually Additions

package org.sdoaj.blocks.launch.controller;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.blocks.gui.EnergyDisplay;
import org.sdoaj.blocks.gui.FluidDisplay;
import org.sdoaj.blocks.gui.GuiBase;
import org.sdoaj.blocks.tileentities.TileEntityBase;
import org.sdoaj.util.AssetUtil;

@SideOnly(Side.CLIENT)
public class GuiLaunchController extends GuiBase {
    private static final ResourceLocation resourceLocation = AssetUtil.getGuiLocation("gui_launch_controller");
    private final TileEntityLaunchController tileEntity;

    private FluidDisplay fuelDisplay;
    private FluidDisplay oxygenDisplay;

    public GuiLaunchController(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerLaunchController(inventory, tile));
        this.tileEntity = (TileEntityLaunchController) tile;
        this.xSize = 176;
        this.ySize = tileEntity.guiTopHeight + 86;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.fuelDisplay = new FluidDisplay(this.guiLeft + 8, this.guiTop + 8, tileEntity.fuelTank);
        this.oxygenDisplay = new FluidDisplay(this.guiLeft + 31, this.guiTop + 8, tileEntity.oxygenTank);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        this.fuelDisplay.drawOverlay(x, y);
        this.oxygenDisplay.drawOverlay(x, y);
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
            this.drawTexturedModalRect(this.guiLeft + 93, this.guiTop + 43, 176, 0, 13, 14);
        }

        this.fuelDisplay.draw();
        this.oxygenDisplay.draw();
    }
}