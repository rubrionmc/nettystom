// Déclaration du paquet de ce fichier
package net.minestom.server.entity;

// Import d'une classe nécessaire
import com.google.gson.JsonArray;
// Import d'une classe nécessaire
import com.google.gson.JsonElement;
// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.utils.mojang.MojangUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Blocking;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

/**
 * Contains all the data required to store a skin.
 * <p>
 * Can be applied to a player with {@link Player#setSkin(PlayerSkin)}
 * or in the linked event {@link net.minestom.server.event.player.PlayerSkinInitEvent}.
 */
// Déclaration de type (classe/interface/enum/record)
public record PlayerSkin(String textures, String signature) {

    /**
     * Gets a skin from a Mojang UUID.
     *
     * @param uuid Mojang UUID
     * @return a player skin based on the UUID, null if not found
     */
    // Annotation pour l'élément suivant
    @Blocking
    // Début d'une méthode/d'un bloc
    public static @Nullable PlayerSkin fromUuid(String uuid) {
        // Appelle une méthode
        final JsonObject jsonObject = MojangUtils.fromUuid(uuid);
        // Embranchement : vérifie une condition
        if (jsonObject == null) return null;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final JsonArray propertiesArray = jsonObject.get("properties").getAsJsonArray();
            // Boucle : répète un bloc
            for (JsonElement jsonElement : propertiesArray) {
                // Appelle une méthode
                final JsonObject propertyObject = jsonElement.getAsJsonObject();
                // Appelle une méthode
                final String name = propertyObject.get("name").getAsString();
                // Embranchement : vérifie une condition
                if (!name.equals("textures")) continue;
                // Appelle une méthode
                final String textureValue = propertyObject.get("value").getAsString();
                // Appelle une méthode
                final String signatureValue = propertyObject.get("signature").getAsString();
                // Renvoie une valeur à l'appelant
                return new PlayerSkin(textureValue, signatureValue);
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return null;
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets a skin from a Minecraft username.
     *
     * @param username the Minecraft username
     * @return a skin based on a Minecraft username, null if not found
     */
    // Annotation pour l'élément suivant
    @Blocking
    // Début d'une méthode/d'un bloc
    public static @Nullable PlayerSkin fromUsername(String username) {
        // Appelle une méthode
        final JsonObject jsonObject = MojangUtils.fromUsername(username);
        // Embranchement : vérifie une condition
        if (jsonObject == null) return null;
        // Gestion des exceptions
        try {
            // Appelle une méthode
            final String uuid = jsonObject.get("id").getAsString();
            // Retrieve the skin data from the mojang uuid
            // Renvoie une valeur à l'appelant
            return fromUuid(uuid);
        // Début d'une méthode/d'un bloc
        } catch (Exception e) {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Patch(
            // Annotation pour l'élément suivant
            @Nullable Key body,
            // Annotation pour l'élément suivant
            @Nullable Key cape,
            // Annotation pour l'élément suivant
            @Nullable Key elytra,
            // Annotation pour l'élément suivant
            @Nullable Boolean slim
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        public static final Patch EMPTY = new Patch(null, null, null, null);

        // Affecte une valeur
        public static final NetworkBuffer.Type<Patch> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.KEY.optional(), Patch::body,
                // Instruction de code
                NetworkBuffer.KEY.optional(), Patch::cape,
                // Instruction de code
                NetworkBuffer.KEY.optional(), Patch::elytra,
                // Instruction de code
                NetworkBuffer.BOOLEAN.optional(), Patch::slim,
                // Instruction de code
                Patch::new);
        // Affecte une valeur
        public static final StructCodec<Patch> CODEC = StructCodec.struct(
                // Instruction de code
                "body", Codec.KEY.optional(), Patch::body,
                // Instruction de code
                "cape", Codec.KEY.optional(), Patch::cape,
                // Instruction de code
                "elytra", Codec.KEY.optional(), Patch::elytra,
                // Instruction de code
                "slim", Codec.BOOLEAN.optional(), Patch::slim,
                // Instruction de code
                Patch::new);

        // Début d'une méthode/d'un bloc
        public Patch(Key body) {
            // Appelle une méthode
            this(body, null, null, null);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
