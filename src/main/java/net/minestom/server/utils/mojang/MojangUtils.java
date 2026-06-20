// Package declaration for this file
package net.minestom.server.utils.mojang;

// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import com.google.gson.JsonParser;
// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import net.minestom.server.utils.url.URLUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Blocking;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.net.InetAddress;
// Import of a required class
import java.net.InetSocketAddress;
// Import of a required class
import java.net.SocketAddress;
// Import of a required class
import java.net.URLEncoder;
// Import of a required class
import java.nio.charset.StandardCharsets;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.regex.Pattern;

/**
 * Utils class using mojang API.
 */
// Type declaration (class/interface/enum/record)
public final class MojangUtils {
    // Assigns a value
    private static final String FROM_UUID_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";
    // Assigns a value
    private static final String FROM_USERNAME_URL = "https://api.minecraftservices.com/minecraft/profile/lookup/name/%s";

    // Auth
    // Calls a method
    private static final String BASE_AUTH_URL = ServerFlag.AUTH_URL.concat("?username=%s&serverId=%s");
    // Calls a method
    private static final String PREVENT_PROXY_CONNECTIONS_AUTH_URL = BASE_AUTH_URL.concat("&ip=%s");

    // Calls a method
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]{3,16}");

    /**
     * Gets a player's UUID from their username
     *
     * @param username The players username
     * @return The {@link UUID}
     * @throws IOException with text detailing the exception
     */
    // Annotation for the following element
    @Blocking
    // Start of a method/block
    public static UUID getUUID(String username) throws IOException {
        // Thanks stackoverflow: https://stackoverflow.com/a/19399768/13247146
        // Returns a value to the caller
        return UUID.fromString(
                // Code statement
                retrieve(String.format(FROM_USERNAME_URL, validateUsername(username))).get("id")
                        // Code statement
                        .getAsString()
                        // Code statement
                        .replaceFirst(
                                // Code statement
                                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                                // Code statement
                                "$1-$2-$3-$4-$5"
                        // End of a block/expression
                        )
        // End of a block/expression
        );
    // End of a block/expression
    }

    /**
     * Gets a player's username from their UUID
     *
     * @param playerUUID The {@link UUID} of the player
     * @return The player's username
     * @throws IOException with text detailing the exception
     */
    // Annotation for the following element
    @Blocking
    // Start of a method/block
    public static String getUsername(UUID playerUUID) throws IOException {
        // Returns a value to the caller
        return retrieve(String.format(FROM_UUID_URL, playerUUID)).get("name").getAsString();
    // End of a block/expression
    }

    /**
     * Gets a {@link JsonObject} with the response from the mojang API
     *
     * @param uuid The UUID as a {@link UUID}
     * @return The {@link JsonObject} or {@code null} if the mojang API is down or the UUID is invalid
     */
    // Annotation for the following element
    @Blocking
    // Start of a method/block
    public static @Nullable JsonObject fromUuid(UUID uuid) {
        // Exception handling
        try {
            // Returns a value to the caller
            return retrieve(String.format(FROM_UUID_URL, uuid));
        // Start of a method/block
        } catch (IOException e) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets a {@link JsonObject} with the response from the mojang API
     *
     * @param uuid The UUID as a {@link String}
     * @return The {@link JsonObject} or {@code null} if the mojang API is down or the UUID is invalid
     */
    // Annotation for the following element
    @Blocking
    // Start of a method/block
    public static @Nullable JsonObject fromUuid(String uuid) {
        // Code statement
        final UUID parsed;
        // Exception handling
        try {
            // Calls a method
            parsed = UUID.fromString(uuid);
        // Start of a method/block
        } catch (IllegalArgumentException e) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
        // Returns a value to the caller
        return fromUuid(parsed);
    // End of a block/expression
    }

    /**
     * Gets a {@link JsonObject} with the response from the mojang API
     *
     * @param username The username as a {@link String}
     * @return The {@link JsonObject} or {@code null} if the mojang API is down or the username is invalid
     */
    // Annotation for the following element
    @Blocking
    // Start of a method/block
    public static @Nullable JsonObject fromUsername(String username) {
        // Branch: checks a condition
        if (!USERNAME_PATTERN.matcher(username).matches()) return null;
        // Exception handling
        try {
            // Returns a value to the caller
            return retrieve(String.format(FROM_USERNAME_URL, username));
        // Start of a method/block
        } catch (IOException e) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Blocking
    // Annotation for the following element
    @ApiStatus.Internal
    // Start of a method/block
    public static JsonObject authenticateSession(String loginUsername, String serverId, @Nullable SocketAddress userSocket) throws IOException {
        // Calls a method
        final String username = encode(loginUsername);
        // Calls a method
        final String encodedServerId = encode(serverId);

        // Code statement
        final String url;
        // Branch: checks a condition
        if (ServerFlag.AUTH_PREVENT_PROXY_CONNECTIONS
                // Code statement
                && userSocket instanceof InetSocketAddress inetSocketAddress
                // Code statement
                && inetSocketAddress.getAddress() instanceof InetAddress address
        // Start of a method/block
        ) {
            // Calls a method
            url = String.format(PREVENT_PROXY_CONNECTIONS_AUTH_URL, username, encodedServerId, encode(address.getHostAddress()));
        // Alternative branch of the condition
        } else {
            // Calls a method
            url = String.format(BASE_AUTH_URL, username, encodedServerId);
        // End of a block/expression
        }

        // Returns a value to the caller
        return retrieve(url);
    // End of a block/expression
    }

    // Start of a method/block
    private static String encode(String value) {
        // Returns a value to the caller
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    // End of a block/expression
    }

    // Start of a method/block
    private static String validateUsername(String username) throws IOException {
        // Branch: checks a condition
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            // Throws an exception
            throw new IOException("Invalid username: " + username);
        // End of a block/expression
        }
        // Returns a value to the caller
        return username;
    // End of a block/expression
    }

    /**
     * Gets the JsonObject from a URL, expects a mojang player URL so the errors might not make sense if it is not
     *
     * @param url The url to retrieve
     * @return The {@link JsonObject} of the result
     * @throws IOException with the text detailing the exception
     */
    // Start of a method/block
    private static JsonObject retrieve(String url) throws IOException {
        // Retrieve from the rate-limited Mojang API
        // Calls a method
        final String response = URLUtils.getText(url);
        // If our response is "", that means the url did not get a proper object from the url
        // So the username or UUID was invalid, and therefore we return null
        // Branch: checks a condition
        if (response.isEmpty()) throw new IOException("The Mojang API is down");
        // Calls a method
        JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
        // Branch: checks a condition
        if (jsonObject.has("errorMessage")) {
            // Throws an exception
            throw new IOException(jsonObject.get("errorMessage").getAsString());
        // End of a block/expression
        }
        // Returns a value to the caller
        return jsonObject;
    // End of a block/expression
    }
// End of a block/expression
}
