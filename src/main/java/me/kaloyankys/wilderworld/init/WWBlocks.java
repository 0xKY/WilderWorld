package me.kaloyankys.wilderworld.init;

import me.andante.chord.block.helper.WoodBlocks;
import me.kaloyankys.wilderworld.Wilderworld;
import me.kaloyankys.wilderworld.block.*;
import me.kaloyankys.wilderworld.world.AspenSaplingGenerator;
import me.kaloyankys.wilderworld.world.WisteriaSaplingGenerator;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWBlocks {

    public static final Block BIRD_OF_PARADISE = registerFF("bird_of_paradise", new TallPlantBlock(FabricBlockSettings.of(Material.PLANT, MapColor.ORANGE)
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

    public static final Block PHOSPHOSHOOTS = registerFF("phosphoshoots", new FlowerBlock(StatusEffects.GLOWING, 400, FabricBlockSettings
            .of(Material.PLANT, MapColor.LIME)
            .nonOpaque()
            .breakInstantly()
            .luminance(state -> 12)
            .collidable(false)));

    public static final Block SHELFSHROOM = registerFF("shelfshroom", new ShelfshroomBlock(FabricBlockSettings
            .of(Material.PLANT, MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

    public static final Block ASPEN_LEAVES = registerFF("aspen_leaves", new LeavesBlock(FabricBlockSettings
            .copy(Blocks.OAK_LEAVES)
            .nonOpaque()));

    public static final WoodBlocks WISTERIA = new WoodBlocks.Builder()
            .saplingGenerator(new WisteriaSaplingGenerator())
            .boatType(BoatEntity.Type.BIRCH)
            .itemGroup(Wilderworld.FF_ADDITIONS)
            .build("wilderworld", "wisteria");

    public static final Block MOSS_COVER = registerDeco("moss_cover", new MossCoverBlock(FabricBlockSettings
            .copy(Blocks.VINE)
            .nonOpaque()));

    public static final Block ASPEN_SAPLING = registerNoItem("aspen_sapling", new AspenBirchSaplingBlock(new AspenSaplingGenerator(), FabricBlockSettings
            .copy(Blocks.BIRCH_SAPLING)
            .nonOpaque()));

    public static final Block BUTTERFLY_SPAWN = registerFF("butterfly_larvae", new SpawnBlock(FabricBlockSettings
            .of(Material.EGG)
            .collidable(false)
            .nonOpaque()
            .breakInstantly()));

    public static final Block BUTTERFLY_WING_MEMBRANE_BLOCK = registerFF("butterfly_wing_membrane", new MembraneBlock(FabricBlockSettings
            .of(Material.SOLID_ORGANIC)
            .collidable(false)));

    public static Block registerFF(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier("wilderworld", id), new BlockItem(block, new Item.Settings().group(Wilderworld.FF_ADDITIONS)));
        return Registry.register(Registry.BLOCK, new Identifier("wilderworld", id), block);
    }

    public static Block registerDeco(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier("wilderworld", id), new BlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)));
        return Registry.register(Registry.BLOCK, new Identifier("wilderworld", id), block);
    }

    public static Block registerNoItem(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier("wilderworld", id), block);
    }
}
