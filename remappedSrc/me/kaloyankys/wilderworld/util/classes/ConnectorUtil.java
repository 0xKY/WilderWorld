package me.kaloyankys.wilderworld.util.classes;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.util.interfaces.WisteriaChainAffector;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class ConnectorUtil {
    public static BlockPos findTop(BlockPos start, World world, WisteriaChainAffector affector) {
        Iterable<BlockPos> chain = BlockPos.iterate(start, start.up(14));
        for (BlockPos pos : chain) {
            if (world.getBlockState(pos).isOf(WWBlocks.DROOPBLOOM)) {
                affector.affect(world, pos, world.getBlockState(pos));
            }
            if (!world.getBlockState(pos.up()).isOf(WWBlocks.DROOPBLOOM) && world.getBlockState(pos).isOf(WWBlocks.DROOPBLOOM)) {
                return pos;
            }
        }
        return null;
    }

    public static BlockPos findBottom(BlockPos start, World world, WisteriaChainAffector affector) {
        Iterable<BlockPos> chain = BlockPos.iterate(start, start.down(14));
        for (BlockPos pos : chain) {
            if (world.getBlockState(pos).isOf(WWBlocks.DROOPBLOOM)) {
                affector.affect(world, pos, world.getBlockState(pos));
            }
            if (!world.getBlockState(pos.down()).isOf(WWBlocks.DROOPBLOOM) && world.getBlockState(pos).isOf(WWBlocks.DROOPBLOOM)) {
                return pos;
            }
        }
        return null;
    }

    public static boolean placeConditions(BlockPos blockPos, WorldView worldView) {
        return (worldView.getBlockState(blockPos.up()).isOf(WWBlocks.DROOPBLOOM) && ConnectorUtil.findTop(blockPos, (World) worldView, ((w, pos, state) -> false)) != null) || worldView.getBlockState(blockPos.up()).isSolidBlock(worldView, blockPos) || worldView.getBlockState(blockPos.up()).isIn(BlockTags.LEAVES) ||
                //worldView.getBlockState(blockPos.up()).isOf(WWBlocks.WISTERIA.LEAVES)||
                worldView.getBlockState(blockPos.up()).isOf(WWBlocks.WISTERIA_BLOSSOM_LEAVES);
    }
}
