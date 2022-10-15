package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.Wilderworld;
import me.kaloyankys.wilderworld.block.IceCreamBlock;
import me.kaloyankys.wilderworld.item.*;
import me.kaloyankys.wilderworld.util.enums.Flavours;
import me.kaloyankys.wilderworld.util.interfaces.FlavourSet;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWItems {
    public static final Item BUTTERFLY_WING = register("butterfly_wing", new Item(new Item.Settings().group(Wilderworld.FF_ADDITIONS)));

    public static final Item SPAWN_BUTTERFLY = register("butterfly_spawn_egg", new SpawnEggItem(WWEntities.BUTTERFLY,
            0x381a20, 0xedaa00, new Item.Settings()
            .group(ItemGroup.MISC)));

    public static final Item DRUPES = register("drupes", new Item(new Item.Settings().group(Wilderworld.FF_ADDITIONS)
            .food(new FoodComponent.Builder().
                    hunger(1).saturationModifier(0.1f).alwaysEdible().snack().statusEffect(new StatusEffectInstance(
                            StatusEffects.GLOWING, 100, 0), 1.0f)
                    .build())));


    public static final Item EBONY = register("ebony", new Item(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)));

    public static final Item TAMBURA = register("tambura", new MusicalItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS),
            0, WWSounds.TAMBURA_SPRUCE));

    public static final Item TAMBURA_EBONY = register("tambura_ebony", new MusicalItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS),
            0, WWSounds.TAMBURA_EBONY));

    public static final Item TAMBURA_WISTERIA = register("tambura_wisteria", new MusicalItem(new Item.Settings().group(Wilderworld.ICY_ADDITIONS),
            0, WWSounds.TAMBURA_WISTERIA));

    public static final Item ICE_CUBE = register("ice_cube", new Item(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)));

    public static final Item MINT = register("mint", new Item(new Item.Settings().group(Wilderworld.ICY_ADDITIONS)));

    /* public static final Item CHOCOLATE_ICECREAM = new ChocolateIceCreamItem((FlavourSet) Flavours.CHOCOLATE, (IceCreamBlock) WWBlocks.COCOA_ICECREAM,
            new Item.Settings().group(Wilderworld.ICY_ADDITIONS).food(
                    new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build()));

    public static final Item SWEETBERRY_ICECREAM = new SweetBerryIceCreamItem((FlavourSet) Flavours.SWEET_BERRY, (IceCreamBlock) WWBlocks.SWEETBERRY_ICECREAM,
            new Item.Settings().group(Wilderworld.ICY_ADDITIONS).food(
                    new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build()));

    public static final Item MINT_ICECREAM = new MintIceCreamItem((FlavourSet) Flavours.MINT, (IceCreamBlock) WWBlocks.MINT_ICECREAM,
            new Item.Settings().group(Wilderworld.ICY_ADDITIONS).food(
                    new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).build()));
    */

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier("wilderworld", id), item);
    }
}
