package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.recipe.SteamingRecipe;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WWRecipes {
    public static final RecipeType<SteamingRecipe> CHEWING = register(
            SteamingRecipe.Serializer.INSTANCE, SteamingRecipe.Serializer.ID, SteamingRecipe.Type.INSTANCE, SteamingRecipe.Type.ID);

    public static <IN extends Inventory, R extends Recipe<IN>> RecipeType<R> register(RecipeSerializer<R> serializerInstance, String serializerId, RecipeType<R> typeInstance, String typeId) {
        Registry.register(Registries.RECIPE_SERIALIZER, new Identifier("wilderworld", serializerId), serializerInstance);
        Registry.register(Registries.RECIPE_TYPE, new Identifier("wilderworld", typeId), typeInstance);
        return typeInstance;
    }
}
