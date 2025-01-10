package abeshutt.staracademy.config;

import abeshutt.staracademy.data.biome.BiomePredicate;
import abeshutt.staracademy.data.item.ItemPredicate;
import abeshutt.staracademy.world.random.RandomSource;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.google.gson.annotations.Expose;
import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.objects.Object2DoubleFunction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.WeightedList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ForceSpawnItemConfig extends FileConfig {

    @Expose private int horizontalSpawnRadius;
    @Expose private int verticalSpawnRadius;
    @Expose private Map<ItemPredicate, List<PokemonSpawnEntry>> entries;

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

    public Optional<PokemonSpawnEntry> getPokemon(ItemStack stack, RandomSource random) {
        for(Map.Entry<ItemPredicate, List<PokemonSpawnEntry>> entry : this.entries.entrySet()) {
           if(entry.getKey().test(stack)) {
               List<PokemonSpawnEntry> options = entry.getValue();
               return this.getWeighted(options, o -> ((PokemonSpawnEntry)o).getWeight(), random);
           }
        }

        return Optional.empty();
    }

    public <T> Optional<T> getWeighted(Iterable<T> values, Object2DoubleFunction<T> weighter, RandomSource random) {
        double sum = 0.0D;

        for(T value : values) {
           sum += weighter.applyAsDouble(value);
        }

        double pick = random.nextDouble() * sum;

        for(T value : values) {
            double weight = weighter.applyAsDouble(value);

            if(pick <= weight) {
                return Optional.of(value);
            }

            pick -= weight;
        }

        return Optional.empty();
    }

    @Override
    protected void reset() {
        this.horizontalSpawnRadius = 15;
        this.verticalSpawnRadius = 10;

        this.entries = new LinkedHashMap<>();
        this.entries.put(ItemPredicate.of("myths_and_legends:adamant_orb", true).orElseThrow(), List.of(
                new PokemonSpawnEntry("pikachu",
                        BiomePredicate.of("minecraft:plains", true).orElseThrow(),
                        1.0D),
                new PokemonSpawnEntry("charmander",
                        BiomePredicate.of("minecraft:desert", true).orElseThrow(),
                        1.0D)
        ));
    }

}
