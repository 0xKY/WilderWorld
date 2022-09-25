package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWItems;
import me.kaloyankys.wilderworld.init.WWParticles;
import me.kaloyankys.wilderworld.util.interfaces.FruitBearer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class GlowBrushBlock extends SeedSpreaderBlock {
    public GlowBrushBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult collect(World world, BlockPos pos, BlockState state, ItemStack stack) {
        if (state.get(STAGES) == 1 && !stack.isOf(Items.BONE_MEAL)) {
            this.collectFruits(new Random(), world, pos, state);
            return ActionResult.SUCCESS;
        } else if (stack.isOf(Items.BONE_MEAL) && state.get(STAGES) != 1) {
            world.setBlockState(pos, state.with(STAGES, 1));
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }

    @Override
    public Item getFruit() {
        return WWItems.DRUPES;
    }

    @Override
    public int getMaxFruits() {
        return 6;
    }

    @Override
    public int getStages() {
        return 2;
    }

    @Override
    public ParticleEffect getParticles() {
        return WWParticles.GLOW_SEEDS;
    }

    @Override
    public ParticleEffect getAmbient() {
        return WWParticles.AMBIENT_GLOW_SEEDS;
    }

    @Override
    public void randomDisplayTick(BlockState blockState, World world, BlockPos blockPos, net.minecraft.util.math.random.Random random) {
        super.randomDisplayTick(blockState, world, blockPos, random);

        if (blockState.get(STAGES) == 1) {
            BlockPos.iterateOutwards(blockPos, 5, 5, 5).forEach(pos -> {
                if (random.nextInt(1000) == 0) {
                    this.addParticles(true, pos, world, 0.5 + this.throwRandomly(), 0.5 + this.throwRandomly(), 0.5 + this.throwRandomly(), new Random().nextInt(7) + 2);
                }
            });
        }
    }
}
