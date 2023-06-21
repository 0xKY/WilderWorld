package me.kaloyankys.wilderworld.util.classes;

import com.mojang.datafixers.types.templates.Named;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.List;

public class TagUtil {

    public static Boolean tagContainsItem(TagKey<Item> tag, Item item) {
        return Registries.ITEM.getOrCreateEntryList(tag).contains(Registries.ITEM.entryOf(item.getRegistryEntry().registryKey()));
    }

    public static Boolean tagContainsBlock(TagKey<Block> tag, Block block) {
        return Registries.BLOCK.getOrCreateEntryList(tag).contains(Registries.BLOCK.entryOf(block.getRegistryEntry().registryKey()));
    }

    public static ArrayList<Block> blockTagToList(TagKey<Block> tag) {
        ArrayList<Block> blocks = new ArrayList<>();
        RegistryEntryList.Named<Block> registryEntries = Registries.BLOCK.getOrCreateEntryList(tag);
        registryEntries.stream().forEach((blockRegistryEntry -> blocks.add(blockRegistryEntry.comp_349())));
        return blocks;
    }

    public static ArrayList<Item> itemTagToList(TagKey<Item> tag) {
        ArrayList<Item> blocks = new ArrayList<>();
        RegistryEntryList.Named<Item> registryEntries = Registries.ITEM.getOrCreateEntryList(tag);
        registryEntries.stream().forEach((itemRegistryEntry -> blocks.add(itemRegistryEntry.comp_349())));
        return blocks;
    }
}
