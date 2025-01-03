package abeshutt.staracademy.event;

import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.entity.SpawnEvent;
import com.cobblemon.mod.common.api.events.storage.ReleasePokemonEvent;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;

public class CommonEvents {

    public static final CobblemonEvent<SpawnEvent<PokemonEntity>> POKEMON_ENTITY_SPAWN = new CobblemonEvent<>(CobblemonEvents.POKEMON_ENTITY_SPAWN);
    public static final CobblemonEvent<ReleasePokemonEvent.Post> POKEMON_RELEASED = new CobblemonEvent<>(CobblemonEvents.POKEMON_RELEASED_EVENT_POST);

}
