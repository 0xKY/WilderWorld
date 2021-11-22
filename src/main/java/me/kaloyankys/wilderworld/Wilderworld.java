package me.kaloyankys.wilderworld;

import me.kaloyankys.wilderworld.init.*;
import net.fabricmc.api.ModInitializer;

public class Wilderworld implements ModInitializer {

    @Override
    public void onInitialize() {
        new WWBiomeModifications();
        new WWBlocks();
        new WWEntities();
        new WWTags();
        WWPotions.init();
    }
}
