package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.Wilderworld;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WoodBlocksNEW {
    public String id;

    public WoodBlocksNEW(String id) {
        this.id = id;
    }



    public final Block STRIPPED_LOG = register(id + "_stripped_log", new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_LOG)));
    public final Block LOG = register(id + "_log", new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_LOG)), STRIPPED_LOG);
    public final Block STRIPPED_WOOD = register(id + "_stripped_wood", new PillarBlock(FabricBlockSettings.copy(Blocks.STRIPPED_OAK_WOOD)));
    public final Block WOOD = register(id + "_wood", new PillarBlock(FabricBlockSettings.copy(Blocks.OAK_WOOD)), STRIPPED_WOOD);
    public final Block PLANKS = register(id + "_planks", new Block(FabricBlockSettings.copy(Blocks.OAK_PLANKS)));

    private static Block register(String id, Block block, Block stripped) {
        register(id, block);
        Wilderworld.STRIPPABLE.put(block, stripped);
        return block;
    }

    private static Block register(String id, Block block) {
        Registry.register(Registries.BLOCK, new Identifier("wilderworld", id), block);
        register(id, new BlockItem(block, new Item.Settings()));
        return block;
    }

    public static Item register(String id, Item item) {
        Registry.register(Registries.ITEM, new Identifier("wilderworld", id), item);
        return item;
    }
}
