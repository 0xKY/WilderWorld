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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class HoneyCauldronBlock extends LeveledCauldronBlock {
    public static final IntProperty LEVEL = IntProperty.of("level_1_8", 1, 8);

    public HoneyCauldronBlock(Settings settings, Predicate<Biome.Precipitation> precipitationPredicate, Map<Item, CauldronBehavior> behaviorMap) {
        super(settings, precipitationPredicate, behaviorMap);
    }

    public static void decrementFluidLevel(BlockState state, World world, BlockPos pos) {
        int i = state.get(HoneyCauldronBlock.LEVEL) - 1;
        world.setBlockState(pos, i == 0 ? Blocks.CAULDRON.getDefaultState() : state.with(HoneyCauldronBlock.LEVEL, i));
    }

    public static void incrementFluidLevel(BlockState state, World world, BlockPos pos) {
        int i = state.get(HoneyCauldronBlock.LEVEL) + 1;
        world.setBlockState(pos, i == 9 ? state.with(HoneyCauldronBlock.LEVEL, 8) : state.with(HoneyCauldronBlock.LEVEL, i));
        world.addBlockBreakParticles(new BlockPos((int) (pos.getX() + 0.5), pos.getY(), (int) (pos.getZ() + 0.5)), Blocks.HONEY_BLOCK.getDefaultState());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HoneyCauldronBlock.LEVEL);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if (stack.isOf(Items.GLASS_BOTTLE)) {
            HoneyCauldronBlock.decrementFluidLevel(state, world, pos);
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(WWItems.WILD_HONEY_BOTTLE));
            stack.decrement(1);
            return ActionResult.SUCCESS;
        } else if (stack.isOf(WWItems.WILD_HONEY_BOTTLE)) {
            HoneyCauldronBlock.incrementFluidLevel(state, world, pos);
            stack.decrement(1);
            return ActionResult.SUCCESS;
        } else if (stack.isOf(Items.POTION)) {
            Potion potion = PotionUtil.getPotion(stack);
            ItemStack antidote = WWItems.WILD_HONEY_BOTTLE.getDefaultStack();
            PotionUtil.setPotion(antidote, potion);
            antidote.setCustomName(Text.of("Andidote"));
            PotionUtil.buildTooltip(antidote, new ArrayList<>(), 1f);
            stack.decrement(1);
            HoneyCauldronBlock.decrementFluidLevel(state, world, pos);
            player.giveItemStack(antidote);
        }
        return ActionResult.PASS;
    }

    @Override
    public boolean isFull(BlockState state) {
        return state.get(HoneyCauldronBlock.LEVEL) == 8;
    }

    @Override
    protected double getFluidHeight(BlockState state) {
        return (6.0 + state.get(HoneyCauldronBlock.LEVEL) * 8.0) / 16.0;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(HoneyCauldronBlock.LEVEL);
    }
}
