// Package declaration for this file
package net.minestom.server.monitoring;

// Import of a required class
import jdk.jfr.Category;
// Import of a required class
import jdk.jfr.Description;
// Import of a required class
import jdk.jfr.Label;
// Import of a required class
import jdk.jfr.Name;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.UUID;

/**
 * JFR events for monitoring Minestom server activities.
 */
// Annotation for the following element
@ApiStatus.Internal
// Annotation for the following element
@SuppressWarnings("ALL")
// Type declaration (class/interface/enum/record)
public final class EventsJFR {
    // Calls a method
    public static final boolean JFR_AVAILABLE = jfrAvailable();

    // Start of a method/block
    private static boolean jfrAvailable() {
        // Exception handling
        try {
            // Calls a method
            Class<?> vmClass = Class.forName("org.graalvm.nativeimage.VMRuntime");
            // Returns a value to the caller
            return false;
        // Start of a method/block
        } catch (ClassNotFoundException e) {
        // End of a block/expression
        }
        // Exception handling
        try {
            // Calls a method
            Class.forName("jdk.jfr.Event");
            // Returns a value to the caller
            return true;
        // Start of a method/block
        } catch (Throwable t) {
            // Returns a value to the caller
            return false;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Assigns a value
    public static final String SERVER_PING = "minestom.ServerPing";
    // Assigns a value
    public static final String SERVER_TICK = "minestom.ServerTickTime";

    // Assigns a value
    public static final String CHUNK_GENERATION = "minestom.ChunkGeneration";
    // Assigns a value
    public static final String CHUNK_LOADING = "minestom.ChunkLoading";

    // Assigns a value
    public static final String INSTANCE_JOIN = "minestom.InstanceJoin";
    // Assigns a value
    public static final String INSTANCE_LEAVE = "minestom.InstanceLeave";

    // Assigns a value
    public static final String PLAYER_JOIN = "minestom.PlayerJoin";
    // Assigns a value
    public static final String PLAYER_LEAVE = "minestom.PlayerLeave";
    // Assigns a value
    public static final String PLAYER_COMMAND = "minestom.PlayerCommand";
    // Assigns a value
    public static final String PLAYER_CHAT = "minestom.PlayerChat";

    // Start of a method/block
    public static EventMarker newServerPing(String remoteAddress) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new ServerPing(remoteAddress) : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newServerTick() {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new ServerTick() : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newChunkGeneration(UUID instance, int chunkX, int chunkZ) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new ChunkGeneration(instance.toString(), chunkX, chunkZ) : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newChunkLoading(UUID instance, Class loader, int chunkX, int chunkZ) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new ChunkLoading(instance.toString(), loader, chunkX, chunkZ) : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newInstanceJoin(UUID entity, UUID instance) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new InstanceJoin(entity.toString(), instance.toString()) : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newInstanceLeave(UUID entity, UUID instance) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new InstanceLeave(entity.toString(), instance.toString()) : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newPlayerJoin(UUID player) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new PlayerJoin(player.toString()) : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newPlayerLeave(UUID player) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new PlayerLeave(player.toString()) : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newPlayerCommand(UUID player, String command) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new PlayerCommand(player.toString(), command) : NO_OP;
    // End of a block/expression
    }

    // Start of a method/block
    public static EventMarker newPlayerChat(UUID player, String message) {
        // Returns a value to the caller
        return JFR_AVAILABLE ? new PlayerChat(player.toString(), message) : NO_OP;
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(SERVER_PING)
    // Annotation for the following element
    @Label("Server Ping")
    // Annotation for the following element
    @Category({"Minestom", "Server"})
    // Annotation for the following element
    @Description("A server ping (status query) was received")
    // Start of a method/block
    private static final class ServerPing extends JFREventWrapper {
        // Annotation for the following element
        @Label("Remote Address")
        // Code statement
        String remoteAddress;

        // Start of a method/block
        private ServerPing(String remoteAddress) {
            // Access to the current/parent object
            this.remoteAddress = remoteAddress;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(SERVER_TICK)
    // Annotation for the following element
    @Label("Server Tick")
    // Annotation for the following element
    @Category({"Minestom", "Server"})
    // Annotation for the following element
    @Description("Time spent ticking the server once")
    // Start of a method/block
    private static final class ServerTick extends JFREventWrapper {
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(CHUNK_GENERATION)
    // Annotation for the following element
    @Label("Chunk Generation")
    // Annotation for the following element
    @Category({"Minestom", "World"})
    // Annotation for the following element
    @Description("Chunk generation from instances' Generator")
    // Start of a method/block
    private static final class ChunkGeneration extends JFREventWrapper {
        // Annotation for the following element
        @Label("Instance UUID")
        // Code statement
        String instance;
        // Annotation for the following element
        @Label("Chunk X")
        // Code statement
        int chunkX;
        // Annotation for the following element
        @Label("Chunk Z")
        // Code statement
        int chunkZ;

        // Start of a method/block
        private ChunkGeneration(String instance, int chunkX, int chunkZ) {
            // Access to the current/parent object
            this.instance = instance;
            // Access to the current/parent object
            this.chunkX = chunkX;
            // Access to the current/parent object
            this.chunkZ = chunkZ;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(CHUNK_LOADING)
    // Annotation for the following element
    @Label("Chunk Loading")
    // Annotation for the following element
    @Category({"Minestom", "World"})
    // Annotation for the following element
    @Description("Chunk loading from the instances' ChunkLoader")
    // Start of a method/block
    private static final class ChunkLoading extends JFREventWrapper {
        // Annotation for the following element
        @Label("Instance UUID")
        // Code statement
        String instance;
        // Annotation for the following element
        @Label("Loader Class")
        // Code statement
        Class loader;
        // Annotation for the following element
        @Label("Chunk X")
        // Code statement
        int chunkX;
        // Annotation for the following element
        @Label("Chunk Z")
        // Code statement
        int chunkZ;

        // Start of a method/block
        private ChunkLoading(String instance, Class loader, int chunkX, int chunkZ) {
            // Access to the current/parent object
            this.instance = instance;
            // Access to the current/parent object
            this.loader = loader;
            // Access to the current/parent object
            this.chunkX = chunkX;
            // Access to the current/parent object
            this.chunkZ = chunkZ;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(INSTANCE_JOIN)
    // Annotation for the following element
    @Label("Instance Join")
    // Annotation for the following element
    @Category({"Minestom", "Instance"})
    // Annotation for the following element
    @Description("An Entity has joined an instance")
    // Start of a method/block
    private static final class InstanceJoin extends JFREventWrapper {
        // Annotation for the following element
        @Label("Entity UUID")
        // Code statement
        String entity;
        // Annotation for the following element
        @Label("Instance UUID")
        // Code statement
        String instance;

        // Start of a method/block
        private InstanceJoin(String entity, String instance) {
            // Access to the current/parent object
            this.entity = entity;
            // Access to the current/parent object
            this.instance = instance;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(INSTANCE_LEAVE)
    // Annotation for the following element
    @Label("Instance Leave")
    // Annotation for the following element
    @Category({"Minestom", "Instance"})
    // Annotation for the following element
    @Description("An Entity has left an instance")
    // Start of a method/block
    private static final class InstanceLeave extends JFREventWrapper {
        // Annotation for the following element
        @Label("Entity UUID")
        // Code statement
        String entity;
        // Annotation for the following element
        @Label("Instance UUID")
        // Code statement
        String instance;

        // Start of a method/block
        private InstanceLeave(String entity, String instance) {
            // Access to the current/parent object
            this.entity = entity;
            // Access to the current/parent object
            this.instance = instance;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(PLAYER_JOIN)
    // Annotation for the following element
    @Label("Player Join")
    // Annotation for the following element
    @Category({"Minestom", "Player"})
    // Annotation for the following element
    @Description("A player joined the server")
    // Start of a method/block
    private static final class PlayerJoin extends JFREventWrapper {
        // Annotation for the following element
        @Label("Player UUID")
        // Code statement
        String player;

        // Start of a method/block
        private PlayerJoin(String player) {
            // Access to the current/parent object
            this.player = player;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(PLAYER_LEAVE)
    // Annotation for the following element
    @Label("Player Leave")
    // Annotation for the following element
    @Category({"Minestom", "Player"})
    // Annotation for the following element
    @Description("A player left the server")
    // Start of a method/block
    private static final class PlayerLeave extends JFREventWrapper {
        // Annotation for the following element
        @Label("Player UUID")
        // Code statement
        String player;

        // Start of a method/block
        private PlayerLeave(String player) {
            // Access to the current/parent object
            this.player = player;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(PLAYER_COMMAND)
    // Annotation for the following element
    @Label("Player Command")
    // Annotation for the following element
    @Category({"Minestom", "Player"})
    // Annotation for the following element
    @Description("A player executed a command")
    // Start of a method/block
    private static final class PlayerCommand extends JFREventWrapper {
        // Annotation for the following element
        @Label("Player UUID")
        // Code statement
        String player;
        // Annotation for the following element
        @Label("Command")
        // Code statement
        String command;

        // Start of a method/block
        private PlayerCommand(String player, String command) {
            // Access to the current/parent object
            this.player = player;
            // Access to the current/parent object
            this.command = command;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Name(PLAYER_CHAT)
    // Annotation for the following element
    @Label("Player Chat")
    // Annotation for the following element
    @Category({"Minestom", "Player"})
    // Annotation for the following element
    @Description("A player sent a chat message")
    // Start of a method/block
    private static final class PlayerChat extends JFREventWrapper {
        // Annotation for the following element
        @Label("Player UUID")
        // Code statement
        String player;
        // Annotation for the following element
        @Label("Message")
        // Code statement
        String message;

        // Start of a method/block
        private PlayerChat(String player, String message) {
            // Access to the current/parent object
            this.player = player;
            // Access to the current/parent object
            this.message = message;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public interface EventMarker {
        // Start of a method/block
        default void begin() {
        // End of a block/expression
        }

        // Start of a method/block
        default void end() {
        // End of a block/expression
        }

        // Start of a method/block
        default void commit() {
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    private static class JFREventWrapper extends jdk.jfr.Event implements EventMarker {
    // End of a block/expression
    }

    // Calls a method
    private static final EventMarker NO_OP = new NoOpEvent();

    // Type declaration (class/interface/enum/record)
    private static class NoOpEvent implements EventMarker {
        // Annotation for the following element
        @Override
        // Start of a method/block
        public void commit() {
            // do nothing
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
