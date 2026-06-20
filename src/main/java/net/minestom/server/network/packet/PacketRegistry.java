// Package declaration for this file
package net.minestom.server.network.packet;

// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.packet.client.ClientPacket;
// Import of a required class
import net.minestom.server.network.packet.client.common.*;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientAcceptCodeOfConductPacket;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientFinishConfigurationPacket;
// Import of a required class
import net.minestom.server.network.packet.client.configuration.ClientSelectKnownPacksPacket;
// Import of a required class
import net.minestom.server.network.packet.client.handshake.ClientHandshakePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientEncryptionResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginAcknowledgedPacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginPluginResponsePacket;
// Import of a required class
import net.minestom.server.network.packet.client.login.ClientLoginStartPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.*;
// Import of a required class
import net.minestom.server.network.packet.client.status.StatusRequestPacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.packet.server.common.*;
// Import of a required class
import net.minestom.server.network.packet.server.configuration.*;
// Import of a required class
import net.minestom.server.network.packet.server.login.*;
// Import of a required class
import net.minestom.server.network.packet.server.play.*;
// Import of a required class
import net.minestom.server.network.packet.server.status.ResponsePacket;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;
// Import of a required class
import org.jetbrains.annotations.Unmodifiable;

// Import of a required class
import java.util.List;

// Type declaration (class/interface/enum/record)
public interface PacketRegistry<T> {
    // Annotation for the following element
    @UnknownNullability
    // Calls a method
    T create(int packetId, NetworkBuffer reader);

    // Calls a method
    PacketInfo<T> packetInfo(Class<?> packetClass);

    // Start of a method/block
    default PacketInfo<T> packetInfo(T packet) {
        // Returns a value to the caller
        return packetInfo(packet.getClass());
    // End of a block/expression
    }

    // Calls a method
    PacketInfo<T> packetInfo(int packetId);

    // Calls a method
    ConnectionState state();

    // Calls a method
    ConnectionSide side();

    // Annotation for the following element
    @ApiStatus.Experimental
    // Annotation for the following element
    @Unmodifiable
    // Calls a method
    List<PacketInfo<? extends T>> packets();

    // Type declaration (class/interface/enum/record)
    record PacketInfo<T>(Class<T> packetClass, int id, NetworkBuffer.Type<T> serializer) {
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    abstract sealed class Client<T extends ClientPacket> extends PacketRegistryTemplate<T> {
        // Annotation for the following element
        @SafeVarargs Client(Entry<? extends T>... suppliers) {
            // Access to the current/parent object
            super(suppliers);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public final ConnectionSide side() {
            // Returns a value to the caller
            return ConnectionSide.CLIENT;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ClientHandshake extends Client<ClientPacket.Handshake> {
        // Start of a method/block
        public ClientHandshake() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(ClientHandshakePacket.class, ClientHandshakePacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.HANDSHAKE;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ClientStatus extends Client<ClientPacket.Status> {
        // Start of a method/block
        public ClientStatus() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(StatusRequestPacket.class, StatusRequestPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPingRequestPacket.class, ClientPingRequestPacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.STATUS;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ClientLogin extends Client<ClientPacket.Login> {
        // Start of a method/block
        public ClientLogin() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(ClientLoginStartPacket.class, ClientLoginStartPacket.SERIALIZER),
                    // Code statement
                    entry(ClientEncryptionResponsePacket.class, ClientEncryptionResponsePacket.SERIALIZER),
                    // Code statement
                    entry(ClientLoginPluginResponsePacket.class, ClientLoginPluginResponsePacket.SERIALIZER),
                    // Code statement
                    entry(ClientLoginAcknowledgedPacket.class, ClientLoginAcknowledgedPacket.SERIALIZER),
                    // Code statement
                    entry(ClientCookieResponsePacket.class, ClientCookieResponsePacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.LOGIN;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ClientConfiguration extends Client<ClientPacket.Configuration> {
        // Start of a method/block
        public ClientConfiguration() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(ClientSettingsPacket.class, ClientSettingsPacket.SERIALIZER),
                    // Code statement
                    entry(ClientCookieResponsePacket.class, ClientCookieResponsePacket.SERIALIZER),
                    // Code statement
                    entry(ClientPluginMessagePacket.class, ClientPluginMessagePacket.SERIALIZER),
                    // Code statement
                    entry(ClientFinishConfigurationPacket.class, ClientFinishConfigurationPacket.SERIALIZER),
                    // Code statement
                    entry(ClientKeepAlivePacket.class, ClientKeepAlivePacket.SERIALIZER),
                    // Code statement
                    entry(ClientPongPacket.class, ClientPongPacket.SERIALIZER),
                    // Code statement
                    entry(ClientResourcePackStatusPacket.class, ClientResourcePackStatusPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSelectKnownPacksPacket.class, ClientSelectKnownPacksPacket.SERIALIZER),
                    // Code statement
                    entry(ClientCustomClickActionPacket.class, ClientCustomClickActionPacket.SERIALIZER),
                    // Code statement
                    entry(ClientAcceptCodeOfConductPacket.class, ClientAcceptCodeOfConductPacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.CONFIGURATION;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ClientPlay extends Client<ClientPacket.Play> {
        // Start of a method/block
        public ClientPlay() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(ClientTeleportConfirmPacket.class, ClientTeleportConfirmPacket.SERIALIZER),
                    // Code statement
                    entry(ClientAttackPacket.class, ClientAttackPacket.SERIALIZER),
                    // Code statement
                    entry(ClientQueryBlockNbtPacket.class, ClientQueryBlockNbtPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSelectBundleItemPacket.class, ClientSelectBundleItemPacket.SERIALIZER),
                    // Code statement
                    entry(ClientChangeDifficultyPacket.class, ClientChangeDifficultyPacket.SERIALIZER),
                    // Code statement
                    entry(ClientChangeGameModePacket.class, ClientChangeGameModePacket.SERIALIZER),
                    // Code statement
                    entry(ClientChatAckPacket.class, ClientChatAckPacket.SERIALIZER),
                    // Code statement
                    entry(ClientCommandChatPacket.class, ClientCommandChatPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSignedCommandChatPacket.class, ClientSignedCommandChatPacket.SERIALIZER),
                    // Code statement
                    entry(ClientChatMessagePacket.class, ClientChatMessagePacket.SERIALIZER),
                    // Code statement
                    entry(ClientChatSessionUpdatePacket.class, ClientChatSessionUpdatePacket.SERIALIZER),
                    // Code statement
                    entry(ClientChunkBatchReceivedPacket.class, ClientChunkBatchReceivedPacket.SERIALIZER),
                    // Code statement
                    entry(ClientStatusPacket.class, ClientStatusPacket.SERIALIZER),
                    // Code statement
                    entry(ClientTickEndPacket.class, ClientTickEndPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSettingsPacket.class, ClientSettingsPacket.SERIALIZER),
                    // Code statement
                    entry(ClientTabCompletePacket.class, ClientTabCompletePacket.SERIALIZER),
                    // Code statement
                    entry(ClientConfigurationAckPacket.class, ClientConfigurationAckPacket.SERIALIZER),
                    // Code statement
                    entry(ClientClickWindowButtonPacket.class, ClientClickWindowButtonPacket.SERIALIZER),
                    // Code statement
                    entry(ClientClickWindowPacket.class, ClientClickWindowPacket.SERIALIZER),
                    // Code statement
                    entry(ClientCloseWindowPacket.class, ClientCloseWindowPacket.SERIALIZER),
                    // Code statement
                    entry(ClientWindowSlotStatePacket.class, ClientWindowSlotStatePacket.SERIALIZER),
                    // Code statement
                    entry(ClientCookieResponsePacket.class, ClientCookieResponsePacket.SERIALIZER),
                    // Code statement
                    entry(ClientPluginMessagePacket.class, ClientPluginMessagePacket.SERIALIZER),
                    // Code statement
                    entry(ClientDebugSubscriptionRequestPacket.class, ClientDebugSubscriptionRequestPacket.SERIALIZER),
                    // Code statement
                    entry(ClientEditBookPacket.class, ClientEditBookPacket.SERIALIZER),
                    // Code statement
                    entry(ClientQueryEntityNbtPacket.class, ClientQueryEntityNbtPacket.SERIALIZER),
                    // Code statement
                    entry(ClientInteractEntityPacket.class, ClientInteractEntityPacket.SERIALIZER),
                    // Code statement
                    entry(ClientGenerateStructurePacket.class, ClientGenerateStructurePacket.SERIALIZER),
                    // Code statement
                    entry(ClientKeepAlivePacket.class, ClientKeepAlivePacket.SERIALIZER),
                    // Code statement
                    entry(ClientLockDifficultyPacket.class, ClientLockDifficultyPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlayerPositionPacket.class, ClientPlayerPositionPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlayerPositionAndRotationPacket.class, ClientPlayerPositionAndRotationPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlayerRotationPacket.class, ClientPlayerRotationPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlayerPositionStatusPacket.class, ClientPlayerPositionStatusPacket.SERIALIZER),
                    // Code statement
                    entry(ClientVehicleMovePacket.class, ClientVehicleMovePacket.SERIALIZER),
                    // Code statement
                    entry(ClientSteerBoatPacket.class, ClientSteerBoatPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPickItemFromBlockPacket.class, ClientPickItemFromBlockPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPickItemFromEntityPacket.class, ClientPickItemFromEntityPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPingRequestPacket.class, ClientPingRequestPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlaceRecipePacket.class, ClientPlaceRecipePacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlayerAbilitiesPacket.class, ClientPlayerAbilitiesPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlayerActionPacket.class, ClientPlayerActionPacket.SERIALIZER),
                    // Code statement
                    entry(ClientEntityActionPacket.class, ClientEntityActionPacket.SERIALIZER),
                    // Code statement
                    entry(ClientInputPacket.class, ClientInputPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlayerLoadedPacket.class, ClientPlayerLoadedPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPongPacket.class, ClientPongPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSetRecipeBookStatePacket.class, ClientSetRecipeBookStatePacket.SERIALIZER),
                    // Code statement
                    entry(ClientRecipeBookSeenRecipePacket.class, ClientRecipeBookSeenRecipePacket.SERIALIZER),
                    // Code statement
                    entry(ClientNameItemPacket.class, ClientNameItemPacket.SERIALIZER),
                    // Code statement
                    entry(ClientResourcePackStatusPacket.class, ClientResourcePackStatusPacket.SERIALIZER),
                    // Code statement
                    entry(ClientAdvancementTabPacket.class, ClientAdvancementTabPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSelectTradePacket.class, ClientSelectTradePacket.SERIALIZER),
                    // Code statement
                    entry(ClientSetBeaconEffectPacket.class, ClientSetBeaconEffectPacket.SERIALIZER),
                    // Code statement
                    entry(ClientHeldItemChangePacket.class, ClientHeldItemChangePacket.SERIALIZER),
                    // Code statement
                    entry(ClientUpdateCommandBlockPacket.class, ClientUpdateCommandBlockPacket.SERIALIZER),
                    // Code statement
                    entry(ClientUpdateCommandBlockMinecartPacket.class, ClientUpdateCommandBlockMinecartPacket.SERIALIZER),
                    // Code statement
                    entry(ClientCreativeInventoryActionPacket.class, ClientCreativeInventoryActionPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSetGameRulesPacket.class, ClientSetGameRulesPacket.SERIALIZER),
                    // Code statement
                    entry(ClientUpdateJigsawBlockPacket.class, ClientUpdateJigsawBlockPacket.SERIALIZER),
                    // Code statement
                    entry(ClientUpdateStructureBlockPacket.class, ClientUpdateStructureBlockPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSetTestBlockPacket.class, ClientSetTestBlockPacket.SERIALIZER),
                    // Code statement
                    entry(ClientUpdateSignPacket.class, ClientUpdateSignPacket.SERIALIZER),
                    // Code statement
                    entry(ClientSpectateEntityPacket.class, ClientSpectateEntityPacket.SERIALIZER),
                    // Code statement
                    entry(ClientAnimationPacket.class, ClientAnimationPacket.SERIALIZER),
                    // Code statement
                    entry(ClientTeleportToEntityPacket.class, ClientTeleportToEntityPacket.SERIALIZER),
                    // Code statement
                    entry(ClientTestInstanceBlockActionPacket.class, ClientTestInstanceBlockActionPacket.SERIALIZER),
                    // Code statement
                    entry(ClientPlayerBlockPlacementPacket.class, ClientPlayerBlockPlacementPacket.SERIALIZER),
                    // Code statement
                    entry(ClientUseItemPacket.class, ClientUseItemPacket.SERIALIZER),
                    // Code statement
                    entry(ClientCustomClickActionPacket.class, ClientCustomClickActionPacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.PLAY;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    abstract sealed class Server<T extends ServerPacket> extends PacketRegistryTemplate<T> {
        // Annotation for the following element
        @SafeVarargs Server(Entry<? extends T>... suppliers) {
            // Access to the current/parent object
            super(suppliers);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public final ConnectionSide side() {
            // Returns a value to the caller
            return ConnectionSide.SERVER;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ServerHandshake extends Server<ServerPacket.Handshake> {
        // Start of a method/block
        public ServerHandshake() {
            // Access to the current/parent object
            super(
                    // Empty
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.HANDSHAKE;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ServerStatus extends Server<ServerPacket.Status> {
        // Start of a method/block
        public ServerStatus() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(ResponsePacket.class, ResponsePacket.SERIALIZER),
                    // Code statement
                    entry(PingResponsePacket.class, PingResponsePacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.STATUS;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ServerLogin extends Server<ServerPacket.Login> {
        // Start of a method/block
        public ServerLogin() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(LoginDisconnectPacket.class, LoginDisconnectPacket.SERIALIZER),
                    // Code statement
                    entry(EncryptionRequestPacket.class, EncryptionRequestPacket.SERIALIZER),
                    // Code statement
                    entry(LoginSuccessPacket.class, LoginSuccessPacket.SERIALIZER),
                    // Code statement
                    entry(SetCompressionPacket.class, SetCompressionPacket.SERIALIZER),
                    // Code statement
                    entry(LoginPluginRequestPacket.class, LoginPluginRequestPacket.SERIALIZER),
                    // Code statement
                    entry(CookieRequestPacket.class, CookieRequestPacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.LOGIN;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ServerConfiguration extends Server<ServerPacket.Configuration> {
        // Start of a method/block
        public ServerConfiguration() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(CookieRequestPacket.class, CookieRequestPacket.SERIALIZER),
                    // Code statement
                    entry(PluginMessagePacket.class, PluginMessagePacket.SERIALIZER),
                    // Code statement
                    entry(DisconnectPacket.class, DisconnectPacket.SERIALIZER),
                    // Code statement
                    entry(FinishConfigurationPacket.class, FinishConfigurationPacket.SERIALIZER),
                    // Code statement
                    entry(KeepAlivePacket.class, KeepAlivePacket.SERIALIZER),
                    // Code statement
                    entry(PingPacket.class, PingPacket.SERIALIZER),
                    // Code statement
                    entry(ResetChatPacket.class, ResetChatPacket.SERIALIZER),
                    // Code statement
                    entry(RegistryDataPacket.class, RegistryDataPacket.SERIALIZER),
                    // Code statement
                    entry(ResourcePackPopPacket.class, ResourcePackPopPacket.SERIALIZER),
                    // Code statement
                    entry(ResourcePackPushPacket.class, ResourcePackPushPacket.SERIALIZER),
                    // Code statement
                    entry(CookieStorePacket.class, CookieStorePacket.SERIALIZER),
                    // Code statement
                    entry(TransferPacket.class, TransferPacket.SERIALIZER),
                    // Code statement
                    entry(UpdateEnabledFeaturesPacket.class, UpdateEnabledFeaturesPacket.SERIALIZER),
                    // Code statement
                    entry(TagsPacket.class, TagsPacket.SERIALIZER),
                    // Code statement
                    entry(SelectKnownPacksPacket.class, SelectKnownPacksPacket.SERIALIZER),
                    // Code statement
                    entry(CustomReportDetailsPacket.class, CustomReportDetailsPacket.SERIALIZER),
                    // Code statement
                    entry(ServerLinksPacket.class, ServerLinksPacket.SERIALIZER),
                    // Code statement
                    entry(ClearDialogPacket.class, ClearDialogPacket.SERIALIZER),
                    // Code statement
                    entry(ShowDialogPacket.class, ShowDialogPacket.INLINE_SERIALIZER),
                    // Code statement
                    entry(CodeOfConductPacket.class, CodeOfConductPacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.CONFIGURATION;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class ServerPlay extends Server<ServerPacket.Play> {
        // Start of a method/block
        public ServerPlay() {
            // Access to the current/parent object
            super(
                    // Code statement
                    entry(BundlePacket.class, BundlePacket.SERIALIZER),
                    // Code statement
                    entry(SpawnEntityPacket.class, SpawnEntityPacket.SERIALIZER),
                    // Code statement
                    entry(EntityAnimationPacket.class, EntityAnimationPacket.SERIALIZER),
                    // Code statement
                    entry(StatisticsPacket.class, StatisticsPacket.SERIALIZER),
                    // Code statement
                    entry(AcknowledgeBlockChangePacket.class, AcknowledgeBlockChangePacket.SERIALIZER),
                    // Code statement
                    entry(BlockBreakAnimationPacket.class, BlockBreakAnimationPacket.SERIALIZER),
                    // Code statement
                    entry(BlockEntityDataPacket.class, BlockEntityDataPacket.SERIALIZER),
                    // Code statement
                    entry(BlockActionPacket.class, BlockActionPacket.SERIALIZER),
                    // Code statement
                    entry(BlockChangePacket.class, BlockChangePacket.SERIALIZER),
                    // Code statement
                    entry(BossBarPacket.class, BossBarPacket.SERIALIZER),
                    // Code statement
                    entry(ServerDifficultyPacket.class, ServerDifficultyPacket.SERIALIZER),
                    // Code statement
                    entry(ChunkBatchFinishedPacket.class, ChunkBatchFinishedPacket.SERIALIZER),
                    // Code statement
                    entry(ChunkBatchStartPacket.class, ChunkBatchStartPacket.SERIALIZER),
                    // Code statement
                    entry(ChunkBiomesPacket.class, ChunkBiomesPacket.SERIALIZER),
                    // Code statement
                    entry(ClearTitlesPacket.class, ClearTitlesPacket.SERIALIZER),
                    // Code statement
                    entry(TabCompletePacket.class, TabCompletePacket.SERIALIZER),
                    // Code statement
                    entry(DeclareCommandsPacket.class, DeclareCommandsPacket.SERIALIZER),
                    // Code statement
                    entry(CloseWindowPacket.class, CloseWindowPacket.SERIALIZER),
                    // Code statement
                    entry(WindowItemsPacket.class, WindowItemsPacket.SERIALIZER),
                    // Code statement
                    entry(WindowPropertyPacket.class, WindowPropertyPacket.SERIALIZER),
                    // Code statement
                    entry(SetSlotPacket.class, SetSlotPacket.SERIALIZER),
                    // Code statement
                    entry(CookieRequestPacket.class, CookieRequestPacket.SERIALIZER),
                    // Code statement
                    entry(SetCooldownPacket.class, SetCooldownPacket.SERIALIZER),
                    // Code statement
                    entry(CustomChatCompletionPacket.class, CustomChatCompletionPacket.SERIALIZER),
                    // Code statement
                    entry(PluginMessagePacket.class, PluginMessagePacket.SERIALIZER),
                    // Code statement
                    entry(DamageEventPacket.class, DamageEventPacket.SERIALIZER),
                    // Code statement
                    entry(DebugBlockValuePacket.class, DebugBlockValuePacket.SERIALIZER),
                    // Code statement
                    entry(DebugChunkValuePacket.class, DebugChunkValuePacket.SERIALIZER),
                    // Code statement
                    entry(DebugEntityValuePacket.class, DebugEntityValuePacket.SERIALIZER),
                    // Code statement
                    entry(DebugEventPacket.class, DebugEventPacket.SERIALIZER),
                    // Code statement
                    entry(DebugSamplePacket.class, DebugSamplePacket.SERIALIZER),
                    // Code statement
                    entry(DeleteChatPacket.class, DeleteChatPacket.SERIALIZER),
                    // Code statement
                    entry(DisconnectPacket.class, DisconnectPacket.SERIALIZER),
                    // Code statement
                    entry(DisguisedChatPacket.class, DisguisedChatPacket.SERIALIZER),
                    // Code statement
                    entry(EntityStatusPacket.class, EntityStatusPacket.SERIALIZER),
                    // Code statement
                    entry(EntityPositionSyncPacket.class, EntityPositionSyncPacket.SERIALIZER),
                    // Code statement
                    entry(ExplosionPacket.class, ExplosionPacket.SERIALIZER),
                    // Code statement
                    entry(UnloadChunkPacket.class, UnloadChunkPacket.SERIALIZER),
                    // Code statement
                    entry(ChangeGameStatePacket.class, ChangeGameStatePacket.SERIALIZER),
                    // Code statement
                    entry(GameRuleValuesPacket.class, GameRuleValuesPacket.SERIALIZER),
                    // Code statement
                    entry(GameTestHighlightPosPacket.class, GameTestHighlightPosPacket.SERIALIZER),
                    // Code statement
                    entry(OpenHorseWindowPacket.class, OpenHorseWindowPacket.SERIALIZER),
                    // Code statement
                    entry(HitAnimationPacket.class, HitAnimationPacket.SERIALIZER),
                    // Code statement
                    entry(InitializeWorldBorderPacket.class, InitializeWorldBorderPacket.SERIALIZER),
                    // Code statement
                    entry(KeepAlivePacket.class, KeepAlivePacket.SERIALIZER),
                    // Code statement
                    entry(ChunkDataPacket.class, ChunkDataPacket.SERIALIZER),
                    // Code statement
                    entry(WorldEventPacket.class, WorldEventPacket.SERIALIZER),
                    // Code statement
                    entry(ParticlePacket.class, ParticlePacket.SERIALIZER),
                    // Code statement
                    entry(UpdateLightPacket.class, UpdateLightPacket.SERIALIZER),
                    // Code statement
                    entry(JoinGamePacket.class, JoinGamePacket.SERIALIZER),
                    // Code statement
                    entry(LowDiskSpaceWarningPacket.class, LowDiskSpaceWarningPacket.SERIALIZER),
                    // Code statement
                    entry(MapDataPacket.class, MapDataPacket.SERIALIZER),
                    // Code statement
                    entry(TradeListPacket.class, TradeListPacket.SERIALIZER),
                    // Code statement
                    entry(EntityPositionPacket.class, EntityPositionPacket.SERIALIZER),
                    // Code statement
                    entry(EntityPositionAndRotationPacket.class, EntityPositionAndRotationPacket.SERIALIZER),
                    // Code statement
                    entry(MoveMinecartPacket.class, MoveMinecartPacket.SERIALIZER),
                    // Code statement
                    entry(EntityRotationPacket.class, EntityRotationPacket.SERIALIZER),
                    // Code statement
                    entry(VehicleMovePacket.class, VehicleMovePacket.SERIALIZER),
                    // Code statement
                    entry(OpenBookPacket.class, OpenBookPacket.SERIALIZER),
                    // Code statement
                    entry(OpenWindowPacket.class, OpenWindowPacket.SERIALIZER),
                    // Code statement
                    entry(OpenSignEditorPacket.class, OpenSignEditorPacket.SERIALIZER),
                    // Code statement
                    entry(PingPacket.class, PingPacket.SERIALIZER),
                    // Code statement
                    entry(PingResponsePacket.class, PingResponsePacket.SERIALIZER),
                    // Code statement
                    entry(PlaceGhostRecipePacket.class, PlaceGhostRecipePacket.SERIALIZER),
                    // Code statement
                    entry(PlayerAbilitiesPacket.class, PlayerAbilitiesPacket.SERIALIZER),
                    // Code statement
                    entry(PlayerChatMessagePacket.class, PlayerChatMessagePacket.SERIALIZER),
                    // Code statement
                    entry(EndCombatEventPacket.class, EndCombatEventPacket.SERIALIZER),
                    // Code statement
                    entry(EnterCombatEventPacket.class, EnterCombatEventPacket.SERIALIZER),
                    // Code statement
                    entry(DeathCombatEventPacket.class, DeathCombatEventPacket.SERIALIZER),
                    // Code statement
                    entry(PlayerInfoRemovePacket.class, PlayerInfoRemovePacket.SERIALIZER),
                    // Code statement
                    entry(PlayerInfoUpdatePacket.class, PlayerInfoUpdatePacket.SERIALIZER),
                    // Code statement
                    entry(FacePlayerPacket.class, FacePlayerPacket.SERIALIZER),
                    // Code statement
                    entry(PlayerPositionAndLookPacket.class, PlayerPositionAndLookPacket.SERIALIZER),
                    // Code statement
                    entry(PlayerRotationPacket.class, PlayerRotationPacket.SERIALIZER),
                    // Code statement
                    entry(RecipeBookAddPacket.class, RecipeBookAddPacket.SERIALIZER),
                    // Code statement
                    entry(RecipeBookRemovePacket.class, RecipeBookRemovePacket.SERIALIZER),
                    // Code statement
                    entry(RecipeBookSettingsPacket.class, RecipeBookSettingsPacket.SERIALIZER),
                    // Code statement
                    entry(DestroyEntitiesPacket.class, DestroyEntitiesPacket.SERIALIZER),
                    // Code statement
                    entry(RemoveEntityEffectPacket.class, RemoveEntityEffectPacket.SERIALIZER),
                    // Code statement
                    entry(ResetScorePacket.class, ResetScorePacket.SERIALIZER),
                    // Code statement
                    entry(ResourcePackPopPacket.class, ResourcePackPopPacket.SERIALIZER),
                    // Code statement
                    entry(ResourcePackPushPacket.class, ResourcePackPushPacket.SERIALIZER),
                    // Code statement
                    entry(RespawnPacket.class, RespawnPacket.SERIALIZER),
                    // Code statement
                    entry(EntityHeadLookPacket.class, EntityHeadLookPacket.SERIALIZER),
                    // Code statement
                    entry(MultiBlockChangePacket.class, MultiBlockChangePacket.SERIALIZER),
                    // Code statement
                    entry(SelectAdvancementTabPacket.class, SelectAdvancementTabPacket.SERIALIZER),
                    // Code statement
                    entry(ServerDataPacket.class, ServerDataPacket.SERIALIZER),
                    // Code statement
                    entry(ActionBarPacket.class, ActionBarPacket.SERIALIZER),
                    // Code statement
                    entry(WorldBorderCenterPacket.class, WorldBorderCenterPacket.SERIALIZER),
                    // Code statement
                    entry(WorldBorderLerpSizePacket.class, WorldBorderLerpSizePacket.SERIALIZER),
                    // Code statement
                    entry(WorldBorderSizePacket.class, WorldBorderSizePacket.SERIALIZER),
                    // Code statement
                    entry(WorldBorderWarningDelayPacket.class, WorldBorderWarningDelayPacket.SERIALIZER),
                    // Code statement
                    entry(WorldBorderWarningReachPacket.class, WorldBorderWarningReachPacket.SERIALIZER),
                    // Code statement
                    entry(CameraPacket.class, CameraPacket.SERIALIZER),
                    // Code statement
                    entry(UpdateViewPositionPacket.class, UpdateViewPositionPacket.SERIALIZER),
                    // Code statement
                    entry(UpdateViewDistancePacket.class, UpdateViewDistancePacket.SERIALIZER),
                    // Code statement
                    entry(SetCursorItemPacket.class, SetCursorItemPacket.SERIALIZER),
                    // Code statement
                    entry(SpawnPositionPacket.class, SpawnPositionPacket.SERIALIZER),
                    // Code statement
                    entry(DisplayScoreboardPacket.class, DisplayScoreboardPacket.SERIALIZER),
                    // Code statement
                    entry(EntityMetaDataPacket.class, EntityMetaDataPacket.SERIALIZER),
                    // Code statement
                    entry(AttachEntityPacket.class, AttachEntityPacket.SERIALIZER),
                    // Code statement
                    entry(EntityVelocityPacket.class, EntityVelocityPacket.SERIALIZER),
                    // Code statement
                    entry(EntityEquipmentPacket.class, EntityEquipmentPacket.SERIALIZER),
                    // Code statement
                    entry(SetExperiencePacket.class, SetExperiencePacket.SERIALIZER),
                    // Code statement
                    entry(UpdateHealthPacket.class, UpdateHealthPacket.SERIALIZER),
                    // Code statement
                    entry(HeldItemChangePacket.class, HeldItemChangePacket.SERIALIZER),
                    // Code statement
                    entry(ScoreboardObjectivePacket.class, ScoreboardObjectivePacket.SERIALIZER),
                    // Code statement
                    entry(SetPassengersPacket.class, SetPassengersPacket.SERIALIZER),
                    // Code statement
                    entry(SetPlayerInventorySlotPacket.class, SetPlayerInventorySlotPacket.SERIALIZER),
                    // Code statement
                    entry(TeamsPacket.class, TeamsPacket.SERIALIZER),
                    // Code statement
                    entry(UpdateScorePacket.class, UpdateScorePacket.SERIALIZER),
                    // Code statement
                    entry(UpdateSimulationDistancePacket.class, UpdateSimulationDistancePacket.SERIALIZER),
                    // Code statement
                    entry(SetTitleSubTitlePacket.class, SetTitleSubTitlePacket.SERIALIZER),
                    // Code statement
                    entry(SetTimePacket.class, SetTimePacket.SERIALIZER),
                    // Code statement
                    entry(SetTitleTextPacket.class, SetTitleTextPacket.SERIALIZER),
                    // Code statement
                    entry(SetTitleTimePacket.class, SetTitleTimePacket.SERIALIZER),
                    // Code statement
                    entry(EntitySoundEffectPacket.class, EntitySoundEffectPacket.SERIALIZER),
                    // Code statement
                    entry(SoundEffectPacket.class, SoundEffectPacket.SERIALIZER),
                    // Code statement
                    entry(StartConfigurationPacket.class, StartConfigurationPacket.SERIALIZER),
                    // Code statement
                    entry(StopSoundPacket.class, StopSoundPacket.SERIALIZER),
                    // Code statement
                    entry(CookieStorePacket.class, CookieStorePacket.SERIALIZER),
                    // Code statement
                    entry(SystemChatPacket.class, SystemChatPacket.SERIALIZER),
                    // Code statement
                    entry(PlayerListHeaderAndFooterPacket.class, PlayerListHeaderAndFooterPacket.SERIALIZER),
                    // Code statement
                    entry(NbtQueryResponsePacket.class, NbtQueryResponsePacket.SERIALIZER),
                    // Code statement
                    entry(CollectItemPacket.class, CollectItemPacket.SERIALIZER),
                    // Code statement
                    entry(EntityTeleportPacket.class, EntityTeleportPacket.SERIALIZER),
                    // Code statement
                    entry(TestInstanceBlockStatus.class, TestInstanceBlockStatus.SERIALIZER),
                    // Code statement
                    entry(SetTickStatePacket.class, SetTickStatePacket.SERIALIZER),
                    // Code statement
                    entry(TickStepPacket.class, TickStepPacket.SERIALIZER),
                    // Code statement
                    entry(TransferPacket.class, TransferPacket.SERIALIZER),
                    // Code statement
                    entry(AdvancementsPacket.class, AdvancementsPacket.SERIALIZER),
                    // Code statement
                    entry(EntityAttributesPacket.class, EntityAttributesPacket.SERIALIZER),
                    // Code statement
                    entry(EntityEffectPacket.class, EntityEffectPacket.SERIALIZER),
                    // Code statement
                    entry(DeclareRecipesPacket.class, DeclareRecipesPacket.SERIALIZER),
                    // Code statement
                    entry(TagsPacket.class, TagsPacket.SERIALIZER),
                    // Code statement
                    entry(ProjectilePowerPacket.class, ProjectilePowerPacket.SERIALIZER),
                    // Code statement
                    entry(CustomReportDetailsPacket.class, CustomReportDetailsPacket.SERIALIZER),
                    // Code statement
                    entry(ServerLinksPacket.class, ServerLinksPacket.SERIALIZER),
                    // Code statement
                    entry(TrackedWaypointPacket.class, TrackedWaypointPacket.SERIALIZER),
                    // Code statement
                    entry(ClearDialogPacket.class, ClearDialogPacket.SERIALIZER),
                    // Code statement
                    entry(ShowDialogPacket.class, ShowDialogPacket.SERIALIZER)
            // End of a block/expression
            );
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ConnectionState state() {
            // Returns a value to the caller
            return ConnectionState.PLAY;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @SuppressWarnings({"unchecked", "rawtypes"})
    // Type declaration (class/interface/enum/record)
    abstract sealed class PacketRegistryTemplate<T> implements PacketRegistry<T> {
        // Code statement
        private final PacketInfo<? extends T>[] suppliers;
        // Assigns a value
        private final ClassValue<PacketInfo<T>> packetIds = new ClassValue<>() {
            // Annotation for the following element
            @Override
            // Start of a method/block
            protected PacketInfo<T> computeValue(Class<?> type) {
                // Loop: repeats a block
                for (PacketInfo<? extends T> info : suppliers) {
                    // Branch: checks a condition
                    if (info != null && info.packetClass == type) {
                        // Returns a value to the caller
                        return (PacketInfo<T>) info;
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Throws an exception
                throw new IllegalStateException("Packet type " + type + " cannot be sent in state " + side().name() + "_" + state().name() + "!");
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Annotation for the following element
        @SafeVarargs PacketRegistryTemplate(Entry<? extends T>... suppliers) {
            // Assigns a value
            PacketInfo<? extends T>[] packetInfos = new PacketInfo[suppliers.length];
            // Loop: repeats a block
            for (int i = 0; i < suppliers.length; i++) {
                // Assigns a value
                final Entry<? extends T> entry = suppliers[i];
                // Branch: checks a condition
                if (entry == null) continue;
                // Calls a method
                packetInfos[i] = new PacketInfo(entry.type, i, entry.reader);
            // End of a block/expression
            }
            // Access to the current/parent object
            this.suppliers = packetInfos;
        // End of a block/expression
        }

        // Start of a method/block
        public @UnknownNullability T create(int packetId, NetworkBuffer reader) {
            // Calls a method
            final PacketInfo<T> info = packetInfo(packetId);
            // Assigns a value
            final NetworkBuffer.Type<T> supplier = info.serializer;
            // Calls a method
            final T packet = supplier.read(reader);
            // Branch: checks a condition
            if (packet == null) {
                // Throws an exception
                throw new IllegalStateException("Packet " + info.packetClass + " failed to read!");
            // End of a block/expression
            }
            // Returns a value to the caller
            return packet;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public PacketInfo<T> packetInfo(Class<?> packetClass) {
            // Returns a value to the caller
            return packetIds.get(packetClass);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public PacketInfo<T> packetInfo(int packetId) {
            // Code statement
            final PacketInfo<T> info;
            // Branch: checks a condition
            if (packetId < 0 || packetId >= suppliers.length || (info = (PacketInfo<T>) suppliers[packetId]) == null) {
                // Throws an exception
                throw new IllegalStateException("Packet id 0x" + Integer.toHexString(packetId) + " isn't registered!");
            // End of a block/expression
            }
            // Returns a value to the caller
            return info;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public @Unmodifiable List<PacketInfo<? extends T>> packets() {
            // Returns a value to the caller
            return List.of(suppliers);
        // End of a block/expression
        }


        // Type declaration (class/interface/enum/record)
        record Entry<T>(Class<T> type, NetworkBuffer.Type<T> reader) {
        // End of a block/expression
        }

        // Annotation for the following element
        @SuppressWarnings({"unchecked", "rawtypes"})
        // Start of a method/block
        static <T> Entry<T> entry(Class<T> type, NetworkBuffer.Type<T> reader) {
            // Returns a value to the caller
            return new Entry<>((Class) type, reader);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    enum ConnectionSide {
        // Code statement
        CLIENT,
        // Code statement
        SERVER
    // End of a block/expression
    }
// End of a block/expression
}
