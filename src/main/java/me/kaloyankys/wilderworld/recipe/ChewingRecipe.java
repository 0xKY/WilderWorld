package me.kaloyankys.wilderworld.recipe;

import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;

import java.util.HashMap;

public class ChewingRecipe {
    public static final HashMap<Item, Item> RECIPES = new HashMap<>();

    public ChewingRecipe() {
        ChewingRecipe.addRecipe(Blocks.PACKED_ICE.asItem(), WWItems.MINT);
    }

    public static void addRecipe(Item input, Item output) {
        RECIPES.put(input, output);
    }
}
