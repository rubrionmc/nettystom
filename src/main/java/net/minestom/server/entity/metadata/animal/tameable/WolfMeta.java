// Package declaration for this file
package net.minestom.server.entity.metadata.animal.tameable;

// Import of a required class
import net.minestom.server.color.DyeColor;
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
public class WolfMeta extends TameableAnimalMeta {
    // Start of a method/block
    public WolfMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isBegging() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Wolf.IS_BEGGING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBegging(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Wolf.IS_BEGGING, value);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_COLLAR} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public DyeColor getCollarColor() {
        // Returns a value to the caller
        return DyeColor.values()[metadata.get(MetadataDef.Wolf.COLLAR_COLOR)];
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_COLLAR} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setCollarColor(DyeColor value) {
        // Calls a method
        metadata.set(MetadataDef.Wolf.COLLAR_COLOR, value.ordinal());
    // End of a block/expression
    }

    // Start of a method/block
    public long getAngerTime() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Wolf.ANGER_TIME);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAngerTime(long value) {
        // Calls a method
        metadata.set(MetadataDef.Wolf.ANGER_TIME, value);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public RegistryKey<WolfVariant> getVariant() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Wolf.VARIANT);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(RegistryKey<WolfVariant> value) {
        // Calls a method
        metadata.set(MetadataDef.Wolf.VARIANT, value);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_SOUND_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public RegistryKey<WolfSoundVariant> getSoundVariant() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Wolf.SOUND_VARIANT);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#WOLF_SOUND_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setSoundVariant(RegistryKey<WolfSoundVariant> value) {
        // Calls a method
        metadata.set(MetadataDef.Wolf.SOUND_VARIANT, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.WOLF_VARIANT)
            // Returns a value to the caller
            return (T) getVariant();
        // Branch: checks a condition
        if (component == DataComponents.WOLF_SOUND_VARIANT)
            // Returns a value to the caller
            return (T) getSoundVariant();
        // Branch: checks a condition
        if (component == DataComponents.WOLF_COLLAR)
            // Returns a value to the caller
            return (T) getCollarColor();
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
        if (component == DataComponents.WOLF_VARIANT)
            // Calls a method
            setVariant((RegistryKey<WolfVariant>) value);
        // Branch: checks a condition
        else if (component == DataComponents.WOLF_SOUND_VARIANT)
            // Calls a method
            setSoundVariant((RegistryKey<WolfSoundVariant>) value);
        // Branch: checks a condition
        else if (component == DataComponents.WOLF_COLLAR)
            // Calls a method
            setCollarColor((DyeColor) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }
// End of a block/expression
}
