package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWParticles;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block {
    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance);
        for (int i = 0; i < 3 + new Random().nextInt(3); i++) {
            if (entity instanceof PlayerEntity player) {
                spawnBreakParticles(world, player, pos, state);
            }
            leafFall(world, pos, new Random(), -2, 2);
        }
        world.breakBlock(pos, false);
        world.setBlockState(pos, WWBlocks.LEAVES_PILE.getDefaultState());
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (random.nextInt(25) == 0) {
            leafFall(world, pos, random, -1, 1);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (new Random().nextInt(5) == 0 && (entity.getVelocity().getX() > 0 || entity.getVelocity().getZ() > 0)) {
            if (entity instanceof PlayerEntity player) {
                spawnBreakParticles(world, player, pos, state);
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.cuboid(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    }

    private void leafFall(World world, BlockPos pos, Random random, int x, int y) {
        for (int i = x; i <= y; i++) {
            for (int j = x; j <= y; j++) {
                if (world.getBlockState(pos.add(i, 0, j)).isOf(Blocks.AIR)) {
                    world.addParticle(WWParticles.LEAF, true, pos.getX() + random.nextFloat() - 0.5f * j, pos.getY() + i / 5.0f, pos.getZ() + random.nextFloat() - 0.5f * j,
                            0.0f, 0.0f, 0.0f);
                }
            }
        }
    }
}
