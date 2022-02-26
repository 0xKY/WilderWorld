package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.biome.Biome;

import java.util.Map;
import java.util.function.Predicate;

public class SlushCauldronBlock extends LeveledCauldronBlock {
    public static final IntProperty LEVEL = IntProperty.of("level_1_7", 1, 7);
    private final Predicate<Biome.Precipitation> precipitationPredicate;

    public SlushCauldronBlock(Settings settings, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, precipitationPredicate, behaviorMap);
        this.precipitationPredicate = precipitationPredicate;
    }

    public static void decrementFluidLevel(BlockState state, World world, BlockPos pos) {
        int i = state.get(SlushCauldronBlock.LEVEL) - 1;
        world.setBlockState(pos, i == 0 ? Blocks.CAULDRON.getDefaultState() : state.with(SlushCauldronBlock.LEVEL, i));
    }

    public static void incrementFluidLevel(BlockState state, World world, BlockPos pos) {
        int i = state.get(SlushCauldronBlock.LEVEL) + 1;
        world.setBlockState(pos, i == 8 ? state.with(SlushCauldronBlock.LEVEL, 7) : state.with(SlushCauldronBlock.LEVEL, i));
    }

    public static boolean canFillWithPrecipitation(World world, Biome.Precipitation precipitation) {
        if (precipitation == Biome.Precipitation.RAIN) {
            return world.getRandom().nextFloat() < 0.05f;
        }
        if (precipitation == Biome.Precipitation.SNOW) {
            return world.getRandom().nextFloat() < 0.1f;
        }
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SlushCauldronBlock.LEVEL);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isEmpty()) {
            SlushCauldronBlock.decrementFluidLevel(state, world, pos);
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(WWItems.ICE_CUBE));
        } else if (player.getStackInHand(hand).isOf(WWItems.ICE_CUBE)) {
            SlushCauldronBlock.incrementFluidLevel(state, world, pos);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(SlushCauldronBlock.LEVEL) == 7;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (6.0 + state.get(SlushCauldronBlock.LEVEL) * 7.0) / 16.0;
    }

    @Override
    public void precipitationTick(BlockState state, World world, BlockPos pos, Biome.Precipitation precipitation) {
        if (SlushCauldronBlock.canFillWithPrecipitation(world, precipitation) || state.get(SlushCauldronBlock.LEVEL) != 7 || this.precipitationPredicate.test(precipitation)) {
            world.setBlockState(pos, state.cycle(SlushCauldronBlock.LEVEL));
        }
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(SlushCauldronBlock.LEVEL);
    }

    @Override
    protected void fillFromDripstone(BlockState state, World world, BlockPos pos, Fluid fluid) {
        if (!this.isFull(state)) {
            world.setBlockState(pos, state.with(SlushCauldronBlock.LEVEL, state.get(SlushCauldronBlock.LEVEL) + 1));
            world.syncWorldEvent(WorldEvents.POINTED_DRIPSTONE_DRIPS_WATER_INTO_CAULDRON, pos, 0);
        }
    }
}
