package abeshutt.staracademy.init;

import abeshutt.staracademy.outfit.core.OutfitPiece;
import abeshutt.staracademy.outfit.models.*;

import java.util.HashMap;
import java.util.Map;

public class ModOutfits {

    public static Map<String, OutfitPiece> REGISTRY = new HashMap<>();

    public static Classy1Outfit.Hat CLASSY1_HAT;
    public static Classy1Outfit.Shirt CLASSY1_SHIRT;
    public static Classy1Outfit.Pants CLASSY1_PANTS;
    public static Classy1Outfit.Shoes CLASSY1_SHOES;

    public static Classy2Outfit.Hat CLASSY2_HAT;
    public static Classy2Outfit.Shirt CLASSY2_SHIRT;
    public static Classy2Outfit.Skirt CLASSY2_SKIRT;
    public static Classy2Outfit.Shoes CLASSY2_SHOES;

    public static Comfy1Outfit.Hat COMFY1_HAT;
    public static Comfy1Outfit.Shirt COMFY1_SHIRT;
    public static Comfy1Outfit.Pants COMFY1_PANTS;
    public static Comfy1Outfit.Shoes COMFY1_SHOES;

    public static Comfy2Outfit.Hat COMFY2_HAT;
    public static Comfy2Outfit.Shirt COMFY2_SHIRT;
    public static Comfy2Outfit.Skirt COMFY2_SKIRT;
    public static Comfy2Outfit.Shoes COMFY2_SHOES;

    public static Formal1Outfit.Jacket FORMAL1_JACKET;
    public static Formal1Outfit.Pants FORMAL1_PANTS;
    public static Formal1Outfit.Boots FORMAL1_BOOTS;

    public static Formal2Outfit.Jacket FORMAL2_JACKET;
    public static Formal2Outfit.Skirt FORMAL2_SKIRT;
    public static Formal2Outfit.Boots FORMAL2_BOOTS;

    public static GengarHatOutfit GENGAR_HAT;
    public static GoomyHatOutfit GOOMY_HAT;

    public static void register() {
        CLASSY1_HAT = register(new Classy1Outfit.Hat("classy1_hat"));
        CLASSY1_SHIRT = register(new Classy1Outfit.Shirt("classy1_shirt"));
        CLASSY1_PANTS = register(new Classy1Outfit.Pants("classy1_pants"));
        CLASSY1_SHOES = register(new Classy1Outfit.Shoes("classy1_shoes"));

        CLASSY2_HAT = register(new Classy2Outfit.Hat("classy2_hat"));
        CLASSY2_SHIRT = register(new Classy2Outfit.Shirt("classy2_shirt"));
        CLASSY2_SKIRT = register(new Classy2Outfit.Skirt("classy2_skirt"));
        CLASSY2_SHOES = register(new Classy2Outfit.Shoes("classy2_shoes"));

        COMFY1_HAT = register(new Comfy1Outfit.Hat("classy1_hat"));
        COMFY1_SHIRT = register(new Comfy1Outfit.Shirt("classy1_shirt"));
        COMFY1_PANTS = register(new Comfy1Outfit.Pants("classy1_pants"));
        COMFY1_SHOES = register(new Comfy1Outfit.Shoes("classy1_shoes"));

        COMFY2_HAT = register(new Comfy2Outfit.Hat("comfy2_hat"));
        COMFY2_SHIRT = register(new Comfy2Outfit.Shirt("comfy2_shirt"));
        COMFY2_SKIRT = register(new Comfy2Outfit.Skirt("comfy2_skirt"));
        COMFY2_SHOES = register(new Comfy2Outfit.Shoes("comfy2_shoes"));

        FORMAL1_JACKET = register(new Formal1Outfit.Jacket("formal1_jacket"));
        FORMAL1_PANTS = register(new Formal1Outfit.Pants("formal1_pants"));
        FORMAL1_BOOTS = register(new Formal1Outfit.Boots("formal1_boots"));

        FORMAL2_JACKET = register(new Formal2Outfit.Jacket("formal2_jacket"));
        FORMAL2_SKIRT = register(new Formal2Outfit.Skirt("formal2_jacket"));
        FORMAL2_BOOTS = register(new Formal2Outfit.Boots("formal2_jacket"));

        GENGAR_HAT = register(new GengarHatOutfit("gengar_hat"));
        GOOMY_HAT = register(new GoomyHatOutfit("goomy_hat"));
    }

    public static <T extends OutfitPiece> T register(T piece) {
        REGISTRY.put(piece.getId(), piece);
        return piece;
    }

}
