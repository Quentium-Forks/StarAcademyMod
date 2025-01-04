package abeshutt.staracademy.init;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.models.ClassyHatOutfit;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModOutfits {

    public static Map<String, OutfitPiece> REGISTRY = new HashMap<>();

    public static ClassyHatOutfit CLASSY_HAT;

    public static void register() {
        CLASSY_HAT = register(new ClassyHatOutfit("classy_hat"));
    }

    public static <T extends OutfitPiece> T register(T piece) {
        REGISTRY.put(piece.getId(), piece);
        return piece;
    }

}
