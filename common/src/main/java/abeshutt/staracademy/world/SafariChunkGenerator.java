package abeshutt.staracademy.world;

import abeshutt.staracademy.init.ModConfigs;
import abeshutt.staracademy.util.ProxyStructureTemplate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureSet;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Clearable;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

public class SafariChunkGenerator extends ChunkGenerator {
    public static final Codec<SafariChunkGenerator> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(FlatChunkGeneratorConfig.CODEC.fieldOf("settings").forGetter(SafariChunkGenerator::getConfig))
                    .apply(instance, instance.stable(SafariChunkGenerator::new))
    );
    private final FlatChunkGeneratorConfig config;

    public SafariChunkGenerator(FlatChunkGeneratorConfig config) {
        super(new FixedBiomeSource(config.getBiome()), Util.memoize(config::createGenerationSettings));
        this.config = config;
    }

    @Override
    public StructurePlacementCalculator createStructurePlacementCalculator(RegistryWrapper<StructureSet> structureSetRegistry, NoiseConfig noiseConfig, long seed) {
        Stream<RegistryEntry<StructureSet>> stream = this.config
                .getStructureOverrides()
                .map(RegistryEntryList::stream)
                .orElseGet(() -> structureSetRegistry.streamEntries().map(reference -> reference));
        return StructurePlacementCalculator.create(noiseConfig, seed, this.biomeSource, stream);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    public FlatChunkGeneratorConfig getConfig() {
        return this.config;
    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
        ServerWorld world = region.toServerWorld();

        world.getStructureTemplateManager().getTemplate(ModConfigs.SAFARI.getStructure()).ifPresent(template -> {
            ProxyStructureTemplate.of(template).ifPresent(proxy -> {
                ChunkPos chunkPos = chunk.getPos();

                for(int ox = 0; ox < 16; ox++) {
                    for(int oz = 0; oz < 16; oz++) {
                        for(int oy = region.getBottomY(); oy <= region.getTopY(); oy++) {
                            BlockPos pos = new BlockPos((chunkPos.x << 4) + ox, oy, (chunkPos.z << 4) + oz);
                            BlockPos target = pos.subtract(ModConfigs.SAFARI.getPlacementOffset());

                            if(target.getX() < template.getSize().getX() && target.getZ() < template.getSize().getZ()
                                    && target.getY() < template.getSize().getY() && target.getX() >= 0
                                    && target.getZ() >= 0 && target.getY() >= 0) {
                                StructureTemplate.StructureBlockInfo entry = proxy.get(target);
                                if(entry == null) continue;

                                FluidState fluidState = region.getFluidState(pos);
                                BlockState state = entry.state();

                                if(entry.nbt() != null) {
                                    BlockEntity blockEntity = region.getBlockEntity(pos);
                                    Clearable.clear(blockEntity);
                                    region.setBlockState(pos, Blocks.BARRIER.getDefaultState(), Block.NO_REDRAW | Block.FORCE_STATE);
                                }

                                if(region.setBlockState(pos, state, Block.NOTIFY_ALL)) {
                                    if(fluidState != null && state.getBlock() instanceof FluidFillable fillable) {
                                        fillable.tryFillWithFluid(world, pos, state, fluidState);
                                    }
                                }
                            }
                        }
                    }
                }
            });
        });
    }

    @Override
    public int getSpawnHeight(HeightLimitView world) {
        return world.getBottomY() + Math.min(world.getHeight(), this.config.getLayerBlocks().size());
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        List<BlockState> list = this.config.getLayerBlocks();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Heightmap heightmap = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
        Heightmap heightmap2 = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);

        for (int i = 0; i < Math.min(chunk.getHeight(), list.size()); i++) {
            BlockState blockState = (BlockState)list.get(i);
            if (blockState != null) {
                int j = chunk.getBottomY() + i;

                for (int k = 0; k < 16; k++) {
                    for (int l = 0; l < 16; l++) {
                        chunk.setBlockState(mutable.set(k, j, l), blockState, false);
                        heightmap.trackUpdate(k, j, l, blockState);
                        heightmap2.trackUpdate(k, j, l, blockState);
                    }
                }
            }
        }

        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        List<BlockState> list = this.config.getLayerBlocks();

        for (int i = Math.min(list.size(), world.getTopY()) - 1; i >= 0; i--) {
            BlockState blockState = list.get(i);
            if (blockState != null && heightmap.getBlockPredicate().test(blockState)) {
                return world.getBottomY() + i + 1;
            }
        }

        return world.getBottomY();
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return new VerticalBlockSample(
                world.getBottomY(),
                this.config
                        .getLayerBlocks()
                        .stream()
                        .limit(world.getHeight())
                        .map(state -> state == null ? Blocks.AIR.getDefaultState() : state)
                        .toArray(BlockState[]::new)
        );
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {
    }

    @Override
    public void carve(
            ChunkRegion chunkRegion,
            long seed,
            NoiseConfig noiseConfig,
            BiomeAccess biomeAccess,
            StructureAccessor structureAccessor,
            Chunk chunk,
            GenerationStep.Carver carverStep
    ) {
    }

    @Override
    public void populateEntities(ChunkRegion region) {
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getWorldHeight() {
        return 384;
    }

    @Override
    public int getSeaLevel() {
        return -63;
    }
}

