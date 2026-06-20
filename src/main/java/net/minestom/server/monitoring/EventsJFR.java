// Déclaration du paquet de ce fichier
package net.minestom.server.monitoring;

// Import d'une classe nécessaire
import jdk.jfr.Category;
// Import d'une classe nécessaire
import jdk.jfr.Description;
// Import d'une classe nécessaire
import jdk.jfr.Label;
// Import d'une classe nécessaire
import jdk.jfr.Name;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.UUID;

/**
 * JFR events for monitoring Minestom server activities.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Annotation pour l'élément suivant
@SuppressWarnings("ALL")
// Déclaration de type (classe/interface/enum/record)
public final class EventsJFR {
    // Appelle une méthode
    public static final boolean JFR_AVAILABLE = jfrAvailable();

    // Début d'une méthode/d'un bloc
    private static boolean jfrAvailable() {
        // Gestion des exceptions
        try {
            // Appelle une méthode
            Class<?> vmClass = Class.forName("org.graalvm.nativeimage.VMRuntime");
            // Renvoie une valeur à l'appelant
            return false;
        // Début d'une méthode/d'un bloc
        } catch (ClassNotFoundException e) {
        // Fin d'un bloc/d'une expression
        }
        // Gestion des exceptions
        try {
            // Appelle une méthode
            Class.forName("jdk.jfr.Event");
            // Renvoie une valeur à l'appelant
            return true;
        // Début d'une méthode/d'un bloc
        } catch (Throwable t) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final String SERVER_PING = "minestom.ServerPing";
    // Affecte une valeur
    public static final String SERVER_TICK = "minestom.ServerTickTime";

    // Affecte une valeur
    public static final String CHUNK_GENERATION = "minestom.ChunkGeneration";
    // Affecte une valeur
    public static final String CHUNK_LOADING = "minestom.ChunkLoading";

    // Affecte une valeur
    public static final String INSTANCE_JOIN = "minestom.InstanceJoin";
    // Affecte une valeur
    public static final String INSTANCE_LEAVE = "minestom.InstanceLeave";

    // Affecte une valeur
    public static final String PLAYER_JOIN = "minestom.PlayerJoin";
    // Affecte une valeur
    public static final String PLAYER_LEAVE = "minestom.PlayerLeave";
    // Affecte une valeur
    public static final String PLAYER_COMMAND = "minestom.PlayerCommand";
    // Affecte une valeur
    public static final String PLAYER_CHAT = "minestom.PlayerChat";

    // Début d'une méthode/d'un bloc
    public static EventMarker newServerPing(String remoteAddress) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new ServerPing(remoteAddress) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newServerTick() {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new ServerTick() : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newChunkGeneration(UUID instance, int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new ChunkGeneration(instance.toString(), chunkX, chunkZ) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newChunkLoading(UUID instance, Class loader, int chunkX, int chunkZ) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new ChunkLoading(instance.toString(), loader, chunkX, chunkZ) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newInstanceJoin(UUID entity, UUID instance) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new InstanceJoin(entity.toString(), instance.toString()) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newInstanceLeave(UUID entity, UUID instance) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new InstanceLeave(entity.toString(), instance.toString()) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newPlayerJoin(UUID player) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new PlayerJoin(player.toString()) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newPlayerLeave(UUID player) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new PlayerLeave(player.toString()) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newPlayerCommand(UUID player, String command) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new PlayerCommand(player.toString(), command) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static EventMarker newPlayerChat(UUID player, String message) {
        // Renvoie une valeur à l'appelant
        return JFR_AVAILABLE ? new PlayerChat(player.toString(), message) : NO_OP;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(SERVER_PING)
    // Annotation pour l'élément suivant
    @Label("Server Ping")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "Server"})
    // Annotation pour l'élément suivant
    @Description("A server ping (status query) was received")
    // Début d'une méthode/d'un bloc
    private static final class ServerPing extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Remote Address")
        // Instruction de code
        String remoteAddress;

        // Début d'une méthode/d'un bloc
        private ServerPing(String remoteAddress) {
            // Accès à l'objet courant/parent
            this.remoteAddress = remoteAddress;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(SERVER_TICK)
    // Annotation pour l'élément suivant
    @Label("Server Tick")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "Server"})
    // Annotation pour l'élément suivant
    @Description("Time spent ticking the server once")
    // Début d'une méthode/d'un bloc
    private static final class ServerTick extends JFREventWrapper {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(CHUNK_GENERATION)
    // Annotation pour l'élément suivant
    @Label("Chunk Generation")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "World"})
    // Annotation pour l'élément suivant
    @Description("Chunk generation from instances' Generator")
    // Début d'une méthode/d'un bloc
    private static final class ChunkGeneration extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Instance UUID")
        // Instruction de code
        String instance;
        // Annotation pour l'élément suivant
        @Label("Chunk X")
        // Instruction de code
        int chunkX;
        // Annotation pour l'élément suivant
        @Label("Chunk Z")
        // Instruction de code
        int chunkZ;

        // Début d'une méthode/d'un bloc
        private ChunkGeneration(String instance, int chunkX, int chunkZ) {
            // Accès à l'objet courant/parent
            this.instance = instance;
            // Accès à l'objet courant/parent
            this.chunkX = chunkX;
            // Accès à l'objet courant/parent
            this.chunkZ = chunkZ;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(CHUNK_LOADING)
    // Annotation pour l'élément suivant
    @Label("Chunk Loading")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "World"})
    // Annotation pour l'élément suivant
    @Description("Chunk loading from the instances' ChunkLoader")
    // Début d'une méthode/d'un bloc
    private static final class ChunkLoading extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Instance UUID")
        // Instruction de code
        String instance;
        // Annotation pour l'élément suivant
        @Label("Loader Class")
        // Instruction de code
        Class loader;
        // Annotation pour l'élément suivant
        @Label("Chunk X")
        // Instruction de code
        int chunkX;
        // Annotation pour l'élément suivant
        @Label("Chunk Z")
        // Instruction de code
        int chunkZ;

        // Début d'une méthode/d'un bloc
        private ChunkLoading(String instance, Class loader, int chunkX, int chunkZ) {
            // Accès à l'objet courant/parent
            this.instance = instance;
            // Accès à l'objet courant/parent
            this.loader = loader;
            // Accès à l'objet courant/parent
            this.chunkX = chunkX;
            // Accès à l'objet courant/parent
            this.chunkZ = chunkZ;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(INSTANCE_JOIN)
    // Annotation pour l'élément suivant
    @Label("Instance Join")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "Instance"})
    // Annotation pour l'élément suivant
    @Description("An Entity has joined an instance")
    // Début d'une méthode/d'un bloc
    private static final class InstanceJoin extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Entity UUID")
        // Instruction de code
        String entity;
        // Annotation pour l'élément suivant
        @Label("Instance UUID")
        // Instruction de code
        String instance;

        // Début d'une méthode/d'un bloc
        private InstanceJoin(String entity, String instance) {
            // Accès à l'objet courant/parent
            this.entity = entity;
            // Accès à l'objet courant/parent
            this.instance = instance;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(INSTANCE_LEAVE)
    // Annotation pour l'élément suivant
    @Label("Instance Leave")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "Instance"})
    // Annotation pour l'élément suivant
    @Description("An Entity has left an instance")
    // Début d'une méthode/d'un bloc
    private static final class InstanceLeave extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Entity UUID")
        // Instruction de code
        String entity;
        // Annotation pour l'élément suivant
        @Label("Instance UUID")
        // Instruction de code
        String instance;

        // Début d'une méthode/d'un bloc
        private InstanceLeave(String entity, String instance) {
            // Accès à l'objet courant/parent
            this.entity = entity;
            // Accès à l'objet courant/parent
            this.instance = instance;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(PLAYER_JOIN)
    // Annotation pour l'élément suivant
    @Label("Player Join")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "Player"})
    // Annotation pour l'élément suivant
    @Description("A player joined the server")
    // Début d'une méthode/d'un bloc
    private static final class PlayerJoin extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Player UUID")
        // Instruction de code
        String player;

        // Début d'une méthode/d'un bloc
        private PlayerJoin(String player) {
            // Accès à l'objet courant/parent
            this.player = player;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(PLAYER_LEAVE)
    // Annotation pour l'élément suivant
    @Label("Player Leave")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "Player"})
    // Annotation pour l'élément suivant
    @Description("A player left the server")
    // Début d'une méthode/d'un bloc
    private static final class PlayerLeave extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Player UUID")
        // Instruction de code
        String player;

        // Début d'une méthode/d'un bloc
        private PlayerLeave(String player) {
            // Accès à l'objet courant/parent
            this.player = player;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(PLAYER_COMMAND)
    // Annotation pour l'élément suivant
    @Label("Player Command")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "Player"})
    // Annotation pour l'élément suivant
    @Description("A player executed a command")
    // Début d'une méthode/d'un bloc
    private static final class PlayerCommand extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Player UUID")
        // Instruction de code
        String player;
        // Annotation pour l'élément suivant
        @Label("Command")
        // Instruction de code
        String command;

        // Début d'une méthode/d'un bloc
        private PlayerCommand(String player, String command) {
            // Accès à l'objet courant/parent
            this.player = player;
            // Accès à l'objet courant/parent
            this.command = command;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Name(PLAYER_CHAT)
    // Annotation pour l'élément suivant
    @Label("Player Chat")
    // Annotation pour l'élément suivant
    @Category({"Minestom", "Player"})
    // Annotation pour l'élément suivant
    @Description("A player sent a chat message")
    // Début d'une méthode/d'un bloc
    private static final class PlayerChat extends JFREventWrapper {
        // Annotation pour l'élément suivant
        @Label("Player UUID")
        // Instruction de code
        String player;
        // Annotation pour l'élément suivant
        @Label("Message")
        // Instruction de code
        String message;

        // Début d'une méthode/d'un bloc
        private PlayerChat(String player, String message) {
            // Accès à l'objet courant/parent
            this.player = player;
            // Accès à l'objet courant/parent
            this.message = message;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public interface EventMarker {
        // Début d'une méthode/d'un bloc
        default void begin() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default void end() {
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        default void commit() {
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    private static class JFREventWrapper extends jdk.jfr.Event implements EventMarker {
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private static final EventMarker NO_OP = new NoOpEvent();

    // Déclaration de type (classe/interface/enum/record)
    private static class NoOpEvent implements EventMarker {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void commit() {
            // do nothing
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
