// based on GuiGrinder from Actually Additions

package org.sdoaj.eloncraft.blocks.launch.controller;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.sdoaj.eloncraft.blocks.gui.EnergyDisplay;
import org.sdoaj.eloncraft.blocks.gui.FluidDisplay;
import org.sdoaj.eloncraft.blocks.gui.GuiBase;
import org.sdoaj.eloncraft.blocks.gui.GuiCustomButton;
import org.sdoaj.eloncraft.blocks.tileentities.MessageButtonPressed;
import org.sdoaj.eloncraft.blocks.tileentities.TileEntityBase;
import org.sdoaj.eloncraft.entity.rocket.falcon9.EntityFalcon9Stage1;
import org.sdoaj.eloncraft.util.AssetUtil;
import org.sdoaj.eloncraft.util.PacketHandler;

@SideOnly(Side.CLIENT)
public class GuiLaunchController extends GuiBase {
    private static final ResourceLocation resourceLocation = AssetUtil.getGuiLocation("launch_controller");
    private final TileEntityLaunchController tileEntity;

    private EnergyDisplay energy;

    private FluidDisplay fuelDisplay;
    private FluidDisplay oxygenDisplay;

    private GuiCustomButton loadButton;
    private GuiCustomButton destinationPrevButton;
    private GuiCustomButton destinationNextButton;
    private GuiCustomButton launchButton;

    private FluidDisplay rocketFuelDisplay;
    private FluidDisplay rocketOxygenDisplay;

    public GuiLaunchController(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerLaunchController(inventory, tile));
        this.tileEntity = (TileEntityLaunchController) tile;
        this.xSize = 176;
        this.ySize = tileEntity.guiTopHeight + 86;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.energy = new EnergyDisplay(this.guiLeft - EnergyDisplay.WIDTH_OUTLINE, this.guiTop,
                this.tileEntity.getCustomEnergyStorage(), true, false);

        this.fuelDisplay = new FluidDisplay(this.guiLeft + 8, this.guiTop + 8, tileEntity.fuelTank);
        this.oxygenDisplay = new FluidDisplay(this.guiLeft + 31, this.guiTop + 8, tileEntity.oxygenTank);

        this.loadButton = new GuiCustomButton(0, this.guiLeft + 54, this.guiTop + 44, 13, 13, "", resourceLocation, 0, 99);
        loadButton.enabled = false;

        this.destinationPrevButton = new GuiCustomButton(1, this.guiLeft + 116, this.guiTop + 43, 10, 15, "", resourceLocation, 13, 99);
        this.destinationNextButton = new GuiCustomButton(2, this.guiLeft + 160, this.guiTop + 43, 10, 15, "", resourceLocation, 23, 99);

        this.launchButton = new GuiCustomButton(3, this.guiLeft + 127, this.guiTop + 78, 43, 15, "", resourceLocation, 33, 99);

        addButtons(loadButton, destinationPrevButton, destinationNextButton, launchButton);

        this.rocketFuelDisplay = new FluidDisplay(this.guiLeft + 72, this.guiTop + 8, null);
        this.rocketOxygenDisplay = new FluidDisplay(this.guiLeft + 95, this.guiTop + 8, null);
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        this.energy.drawOverlay(x, y);

        this.fuelDisplay.drawOverlay(x, y);
        this.oxygenDisplay.drawOverlay(x, y);

        this.rocketFuelDisplay.drawOverlay(x, y);
        this.rocketOxygenDisplay.drawOverlay(x, y);

        ErrorCode status = tileEntity.getLaunchStatus();
        if (status != ErrorCode.OK) {
            this.launchButton.drawOverlay(x, y, status.message);
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        loadButton.enabled = tileEntity.canProcessWithoutLoading() && tileEntity.hasEnergyForTick();*

        destinationPrevButton.enabled = tileEntity.getDestination().ordinal() != 0;
        destinationNextButton.enabled = tileEntity.getDestination().ordinal() != Destination.values().length - 1;

        EntityFalcon9Stage1 rocket = tileEntity.rocket;
        if (rocket != null) {
            rocketFuelDisplay.setTank(rocket.fuelTank);
            rocketOxygenDisplay.setTank(rocket.oxygenTank);
        } else {
            rocketFuelDisplay.setTank(null);
            rocketOxygenDisplay.setTank(null);
        }

        launchButton.enabled = tileEntity.getLaunchStatus() == ErrorCode.OK;
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

        this.drawTexturedModalRect(this.guiLeft + 116, this.guiTop + 8, 202, tileEntity.getDestination().ordinal() * 33, 54, 32);

        this.energy.draw();

        this.fuelDisplay.draw();
        this.oxygenDisplay.draw();

        this.rocketFuelDisplay.draw();
        this.rocketOxygenDisplay.draw();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        PacketHandler.sendToServer(new MessageButtonPressed(button.id));
    }
}