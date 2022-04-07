package me.kaloyankys.wilderworld.util.classes;

import me.kaloyankys.wilderworld.block.IceCreamBlock;
import me.kaloyankys.wilderworld.init.WWPotions;
import me.kaloyankys.wilderworld.util.interfaces.FlavourSet;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IceCreamUtil {

    public static void applyEffectCorrectly(FlavourSet flavour, PlayerEntity player) {
        int amplifier = player.hasStatusEffect(flavour.getEffect()) ? player.getStatusEffect(flavour.getEffect()).getAmplifier() + 1 : 0;
        if (amplifier < 3) {
            if (flavour.getEffect().isInstant()) {
                flavour.getEffect().applyInstantEffect(player, player, player, amplifier, 1);
            } else if (flavour.getEffect() == WWPotions.MINT_EFFECT) {
                player.addStatusEffect(new StatusEffectInstance(flavour.getEffect(), 8000, amplifier));
            } else {
                player.addStatusEffect(new StatusEffectInstance(flavour.getEffect(), 400, amplifier));
            }
        }
    }

    public static void changeLayers(World world, BlockPos pos, Block block, int amount) {
        if (world.getBlockState(pos).isOf(block)) {
            int i = world.getBlockState(pos).get(IceCreamBlock.LAYERS);
            world.setBlockState(pos, world.getBlockState(pos).with(IceCreamBlock.LAYERS, Math.min(8, i + amount)));
        }
    }
}
