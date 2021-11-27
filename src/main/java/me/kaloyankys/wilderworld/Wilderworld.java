package me.kaloyankys.wilderworld;

import me.kaloyankys.wilderworld.init.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Wilderworld implements ModInitializer {

    public static final ItemGroup FF_ADDITIONS = FabricItemGroupBuilder.create(
                    new Identifier("wilderworld", "ff_additions"))
            .icon(() -> new ItemStack(WWBlocks.WISTERIA.SAPLING))
            .build();

    @Override
    public void onInitialize() {
        new WWBiomeModifications();
        new WWBlocks();
        new WWEntities();
        new WWTags();
        WWPotions.init();
    }
}
