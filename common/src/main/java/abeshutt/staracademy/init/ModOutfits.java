package abeshutt.staracademy.init;

import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.models.Classy1Outfit;

import java.util.HashMap;
import java.util.Map;

public class ModOutfits {

    public static Map<String, OutfitPiece> REGISTRY = new HashMap<>();

    public static Classy1Outfit.Hat CLASSY1_HAT;
    public static Classy1Outfit.Shirt CLASSY1_SHIRT;
    public static Classy1Outfit.Pants CLASSY1_PANTS;
    public static Classy1Outfit.Shoes CLASSY1_SHOES;

    public static void register() {
        CLASSY1_HAT = register(new Classy1Outfit.Hat("classy1_hat"));
        CLASSY1_SHIRT = register(new Classy1Outfit.Shirt("classy1_shirt"));
        CLASSY1_PANTS = register(new Classy1Outfit.Pants("classy1_pants"));
        CLASSY1_SHOES = register(new Classy1Outfit.Shoes("classy1_shoes"));
    }

    public static <T extends OutfitPiece> T register(T piece) {
        REGISTRY.put(piece.getId(), piece);
        return piece;
    }

}
