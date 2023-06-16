package me.kaloyankys.wilderworld.init;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.constant.EmptyPart;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.serialization.Codec;
import me.kaloyankys.wilderworld.block.SingleItemHolderBlockEntity;
import me.kaloyankys.wilderworld.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.block.entity.SignBlockEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestBoatEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class WWEntities {

    public static final EntityType<ButterflyEntity> BUTTERFLY = register("butterfly", FabricEntityTypeBuilder.<ButterflyEntity>create(
            SpawnGroup.AMBIENT, (type, world) ->
                    new ButterflyEntity(world)).dimensions(EntityDimensions.fixed(0.6F, 0.6F)).trackRangeBlocks(12).build());

    public static final EntityType<DoodEntity> DOOD = register("dood", FabricEntityTypeBuilder.<DoodEntity>create(
            SpawnGroup.AMBIENT, (type, world) ->
                    new DoodEntity(world)).dimensions(EntityDimensions.fixed(1.0F, 1.0F)).trackRangeBlocks(24).build());

    public static final EntityType<GeyserStreamEntity> GEYSER_STREAM = register("geyser_stream", FabricEntityTypeBuilder.<GeyserStreamEntity>create(
            SpawnGroup.AMBIENT, (type, world) ->
                    new GeyserStreamEntity(world)).dimensions(EntityDimensions.changing(1.0F, 1.0F)).trackRangeBlocks(24).fireImmune().build());

    public static final EntityType<BoatEntity> ASPEN_BOAT = register("aspen_boat", FabricEntityTypeBuilder.<BoatEntity>create(
            SpawnGroup.AMBIENT, BoatEntity::new).dimensions(EntityDimensions.changing(1.375F, 0.5625F)).trackRangeBlocks(128).trackedUpdateRate(3).build());

    public static final EntityType<BoatEntity> WISTERIA_BOAT = register("wisteria_boat", FabricEntityTypeBuilder.<BoatEntity>create(
            SpawnGroup.AMBIENT, BoatEntity::new).dimensions(EntityDimensions.changing(1.375F, 0.5625F)).trackRangeBlocks(128).trackedUpdateRate(3).build());
    public static final EntityType<ChestBoatEntity> ASPEN_CHEST_BOAT = register("aspen_chest_boat", FabricEntityTypeBuilder.<ChestBoatEntity>create(
            SpawnGroup.AMBIENT, ChestBoatEntity::new).dimensions(EntityDimensions.changing(1.375F, 0.5625F)).trackRangeBlocks(128).trackedUpdateRate(3).build());

    public static final EntityType<ChestBoatEntity> WISTERIA_CHEST_BOAT = register("wisteria_chest_boat", FabricEntityTypeBuilder.<ChestBoatEntity>create(
            SpawnGroup.AMBIENT, ChestBoatEntity::new).dimensions(EntityDimensions.changing(1.375F, 0.5625F)).trackRangeBlocks(128).trackedUpdateRate(3).build());




    private static <BT extends BlockEntity> BlockEntityType<BT> register(String id, BlockEntityType<BT> type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier("wilderworld", id), type);
    }


    public static BlockEntityType<SingleItemHolderBlockEntity> SINGLE_ITEM_HOLDER = register("membrane_block",
            FabricBlockEntityTypeBuilder.create(SingleItemHolderBlockEntity::new, WWBlocks.BUTTERFLY_WING_MEMBRANE_BLOCK).build(null));


    public WWEntities() {
        FabricDefaultAttributeRegistry.register(BUTTERFLY, ButterflyEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 5.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 2.0D));
        FabricDefaultAttributeRegistry.register(DOOD, DoodEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0.0D));
        FabricDefaultAttributeRegistry.register(GEYSER_STREAM, GeyserStreamEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10.0D));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier("wilderworld", id), type);
    }
}
