// based on GuiGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.loxcollector;

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
public class GuiLOXCollector extends GuiBase {
    private static final ResourceLocation resourceLocation = AssetUtil.getGuiLocation("lox_collector");
    private final TileEntityLOXCollector tileEntity;

    private EnergyDisplay energy;

    private FluidDisplay fluidDisplay;

    public GuiLOXCollector(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerLOXCollector(inventory, tile));
        this.tileEntity = (TileEntityLOXCollector) tile;
        this.xSize = 176;
        this.ySize = tileEntity.guiTopHeight + 86;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.energy = new EnergyDisplay(this.guiLeft - EnergyDisplay.WIDTH_OUTLINE, this.guiTop,
                this.tileEntity.getCustomEnergyStorage(), true, false);

        this.fluidDisplay = new FluidDisplay(this.guiLeft + 71, this.guiTop + 8, tileEntity.tank);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        this.energy.drawOverlay(x, y);

        this.fluidDisplay.drawOverlay(x, y);
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

        this.energy.draw();

        this.fluidDisplay.draw();
    }
}