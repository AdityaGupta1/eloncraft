package org.sdoaj.items.blocks;

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
import org.sdoaj.items.DropRange;
import org.sdoaj.items.Drops;
import org.sdoaj.items.ModCreativeTabs;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModBlocks {
    private static final List<BlockBasic> blocks = new ArrayList<>();

    public static Block components;

    public static Block aluminumOre;
    public static Block titaniumOre;
    public static Block lithiumOre;
    public static Block nickelOre;
    public static Block chromiumOre;

    public static void init() {
        components = new BlockOre("components", Material.IRON, new Drops(
                new Drop(new ItemStack(Items.REDSTONE), new DropRange(3, 5), 2),
                new Drop(new ItemStack(Items.REPEATER), new DropRange(1, 2), 1),
                new Drop(new ItemStack(Items.COMPARATOR), new DropRange(1, 2), 1)))
                .setHardness(5.0F).setResistance(10.0F);
        components.setHarvestLevel("pickaxe", 2);
        components.setCreativeTab(ModCreativeTabs.ELONCRAFT);

        aluminumOre = new BlockOreStone("aluminum_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        aluminumOre.setCreativeTab(ModCreativeTabs.ELONCRAFT);
        titaniumOre = new BlockOreStone("titanium_ore").setHarvestLevel(3).setHardness(3.0F)
                .setResistance(5.0F);
        titaniumOre.setCreativeTab(ModCreativeTabs.ELONCRAFT);
        lithiumOre = new BlockOreStone("lithium_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        lithiumOre.setCreativeTab(ModCreativeTabs.ELONCRAFT);
        nickelOre = new BlockOreStone("nickel_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        nickelOre.setCreativeTab(ModCreativeTabs.ELONCRAFT);
        chromiumOre = new BlockOreStone("chromium_ore").setHarvestLevel(2).setHardness(3.0F)
                .setResistance(5.0F);
        chromiumOre.setCreativeTab(ModCreativeTabs.ELONCRAFT);
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
