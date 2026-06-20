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
public class HorseMeta extends AbstractHorseMeta {
    // Début d'une méthode/d'un bloc
    public HorseMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#HORSE_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Variant getVariant() {
        // Renvoie une valeur à l'appelant
        return getVariantFromID(metadata.get(MetadataDef.Horse.VARIANT));
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#HORSE_VARIANT} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(Variant variant) {
        // Appelle une méthode
        metadata.set(MetadataDef.Horse.VARIANT, getVariantID(variant.marking, variant.color));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int getVariantID(Marking marking, Color color) {
        // Renvoie une valeur à l'appelant
        return (marking.ordinal() << 8) + color.ordinal();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Variant getVariantFromID(int variantID) {
        // Renvoie une valeur à l'appelant
        return new Variant(
                // Instruction de code
                Marking.VALUES[variantID >> 8],
                // Instruction de code
                Color.VALUES[variantID & 0xFF]
        // Fin d'un bloc/d'une expression
        );
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.HORSE_VARIANT)
            // Renvoie une valeur à l'appelant
            return (T) getVariant().getMarking();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.HORSE_VARIANT) {
            // Appelle une méthode
            var variant = getVariant();
            // Appelle une méthode
            variant.setMarking((Marking) value);
            // Appelle une méthode
            setVariant(variant);
        // Branche alternative de la condition
        } else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public static class Variant {

        // Instruction de code
        private Marking marking;
        // Instruction de code
        private Color color;

        // Début d'une méthode/d'un bloc
        public Variant(Marking marking, Color color) {
            // Accès à l'objet courant/parent
            this.marking = marking;
            // Accès à l'objet courant/parent
            this.color = color;
        // Fin d'un bloc/d'une expression
        }

            // Début d'une méthode/d'un bloc
            public Marking getMarking() {
            // Renvoie une valeur à l'appelant
            return this.marking;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void setMarking(Marking marking) {
            // Accès à l'objet courant/parent
            this.marking = marking;
        // Fin d'un bloc/d'une expression
        }

            // Début d'une méthode/d'un bloc
            public Color getColor() {
            // Renvoie une valeur à l'appelant
            return this.color;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public void setColor(Color color) {
            // Accès à l'objet courant/parent
            this.color = color;
        // Fin d'un bloc/d'une expression
        }

    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Marking {
        // Instruction de code
        NONE,
        // Instruction de code
        WHITE,
        // Instruction de code
        WHITE_FIELD,
        // Instruction de code
        WHITE_DOTS,
        // Instruction de code
        BLACK_DOTS;

        // Appelle une méthode
        private final static Marking[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Color {
        // Instruction de code
        WHITE,
        // Instruction de code
        CREAMY,
        // Instruction de code
        CHESTNUT,
        // Instruction de code
        BROWN,
        // Instruction de code
        BLACK,
        // Instruction de code
        GRAY,
        // Instruction de code
        DARK_BROWN;

        // Appelle une méthode
        public static final NetworkBuffer.Type<Color> NETWORK_TYPE = NetworkBuffer.Enum(Color.class);
        // Appelle une méthode
        public static final Codec<Color> NBT_TYPE = Codec.Enum(Color.class);

        // Appelle une méthode
        private final static Color[] VALUES = values();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
