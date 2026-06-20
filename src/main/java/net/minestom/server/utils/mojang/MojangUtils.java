// Déclaration du paquet de ce fichier
package net.minestom.server.utils.mojang;

// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import com.google.gson.JsonParser;
// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import net.minestom.server.utils.url.URLUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Blocking;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.net.InetAddress;
// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.net.SocketAddress;
// Import d'une classe nécessaire
import java.net.URLEncoder;
// Import d'une classe nécessaire
import java.nio.charset.StandardCharsets;
// Import d'une classe nécessaire
import java.util.UUID;

/**
 * Utils class using mojang API.
 */
// Déclaration de type (classe/interface/enum/record)
public final class MojangUtils {
    // Affecte une valeur
    private static final String FROM_UUID_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";
    // Affecte une valeur
    private static final String FROM_USERNAME_URL = "https://api.minecraftservices.com/minecraft/profile/lookup/name/%s";

    // Auth
    // Appelle une méthode
    private static final String BASE_AUTH_URL = ServerFlag.AUTH_URL.concat("?username=%s&serverId=%s");
    // Appelle une méthode
    private static final String PREVENT_PROXY_CONNECTIONS_AUTH_URL = BASE_AUTH_URL.concat("&ip=%s");

    /**
     * Gets a player's UUID from their username
     *
     * @param username The players username
     * @return The {@link UUID}
     * @throws IOException with text detailing the exception
     */
    // Annotation pour l'élément suivant
    @Blocking
    // Début d'une méthode/d'un bloc
    public static UUID getUUID(String username) throws IOException {
        // Thanks stackoverflow: https://stackoverflow.com/a/19399768/13247146
        // Renvoie une valeur à l'appelant
        return UUID.fromString(
                // Instruction de code
                retrieve(String.format(FROM_USERNAME_URL, username)).get("id")
                        // Instruction de code
                        .getAsString()
                        // Instruction de code
                        .replaceFirst(
                                // Instruction de code
                                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                                // Instruction de code
                                "$1-$2-$3-$4-$5"
                        // Fin d'un bloc/d'une expression
                        )
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a player's username from their UUID
     *
     * @param playerUUID The {@link UUID} of the player
     * @return The player's username
     * @throws IOException with text detailing the exception
     */
    // Annotation pour l'élément suivant
    @Blocking
    // Début d'une méthode/d'un bloc
    public static String getUsername(UUID playerUUID) throws IOException {
        // Renvoie une valeur à l'appelant
        return retrieve(String.format(FROM_UUID_URL, playerUUID)).get("name").getAsString();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link JsonObject} with the response from the mojang API
     *
     * @param uuid The UUID as a {@link UUID}
     * @return The {@link JsonObject} or {@code null} if the mojang API is down or the UUID is invalid
     */
    // Annotation pour l'élément suivant
    @Blocking
    // Début d'une méthode/d'un bloc
    public static @Nullable JsonObject fromUuid(UUID uuid) {
        // Renvoie une valeur à l'appelant
        return fromUuid(uuid.toString());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link JsonObject} with the response from the mojang API
     *
     * @param uuid The UUID as a {@link String}
     * @return The {@link JsonObject} or {@code null} if the mojang API is down or the UUID is invalid
     */
    // Annotation pour l'élément suivant
    @Blocking
    // Début d'une méthode/d'un bloc
    public static @Nullable JsonObject fromUuid(String uuid) {
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return retrieve(String.format(FROM_UUID_URL, uuid));
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a {@link JsonObject} with the response from the mojang API
     *
     * @param username The username as a {@link String}
     * @return The {@link JsonObject} or {@code null} if the mojang API is down or the username is invalid
     */
    // Annotation pour l'élément suivant
    @Blocking
    // Début d'une méthode/d'un bloc
    public static @Nullable JsonObject fromUsername(String username) {
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return retrieve(String.format(FROM_USERNAME_URL, username));
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Blocking
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    public static JsonObject authenticateSession(String loginUsername, String serverId, @Nullable SocketAddress userSocket) throws IOException {
        // Appelle une méthode
        final String username = URLEncoder.encode(loginUsername, StandardCharsets.UTF_8);

        // Instruction de code
        final String url;
        // Embranchement : vérifie une condition
        if (ServerFlag.AUTH_PREVENT_PROXY_CONNECTIONS
                // Instruction de code
                && userSocket instanceof InetSocketAddress inetSocketAddress
                // Instruction de code
                && inetSocketAddress.getAddress() instanceof InetAddress address
        // Début d'une méthode/d'un bloc
        ) {
            // Appelle une méthode
            url = String.format(PREVENT_PROXY_CONNECTIONS_AUTH_URL, username, serverId, address.getHostAddress());
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            url = String.format(BASE_AUTH_URL, username, serverId);
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return retrieve(url);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the JsonObject from a URL, expects a mojang player URL so the errors might not make sense if it is not
     *
     * @param url The url to retrieve
     * @return The {@link JsonObject} of the result
     * @throws IOException with the text detailing the exception
     */
    // Début d'une méthode/d'un bloc
    private static JsonObject retrieve(String url) throws IOException {
        // Retrieve from the rate-limited Mojang API
        // Appelle une méthode
        final String response = URLUtils.getText(url);
        // If our response is "", that means the url did not get a proper object from the url
        // So the username or UUID was invalid, and therefore we return null
        // Embranchement : vérifie une condition
        if (response.isEmpty()) throw new IOException("The Mojang API is down");
        // Appelle une méthode
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        // Embranchement : vérifie une condition
        if (jsonObject.has("errorMessage")) {
            // Lève une exception
            throw new IOException(jsonObject.get("errorMessage").getAsString());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return jsonObject;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
