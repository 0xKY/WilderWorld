package me.kaloyankys.wilderworld.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ScentedCandleItem extends BlockItem {
    private final Potion potion;

    public ScentedCandleItem(Block block, Potion potion, Settings settings) {
        super(block, settings);
        this.potion = potion;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> list, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack, world, list, tooltipContext);

        potion.getEffects().forEach((sei -> {
            list.add(Text.translatable(sei.getEffectType().getTranslationKey()));
        }));

    }
}
