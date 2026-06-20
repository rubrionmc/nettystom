// Package declaration for this file
package net.minestom.server;

// Import of a required class
import org.jetbrains.annotations.Contract;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Contains server settings/flags to be set with system properties.
 *
 * <p>Some flags (labeled at the bottom) are experimental. They may be removed without notice, and may have issues.</p>
 */
// Type declaration (class/interface/enum/record)
public final class ServerFlag {

    // Server Behavior
    // Calls a method
    public static final boolean SHUTDOWN_ON_SIGNAL = booleanProperty("minestom.shutdown-on-signal", true);
    // Calls a method
    public static final int SERVER_TICKS_PER_SECOND = intProperty("minestom.tps", 20);
    // Calls a method
    public static final int SERVER_MAX_TICK_CATCH_UP = intProperty("minestom.max-tick-catch-up", 5);
    // Assigns a value
    public static final int CHUNK_VIEW_DISTANCE = intProperty("minestom.chunk-view-distance", 8); // Base chunk view distance of instances and client settings
    // Calls a method
    public static final int ENTITY_VIEW_DISTANCE = intProperty("minestom.entity-view-distance", 5);
    // Calls a method
    public static final int ENTITY_SYNCHRONIZATION_TICKS = intProperty("minestom.entity-synchronization-ticks", 20);
    // Calls a method
    public static final int DISPATCHER_THREADS = intProperty("minestom.dispatcher-threads", 1);
    // Calls a method
    public static final int SEND_LIGHT_AFTER_BLOCK_PLACEMENT_DELAY = intProperty("minestom.send-light-after-block-placement-delay", 100);
    // Assigns a value
    public static final long LOGIN_PLUGIN_MESSAGE_TIMEOUT = longProperty("minestom.login-plugin-message-timeout", 5_000); // 5s
    // Assigns a value
    public static final long KNOWN_PACKS_RESPONSE_TIMEOUT = longProperty("minestom.known-packs-response-timeout", 5 * 60_000); // 5m
    // Calls a method
    public static final boolean ACCEPT_TRANSFERS = booleanProperty("minestom.accept-transfers", false);
    // Calls a method
    public static final boolean AUTOMATIC_COMPONENT_TRANSLATION = booleanProperty("minestom.automatic-component-translation", false);

    // Network rate limiting
    // Calls a method
    public static final int PLAYER_PACKET_PER_TICK = intProperty("minestom.packet-per-tick", 50);
    // Calls a method
    public static final int PLAYER_PACKET_QUEUE_SIZE = intProperty("minestom.packet-queue-size", 1000);
    // Calls a method
    public static final long KEEP_ALIVE_DELAY = longProperty("minestom.keep-alive-delay", 10_000);
    // Calls a method
    public static final long KEEP_ALIVE_KICK = longProperty("minestom.keep-alive-kick", 15_000);
    // Calls a method
    public static final int PLAYER_CHUNK_UPDATE_LIMITER_HISTORY_SIZE = intProperty("minestom.player.chunk-update-limiter-history-size", 5, 0, Integer.MAX_VALUE);

    // Network buffers
    // Assigns a value
    public static final int MAX_PACKET_SIZE = intProperty("minestom.max-packet-size", 2_097_151); // 3 bytes var-int
    // Calls a method
    public static final int MAX_PACKET_SIZE_PRE_AUTH = intProperty("minestom.max-packet-size-pre-auth", 8_192);
    // Calls a method
    public static final int SOCKET_SEND_BUFFER_SIZE = intProperty("minestom.send-buffer-size", 262_143);
    // Calls a method
    public static final int SOCKET_RECEIVE_BUFFER_SIZE = intProperty("minestom.receive-buffer-size", 32_767);
    // Calls a method
    public static final boolean SOCKET_NO_DELAY = booleanProperty("minestom.tcp-no-delay", true);
    // Calls a method
    public static final int SOCKET_TIMEOUT = intProperty("minestom.socket-timeout", 15_000);
    // Calls a method
    public static final int POOLED_BUFFER_SIZE = intProperty("minestom.pooled-buffer-size", 16_383);

    // Chunk update
    // Calls a method
    public static final float MIN_CHUNKS_PER_TICK = floatProperty("minestom.chunk-queue.min-per-tick", 0.01f);
    // Calls a method
    public static final float MAX_CHUNKS_PER_TICK = floatProperty("minestom.chunk-queue.max-per-tick", 64.0f);
    // Calls a method
    public static final float CHUNKS_PER_TICK_MULTIPLIER = floatProperty("minestom.chunk-queue.multiplier", 1f);

    // Packet sending optimizations
    // Calls a method
    public static final boolean GROUPED_PACKET = booleanProperty("minestom.grouped-packet", true);
    // Calls a method
    public static final boolean CACHED_PACKET = booleanProperty("minestom.cached-packet", true);
    // Calls a method
    public static final boolean VIEWABLE_PACKET = booleanProperty("minestom.viewable-packet", true);

    // Tags
    // Calls a method
    public static final boolean TAG_HANDLER_CACHE_ENABLED = booleanProperty("minestom.tag-handler-cache", true);
    // Calls a method
    public static final boolean SERIALIZE_EMPTY_COMPOUND = booleanProperty("minestom.serialization.serialize-empty-nbt-compound", false);

    // Online Mode
    // Calls a method
    public static final String AUTH_URL = stringProperty("minestom.auth.url", "https://sessionserver.mojang.com/session/minecraft/hasJoined");
    // Calls a method
    public static final boolean AUTH_PREVENT_PROXY_CONNECTIONS = booleanProperty("minestom.auth.prevent-proxy-connections", false);

    // World
    // Calls a method
    public static final int WORLD_BORDER_SIZE = intProperty("minestom.world-border-size", 29999984);

    // Maps
    // Calls a method
    public static final String MAP_RGB_MAPPING = stringProperty("minestom.map.rgbmapping", "lazy");
    // Assigns a value
    public static final int MAP_RGB_REDUCTION = intProperty("minestom.map.rgbreduction", -1); // Only used if rgb mapping is "approximate"

    // Entities
    // Calls a method
    public static final boolean ENFORCE_INTERACTION_LIMIT = booleanProperty("minestom.enforce-entity-interaction-range", true);

    // Experimental/Unstable
    // Calls a method
    public static final boolean REGISTRY_UNSAFE_OPS = booleanProperty("minestom.registry.unsafe-ops");
    // Calls a method
    public static final boolean EVENT_NODE_ALLOW_MULTIPLE_PARENTS = booleanProperty("minestom.event.multiple-parents");
    // Assigns a value
    public static final boolean FASTER_SOCKET_WRITES = booleanProperty("minestom.new-socket-write-lock"); // TODO: promote to default
    // Calls a method
    public static final boolean ACQUIRABLE_STRICT = booleanProperty("minestom.acquirable-strict", false);
    // Assigns a value
    public static final boolean UNSAFE_COLLECTIONS = booleanProperty("minestom.unsafe-collections", false); // Likely to be removed in the future
    // Calls a method
    public static final boolean TEMPLATE_COMPILER = booleanProperty("minestom.template-compiler", false);

    // Calls a method
    public static boolean INSIDE_TEST = booleanProperty("minestom.inside-test", false);

    // Code statement
    private ServerFlag() {}

    // Start of a method/block
    private static boolean booleanProperty(String name) {
        // Returns a value to the caller
        return Boolean.getBoolean(name);
    // End of a block/expression
    }

    // Start of a method/block
    private static boolean booleanProperty(String name, boolean defaultValue) {
        // Assigns a value
        boolean result = defaultValue;
        // Exception handling
        try {
            // Calls a method
            final String value = System.getProperty(name);
            // Branch: checks a condition
            if (value != null) result = Boolean.parseBoolean(value);
        // Start of a method/block
        } catch (IllegalArgumentException | NullPointerException ignored) {
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }

    // Annotation for the following element
    @Contract("_, null -> null; _, !null -> !null")
    // Start of a method/block
    private static String stringProperty(String name, @Nullable String defaultValue) {
        // Returns a value to the caller
        return System.getProperty(name, defaultValue);
    // End of a block/expression
    }

    // Start of a method/block
    private static String stringProperty(String name) {
        // Returns a value to the caller
        return System.getProperty(name);
    // End of a block/expression
    }

    // Start of a method/block
    private static int intProperty(String name, int defaultValue, int minValue, int maxValue) {
        // Calls a method
        int value = Integer.getInteger(name, defaultValue);
        // Branch: checks a condition
        if (value < minValue || value > maxValue) {
            // Throws an exception
            throw new IllegalArgumentException(String.format(
                    // Code statement
                    "Property '%s' value must be in range [%d..%d] but was %d",
                    // Code statement
                    name, minValue, maxValue, value
            // Code statement
            ));
        // End of a block/expression
        }
        // Returns a value to the caller
        return value;
    // End of a block/expression
    }

    // Start of a method/block
    private static int intProperty(String name, int defaultValue) {
        // Returns a value to the caller
        return intProperty(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    // End of a block/expression
    }

    // Start of a method/block
    private static long longProperty(String name, long defaultValue) {
        // Returns a value to the caller
        return Long.getLong(name, defaultValue);
    // End of a block/expression
    }

    // Start of a method/block
    private static float floatProperty(String name, float defaultValue) {
        // Assigns a value
        float result = defaultValue;
        // Exception handling
        try {
            // Calls a method
            final String value = System.getProperty(name);
            // Branch: checks a condition
            if (value != null) result = Float.parseFloat(value);
        // Start of a method/block
        } catch (IllegalArgumentException | NullPointerException ignored) {
        // End of a block/expression
        }
        // Returns a value to the caller
        return result;
    // End of a block/expression
    }
// End of a block/expression
}
