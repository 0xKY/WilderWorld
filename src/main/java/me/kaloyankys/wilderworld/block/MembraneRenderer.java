package me.kaloyankys.wilderworld.block;

import me.kaloyankys.wilderworld.client.WilderworldClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class MembraneRenderer implements BlockEntityRenderer<SingleItemHolderBlockEntity> {

    private static ItemStack stack = new ItemStack(Items.JUKEBOX, 1);

    public MembraneRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(SingleItemHolderBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Random r = new Random();
        vertexConsumers.getBuffer(RenderLayer.getEntitySolid(WilderworldClient.BUTTERFLY_RENDER_LAYER.getId())).texture(r.nextFloat(), r.nextFloat());
        matrices.push();
    }
}