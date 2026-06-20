// Package declaration for this file
package net.minestom.server.network.player;

// Import of a required class
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.function.Function;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.STRING;

// Type declaration (class/interface/enum/record)
public record GameProfile(
        // Code statement
        UUID uuid, String name,
        // Code statement
        List<Property> properties
// Start of a method/block
) {
    // Assigns a value
    public static final int MAX_PROPERTIES = 1024;

    // Start of a method/block
    public GameProfile {
        // Calls a method
        Objects.requireNonNull(uuid, "uuid");
        // Calls a method
        Objects.requireNonNull(name, "name");
        // Calls a method
        Objects.requireNonNull(properties, "properties");
        // Branch: checks a condition
        if (name.length() > 16)
            // Throws an exception
            throw new IllegalArgumentException("Name length cannot be greater than 16 characters");
        // Calls a method
        properties = List.copyOf(properties);
    // End of a block/expression
    }

    // Start of a method/block
    public GameProfile(UUID uuid, String name) {
        // Calls a method
        this(uuid, name, List.of());
    // End of a block/expression
    }

    // Assigns a value
    public static final NetworkBuffer.Type<GameProfile> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.UUID, GameProfile::uuid,
            // Code statement
            STRING, GameProfile::name,
            // Code statement
            Property.SERIALIZER.list(MAX_PROPERTIES), GameProfile::properties,
            // Code statement
            GameProfile::new);
    // Assigns a value
    public static final StructCodec<GameProfile> CODEC = StructCodec.struct(
            // Code statement
            "id", Codec.UUID, GameProfile::uuid,
            // Code statement
            "name", Codec.STRING, GameProfile::name,
            // Code statement
            "properties", Property.LIST_CODEC.optional(List.of()), GameProfile::properties,
            // Code statement
            GameProfile::new);

    // Type declaration (class/interface/enum/record)
    public record Property(String name, String value, @Nullable String signature) implements PlayerHeadObjectContents.ProfileProperty {
        // Start of a method/block
        public Property {
            // Calls a method
            Objects.requireNonNull(name, "name");
            // Calls a method
            Objects.requireNonNull(value, "value");
        // End of a block/expression
        }

        // Start of a method/block
        public Property(String name, String value) {
            // Calls a method
            this(name, value, null);
        // End of a block/expression
        }

        // Assigns a value
        public static final NetworkBuffer.Type<Property> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                STRING, Property::name,
                // Code statement
                STRING, Property::value,
                // Code statement
                STRING.optional(), Property::signature,
                // Code statement
                Property::new);
        // Assigns a value
        public static final Codec<Property> CODEC = StructCodec.struct(
                // Code statement
                "name", Codec.STRING, Property::name,
                // Code statement
                "value", Codec.STRING, Property::value,
                // Code statement
                "signature", Codec.STRING.optional(), Property::signature,
                // Code statement
                Property::new);

        // Assigns a value
        public static final Codec<List<Property>> LIST_CODEC = Codec
                // Code statement
                .Either(Codec.STRING.mapValue(Codec.STRING), CODEC.list())
                // Code statement
                .transform(either -> either.unify(
                        // Code statement
                        map -> map.entrySet().stream().map(
                                // Code statement
                                entry -> new Property(entry.getKey(), entry.getValue(), null)
                        // Code statement
                        ).toList(), Function.identity()),
                        // Code statement
                        Either::right);
    // End of a block/expression
    }
// End of a block/expression
}
