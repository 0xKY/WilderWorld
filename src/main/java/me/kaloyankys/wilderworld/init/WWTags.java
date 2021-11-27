package me.kaloyankys.wilderworld.init;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class WWTags {

    public static final Tag<Item> WISTERIA_LOGS = register("wisteria_logs", TagFactory.ITEM);

    private static <E> Tag<E> register(String path, TagFactory<E> factory) {
        return factory.create(new Identifier("wilderworld", path));
    }
}
