// based on GuiGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.generator;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.blocks.gui.EnergyDisplay;
import org.sdoaj.eloncraft.blocks.gui.GuiBase;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;
import org.sdoaj.eloncraft.util.AssetUtil;

@SideOnly(Side.CLIENT)
public class GuiGenerator extends GuiBase {
    private static final ResourceLocation resourceLocation = AssetUtil.getGuiLocation("generator");
    private final TileEntityGenerator tileEntity;

    private EnergyDisplay energy;

    public GuiGenerator(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerGenerator(inventory, tile));
        this.tileEntity = (TileEntityGenerator) tile;
        this.xSize = 176;
        this.ySize = tileEntity.guiTopHeight + 86;
    }

    @Override
    public void initGui() {
        super.initGui();
        this.energy = new EnergyDisplay(this.guiLeft + 95, this.guiTop + 8,
                this.tileEntity.getCustomEnergyStorage(), false, false);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);
        this.energy.drawOverlay(x, y);
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
            int i = this.tileEntity.getFuelUsedScaled(14);
            this.drawTexturedModalRect(this.guiLeft + 69, this.guiTop + 29 + i, 176, i, 14, 14 - i);
        }

        this.energy.draw();
    }
}