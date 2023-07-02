package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChiseledBookshelfBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Optional;

public class MosaicBlock extends ChiseledBookshelfBlock {
    public MosaicBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
        BlockEntity var8 = world.getBlockEntity(blockPos);
        if (var8 instanceof ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity) {
            Optional<Vec2f> optional = getHitPos(blockHitResult, blockState.get(HorizontalFacingBlock.FACING));
            if (optional.isEmpty()) {
                return ActionResult.PASS;
            } else {
                int i = getSlotForHitPos(optional.get());
                if (blockState.get(SLOT_OCCUPIED_PROPERTIES.get(i))) {
                    tryRemoveBook(world, blockPos, playerEntity, chiseledBookshelfBlockEntity, i);
                    return ActionResult.success(world.isClient);
                } else {
                    ItemStack itemStack = playerEntity.getStackInHand(hand);
                    if (itemStack.isOf(WWItems.WILD_WAX)) {
                        ItemStack book = Items.BOOK.getDefaultStack();
                        tryAddBook(world, blockPos, playerEntity, chiseledBookshelfBlockEntity, book, i);
                        return ActionResult.success(world.isClient);
                    } else {
                        return ActionResult.CONSUME;
                    }
                }
            }
        } else {
            return ActionResult.PASS;
        }
    }

    private static void tryAddBook(World world, BlockPos blockPos, PlayerEntity playerEntity, ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity, ItemStack itemStack, int i) {
        if (!world.isClient) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            SoundEvent soundEvent = SoundEvents.BLOCK_CHISELED_BOOKSHELF_INSERT;
            chiseledBookshelfBlockEntity.setStack(i, itemStack.split(1));
            world.playSound(null, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (playerEntity.isCreative()) {
                itemStack.increment(1);
            }

            world.emitGameEvent(playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
        }
    }

    private static void tryRemoveBook(World world, BlockPos blockPos, PlayerEntity playerEntity, ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity, int i) {
        if (!world.isClient) {
            ItemStack book = chiseledBookshelfBlockEntity.removeStack(i, 1);
            ItemStack itemStack = WWItems.WILD_WAX.getDefaultStack();
            SoundEvent soundEvent = SoundEvents.BLOCK_CHISELED_BOOKSHELF_PICKUP;
            world.playSound(null, blockPos, soundEvent, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (!playerEntity.getInventory().insertStack(itemStack)) {
                playerEntity.dropItem(itemStack, false);
            }

            world.emitGameEvent(playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
        }
    }

    private static Optional<Vec2f> getHitPos(BlockHitResult blockHitResult, Direction direction) {
        Direction direction2 = blockHitResult.getSide();
        if (direction != direction2) {
            return Optional.empty();
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos().offset(direction2);
            Vec3d vec3d = blockHitResult.getPos().subtract(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            double d = vec3d.getX();
            double e = vec3d.getY();
            double f = vec3d.getZ();
            return switch (direction2) {
                case NORTH -> Optional.of(new Vec2f((float) (1.0 - d), (float) e));
                case SOUTH -> Optional.of(new Vec2f((float) d, (float) e));
                case WEST -> Optional.of(new Vec2f((float) f, (float) e));
                case EAST -> Optional.of(new Vec2f((float) (1.0 - f), (float) e));
                case DOWN, UP -> Optional.empty();
            };
        }
    }

    private static int getSlotForHitPos(Vec2f vec2f) {
        int i = vec2f.y >= 0.5F ? 0 : 1;
        int j = getColumn(vec2f.x);
        return j + i * 3;
    }

    private static int getColumn(float f) {
        if (f < 0.375F) {
            return 0;
        } else {
            return f < 0.6875F ? 1 : 2;
        }
    }

    @Override
    public void onStateReplaced(BlockState blockState, World world, BlockPos blockPos, BlockState blockState2, boolean bl) {
        if (!blockState.isOf(blockState2.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(blockPos);
            if (blockEntity instanceof ChiseledBookshelfBlockEntity chiseledBookshelfBlockEntity) {
                if (!chiseledBookshelfBlockEntity.isEmpty()) {
                    for(int i = 0; i < 6; ++i) {
                        ItemStack itemStack = chiseledBookshelfBlockEntity.getStack(i);
                        if (!itemStack.isEmpty()) {
                            ItemScatterer.spawn(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(WWItems.WILD_WAX));
                        }
                    }

                    chiseledBookshelfBlockEntity.clear();
                    world.updateComparators(blockPos, this);
                }
            }

            super.onStateReplaced(blockState, world, blockPos, blockState2, bl);
        }
    }
}
