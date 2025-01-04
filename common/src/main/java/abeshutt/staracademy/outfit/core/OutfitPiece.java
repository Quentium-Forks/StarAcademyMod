package abeshutt.staracademy.outfit.core;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;

public abstract class OutfitPiece {

    private static final String[] BASE_PARTS = { "head",  "hat",  "body",  "right_arm",  "right_leg", "right_sleeve",
            "right_pants", "left_arm", "left_leg", "left_sleeve", "left_pants", "ear", "cloak", "jacket" };

    private final String id;
    private final OutfitTexture texture;

    @Environment(EnvType.CLIENT)
    private OutfitModel model;

    public OutfitPiece(String id) {
        this.id = id;
        this.texture = this.buildTexture();

        if(Platform.getEnvironment() == Env.CLIENT) {
            this.model = new OutfitModel(this.createMesh().createModel());
        }
    }

    public String getId() {
        return this.id;
    }

    public OutfitTexture getTexture() {
        return this.texture;
    }

    public OutfitModel getModel() {
        return this.model;
    }

    protected abstract OutfitTexture buildTexture();

    @Environment(EnvType.CLIENT)
    protected TexturedModelData createMesh() {
        ModelData mesh = new ModelData();
        ModelPartData root = mesh.getRoot();

        for(String name : BASE_PARTS) {
            root.addChild(name, ModelPartBuilder.create(),
                    ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        }

        this.buildMesh(root);
        return TexturedModelData.of(mesh, this.texture.getWidth(), this.texture.getHeight());
    }

    @Environment(EnvType.CLIENT)
    protected abstract void buildMesh(ModelPartData root);

}