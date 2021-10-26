package me.kaloyankys.wilderworld;

import me.kaloyankys.wilderworld.init.WWBiomeModifications;
import me.kaloyankys.wilderworld.init.WWBlocks;
import net.fabricmc.api.ModInitializer;

public class Wilderworld implements ModInitializer {

    @Override
    public void onInitialize() {
        WWBiomeModifications.initModifications();
        new WWBlocks();
    }
}
