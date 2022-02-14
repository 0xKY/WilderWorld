package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWParticles;
import me.kaloyankys.wilderworld.item.IceCreamItem;
import me.kaloyankys.wilderworld.util.interfaces.FlavourSet;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Random;

public class IceCreamBlock extends SnowBlock {
    //public static final IntProperty LAYERS = Properties.LAYERS;
    private final FlavourSet getFlavour;

    public IceCreamBlock(Settings settings, FlavourSet flavour) {
        super(settings);
        getFlavour = flavour;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        //builder.add(LAYERS);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!(player.getStackInHand(hand).getItem() instanceof IceCreamItem) && this.getDefaultState().get(IceCreamBlock.LAYERS) > 1) {
            this.getFlavour.getEffects().forEach((player::addStatusEffect));
            this.changeLayers(world, pos, -1);
            this.spawnBreakParticles(world, player, pos, state);
            player.playSound(player.getEatSound(Items.MUSHROOM_STEW.getDefaultStack()), SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = world.getBlockState(pos).get(IceCreamBlock.LAYERS);
        if (random.nextInt(3) == 0) {
            Direction direction = Direction.random(random);
            if (direction != Direction.UP) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                if (!state.isOpaque() && !blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
                    double d = direction.getOffsetX() == 0 ? random.nextDouble() : 0.5 + (double) direction.getOffsetX() * 0.6;
                    double e = direction.getOffsetY() == 0 ? random.nextDouble() : 0.5 + (double) direction.getOffsetY() * 0.6;
                    double f = direction.getOffsetZ() == 0 ? random.nextDouble() : 0.5 + (double) direction.getOffsetZ() * 0.6;
                    world.addParticle(WWParticles.CHOCOLATE_DRIP, true, (double) pos.getX() + d, (double) pos.getY() - 1.0f + i + (e / 2.0f), (double) pos.getZ() + f, 0.0, -0.001, 0.0);
                }
            }
        }
    }

    public void changeLayers(World world, BlockPos pos, int amount) {
        if (world.getBlockState(pos).isOf(this)) {
            int i = world.getBlockState(pos).get(IceCreamBlock.LAYERS);
            world.setBlockState(pos, world.getBlockState(pos).with(IceCreamBlock.LAYERS, Math.min(8, i + amount)));
        }
    }
}
