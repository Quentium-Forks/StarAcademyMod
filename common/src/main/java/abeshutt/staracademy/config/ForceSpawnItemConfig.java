package abeshutt.staracademy.config;

import abeshutt.staracademy.data.biome.BiomePredicate;
import abeshutt.staracademy.data.item.ItemPredicate;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.google.gson.annotations.Expose;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class ForceSpawnItemConfig extends FileConfig {

    @Expose private Map<ItemPredicate, PokemonSpawnEntry> entries;

    @Override
    public String getPath() {
        return "force_spawn_item";
    }

    public Optional<PokemonSpawnEntry> getPokemon(ItemStack stack) {
        for(Map.Entry<ItemPredicate, PokemonSpawnEntry> entry : this.entries.entrySet()) {
           if(entry.getKey().test(stack)) {
               return Optional.of(entry.getValue());
           }
        }

        return Optional.empty();
    }

    @Override
    protected void reset() {
        this.entries = new LinkedHashMap<>();
        this.entries.put(ItemPredicate.of("myths_and_legends:adamant_orb", true).orElseThrow(), new PokemonSpawnEntry(
                PokemonProperties.Companion.parse("pikachu"),
                BiomePredicate.of("minecraft:plains", true).orElseThrow()
        ));
    }

}
