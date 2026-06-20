// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet;

// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.ClientPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.common.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientAcceptCodeOfConductPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.common.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.configuration.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.login.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.*;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.status.ResponsePacket;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Déclaration de type (classe/interface/enum/record)
public interface PacketRegistry<T> {
    // Annotation pour l'élément suivant
    @UnknownNullability
    // Appelle une méthode
    T create(int packetId, NetworkBuffer reader);

    // Appelle une méthode
    PacketInfo<T> packetInfo(Class<?> packetClass);

    // Début d'une méthode/d'un bloc
    default PacketInfo<T> packetInfo(T packet) {
        // Renvoie une valeur à l'appelant
        return packetInfo(packet.getClass());
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    PacketInfo<T> packetInfo(int packetId);

    // Appelle une méthode
    ConnectionState state();

    // Appelle une méthode
    ConnectionSide side();

    // Déclaration de type (classe/interface/enum/record)
    record PacketInfo<T>(Class<T> packetClass, int id, NetworkBuffer.Type<T> serializer) {
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    abstract sealed class Client extends PacketRegistryTemplate<ClientPacket> {
        // Annotation pour l'élément suivant
        @SafeVarargs Client(Entry<? extends ClientPacket>... suppliers) {
            // Accès à l'objet courant/parent
            super(suppliers);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionSide side() {
            // Renvoie une valeur à l'appelant
            return ConnectionSide.CLIENT;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ClientHandshake extends Client {
        // Début d'une méthode/d'un bloc
        public ClientHandshake() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(ClientHandshakePacket.class, ClientHandshakePacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.HANDSHAKE;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ClientStatus extends Client {
        // Début d'une méthode/d'un bloc
        public ClientStatus() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(StatusRequestPacket.class, StatusRequestPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPingRequestPacket.class, ClientPingRequestPacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.STATUS;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ClientLogin extends Client {
        // Début d'une méthode/d'un bloc
        public ClientLogin() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(ClientLoginStartPacket.class, ClientLoginStartPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientEncryptionResponsePacket.class, ClientEncryptionResponsePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientLoginPluginResponsePacket.class, ClientLoginPluginResponsePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientLoginAcknowledgedPacket.class, ClientLoginAcknowledgedPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientCookieResponsePacket.class, ClientCookieResponsePacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.LOGIN;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ClientConfiguration extends Client {
        // Début d'une méthode/d'un bloc
        public ClientConfiguration() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(ClientSettingsPacket.class, ClientSettingsPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientCookieResponsePacket.class, ClientCookieResponsePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPluginMessagePacket.class, ClientPluginMessagePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientFinishConfigurationPacket.class, ClientFinishConfigurationPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientKeepAlivePacket.class, ClientKeepAlivePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPongPacket.class, ClientPongPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientResourcePackStatusPacket.class, ClientResourcePackStatusPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSelectKnownPacksPacket.class, ClientSelectKnownPacksPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientCustomClickActionPacket.class, ClientCustomClickActionPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientAcceptCodeOfConductPacket.class, ClientAcceptCodeOfConductPacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.CONFIGURATION;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ClientPlay extends Client {
        // Début d'une méthode/d'un bloc
        public ClientPlay() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(ClientTeleportConfirmPacket.class, ClientTeleportConfirmPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientQueryBlockNbtPacket.class, ClientQueryBlockNbtPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSelectBundleItemPacket.class, ClientSelectBundleItemPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientChangeDifficultyPacket.class, ClientChangeDifficultyPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientChangeGameModePacket.class, ClientChangeGameModePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientChatAckPacket.class, ClientChatAckPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientCommandChatPacket.class, ClientCommandChatPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSignedCommandChatPacket.class, ClientSignedCommandChatPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientChatMessagePacket.class, ClientChatMessagePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientChatSessionUpdatePacket.class, ClientChatSessionUpdatePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientChunkBatchReceivedPacket.class, ClientChunkBatchReceivedPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientStatusPacket.class, ClientStatusPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientTickEndPacket.class, ClientTickEndPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSettingsPacket.class, ClientSettingsPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientTabCompletePacket.class, ClientTabCompletePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientConfigurationAckPacket.class, ClientConfigurationAckPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientClickWindowButtonPacket.class, ClientClickWindowButtonPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientClickWindowPacket.class, ClientClickWindowPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientCloseWindowPacket.class, ClientCloseWindowPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientWindowSlotStatePacket.class, ClientWindowSlotStatePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientCookieResponsePacket.class, ClientCookieResponsePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPluginMessagePacket.class, ClientPluginMessagePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientDebugSubscriptionRequestPacket.class, ClientDebugSubscriptionRequestPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientEditBookPacket.class, ClientEditBookPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientQueryEntityNbtPacket.class, ClientQueryEntityNbtPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientInteractEntityPacket.class, ClientInteractEntityPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientGenerateStructurePacket.class, ClientGenerateStructurePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientKeepAlivePacket.class, ClientKeepAlivePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientLockDifficultyPacket.class, ClientLockDifficultyPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlayerPositionPacket.class, ClientPlayerPositionPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlayerPositionAndRotationPacket.class, ClientPlayerPositionAndRotationPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlayerRotationPacket.class, ClientPlayerRotationPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlayerPositionStatusPacket.class, ClientPlayerPositionStatusPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientVehicleMovePacket.class, ClientVehicleMovePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSteerBoatPacket.class, ClientSteerBoatPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPickItemFromBlockPacket.class, ClientPickItemFromBlockPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPickItemFromEntityPacket.class, ClientPickItemFromEntityPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPingRequestPacket.class, ClientPingRequestPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlaceRecipePacket.class, ClientPlaceRecipePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlayerAbilitiesPacket.class, ClientPlayerAbilitiesPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlayerActionPacket.class, ClientPlayerActionPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientEntityActionPacket.class, ClientEntityActionPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientInputPacket.class, ClientInputPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlayerLoadedPacket.class, ClientPlayerLoadedPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPongPacket.class, ClientPongPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSetRecipeBookStatePacket.class, ClientSetRecipeBookStatePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientRecipeBookSeenRecipePacket.class, ClientRecipeBookSeenRecipePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientNameItemPacket.class, ClientNameItemPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientResourcePackStatusPacket.class, ClientResourcePackStatusPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientAdvancementTabPacket.class, ClientAdvancementTabPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSelectTradePacket.class, ClientSelectTradePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSetBeaconEffectPacket.class, ClientSetBeaconEffectPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientHeldItemChangePacket.class, ClientHeldItemChangePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientUpdateCommandBlockPacket.class, ClientUpdateCommandBlockPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientUpdateCommandBlockMinecartPacket.class, ClientUpdateCommandBlockMinecartPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientCreativeInventoryActionPacket.class, ClientCreativeInventoryActionPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientUpdateJigsawBlockPacket.class, ClientUpdateJigsawBlockPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientUpdateStructureBlockPacket.class, ClientUpdateStructureBlockPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSetTestBlockPacket.class, ClientSetTestBlockPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientUpdateSignPacket.class, ClientUpdateSignPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientAnimationPacket.class, ClientAnimationPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientSpectatePacket.class, ClientSpectatePacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientTestInstanceBlockActionPacket.class, ClientTestInstanceBlockActionPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientPlayerBlockPlacementPacket.class, ClientPlayerBlockPlacementPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientUseItemPacket.class, ClientUseItemPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClientCustomClickActionPacket.class, ClientCustomClickActionPacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.PLAY;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    abstract sealed class Server extends PacketRegistryTemplate<ServerPacket> {
        // Annotation pour l'élément suivant
        @SafeVarargs Server(Entry<? extends ServerPacket>... suppliers) {
            // Accès à l'objet courant/parent
            super(suppliers);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionSide side() {
            // Renvoie une valeur à l'appelant
            return ConnectionSide.SERVER;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ServerHandshake extends Server {
        // Début d'une méthode/d'un bloc
        public ServerHandshake() {
            // Accès à l'objet courant/parent
            super(
                    // Empty
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.HANDSHAKE;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ServerStatus extends Server {
        // Début d'une méthode/d'un bloc
        public ServerStatus() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(ResponsePacket.class, ResponsePacket.SERIALIZER),
                    // Instruction de code
                    entry(PingResponsePacket.class, PingResponsePacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.STATUS;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ServerLogin extends Server {
        // Début d'une méthode/d'un bloc
        public ServerLogin() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(LoginDisconnectPacket.class, LoginDisconnectPacket.SERIALIZER),
                    // Instruction de code
                    entry(EncryptionRequestPacket.class, EncryptionRequestPacket.SERIALIZER),
                    // Instruction de code
                    entry(LoginSuccessPacket.class, LoginSuccessPacket.SERIALIZER),
                    // Instruction de code
                    entry(SetCompressionPacket.class, SetCompressionPacket.SERIALIZER),
                    // Instruction de code
                    entry(LoginPluginRequestPacket.class, LoginPluginRequestPacket.SERIALIZER),
                    // Instruction de code
                    entry(CookieRequestPacket.class, CookieRequestPacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.LOGIN;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ServerConfiguration extends Server {
        // Début d'une méthode/d'un bloc
        public ServerConfiguration() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(CookieRequestPacket.class, CookieRequestPacket.SERIALIZER),
                    // Instruction de code
                    entry(PluginMessagePacket.class, PluginMessagePacket.SERIALIZER),
                    // Instruction de code
                    entry(DisconnectPacket.class, DisconnectPacket.SERIALIZER),
                    // Instruction de code
                    entry(FinishConfigurationPacket.class, FinishConfigurationPacket.SERIALIZER),
                    // Instruction de code
                    entry(KeepAlivePacket.class, KeepAlivePacket.SERIALIZER),
                    // Instruction de code
                    entry(PingPacket.class, PingPacket.SERIALIZER),
                    // Instruction de code
                    entry(ResetChatPacket.class, ResetChatPacket.SERIALIZER),
                    // Instruction de code
                    entry(RegistryDataPacket.class, RegistryDataPacket.SERIALIZER),
                    // Instruction de code
                    entry(ResourcePackPopPacket.class, ResourcePackPopPacket.SERIALIZER),
                    // Instruction de code
                    entry(ResourcePackPushPacket.class, ResourcePackPushPacket.SERIALIZER),
                    // Instruction de code
                    entry(CookieStorePacket.class, CookieStorePacket.SERIALIZER),
                    // Instruction de code
                    entry(TransferPacket.class, TransferPacket.SERIALIZER),
                    // Instruction de code
                    entry(UpdateEnabledFeaturesPacket.class, UpdateEnabledFeaturesPacket.SERIALIZER),
                    // Instruction de code
                    entry(TagsPacket.class, TagsPacket.SERIALIZER),
                    // Instruction de code
                    entry(SelectKnownPacksPacket.class, SelectKnownPacksPacket.SERIALIZER),
                    // Instruction de code
                    entry(CustomReportDetailsPacket.class, CustomReportDetailsPacket.SERIALIZER),
                    // Instruction de code
                    entry(ServerLinksPacket.class, ServerLinksPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClearDialogPacket.class, ClearDialogPacket.SERIALIZER),
                    // Instruction de code
                    entry(ShowDialogPacket.class, ShowDialogPacket.INLINE_SERIALIZER),
                    // Instruction de code
                    entry(CodeOfConductPacket.class, CodeOfConductPacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.CONFIGURATION;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class ServerPlay extends Server {
        // Début d'une méthode/d'un bloc
        public ServerPlay() {
            // Accès à l'objet courant/parent
            super(
                    // Instruction de code
                    entry(BundlePacket.class, BundlePacket.SERIALIZER),
                    // Instruction de code
                    entry(SpawnEntityPacket.class, SpawnEntityPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityAnimationPacket.class, EntityAnimationPacket.SERIALIZER),
                    // Instruction de code
                    entry(StatisticsPacket.class, StatisticsPacket.SERIALIZER),
                    // Instruction de code
                    entry(AcknowledgeBlockChangePacket.class, AcknowledgeBlockChangePacket.SERIALIZER),
                    // Instruction de code
                    entry(BlockBreakAnimationPacket.class, BlockBreakAnimationPacket.SERIALIZER),
                    // Instruction de code
                    entry(BlockEntityDataPacket.class, BlockEntityDataPacket.SERIALIZER),
                    // Instruction de code
                    entry(BlockActionPacket.class, BlockActionPacket.SERIALIZER),
                    // Instruction de code
                    entry(BlockChangePacket.class, BlockChangePacket.SERIALIZER),
                    // Instruction de code
                    entry(BossBarPacket.class, BossBarPacket.SERIALIZER),
                    // Instruction de code
                    entry(ServerDifficultyPacket.class, ServerDifficultyPacket.SERIALIZER),
                    // Instruction de code
                    entry(ChunkBatchFinishedPacket.class, ChunkBatchFinishedPacket.SERIALIZER),
                    // Instruction de code
                    entry(ChunkBatchStartPacket.class, ChunkBatchStartPacket.SERIALIZER),
                    // Instruction de code
                    entry(ChunkBiomesPacket.class, ChunkBiomesPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClearTitlesPacket.class, ClearTitlesPacket.SERIALIZER),
                    // Instruction de code
                    entry(TabCompletePacket.class, TabCompletePacket.SERIALIZER),
                    // Instruction de code
                    entry(DeclareCommandsPacket.class, DeclareCommandsPacket.SERIALIZER),
                    // Instruction de code
                    entry(CloseWindowPacket.class, CloseWindowPacket.SERIALIZER),
                    // Instruction de code
                    entry(WindowItemsPacket.class, WindowItemsPacket.SERIALIZER),
                    // Instruction de code
                    entry(WindowPropertyPacket.class, WindowPropertyPacket.SERIALIZER),
                    // Instruction de code
                    entry(SetSlotPacket.class, SetSlotPacket.SERIALIZER),
                    // Instruction de code
                    entry(CookieRequestPacket.class, CookieRequestPacket.SERIALIZER),
                    // Instruction de code
                    entry(SetCooldownPacket.class, SetCooldownPacket.SERIALIZER),
                    // Instruction de code
                    entry(CustomChatCompletionPacket.class, CustomChatCompletionPacket.SERIALIZER),
                    // Instruction de code
                    entry(PluginMessagePacket.class, PluginMessagePacket.SERIALIZER),
                    // Instruction de code
                    entry(DamageEventPacket.class, DamageEventPacket.SERIALIZER),
                    // Instruction de code
                    entry(DebugBlockValuePacket.class, DebugBlockValuePacket.SERIALIZER),
                    // Instruction de code
                    entry(DebugChunkValuePacket.class, DebugChunkValuePacket.SERIALIZER),
                    // Instruction de code
                    entry(DebugEntityValuePacket.class, DebugEntityValuePacket.SERIALIZER),
                    // Instruction de code
                    entry(DebugEventPacket.class, DebugEventPacket.SERIALIZER),
                    // Instruction de code
                    entry(DebugSamplePacket.class, DebugSamplePacket.SERIALIZER),
                    // Instruction de code
                    entry(DeleteChatPacket.class, DeleteChatPacket.SERIALIZER),
                    // Instruction de code
                    entry(DisconnectPacket.class, DisconnectPacket.SERIALIZER),
                    // Instruction de code
                    entry(DisguisedChatPacket.class, DisguisedChatPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityStatusPacket.class, EntityStatusPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityPositionSyncPacket.class, EntityPositionSyncPacket.SERIALIZER),
                    // Instruction de code
                    entry(ExplosionPacket.class, ExplosionPacket.SERIALIZER),
                    // Instruction de code
                    entry(UnloadChunkPacket.class, UnloadChunkPacket.SERIALIZER),
                    // Instruction de code
                    entry(ChangeGameStatePacket.class, ChangeGameStatePacket.SERIALIZER),
                    // Instruction de code
                    entry(GameTestHighlightPosPacket.class, GameTestHighlightPosPacket.SERIALIZER),
                    // Instruction de code
                    entry(OpenHorseWindowPacket.class, OpenHorseWindowPacket.SERIALIZER),
                    // Instruction de code
                    entry(HitAnimationPacket.class, HitAnimationPacket.SERIALIZER),
                    // Instruction de code
                    entry(InitializeWorldBorderPacket.class, InitializeWorldBorderPacket.SERIALIZER),
                    // Instruction de code
                    entry(KeepAlivePacket.class, KeepAlivePacket.SERIALIZER),
                    // Instruction de code
                    entry(ChunkDataPacket.class, ChunkDataPacket.SERIALIZER),
                    // Instruction de code
                    entry(WorldEventPacket.class, WorldEventPacket.SERIALIZER),
                    // Instruction de code
                    entry(ParticlePacket.class, ParticlePacket.SERIALIZER),
                    // Instruction de code
                    entry(UpdateLightPacket.class, UpdateLightPacket.SERIALIZER),
                    // Instruction de code
                    entry(JoinGamePacket.class, JoinGamePacket.SERIALIZER),
                    // Instruction de code
                    entry(MapDataPacket.class, MapDataPacket.SERIALIZER),
                    // Instruction de code
                    entry(TradeListPacket.class, TradeListPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityPositionPacket.class, EntityPositionPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityPositionAndRotationPacket.class, EntityPositionAndRotationPacket.SERIALIZER),
                    // Instruction de code
                    entry(MoveMinecartPacket.class, MoveMinecartPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityRotationPacket.class, EntityRotationPacket.SERIALIZER),
                    // Instruction de code
                    entry(VehicleMovePacket.class, VehicleMovePacket.SERIALIZER),
                    // Instruction de code
                    entry(OpenBookPacket.class, OpenBookPacket.SERIALIZER),
                    // Instruction de code
                    entry(OpenWindowPacket.class, OpenWindowPacket.SERIALIZER),
                    // Instruction de code
                    entry(OpenSignEditorPacket.class, OpenSignEditorPacket.SERIALIZER),
                    // Instruction de code
                    entry(PingPacket.class, PingPacket.SERIALIZER),
                    // Instruction de code
                    entry(PingResponsePacket.class, PingResponsePacket.SERIALIZER),
                    // Instruction de code
                    entry(PlaceGhostRecipePacket.class, PlaceGhostRecipePacket.SERIALIZER),
                    // Instruction de code
                    entry(PlayerAbilitiesPacket.class, PlayerAbilitiesPacket.SERIALIZER),
                    // Instruction de code
                    entry(PlayerChatMessagePacket.class, PlayerChatMessagePacket.SERIALIZER),
                    // Instruction de code
                    entry(EndCombatEventPacket.class, EndCombatEventPacket.SERIALIZER),
                    // Instruction de code
                    entry(EnterCombatEventPacket.class, EnterCombatEventPacket.SERIALIZER),
                    // Instruction de code
                    entry(DeathCombatEventPacket.class, DeathCombatEventPacket.SERIALIZER),
                    // Instruction de code
                    entry(PlayerInfoRemovePacket.class, PlayerInfoRemovePacket.SERIALIZER),
                    // Instruction de code
                    entry(PlayerInfoUpdatePacket.class, PlayerInfoUpdatePacket.SERIALIZER),
                    // Instruction de code
                    entry(FacePlayerPacket.class, FacePlayerPacket.SERIALIZER),
                    // Instruction de code
                    entry(PlayerPositionAndLookPacket.class, PlayerPositionAndLookPacket.SERIALIZER),
                    // Instruction de code
                    entry(PlayerRotationPacket.class, PlayerRotationPacket.SERIALIZER),
                    // Instruction de code
                    entry(RecipeBookAddPacket.class, RecipeBookAddPacket.SERIALIZER),
                    // Instruction de code
                    entry(RecipeBookRemovePacket.class, RecipeBookRemovePacket.SERIALIZER),
                    // Instruction de code
                    entry(RecipeBookSettingsPacket.class, RecipeBookSettingsPacket.SERIALIZER),
                    // Instruction de code
                    entry(DestroyEntitiesPacket.class, DestroyEntitiesPacket.SERIALIZER),
                    // Instruction de code
                    entry(RemoveEntityEffectPacket.class, RemoveEntityEffectPacket.SERIALIZER),
                    // Instruction de code
                    entry(ResetScorePacket.class, ResetScorePacket.SERIALIZER),
                    // Instruction de code
                    entry(ResourcePackPopPacket.class, ResourcePackPopPacket.SERIALIZER),
                    // Instruction de code
                    entry(ResourcePackPushPacket.class, ResourcePackPushPacket.SERIALIZER),
                    // Instruction de code
                    entry(RespawnPacket.class, RespawnPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityHeadLookPacket.class, EntityHeadLookPacket.SERIALIZER),
                    // Instruction de code
                    entry(MultiBlockChangePacket.class, MultiBlockChangePacket.SERIALIZER),
                    // Instruction de code
                    entry(SelectAdvancementTabPacket.class, SelectAdvancementTabPacket.SERIALIZER),
                    // Instruction de code
                    entry(ServerDataPacket.class, ServerDataPacket.SERIALIZER),
                    // Instruction de code
                    entry(ActionBarPacket.class, ActionBarPacket.SERIALIZER),
                    // Instruction de code
                    entry(WorldBorderCenterPacket.class, WorldBorderCenterPacket.SERIALIZER),
                    // Instruction de code
                    entry(WorldBorderLerpSizePacket.class, WorldBorderLerpSizePacket.SERIALIZER),
                    // Instruction de code
                    entry(WorldBorderSizePacket.class, WorldBorderSizePacket.SERIALIZER),
                    // Instruction de code
                    entry(WorldBorderWarningDelayPacket.class, WorldBorderWarningDelayPacket.SERIALIZER),
                    // Instruction de code
                    entry(WorldBorderWarningReachPacket.class, WorldBorderWarningReachPacket.SERIALIZER),
                    // Instruction de code
                    entry(CameraPacket.class, CameraPacket.SERIALIZER),
                    // Instruction de code
                    entry(UpdateViewPositionPacket.class, UpdateViewPositionPacket.SERIALIZER),
                    // Instruction de code
                    entry(UpdateViewDistancePacket.class, UpdateViewDistancePacket.SERIALIZER),
                    // Instruction de code
                    entry(SetCursorItemPacket.class, SetCursorItemPacket.SERIALIZER),
                    // Instruction de code
                    entry(SpawnPositionPacket.class, SpawnPositionPacket.SERIALIZER),
                    // Instruction de code
                    entry(DisplayScoreboardPacket.class, DisplayScoreboardPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityMetaDataPacket.class, EntityMetaDataPacket.SERIALIZER),
                    // Instruction de code
                    entry(AttachEntityPacket.class, AttachEntityPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityVelocityPacket.class, EntityVelocityPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityEquipmentPacket.class, EntityEquipmentPacket.SERIALIZER),
                    // Instruction de code
                    entry(SetExperiencePacket.class, SetExperiencePacket.SERIALIZER),
                    // Instruction de code
                    entry(UpdateHealthPacket.class, UpdateHealthPacket.SERIALIZER),
                    // Instruction de code
                    entry(HeldItemChangePacket.class, HeldItemChangePacket.SERIALIZER),
                    // Instruction de code
                    entry(ScoreboardObjectivePacket.class, ScoreboardObjectivePacket.SERIALIZER),
                    // Instruction de code
                    entry(SetPassengersPacket.class, SetPassengersPacket.SERIALIZER),
                    // Instruction de code
                    entry(SetPlayerInventorySlotPacket.class, SetPlayerInventorySlotPacket.SERIALIZER),
                    // Instruction de code
                    entry(TeamsPacket.class, TeamsPacket.SERIALIZER),
                    // Instruction de code
                    entry(UpdateScorePacket.class, UpdateScorePacket.SERIALIZER),
                    // Instruction de code
                    entry(UpdateSimulationDistancePacket.class, UpdateSimulationDistancePacket.SERIALIZER),
                    // Instruction de code
                    entry(SetTitleSubTitlePacket.class, SetTitleSubTitlePacket.SERIALIZER),
                    // Instruction de code
                    entry(TimeUpdatePacket.class, TimeUpdatePacket.SERIALIZER),
                    // Instruction de code
                    entry(SetTitleTextPacket.class, SetTitleTextPacket.SERIALIZER),
                    // Instruction de code
                    entry(SetTitleTimePacket.class, SetTitleTimePacket.SERIALIZER),
                    // Instruction de code
                    entry(EntitySoundEffectPacket.class, EntitySoundEffectPacket.SERIALIZER),
                    // Instruction de code
                    entry(SoundEffectPacket.class, SoundEffectPacket.SERIALIZER),
                    // Instruction de code
                    entry(StartConfigurationPacket.class, StartConfigurationPacket.SERIALIZER),
                    // Instruction de code
                    entry(StopSoundPacket.class, StopSoundPacket.SERIALIZER),
                    // Instruction de code
                    entry(CookieStorePacket.class, CookieStorePacket.SERIALIZER),
                    // Instruction de code
                    entry(SystemChatPacket.class, SystemChatPacket.SERIALIZER),
                    // Instruction de code
                    entry(PlayerListHeaderAndFooterPacket.class, PlayerListHeaderAndFooterPacket.SERIALIZER),
                    // Instruction de code
                    entry(NbtQueryResponsePacket.class, NbtQueryResponsePacket.SERIALIZER),
                    // Instruction de code
                    entry(CollectItemPacket.class, CollectItemPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityTeleportPacket.class, EntityTeleportPacket.SERIALIZER),
                    // Instruction de code
                    entry(TestInstanceBlockStatus.class, TestInstanceBlockStatus.SERIALIZER),
                    // Instruction de code
                    entry(SetTickStatePacket.class, SetTickStatePacket.SERIALIZER),
                    // Instruction de code
                    entry(TickStepPacket.class, TickStepPacket.SERIALIZER),
                    // Instruction de code
                    entry(TransferPacket.class, TransferPacket.SERIALIZER),
                    // Instruction de code
                    entry(AdvancementsPacket.class, AdvancementsPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityAttributesPacket.class, EntityAttributesPacket.SERIALIZER),
                    // Instruction de code
                    entry(EntityEffectPacket.class, EntityEffectPacket.SERIALIZER),
                    // Instruction de code
                    entry(DeclareRecipesPacket.class, DeclareRecipesPacket.SERIALIZER),
                    // Instruction de code
                    entry(TagsPacket.class, TagsPacket.SERIALIZER),
                    // Instruction de code
                    entry(ProjectilePowerPacket.class, ProjectilePowerPacket.SERIALIZER),
                    // Instruction de code
                    entry(CustomReportDetailsPacket.class, CustomReportDetailsPacket.SERIALIZER),
                    // Instruction de code
                    entry(ServerLinksPacket.class, ServerLinksPacket.SERIALIZER),
                    // Instruction de code
                    entry(TrackedWaypointPacket.class, TrackedWaypointPacket.SERIALIZER),
                    // Instruction de code
                    entry(ClearDialogPacket.class, ClearDialogPacket.SERIALIZER),
                    // Instruction de code
                    entry(ShowDialogPacket.class, ShowDialogPacket.SERIALIZER)
            // Fin d'un bloc/d'une expression
            );
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ConnectionState state() {
            // Renvoie une valeur à l'appelant
            return ConnectionState.PLAY;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Déclaration de type (classe/interface/enum/record)
    abstract sealed class PacketRegistryTemplate<T> implements PacketRegistry<T> {
        // Instruction de code
        private final PacketInfo<? extends T>[] suppliers;
        // Affecte une valeur
        private final ClassValue<PacketInfo<T>> packetIds = new ClassValue<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            protected PacketInfo<T> computeValue(Class<?> type) {
                // Boucle : répète un bloc
                for (PacketInfo<? extends T> info : suppliers) {
                    // Embranchement : vérifie une condition
                    if (info != null && info.packetClass == type) {
                        // Renvoie une valeur à l'appelant
                        return (PacketInfo<T>) info;
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Lève une exception
                throw new IllegalStateException("Packet type " + type + " cannot be sent in state " + side().name() + "_" + state().name() + "!");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Annotation pour l'élément suivant
        @SafeVarargs PacketRegistryTemplate(Entry<? extends T>... suppliers) {
            // Affecte une valeur
            PacketInfo<? extends T>[] packetInfos = new PacketInfo[suppliers.length];
            // Boucle : répète un bloc
            for (int i = 0; i < suppliers.length; i++) {
                // Affecte une valeur
                final Entry<? extends T> entry = suppliers[i];
                // Embranchement : vérifie une condition
                if (entry == null) continue;
                // Appelle une méthode
                packetInfos[i] = new PacketInfo(entry.type, i, entry.reader);
            // Fin d'un bloc/d'une expression
            }
            // Accès à l'objet courant/parent
            this.suppliers = packetInfos;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public @UnknownNullability T create(int packetId, NetworkBuffer reader) {
            // Appelle une méthode
            final PacketInfo<T> info = packetInfo(packetId);
            // Affecte une valeur
            final NetworkBuffer.Type<T> supplier = info.serializer;
            // Appelle une méthode
            final T packet = supplier.read(reader);
            // Embranchement : vérifie une condition
            if (packet == null) {
                // Lève une exception
                throw new IllegalStateException("Packet " + info.packetClass + " failed to read!");
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return packet;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public PacketInfo<T> packetInfo(Class<?> packetClass) {
            // Renvoie une valeur à l'appelant
            return packetIds.get(packetClass);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public PacketInfo<T> packetInfo(int packetId) {
            // Instruction de code
            final PacketInfo<T> info;
            // Embranchement : vérifie une condition
            if (packetId < 0 || packetId >= suppliers.length || (info = (PacketInfo<T>) suppliers[packetId]) == null) {
                // Lève une exception
                throw new IllegalStateException("Packet id 0x" + Integer.toHexString(packetId) + " isn't registered!");
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return info;
        // Fin d'un bloc/d'une expression
        }


        // Déclaration de type (classe/interface/enum/record)
        record Entry<T>(Class<T> type, NetworkBuffer.Type<T> reader) {
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @SuppressWarnings({"unchecked", "rawtypes"})
        // Début d'une méthode/d'un bloc
        static <T> Entry<T> entry(Class<T> type, NetworkBuffer.Type<T> reader) {
            // Renvoie une valeur à l'appelant
            return new Entry<>((Class) type, reader);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    enum ConnectionSide {
        // Instruction de code
        CLIENT,
        // Instruction de code
        SERVER
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
