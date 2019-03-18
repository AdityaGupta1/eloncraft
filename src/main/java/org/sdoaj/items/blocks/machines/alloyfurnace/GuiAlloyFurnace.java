// based on GuiGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.alloyfurnace;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.items.blocks.machines.TileEntityBase;
import org.sdoaj.items.blocks.machines.gui.GuiBase;
import org.sdoaj.util.AssetUtil;

@SideOnly(Side.CLIENT)
public class GuiAlloyFurnace extends GuiBase {
    private static final ResourceLocation resourceLocation = AssetUtil.getGuiLocation("gui_alloy_furnace");
    private final TileEntityAlloyFurnace tileEntity;

    public GuiAlloyFurnace(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerAlloyFurnace(inventory, tile));
        this.tileEntity = (TileEntityAlloyFurnace) tile;
        this.xSize = 176;
        this.ySize = tileEntity.guiTopHeight + 86;
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

        if (this.tileEntity.processTime > 0) {
            int i = this.tileEntity.getTimeToScale(24);
            this.drawTexturedModalRect(this.guiLeft + 76, this.guiTop + 46, 180, 0, i, 26);
        }
    }
}