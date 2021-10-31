package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.mixin.BrewingRecipeRegistryInvoker;
import me.kaloyankys.wilderworld.statuseffect.ShelfSenseEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWPotions {

    public static final StatusEffect SHELF_SENSE_EFFECT = new ShelfSenseEffect(StatusEffectCategory.BENEFICIAL, 0xf5d442);

    public static final Potion SHELF_SENSE = new Potion("shelf_sense", new StatusEffectInstance(SHELF_SENSE_EFFECT, 2400, 1));

    public static void init() {
        BrewingRecipeRegistryInvoker.registerPotionRecipe(Potions.AWKWARD, WWBlocks.SHELFSHROOM.asItem(), SHELF_SENSE);
        registerPotion("shelf_sense", SHELF_SENSE_EFFECT, SHELF_SENSE);
    }

    private static void registerPotion(String id, StatusEffect statusEffect, Potion potion) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("wilderworld", id), statusEffect);
        Registry.register(Registry.POTION, new Identifier("wilderworld", id), potion);
    }
}