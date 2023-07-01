package me.kaloyankys.wilderworld.world;

import com.mojang.serialization.Codec;
import me.kaloyankys.wilderworld.block.ShelfshroomBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.mixin.TreeDecoratorTypeInvoker;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShelfshroomTreeDecorator extends TreeDecorator {

    public static final ShelfshroomTreeDecorator DECORATOR = new ShelfshroomTreeDecorator();
    public static final Codec<ShelfshroomTreeDecorator> CODEC = Codec.unit(() -> DECORATOR);
    //public static TreeDecoratorType<ShelfshroomTreeDecorator> SHELFSHROOM = register();

    @Override
    protected TreeDecoratorType<?> getType() {
        //return SHELFSHROOM;
        return TreeDecoratorType.TRUNK_VINE;
    }

    @Override
    public void generate(Generator generator) {
        List<BlockPos> logPositions = generator.getLogPositions();
        Random random = Random.create();
        TestableWorld world = generator.getWorld();
        List<Integer> logYs = new ArrayList<>();
        logPositions.forEach((pos -> logYs.add(pos.getY())));
        int minY = Collections.min(logYs) + 12;
        if (world instanceof ChunkRegion world1) {
            for (BlockPos pos : logPositions) {
                Direction dir = Direction.fromHorizontal(random.nextInt(4));
                if (world1.getBlockState(pos.offset(dir)).isAir() && minY > pos.getY() && random.nextInt(5) == 0) {
                    world1.setBlockState(pos.offset(dir), WWBlocks.SHELFSHROOM.getDefaultState().with(ShelfshroomBlock.FACING, dir), 3);
                }
            }
        } else if (world instanceof World world1) {
            for (BlockPos pos : logPositions) {
                Direction dir = Direction.fromHorizontal(random.nextInt(4));
                if (world1.getBlockState(pos.offset(dir)).isAir() && minY > pos.getY() && random.nextInt(5) == 0) {
                    world1.setBlockState(pos.offset(dir), WWBlocks.SHELFSHROOM.getDefaultState().with(ShelfshroomBlock.FACING, dir), 3);
                }
            }
        }
    }

    //private static TreeDecoratorType<ShelfshroomTreeDecorator> register() {
        //SHELFSHROOM = TreeDecoratorTypeInvoker.createType(CODEC);
        //Registry.register(Registries.TREE_DECORATOR_TYPE, new Identifier("wilderworld", "shelfshroom"), SHELFSHROOM);
        //return SHELFSHROOM;
    //}
}