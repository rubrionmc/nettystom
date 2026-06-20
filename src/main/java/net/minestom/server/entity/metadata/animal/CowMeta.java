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
public class CowMeta extends AnimalMeta {
    // Start of a method/block
    public CowMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#COW_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public RegistryKey<CowVariant> getVariant() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Cow.VARIANT);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#COW_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(RegistryKey<CowVariant> variant) {
        // Calls a method
        metadata.set(MetadataDef.Cow.VARIANT, variant);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.COW_VARIANT)
            // Returns a value to the caller
            return (T) getVariant();
        // Branch: checks a condition
        if (component == DataComponents.COW_SOUND_VARIANT)
            // Returns a value to the caller
            return (T) metadata.get(MetadataDef.Cow.SOUND_VARIANT);
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
        if (component == DataComponents.COW_VARIANT)
            // Calls a method
            setVariant((RegistryKey<CowVariant>) value);
        // Branch: checks a condition
        else if (component == DataComponents.COW_SOUND_VARIANT)
            // Calls a method
            metadata.set(MetadataDef.Cow.SOUND_VARIANT, (RegistryKey<CowSoundVariant>) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

// End of a block/expression
}
