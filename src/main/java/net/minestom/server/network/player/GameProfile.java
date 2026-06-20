// Déclaration du paquet de ce fichier
package net.minestom.server.network.player;

// Import d'une classe nécessaire
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.function.Function;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.STRING;

// Déclaration de type (classe/interface/enum/record)
public record GameProfile(
        // Instruction de code
        UUID uuid, String name,
        // Instruction de code
        List<Property> properties
// Début d'une méthode/d'un bloc
) {
    // Affecte une valeur
    public static final int MAX_PROPERTIES = 1024;

    // Début d'une méthode/d'un bloc
    public GameProfile {
        // Appelle une méthode
        Objects.requireNonNull(uuid, "uuid");
        // Appelle une méthode
        Objects.requireNonNull(name, "name");
        // Appelle une méthode
        Objects.requireNonNull(properties, "properties");
        // Embranchement : vérifie une condition
        if (name.length() > 16)
            // Lève une exception
            throw new IllegalArgumentException("Name length cannot be greater than 16 characters");
        // Appelle une méthode
        properties = List.copyOf(properties);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public GameProfile(UUID uuid, String name) {
        // Appelle une méthode
        this(uuid, name, List.of());
    // Fin d'un bloc/d'une expression
    }

    // Affecte une valeur
    public static final NetworkBuffer.Type<GameProfile> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.UUID, GameProfile::uuid,
            // Instruction de code
            STRING, GameProfile::name,
            // Instruction de code
            Property.SERIALIZER.list(MAX_PROPERTIES), GameProfile::properties,
            // Instruction de code
            GameProfile::new);
    // Affecte une valeur
    public static final StructCodec<GameProfile> CODEC = StructCodec.struct(
            // Instruction de code
            "id", Codec.UUID, GameProfile::uuid,
            // Instruction de code
            "name", Codec.STRING, GameProfile::name,
            // Instruction de code
            "properties", Property.LIST_CODEC.optional(List.of()), GameProfile::properties,
            // Instruction de code
            GameProfile::new);

    // Déclaration de type (classe/interface/enum/record)
    public record Property(String name, String value, @Nullable String signature) implements PlayerHeadObjectContents.ProfileProperty {
        // Début d'une méthode/d'un bloc
        public Property {
            // Appelle une méthode
            Objects.requireNonNull(name, "name");
            // Appelle une méthode
            Objects.requireNonNull(value, "value");
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Property(String name, String value) {
            // Appelle une méthode
            this(name, value, null);
        // Fin d'un bloc/d'une expression
        }

        // Affecte une valeur
        public static final NetworkBuffer.Type<Property> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                STRING, Property::name,
                // Instruction de code
                STRING, Property::value,
                // Instruction de code
                STRING.optional(), Property::signature,
                // Instruction de code
                Property::new);
        // Affecte une valeur
        public static final Codec<Property> CODEC = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING, Property::name,
                // Instruction de code
                "value", Codec.STRING, Property::value,
                // Instruction de code
                "signature", Codec.STRING.optional(), Property::signature,
                // Instruction de code
                Property::new);

        // Affecte une valeur
        public static final Codec<List<Property>> LIST_CODEC = Codec
                // Instruction de code
                .Either(Codec.STRING.mapValue(Codec.STRING), CODEC.list())
                // Instruction de code
                .transform(either -> either.unify(
                        // Instruction de code
                        map -> map.entrySet().stream().map(
                                // Instruction de code
                                entry -> new Property(entry.getKey(), entry.getValue(), null)
                        // Instruction de code
                        ).toList(), Function.identity()),
                        // Instruction de code
                        Either::right);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
