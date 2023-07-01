package me.kaloyankys.wilderworld.init;


import com.github.creoii.creolib.api.util.registry.RegistrySets;
import me.kaloyankys.wilderworld.Wilderworld;
import me.kaloyankys.wilderworld.block.*;
import me.kaloyankys.wilderworld.item.IceCreamItem;
import me.kaloyankys.wilderworld.item.PontoonItem;
import me.kaloyankys.wilderworld.util.enums.Flavours;
import me.kaloyankys.wilderworld.world.AspenSaplingGenerator;
import me.kaloyankys.wilderworld.world.WisteriaSaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class WWBlocks {
    public static final HashMap<String, Block> WOOD_BLOCKS = new HashMap<>();
    public static final HashMap<String, Item> WOOD_ITEMS = new HashMap<>();
    public static final Block BIRD_OF_PARADISE = registerFF("bird_of_paradise", new TallPlantBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.ORANGE)
            .nonOpaque()
            .breakInstantly()
            .noCollision()));

    public static final Block CHAMOMILE = registerFF("chamomile", new FlowerBlock(StatusEffects.LUCK, 400, FabricBlockSettings
            .create().mapColor(MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .noCollision()));

    public static final Block RAGING_VIOLET = registerFF("raging_violet", new FlowerBlock(StatusEffects.STRENGTH, 400, FabricBlockSettings
            .create().mapColor(MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .noCollision()));

    public static final Block GLOWBRUSH = registerFF("glowbrush", new GlowBrushBlock(FabricBlockSettings
            .create().mapColor(MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .luminance(state -> {
                if (state.get(SeedSpreaderBlock.STAGES) == 1) {
                    return 12;
                }
                return 0;
            })
            .noCollision()));

    public static final Block SHELFSHROOM = registerFF("shelfshroom", new ShelfshroomBlock(FabricBlockSettings
            .create()
            .mapColor(MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

    /*public static final WoodBlocks WISTERIA = new WoodBlocks.Builder()
            .saplingGenerator(new WisteriaSaplingGenerator())
            .boatType(BoatEntity.Type.BIRCH)
            .itemGroup(ItemGroups.FF)
            .build("wilderworld", "wisteria"); */

    public static final Block WISTERIA_BLOSSOM_LEAVES = registerFF("wisteria_blossom_leaves", new LeavesBlock(FabricBlockSettings
            .copy(Blocks.OAK_LEAVES).mapColor(MapColor.MAGENTA)
            .nonOpaque()));

    //public static final WoodType A = registerWoodSet("a", BlockSetType.BIRCH, new AspenSaplingGenerator());
    //public static final WoodType W = registerWoodSet("w", BlockSetType.BIRCH, new WisteriaSaplingGenerator());

    public static final RegistrySets.WoodSet ASPEN = registerWoodSet(RegistrySets.createWoodSet("wilderworld", "aspen",
            MapColor.OFF_WHITE, MapColor.GOLD, null, null, null), "aspen", new AspenSaplingGenerator());

    public static final RegistrySets.WoodSet WISTERIA = registerWoodSet(RegistrySets.createWoodSet("wilderworld", "wisteria",
            MapColor.BROWN, MapColor.OFF_WHITE, null, null, null), "wisteria", new WisteriaSaplingGenerator());

    public static final Block DROOPBLOOM = registerFF("droopbloom", new VerticalConnectorBlock(AbstractBlock.Settings.create().noCollision().breakInstantly().mapColor(DyeColor.MAGENTA).replaceable()));

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

    public static final Block WISTERIA_LEAVES = registerFF("wisteria_leaves", new LeavesBlock(FabricBlockSettings
            .copy(Blocks.OAK_LEAVES).mapColor(MapColor.YELLOW)
            .nonOpaque()));

    public static final Block BUTTERFLY_SPAWN = registerFF("larvae", new SpawnBlock(FabricBlockSettings
            .create()
            .noCollision()
            .nonOpaque()
            .breakInstantly()));

    public static final Block BUTTERFLY_WING_MEMBRANE_BLOCK = registerFF("butterfly_wing_membrane_block", new Block(FabricBlockSettings
            .create().mapColor(MapColor.ORANGE)));

    public static final Block WAX = registerFF("wax", new WaxBlock(FabricBlockSettings.copy(Blocks.HONEY_BLOCK)));

    public static final Block SCENTED_CANDLE = registerFF("scented_candle", new ScentedCandleBlock(FabricBlockSettings
            .create()
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
            AbstractBlock.Settings.copy(Blocks.SNOW_BLOCK).nonOpaque(), Flavours.CHOCOLATE));

    public static final Block SWEETBERRY_ICECREAM = registerIcecream("sweetberry_icecream", new IceCreamBlock(
            AbstractBlock.Settings.copy(Blocks.SNOW_BLOCK).nonOpaque(), Flavours.SWEET_BERRY));

    public static final Block MINT_ICECREAM = registerIcecream("mint_icecream", new IceCreamBlock(
            AbstractBlock.Settings.copy(Blocks.SNOW_BLOCK).nonOpaque(), Flavours.MINT));

    public static final Block SLUSH_CAULDRON = registerNoItem("slush_cauldron", new SlushCauldronBlock(
            FabricBlockSettings.copy(Blocks.CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, CauldronBehavior.WATER_CAULDRON_BEHAVIOR));

    public static final Block HONEY_CAULDRON = registerNoItem("honey_cauldron", new HoneyCauldronBlock(
            FabricBlockSettings.copy(Blocks.CAULDRON), LeveledCauldronBlock.RAIN_PREDICATE, CauldronBehavior.WATER_CAULDRON_BEHAVIOR));

    public static final Block GLOWGI = registerAboveWater("glowgi", new LilyPadBlock(FabricBlockSettings.copy(Blocks.LILY_PAD).luminance((state) -> 15)));
    public static final Block TRAVERTINE = registerIcy("travertine", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)));
    public static final Block TRAVERTINE_PEACH = registerIcy("travertine_peach", new Block(FabricBlockSettings.copy(Blocks.TERRACOTTA)));
    public static final Block PEARL_STONE = registerIcy("pearl_stone", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)));
    public static final Block PEARL_CHISELED = registerIcy("pearl_chiseled", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)));
    public static final Block PEARL_TILES = registerIcy("pearl_tiles", new Block(FabricBlockSettings.copy(Blocks.QUARTZ_BLOCK)));

    public static final Block COCONUT_FRUIT = registerFF("coconut_fruit", new CoconutFruitBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS).ticksRandomly()));
    public static final Block COCONUT = registerFF("coconut", new CoconutBlock(FabricBlockSettings.copy(Blocks.OAK_PLANKS)));

    public static final Block PALM_TRUNK = registerFF("palm_trunk", new CoconutBlock(FabricBlockSettings.copy(Blocks.COCOA)));

    public static final Block FROND = registerFF("frond_block", new FrondBlock(FabricBlockSettings.copy(Blocks.OAK_LEAVES)));

    //public static final Block PONTOON = registerPontoon("pontoon", new PontoonBlock(FabricBlockSettings.copy(Blocks.DARK_OAK_PLANKS)));



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
        Registry.register(Registries.BLOCK, new Identifier("wilderworld", id + "_block"), block);
        Registry.register(Registries.ITEM, new Identifier("wilderworld", id), new IceCreamItem(block, new Item.Settings()
                .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build())));
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

   public static RegistrySets.WoodSet registerWoodSet(RegistrySets.WoodSet set, String id, SaplingGenerator saplingGenerator) {
       /*WoodType woodType = WoodTypeRegistry.register(new Identifier("wilderworld", id), type);
       Block wood = registerFF(id + "_wood", new PillarBlock(copy(Blocks.OAK_WOOD)));
       Block stairs = registerFF(id + "_stairs", new StairsBlock(Blocks.OAK_STAIRS.getDefaultState(), copy(Blocks.OAK_STAIRS)));
       Block button = registerFF(id + "_button", new ButtonBlock(copy(Blocks.OAK_BUTTON), type, 40, true));
       Block door = registerFF(id + "_door", new DoorBlock(copy(Blocks.OAK_DOOR), type));
       Block fence = registerFF(id + "_fence", new FenceBlock(copy(Blocks.OAK_FENCE)));
       Block gate = registerFF(id + "_fence_gate", new FenceGateBlock(copy(Blocks.OAK_FENCE_GATE), woodType));
       Block HANGING_SIGN = registerNoItem(id + "_hanging_sign", new HangingSignBlock(copy(Blocks.OAK_HANGING_SIGN), woodType));
       Block HANGING_SIGN_WALL = registerNoItem(id + "_hanging_wall_sign", new WallHangingSignBlock(copy(Blocks.OAK_WALL_HANGING_SIGN), woodType));
       //Item hangingSign = WWItems.register(id + "_hanging_sign", new HangingSignItem(HANGING_SIGN, HANGING_SIGN_WALL, settings()));
       Block log = registerFF(id + "_log", new PillarBlock(copy(Blocks.OAK_LOG)));
       Block planks = registerFF(id + "_planks", new Block(copy(Blocks.OAK_PLANKS)));
       Block plate = registerFF(id + "_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, copy(Blocks.OAK_PRESSURE_PLATE), type));
       Block strippedWood = registerFF("stripped_" + id + "_wood", new PillarBlock(copy(Blocks.STRIPPED_OAK_WOOD)));
       Block strippedLog = registerFF("stripped_" + id + "_log", new PillarBlock(copy(Blocks.STRIPPED_OAK_LOG)));
       Block SIGN = registerNoItem(id + "_sign", new SignBlock(copy(Blocks.OAK_SIGN), woodType));
       Block WALL_SIGN = registerNoItem(id + "_wall_sign", new WallSignBlock(copy(Blocks.OAK_WALL_SIGN), woodType));
       //Item sign = WWItems.register(id + "_sign", new SignItem(settings(), SIGN, WALL_SIGN));
       Block trapDoor = registerFF(id + "_trapdoor", new TrapdoorBlock(copy(Blocks.OAK_TRAPDOOR), type));
       Block slab = registerFF(id + "_slab", new SlabBlock(copy(Blocks.OAK_SLAB)));*/
       Item boatItem = new BoatItem(false, BoatEntity.Type.BIRCH, settings());
       Item boatChest = new BoatItem(true, BoatEntity.Type.BIRCH, settings());
       WOOD_BLOCKS.put(id + "_wood",set.wood());
       WOOD_BLOCKS.put(id + "_stairs",set.stairs());
       WOOD_BLOCKS.put(id + "_button",set.button());
       WOOD_BLOCKS.put(id + "_door",set.door());
       WOOD_BLOCKS.put(id + "_fence",set.fence());
       WOOD_BLOCKS.put(id + "_fence_gate",set.fenceGate());
       WOOD_BLOCKS.put(id + "_hanging_sign", set.hangingSign());
       WOOD_BLOCKS.put(id + "_hanging_wall_sign", set.wallHangingSign());
       WOOD_BLOCKS.put(id + "_log",set.log());
       WOOD_BLOCKS.put(id + "_" + "planks",set.planks());
       WOOD_BLOCKS.put(id + "_pressure_plate",set.pressurePlate());
       WOOD_BLOCKS.put("stripped_" + id + "_log",set.strippedLog());
       WOOD_BLOCKS.put("stripped_" + id + "_wood",set.strippedWood());
       WOOD_BLOCKS.put(id + "_sign", set.sign());
       WOOD_BLOCKS.put(id + "_wall_sign", set.wallSign());
       WOOD_BLOCKS.put(id + "_trapdoor",set.trapdoor());
       WOOD_BLOCKS.put(id + "_slab",set.slab());
       WOOD_BLOCKS.put(id + "_sapling", new SaplingBlock(saplingGenerator, copy(Blocks.OAK_SAPLING)));
       WOOD_ITEMS.put(id + "_sign", set.signItem());
       WOOD_ITEMS.put(id + "_hanging_sign", set.hangingSignItem());
       WOOD_ITEMS.put(id + "_boat", boatItem);
       WOOD_ITEMS.put(id + "_chest_boat", boatChest);
       Wilderworld.STRIPPABLE.put(set.log(), set.strippedLog());
       Wilderworld.STRIPPABLE.put(set.wood(), set.strippedWood());
       return set;
   }

    private static AbstractBlock.Settings copy(Block block) {
        return AbstractBlock.Settings.copy(block);
    }
    private static Item.Settings settings() {
        return new Item.Settings();
    }

    public static Block findBlock(String id) {
        return WOOD_BLOCKS.get(id);
    }
    public static Item findItem(String id) {
        return WOOD_ITEMS.get(id);
    }

    public static void registerWood() {
        WOOD_BLOCKS.forEach(((identifier, block) -> {
            if (!identifier.contains("sign")) {
                registerFF(identifier, block);
            } else {
                registerNoItem(identifier, block);
            }
        }));
        WOOD_ITEMS.forEach((WWItems::register));
    }
}
