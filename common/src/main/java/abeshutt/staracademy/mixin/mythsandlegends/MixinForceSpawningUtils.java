package abeshutt.staracademy.mixin.mythsandlegends;

import abeshutt.staracademy.init.ModConfigs;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.github.d0ctorleon.mythsandlegends.MythsAndLegends;
import com.github.d0ctorleon.mythsandlegends.utils.ForceSpawningUtils;
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

@Mixin(ForceSpawningUtils.class)
public class MixinForceSpawningUtils {

    @Inject(method = "forceSpawnv1", at = @At("HEAD"), cancellable = true)
    private static void forceSpawnv1(World world, PlayerEntity player, Hand hand, String keyItem, CallbackInfoReturnable<TypedActionResult<ItemStack>> ci) {
        ItemStack stack = player.getStackInHand(hand);

        ModConfigs.FORCE_SPAWN_ITEM.getPokemon(stack).ifPresent(entry -> {
            BlockPos pos = player.getBlockPos();
            RegistryEntry<Biome> biome = world.getBiome(pos);

            if(entry.getBiome().test(biome)) {
                PokemonEntity entity = entry.getPokemon().createEntity(world);
                world.spawnEntity(entity);
                entity.setPosition(player.getX(), player.getY(), player.getZ());
                stack.decrement(1);
            } else {
                player.getItemCooldownManager().set(stack.getItem(), MythsAndLegends.getConfigManager().getConfig().force_spawn_item_cooldown);
            }
        });

        ci.setReturnValue(TypedActionResult.success(stack, world.isClient()));
    }

}
