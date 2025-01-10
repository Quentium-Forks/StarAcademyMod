package abeshutt.staracademy.config;

import abeshutt.staracademy.data.biome.BiomePredicate;
import abeshutt.staracademy.data.item.ItemPredicate;
import abeshutt.staracademy.data.item.PartialItem;
import abeshutt.staracademy.data.item.PartialStack;
import abeshutt.staracademy.data.nbt.PartialCompoundNbt;
import com.google.gson.annotations.Expose;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Dynamic;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BiomeGroupsConfig extends FileConfig {

    @Expose private Map<Identifier, Set<BiomePredicate>> groups;

    @Override
    public String getPath() {
        return "biome_groups";
    }

    public boolean hasGroup(Identifier groupId) {
        return this.groups.containsKey(groupId);
    }

    public Set<BiomePredicate> getGroup(Identifier groupId) {
        return this.groups.getOrDefault(groupId, new HashSet<>());
    }

    public boolean isInGroup(Identifier groupId, RegistryEntry<Biome> biome) {
        for(BiomePredicate predicate : this.groups.getOrDefault(groupId, new HashSet<>())) {
            if(predicate.test(biome)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void reset() {
        this.groups = new LinkedHashMap<>();
    }

}
