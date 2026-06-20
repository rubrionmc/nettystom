// Package declaration for this file
package net.minestom.server.entity.metadata.water;

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
import net.minestom.server.entity.metadata.animal.AnimalMeta;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class AxolotlMeta extends AnimalMeta {
    // Start of a method/block
    public AxolotlMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#AXOLOTL_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public Variant getVariant() {
        // Returns a value to the caller
        return Variant.VALUES[metadata.get(MetadataDef.Axolotl.VARIANT)];
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#AXOLOTL_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(Variant variant) {
        // Calls a method
        metadata.set(MetadataDef.Axolotl.VARIANT, variant.ordinal());
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isPlayingDead() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Axolotl.IS_PLAYING_DEAD);
    // End of a block/expression
    }

    // Start of a method/block
    public void setPlayingDead(boolean playingDead) {
        // Calls a method
        metadata.set(MetadataDef.Axolotl.IS_PLAYING_DEAD, playingDead);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFromBucket() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Axolotl.IS_FROM_BUCKET);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFromBucket(boolean fromBucket) {
        // Calls a method
        metadata.set(MetadataDef.Axolotl.IS_FROM_BUCKET, fromBucket);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.AXOLOTL_VARIANT)
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
        if (component == DataComponents.AXOLOTL_VARIANT)
            // Calls a method
            setVariant((Variant) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Variant {
        // Code statement
        LUCY,
        // Code statement
        WILD,
        // Code statement
        GOLD,
        // Code statement
        CYAN,
        // Code statement
        BLUE;

        // Calls a method
        public static final NetworkBuffer.Type<Variant> NETWORK_TYPE = NetworkBuffer.Enum(Variant.class);
        // Calls a method
        public static final Codec<Variant> CODEC = Codec.Enum(Variant.class);

        // Calls a method
        private final static AxolotlMeta.Variant[] VALUES = values();
    // End of a block/expression
    }
// End of a block/expression
}
