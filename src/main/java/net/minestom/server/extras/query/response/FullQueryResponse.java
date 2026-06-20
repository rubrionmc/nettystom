// Déclaration du paquet de ce fichier
package net.minestom.server.extras.query.response;

// Import d'une classe nécessaire
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Import d'une classe nécessaire
import java.util.*;

/**
 * A full query response containing a dynamic set of responses.
 */
// Déclaration de type (classe/interface/enum/record)
public class FullQueryResponse {
    // Appelle une méthode
    private static final PlainTextComponentSerializer PLAIN = PlainTextComponentSerializer.plainText();
    // Affecte une valeur
    private static final byte[] PADDING_10 = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00},
            // Affecte une valeur
            PADDING_11 = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    // Instruction de code
    private Map<String, String> kv;
    // Instruction de code
    private List<String> players;

    /**
     * Creates a new full query response with default values set.
     */
    // Début d'une méthode/d'un bloc
    public FullQueryResponse() {
        // Accès à l'objet courant/parent
        this.kv = new HashMap<>();

        // populate defaults
        // Boucle : répète un bloc
        for (QueryKey key : QueryKey.VALUES) {
            // Accès à l'objet courant/parent
            this.kv.put(key.getKey(), key.getValue());
        // Fin d'un bloc/d'une expression
        }

        // Accès à l'objet courant/parent
        this.players = MinecraftServer.getConnectionManager().getOnlinePlayers()
                // Instruction de code
                .stream()
                // Instruction de code
                .map(player -> PLAIN.serialize(player.getName()))
                // Appelle une méthode
                .toList();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Puts a key-value mapping into the response.
     *
     * @param key   the key
     * @param value the value
     */
    // Début d'une méthode/d'un bloc
    public void put(QueryKey key, String value) {
        // Accès à l'objet courant/parent
        this.put(key.getKey(), value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Puts a key-value mapping into the response.
     *
     * @param key   the key
     * @param value the value
     */
    // Début d'une méthode/d'un bloc
    public void put(String key, String value) {
        // Accès à l'objet courant/parent
        this.kv.put(key, value);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the map containing the key-value mappings.
     *
     * @return the map
     */
    // Début d'une méthode/d'un bloc
    public Map<String, String> getKeyValuesMap() {
        // Renvoie une valeur à l'appelant
        return this.kv;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the map containing the key-value mappings.
     *
     * @param map the map
     */
    // Début d'une méthode/d'un bloc
    public void setKeyValuesMap(Map<String, String> map) {
        // Accès à l'objet courant/parent
        this.kv = Objects.requireNonNull(map, "map");
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds some players to the response.
     *
     * @param players the players
     */
    // Début d'une méthode/d'un bloc
    public void addPlayers(String ... players) {
        // Appelle une méthode
        Collections.addAll(this.players, players);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds some players to the response.
     *
     * @param players the players
     */
    // Début d'une méthode/d'un bloc
    public void addPlayers(Collection<String> players) {
        // Accès à l'objet courant/parent
        this.players.addAll(players);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the list of players.
     *
     * @return the list
     */
    // Début d'une méthode/d'un bloc
    public List<String> getPlayers() {
        // Renvoie une valeur à l'appelant
        return this.players;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the list of players.
     *
     * @param players the players
     */
    // Début d'une méthode/d'un bloc
    public void setPlayers(List<String> players) {
        // Accès à l'objet courant/parent
        this.players = Objects.requireNonNull(players, "players");
    // Fin d'un bloc/d'une expression
    }

    /**
     * Generates the default plugins value. That being the server name and version followed
     * by the name and version for each extension.
     *
     * @return the string result
     */
    // Début d'une méthode/d'un bloc
    public static String generatePluginsValue() {
        // Affecte une valeur
        StringBuilder builder = new StringBuilder(MinecraftServer.getBrandName())
                // Instruction de code
                .append(' ')
                // Appelle une méthode
                .append(MinecraftServer.VERSION_NAME);

        // Renvoie une valeur à l'appelant
        return builder.toString();
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<FullQueryResponse> SERIALIZER = new NetworkBuffer.Type<>() {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, FullQueryResponse value) {
            // Appelle une méthode
            buffer.write(NetworkBuffer.RAW_BYTES, PADDING_11);
            // key-values
            // Boucle : répète un bloc
            for (var entry : value.kv.entrySet()) {
                // Appelle une méthode
                buffer.write(NetworkBuffer.STRING_TERMINATED, entry.getKey());
                // Appelle une méthode
                buffer.write(NetworkBuffer.STRING_TERMINATED, entry.getValue());
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            buffer.write(NetworkBuffer.STRING_TERMINATED, "");
            // Appelle une méthode
            buffer.write(NetworkBuffer.RAW_BYTES, PADDING_10);
            // players
            // Boucle : répète un bloc
            for (String player : value.players) {
                // Appelle une méthode
                buffer.write(NetworkBuffer.STRING_TERMINATED, player);
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            buffer.write(NetworkBuffer.STRING_TERMINATED, "");
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public FullQueryResponse read(NetworkBuffer buffer) {
            // Lève une exception
            throw new UnsupportedOperationException("FullQueryResponse is write-only");
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    };
// Fin d'un bloc/d'une expression
}
