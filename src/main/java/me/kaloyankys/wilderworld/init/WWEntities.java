package me.kaloyankys.wilderworld.init;

import me.kaloyankys.wilderworld.block.MembraneBlockEntity;
import me.kaloyankys.wilderworld.entity.ButterflyEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WWEntities {

    public static final EntityType<ButterflyEntity> BUTTERFLY = register("butterfly", FabricEntityTypeBuilder.<ButterflyEntity>create(
            SpawnGroup.AMBIENT, (type, world) ->
                    new ButterflyEntity(world)).dimensions(EntityDimensions.fixed(0.6F, 0.6F)).trackRangeBlocks(12).build());

    private static <BT extends BlockEntity> BlockEntityType<BT> register(String id, BlockEntityType<BT> type) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("wilderworld", id), type);
    }

    public static BlockEntityType<MembraneBlockEntity> MEMBRANE = register("oven_block_entity",
            FabricBlockEntityTypeBuilder.create(MembraneBlockEntity::new, WWBlocks.BUTTERFLY_WING_MEMBRANE_BLOCK).build(null));


    public WWEntities() {
        FabricDefaultAttributeRegistry.register(BUTTERFLY, ButterflyEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 2.0D));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier("wilderworld", id), type);
    }


}
