package me.kaloyankys.wilderworld.init;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WWNetwork {
    public static final Identifier SPAWN_BOAT_PACKET_ID = new Identifier("wilderworld", "spawn_boat");
    public static final Identifier GLOWBRUSH_SEED_PLANT_C2S = c2s("glowbrush_seed_plant", GlowBrushSeedPlantC2S::receive);

    private static Identifier c2s(String name, ServerPlayNetworking.PlayChannelHandler channelHandler) {
        Identifier id = new Identifier("wilderworld", name);
        ServerPlayNetworking.registerGlobalReceiver(id, channelHandler);
        return id;
    }

    public static class GlowBrushSeedPlantC2S {
        public static void receive(MinecraftServer server, PlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
            World world = server.getWorld(World.OVERWORLD);
            BlockPos pos = buf.readBlockPos();

            if (world.getBlockState(pos).isAir() && world.getBlockState(pos.down()).isIn(BlockTags.BAMBOO_PLANTABLE_ON)) {
                world.setBlockState(pos, WWBlocks.GLOWBRUSH.getDefaultState());
            }
        }
    }
}
