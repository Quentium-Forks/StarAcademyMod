package abeshutt.staracademy.mixin.mythsandlegends;

import abeshutt.staracademy.data.biome.BiomePredicate;
import abeshutt.staracademy.init.ModConfigs;
import abeshutt.staracademy.world.random.JavaRandom;
import abeshutt.staracademy.world.random.RandomSource;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.d0ctorleon.mythsandlegends.MythsAndLegends;
import com.github.d0ctorleon.mythsandlegends.utils.ForceSpawningUtils;
import com.mojang.datafixers.optics.Optic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ForceSpawningUtils.class)
public class MixinForceSpawningUtils {

    @Inject(method = "forceSpawnv1", at = @At("HEAD"), cancellable = true)
    private static void forceSpawnv1(World world, PlayerEntity player, Hand hand, String keyItem, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        ItemStack stack = player.getStackInHand(hand);

        ModConfigs.FORCE_SPAWN_ITEM.getPokemon(stack, JavaRandom.ofNanoTime()).ifPresent(entry -> {
            RegistryEntry<Biome> biome = world.getBiome(player.getBlockPos());

            if(entry.getBiome().test(biome)) {
                PokemonEntity entity = entry.getPokemon().createEntity(world);
                BlockPos pos = findSpawningSpace(world, player.getBlockPos(), entity).orElse(null);

                if(pos != null) {
                    world.spawnEntity(entity);
                    stack.decrement(1);
                } else {
                    player.getItemCooldownManager().set(stack.getItem(), MythsAndLegends.getConfigManager().getConfig().force_spawn_item_cooldown);
                }
            } else {
                player.getItemCooldownManager().set(stack.getItem(), MythsAndLegends.getConfigManager().getConfig().force_spawn_item_cooldown);
            }
        });

        ci.setReturnValue(TypedActionResult.success(stack, world.isClient()));
    }

    private static Optional<BlockPos> findSpawningSpace(World world, BlockPos center, Entity entity) {
        RandomSource random = JavaRandom.ofNanoTime();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        int w = ModConfigs.FORCE_SPAWN_ITEM.getHorizontalSpawnRadius();
        int h = ModConfigs.FORCE_SPAWN_ITEM.getVerticalSpawnRadius();

        for(int i = 0; i < 1000; i++) {
            int x = center.getX() + random.nextInt(w * 2 + 1) - w;
            int y = center.getY() + random.nextInt(h * 2 + 1) - h;
            int z = center.getZ() + random.nextInt(w * 2 + 1) - w;
            pos.set(x, y, z);

            entity.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);

            if(world.isSpaceEmpty(entity) && world.getBlockState(pos.down()).isFullCube(world, pos.down())) {
                return Optional.of(pos);
            }
        }

        return Optional.empty();
    }

}
