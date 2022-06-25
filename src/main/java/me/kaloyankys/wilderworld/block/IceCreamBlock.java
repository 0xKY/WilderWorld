package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.util.classes.IceCreamUtil;
import me.kaloyankys.wilderworld.util.interfaces.FlavourSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IceCreamBlock extends SnowBlock {
    private final FlavourSet flavour;
    private final DefaultParticleType particle;

    public IceCreamBlock(Settings settings, FlavourSet flavour) {
        super(settings);
        this.flavour = flavour;
        this.particle = flavour.getParticle();
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getStackInHand(hand).isOf(Items.BOWL) && player.isSneaking()) {
            this.spawnBreakParticles(world, player, pos, state);
            player.playSound(player.getEatSound(Items.MUSHROOM_STEW.getDefaultStack()), SoundCategory.PLAYERS, 1.0f, 1.0f);
            IceCreamUtil.applyEffectCorrectly(flavour, player);
            if ((state.get(IceCreamBlock.LAYERS) > 1)) {
                IceCreamUtil.changeLayers(world, pos, this, -1);
            } else {
                world.breakBlock(pos, false);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }


    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int i = world.getBlockState(pos).get(IceCreamBlock.LAYERS);
        if (random.nextInt(2) == 0) {
            Direction direction = Direction.random(random);
            if (direction != Direction.UP) {
                BlockPos blockPos = pos.offset(direction);
                BlockState blockState = world.getBlockState(blockPos);
                if (!state.isOpaque() && !blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
                    double d = direction.getOffsetX() == 0 ? random.nextDouble() : 0.5 + (double) direction.getOffsetX() * 0.6;
                    double e = direction.getOffsetY() == 0 ? random.nextDouble() : 0.5 + (double) direction.getOffsetY() * 0.6;
                    double f = direction.getOffsetZ() == 0 ? random.nextDouble() : 0.5 + (double) direction.getOffsetZ() * 0.6;
                    world.addParticle(particle, true, (double) pos.getX() + d, (double) pos.getY() - 0.5f + (i / 6.0f) + (e / 2.0f), (double) pos.getZ() + f, 0.0, -0.001, 0.0);
                }
            }
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (placer instanceof PlayerEntity player) player.giveItemStack(new ItemStack(Items.BOWL));
        super.onPlaced(world, pos, state, placer, itemStack);
    }
}
