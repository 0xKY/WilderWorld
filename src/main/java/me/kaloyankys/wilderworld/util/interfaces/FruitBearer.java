package me.kaloyankys.wilderworld.util.interfaces;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FruitBearer {
    Item getFruit();

    int getMaxFruits();
    int getStages();

    ActionResult collect(World world, BlockPos pos, BlockState state, ItemStack stack);

    static IntProperty createProperty(int stages) {
        return IntProperty.of("stage", 1, stages);
    }

    ParticleEffect getParticles();

    ParticleEffect getAmbient();
}

