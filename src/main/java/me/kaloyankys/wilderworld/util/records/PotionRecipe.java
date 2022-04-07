package me.kaloyankys.wilderworld.util.records;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

public record PotionRecipe(Potion base, Item ingredient) {
}
