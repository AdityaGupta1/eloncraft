// based on GuiGrinder from Actually Additions

package org.sdoaj.items.blocks.machines.metalroller;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.items.blocks.machines.gui.GuiBase;
import org.sdoaj.items.blocks.machines.TileEntityBase;
import org.sdoaj.util.AssetUtil;

@SideOnly(Side.CLIENT)
public class GuiMetalRoller extends GuiBase {
    private static final ResourceLocation resourceLocation = AssetUtil.getGuiLocation("gui_metal_roller");
    private final TileEntityMetalRoller tileEntity;

    public GuiMetalRoller(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerMetalRoller(inventory, tile));
        this.tileEntity = (TileEntityMetalRoller) tile;
        this.xSize = 176;
        this.ySize = 93 + 86;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y) {
        AssetUtil.displayNameString(this.fontRenderer, this.xSize, -10, this.tileEntity);
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(AssetUtil.GUI_INVENTORY_LOCATION);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop + 93, 0, 0, 176, 86);

        this.mc.getTextureManager().bindTexture(resourceLocation);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, 176, 93);

        if (this.tileEntity.processTime > 0) {
            int i = this.tileEntity.getTimeToScale(23);
            this.drawTexturedModalRect(this.guiLeft + (80), this.guiTop + 40, 176, 0, 24, i);
        }
    }
}