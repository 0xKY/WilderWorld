package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.Wilderworld;
import me.kaloyankys.wilderworld.item.IceCreamItem;
import me.kaloyankys.wilderworld.item.MusicalItem;
import me.kaloyankys.wilderworld.util.enums.Flavours;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWItems {

    /* public static final Item EMPTY_MUG = register("empty_mug", new MugItem(
            new Item.Settings().group(Wilderworld.ICY_ADDITIONS), null));

    public static final Item COFFEE_MUG = register("coffee_mug", new MugItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)
            .food(new FoodComponent.Builder().hunger(1).saturationModifier(0.3f).alwaysEdible().build()).maxCount(1),
            new StatusEffectInstance(WWPotions.COFFEE_EFFECT, 2400, 0))); */

    public static final Item SPAWN_BUTTERFLY = register("butterfly_spawn_egg", new SpawnEggItem(WWEntities.BUTTERFLY,
            0xedaa00, 0x381a20, new Item.Settings()
            .group(ItemGroup.MISC)));

    public static final Item BUTTERFLY_WING = register("butterfly_wing", new Item(new Item.Settings().group(Wilderworld.FF_ADDITIONS)));

    public static final Item EBONY = register("ebony", new Item(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)));

    public static final Item TAMBURA = register("tambura", new MusicalItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS),
            0, WWSounds.TAMBURA_SPRUCE));

    public static final Item TAMBURA_EBONY = register("tambura_ebony", new MusicalItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS),
            0, WWSounds.TAMBURA_EBONY));

    public static final Item TAMBURA_WISTERIA = register("tambura_wisteria", new MusicalItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS),
            0, WWSounds.TAMBURA_WISTERIA));

    public static final Item ICE_CUBE = register("ice_cube", new Item(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)));

    public static final Item MINT = register("mint", new Item(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)));

    public static final Item CHOCOLATE_ICECREAM = register("chocolate_icecream", new IceCreamItem(Flavours.CHOCOLATE, WWBlocks.COCOA_ICECREAM,
            new Item.Settings().group(Wilderworld.ICY_ADDITIONS).food(
                    new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build())));

    public static final Item SWEETBERRY_ICECREAM = register("sweetberry_icecream", new IceCreamItem(Flavours.SWEET_BERRY, WWBlocks.SWEETBERRY_ICECREAM,
            new Item.Settings().group(Wilderworld.ICY_ADDITIONS).food(
                    new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build())));

    public static final Item MINT_ICECREAM = register("mint_icecream", new IceCreamItem(Flavours.MINT, WWBlocks.MINT_ICECREAM,
            new Item.Settings().group(Wilderworld.ICY_ADDITIONS).food(
                    new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build())));


    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("wilderworld", id), item);
    }
}
