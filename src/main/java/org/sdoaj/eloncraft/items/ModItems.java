package org.sdoaj.eloncraft.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.sdoaj.eloncraft.Eloncraft;
import org.sdoaj.eloncraft.items.tools.ItemCustomPickaxe;
import org.sdoaj.eloncraft.items.tools.ModToolMaterials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.sdoaj.eloncraft.items.ItemBasic.*;

@Mod.EventBusSubscriber(modid = Eloncraft.MODID)
public class ModItems {
    private static final List<Item> items = new ArrayList<>();
    private static final HashMap<Item, String> oreDictEntries = new HashMap<>();

    // metals from ores

    public static ItemBasic ALUMINUM_INGOT;
    public static ItemBasic TITANIUM_INGOT;
    public static ItemBasic LITHIUM_INGOT;
    public static ItemBasic NICKEL_INGOT;
    public static ItemBasic CHROMIUM_INGOT;
    public static ItemBasic COPPER_INGOT;
    public static ItemBasic NIOBIUM_INGOT;
    public static ItemBasic HAFNIUM_INGOT;
    public static ItemBasic MAGNESIUM_INGOT;
    public static ItemBasic ZINC_INGOT;

    public static ItemBasic ALUMINUM_NUGGET;
    public static ItemBasic TITANIUM_NUGGET;
    public static ItemBasic LITHIUM_NUGGET;
    public static ItemBasic NICKEL_NUGGET;
    public static ItemBasic CHROMIUM_NUGGET;
    public static ItemBasic COPPER_NUGGET;
    public static ItemBasic NIOBIUM_NUGGET;
    public static ItemBasic HAFNIUM_NUGGET;
    public static ItemBasic MAGNESIUM_NUGGET;
    public static ItemBasic ZINC_NUGGET;

    public static ItemBasic IRON_PLATE;
    public static ItemBasic GOLD_PLATE;
    public static ItemBasic ALUMINUM_PLATE;
    public static ItemBasic TITANIUM_PLATE;
    public static ItemBasic LITHIUM_PLATE;
    public static ItemBasic NICKEL_PLATE;
    public static ItemBasic CHROMIUM_PLATE;
    public static ItemBasic COPPER_PLATE;
    public static ItemBasic NIOBIUM_PLATE;
    public static ItemBasic HAFNIUM_PLATE;
    public static ItemBasic MAGNESIUM_PLATE;
    public static ItemBasic ZINC_PLATE;

    public static ItemBasic IRON_DUST;
    public static ItemBasic GOLD_DUST;
    public static ItemBasic ALUMINUM_DUST;
    public static ItemBasic TITANIUM_DUST;
    public static ItemBasic LITHIUM_DUST;
    public static ItemBasic NICKEL_DUST;
    public static ItemBasic CHROMIUM_DUST;
    public static ItemBasic COPPER_DUST;
    public static ItemBasic NIOBIUM_DUST;
    public static ItemBasic HAFNIUM_DUST;
    public static ItemBasic MAGNESIUM_DUST;
    public static ItemBasic ZINC_DUST;

    // alloys

    public static ItemBasic STEEL_INGOT;

    public static ItemBasic STEEL_NUGGET;

    public static ItemBasic STEEL_PLATE;

    public static ItemBasic INCONEL_BARS;
    public static ItemBasic ALUMINUM_2198_INGOT;
    public static ItemBasic ALUMINUM_7XXX_INGOT;
    public static ItemBasic NIOBIUM_C103_INGOT;

    public static ItemBasic INCONEL_PLATE;
    public static ItemBasic ALUMINUM_2198_PLATE;
    public static ItemBasic ALUMINUM_7XXX_PLATE;
    public static ItemBasic NIOBIUM_C103_PLATE;

    // other metal items

    public static ItemBasic BROKEN_STEEL_GEAR;
    public static ItemBasic DAMAGED_AIRCRAFT_PLATING;

    public static ItemBasic TITANIUM_FAN;
    public static ItemBasic FUEL_PIPE;

    // random items

    public static ItemBasic CRUSHED_COAL;
    public static ItemBasic UNTREATED_CARBON_FIBERS;
    public static ItemBasic CARBON_FIBERS;
    public static ItemBasic CARBON_FIBER_PLATE;
    public static ItemBasic HEAT_SHIELD;
    public static ItemBasic STEEL_ROD;

    // rocket parts

    public static ItemBasic MERLIN_ENGINE;

    public static ItemBasic OCTAWEB;
    public static ItemBasic LANDING_LEG;
    public static ItemBasic FUEL_TANK_LOX_1;
    public static ItemBasic FUEL_TANK_RP1_1;
    public static ItemBasic GRID_FIN;
    public static ItemBasic COLD_GAS_THRUSTER;
    public static ItemBasic INTERSTAGE;
    public static ItemBasic FUEL_TANK_LOX_2;
    public static ItemBasic FUEL_TANK_RP1_2;

    public static ItemBasic USA_FLAG;
    public static ItemBasic USA_FLAG_EPIC;

    public static ItemBasic SUPERDRACO_ENGINE;
    public static ItemBasic FUEL_TANK_3;
    public static ItemBasic FALCON9_DRAGON;

    public static ItemBasic FALCON9;

    // tools/weapons

    public static ItemPickaxe TITANIUM_PICKAXE;

    public static ItemRangedWeapon FLAMETHROWER;

    public static void init() {
        ALUMINUM_INGOT = newIngot("aluminum");
        TITANIUM_INGOT = newIngot("titanium");
        LITHIUM_INGOT = newIngot("lithium");
        NICKEL_INGOT = newIngot("nickel");
        CHROMIUM_INGOT = newIngot("chromium");
        COPPER_INGOT = newIngot("copper");
        NIOBIUM_INGOT = newIngot("niobium");
        HAFNIUM_INGOT = newIngot("hafnium");
        MAGNESIUM_INGOT = newIngot("magnesium");
        ZINC_INGOT = newIngot("zinc");

        ALUMINUM_NUGGET = newNugget("aluminum");
        TITANIUM_NUGGET = newNugget("titanium");
        LITHIUM_NUGGET = newNugget("lithium");
        NICKEL_NUGGET = newNugget("nickel");
        CHROMIUM_NUGGET = newNugget("chromium");
        COPPER_NUGGET = newNugget("copper");
        NIOBIUM_NUGGET = newNugget("niobium");
        HAFNIUM_NUGGET = newNugget("hafnium");
        MAGNESIUM_NUGGET = newNugget("magnesium");
        ZINC_NUGGET = newNugget("zinc");

        IRON_PLATE = newPlate("iron");
        GOLD_PLATE = newPlate("gold");
        ALUMINUM_PLATE = newPlate("aluminum");
        TITANIUM_PLATE = newPlate("titanium");
        LITHIUM_PLATE = newPlate("lithium");
        NICKEL_PLATE = newPlate("nickel");
        CHROMIUM_PLATE = newPlate("chromium");
        COPPER_PLATE = newPlate("copper");
        NIOBIUM_PLATE = newPlate("niobium");
        HAFNIUM_PLATE = newPlate("hafnium");
        MAGNESIUM_PLATE = newPlate("magnesium");
        ZINC_PLATE = newPlate("zinc");

        IRON_DUST = newDust("iron");
        GOLD_DUST = newDust("gold");
        ALUMINUM_DUST = newDust("aluminum");
        TITANIUM_DUST = newDust("titanium");
        LITHIUM_DUST = newDust("lithium");
        NICKEL_DUST = newDust("nickel");
        CHROMIUM_DUST = newDust("chromium");
        COPPER_DUST = newDust("copper");
        NIOBIUM_DUST = newDust("niobium");
        HAFNIUM_DUST = newDust("hafnium");
        MAGNESIUM_DUST = newDust("magnesium");
        ZINC_DUST = newDust("zinc");

        STEEL_INGOT = newIngot("steel");

        STEEL_NUGGET = newNugget("steel");

        STEEL_PLATE = newPlate("steel");

        INCONEL_BARS = new ItemBasic("inconel_bars");
        INCONEL_BARS.addLore("A heat-resistant nickel-chromium superalloy often used in rocket engines.");
        INCONEL_BARS.setGlows();
        ALUMINUM_2198_INGOT = new ItemBasic("aluminum_2198_ingot");
        ALUMINUM_2198_INGOT.addLore("An aluminum-lithium alloy found in rocket fuel and oxidizer tanks.");
        ALUMINUM_2198_INGOT.setGlows();
        ALUMINUM_7XXX_INGOT = new ItemBasic("aluminum_7xxx_ingot");
        ALUMINUM_7XXX_INGOT.addLore("7000 series aluminum alloys are made from ~90% aluminum, ~7% zinc, ~2% magnesium, and ~1% other elements; they are commonly used in aerospace applications.");
        ALUMINUM_7XXX_INGOT.setGlows();
        NIOBIUM_C103_INGOT = new ItemBasic("niobium_c103_ingot");
        NIOBIUM_C103_INGOT.addLore("A high-performance, heat-resistant niobium-hafnium-titanium alloy used in rocket engine nozzles.");
        NIOBIUM_C103_INGOT.setGlows();

        INCONEL_PLATE = new ItemBasic("inconel_plate");
        INCONEL_PLATE.setGlows();
        ALUMINUM_2198_PLATE = new ItemBasic("aluminum_2198_plate");
        ALUMINUM_2198_PLATE.setGlows();
        ALUMINUM_7XXX_PLATE = new ItemBasic("aluminum_7xxx_plate");
        ALUMINUM_7XXX_PLATE.setGlows();
        NIOBIUM_C103_PLATE = new ItemBasic("niobium_c103_plate");
        NIOBIUM_C103_PLATE.setGlows();

        BROKEN_STEEL_GEAR = new ItemBasic("broken_steel_gear");
        DAMAGED_AIRCRAFT_PLATING = new ItemBasic("damaged_aircraft_plating");

        TITANIUM_FAN = new ItemBasic("titanium_fan");
        FUEL_PIPE = new ItemBasic("fuel_pipe");

        CRUSHED_COAL = new ItemBasic("crushed_coal");
        CRUSHED_COAL.setBurnTime(800);
        UNTREATED_CARBON_FIBERS = new ItemBasic("untreated_carbon_fibers");
        CARBON_FIBERS = new ItemBasic("carbon_fibers");
        CARBON_FIBERS.addLore("Disclaimer: most real carbon fibers aren't gmade of coal.");
        CARBON_FIBERS.setGlows();
        CARBON_FIBER_PLATE = new ItemBasic("carbon_fiber_plate");
        CARBON_FIBER_PLATE.setGlows();
        HEAT_SHIELD = new ItemBasic("heat_shield");
        HEAT_SHIELD.addLore("Magical heat shielding made from void-resistant plants and pulverized hellfire. What more could you ask for?");
        HEAT_SHIELD.setGlows();
        STEEL_ROD = new ItemBasic("steel_rod");

        MERLIN_ENGINE = new ItemBasic("merlin_engine");
        MERLIN_ENGINE.addLore("\"Merlin's thrust-to-weight ratio exceeds 150, making the Merlin the most efficient booster engine ever built, while still maintaining the structural and thermal safety margins needed to carry astronauts.\"");
        MERLIN_ENGINE.setMaxStackSize(16);

        OCTAWEB = new ItemBasic("octaweb");
        OCTAWEB.addLore("\"A metal structure that supports eight engines surrounding a center engine at the base of the launch vehicle.\"");
        OCTAWEB.setMaxStackSize(1);
        LANDING_LEG = new ItemBasic("landing_leg");
        LANDING_LEG.addLore("\"The Falcon 9 first stage carries landing legs which will deploy after stage separation and allow for the rocket's soft return to Earth.\"");
        LANDING_LEG.setMaxStackSize(4);
        FUEL_TANK_LOX_1 = new ItemBasic("fuel_tank_lox_1");
        FUEL_TANK_LOX_1.addLore("A first stage component that contains a fuel tank for liquid oxygen, an oxidizer essential for the combustion reaction used by liquid-propelled rockets to provide thrust.");
        FUEL_TANK_LOX_1.setMaxStackSize(1);
        FUEL_TANK_RP1_1 = new ItemBasic("fuel_tank_rp1_1");
        FUEL_TANK_RP1_1.addLore("A first stage component that contains a fuel tank for RP-1 (Rocket Propellant-1), a highly refined form of kerosene used as rocket fuel.");
        FUEL_TANK_RP1_1.setMaxStackSize(1);
        GRID_FIN = new ItemBasic("grid_fin");
        GRID_FIN.addLore("\"Falcon 9's first stage is equipped with hypersonic grid fins that manipulate the direction of the stage's lift during re-entry.\"");
        GRID_FIN.setMaxStackSize(4);
        COLD_GAS_THRUSTER = new ItemBasic("cold_gas_thruster");
        COLD_GAS_THRUSTER.addLore("Used to flip the rocket around as it begins its journey back to Earth.");
        COLD_GAS_THRUSTER.setMaxStackSize(2);
        INTERSTAGE = new ItemBasic("interstage");
        INTERSTAGE.addLore("The interstage connects the first and second stages and facilitates their separation. It also has grid fins and cold gas thrusters for steering and alignment during re-entry and landing.");
        INTERSTAGE.setMaxStackSize(1);
        FUEL_TANK_LOX_2 = new ItemBasic("fuel_tank_lox_2");
        FUEL_TANK_LOX_2.addLore("A second stage component that contains a fuel tank for liquid oxygen, an oxidizer essential for the combustion reaction used by liquid-propelled rockets to provide thrust.");
        FUEL_TANK_LOX_2.setMaxStackSize(1);
        FUEL_TANK_RP1_2 = new ItemBasic("fuel_tank_rp1_2");
        FUEL_TANK_RP1_2.addLore("A second stage component that contains a fuel tank for RP-1 (Rocket Propellant-1), a highly refined form of kerosene used as rocket fuel.");
        FUEL_TANK_RP1_2.setMaxStackSize(1);

        USA_FLAG = new ItemBasic("usa_flag");
        USA_FLAG.setMaxStackSize(1);
        USA_FLAG_EPIC = new ItemBasic("usa_flag_epic");
        USA_FLAG_EPIC.setMaxStackSize(1);
        USA_FLAG_EPIC.setGlows();

        SUPERDRACO_ENGINE = new ItemBasic("superdraco_engine");
        SUPERDRACO_ENGINE.addLore("3D printed engines made of Inconel and used to provide propulsion for the Crew Dragon.");
        SUPERDRACO_ENGINE.setMaxStackSize(8);
        FUEL_TANK_3 = new ItemBasic("fuel_tank_3");
        FUEL_TANK_3.addLore("SuperDraco engines don't actually use RP-1 and LOX, but making fuel is annoying enough already.");
        FUEL_TANK_3.setMaxStackSize(4);
        FALCON9_DRAGON = new ItemBasic("falcon9_dragon");
        FALCON9_DRAGON.addLore("\"Dragon is a free-flying spacecraft designed to deliver both cargo and people to orbiting destinations.\"");
        FALCON9_DRAGON.setMaxStackSize(1);

        FALCON9 = new ItemBasic("falcon9");
        FALCON9.addLore("One small step for a Steve, one giant leap for mankind.");
        FALCON9.setMaxStackSize(1);

        TITANIUM_PICKAXE = new ItemCustomPickaxe("titanium_pickaxe", ModToolMaterials.TITANIUM);

        FLAMETHROWER = new ItemRangedWeapon("flamethrower", world -> {
            EntityFallingBlock fire = new EntityFallingBlock(world, 0, 0, 0, Blocks.FIRE.getDefaultState());
            fire.fallTime = 1;
            return fire;
        }, 5, 5.0, 1.0, null);

        ModCreativeTabs.SPACEX.addAll(INCONEL_BARS, ALUMINUM_2198_INGOT, ALUMINUM_7XXX_INGOT,
                NIOBIUM_C103_INGOT, INCONEL_PLATE, ALUMINUM_2198_PLATE, ALUMINUM_7XXX_PLATE, NIOBIUM_C103_PLATE,
                TITANIUM_FAN, FUEL_PIPE, MERLIN_ENGINE, OCTAWEB, LANDING_LEG, FUEL_TANK_LOX_1, FUEL_TANK_RP1_1,
                GRID_FIN, COLD_GAS_THRUSTER, INTERSTAGE, FUEL_TANK_LOX_2, FUEL_TANK_RP1_2, SUPERDRACO_ENGINE,
                FUEL_TANK_3, FALCON9_DRAGON, FALCON9);

        ModCreativeTabs.BORING_COMPANY.addAll(FLAMETHROWER);
    }

    static void addItem(Item item) {
        items.add(item);
    }

    static void setOreDictName(Item item, String name) {
        oreDictEntries.put(item, name);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        items.forEach(registry::register);
        oreDictEntries.keySet().forEach(item -> OreDictionary.registerOre(oreDictEntries.get(item), item));
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerRenders(ModelRegistryEvent event) {
        items.forEach(ModItems::registerRender);
    }
}
