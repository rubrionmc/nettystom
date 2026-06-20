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
public class PigMeta extends AnimalMeta {
    // Start of a method/block
    public PigMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public int getTimeToBoost() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Pig.BOOST_TIME);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTimeToBoost(int value) {
        // Calls a method
        metadata.set(MetadataDef.Pig.BOOST_TIME, value);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#PIG_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public RegistryKey<PigVariant> getVariant() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Pig.VARIANT);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#PIG_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(RegistryKey<PigVariant> value) {
        // Calls a method
        metadata.set(MetadataDef.Pig.VARIANT, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.PIG_VARIANT)
            // Returns a value to the caller
            return (T) getVariant();
        // Branch: checks a condition
        if (component == DataComponents.PIG_SOUND_VARIANT)
            // Returns a value to the caller
            return (T) metadata.get(MetadataDef.Pig.SOUND_VARIANT);
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
        if (component == DataComponents.PIG_VARIANT)
            // Calls a method
            setVariant((RegistryKey<PigVariant>) value);
        // Branch: checks a condition
        else if (component == DataComponents.PIG_SOUND_VARIANT)
            // Calls a method
            metadata.set(MetadataDef.Pig.SOUND_VARIANT, (RegistryKey<PigSoundVariant>) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

// End of a block/expression
}
