package abeshutt.staracademy.init;

import abeshutt.staracademy.StarAcademyMod;
import abeshutt.staracademy.net.*;
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
            ModNetwork.register(UpdatePlayerProfileS2CPacket.class, UpdatePlayerProfileS2CPacket::new, CLIENT_PLAY);
            ModNetwork.register(UpdateStarBadgeS2CPacket.class, UpdateStarBadgeS2CPacket::new, CLIENT_PLAY);
            ModNetwork.register(UpdateStarterRaffleS2CPacket.class, UpdateStarterRaffleS2CPacket::new, CLIENT_PLAY);
            ModNetwork.register(UpdateSafariS2CPacket.class, UpdateSafariS2CPacket::new, CLIENT_PLAY);
            ModNetwork.register(UpdateOutfitS2CPacket.class, UpdateOutfitS2CPacket::new, CLIENT_PLAY);
            ModNetwork.register(UpdateBetterStructureBlockC2SPacket.class, UpdateBetterStructureBlockC2SPacket::new, SERVER_PLAY);
        }
    }

    public static class Server {
        public static final Function<NetworkManager.PacketContext, ServerPlayNetworkHandler> SERVER_PLAY = context -> ((ServerPlayerEntity)context.getPlayer()).networkHandler;

        public static void register() {
            ModNetwork.register(UpdatePlayerProfileS2CPacket.class, UpdatePlayerProfileS2CPacket::new, null);
            ModNetwork.register(UpdateStarBadgeS2CPacket.class, UpdateStarBadgeS2CPacket::new, null);
            ModNetwork.register(UpdateStarterRaffleS2CPacket.class, UpdateStarterRaffleS2CPacket::new, null);
            ModNetwork.register(UpdateSafariS2CPacket.class, UpdateSafariS2CPacket::new, null);
            ModNetwork.register(UpdateOutfitS2CPacket.class, UpdateOutfitS2CPacket::new, null);
            ModNetwork.register(UpdateBetterStructureBlockC2SPacket.class, UpdateBetterStructureBlockC2SPacket::new, SERVER_PLAY);
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
