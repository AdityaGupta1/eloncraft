package org.sdoaj.items.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.sdoaj.eloncraft.Main;
import org.sdoaj.items.Drop;
import org.sdoaj.items.DropRange;
import org.sdoaj.items.Drops;
import org.sdoaj.items.blocks.machines.alloyfurnace.BlockAlloyFurnace;
import org.sdoaj.items.blocks.machines.crusher.BlockCrusher;
import org.sdoaj.items.blocks.machines.metalroller.BlockMetalRoller;
import org.sdoaj.items.blocks.machines.workbench.BlockWorkbench;
import org.sdoaj.items.items.ModItems;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModBlocks {
    private static final List<Block> blocks = new ArrayList<>();

    public static Block COMPONENTS;

    public static Block ALUMINUM_ORE;
    public static Block TITANIUM_ORE;
    public static Block LITHIUM_ORE;
    public static Block NICKEL_ORE;
    public static Block CHROMIUM_ORE;
    public static Block COPPER_ORE;
    public static Block NIOBIUM_ORE;
    public static Block HAFNIUM_ORE;
    public static Block MAGNESIUM_ORE;
    public static Block ZINC_ORE;

    public static BlockWorkbench ELON_WORKBENCH;

    public static BlockMetalRoller METAL_ROLLER;
    public static BlockCrusher CRUSHER;
    public static BlockAlloyFurnace ALLOY_FURNACE;

    public static void init() {
        COMPONENTS = new BlockOre("components", Material.IRON, new Drops(
                new Drop(new ItemStack(ModItems.BROKEN_STEEL_GEAR), new DropRange(2, 3), 1)))
                .setHardness(5.0F).setResistance(10.0F);
        COMPONENTS.setHarvestLevel("pickaxe", 2);

        ALUMINUM_ORE = new BlockOreStone("aluminum_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        TITANIUM_ORE = new BlockOreStone("titanium_ore").setHarvestLevel(3).setHardness(7.0F)
                .setResistance(20.0F);
        LITHIUM_ORE = new BlockOreStone("lithium_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        NICKEL_ORE = new BlockOreStone("nickel_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        CHROMIUM_ORE = new BlockOreStone("chromium_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        COPPER_ORE = new BlockOreStone("copper_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        NIOBIUM_ORE = new BlockOreStone("niobium_ore").setHarvestLevel(3).setHardness(5.0F)
                .setResistance(8.0F);
        HAFNIUM_ORE = new BlockOreStone("hafnium_ore").setHarvestLevel(3).setHardness(5.0F)
                .setResistance(8.0F);
        MAGNESIUM_ORE = new BlockOreStone("magnesium_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        ZINC_ORE = new BlockOreStone("zinc_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);

        ELON_WORKBENCH = new BlockWorkbench("elon_workbench", Material.IRON);
        ELON_WORKBENCH.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);

        METAL_ROLLER = new BlockMetalRoller("metal_roller", Material.IRON);
        METAL_ROLLER.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);
        CRUSHER = new BlockCrusher("crusher", Material.IRON);
        CRUSHER.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);
        CRUSHER.addLore("Pulverizer, SAG Mill, etc. - whatever you want to call it.");
        ALLOY_FURNACE = new BlockAlloyFurnace("alloy_furnace", Material.IRON);
        ALLOY_FURNACE.setHardness(20.0F).setResistance(40.0F).setHarvestLevel("pickaxe", 3);
        ALLOY_FURNACE.addLore("A somewhat unrealistic alloy furnace which can also serve as a blast furnace.");
    }

    public static void addBlock(Block block) {
        blocks.add(block);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        blocks.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        blocks.forEach(block -> registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName())));
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        blocks.forEach(block -> registerRender(Item.getItemFromBlock(block)));
    }
}
