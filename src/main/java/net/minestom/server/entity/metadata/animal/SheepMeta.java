// Package declaration for this file
package net.minestom.server.entity.metadata.animal;

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
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class SheepMeta extends AnimalMeta {
    // Calls a method
    private static final DyeColor[] DYE_VALUES = DyeColor.values();

    // Start of a method/block
    public SheepMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SHEEP_COLOR} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public DyeColor getColor() {
        // Returns a value to the caller
        return DYE_VALUES[metadata.get(MetadataDef.Sheep.COLOR_ID)];
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SHEEP_COLOR} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setColor(DyeColor color) {
        // Calls a method
        metadata.set(MetadataDef.Sheep.COLOR_ID, (byte) color.ordinal());
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSheared() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Sheep.IS_SHEARED);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSheared(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.Sheep.IS_SHEARED, value);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.SHEEP_COLOR)
            // Returns a value to the caller
            return (T) getColor();
        // Returns a value to the caller
        return super.get(component);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    protected <T> void set(DataComponent<T> component, T value) {
        // Branch: checks a condition
        if (component == DataComponents.SHEEP_COLOR)
            // Calls a method
            setColor((DyeColor) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

// End of a block/expression
}
