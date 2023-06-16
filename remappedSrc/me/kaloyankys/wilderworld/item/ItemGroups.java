package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ItemGroups {
    public static final ItemGroup FF = FabricItemGroup.builder(new Identifier("wilderworld", "ff_additions"))
            .icon(() -> new ItemStack(WWItems.DRUPES))
            .entries(((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(new ItemStack(WWBlocks.SHELFSHROOM));
                entries.add(new ItemStack(WWBlocks.RAGING_VIOLET));
                entries.add(new ItemStack(WWBlocks.BIRD_OF_PARADISE));
                entries.add(new ItemStack(WWBlocks.CHAMOMILE));
                entries.add(new ItemStack(WWItems.DRUPES));
                entries.add(new ItemStack(WWBlocks.DROOPBLOOM));
                entries.add(new ItemStack(WWBlocks.WISTERIA_BLOSSOM_LEAVES));
                entries.add(new ItemStack(WWBlocks.ASPEN_LEAVES_AGED));
                entries.add(new ItemStack(WWItems.TAMBURA_WISTERIA));
                entries.add(new ItemStack(WWBlocks.WAX));
                entries.add(new ItemStack(WWBlocks.SCENTED_CANDLE));
                entries.add(new ItemStack(WWItems.SPAWN_BUTTERFLY));
                entries.add(new ItemStack(WWBlocks.BUTTERFLY_WING_MEMBRANE_BLOCK));
                entries.add(new ItemStack(WWItems.BUTTERFLY_WING));
            })).build();

    public static final ItemGroup ICY = FabricItemGroup.builder(new Identifier("wilderworld", "icy_additions"))
            .icon(() -> new ItemStack(WWItems.ICE_CUBE))
            .entries(((enabledFeatures, entries, operatorEnabled) -> {
                entries.add(new ItemStack(WWItems.ICE_CUBE));
                entries.add(new ItemStack(WWItems.EBONY));
                entries.add(new ItemStack(WWItems.TAMBURA_EBONY));
                entries.add(new ItemStack(WWItems.TAMBURA));
                entries.add(new ItemStack(WWItems.MINT));
                entries.add(new ItemStack(WWBlocks.CHOCOLATE_ICECREAM));
                entries.add(new ItemStack(WWBlocks.MINT_ICECREAM));
                entries.add(new ItemStack(WWBlocks.SWEETBERRY_ICECREAM));
                entries.add(new ItemStack(WWBlocks.EBONY_BUSH));
                entries.add(new ItemStack(WWBlocks.EBONY_BUSH_TALL));
                entries.add(new ItemStack(WWBlocks.TRAVERTINE));
                entries.add(new ItemStack(WWBlocks.TRAVERTINE_PEACH));
                entries.add(new ItemStack(WWBlocks.GLOWGI));
                entries.add(new ItemStack(WWBlocks.PEARL_STONE));
                entries.add(new ItemStack(WWBlocks.PEARL_CHISELED));
                entries.add(new ItemStack(WWBlocks.PEARL_TILES));
            })).build();
}

