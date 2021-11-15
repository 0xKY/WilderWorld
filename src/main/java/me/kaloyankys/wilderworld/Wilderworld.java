package me.kaloyankys.wilderworld;

import me.kaloyankys.wilderworld.init.WWBiomeModifications;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWEntities;
import me.kaloyankys.wilderworld.init.WWPotions;
import net.fabricmc.api.ModInitializer;

public class Wilderworld implements ModInitializer {

    @Override
    public void onInitialize() {
        new WWBiomeModifications();
        new WWBlocks();
        new WWEntities();
        WWPotions.init();
    }
}
