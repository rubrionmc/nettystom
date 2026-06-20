// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

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
import net.minestom.server.registry.RegistryKey;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class FrogMeta extends AnimalMeta {
    // Start of a method/block
    public FrogMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#FROG_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public RegistryKey<FrogVariant> getVariant() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Frog.VARIANT);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#FROG_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(RegistryKey<FrogVariant> value) {
        // Calls a method
        metadata.set(MetadataDef.Frog.VARIANT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable Integer getTongueTarget() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Frog.TONGUE_TARGET);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTongueTarget(@Nullable Integer value) {
        // Calls a method
        metadata.set(MetadataDef.Frog.TONGUE_TARGET, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.FROG_VARIANT)
            // Returns a value to the caller
            return (T) getVariant();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.FROG_VARIANT)
            // Calls a method
            setVariant((RegistryKey<FrogVariant>) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }
// End of a block/expression
}
