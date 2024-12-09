package abeshutt.staracademy.init;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.world.data.PlayerProfileData;
import abeshutt.staracademy.world.data.PokemonStarterData;
import abeshutt.staracademy.world.data.StarBadgeData;
import abeshutt.staracademy.world.data.WorldDataType;

public class ModWorldData extends ModRegistries {

    public static WorldDataType<PlayerProfileData> PLAYER_NAME;
    public static WorldDataType<StarBadgeData> STAR_BADGE;
    public static WorldDataType<PokemonStarterData> POKEMON_STARTER;

    public static void register() {
        PLAYER_NAME = new WorldDataType<>(StarAcademyMod.ID + ".player_profile", PlayerProfileData::new);
        STAR_BADGE = new WorldDataType<>(StarAcademyMod.ID + ".star_badge", StarBadgeData::new);
        POKEMON_STARTER = new WorldDataType<>(StarAcademyMod.ID + ".pokemon_starter", PokemonStarterData::new);

        PlayerProfileData.init();
        StarBadgeData.init();
        PokemonStarterData.init();
    }

}
