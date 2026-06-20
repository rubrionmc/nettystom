// Déclaration du paquet de ce fichier
package net.minestom.server.extras.query.response;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * An enum of default query keys.
 */
// Déclaration de type (classe/interface/enum/record)
public enum QueryKey {
    // Instruction de code
    HOSTNAME(() -> "A Minestom Server"),
    // Instruction de code
    GAME_TYPE(() -> "SMP"),
    // Instruction de code
    GAME_ID("game_id", () -> "MINECRAFT"),
    // Instruction de code
    VERSION(() -> MinecraftServer.VERSION_NAME),
    // Instruction de code
    PLUGINS(FullQueryResponse::generatePluginsValue),
    // Instruction de code
    MAP(() -> "world"),
    // Instruction de code
    NUM_PLAYERS("numplayers", () -> String.valueOf(MinecraftServer.getConnectionManager().getOnlinePlayerCount())),
    // Instruction de code
    MAX_PLAYERS("maxplayers", () -> String.valueOf(MinecraftServer.getConnectionManager().getOnlinePlayerCount() + 1)),
    // Instruction de code
    HOST_PORT("hostport", () -> String.valueOf(MinecraftServer.getServer().getPort())),
    // Appelle une méthode
    HOST_IP("hostip", () -> Objects.requireNonNullElse(MinecraftServer.getServer().getAddress(), "localhost"));

    // Appelle une méthode
    static QueryKey[] VALUES = QueryKey.values();

    // Instruction de code
    private final String key;
    // Instruction de code
    private final Supplier<String> value;

    // Début d'une méthode/d'un bloc
    QueryKey(Supplier<String> value) {
        // Appelle une méthode
        this(null, value);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    QueryKey(@Nullable String key, Supplier<String> value) {
        // Accès à l'objet courant/parent
        this.key = Objects.requireNonNullElse(key, this.name().toLowerCase(Locale.ROOT).replace('_', ' '));
        // Accès à l'objet courant/parent
        this.value = value;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the key of this query key.
     *
     * @return the key
     */
    // Début d'une méthode/d'un bloc
    public String getKey() {
        // Renvoie une valeur à l'appelant
        return this.key;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the value of this query key.
     *
     * @return the value
     */
    // Début d'une méthode/d'un bloc
    public String getValue() {
        // Renvoie une valeur à l'appelant
        return this.value.get();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
