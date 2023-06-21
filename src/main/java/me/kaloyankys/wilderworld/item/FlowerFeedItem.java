package me.kaloyankys.wilderworld.item;

import me.kaloyankys.wilderworld.util.classes.TagUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;

import java.util.ArrayList;

public class FlowerFeedItem extends Item {


    public FlowerFeedItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext itemUsageContext) {
        World world = itemUsageContext.getWorld();
        BlockPos pos = itemUsageContext.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Block flower = state.getBlock();
        ArrayList<Block> flowers = TagUtil.blockTagToList(BlockTags.FLOWERS);
        ItemStack stack = itemUsageContext.getStack();
        //Block randomFlower = flowers.get(new Random().nextInt(flowers.size()));

        if (Blocks.OAK_SAPLING.canPlaceAt(Blocks.OAK_SAPLING.getDefaultState(), world, pos) && flowers.contains(flower)) {
            if (!itemUsageContext.getPlayer().isCreative()) {
                stack.decrement(1);
            }
            world.spawnEntity(new ItemEntity(world, pos.up().getX(), pos.up().getY(), pos.up().getZ(), flower.asItem().getDefaultStack()));
            ParticleUtil.spawnParticle(world, pos.up(), ParticleTypes.HAPPY_VILLAGER, UniformIntProvider.create(1, 3));
            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(itemUsageContext);
    }
}
