// Package declaration for this file
package net.minestom.server.entity;

// Import of a required class
import com.google.gson.JsonArray;
// Import of a required class
import com.google.gson.JsonElement;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.utils.mojang.MojangUtils;
// Import of a required class
import org.jetbrains.annotations.Blocking;
// Import of a required class
import org.jetbrains.annotations.Nullable;

/**
 * Contains all the data required to store a skin.
 * <p>
 * Can be applied to a player with {@link Player#setSkin(PlayerSkin)}
 * or in the linked event {@link net.minestom.server.event.player.PlayerSkinInitEvent}.
 */
// Type declaration (class/interface/enum/record)
public record PlayerSkin(String textures, String signature) {

    /**
     * Gets a skin from a Mojang UUID.
     *
     * @param uuid Mojang UUID
     * @return a player skin based on the UUID, null if not found
     */
    // Annotation for the following element
    @Blocking
    // Start of a method/block
    public static @Nullable PlayerSkin fromUuid(String uuid) {
        // Calls a method
        final JsonObject jsonObject = MojangUtils.fromUuid(uuid);
        // Branch: checks a condition
        if (jsonObject == null) return null;
        // Exception handling
        try {
            // Calls a method
            final JsonArray propertiesArray = jsonObject.get("properties").getAsJsonArray();
            // Loop: repeats a block
            for (JsonElement jsonElement : propertiesArray) {
                // Calls a method
                final JsonObject propertyObject = jsonElement.getAsJsonObject();
                // Calls a method
                final String name = propertyObject.get("name").getAsString();
                // Branch: checks a condition
                if (!name.equals("textures")) continue;
                // Calls a method
                final String textureValue = propertyObject.get("value").getAsString();
                // Calls a method
                final String signatureValue = propertyObject.get("signature").getAsString();
                // Returns a value to the caller
                return new PlayerSkin(textureValue, signatureValue);
            // End of a block/expression
            }
            // Returns a value to the caller
            return null;
        // Start of a method/block
        } catch (Exception e) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Gets a skin from a Minecraft username.
     *
     * @param username the Minecraft username
     * @return a skin based on a Minecraft username, null if not found
     */
    // Annotation for the following element
    @Blocking
    // Start of a method/block
    public static @Nullable PlayerSkin fromUsername(String username) {
        // Calls a method
        final JsonObject jsonObject = MojangUtils.fromUsername(username);
        // Branch: checks a condition
        if (jsonObject == null) return null;
        // Exception handling
        try {
            // Calls a method
            final String uuid = jsonObject.get("id").getAsString();
            // Retrieve the skin data from the mojang uuid
            // Returns a value to the caller
            return fromUuid(uuid);
        // Start of a method/block
        } catch (Exception e) {
            // Returns a value to the caller
            return null;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Patch(
            // Annotation for the following element
            @Nullable Key body,
            // Annotation for the following element
            @Nullable Key cape,
            // Annotation for the following element
            @Nullable Key elytra,
            // Annotation for the following element
            @Nullable Boolean slim
    // Start of a method/block
    ) {
        // Calls a method
        public static final Patch EMPTY = new Patch(null, null, null, null);

        // Assigns a value
        public static final NetworkBuffer.Type<Patch> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.KEY.optional(), Patch::body,
                // Code statement
                NetworkBuffer.KEY.optional(), Patch::cape,
                // Code statement
                NetworkBuffer.KEY.optional(), Patch::elytra,
                // Code statement
                NetworkBuffer.BOOLEAN.optional(), Patch::slim,
                // Code statement
                Patch::new);
        // Assigns a value
        public static final StructCodec<Patch> CODEC = StructCodec.struct(
                // Code statement
                "body", Codec.KEY.optional(), Patch::body,
                // Code statement
                "cape", Codec.KEY.optional(), Patch::cape,
                // Code statement
                "elytra", Codec.KEY.optional(), Patch::elytra,
                // Code statement
                "slim", Codec.BOOLEAN.optional(), Patch::slim,
                // Code statement
                Patch::new);

        // Start of a method/block
        public Patch(Key body) {
            // Calls a method
            this(body, null, null, null);
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
