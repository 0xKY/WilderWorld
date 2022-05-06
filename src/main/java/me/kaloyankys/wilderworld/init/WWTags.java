package me.kaloyankys.wilderworld.init;

import net.minecraft.block.Block;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWTags {

    public static final TagKey<Block> WISTERIA_LOGS = block("wisteria_logs");
    public static final TagKey<Block> ICECREAM_BLOCKS = item("icecream_blocks");


    private static TagKey<Block> block(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier("wilderworld", id));
    }

    private static TagKey<Block> item(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier("wilderworld", id));
    }
}
