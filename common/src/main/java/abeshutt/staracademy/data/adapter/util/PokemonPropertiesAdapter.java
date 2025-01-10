package abeshutt.staracademy.data.adapter.util;

import abeshutt.staracademy.data.adapter.ISimpleAdapter;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.nbt.NbtElement;

import java.util.Optional;

public class PokemonPropertiesAdapter implements ISimpleAdapter<PokemonProperties, NbtElement, JsonElement> {

    @Override
    public Optional<JsonElement> writeJson(PokemonProperties value) {
        return Optional.of(new JsonPrimitive(value.getOriginalString()));
    }

    @Override
    public Optional<PokemonProperties> readJson(JsonElement json) {
        if(json instanceof JsonPrimitive primitive && primitive.isString()) {
            return Optional.of(PokemonProperties.Companion.parse(primitive.getAsString()));
        }

        return Optional.empty();
    }

}
