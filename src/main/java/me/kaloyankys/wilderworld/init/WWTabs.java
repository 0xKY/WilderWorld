package me.kaloyankys.wilderworld.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class WWTabs {
    public static final ItemGroup FF = register("ff_additions", FabricItemGroup.builder().displayName(Text.of("Flower Forest Additions"))
            .icon(() -> new ItemStack(Objects.requireNonNull(WWBlocks.findBlock("aspen_sapling")).asItem()))
            .entries(((a, entries) -> {
                entries.add(new ItemStack(WWBlocks.SHELFSHROOM));
                entries.add(new ItemStack(WWBlocks.RAGING_VIOLET));
                entries.add(new ItemStack(WWBlocks.BIRD_OF_PARADISE));
                entries.add(new ItemStack(WWBlocks.CHAMOMILE));
                entries.add(new ItemStack(WWItems.DRUPES));
                entries.add(new ItemStack(WWBlocks.DROOPBLOOM));
                entries.add(new ItemStack(WWBlocks.WISTERIA_BLOSSOM_LEAVES));
                entries.add(new ItemStack(WWBlocks.WISTERIA_LEAVES));
                entries.add(new ItemStack(WWBlocks.ASPEN_LEAVES_AGED));
                entries.add(new ItemStack(WWBlocks.ASPEN_LEAVES));
                entries.add(new ItemStack(WWItems.TAMBURA_WISTERIA));
                entries.add(new ItemStack(WWItems.WILD_WAX));
                entries.add(new ItemStack(WWItems.WILD_HONEY_BOTTLE));
                entries.add(new ItemStack(WWBlocks.WAX));
                entries.add(new ItemStack(WWBlocks.MOSAIC));
                entries.add(new ItemStack(WWBlocks.SCENTED_CANDLE));
                entries.add(new ItemStack(WWItems.SPAWN_BUTTERFLY));
                entries.add(new ItemStack(WWBlocks.BUTTERFLY_WING_MEMBRANE_BLOCK));
                entries.add(new ItemStack(WWItems.BUTTERFLY_WING));
                entries.add(new ItemStack(WWItems.FLOWER_FEED));
                WWBlocks.WOOD_BLOCKS.forEach(((id, block) -> {
                    if (!id.contains("sign")) {
                        entries.add(block.asItem());
                    }
                }));
                //@TODO WWBlocks.WOOD_ITEMS.forEach((id, item) -> entries.add(item));
            })).build());

    public static final ItemGroup ICY = register("icy_additions", FabricItemGroup.builder().displayName(Text.of("Icy Additions"))
            .icon(() -> new ItemStack(WWItems.ICE_CUBE))
            .entries(((a, entries) -> {
                entries.add(new ItemStack(WWItems.SPAWN_DOOD));
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
            })).build());

    /*public static final ItemGroup TROPICAL = register("tropical_additions", FabricItemGroup.builder().displayName(Text.of("Icy Additions"))
            .icon(() -> new ItemStack(WWBlocks.COCONUT.asItem()))
            .entries(((a, entries) -> {
                entries.add(new ItemStack(WWBlocks.PALM_TRUNK));
                entries.add(new ItemStack(WWBlocks.FROND));
                entries.add(new ItemStack(WWBlocks.COCONUT));
                entries.add(new ItemStack(WWBlocks.COCONUT_FRUIT));
            })).build());*/

    public static ItemGroup register(String id, ItemGroup group) {
        Registry.register(Registries.ITEM_GROUP, new Identifier("wilderworld", id), group);
        return group;
    }


}
