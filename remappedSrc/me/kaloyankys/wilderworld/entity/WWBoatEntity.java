package me.kaloyankys.wilderworld.entity;

import io.netty.buffer.Unpooled;
import me.kaloyankys.wilderworld.init.WWNetwork;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class WWBoatEntity extends BoatEntity {
    private final WWBoatInfo info;

    public WWBoatEntity(EntityType<? extends WWBoatEntity> type, World world, WWBoatInfo info) {
        super(type, world);
        this.info = info;
    }

    @Override
    public Item asItem() {
        return this.info.asItem();
    }

    public Item asPlanks() {
        return this.info.asPlanks();
    }

    public Identifier getBoatTexture() {
        return this.info.getTexture();
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound tag) {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound tag) {}

    private boolean isOnLand() {
        return getPaddleSoundEvent() == SoundEvents.ENTITY_BOAT_PADDLE_LAND;
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
        float savedFallDistance = this.fallDistance;

        // Run other logic, including setting the private field fallVelocity
        super.fall(heightDifference, false, landedState, landedPosition);

        if (!this.hasVehicle() && onGround) {
            this.fallDistance = savedFallDistance;

            if (this.fallDistance > 3.0F) {
                if (!isOnLand()) {
                    this.fallDistance = 0.0F;
                    return;
                }

                this.handleFallDamage(this.fallDistance, 1.0F, DamageSource.FALL);
                if (!this.world.isClient && !this.isRemoved()) {
                    this.kill();
                    if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                        for (int i = 0; i < 3; i++) {
                            this.dropItem(this.asPlanks());
                        }

                        for (int i = 0; i < 2; i++) {
                            this.dropItem(Items.STICK);
                        }
                    }
                }
            }

            this.fallDistance = 0.0F;
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        final PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

        buf.writeVarInt(this.getId());
        buf.writeUuid(this.uuid);
        buf.writeVarInt(Registries.ENTITY_TYPE.getRawId(this.getType()));
        buf.writeDouble(this.getX());
        buf.writeDouble(this.getY());
        buf.writeDouble(this.getZ());
        buf.writeByte(MathHelper.floor(this.getPitch() * 256.0F / 360.0F));
        buf.writeByte(MathHelper.floor(this.getYaw() * 256.0F / 360.0F));

        return ServerPlayNetworking.createS2CPacket(WWNetwork.SPAWN_BOAT_PACKET_ID, buf);
    }

    @Override
    public void setVariant(Type type) {
        throw new UnsupportedOperationException("Tried to set the boat type of a wilderworld boat");
    }

    @Override
    public Type getVariant() {
        return this.info.getVanillaType();
    }
}
