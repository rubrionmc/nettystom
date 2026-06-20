// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.animal.tameable;

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
public class ParrotMeta extends TameableAnimalMeta {
    // Début d'une méthode/d'un bloc
    public ParrotMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#PARROT_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Color getColor() {
        // Renvoie une valeur à l'appelant
        return Color.VALUES[metadata.get(MetadataDef.Parrot.VARIANT)];
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#PARROT_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setColor(Color value) {
        // Appelle une méthode
        metadata.set(MetadataDef.Parrot.VARIANT, value.ordinal());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.PARROT_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getColor();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.PARROT_VARIANT)
            // Appelle une méthode
            setColor((Color) value);
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Color {
        // Instruction de code
        RED_BLUE,
        // Instruction de code
        BLUE,
        // Instruction de code
        GREEN,
        // Instruction de code
        YELLOW_BLUE,
        // Instruction de code
        GREY;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Color> NETWORK_TYPE = NetworkBuffer.Enum(Color.class);
        // Appelle une méthode
        public static final Codec<Color> CODEC = Codec.Enum(Color.class);

        // Appelle une méthode
        private final static Color[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
