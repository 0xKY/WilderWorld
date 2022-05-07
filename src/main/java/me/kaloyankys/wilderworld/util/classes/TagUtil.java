package me.kaloyankys.wilderworld.util.classes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class TagUtil {

    public static Boolean tagContainsItem(TagKey<Item> tag, Item item) {
        return Registry.ITEM.getOrCreateEntryList(tag).contains(Registry.ITEM.entryOf(item.getRegistryEntry().registryKey()));
    }

    public static Boolean tagContainsBlock(TagKey<Block> tag, Block block) {
        return Registry.BLOCK.getOrCreateEntryList(tag).contains(Registry.BLOCK.entryOf(block.getRegistryEntry().registryKey()));
    }
}
