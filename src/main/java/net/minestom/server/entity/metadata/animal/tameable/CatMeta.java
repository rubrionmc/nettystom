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
public class CatMeta extends TameableAnimalMeta {
    // Calls a method
    private static final DyeColor[] DYE_VALUES = DyeColor.values();

    // Start of a method/block
    public CatMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CAT_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public RegistryKey<CatVariant> getVariant() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Cat.VARIANT);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CAT_VARIANT} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setVariant(RegistryKey<CatVariant> value) {
        // Calls a method
        metadata.set(MetadataDef.Cat.VARIANT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isLying() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Cat.IS_LYING);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLying(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Cat.IS_LYING, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isRelaxed() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Cat.IS_RELAXED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setRelaxed(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Cat.IS_RELAXED, value);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CAT_COLLAR} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public DyeColor getCollarColor() {
        // Returns a value to the caller
        return DYE_VALUES[metadata.get(MetadataDef.Cat.COLLAR_COLOR)];
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#CAT_COLLAR} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setCollarColor(DyeColor value) {
        // Calls a method
        metadata.set(MetadataDef.Cat.COLLAR_COLOR, value.ordinal());
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.CAT_VARIANT)
            // Returns a value to the caller
            return (T) getVariant();
        // Branch: checks a condition
        if (component == DataComponents.CAT_SOUND_VARIANT)
            // Returns a value to the caller
            return (T) metadata.get(MetadataDef.Cat.SOUND_VARIANT);
        // Branch: checks a condition
        if (component == DataComponents.CAT_COLLAR)
            // Returns a value to the caller
            return (T) getCollarColor();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.CAT_VARIANT)
            // Calls a method
            setVariant((RegistryKey<CatVariant>) value);
        // Branch: checks a condition
        else if (component == DataComponents.CAT_SOUND_VARIANT) {
            // Calls a method
            metadata.set(MetadataDef.Cat.SOUND_VARIANT, (RegistryKey<CatSoundVariant>) value);
        // Branch: checks a condition
        } else if (component == DataComponents.CAT_COLLAR)
            // Calls a method
            setCollarColor((DyeColor) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

// End of a block/expression
}
