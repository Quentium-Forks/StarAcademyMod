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

    @Expose private int horizontalSpawnRadius;
    @Expose private int verticalSpawnRadius;
    @Expose private Map<ItemPredicate, PokemonSpawnEntry> entries;

    @Override
    public String getPath() {
        return "force_spawn_item";
    }

    public int getHorizontalSpawnRadius() {
        return this.horizontalSpawnRadius;
    }

    public int getVerticalSpawnRadius() {
        return this.verticalSpawnRadius;
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
        this.horizontalSpawnRadius = 15;
        this.verticalSpawnRadius = 10;

        this.entries = new LinkedHashMap<>();
        this.entries.put(ItemPredicate.of("myths_and_legends:adamant_orb", true).orElseThrow(), new PokemonSpawnEntry(
                "pikachu",
                BiomePredicate.of("minecraft:plains", true).orElseThrow()
        ));
    }

}
