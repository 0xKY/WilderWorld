package me.kaloyankys.wilderworld;

import me.kaloyankys.wilderworld.init.*;
import me.kaloyankys.wilderworld.recipe.ChewingRecipe;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.gen.feature.PlacedFeatures;
import org.apache.commons.compress.compressors.lz77support.LZ77Compressor;
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
        WWFeatures.registerFeatures();
        new WWFeatures();
        new WWFoliagePlacers();

        STRIPPABLE.forEach(StrippableBlockRegistry::register);


        LOGGER.info("Things are gettin' wild!");
    }
}
