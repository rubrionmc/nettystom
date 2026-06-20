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

// Import of a required class
import java.util.UUID;

// Type declaration (class/interface/enum/record)
public class FoxMeta extends AnimalMeta {
    // Start of a method/block
    public FoxMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }


    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#FOX_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public FoxMeta.Variant getVariant() {
        // Returns a value to the caller
        return Variant.VALUES[metadata.get(MetadataDef.Fox.VARIANT)];
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#FOX_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(FoxMeta.Variant variant) {
        // Calls a method
        metadata.set(MetadataDef.Fox.VARIANT, variant.ordinal());
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSitting() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.IS_SITTING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSitting(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.IS_SITTING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFoxSneaking() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.IS_CROUCHING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFoxSneaking(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.IS_CROUCHING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isInterested() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.IS_INTERESTED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setInterested(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.IS_INTERESTED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isPouncing() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.IS_POUNCING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setPouncing(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.IS_POUNCING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSleeping() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.IS_SLEEPING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSleeping(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.IS_SLEEPING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isFaceplanted() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.IS_FACEPLANTED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFaceplanted(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.IS_FACEPLANTED, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isDefending() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.IS_DEFENDING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setDefending(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.IS_DEFENDING, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public UUID getFirstUUID() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.FIRST_UUID);
    // End of a block/expression
    }

    // Start of a method/block
    public void setFirstUUID(@Nullable UUID value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.FIRST_UUID, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public UUID getSecondUUID() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Fox.SECOND_UUID);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSecondUUID(@Nullable UUID value) {
        // Calls a method
        metadata.set(MetadataDef.Fox.SECOND_UUID, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.FOX_VARIANT)
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
        if (component == DataComponents.FOX_VARIANT)
            // Calls a method
            setVariant((FoxMeta.Variant) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Variant {
        // Code statement
        RED,
        // Code statement
        SNOW;

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
