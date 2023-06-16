package me.kaloyankys.wilderworld.init;


import me.kaloyankys.wilderworld.block.*;
import me.kaloyankys.wilderworld.item.IceCreamItem;
import me.kaloyankys.wilderworld.item.ItemGroups;
import me.kaloyankys.wilderworld.item.PontoonItem;
import me.kaloyankys.wilderworld.util.enums.Flavours;
import me.kaloyankys.wilderworld.world.AspenSaplingGenerator;
import me.kaloyankys.wilderworld.world.WisteriaSaplingGenerator;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WWBlocks {
    public static final Block BIRD_OF_PARADISE = registerFF("bird_of_paradise", new TallPlantBlock(AbstractBlock.Settings.create()(Material.PLANT, MapColor.ORANGE)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

    public static final Block CHAMOMILE = registerFF("chamomile", new FlowerBlock(StatusEffects.LUCK, 400, FabricBlockSettings
            .of(Material.PLANT, MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

    public static final Block RAGING_VIOLET = registerFF("raging_violet", new FlowerBlock(StatusEffects.STRENGTH, 400, FabricBlockSettings
            .of(Material.PLANT, MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

    public static final Block GLOWBRUSH = registerFF("glowbrush", new GlowBrushBlock(FabricBlockSettings
            .of(Material.PLANT, MapColor.LIME)
            .nonOpaque()
            .breakInstantly()
            .luminance(state -> {
                if (state.get(SeedSpreaderBlock.STAGES) == 1) {
                    return 12;
                }
                return 0;
            })
            .collidable(false)));

    public static final Block SHELFSHROOM = registerFF("shelfshroom", new ShelfshroomBlock(FabricBlockSettings
            .of(Material.PLANT, MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

    public static final WoodBlocks WISTERIA = new WoodBlocks.Builder()
            .saplingGenerator(new WisteriaSaplingGenerator())
            .boatType(BoatEntity.Type.BIRCH)
            .itemGroup(ItemGroups.FF)
            .build("wilderworld", "wisteria");

    public static final Block WISTERIA_BLOSSOM_LEAVES = registerFF("wisteria_blossom_leaves", new LeavesBlock(FabricBlockSettings
            .copy(Blocks.OAK_LEAVES).mapColor(MapColor.MAGENTA)
            .nonOpaque()));

    public static final Block DROOPBLOOM = registerFF("droopbloom", new VerticalConnectorBlock(AbstractBlock.Settings.create()(Material.PLANT)));

    /*public static final WoodBlocks ASPEN = new WoodBlocks.Builder()
            .saplingGenerator(new AspenSaplingGenerator())
            .boatType(BoatEntity.Type.BIRCH)
            .itemGroup(ItemGroups.FF)
            .build("wilderworld", "aspen");*/

    public static final Block ASPEN_LEAVES_AGED = registerFF("aspen_leaves_aged", new LeavesBlock(FabricBlockSettings
            .copy(Blocks.OAK_LEAVES).mapColor(MapColor.ORANGE)
            .nonOpaque()));

    public static final Block ASPEN_LEAVES = registerFF("aspen_leaves", new LeavesBlock(FabricBlockSettings
            .copy(Blocks.OAK_LEAVES).mapColor(MapColor.YELLOW)
            .nonOpaque()));

    public static final Block BUTTERFLY_SPAWN = registerFF("larvae", new SpawnBlock(FabricBlockSettings
            .of(Material.EGG)
            .collidable(false)
            .nonOpaque()
            .breakInstantly()));

    public static final Block BUTTERFLY_WING_MEMBRANE_BLOCK = registerFF("butterfly_wing_membrane_block", new MembraneBlock(FabricBlockSettings
            .of(Material.SOLID_ORGANIC)
            .collidable(false)));

    public static final Block WAX = registerFF("wax", new WaxBlock(FabricBlockSettings.copy(Blocks.HONEY_BLOCK)));

    public static final Block SCENTED_CANDLE = registerFF("scented_candle", new ScentedCandleBlock(FabricBlockSettings
            .of(Material.SOLID_ORGANIC)
            .luminance((state) -> {
                if (state.get(ScentedCandleBlock.LIT)) {
                    return 15;
                } else {
                    return 0;
                }
            })
            .ticksRandomly()));

    public static final Block EBONY_BUSH_TALL = registerIcy("ebony_bush_tall", new SnowyBushBlock(FabricBlockSettings.copy(Blocks.GRASS)));
    public static final Block EBONY_BUSH = registerIcy("ebony_bush", new SnowyPlantBlock(FabricBlockSettings.copy(Blocks.GRASS)));

    public static final Block CHOCOLATE_ICECREAM = registerIcecream("chocolate_icecream", new IceCreamBlock(
            AbstractBlock.Settings.create()(Material.SNOW_LAYER).nonOpaque(), Flavours.CHOCOLATE));

    public static final Block SWEETBERRY_ICECREAM = registerIcecream("sweetberry_icecream", new IceCreamBlock(
            AbstractBlock.Settings.create()(Material.SNOW_LAYER).nonOpaque(), Flavours.SWEET_BERRY));

    public static final Block MINT_ICECREAM = registerIcecream("mint_icecream", new IceCreamBlock(
            AbstractBlock.Settings.create()(Material.SNOW_LAYER).nonOpaque(), Flavours.MINT));

    public static final Block SLUSH_CAULDRON = registerNoItem("slush_cauldron", new SlushCauldronBlock(
            FabricBlockSettings.copy(Blocks.CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, CauldronBehavior.WATER_CAULDRON_BEHAVIOR));

    public static final Block GLOWGI = registerAboveWater("glowgi", new LilyPadBlock(FabricBlockSettings.copy(Blocks.LILY_PAD).luminance((state) -> 15)));
    public static final Block TRAVERTINE = registerIcy("travertine", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)));
    public static final Block TRAVERTINE_PEACH = registerIcy("travertine_peach", new Block(FabricBlockSettings.copy(Blocks.TERRACOTTA)));
    public static final Block PEARL_STONE = registerIcy("pearl_stone", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)));
    public static final Block PEARL_CHISELED = registerIcy("pearl_chiseled", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)));
    public static final Block PEARL_TILES = registerIcy("pearl_tiles", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)));

    public static final Block COCONUT_FRUIT = registerNoItem("coconut_fruit", new CoconutFruitBlock(FabricBlockSettings.copy(Blocks.COCOA).ticksRandomly()));
    public static final Block COCONUT = registerNoItem("coconut", new CoconutBlock(FabricBlockSettings.copy(Blocks.COCOA)));

    public static final Block PONTOON = registerPontoon("pontoon", new PontoonBlock(FabricBlockSettings.copy(Blocks.DARK_OAK_PLANKS)));



    public static Block registerFF(String id, Block block) {
        Registry.register(Registries.ITEM, new Identifier("wilderworld", id), new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, new Identifier("wilderworld", id), block);
    }

    public static Block registerIcy(String id, Block block) {
        Registry.register(Registries.ITEM, new Identifier("wilderworld", id), new BlockItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, new Identifier("wilderworld", id), block);
    }

    public static Block registerNoItem(String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier("wilderworld", id), block);
    }

    public static Block registerIcecream(String id, IceCreamBlock block) {
        Registry.register(Registries.ITEM, new Identifier("wilderworld", id), new IceCreamItem(block, new Item.Settings()
                .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build())));
        Registry.register(Registries.BLOCK, new Identifier("wilderworld", id + "_block"), block);
        return block;
    }

    public static Block registerPontoon(String id, PontoonBlock block) {
        Registry.register(Registries.ITEM, new Identifier("wilderworld", id), new PontoonItem(block, new Item.Settings()));
        Registry.register(Registries.BLOCK, new Identifier("wilderworld", id), block);
        return block;
    }

    public static Block registerAboveWater(String id, Block block) {
        Registry.register(Registries.ITEM, new Identifier("wilderworld", id), new PlaceableOnWaterItem(block, new Item.Settings()));
        return Registry.register(Registries.BLOCK, new Identifier("wilderworld", id), block);
    }
    public static void addToGroup(ItemGroup group, Item before, Item item) {
        if (group != null & before != null) {
            ItemGroupEvents.modifyEntriesEvent(group).register(content -> content.addAfter(before, item));
        }
    }
}
