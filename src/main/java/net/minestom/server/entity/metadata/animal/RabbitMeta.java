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
public class RabbitMeta extends AnimalMeta {
    // Start of a method/block
    public RabbitMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#RABBIT_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(RabbitMeta.Variant variant) {
        // Calls a method
        int id = variant == Variant.KILLER_BUNNY ? 99 : variant.ordinal();
        // Calls a method
        metadata.set(MetadataDef.Rabbit.TYPE, id);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#RABBIT_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public RabbitMeta.Variant getVariant() {
        // Calls a method
        int id = metadata.get(MetadataDef.Rabbit.TYPE);
        // Branch: checks a condition
        if (id == 99) {
            // Returns a value to the caller
            return Variant.KILLER_BUNNY;
        // End of a block/expression
        }
        // Returns a value to the caller
        return Variant.VALUES[id];
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.RABBIT_VARIANT)
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
        if (component == DataComponents.RABBIT_VARIANT)
            // Calls a method
            setVariant((Variant) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Variant {
        // Code statement
        BROWN,
        // Code statement
        WHITE,
        // Code statement
        BLACK,
        // Code statement
        BLACK_AND_WHITE,
        // Code statement
        GOLD,
        // Code statement
        SALT_AND_PEPPER,
        // Code statement
        KILLER_BUNNY;

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
