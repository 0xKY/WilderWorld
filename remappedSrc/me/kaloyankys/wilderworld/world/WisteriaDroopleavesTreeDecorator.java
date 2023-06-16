package me.kaloyankys.wilderworld.world;

import com.mojang.serialization.Codec;
import me.kaloyankys.wilderworld.block.VerticalConnectorBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.mixin.TreeDecoratorTypeInvoker;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AttachedToLeavesTreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.HashSet;
import java.util.List;

public class WisteriaDroopleavesTreeDecorator extends AttachedToLeavesTreeDecorator {
    public static final WisteriaDroopleavesTreeDecorator DECORATOR = new WisteriaDroopleavesTreeDecorator();
    public static final Codec<WisteriaDroopleavesTreeDecorator> CODEC = Codec.unit(() -> DECORATOR);
    public static TreeDecoratorType<WisteriaDroopleavesTreeDecorator> WISTERIA = register();

    protected final float probability = 1.0f;
    protected final int exclusionRadiusXZ = 1;
    protected final int exclusionRadiusY = 0;
    protected final BlockStateProvider blockProvider = BlockStateProvider.of(WWBlocks.DROOPBLOOM);
    protected final BlockStateProvider blockProviderBlossom = BlockStateProvider.of(WWBlocks.WISTERIA_BLOSSOM_LEAVES);
    protected final int requiredEmptyBlocks = 1;
    protected final List<Direction> directions = List.of(Direction.DOWN);

    public WisteriaDroopleavesTreeDecorator() {
        super(0.5f, 1, 0, SimpleBlockStateProvider.of(WWBlocks.DROOPBLOOM), 1, List.of(Direction.DOWN));
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return WISTERIA;
    }

    @Override
    public void generate(TreeDecorator.Generator generator) {
        HashSet<BlockPos> set = new HashSet<BlockPos>();
        Random random = generator.getRandom();
        for (BlockPos blockPos : Util.copyShuffled(generator.getLeavesPositions(), random)) {
            Direction direction;
            BlockPos blockPos2 = blockPos.offset(direction = Util.getRandom(this.directions, random));
            if (set.contains(blockPos2) || !(random.nextFloat() < this.probability) || !this.meetsRequiredEmptyBlocks(generator, blockPos, direction)) continue;
            BlockPos blockPos3 = blockPos2.add(-this.exclusionRadiusXZ, -this.exclusionRadiusY, -this.exclusionRadiusXZ);
            BlockPos blockPos4 = blockPos2.add(this.exclusionRadiusXZ, this.exclusionRadiusY, this.exclusionRadiusXZ);
            for (BlockPos blockPos5 : BlockPos.iterate(blockPos3, blockPos4)) {
                set.add(blockPos5.toImmutable());
            }
            if (random.nextInt(2) == 0) {
                if (generator.isAir(blockPos2.down(1))) {
                    if (random.nextInt(2) == 0) {
                        generator.replace(blockPos2, this.blockProvider.get(random, blockPos2).with(VerticalConnectorBlock.TYPE, 3));
                        generator.replace(blockPos2.down(1), this.blockProvider.get(random, blockPos2).with(VerticalConnectorBlock.TYPE, 1));
                    } else if (generator.isAir(blockPos.down(3))) {
                        generator.replace(blockPos2, this.blockProviderBlossom.get(random, blockPos2));
                        generator.replace(blockPos2.down(1), this.blockProvider.get(random, blockPos2).with(VerticalConnectorBlock.TYPE, 2));
                        generator.replace(blockPos2.down(2), this.blockProvider.get(random, blockPos2).with(VerticalConnectorBlock.TYPE, 1));
                    }
                }
            } else {
                if (random.nextInt(2) == 0) {
                    generator.replace(blockPos2, this.blockProvider.get(random, blockPos2));
                } else {
                    generator.replace(blockPos2, this.blockProviderBlossom.get(random, blockPos2));
                }
            }
        }
    }

    private boolean meetsRequiredEmptyBlocks(TreeDecorator.Generator generator, BlockPos blockPos, Direction direction) {
        for (int i = 1; i <= this.requiredEmptyBlocks; ++i) {
            BlockPos blockPos2 = blockPos.offset(direction, i);
            if (generator.isAir(blockPos2)) continue;
            return false;
        }
        return true;
    }

    private static TreeDecoratorType<WisteriaDroopleavesTreeDecorator> register() {
        WISTERIA = TreeDecoratorTypeInvoker.createType(CODEC);
        Registry.register(Registries.TREE_DECORATOR_TYPE, new Identifier("wilderworld", "wisteria_decorator"), WISTERIA);
        return WISTERIA;
    }
}