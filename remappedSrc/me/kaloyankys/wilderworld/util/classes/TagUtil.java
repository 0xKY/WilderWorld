package me.kaloyankys.wilderworld.util.classes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public class TagUtil {

    public static Boolean tagContainsItem(TagKey<Item> tag, Item item) {
        return Registries.ITEM.getOrCreateEntryList(tag).contains(Registries.ITEM.entryOf(item.getRegistryEntry().registryKey()));
    }

    public static Boolean tagContainsBlock(TagKey<Block> tag, Block block) {
        return Registries.BLOCK.getOrCreateEntryList(tag).contains(Registries.BLOCK.entryOf(block.getRegistryEntry().registryKey()));
    }
}
