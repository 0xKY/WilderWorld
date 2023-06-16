package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.block.PontoonBlock;
import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class PontoonItem extends BlockItem {
    public PontoonItem(Block block, Settings settings) {
        super(block, settings);
    }

   @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
       ItemStack itemStack = playerEntity.getStackInHand(hand);
       BlockHitResult blockHitResult = raycast(world, playerEntity, RaycastContext.FluidHandling.SOURCE_ONLY);

       BlockPos pos = blockHitResult.getBlockPos();
       BlockState state = world.getBlockState(pos);
       BlockState stateAbove = world.getBlockState(pos.up());
       FluidState fluid = world.getFluidState(pos);
       Direction facing = playerEntity.getHorizontalFacing();

       playerEntity.swingHand(hand);
       if (fluid.isOf(Fluids.WATER) && stateAbove.isOf(Blocks.AIR)) {
           if (!playerEntity.isCreative()) {
               itemStack.decrement(1);
           }
           world.setBlockState(pos, WWBlocks.PONTOON.getDefaultState().with(PontoonBlock.FACING, facing).with(PontoonBlock.WATERLOGGED, true));
           return TypedActionResult.success(itemStack);
       }

       return super.use(world, playerEntity, hand);
   }

   @Override
    public ActionResult place(ItemPlacementContext itemPlacementContext) {
        World world = itemPlacementContext.getWorld();
        PlayerEntity player = itemPlacementContext.getPlayer();
        Vec3d hit = itemPlacementContext.getHitPos();
        Hand hand = itemPlacementContext.getHand();
        ItemStack itemStack = player.getStackInHand(hand);
        Direction facing = player.getHorizontalFacing();
        BlockPos pos = itemPlacementContext.getBlockPos();
        BlockState state = world.getBlockState(pos.offset(itemPlacementContext.getPlayerLookDirection()));

        /* System.out.println(state + " s1");
        System.out.println(pos + " p1");
        System.out.println(facing + " d1");

        if (state.isOf(WWBlocks.PONTOON)) {
            for (int i = 0; i < 16; i++) {
                BlockPos frontPos = pos.offset(facing, i);
                BlockState frontState = world.getBlockState(frontPos);

                System.out.println(frontState + " s2");
                System.out.println(frontPos + " p2");
                System.out.println(facing + " d2");


                if (frontState.isAir()) {
                    world.setBlockState(frontPos, WWBlocks.PONTOON.getDefaultState().with(PontoonBlock.FACING, facing));
                    return ActionResult.CONSUME;
                } else {
                    world.setBlockState(frontPos, Blocks.BONE_BLOCK.getDefaultState());
                }
            } */
       world.setBlockState(pos, WWBlocks.PONTOON.getDefaultState().with(PontoonBlock.FACING, facing).with(PontoonBlock.WATERLOGGED, false));
       return super.place(itemPlacementContext);
    }
}
