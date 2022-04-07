package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.mixin.BrewingRecipeRegistryInvoker;
import me.kaloyankys.wilderworld.statuseffect.ChocolateEffect;
import me.kaloyankys.wilderworld.statuseffect.MintEffect;
import me.kaloyankys.wilderworld.statuseffect.RageEffect;
import me.kaloyankys.wilderworld.statuseffect.ShelfSenseEffect;
import me.kaloyankys.wilderworld.util.records.PotionRecipe;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWPotions {

    public static final StatusEffect SHELF_SENSE_EFFECT = new ShelfSenseEffect(StatusEffectCategory.BENEFICIAL, 0xf5d442);

    public static final StatusEffect RAGE_EFFECT = new RageEffect(StatusEffectCategory.BENEFICIAL, 0x15031c)
            .addAttributeModifier(
                    EntityAttributes.GENERIC_ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.4D,
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(
                    EntityAttributes.GENERIC_MOVEMENT_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", 0.4D,
                    EntityAttributeModifier.Operation.MULTIPLY_TOTAL);

    public static final StatusEffect CHOCOLATE_EFFECT = registerEffect("spelunking", new ChocolateEffect(StatusEffectCategory.BENEFICIAL, 0xf79797));

    public static final StatusEffect MINT_EFFECT = registerEffect("minty_taste", new MintEffect(StatusEffectCategory.BENEFICIAL, 0x6ee0b8));


    public static final Potion SHELF_SENSE = registerPotion("shelf_sense", SHELF_SENSE_EFFECT, new Potion("shelf_sense", new StatusEffectInstance(
            SHELF_SENSE_EFFECT, 2400, 0)), new PotionRecipe(Potions.AWKWARD, WWBlocks.SHELFSHROOM.asItem()));

    public static final Potion RAGE = registerPotion("rage", RAGE_EFFECT, new Potion("rage", new StatusEffectInstance(
            RAGE_EFFECT, 2400, 0)), new PotionRecipe(Potions.SWIFTNESS, WWBlocks.RAGING_VIOLET.asItem()));

    public static final Potion GLOWING = registerPotionWithExistingEffect("glowing", new Potion("glowing", new StatusEffectInstance(
            StatusEffects.GLOWING, 2400, 0)), new PotionRecipe(Potions.AWKWARD, WWBlocks.PHOSPHOSHOOTS.asItem()));

    private static Potion registerPotion(String id, StatusEffect statusEffect, Potion potion, PotionRecipe recipe) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("wilderworld", id), statusEffect);
        Registry.register(Registry.POTION, new Identifier("wilderworld", id), potion);
        BrewingRecipeRegistryInvoker.registerPotionRecipe(recipe.base(), recipe.ingredient(), potion);
        return potion;
    }

    private static Potion registerPotionWithExistingEffect(String id, Potion potion, PotionRecipe recipe) {
        Registry.register(Registry.POTION, new Identifier("wilderworld", id), potion);
        BrewingRecipeRegistryInvoker.registerPotionRecipe(recipe.base(), recipe.ingredient(), potion);
        return potion;
    }

    private static StatusEffect registerEffect(String id, StatusEffect statusEffect) {
        Registry.register(Registry.STATUS_EFFECT, new Identifier("wilderworld", id), statusEffect);
        return statusEffect;
    }
}