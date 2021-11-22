package me.kaloyankys.wilderworld.init;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class WWTags {

    private static <E> Tag<E> register(String path, TagFactory<E> factory) {
        return factory.create(new Identifier("wilderworld", path));
    }

    public static final class Blocks {
        public static final Tag<Block> WISTERIA_LOGS = register("wisteria_Logs", TagFactory.BLOCK);
    }
}
