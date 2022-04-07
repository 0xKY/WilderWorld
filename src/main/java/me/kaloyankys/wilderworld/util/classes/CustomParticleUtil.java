package me.kaloyankys.wilderworld.util.classes;

import net.minecraft.client.util.ParticleUtil;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.Arrays;

public class CustomParticleUtil extends ParticleUtil {
    public static void spawnParticle(World world, BlockPos pos, ParticleEffect effect, UniformIntProvider range, double amplifier) {
        Arrays.stream(Direction.values()).forEach((direction) -> {
            int i = range.get(world.random);
            for (int j = 0; j < i; ++j) {
                spawnParticle(world, pos, direction, effect, amplifier);
            }
        });
    }

    public static void spawnParticle(World world, BlockPos pos, Direction direction, ParticleEffect effect, double amplifier) {
        Vec3d vec3d = Vec3d.ofCenter(pos);
        int i = direction.getOffsetX();
        int j = direction.getOffsetY();
        int k = direction.getOffsetZ();
        double d = vec3d.x + (i == 0 ? MathHelper.nextDouble(world.random, -0.5, 0.5) : (double) i * 0.55);
        double e = vec3d.y + (j == 0 ? MathHelper.nextDouble(world.random, -0.5, 0.5) : (double) j * 0.55);
        double f = vec3d.z + (k == 0 ? MathHelper.nextDouble(world.random, -0.5, 0.5) : (double) k * 0.55);
        double g = i == 0 ? MathHelper.nextDouble(world.random, -1.0, 1.0) : 0.0;
        double h = j == 0 ? MathHelper.nextDouble(world.random, -1.0, 1.0) : 0.0;
        double l = k == 0 ? MathHelper.nextDouble(world.random, -1.0, 1.0) : 0.0;
        world.addParticle(effect, d, e, f, g + amplifier, h, l + amplifier);
        world.addParticle(effect, d, e, f, g + amplifier, h, l - amplifier);
        world.addParticle(effect, d, e, f, g - amplifier, h, l - amplifier);
        world.addParticle(effect, d, e, f, g - amplifier, h, l + amplifier);
    }
}
