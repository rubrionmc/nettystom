// Déclaration du paquet de ce fichier
package net.minestom.server.network.player;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.object.PlayerHeadObjectContents;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.entity.PlayerSkin;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public record ResolvableProfile(
        // Instruction de code
        Either<GameProfile, Partial> profile,
        // Instruction de code
        PlayerSkin.Patch patch
// Début d'une méthode/d'un bloc
) implements PlayerHeadObjectContents.SkinSource {
    // Appelle une méthode
    public static final ResolvableProfile EMPTY = new ResolvableProfile(Either.right(Partial.EMPTY), PlayerSkin.Patch.EMPTY);

    // Affecte une valeur
    public static final NetworkBuffer.Type<ResolvableProfile> NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.Either(GameProfile.SERIALIZER, Partial.NETWORK_TYPE), ResolvableProfile::profile,
            // Instruction de code
            PlayerSkin.Patch.NETWORK_TYPE, ResolvableProfile::patch,
            // Instruction de code
            ResolvableProfile::new);
    // Affecte une valeur
    public static final StructCodec<ResolvableProfile> CODEC = StructCodec.struct(
            // Instruction de code
            StructCodec.INLINE, Codec.EitherStruct(GameProfile.CODEC, Partial.CODEC), ResolvableProfile::profile,
            // Instruction de code
            StructCodec.INLINE, PlayerSkin.Patch.CODEC, ResolvableProfile::patch,
            // Instruction de code
            ResolvableProfile::new);

    // Début d'une méthode/d'un bloc
    public ResolvableProfile {
        // Appelle une méthode
        Objects.requireNonNull(profile, "profile");
        // Appelle une méthode
        Objects.requireNonNull(patch, "patch");
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ResolvableProfile(GameProfile profile) {
        // Appelle une méthode
        this(Either.left(profile), PlayerSkin.Patch.EMPTY);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ResolvableProfile(GameProfile profile, PlayerSkin.Patch patch) {
        // Appelle une méthode
        this(Either.left(profile), patch);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ResolvableProfile(Partial partial) {
        // Appelle une méthode
        this(Either.right(partial), PlayerSkin.Patch.EMPTY);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ResolvableProfile(Partial partial, PlayerSkin.Patch patch) {
        // Appelle une méthode
        this(Either.right(partial), patch);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ResolvableProfile(PlayerSkin skin) {
        // Instruction de code
        this(new Partial(null, null, List.of(
                // Crée un nouvel objet
                new GameProfile.Property("textures", skin.textures(), skin.signature())
        // Instruction de code
        )));
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Partial(
            // Annotation pour l'élément suivant
            @Nullable String name,
            // Annotation pour l'élément suivant
            @Nullable UUID uuid,
            // Instruction de code
            List<GameProfile.Property> properties
    // Début d'une méthode/d'un bloc
    ) {
        // Appelle une méthode
        public static final Partial EMPTY = new Partial(null, null, List.of());

        // Affecte une valeur
        public static final NetworkBuffer.Type<Partial> NETWORK_TYPE = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.STRING.optional(), Partial::name,
                // Instruction de code
                NetworkBuffer.UUID.optional(), Partial::uuid,
                // Instruction de code
                GameProfile.Property.SERIALIZER.list(GameProfile.MAX_PROPERTIES), Partial::properties,
                // Instruction de code
                Partial::new);
        // Affecte une valeur
        public static final StructCodec<Partial> CODEC = StructCodec.struct(
                // Instruction de code
                "name", Codec.STRING.optional(), Partial::name,
                // Instruction de code
                "id", Codec.UUID.optional(), Partial::uuid,
                // Instruction de code
                "properties", GameProfile.Property.LIST_CODEC.optional(List.of()), Partial::properties,
                // Instruction de code
                Partial::new);

        // Début d'une méthode/d'un bloc
        public Partial {
            // Appelle une méthode
            properties = List.copyOf(properties);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Adventure Mapping

    // Début d'une méthode/d'un bloc
    public static ResolvableProfile fromPlayerHeadContents(PlayerHeadObjectContents contents) {
        // Appelle une méthode
        final Key texture = contents.texture();
        // Embranchement : vérifie une condition
        if (texture != null) return new ResolvableProfile(Partial.EMPTY, new PlayerSkin.Patch(texture));

        // Appelle une méthode
        final List<GameProfile.Property> properties = new ArrayList<>(contents.profileProperties().size());
        // Boucle : répète un bloc
        for (PlayerHeadObjectContents.ProfileProperty property : contents.profileProperties()) {
            // Instruction de code
            properties.add(property instanceof GameProfile.Property p ? p :
                    // Crée un nouvel objet
                    new GameProfile.Property(property.name(), property.value(), property.signature()));
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return new ResolvableProfile(new Partial(contents.name(), contents.id(), properties));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("UnstableApiUsage") // Its a platform API, we are allowed to implement it.
    // Début d'une méthode/d'un bloc
    public void applySkinToPlayerHeadContents(PlayerHeadObjectContents.Builder builder) {
        // Embranchement : vérifie une condition
        if (patch.body() != null) builder.texture(patch.body());
        // Embranchement multiple (switch/case)
        switch (profile) {
            // Embranchement multiple (switch/case)
            case Either.Left(GameProfile gameProfile) -> {
                // Appelle une méthode
                builder.name(gameProfile.name());
                // Appelle une méthode
                builder.id(gameProfile.uuid());
                // Appelle une méthode
                builder.profileProperties(gameProfile.properties());
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case Either.Right(Partial partial) -> {
                // Appelle une méthode
                builder.name(partial.name());
                // Appelle une méthode
                builder.id(partial.uuid());
                // Appelle une méthode
                builder.profileProperties(partial.properties());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
