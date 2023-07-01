package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWBlocks;
import me.kaloyankys.wilderworld.world.ShelfshroomTreeDecorator;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TrunkVineTreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(TrunkVineTreeDecorator.class)
public abstract class LeavesVineTreeDecoratorMixin extends TreeDecorator {
    private final ShelfshroomTreeDecorator shelfshroomTreeDecorator = new ShelfshroomTreeDecorator();

    @Inject(at = @At("HEAD"), method = "generate", cancellable = true)
    public void generate(Generator generator, CallbackInfo ci) {
        ci.cancel();
        Random random = generator.getRandom();
        generator.getLogPositions().forEach((blockPos) -> {
            if (!generator.getWorld().testBlockState(blockPos, Predicate.isEqual(WWBlocks.ASPEN.log().getDefaultState()))) {
                BlockPos blockPos2;
                if (random.nextInt(3) > 0) {
                    blockPos2 = blockPos.west();
                    if (generator.isAir(blockPos2)) {
                        generator.replaceWithVine(blockPos2, VineBlock.EAST);
                    }
                }

                if (random.nextInt(3) > 0) {
                    blockPos2 = blockPos.east();
                    if (generator.isAir(blockPos2)) {
                        generator.replaceWithVine(blockPos2, VineBlock.WEST);
                    }
                }

                if (random.nextInt(3) > 0) {
                    blockPos2 = blockPos.north();
                    if (generator.isAir(blockPos2)) {
                        generator.replaceWithVine(blockPos2, VineBlock.SOUTH);
                    }
                }

                if (random.nextInt(3) > 0) {
                    blockPos2 = blockPos.south();
                    if (generator.isAir(blockPos2)) {
                        generator.replaceWithVine(blockPos2, VineBlock.NORTH);
                    }
                }
            } else if (random.nextInt(5) == 0) {
                shelfshroomTreeDecorator.generate(generator);
            }
        });
    }
}