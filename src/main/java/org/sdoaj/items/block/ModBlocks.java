package org.sdoaj.items.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
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
import org.sdoaj.items.Drops;
import org.sdoaj.items.ModCreativeTabs;
import org.sdoaj.items.DropRange;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModBlocks {
    private static final List<BlockBasic> blocks = new ArrayList<>();

    public static Block aluminum_ore;
    public static Block titanium_ore;
    public static Block components;

    public static void init() {
        aluminum_ore = new BlockOre("aluminum_ore", Material.ROCK).setHardness(3.0F).setResistance(5.0F)
                .setCreativeTab(ModCreativeTabs.ELONCRAFT);
        aluminum_ore.setHarvestLevel("pickaxe", 1);
        titanium_ore = new BlockOre("titanium_ore", Material.ROCK).setHardness(3.0F).setResistance(5.0F)
                .setCreativeTab(ModCreativeTabs.ELONCRAFT);
        titanium_ore.setHarvestLevel("pickaxe", 2);

        components = new BlockOre("components", Material.IRON, new Drops(
                new Drop(new ItemStack(Items.REDSTONE), new DropRange(3, 5), 2),
                new Drop(new ItemStack(Items.REPEATER), new DropRange(1, 2), 1),
                new Drop(new ItemStack(Items.COMPARATOR), new DropRange(1, 2), 1)))
                .setHardness(5.0F).setResistance(10.0F).setCreativeTab(ModCreativeTabs.ELONCRAFT);
        components.setHarvestLevel("pickaxe", 2);
    }

    static void addBlock(BlockBasic block) {
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
