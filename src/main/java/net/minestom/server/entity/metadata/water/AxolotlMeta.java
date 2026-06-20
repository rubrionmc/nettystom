// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.water;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponent;
// Import d'une classe nécessaire
import net.minestom.server.component.DataComponents;
// Import d'une classe nécessaire
import net.minestom.server.entity.Entity;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataDef;
// Import d'une classe nécessaire
import net.minestom.server.entity.MetadataHolder;
// Import d'une classe nécessaire
import net.minestom.server.entity.metadata.animal.AnimalMeta;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class AxolotlMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public AxolotlMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#AXOLOTL_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Variant getVariant() {
        // Renvoie une valeur à l'appelant
        return Variant.VALUES[metadata.get(MetadataDef.Axolotl.VARIANT)];
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#AXOLOTL_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(Variant variant) {
        // Appelle une méthode
        metadata.set(MetadataDef.Axolotl.VARIANT, variant.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isPlayingDead() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Axolotl.IS_PLAYING_DEAD);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setPlayingDead(boolean playingDead) {
        // Appelle une méthode
        metadata.set(MetadataDef.Axolotl.IS_PLAYING_DEAD, playingDead);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isFromBucket() {
        // Renvoie une valeur à l'appelant
        return metadata.get(MetadataDef.Axolotl.IS_FROM_BUCKET);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setFromBucket(boolean fromBucket) {
        // Appelle une méthode
        metadata.set(MetadataDef.Axolotl.IS_FROM_BUCKET, fromBucket);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.AXOLOTL_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getVariant();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.AXOLOTL_VARIANT)
            // Appelle une méthode
            setVariant((Variant) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Variant {
        // Instruction de code
        LUCY,
        // Instruction de code
        WILD,
        // Instruction de code
        GOLD,
        // Instruction de code
        CYAN,
        // Instruction de code
        BLUE;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Variant> NETWORK_TYPE = NetworkBuffer.Enum(Variant.class);
        // Appelle une méthode
        public static final Codec<Variant> CODEC = Codec.Enum(Variant.class);

        // Appelle une méthode
        private final static AxolotlMeta.Variant[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
