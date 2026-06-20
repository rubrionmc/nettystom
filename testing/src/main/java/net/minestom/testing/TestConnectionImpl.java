// Déclaration du paquet de ce fichier
package net.minestom.testing;

// Import d'une classe nécessaire
import net.kyori.adventure.translation.GlobalTranslator;
// Import d'une classe nécessaire
import net.minestom.server.ServerProcess;
// Import d'une classe nécessaire
import net.minestom.server.adventure.MinestomAdventure;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
// Import d'une classe nécessaire
import net.minestom.server.instance.Chunk;
// Import d'une classe nécessaire
import net.minestom.server.instance.Instance;
// Import d'une classe nécessaire
import net.minestom.server.network.ConnectionState;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.SendablePacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.player.GameProfile;
// Import d'une classe nécessaire
import net.minestom.server.network.player.PlayerConnection;

// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.concurrent.CompletableFuture;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArrayList;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Déclaration de type (classe/interface/enum/record)
final class TestConnectionImpl implements TestConnection {
    // Instruction de code
    private final ServerProcess process;
    // Instruction de code
    private final GameProfile gameProfile;
    // Appelle une méthode
    private final PlayerConnectionImpl playerConnection = new PlayerConnectionImpl();

    // Appelle une méthode
    private final AtomicBoolean connected = new AtomicBoolean(false);

    // Affecte une valeur
    private final List<IncomingCollector<ServerPacket>> incomingTrackers = new CopyOnWriteArrayList<>();

    // Début d'une méthode/d'un bloc
    TestConnectionImpl(Env env, GameProfile gameProfile) {
        // Accès à l'objet courant/parent
        this.process = env.process();
        // Accès à l'objet courant/parent
        this.gameProfile = gameProfile;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Player connect(Instance instance, Pos pos) {
        // Embranchement : vérifie une condition
        if (!connected.compareAndSet(false, true)) {
            // Lève une exception
            throw new IllegalStateException("Already connected");
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        var player = process.connection().createPlayer(playerConnection, gameProfile);
        // Début d'une méthode/d'un bloc
        player.eventNode().addListener(AsyncPlayerConfigurationEvent.class, event -> {
            // Appelle une méthode
            event.setSpawningInstance(instance);
            // Appelle une méthode
            event.getPlayer().setRespawnPoint(pos);
        // Fin d'un bloc/d'une expression
        });

        // Force the player through the entirety of the login process manually
        // Affecte une valeur
        CompletableFuture<Player> future = new CompletableFuture<>();
        // Début d'une méthode/d'un bloc
        Thread.startVirtualThread(() -> {
            // `isFirstConfig` is set to false in order to not block the thread
            // waiting for known packs.
            // The consequence is that registry packets cannot be listened to.
            // Appelle une méthode
            process.connection().doConfiguration(player, false);
            // Appelle une méthode
            process.connection().transitionConfigToPlay(player);
            // Appelle une méthode
            future.complete(player);
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        future.join();
        // Appelle une méthode
        playerConnection.setClientState(ConnectionState.PLAY);
        // Appelle une méthode
        playerConnection.setServerState(ConnectionState.PLAY);
        // Appelle une méthode
        process.connection().updateWaitingPlayers();
        // Renvoie une valeur à l'appelant
        return player;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T extends ServerPacket> Collector<T> trackIncoming(Class<T> type) {
        // Affecte une valeur
        var tracker = new IncomingCollector<>(type);
        // Accès à l'objet courant/parent
        this.incomingTrackers.add(IncomingCollector.class.cast(tracker));
        // Renvoie une valeur à l'appelant
        return tracker;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class PlayerConnectionImpl extends PlayerConnection {
        // Affecte une valeur
        private boolean online = true;

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void sendPacket(SendablePacket packet) {
            // Appelle une méthode
            final var serverPacket = this.extractPacket(packet);
            // Boucle : répète un bloc
            for (var tracker : incomingTrackers) {
                // Embranchement : vérifie une condition
                if (tracker.type.isAssignableFrom(serverPacket.getClass())) tracker.packets.add(serverPacket);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private ServerPacket extractPacket(final SendablePacket packet) {
            // Embranchement : vérifie une condition
            if (!(packet instanceof ServerPacket serverPacket))
                // Renvoie une valeur à l'appelant
                return SendablePacket.extractServerPacket(getServerState(), packet);

            // Appelle une méthode
            final Player player = getPlayer();
            // Embranchement : vérifie une condition
            if (player == null) return serverPacket;

            // Embranchement : vérifie une condition
            if (MinestomAdventure.AUTOMATIC_COMPONENT_TRANSLATION && serverPacket instanceof ServerPacket.ComponentHolding) {
                // Affecte une valeur
                serverPacket = ((ServerPacket.ComponentHolding) serverPacket).copyWithOperator(component ->
                        // Appelle une méthode
                        GlobalTranslator.render(component, Objects.requireNonNullElseGet(player.getLocale(), MinestomAdventure::getDefaultLocale)));
            // Fin d'un bloc/d'une expression
            }

            // Renvoie une valeur à l'appelant
            return serverPacket;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public SocketAddress getRemoteAddress() {
            // Renvoie une valeur à l'appelant
            return new InetSocketAddress("localhost", 25565);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean isOnline() {
            // Renvoie une valeur à l'appelant
            return online;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void disconnect() {
            // Affecte une valeur
            online = false;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    final class IncomingCollector<T extends ServerPacket> implements Collector<T> {
        // Instruction de code
        private final Class<T> type;
        // Affecte une valeur
        private final List<T> packets = new CopyOnWriteArrayList<>();

        // Début d'une méthode/d'un bloc
        public IncomingCollector(Class<T> type) {
            // Accès à l'objet courant/parent
            this.type = type;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<T> collect() {
            // Appelle une méthode
            incomingTrackers.remove(this);
            // Renvoie une valeur à l'appelant
            return List.copyOf(packets);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class TestPlayerImpl extends Player {
        // Début d'une méthode/d'un bloc
        public TestPlayerImpl(PlayerConnection playerConnection, GameProfile gameProfile) {
            // Accès à l'objet courant/parent
            super(playerConnection, gameProfile);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void sendChunk(Chunk chunk) {
            // Send immediately
            // Appelle une méthode
            sendPacket(chunk.getFullDataPacket());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
