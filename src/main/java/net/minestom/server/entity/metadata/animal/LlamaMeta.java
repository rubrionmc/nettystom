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
public class LlamaMeta extends ChestedHorseMeta {
    // Start of a method/block
    public LlamaMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getStrength() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Llama.STRENGTH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setStrength(int value) {
        // Calls a method
        metadata.set(MetadataDef.Llama.STRENGTH, value);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#LLAMA_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Variant getVariant() {
        // Returns a value to the caller
        return Variant.VALUES[metadata.get(MetadataDef.Llama.VARIANT)];
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#LLAMA_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(Variant value) {
        // Calls a method
        metadata.set(MetadataDef.Llama.VARIANT, value.ordinal());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.LLAMA_VARIANT)
            // Returns a value to the caller
            return (T) getVariant();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.LLAMA_VARIANT)
            // Calls a method
            setVariant((Variant) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Variant {
        // Code statement
        CREAMY,
        // Code statement
        WHITE,
        // Code statement
        BROWN,
        // Code statement
        GRAY;

        // Calls a method
        public static final NetworkBuffer.Type<Variant> NETWORK_TYPE = NetworkBuffer.Enum(Variant.class);
        // Calls a method
        public static final Codec<Variant> CODEC = Codec.Enum(Variant.class);

        // Calls a method
        private final static Variant[] VALUES = values();
    // End of a block/expression
    }

// End of a block/expression
}
