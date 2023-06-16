package me.kaloyankys.wilderworld.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ScentedCandleBlock extends BlockWithEntity {
    public static final BooleanProperty LIT = BooleanProperty.of("lit");

    public ScentedCandleBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);

        builder.add(LIT);
    }

    @Override
    public void randomDisplayTick(BlockState blockState, World world, BlockPos blockPos, Random random) {
        super.randomDisplayTick(blockState, world, blockPos, random);

        if (blockState.get(LIT)) {
            this.spawnCandleParticles(world, new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()), random);
        }
    }

    @Override
    public void randomTick(BlockState blockState, ServerWorld serverWorld, BlockPos blockPos, net.minecraft.util.math.random.Random random) {
        super.randomTick(blockState, serverWorld, blockPos, random);

        if (blockState.get(LIT)) {
            SingleItemHolderBlockEntity be = (SingleItemHolderBlockEntity) serverWorld.getBlockEntity(blockPos);
            if (be != null) {
                this.createIncense(be, serverWorld, blockPos);
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState blockState, World world, BlockPos blockPos, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult) {
       ItemStack left = playerEntity.getMainHandStack();
       ItemStack right = playerEntity.getOffHandStack();
        SingleItemHolderBlockEntity be = (SingleItemHolderBlockEntity) world.getBlockEntity(blockPos);

        if (be != null) {
            if (!blockState.get(LIT)) {
                if (left.isOf(Items.POTION) && right.isOf(Items.FLINT_AND_STEEL)) {
                    this.light(world, blockState, blockPos, playerEntity, be, left);
                    this.createIncense(be, world, blockPos);
                } else if (right.isOf(Items.POTION) && left.isOf(Items.FLINT_AND_STEEL)) {
                    this.light(world, blockState, blockPos, playerEntity, be, right);
                    this.createIncense(be, world, blockPos);
                }
            } else if (left.isEmpty() || right.isEmpty()) {
                this.extinguish(playerEntity, blockState, world, blockPos);
            }
        }


        return super.onUse(blockState, world, blockPos, playerEntity, hand, blockHitResult);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new SingleItemHolderBlockEntity(blockPos, blockState);
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.cuboid(0.41, 0.0, 0.41, 0.61, 0.5, 0.61);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState blockState, BlockView blockView, BlockPos blockPos, ShapeContext shapeContext) {
        return VoxelShapes.cuboid(0.41, 0.0, 0.41, 0.61, 0.5, 0.61);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext itemPlacementContext) {
        return super.getPlacementState(itemPlacementContext).with(LIT, false);
    }

    private void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
        float f = random.nextFloat();
        if (f < 0.3f) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x + 0.48, vec3d.y + 0.5, vec3d.z + 0.48, 0.0, 0.0, 0.0);
            if (f < 0.17f) {
                world.playSound(vec3d.x + 0.48, vec3d.y + 0.5, vec3d.z + 0.48, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7f + 0.3f, false);
            }
        }
        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x + 0.48, vec3d.y + 0.5, vec3d.z + 0.48, 0.0, 0.0, 0.0);
    }
    
    private void light(WorldAccess worldAccess, BlockState blockState, BlockPos blockPos, PlayerEntity playerEntity, SingleItemHolderBlockEntity potion, ItemStack stack) {
        this.setLit(worldAccess, blockState, blockPos, true);
        worldAccess.playSound(null, blockPos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
        worldAccess.emitGameEvent(playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
        potion.set(stack);
    }

    private void extinguish(@Nullable PlayerEntity playerEntity, BlockState blockState, WorldAccess worldAccess, BlockPos blockPos) {
        this.setLit(worldAccess, blockState, blockPos, false);
        if (blockState.getBlock() instanceof AbstractCandleBlock) {
            worldAccess.addParticle(ParticleTypes.SMALL_FLAME, blockPos.getX() + 0.48, blockPos.getY() + 0.5, blockPos.getZ() + 0.48, 0.0, 0.0, 0.0);
        }
        worldAccess.playSound(null, blockPos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0f, 1.0f);
        worldAccess.emitGameEvent(playerEntity, GameEvent.BLOCK_CHANGE, blockPos);
    }

    private void setLit(WorldAccess worldAccess, BlockState blockState, BlockPos blockPos, boolean bl) {
        worldAccess.setBlockState(blockPos, blockState.with(LIT, bl), 11);
    }

    private void createIncense(SingleItemHolderBlockEntity be, World serverWorld, BlockPos blockPos) {
        Potion potion = PotionUtil.getPotion(be.getStack(0).getNbt());
        AreaEffectCloudEntity scent = new AreaEffectCloudEntity(serverWorld, blockPos.getX() + 0.48, blockPos.getY() + 0.5, blockPos.getZ() + 0.48);
        scent.setRadius(3.0f);
        scent.setRadiusOnUse(-0.5f);
        scent.setDuration(2400);
        scent.setRadiusGrowth(0.2f * (-scent.getRadius() / (float) scent.getDuration()));
        scent.setPotion(potion);
        for (StatusEffectInstance statusEffectInstance : potion.getEffects()) {
            scent.addEffect(new StatusEffectInstance(statusEffectInstance));
        }
        scent.setColor(PotionUtil.getColor(potion));
        serverWorld.spawnEntity(scent);
    }
}
