package abeshutt.staracademy.config;

import abeshutt.staracademy.data.biome.BiomePredicate;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.google.gson.annotations.Expose;

public class PokemonSpawnEntry {

    @Expose private PokemonProperties pokemon;
    @Expose private BiomePredicate biome;

    public PokemonSpawnEntry(PokemonProperties pokemon, BiomePredicate biome) {
        this.pokemon = pokemon;
        this.biome = biome;
    }

    public PokemonProperties getPokemon() {
        return this.pokemon;
    }

    public BiomePredicate getBiome() {
        return this.biome;
    }

}
