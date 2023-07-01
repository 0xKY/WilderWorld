package me.kaloyankys.wilderworld.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class WWTags {
    public static final TagKey<Block> GRASS_TAG = block("grass_tag");

    private static TagKey<Block> block(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier("wilderworld", id));
    }

    private static TagKey<Item> item(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier("wilderworld", id));
    }
}
