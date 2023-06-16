package me.kaloyankys.wilderworld.mixin;


import com.mojang.datafixers.util.Pair;
import me.kaloyankys.wilderworld.init.WWEntities;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(BoatEntityRenderer.class)
abstract class BoatEntityRendererMixin extends EntityRenderer<BoatEntity> {
    @Shadow @Final private Map<BoatEntity.Type, Pair<Identifier, CompositeEntityModel<BoatEntity>>> texturesAndModels;

    protected BoatEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/entity/vehicle/BoatEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    private void getTexture(BoatEntity boatEntity, CallbackInfoReturnable<Identifier> cir) {
        cir.cancel();
        if (boatEntity.getType() == WWEntities.ASPEN_BOAT) {
            cir.setReturnValue(new Identifier("textures/entity/boat/aspen.png"));
        } else if (boatEntity.getType() == WWEntities.ASPEN_CHEST_BOAT) {
            cir.setReturnValue(new Identifier("textures/entity/chest_boat/aspen.png"));
        } else if (boatEntity.getType() == WWEntities.WISTERIA_BOAT) {
            cir.setReturnValue(new Identifier("textures/entity/boat/wisteria.png"));
        } else  if (boatEntity.getType() == WWEntities.WISTERIA_CHEST_BOAT) {
            cir.setReturnValue(new Identifier("textures/entity/chest_boat/wisteria.png"));
        } else {
            cir.setReturnValue((Identifier)((Pair)this.texturesAndModels.get(boatEntity.getVariant())).getFirst());
        }
    }
}
