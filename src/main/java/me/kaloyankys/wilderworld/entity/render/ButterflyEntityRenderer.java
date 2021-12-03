package me.kaloyankys.wilderworld.entity.render;

import me.kaloyankys.wilderworld.client.WilderworldClient;
import me.kaloyankys.wilderworld.entity.ButterflyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class ButterflyEntityRenderer extends MobEntityRenderer<ButterflyEntity, ButterflyModel> {

    private static final Identifier[] TEXTURE = {
            new Identifier("wilderworld", "textures/entity/butterfly/flame.png"),
            new Identifier("wilderworld", "textures/entity/butterfly/honey.png"),
            new Identifier("wilderworld", "textures/entity/butterfly/aqua.png"),
            new Identifier("wilderworld", "textures/entity/butterfly/pixie.png")
    };

    private static final Identifier ROXANNE = new Identifier("wilderworld", "textures/entity/butterfly/roxanne.png");

    public ButterflyEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ButterflyModel(context.getPart(WilderworldClient.BUTTERFLY_RENDER_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(ButterflyEntity entity) {
        if (entity.getCustomName() != null && entity.getCustomName().asString().equals("Roxanne")) {
            return ROXANNE;
        } else {
            return TEXTURE[entity.getVariant() % TEXTURE.length];
        }
    }

}
