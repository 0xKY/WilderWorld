package me.kaloyankys.wilderworld;

import me.kaloyankys.wilderworld.init.*;
import me.kaloyankys.wilderworld.recipe.ChewingRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class Wilderworld implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("wilderworld");
    public static final HashMap<Block, Block> STRIPPABLE = new HashMap<>();

    @Override
    public void onInitialize() {
        new WWBiomeModifications();
        new WWBlocks();
        new WWEntities();
        new WWTags();
        new WWItems();
        new WWPotions();
        new WWSounds();
        new WWNetwork();
        new ChewingRecipe();
        new WWFeatures();
        WWFeatures.addFeatures();
        new WWFeatures();
        new WWFoliagePlacers();
        new WWTabs();
        WWBlocks.registerWood();

        STRIPPABLE.forEach(StrippableBlockRegistry::register);


        LOGGER.info("Things are gettin' wild!");
    }
}
