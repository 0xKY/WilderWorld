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

public abstract class SeedSpreaderBlock extends PlantBlock implements FruitBearer {
    public static final IntProperty STAGES = FruitBearer.createProperty(2);
    public SeedSpreaderBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
        return this.collect(world, blockPos, blockState, playerEntity.getStackInHand(hand));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.fullCube();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(STAGES);
    }

    public double throwRandomly() {
        Random random = new Random();
        double d = 0;
        switch (random.nextInt(2)) {
            case (0) :
                d = random.nextDouble();
            case (1) :
                d = random.nextDouble() * -1;
        }
        return d;
    }

    public void addParticles(boolean ambient, BlockPos pos, World world, double x, double y, double z, int i) {
        if (world.isClient) {
            for (int j = 0; j < i; ++j) {
                if (ambient) {
                    world.addParticle(this.getAmbient(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, x, y, z);
                } else {
                    world.addParticle(this.getParticles(), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, x, y, z);
                }
            }
        }
    }

    public void collectFruits(Random random, World world, BlockPos pos, BlockState state) {
        for (int i = random.nextInt(this.getMaxFruits()); i < this.getMaxFruits(); i++) {
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.getFruit()));
        }
        world.setBlockState(pos, state.with(STAGES, 2));

        this.addParticles(false, pos, world, 0.5 + this.throwRandomly(), 1 + this.throwRandomly(), 0.5 + this.throwRandomly(), new Random().nextInt(7) + 2);
    }
}
