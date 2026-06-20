// Package declaration for this file
package net.minestom.server.network.player;

// Import of a required class
import net.kyori.adventure.key.Key;
// Import of a required class
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.codec.StructCodec;
// Import of a required class
import net.minestom.server.entity.PlayerSkin;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.utils.Either;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public record ResolvableProfile(
        // Code statement
        Either<GameProfile, Partial> profile,
        // Code statement
        PlayerSkin.Patch patch
// Start of a method/block
) implements PlayerHeadObjectContents.SkinSource {
    // Calls a method
    public static final ResolvableProfile EMPTY = new ResolvableProfile(Either.right(Partial.EMPTY), PlayerSkin.Patch.EMPTY);

    // Assigns a value
    public static final NetworkBuffer.Type<ResolvableProfile> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Code statement
            NetworkBuffer.Either(GameProfile.SERIALIZER, Partial.NETWORK_TYPE), ResolvableProfile::profile,
            // Code statement
            PlayerSkin.Patch.NETWORK_TYPE, ResolvableProfile::patch,
            // Code statement
            ResolvableProfile::new);
    // Assigns a value
    public static final StructCodec<ResolvableProfile> CODEC = StructCodec.struct(
            // Code statement
            StructCodec.INLINE, Codec.EitherStruct(GameProfile.CODEC, Partial.CODEC), ResolvableProfile::profile,
            // Code statement
            StructCodec.INLINE, PlayerSkin.Patch.CODEC, ResolvableProfile::patch,
            // Code statement
            ResolvableProfile::new);

    // Start of a method/block
    public ResolvableProfile {
        // Calls a method
        Objects.requireNonNull(profile, "profile");
        // Calls a method
        Objects.requireNonNull(patch, "patch");
    // End of a block/expression
    }

    // Start of a method/block
    public ResolvableProfile(GameProfile profile) {
        // Calls a method
        this(Either.left(profile), PlayerSkin.Patch.EMPTY);
    // End of a block/expression
    }

    // Start of a method/block
    public ResolvableProfile(GameProfile profile, PlayerSkin.Patch patch) {
        // Calls a method
        this(Either.left(profile), patch);
    // End of a block/expression
    }

    // Start of a method/block
    public ResolvableProfile(Partial partial) {
        // Calls a method
        this(Either.right(partial), PlayerSkin.Patch.EMPTY);
    // End of a block/expression
    }

    // Start of a method/block
    public ResolvableProfile(Partial partial, PlayerSkin.Patch patch) {
        // Calls a method
        this(Either.right(partial), patch);
    // End of a block/expression
    }

    // Start of a method/block
    public ResolvableProfile(PlayerSkin skin) {
        // Code statement
        this(new Partial(null, null, List.of(
                // Creates a new object
                new GameProfile.Property("textures", skin.textures(), skin.signature())
        // Code statement
        )));
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Partial(
            // Annotation for the following element
            @Nullable String name,
            // Annotation for the following element
            @Nullable UUID uuid,
            // Code statement
            List<GameProfile.Property> properties
    // Start of a method/block
    ) {
        // Calls a method
        public static final Partial EMPTY = new Partial(null, null, List.of());

        // Assigns a value
        public static final NetworkBuffer.Type<Partial> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.STRING.optional(), Partial::name,
                // Code statement
                NetworkBuffer.UUID.optional(), Partial::uuid,
                // Code statement
                GameProfile.Property.SERIALIZER.list(GameProfile.MAX_PROPERTIES), Partial::properties,
                // Code statement
                Partial::new);
        // Assigns a value
        public static final StructCodec<Partial> CODEC = StructCodec.struct(
                // Code statement
                "name", Codec.STRING.optional(), Partial::name,
                // Code statement
                "id", Codec.UUID.optional(), Partial::uuid,
                // Code statement
                "properties", GameProfile.Property.LIST_CODEC.optional(List.of()), Partial::properties,
                // Code statement
                Partial::new);

        // Start of a method/block
        public Partial {
            // Calls a method
            properties = List.copyOf(properties);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Adventure Mapping

    // Start of a method/block
    public static ResolvableProfile fromPlayerHeadContents(PlayerHeadObjectContents contents) {
        // Calls a method
        final Key texture = contents.texture();
        // Branch: checks a condition
        if (texture != null) return new ResolvableProfile(Partial.EMPTY, new PlayerSkin.Patch(texture));

        // Calls a method
        final List<GameProfile.Property> properties = new ArrayList<>(contents.profileProperties().size());
        // Loop: repeats a block
        for (PlayerHeadObjectContents.ProfileProperty property : contents.profileProperties()) {
            // Code statement
            properties.add(property instanceof GameProfile.Property p ? p :
                    // Creates a new object
                    new GameProfile.Property(property.name(), property.value(), property.signature()));
        // End of a block/expression
        }
        // Returns a value to the caller
        return new ResolvableProfile(new Partial(contents.name(), contents.id(), properties));
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("UnstableApiUsage") // Its a platform API, we are allowed to implement it.
    // Start of a method/block
    public void applySkinToPlayerHeadContents(PlayerHeadObjectContents.Builder builder) {
        // Branch: checks a condition
        if (patch.body() != null) builder.texture(patch.body());
        // Multiple branching (switch/case)
        switch (profile) {
            // Multiple branching (switch/case)
            case Either.Left(GameProfile gameProfile) -> {
                // Calls a method
                builder.name(gameProfile.name());
                // Calls a method
                builder.id(gameProfile.uuid());
                // Calls a method
                builder.profileProperties(gameProfile.properties());
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case Either.Right(Partial partial) -> {
                // Calls a method
                builder.name(partial.name());
                // Calls a method
                builder.id(partial.uuid());
                // Calls a method
                builder.profileProperties(partial.properties());
            // End of a block/expression
            }
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
