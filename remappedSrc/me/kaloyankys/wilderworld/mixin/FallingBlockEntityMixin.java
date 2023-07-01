package me.kaloyankys.wilderworld.mixin;

import me.kaloyankys.wilderworld.init.WWBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin extends Entity {
    @Shadow public abstract BlockState getBlockState();

    public FallingBlockEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void onPlayerCollision(PlayerEntity playerEntity) {
        super.onPlayerCollision(playerEntity);

        if (this.getBlockState().isOf(WWBlocks.COCONUT)) {
            this.kill();
            ItemScatterer.spawn(method_48926(), this.getX(), this.getY(), this.getZ(), new ItemStack(WWBlocks.COCONUT.asItem()));
        }
    }
}
