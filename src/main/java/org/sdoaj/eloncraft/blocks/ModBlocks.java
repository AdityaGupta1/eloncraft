package org.sdoaj.eloncraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.sdoaj.eloncraft.blocks.launch.BlockLaunchpad;
import org.sdoaj.eloncraft.blocks.launch.controller.BlockLaunchController;
import org.sdoaj.eloncraft.blocks.machines.alloyfurnace.BlockAlloyFurnace;
import org.sdoaj.eloncraft.blocks.machines.crusher.BlockCrusher;
import org.sdoaj.eloncraft.blocks.machines.generator.BlockGenerator;
import org.sdoaj.eloncraft.blocks.machines.loxcollector.BlockLOXCollector;
import org.sdoaj.eloncraft.blocks.machines.metalroller.BlockMetalRoller;
import org.sdoaj.eloncraft.blocks.machines.refinery.BlockRefinery;
import org.sdoaj.eloncraft.blocks.machines.workbench.BlockWorkbench;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.blocks.pipes.BlockCable;
import org.sdoaj.eloncraft.items.ModCreativeTabs;
import org.sdoaj.eloncraft.items.ModItems;
import org.sdoaj.eloncraft.items.util.Drop;
import org.sdoaj.eloncraft.items.util.Drops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class ModBlocks {
    private static final List<Block> blocks = new ArrayList<>();
    private static final HashMap<Block, String> oreDictEntries = new HashMap<>();

    // ores

    public static BlockBasic COMPONENTS;

    public static BlockBasic ALUMINUM_ORE;
    public static BlockBasic TITANIUM_ORE;
    public static BlockBasic LITHIUM_ORE;
    public static BlockBasic NICKEL_ORE;
    public static BlockBasic CHROMIUM_ORE;
    public static BlockBasic COPPER_ORE;
    public static BlockBasic NIOBIUM_ORE;
    public static BlockBasic HAFNIUM_ORE;
    public static BlockBasic MAGNESIUM_ORE;
    public static BlockBasic ZINC_ORE;

    public static BlockBasic ALUMINUM_BLOCK;
    public static BlockBasic TITANIUM_BLOCK;
    public static BlockBasic LITHIUM_BLOCK;
    public static BlockBasic NICKEL_BLOCK;
    public static BlockBasic CHROMIUM_BLOCK;
    public static BlockBasic COPPER_BLOCK;
    public static BlockBasic NIOBIUM_BLOCK;
    public static BlockBasic HAFNIUM_BLOCK;
    public static BlockBasic MAGNESIUM_BLOCK;
    public static BlockBasic ZINC_BLOCK;

    // alloys

    public static BlockBasic STEEL_BLOCK;

    // machines

    public static BlockWorkbench ELON_WORKBENCH;

    public static BlockMetalRoller METAL_ROLLER;
    public static BlockCrusher CRUSHER;
    public static BlockAlloyFurnace ALLOY_FURNACE;
    public static BlockRefinery REFINERY;
    public static BlockLOXCollector LOX_COLLECTOR;

    public static BlockGenerator GENERATOR;

    // pipes

    public static BlockCable CABLE;

    // launch

    public static BlockLaunchController LAUNCH_CONTROLLER;
    public static BlockLaunchpad LAUNCHPAD;

    // moon

    public static BlockBasic MOON_DIRT;
    public static BlockBasic MOON_ROCK;

    public static void init() {
        COMPONENTS = new BlockOre("components", Material.IRON, new Drops(
                new Drop(ModItems.BROKEN_STEEL_GEAR, 1, 3, 1),
                new Drop(ModItems.DAMAGED_AIRCRAFT_PLATING, 0, 1, 0.5),
                new Drop(ModItems.NICKEL_NUGGET, 1, 3, 1),
                new Drop(ModItems.LITHIUM_NUGGET, 1, 2, 1),
                new Drop(Items.IRON_NUGGET, 1, 3, 1.5)));
        COMPONENTS.setHardness(5.0F).setResistance(10.0F).setHarvestLevel("pickaxe", 2);

        ALUMINUM_ORE = BlockOre.newStoneOre("aluminum", 2, 3, 8);
        TITANIUM_ORE = BlockOre.newStoneOre("titanium", 3, 5, 15);
        LITHIUM_ORE = BlockOre.newStoneOre("lithium", 2, 3, 8);
        NICKEL_ORE = BlockOre.newStoneOre("nickel", 2, 5, 12);
        CHROMIUM_ORE = BlockOre.newStoneOre("chromium", 2, 3, 8);
        COPPER_ORE = BlockOre.newStoneOre("copper", 2, 3, 8);
        NIOBIUM_ORE = BlockOre.newStoneOre("niobium", 3, 4, 10);
        HAFNIUM_ORE = BlockOre.newStoneOre("hafnium", 3, 4, 12);
        MAGNESIUM_ORE = BlockOre.newStoneOre("magnesium", 2, 3, 8);
        ZINC_ORE = BlockOre.newStoneOre("zinc", 2, 3, 8);

        ALUMINUM_BLOCK = BlockBasic.newMetalBlock("aluminum", 2, 5, 16);
        TITANIUM_BLOCK = BlockBasic.newMetalBlock("titanium", 3, 8, 30);
        LITHIUM_BLOCK = BlockBasic.newMetalBlock("lithium", 2, 5, 16);
        NICKEL_BLOCK = BlockBasic.newMetalBlock("nickel", 2, 8, 24);
        CHROMIUM_BLOCK = BlockBasic.newMetalBlock("chromium", 2, 5, 16);
        COPPER_BLOCK = BlockBasic.newMetalBlock("copper", 2, 5, 16);
        NIOBIUM_BLOCK = BlockBasic.newMetalBlock("niobium", 3, 6, 20);
        HAFNIUM_BLOCK = BlockBasic.newMetalBlock("hafnium", 3, 6, 24);
        MAGNESIUM_BLOCK = BlockBasic.newMetalBlock("magnesium", 2, 5, 16);
        ZINC_BLOCK = BlockBasic.newMetalBlock("zinc", 2, 5, 16);

        STEEL_BLOCK = BlockBasic.newMetalBlock("steel", 2, 8, 35);

        ELON_WORKBENCH = new BlockWorkbench("elon_workbench", Material.IRON);
        ELON_WORKBENCH.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);

        METAL_ROLLER = new BlockMetalRoller("metal_roller", Material.IRON);
        METAL_ROLLER.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);
        METAL_ROLLER.addLore("Turns metal ingots into metal plates.");
        METAL_ROLLER.setHarvestLevel("pickaxe", 2);
        CRUSHER = new BlockCrusher("crusher", Material.IRON);
        CRUSHER.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);
        CRUSHER.addLore("Pulverizer, SAG Mill, etc. - whatever you want to call it.");
        CRUSHER.setHarvestLevel("pickaxe", 2);
        ALLOY_FURNACE = new BlockAlloyFurnace("alloy_furnace", Material.IRON);
        ALLOY_FURNACE.setHardness(20.0F).setResistance(40.0F).setHarvestLevel("pickaxe", 3);
        ALLOY_FURNACE.addLore("A somewhat unrealistic alloy furnace that can also serve as a blast furnace.");
        ALLOY_FURNACE.setHarvestLevel("pickaxe", 2);
        REFINERY = new BlockRefinery("refinery", Material.IRON);
        REFINERY.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);
        REFINERY.addLore("Used to refine petroleum oil into rocket fuel (which can't melt steel beams).");
        REFINERY.setHarvestLevel("pickaxe", 2);
        LOX_COLLECTOR = new BlockLOXCollector("lox_collector", Material.IRON);
        LOX_COLLECTOR.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);
        LOX_COLLECTOR.addLore("Condenses and purifies air into liquid oxygen.");
        LOX_COLLECTOR.setHarvestLevel("pickaxe", 2);

        GENERATOR = new BlockGenerator("generator", Material.IRON);
        GENERATOR.setHardness(10.0F).setResistance(25.0F).setHarvestLevel("pickaxe", 3);
        GENERATOR.addLore("Generates energy from coal and charcoal.");
        GENERATOR.setHarvestLevel("pickaxe", 3);

        CABLE = new BlockCable("cable", Material.CLOTH);
        CABLE.setHardness(0.5F).setResistance(4.0F);
        CABLE.setHarvestLevel("", 0);
        CABLE.setSound(SoundType.CLOTH);

        LAUNCH_CONTROLLER = new BlockLaunchController("launch_controller", Material.IRON);
        LAUNCH_CONTROLLER.setHardness(20.0F).setResistance(50.0F).setHarvestLevel("pickaxe", 2);
        LAUNCH_CONTROLLER.addLore("Used to load fuel into rockets and to initiate launch sequences.");
        LAUNCHPAD = new BlockLaunchpad("launchpad", Material.IRON);
        LAUNCHPAD.setHardness(5.0F).setResistance(10.0F).setHarvestLevel("pickaxe", 2);

        MOON_DIRT = new BlockBasic("moon_dirt", Material.ROCK);
        MOON_DIRT.setHardness(1.0F).setResistance(10.0F).setHarvestLevel("pickaxe", -1);
        MOON_ROCK = new BlockBasic("moon_rock", Material.ROCK);
        MOON_ROCK.setHardness(1.5F).setResistance(30.0F).setHarvestLevel("pickaxe", -1);

        ModCreativeTabs.SPACEX.addAll(REFINERY, LOX_COLLECTOR, LAUNCH_CONTROLLER, LAUNCHPAD, MOON_DIRT,
                MOON_ROCK);
    }

    public static void addBlock(Block block) {
        blocks.add(block);
    }

    static void setOreDictName(Block block, String name) {
        oreDictEntries.put(block, name);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> registry = event.getRegistry();
        blocks.forEach(registry::register);
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        blocks.forEach(block -> {
            ItemBlock itemBlock = new ItemBlock(block);

            if (block instanceof BlockBasic) {
                itemBlock = ((BlockBasic) block).getItemBlock();
            }

            registry.register(itemBlock.setRegistryName(block.getRegistryName()));
        });

        oreDictEntries.keySet().forEach(block -> OreDictionary.registerOre(oreDictEntries.get(block), block));
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        blocks.forEach(block -> registerRender(Item.getItemFromBlock(block)));
    }
}
