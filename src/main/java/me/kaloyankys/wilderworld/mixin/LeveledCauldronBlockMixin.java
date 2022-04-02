package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeveledCauldronBlock.class)
public class LeveledCauldronBlockMixin extends Block {
    public LeveledCauldronBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        IntProperty level = (IntProperty) WWBlocks.SLUSH_CAULDRON.getStateManager().getProperty("level_1_8");
        if (state.isOf(Blocks.WATER_CAULDRON)) {
            if (player.getStackInHand(hand).isOf(WWItems.ICE_CUBE)) {
                return this.fillCauldron(true, world, player, pos, level, state, hand);
            } else if (player.getStackInHand(hand).isOf(Blocks.ICE.asItem())) {
                return this.fillCauldron(false, world, player, pos, level, state, hand);
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    private ActionResult fillCauldron(boolean isCube, World world, PlayerEntity player, BlockPos pos, IntProperty level, BlockState state, Hand hand) {
        int value = isCube ? state.get(Properties.LEVEL_3) * (8 / 3) : 8;
        if (!player.isCreative()) {
            player.getStackInHand(hand).decrement(1);
        }
        world.setBlockState(pos, WWBlocks.SLUSH_CAULDRON.getDefaultState().with(level, value));
        player.swingHand(hand);
        return ActionResult.CONSUME;
    }
}
