package abeshutt.staracademy.outfit.core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.util.Identifier;

public abstract class OutfitPiece {

    protected Identifier id;

    public OutfitPiece(Identifier id) {
        this.id = id;
    }

    public Identifier getId() {
        return this.id;
    }

    @Environment(EnvType.CLIENT)
    protected abstract void buildMesh(ModelPartData root);

    protected abstract OutfitTexture buildTexture();

    @Environment(EnvType.CLIENT)
    protected TexturedModelData createMesh() {
        ModelData mesh = new ModelData();
        ModelPartData root = mesh.getRoot();

        String[] partNames = {
                "head",
                "hat",
                "body",
                "right_arm",
                "right_leg",
                "right_sleeve",
                "right_pants",
                "left_arm",
                "left_leg",
                "left_sleeve",
                "left_pants",
                "ear",
                "cloak",
                "jacket",
        };

        for (String partName : partNames) {
            root.addChild(partName,
                    ModelPartBuilder.create(),
                    ModelTransform.pivot(0.0F, 0.0F, 0.0F)
            );
        }

        this.buildMesh(root);
        return TexturedModelData.of(mesh, 128, 128);
    }

    public OutfitPieceModel getModel() {
        return new OutfitPieceModel(this.createMesh().createModel());
    }

    public OutfitTexture getTexture() {
        return this.buildTexture();
    }

}