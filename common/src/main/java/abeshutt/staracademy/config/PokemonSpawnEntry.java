package abeshutt.staracademy.config;

import abeshutt.staracademy.data.biome.BiomePredicate;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.google.gson.annotations.Expose;

public class PokemonSpawnEntry {

    @Expose private String pokemon;
    @Expose private BiomePredicate biome;

    public PokemonSpawnEntry(String pokemon, BiomePredicate biome) {
        this.pokemon = pokemon;
        this.biome = biome;
    }

    public String getRawPokemon() {
        return this.pokemon;
    }

    public PokemonProperties getPokemon() {
        return PokemonProperties.Companion.parse(this.pokemon);
    }

    public BiomePredicate getBiome() {
        return this.biome;
    }

}
