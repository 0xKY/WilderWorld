package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWItems;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(BeehiveBlock.class)
public abstract class BeehiveBlockMixin extends BlockWithEntity {
    protected BeehiveBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity2, Hand hand, BlockHitResult blockHitResult) {
        ItemStack itemStack = playerEntity2.getStackInHand(hand);
        ItemStack honeyBottle = world.getBiome(blockPos).getKey().get() == BiomeKeys.FLOWER_FOREST ? new ItemStack(WWItems.WILD_HONEY_BOTTLE) : new ItemStack(Items.HONEY_BOTTLE);
        int i = blockState.get(BeehiveBlock.HONEY_LEVEL);
        boolean bl = false;
        if (i >= 5) {
            Item item = itemStack.getItem();
            if (itemStack.isOf(Items.SHEARS)) {
                world.playSound(playerEntity2, playerEntity2.getX(), playerEntity2.getY(), playerEntity2.getZ(), SoundEvents.BLOCK_BEEHIVE_SHEAR, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                this.drop(world, blockPos);
                itemStack.damage(1, playerEntity2, playerEntity -> playerEntity.sendToolBreakStatus(hand));
                bl = true;
                world.emitGameEvent(playerEntity2, GameEvent.SHEAR, blockPos);
            } else if (itemStack.isOf(Items.GLASS_BOTTLE)) {
                itemStack.decrement(1);
                world.playSound(playerEntity2, playerEntity2.getX(), playerEntity2.getY(), playerEntity2.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
                if (itemStack.isEmpty()) {
                    playerEntity2.setStackInHand(hand, honeyBottle);
                } else if (!playerEntity2.getInventory().insertStack(honeyBottle)) {
                    playerEntity2.dropItem(honeyBottle, false);
                }
                bl = true;
                world.emitGameEvent(playerEntity2, GameEvent.FLUID_PICKUP, blockPos);
            }
            if (!world.isClient() && bl) {
                playerEntity2.incrementStat(Stats.USED.getOrCreateStat(item));
            }
        }
        if (bl) {
            if (!CampfireBlock.isLitCampfireInRange(world, blockPos)) {
                if (this.hasBees(world, blockPos)) {
                    this.angerNearbyBees(world, blockPos);
                }
                this.takeHoney(world, blockState, blockPos, playerEntity2, BeehiveBlockEntity.BeeState.EMERGENCY);
            } else {
                this.takeHoney(world, blockState, blockPos);
            }
            return ActionResult.success(world.isClient);
        }
        return super.onUse(blockState, world, blockPos, playerEntity2, hand, blockHitResult);
    }

    public void takeHoney(World world, BlockState blockState, BlockPos blockPos, @Nullable PlayerEntity playerEntity, BeehiveBlockEntity.BeeState beeState) {
        this.takeHoney(world, blockState, blockPos);
        BlockEntity blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof BeehiveBlockEntity beehiveBlockEntity) {
            beehiveBlockEntity.angerBees(playerEntity, blockState, beeState);
        }
    }

    public void takeHoney(World world, BlockState blockState, BlockPos blockPos) {
        world.setBlockState(blockPos, blockState.with(BeehiveBlock.HONEY_LEVEL, 0), 3);
    }

    private boolean hasBees(World world, BlockPos blockPos) {
        BlockEntity blockEntity = world.getBlockEntity(blockPos);
        if (blockEntity instanceof BeehiveBlockEntity beehiveBlockEntity) {
            return !beehiveBlockEntity.hasNoBees();
        }
        return false;
    }

    private void angerNearbyBees(World world, BlockPos blockPos) {
        List<BeeEntity> list = world.getNonSpectatingEntities(BeeEntity.class, new Box(blockPos).expand(8.0, 6.0, 8.0));
        if (!list.isEmpty()) {
            List<PlayerEntity> list2 = world.getNonSpectatingEntities(PlayerEntity.class, new Box(blockPos).expand(8.0, 6.0, 8.0));
            int i = list2.size();
            for (BeeEntity beeEntity : list) {
                if (beeEntity.getTarget() != null) continue;
                beeEntity.setTarget(list2.get(world.random.nextInt(i)));
            }
        }
    }

    private void drop(World world, BlockPos blockPos) {
        ItemStack drop = world.getBiome(blockPos).getKey().get() == BiomeKeys.FLOWER_FOREST ? new ItemStack(WWItems.WILD_WAX, 3) : new ItemStack(Items.HONEYCOMB, 3);
        BeehiveBlock.dropStack(world, blockPos, drop);
    }
}
