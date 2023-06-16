package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.util.classes.CoconutUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CoconutBlock extends FallingBlock {
    public static final IntProperty STAGE = IntProperty.of("stage", 1, 3);

    public CoconutBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(STAGE);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
        int stage = CoconutUtil.status(world, blockPos);
        if (stage < 3) {
            CoconutUtil.use(world, blockPos);
            if (stage == 1) {
                CoconutUtil.crack();
            } else if (stage == 2) {
                CoconutUtil.drink();
            }
        }

        return super.onUse(blockState, world, blockPos, playerEntity, hand, blockHitResult);
    }
}
