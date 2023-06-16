package me.kaloyankys.wilderworld.util.classes;

import me.kaloyankys.wilderworld.block.CoconutBlock;
import me.kaloyankys.wilderworld.block.CoconutFruitBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoconutUtil {
    private static final BlockState FRUIT = WWBlocks.COCONUT_FRUIT.getDefaultState();
    private static final BlockState COCONUT = WWBlocks.COCONUT.getDefaultState().with(CoconutBlock.STAGE, 1);
    private static final BlockState OPEN = WWBlocks.COCONUT.getDefaultState().with(CoconutBlock.STAGE, 2);
    private static final BlockState EMPTY = WWBlocks.COCONUT.getDefaultState().with(CoconutBlock.STAGE, 3);

    public static void fall(World world, BlockPos pos) {
        world.setBlockState(pos, COCONUT);
    }

    public static int stage(World world, BlockPos pos) {
        return world.getBlockState(pos).get(CoconutFruitBlock.STAGE);
    }

    public static void grow(World world, BlockPos pos) {
        world.setBlockState(pos, world.getBlockState(pos).with(CoconutFruitBlock.STAGE, world.getBlockState(pos).get(CoconutFruitBlock.STAGE) + 1));
    }

    public static int status(World world, BlockPos pos) {
        return world.getBlockState(pos).get(CoconutBlock.STAGE);
    }

    public static void use(World world, BlockPos pos) {
        world.setBlockState(pos, world.getBlockState(pos).with(CoconutBlock.STAGE, world.getBlockState(pos).get(CoconutBlock.STAGE) + 1));
    }

    public static void crack() {

    }

    public static void drink() {

    }

}
