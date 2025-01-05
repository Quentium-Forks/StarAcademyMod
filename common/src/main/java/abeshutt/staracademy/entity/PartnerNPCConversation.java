package abeshutt.staracademy.entity;

import abeshutt.staracademy.data.serializable.INbtSerializable;
import abeshutt.staracademy.event.CommonEvents;
import abeshutt.staracademy.world.random.JavaRandom;
import abeshutt.staracademy.world.random.RandomSource;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.pokemon.Species;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class PartnerNPCConversation implements INbtSerializable<NbtCompound> {

    public static final Function<RandomSource, String> INITIAL_GREETING = create(
        "Ah, a new trainer! Welcome to the academy, ${uuid}. I’m ${npc}. How wonderful to see a new fresh, eager face!"
    );

    public static final Function<RandomSource, String> SUBSEQUENT_GREETING = create(
        "Ah, a new trainer! Welcome to the academy, ${uuid}. I’m ${npc}. How wonderful to see a new fresh, eager face!"
    );

    public static final Function<RandomSource, String> PARTNER_SUMMON = create(
        "Please summon the Pokemon you wish to partner with by throwing it on the ground.",
        "To set your partner, simply call out your chosen Pokemon and place it here."
    );

    public static final Function<RandomSource, String> PARTNER_SUMMON_EXPLANATION = create(
        "Once summoned, I'll ensure it's exclusively yours for battles.",
        "Once summoned, it becomes your trusted ally, unavailable for others to use in battles."
    );

    public static final Function<RandomSource, String> PARTNER_SUMMON_CONFIRMATION = create(
        "Are you certain you want to select ${pokemon} as your partner?",
        "You've chosen ${pokemon}! Would you like to confirm it as your partner?",
        "Do you want to make ${pokemon} your official partner?",
        "You've selected ${pokemon} as your partner—do you want to proceed with this choice?"
    );

    public static final Function<RandomSource, String> PARTNER_SUMMON_SUCCESS = create(
        "Excellent choice! ${pokemon} will be your steadfast companion. Together, you'll make an unstoppable team!",
        "An intriguing selection. ${pokemon} possesses unique abilities that will aid your research.",
        "Excellent choice! ${pokemon} does align well with the Academy's standards.",
        "Your partnership with ${pokemon} is now official. May your bond lead you to remarkable discoveries and victories."
    );

    public static final Function<RandomSource, String> PARTNER_SUMMON_ALREADY_SET = create(
        "It looks like you've already chosen ${pokemon} as your partner."
    );

    private UUID uuid;
    private Phase phase;
    private boolean initial;
    private Species selectedSpecies;

    public PartnerNPCConversation(UUID uuid) {
        this.phase = Phase.NONE;
        this.initial = true;
    }

    public void attach() {
        CommonEvents.POKEMON_SENT_POST.subscribe(this, Priority.NORMAL, event -> {
            if(!this.uuid.equals(event.getPokemon().getOwnerUUID())) return;
            if(this.phase != Phase.AWAIT_PARTNER_SUMMON) return;
            this.selectedSpecies = event.getPokemon().getSpecies();
        });
    }

    public void tick(PartnerNPCEntity entity) {

    }

    public void onConfirmSpeciesSelection(MinecraftServer server) {
        if(this.selectedSpecies == null) return;
        ServerPlayerEntity player = server.getPlayerManager().getPlayer(this.uuid);


    }

    public void onInteract(PartnerNPCEntity entity) {
        RandomSource random = JavaRandom.ofNanoTime();

        if(this.phase == Phase.NONE) {
            this.getPlayer(entity.getWorld()).ifPresent(player -> {
                String greeting = (this.initial ? INITIAL_GREETING : SUBSEQUENT_GREETING).apply(random);
                this.phase = Phase.GREETING;
            });
        }
    }

    public void detach() {
        CommonEvents.POKEMON_SENT_POST.unsubscribe(this);
    }

    public Optional<ServerPlayerEntity> getPlayer(World world) {
        MinecraftServer server = world.getServer();
        if(server == null) return Optional.empty();
        ServerPlayerEntity player = server.getPlayerManager().getPlayer(this.uuid);
        return Optional.ofNullable(player);
    }

    public static Function<RandomSource, String> create(String... lines) {
        return random -> lines[random.nextInt(lines.length)];
    }

    @Override
    public Optional<NbtCompound> writeNbt() {
        return Optional.empty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {

    }

    private enum Phase {
        NONE, GREETING, AWAIT_PARTNER_SUMMON, DONE
    }

}
