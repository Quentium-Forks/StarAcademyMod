package abeshutt.staracademy.init;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.net.ModPacket;
import abeshutt.staracademy.net.PlayerProfileUpdateS2CPacket;
import abeshutt.staracademy.net.StarBadgeUpdateS2CPacket;
import abeshutt.staracademy.net.StarterRaffleUpdateS2CPacket;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModNetwork extends ModRegistries {

    public static final NetworkChannel CHANNEL = NetworkChannel.create(StarAcademyMod.id("network"));

    public static void register() {
        if(Platform.getEnvironment() == Env.CLIENT) {
            Client.register();
        } else {
            Server.register();
        }
    }

    public static class Client {
        public static final Function<NetworkManager.PacketContext, ClientPlayNetworkHandler> CLIENT_PLAY = context -> MinecraftClient.getInstance().getNetworkHandler();
        public static final Function<NetworkManager.PacketContext, ServerPlayNetworkHandler> SERVER_PLAY = context -> ((ServerPlayerEntity)context.getPlayer()).networkHandler;

        public static void register() {
            ModNetwork.register(PlayerProfileUpdateS2CPacket.class, PlayerProfileUpdateS2CPacket::new, CLIENT_PLAY);
            ModNetwork.register(StarBadgeUpdateS2CPacket.class, StarBadgeUpdateS2CPacket::new, CLIENT_PLAY);
            ModNetwork.register(StarterRaffleUpdateS2CPacket.class, StarterRaffleUpdateS2CPacket::new, CLIENT_PLAY);
        }
    }

    public static class Server {
        public static final Function<NetworkManager.PacketContext, ServerPlayNetworkHandler> SERVER_PLAY = context -> ((ServerPlayerEntity)context.getPlayer()).networkHandler;

        public static void register() {
            ModNetwork.register(PlayerProfileUpdateS2CPacket.class, PlayerProfileUpdateS2CPacket::new, null);
            ModNetwork.register(StarBadgeUpdateS2CPacket.class, StarBadgeUpdateS2CPacket::new, null);
            ModNetwork.register(StarterRaffleUpdateS2CPacket.class, StarterRaffleUpdateS2CPacket::new, null);
        }
    }

    public static <R extends PacketListener, T extends ModPacket<R>> void register(Class<T> type, Supplier<T> packetSupplier,
                                                                                   Function<NetworkManager.PacketContext, R> contextMapper) {
        CHANNEL.register(type, ModPacket::write, packetByteBuf -> {
            T packet = packetSupplier.get();
            packet.read(packetByteBuf);
            return packet;
        }, (packet, contextSupplier) -> {
            if(contextMapper != null) {
                packet.apply(contextMapper.apply(contextSupplier.get()));
            }
        });
    }

}
