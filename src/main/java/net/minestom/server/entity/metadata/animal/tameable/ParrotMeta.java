// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

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
public class ParrotMeta extends TameableAnimalMeta {
    // Start of a method/block
    public ParrotMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#PARROT_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Color getColor() {
        // Returns a value to the caller
        return Color.VALUES[metadata.get(MetadataDef.Parrot.VARIANT)];
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#PARROT_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setColor(Color value) {
        // Calls a method
        metadata.set(MetadataDef.Parrot.VARIANT, value.ordinal());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.PARROT_VARIANT)
            // Returns a value to the caller
            return (T) getColor();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.PARROT_VARIANT)
            // Calls a method
            setColor((Color) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Color {
        // Code statement
        RED_BLUE,
        // Code statement
        BLUE,
        // Code statement
        GREEN,
        // Code statement
        YELLOW_BLUE,
        // Code statement
        GREY;

        // Calls a method
        public static final NetworkBuffer.Type<Color> NETWORK_TYPE = NetworkBuffer.Enum(Color.class);
        // Calls a method
        public static final Codec<Color> CODEC = Codec.Enum(Color.class);

        // Calls a method
        private final static Color[] VALUES = values();
    // End of a block/expression
    }

// End of a block/expression
}
