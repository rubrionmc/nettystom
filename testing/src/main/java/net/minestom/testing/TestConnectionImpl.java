// Package declaration for this file
package net.minestom.testing;

// Import of a required class
import net.kyori.adventure.translation.GlobalTranslator;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.ServerProcess;
// Import of a required class
import net.minestom.server.adventure.MinestomAdventure;
// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
// Import of a required class
import net.minestom.server.instance.Chunk;
// Import of a required class
import net.minestom.server.instance.Instance;
// Import of a required class
import net.minestom.server.network.ConnectionState;
// Import of a required class
import net.minestom.server.network.packet.server.SendablePacket;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.network.player.GameProfile;
// Import of a required class
import net.minestom.server.network.player.PlayerConnection;

// Import of a required class
import java.net.InetSocketAddress;
// Import of a required class
import java.net.SocketAddress;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.concurrent.CompletableFuture;
// Import of a required class
import java.util.concurrent.CopyOnWriteArrayList;
// Import of a required class
import java.util.concurrent.atomic.AtomicBoolean;

// Type declaration (class/interface/enum/record)
final class TestConnectionImpl implements TestConnection {
    // Code statement
    private final ServerProcess process;
    // Code statement
    private final GameProfile gameProfile;
    // Calls a method
    private final PlayerConnectionImpl playerConnection = new PlayerConnectionImpl();

    // Calls a method
    private final AtomicBoolean connected = new AtomicBoolean(false);

    // Calls a method
    private final List<IncomingCollector<ServerPacket>> incomingTrackers = new CopyOnWriteArrayList<>();

    // Start of a method/block
    TestConnectionImpl(Env env, GameProfile gameProfile) {
        // Access to the current/parent object
        this.process = env.process();
        // Access to the current/parent object
        this.gameProfile = gameProfile;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Player connect(Instance instance, Pos pos) {
        // Branch: checks a condition
        if (!connected.compareAndSet(false, true)) {
            // Throws an exception
            throw new IllegalStateException("Already connected");
        // End of a block/expression
        }

        // Calls a method
        var player = process.connection().createPlayer(playerConnection, gameProfile);
        // Start of a method/block
        player.eventNode().addListener(AsyncPlayerConfigurationEvent.class, event -> {
            // Calls a method
            event.setSpawningInstance(instance);
            // Calls a method
            event.getPlayer().setRespawnPoint(pos);
        // End of a block/expression
        });

        // Force the player through the entirety of the login process manually
        // Calls a method
        CompletableFuture<Player> future = new CompletableFuture<>();
        // Start of a method/block
        Thread.startVirtualThread(() -> {
            // `isFirstConfig` is set to false in order to not block the thread
            // waiting for known packs.
            // The consequence is that registry packets cannot be listened to.
            // Calls a method
            process.connection().doConfiguration(player, false);
            // Calls a method
            process.connection().transitionConfigToPlay(player);
            // Calls a method
            future.complete(player);
        // End of a block/expression
        });
        // Calls a method
        future.join();
        // Calls a method
        playerConnection.setClientState(ConnectionState.PLAY);
        // Calls a method
        playerConnection.setServerState(ConnectionState.PLAY);
        // Calls a method
        process.connection().updateWaitingPlayers();
        // Returns a value to the caller
        return player;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T extends ServerPacket> Collector<T> trackIncoming(Class<T> type) {
        // Calls a method
        var tracker = new IncomingCollector<>(type);
        // Access to the current/parent object
        this.incomingTrackers.add(IncomingCollector.class.cast(tracker));
        // Returns a value to the caller
        return tracker;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class PlayerConnectionImpl extends PlayerConnection {
        // Assigns a value
        private boolean online = true;

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void sendPacket(SendablePacket packet) {
            // Calls a method
            final var serverPacket = this.extractPacket(packet);
            // Loop: repeats a block
            for (var tracker : incomingTrackers) {
                // Branch: checks a condition
                if (tracker.type.isAssignableFrom(serverPacket.getClass())) tracker.packets.add(serverPacket);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Start of a method/block
        private ServerPacket extractPacket(final SendablePacket packet) {
            // Branch: checks a condition
            if (!(packet instanceof ServerPacket serverPacket))
                // Returns a value to the caller
                return SendablePacket.extractServerPacket(getServerState(), packet);

            // Calls a method
            final Player player = getPlayer();
            // Branch: checks a condition
            if (player == null) return serverPacket;

            // Branch: checks a condition
            if (ServerFlag.AUTOMATIC_COMPONENT_TRANSLATION && serverPacket instanceof ServerPacket.ComponentHolding) {
                // Assigns a value
                serverPacket = ((ServerPacket.ComponentHolding) serverPacket).copyWithOperator(component ->
                        // Calls a method
                        GlobalTranslator.render(component, Objects.requireNonNullElseGet(player.getLocale(), MinestomAdventure::getDefaultLocale)));
            // End of a block/expression
            }

            // Returns a value to the caller
            return serverPacket;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public SocketAddress getRemoteAddress() {
            // Returns a value to the caller
            return new InetSocketAddress("localhost", 25565);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public boolean isOnline() {
            // Returns a value to the caller
            return online;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void disconnect() {
            // Assigns a value
            online = false;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    final class IncomingCollector<T extends ServerPacket> implements Collector<T> {
        // Code statement
        private final Class<T> type;
        // Calls a method
        private final List<T> packets = new CopyOnWriteArrayList<>();

        // Start of a method/block
        public IncomingCollector(Class<T> type) {
            // Access to the current/parent object
            this.type = type;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public List<T> collect() {
            // Calls a method
            incomingTrackers.remove(this);
            // Returns a value to the caller
            return List.copyOf(packets);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    static final class TestPlayerImpl extends Player {
        // Start of a method/block
        public TestPlayerImpl(PlayerConnection playerConnection, GameProfile gameProfile) {
            // Access to the current/parent object
            super(playerConnection, gameProfile);
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public void sendChunk(Chunk chunk) {
            // Send immediately
            // Calls a method
            sendPacket(chunk.getFullDataPacket());
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
