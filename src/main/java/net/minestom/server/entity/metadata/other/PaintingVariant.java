// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.other;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.codec.StructCodec;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.registry.*;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Contract;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public sealed interface PaintingVariant extends Holder.Direct<PaintingVariant>, PaintingVariants permits PaintingVariantImpl {
    // Affecte une valeur
    NetworkBuffer.Type<PaintingVariant> REGISTRY_NETWORK_TYPE = NetworkBufferTemplate.template(
            // Instruction de code
            NetworkBuffer.INT, PaintingVariant::width,
            // Instruction de code
            NetworkBuffer.INT, PaintingVariant::height,
            // Instruction de code
            NetworkBuffer.KEY, PaintingVariant::assetId,
            // Instruction de code
            NetworkBuffer.COMPONENT.optional(), PaintingVariant::title,
            // Instruction de code
            NetworkBuffer.COMPONENT.optional(), PaintingVariant::author,
            // Instruction de code
            PaintingVariantImpl::new);
    // Affecte une valeur
    Codec<PaintingVariant> REGISTRY_CODEC = StructCodec.struct(
            // Instruction de code
            "width", Codec.INT, PaintingVariant::width,
            // Instruction de code
            "height", Codec.INT, PaintingVariant::height,
            // Instruction de code
            "asset_id", Codec.KEY, PaintingVariant::assetId,
            // Instruction de code
            "title", Codec.COMPONENT.optional(), PaintingVariant::title,
            // Instruction de code
            "author", Codec.COMPONENT.optional(), PaintingVariant::author,
            // Instruction de code
            PaintingVariantImpl::new);

    // For some unknown reason, the network type still uses a holder even though the codec does not.
    // This appears to be a mistake since stopping inline values was explicitly mentioned as a change in snapshot notes.
    // It would also not work on vanilla as serializing a painting entity with inline variant would fail.
    // However, we don't serialize painting entities, so we can allow this :) Use at your own risk.
    // IMPL: Please remove the workaround later if this is fixed.
    // Appelle une méthode
    NetworkBuffer.Type<Holder<PaintingVariant>> NETWORK_TYPE = Holder.networkType(Registries::paintingVariant, REGISTRY_NETWORK_TYPE);
    // Affecte une valeur
    Codec<Holder<PaintingVariant>> CODEC = RegistryKey.codec(Registries::paintingVariant)
            // Appelle une méthode
            .transform(key -> key, Holder::asKey);

    // Instruction de code
    static PaintingVariant create(
            // Instruction de code
            Key assetId,
            // Instruction de code
            int width, int height,
            // Annotation pour l'élément suivant
            @Nullable Component title,
            // Annotation pour l'élément suivant
            @Nullable Component author
    // Début d'une méthode/d'un bloc
    ) {
        // Renvoie une valeur à l'appelant
        return new PaintingVariantImpl(width, height, assetId, title, author);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Builder builder() {
        // Renvoie une valeur à l'appelant
        return new Builder();
    // Fin d'un bloc/d'une expression
    }

    /**
     * <p>Creates a new registry for painting variants, loading the vanilla painting variants.</p>
     *
     * @see net.minestom.server.MinecraftServer to get an existing instance of the registry
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Internal
    // Début d'une méthode/d'un bloc
    static DynamicRegistry<PaintingVariant> createDefaultRegistry() {
        // Renvoie une valeur à l'appelant
        return DynamicRegistry.create(Key.key("painting_variant"), REGISTRY_CODEC, RegistryData.Resource.PAINTING_VARIANTS);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    Key assetId();

    // Appelle une méthode
    int width();

    // Appelle une méthode
    int height();

    // Annotation pour l'élément suivant
    @Nullable Component title();

    // Annotation pour l'élément suivant
    @Nullable Component author();

    // Déclaration de type (classe/interface/enum/record)
    class Builder {
        // Instruction de code
        private Key assetId;
        // Instruction de code
        private int width;
        // Instruction de code
        private int height;
        // Instruction de code
        private Component title;
        // Instruction de code
        private Component author;

        // Début d'une méthode/d'un bloc
        private Builder() {
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder assetId(Key assetId) {
            // Accès à l'objet courant/parent
            this.assetId = assetId;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder width(int width) {
            // Accès à l'objet courant/parent
            this.width = width;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder height(int height) {
            // Accès à l'objet courant/parent
            this.height = height;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder title(@Nullable Component title) {
            // Accès à l'objet courant/parent
            this.title = title;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Contract(value = "_ -> this", pure = true)
        // Début d'une méthode/d'un bloc
        public Builder author(@Nullable Component author) {
            // Accès à l'objet courant/parent
            this.author = author;
            // Renvoie une valeur à l'appelant
            return this;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public PaintingVariant build() {
            // Renvoie une valeur à l'appelant
            return new PaintingVariantImpl(width, height, assetId, title, author);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
