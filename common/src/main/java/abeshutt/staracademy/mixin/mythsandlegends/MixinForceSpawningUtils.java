package abeshutt.staracademy.mixin.mythsandlegends;

import abeshutt.staracademy.init.ModConfigs;
import abeshutt.staracademy.world.random.JavaRandom;
import abeshutt.staracademy.world.random.RandomSource;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.d0ctorleon.mythsandlegends.MythsAndLegends;
import com.github.d0ctorleon.mythsandlegends.utils.ForceSpawningUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(ForceSpawningUtils.class)
public class MixinForceSpawningUtils {

    @Inject(method = "forceSpawnv1", at = @At("HEAD"), cancellable = true)
    private static void forceSpawnv1(World world, PlayerEntity player, Hand hand, String keyItem, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        ItemStack stack = player.getStackInHand(hand);

        if(!(world instanceof ServerWorld) || hand != Hand.MAIN_HAND || player.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            ci.setReturnValue(TypedActionResult.success(stack, world.isClient()));
        }

        RegistryEntry<Biome> biome = world.getBiome(player.getBlockPos());

        ModConfigs.FORCE_SPAWN_ITEM.getPokemon(stack, biome, JavaRandom.ofNanoTime()).ifPresentOrElse(entry -> {
            PokemonEntity entity = entry.getPokemon().createEntity(world);
            BlockPos pos = findSpawningSpace(world, player.getBlockPos(), entity).orElse(null);

            if(pos != null) {
                world.spawnEntity(entity);
                onSpawn(entity);
                stack.decrement(1);
                player.setStackInHand(hand, stack);
            } else {
                player.getItemCooldownManager().set(stack.getItem(), MythsAndLegends.getConfigManager().getConfig().force_spawn_item_cooldown);
            }
        }, () -> {
            player.getItemCooldownManager().set(stack.getItem(), MythsAndLegends.getConfigManager().getConfig().force_spawn_item_cooldown);
        });

        ci.setReturnValue(TypedActionResult.success(stack, world.isClient()));
    }

    private static void onSpawn(PokemonEntity entity) {
        MinecraftServer server = entity.getWorld().getServer();
        if(server == null) return;
        Pokemon pokemon = entity.getPokemon();

        List<String> prefixes = new ArrayList<>();
        if(pokemon.getShiny()) prefixes.add("Shiny");
        if(pokemon.isLegendary()) prefixes.add("Legendary");

        MutableText message = Text.empty()
                .append(Text.literal("A ").formatted(Formatting.BOLD))
                .append(Text.literal(String.join(" ", prefixes)).formatted(Formatting.BOLD))
                .append(prefixes.isEmpty() ? Text.empty() : Text.literal(" "))
                .append(entity.getDisplayName().copy().formatted(Formatting.BOLD))
                .append(Text.literal(" has been summoned!").formatted(Formatting.BOLD));

        for(ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            player.sendMessage(message);
        }
    }

    private static Optional<BlockPos> findSpawningSpace(World world, BlockPos center, Entity entity) {
        RandomSource random = JavaRandom.ofNanoTime();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        int w = ModConfigs.FORCE_SPAWN_ITEM.getHorizontalSpawnRadius();
        int h = ModConfigs.FORCE_SPAWN_ITEM.getVerticalSpawnRadius();

        for(int i = 0; i < 5000; i++) {
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
