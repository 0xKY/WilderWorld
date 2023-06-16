package me.kaloyankys.wilderworld.client;

import me.kaloyankys.wilderworld.init.WWNetwork;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class BoatSpawnNetworkHandler {
    static {
        ClientPlayNetworking.registerGlobalReceiver(WWNetwork.SPAWN_BOAT_PACKET_ID, BoatSpawnNetworkHandler::accept);
    }

    public static void accept(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        int id = buf.readVarInt();
        UUID uuid = buf.readUuid();
        EntityType<?> type = Registries.ENTITY_TYPE.get(buf.readVarInt());
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        byte pitch = buf.readByte();
        byte yaw = buf.readByte();

        if (client.isOnThread()) {
            spawn(client, id, uuid, type, x, y, z, pitch, yaw);
        } else {
            client.execute(() -> spawn(client, id, uuid, type, x, y, z, pitch, yaw));
        }
    }

    private static void spawn(MinecraftClient client, int id, UUID uuid, EntityType<?> type, double x, double y, double z, byte pitch, byte yaw) {
        ClientWorld world = client.world;
        if (world != null) {
            Entity entity = type.create(world);
            if (entity != null) {

                entity.setId(id);
                entity.setUuid(uuid);
                entity.updatePosition(x, y, z);
                entity.updateTrackedPosition(x, y, z);
                entity.setPitch(pitch * 360 / 256F);
                entity.setYaw(yaw * 360 / 256F);

                world.addEntity(entity.getId(), entity);
            }
        }
    }
}
