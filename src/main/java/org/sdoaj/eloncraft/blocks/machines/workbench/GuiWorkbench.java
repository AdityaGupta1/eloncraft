// based on GuiGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.machines.workbench;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.blocks.gui.GuiBase;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;
import org.sdoaj.eloncraft.util.AssetUtil;

@SideOnly(Side.CLIENT)
public class GuiWorkbench extends GuiBase {
    private static final ResourceLocation resourceLocationTop = AssetUtil.getGuiLocation("gui_workbench_top");
    private static final ResourceLocation resourceLocationBottom = AssetUtil.getGuiLocation("gui_workbench_bottom");
    private final TileEntityWorkbench tileEntity;

    public GuiWorkbench(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerWorkbench(inventory, tile));
        this.tileEntity = (TileEntityWorkbench) tile;
        this.xSize = 249;
        this.ySize = 344;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.tileEntity);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(resourceLocationTop);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 249, 249);

        this.mc.getTextureManager().bindTexture(resourceLocationBottom);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 254, 0, 0, 235, 90);
    }
}