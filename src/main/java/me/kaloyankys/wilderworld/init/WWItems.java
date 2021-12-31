package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.Wilderworld;
import me.kaloyankys.wilderworld.item.CoffeeMugItem;
import me.kaloyankys.wilderworld.item.MugItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWItems {

    public static final Item EMPTY_MUG = register("empty_mug", new MugItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)));

    public static final Item COFFEE_MUG = register("coffee_mug", new CoffeeMugItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)
            .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3f).alwaysEdible().build()).maxCount(16)));

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("wilderworld", id), item);
    }
}
