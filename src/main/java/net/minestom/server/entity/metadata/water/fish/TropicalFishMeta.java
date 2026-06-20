// Déclaration du paquet de ce fichier
package net.minestom.server.entity.metadata.water.fish;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;
// Import d'une classe nécessaire
import net.minestom.server.color.DyeColor;
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
public class TropicalFishMeta extends AbstractFishMeta {
    // Début d'une méthode/d'un bloc
    public TropicalFishMeta(Entity entity, MetadataHolder metadata) {
        // Accès à l'objet courant/parent
        super(entity, metadata);
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public Variant getVariant() {
        // Renvoie une valeur à l'appelant
        return Variant.fromPackedId(metadata.get(MetadataDef.TropicalFish.VARIANT));
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents} instead.
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public void setVariant(Variant variant) {
        // Appelle une méthode
        metadata.set(MetadataDef.TropicalFish.VARIANT, variant.packedId());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Annotation pour l'élément suivant
    @SuppressWarnings("unchecked")
    // Début d'une méthode/d'un bloc
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.TROPICAL_FISH_PATTERN)
            // Renvoie une valeur à l'appelant
            return (T) getVariant().pattern();
        // Embranchement : vérifie une condition
        if (component == DataComponents.TROPICAL_FISH_BASE_COLOR)
            // Renvoie une valeur à l'appelant
            return (T) getVariant().baseColor();
        // Embranchement : vérifie une condition
        if (component == DataComponents.TROPICAL_FISH_PATTERN_COLOR)
            // Renvoie une valeur à l'appelant
            return (T) getVariant().patternColor();
        // Renvoie une valeur à l'appelant
        return super.get(component);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    protected <T> void set(DataComponent<T> component, T value) {
        // Embranchement : vérifie une condition
        if (component == DataComponents.TROPICAL_FISH_PATTERN)
            // Appelle une méthode
            setVariant(getVariant().withPattern((Pattern) value));
        // Embranchement : vérifie une condition
        else if (component == DataComponents.TROPICAL_FISH_BASE_COLOR)
            // Appelle une méthode
            setVariant(getVariant().withBodyColor((DyeColor) value));
        // Embranchement : vérifie une condition
        else if (component == DataComponents.TROPICAL_FISH_PATTERN_COLOR)
            // Appelle une méthode
            setVariant(getVariant().withPatternColor((DyeColor) value));
        // Branche alternative de la condition
        else super.set(component, value);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Variant(Pattern pattern, DyeColor baseColor, DyeColor patternColor) {
        // Appelle une méthode
        public static final Variant DEFAULT = new Variant(Pattern.KOB, DyeColor.WHITE, DyeColor.WHITE);

        // Début d'une méthode/d'un bloc
        public static Variant fromPackedId(int packedId) {
            // Affecte une valeur
            int patternColorId = (packedId >> 24) & 0xFF;
            // Affecte une valeur
            int bodyColorId = (packedId >> 16) & 0xFF;
            // Affecte une valeur
            int patternId = packedId & 0xFFFF;

            // Appelle une méthode
            DyeColor patternColor = DyeColor.values()[patternColorId];
            // Appelle une méthode
            DyeColor bodyColor = DyeColor.values()[bodyColorId];
            // Appelle une méthode
            Pattern pattern = Pattern.fromId(patternId);

            // Renvoie une valeur à l'appelant
            return new Variant(pattern, bodyColor, patternColor);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int packedId() {
            // Renvoie une valeur à l'appelant
            return (patternColor.ordinal() << 24)
                    // Instruction de code
                    | (baseColor.ordinal() << 16)
                    // Appelle une méthode
                    | pattern.id();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Variant withPattern(Pattern newPattern) {
            // Renvoie une valeur à l'appelant
            return new Variant(newPattern, this.baseColor, this.patternColor);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Variant withBodyColor(DyeColor newBodyColor) {
            // Renvoie une valeur à l'appelant
            return new Variant(this.pattern, newBodyColor, this.patternColor);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Variant withPatternColor(DyeColor newPatternColor) {
            // Renvoie une valeur à l'appelant
            return new Variant(this.pattern, this.baseColor, newPatternColor);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum Pattern {
        // Instruction de code
        KOB(false, 0),
        // Instruction de code
        SUNSTREAK(false, 1),
        // Instruction de code
        SNOOPER(false, 2),
        // Instruction de code
        DASHER(false, 3),
        // Instruction de code
        BRINELY(false, 4),
        // Instruction de code
        SPOTTY(false, 5),
        // Instruction de code
        FLOPPER(true, 0),
        // Instruction de code
        STRIPEY(true, 1),
        // Instruction de code
        GLITTER(true, 2),
        // Instruction de code
        BLOCKFISH(true, 3),
        // Instruction de code
        BETTY(true, 4),
        // Appelle une méthode
        CLAYFISH(true, 5);

        // Appelle une méthode
        public static final NetworkBuffer.Type<Pattern> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Pattern::fromId, Pattern::id);
        // Appelle une méthode
        public static final Codec<Pattern> CODEC = Codec.Enum(Pattern.class);

        // Appelle une méthode
        private final static Pattern[] VALUES = values();

        // Début d'une méthode/d'un bloc
        public static Pattern fromId(int id) {
            // Boucle : répète un bloc
            for (Pattern pattern : VALUES) {
                // Embranchement : vérifie une condition
                if (pattern.id() == id) {
                    // Renvoie une valeur à l'appelant
                    return pattern;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
            // Lève une exception
            throw new IllegalArgumentException("Invalid pattern id: " + id);
        // Fin d'un bloc/d'une expression
        }

        // Instruction de code
        private final int id;

        // Début d'une méthode/d'un bloc
        Pattern(boolean isLarge, int id) {
            // Accès à l'objet courant/parent
            this.id = (isLarge ? 1 : 0) | (id << 8);
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public int id() {
            // Renvoie une valeur à l'appelant
            return this.id;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
