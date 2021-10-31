package me.kaloyankys.wilderworld.init;

import me.andante.chord.block.helper.WoodBlocks;
import me.kaloyankys.wilderworld.block.ShelfshroomBlock;
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

        public static final Block BIRD_OF_PARADISE = registerDeco("bird_of_paradise", new TallPlantBlock(FabricBlockSettings.of(Material.PLANT, MapColor.ORANGE)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

        public static final Block EDELWEISS = registerDeco("edelweiss", new FlowerBlock(StatusEffects.LUCK, 400, FabricBlockSettings
                .of(Material.PLANT, MapColor.WHITE_GRAY)
                .nonOpaque()
                .breakInstantly()
                .collidable(false)));

    public static final Block RAGING_VIOLET = registerDeco("raging_violet", new FlowerBlock(StatusEffects.STRENGTH, 400, FabricBlockSettings
            .of(Material.PLANT, MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

    public static final Block PHOSPHOSHOOTS = registerDeco("phosphoshoots", new FlowerBlock(StatusEffects.GLOWING, 400, FabricBlockSettings
            .of(Material.PLANT, MapColor.LIME)
            .nonOpaque()
            .breakInstantly()
            .luminance(state -> 12)
            .collidable(false)));

    public static final Block SHELFSHROOM = registerDeco("shelfshroom", new ShelfshroomBlock(FabricBlockSettings
            .of(Material.PLANT, MapColor.WHITE_GRAY)
            .nonOpaque()
            .breakInstantly()
            .collidable(false)));

    public static final Block ASPEN_LEAVES = registerDeco("aspen_leaves", new LeavesBlock(FabricBlockSettings
            .copy(Blocks.OAK_LEAVES)
            .nonOpaque()));

    public static final WoodBlocks WISTERIA = new WoodBlocks.Builder()
            .saplingGenerator(new WisteriaSaplingGenerator())
            .boatType(BoatEntity.Type.BIRCH)
            .itemGroup(ItemGroup.DECORATIONS)
            .build("wilderworld", "wisteria");

    public static Block registerDeco(String id, Block block) {
        Registry.register(Registry.ITEM, new Identifier("wilderworld", id), new BlockItem(block, new Item.Settings().group(ItemGroup.DECORATIONS)));
        return Registry.register(Registry.BLOCK, new Identifier("wilderworld", id), block);
    }
}
