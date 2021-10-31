package me.kaloyankys.wilderworld.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeDecoratorType.class)
public interface TreeDecoratorTypeInvoker {

    @Invoker("<init>")
    static <P extends TreeDecorator> TreeDecoratorType<P> createType(Codec<P> codec) {
        throw new IllegalStateException("This should not happen");
    }
}
