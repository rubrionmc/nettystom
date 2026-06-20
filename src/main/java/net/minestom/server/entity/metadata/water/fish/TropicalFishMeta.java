// Package declaration for this file
package net.minestom.server.entity.metadata.water.fish;

// Import of a required class
import net.minestom.server.codec.Codec;
// Import of a required class
import net.minestom.server.color.DyeColor;
// Import of a required class
import net.minestom.server.component.DataComponent;
// Import of a required class
import net.minestom.server.component.DataComponents;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class TropicalFishMeta extends AbstractFishMeta {
    // Start of a method/block
    public TropicalFishMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Variant getVariant() {
        // Returns a value to the caller
        return Variant.fromPackedId(metadata.get(MetadataDef.TropicalFish.VARIANT));
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(Variant variant) {
        // Calls a method
        metadata.set(MetadataDef.TropicalFish.VARIANT, variant.packedId());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.TROPICAL_FISH_PATTERN)
            // Returns a value to the caller
            return (T) getVariant().pattern();
        // Branch: checks a condition
        if (component == DataComponents.TROPICAL_FISH_BASE_COLOR)
            // Returns a value to the caller
            return (T) getVariant().baseColor();
        // Branch: checks a condition
        if (component == DataComponents.TROPICAL_FISH_PATTERN_COLOR)
            // Returns a value to the caller
            return (T) getVariant().patternColor();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.TROPICAL_FISH_PATTERN)
            // Calls a method
            setVariant(getVariant().withPattern((Pattern) value));
        // Branch: checks a condition
        else if (component == DataComponents.TROPICAL_FISH_BASE_COLOR)
            // Calls a method
            setVariant(getVariant().withBodyColor((DyeColor) value));
        // Branch: checks a condition
        else if (component == DataComponents.TROPICAL_FISH_PATTERN_COLOR)
            // Calls a method
            setVariant(getVariant().withPatternColor((DyeColor) value));
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Variant(Pattern pattern, DyeColor baseColor, DyeColor patternColor) {
        // Calls a method
        public static final Variant DEFAULT = new Variant(Pattern.KOB, DyeColor.WHITE, DyeColor.WHITE);

        // Start of a method/block
        public static Variant fromPackedId(int packedId) {
            // Calls a method
            int patternColorId = (packedId >> 24) & 0xFF;
            // Calls a method
            int bodyColorId = (packedId >> 16) & 0xFF;
            // Assigns a value
            int patternId = packedId & 0xFFFF;

            // Calls a method
            DyeColor patternColor = DyeColor.values()[patternColorId];
            // Calls a method
            DyeColor bodyColor = DyeColor.values()[bodyColorId];
            // Calls a method
            Pattern pattern = Pattern.fromId(patternId);

            // Returns a value to the caller
            return new Variant(pattern, bodyColor, patternColor);
        // End of a block/expression
        }

        // Start of a method/block
        public int packedId() {
            // Returns a value to the caller
            return (patternColor.ordinal() << 24)
                    // Code statement
                    | (baseColor.ordinal() << 16)
                    // Calls a method
                    | pattern.id();
        // End of a block/expression
        }

        // Start of a method/block
        public Variant withPattern(Pattern newPattern) {
            // Returns a value to the caller
            return new Variant(newPattern, this.baseColor, this.patternColor);
        // End of a block/expression
        }

        // Start of a method/block
        public Variant withBodyColor(DyeColor newBodyColor) {
            // Returns a value to the caller
            return new Variant(this.pattern, newBodyColor, this.patternColor);
        // End of a block/expression
        }

        // Start of a method/block
        public Variant withPatternColor(DyeColor newPatternColor) {
            // Returns a value to the caller
            return new Variant(this.pattern, this.baseColor, newPatternColor);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Pattern {
        // Code statement
        KOB(false, 0),
        // Code statement
        SUNSTREAK(false, 1),
        // Code statement
        SNOOPER(false, 2),
        // Code statement
        DASHER(false, 3),
        // Code statement
        BRINELY(false, 4),
        // Code statement
        SPOTTY(false, 5),
        // Code statement
        FLOPPER(true, 0),
        // Code statement
        STRIPEY(true, 1),
        // Code statement
        GLITTER(true, 2),
        // Code statement
        BLOCKFISH(true, 3),
        // Code statement
        BETTY(true, 4),
        // Calls a method
        CLAYFISH(true, 5);

        // Calls a method
        public static final NetworkBuffer.Type<Pattern> NETWORK_TYPE = NetworkBuffer.VAR_INT.transform(Pattern::fromId, Pattern::id);
        // Calls a method
        public static final Codec<Pattern> CODEC = Codec.Enum(Pattern.class);

        // Calls a method
        private final static Pattern[] VALUES = values();

        // Start of a method/block
        public static Pattern fromId(int id) {
            // Loop: repeats a block
            for (Pattern pattern : VALUES) {
                // Branch: checks a condition
                if (pattern.id() == id) {
                    // Returns a value to the caller
                    return pattern;
                // End of a block/expression
                }
            // End of a block/expression
            }
            // Throws an exception
            throw new IllegalArgumentException("Invalid pattern id: " + id);
        // End of a block/expression
        }

        // Code statement
        private final int id;

        // Start of a method/block
        Pattern(boolean isLarge, int id) {
            // Access to the current/parent object
            this.id = (isLarge ? 1 : 0) | (id << 8);
        // End of a block/expression
        }

        // Start of a method/block
        public int id() {
            // Returns a value to the caller
            return this.id;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
