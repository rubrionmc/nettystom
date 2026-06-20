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
public class ZombieNautilusMeta extends AbstractNautilusMeta {
    // Start of a method/block
    public ZombieNautilusMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#ZOMBIE_NAUTILUS_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public RegistryKey<ZombieNautilusVariant> getVariant() {
        // Returns a value to the caller
        return this.metadata.get(MetadataDef.ZombieNautilus.VARIANT);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#ZOMBIE_NAUTILUS_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(RegistryKey<ZombieNautilusVariant> value) {
        // Access to the current/parent object
        this.metadata.set(MetadataDef.ZombieNautilus.VARIANT, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (DataComponents.ZOMBIE_NAUTILUS_VARIANT == component) {
            // Calls a method
            setVariant((RegistryKey<ZombieNautilusVariant>) value);
        // Alternative branch of the condition
        } else super.set(component, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected @Nullable <T> T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (DataComponents.ZOMBIE_NAUTILUS_VARIANT == component) {
            // Returns a value to the caller
            return (T) getVariant();
        // Alternative branch of the condition
        } else return super.get(component);
    // End of a block/expression
    }
// End of a block/expression
}
