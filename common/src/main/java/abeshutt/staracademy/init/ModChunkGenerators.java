package abeshutt.staracademy.init;

import abeshutt.staracademy.world.OldSafariChunkGenerator;
import abeshutt.staracademy.world.SafariChunkGenerator;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ModChunkGenerators extends ModRegistries {

    public static RegistrySupplier<Codec<SafariChunkGenerator>> SAFARI;

    public static void register() {
        SAFARI = register("safari", SafariChunkGenerator.CODEC);
    }

    public static <C extends ChunkGenerator> RegistrySupplier<Codec<C>> register(Identifier id, Codec<C> generator) {
        return register(CHUNK_GENERATORS, id, () -> generator);
    }

    public static <C extends ChunkGenerator> RegistrySupplier<Codec<C>> register(String name, Codec<C> generator) {
        return register(CHUNK_GENERATORS, name, () -> generator);
    }

}
