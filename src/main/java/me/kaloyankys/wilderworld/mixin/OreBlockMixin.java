package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWParticles;
import me.kaloyankys.wilderworld.init.WWPotions;
import me.kaloyankys.wilderworld.util.classes.CustomParticleUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(OreBlock.class)
public class OreBlockMixin extends Block {
    public OreBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        PlayerEntity player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 2430, false);
        if (player != null && player.hasStatusEffect(WWPotions.CHOCOLATE_EFFECT) && random.nextInt(20 - player.getStatusEffect(WWPotions.CHOCOLATE_EFFECT).getAmplifier()) == 0) {
            CustomParticleUtil.spawnParticle(world, pos, WWParticles.SPELUNKING_GLOW,
                    UniformIntProvider.create(1, 10), player.getStatusEffect(WWPotions.CHOCOLATE_EFFECT).getAmplifier() / 4.0);
        }
        super.randomDisplayTick(state, world, pos, random);
    }
}
