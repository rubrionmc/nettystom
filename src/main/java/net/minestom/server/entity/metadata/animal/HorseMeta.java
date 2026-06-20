// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

// Import of a required class
import net.minestom.server.codec.Codec;
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
public class HorseMeta extends AbstractHorseMeta {
    // Start of a method/block
    public HorseMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#HORSE_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Variant getVariant() {
        // Returns a value to the caller
        return getVariantFromID(metadata.get(MetadataDef.Horse.VARIANT));
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#HORSE_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(Variant variant) {
        // Calls a method
        metadata.set(MetadataDef.Horse.VARIANT, getVariantID(variant.marking, variant.color));
    // End of a block/expression
    }

    // Start of a method/block
    public static int getVariantID(Marking marking, Color color) {
        // Returns a value to the caller
        return (marking.ordinal() << 8) + color.ordinal();
    // End of a block/expression
    }

    // Start of a method/block
    public static Variant getVariantFromID(int variantID) {
        // Returns a value to the caller
        return new Variant(
                // Code statement
                Marking.VALUES[variantID >> 8],
                // Code statement
                Color.VALUES[variantID & 0xFF]
        // End of a block/expression
        );
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.HORSE_VARIANT)
            // Returns a value to the caller
            return (T) getVariant().getMarking();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.HORSE_VARIANT) {
            // Calls a method
            var variant = getVariant();
            // Calls a method
            variant.setMarking((Marking) value);
            // Calls a method
            setVariant(variant);
        // Alternative branch of the condition
        } else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public static class Variant {

        // Code statement
        private Marking marking;
        // Code statement
        private Color color;

        // Start of a method/block
        public Variant(Marking marking, Color color) {
            // Access to the current/parent object
            this.marking = marking;
            // Access to the current/parent object
            this.color = color;
        // End of a block/expression
        }

            // Start of a method/block
            public Marking getMarking() {
            // Returns a value to the caller
            return this.marking;
        // End of a block/expression
        }

        // Start of a method/block
        public void setMarking(Marking marking) {
            // Access to the current/parent object
            this.marking = marking;
        // End of a block/expression
        }

            // Start of a method/block
            public Color getColor() {
            // Returns a value to the caller
            return this.color;
        // End of a block/expression
        }

        // Start of a method/block
        public void setColor(Color color) {
            // Access to the current/parent object
            this.color = color;
        // End of a block/expression
        }

    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Marking {
        // Code statement
        NONE,
        // Code statement
        WHITE,
        // Code statement
        WHITE_FIELD,
        // Code statement
        WHITE_DOTS,
        // Code statement
        BLACK_DOTS;

        // Calls a method
        private final static Marking[] VALUES = values();
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Color {
        // Code statement
        WHITE,
        // Code statement
        CREAMY,
        // Code statement
        CHESTNUT,
        // Code statement
        BROWN,
        // Code statement
        BLACK,
        // Code statement
        GRAY,
        // Code statement
        DARK_BROWN;

        // Calls a method
        public static final NetworkBuffer.Type<Color> NETWORK_TYPE = NetworkBuffer.Enum(Color.class);
        // Calls a method
        public static final Codec<Color> NBT_TYPE = Codec.Enum(Color.class);

        // Calls a method
        private final static Color[] VALUES = values();
    // End of a block/expression
    }

// End of a block/expression
}
