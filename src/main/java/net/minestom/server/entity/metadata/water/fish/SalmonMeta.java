// Package declaration for this file
package net.minestom.server.entity.metadata.water.fish;

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

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.stream.Collectors;

// Type declaration (class/interface/enum/record)
public class SalmonMeta extends AbstractFishMeta {
    // Start of a method/block
    public SalmonMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SALMON_SIZE} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public SalmonMeta.Size getSize() {
        // Returns a value to the caller
        return Size.VALUES[metadata.get(MetadataDef.Salmon.SIZE)];
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SALMON_SIZE} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setSize(SalmonMeta.Size size) {
        // Calls a method
        metadata.set(MetadataDef.Salmon.SIZE, size.ordinal());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.SALMON_SIZE)
            // Returns a value to the caller
            return (T) getSize();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.SALMON_SIZE)
            // Calls a method
            setSize((SalmonMeta.Size) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Size {
        // Code statement
        SMALL("small"),
        // Code statement
        MEDIUM("medium"),
        // Calls a method
        LARGE("large");

        // Calls a method
        private static final Size[] VALUES = values();

        // Calls a method
        public static final NetworkBuffer.Type<Size> NETWORK_TYPE = NetworkBuffer.Enum(Size.class);
        // Calls a method
        public static final Codec<Size> CODEC = Codec.Enum(Size.class);

        // Assigns a value
        private static final Map<String, Size> BY_ID = Arrays.stream(values())
                // Calls a method
                .collect(Collectors.toMap(Size::id, (size) -> size));

        // Code statement
        private final String id;

        // Start of a method/block
        Size(String id) {
            // Access to the current/parent object
            this.id = id;
        // End of a block/expression
        }

        // Start of a method/block
        public String id() {
            // Returns a value to the caller
            return id;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
