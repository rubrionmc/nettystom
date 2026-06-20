// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal;

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
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Déclaration de type (classe/interface/enum/record)
public class RabbitMeta extends AnimalMeta {
    // Début d'une méthode/d'un bloc
    public RabbitMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#RABBIT_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(RabbitMeta.Variant variant) {
        // Appelle une méthode
        int id = variant == Variant.KILLER_BUNNY ? 99 : variant.ordinal();
        // Appelle une méthode
        metadata.set(MetadataDef.Rabbit.TYPE, id);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#RABBIT_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public RabbitMeta.Variant getVariant() {
        // Appelle une méthode
        int id = metadata.get(MetadataDef.Rabbit.TYPE);
        // Embranchement : vérifie une condition
        if (id == 99) {
            // Renvoie une valeur à l'appelant
            return Variant.KILLER_BUNNY;
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return Variant.VALUES[id];
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.RABBIT_VARIANT)
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
        if (component == DataComponents.RABBIT_VARIANT)
            // Appelle une méthode
            setVariant((Variant) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Variant {
        // Instruction de code
        BROWN,
        // Instruction de code
        WHITE,
        // Instruction de code
        BLACK,
        // Instruction de code
        BLACK_AND_WHITE,
        // Instruction de code
        GOLD,
        // Instruction de code
        SALT_AND_PEPPER,
        // Instruction de code
        KILLER_BUNNY;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Variant> NETWORK_TYPE = NetworkBuffer.Enum(Variant.class);
        // Appelle une méthode
        public static final Codec<Variant> CODEC = Codec.Enum(Variant.class);

        // Appelle une méthode
        private final static Variant[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
