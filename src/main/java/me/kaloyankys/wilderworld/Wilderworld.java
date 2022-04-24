package me.kaloyankys.wilderworld;

import me.kaloyankys.wilderworld.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Wilderworld implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("wilderworld");

    public static final ItemGroup FF_ADDITIONS = FabricItemGroupBuilder.create(
                    new Identifier("wilderworld", "ff_additions"))
            .icon(() -> new ItemStack(WWBlocks.WISTERIA.SAPLING))
            .build();

    public static final ItemGroup ICY_ADDITIONS = FabricItemGroupBuilder.create(
                    new Identifier("wilderworld", "icy_additions"))
            .icon(() -> new ItemStack(WWItems.ICE_CUBE))
            .build();

    @Override
    public void onInitialize() {
        new WWBiomeModifications();
        new WWBlocks();
        new WWEntities();
        new WWTags();
        new WWItems();
        new WWPotions();
        new WWSounds();

        LOGGER.info("Things are gettin' wild!");
    }
}
