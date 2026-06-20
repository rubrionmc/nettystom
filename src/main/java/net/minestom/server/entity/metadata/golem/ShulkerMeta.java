// Package declaration for this file
package net.minestom.server.entity.metadata.golem;

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
import net.minestom.server.utils.Direction;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Type declaration (class/interface/enum/record)
public class ShulkerMeta extends AbstractGolemMeta {
    // Calls a method
    private static final DyeColor[] DYE_VALUES = DyeColor.values();

    // Start of a method/block
    public ShulkerMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public Direction getAttachFace() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Shulker.ATTACH_FACE);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAttachFace(Direction value) {
        // Calls a method
        metadata.set(MetadataDef.Shulker.ATTACH_FACE, value);
    // End of a block/expression
    }

    // Start of a method/block
    public byte getShieldHeight() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.Shulker.SHIELD_HEIGHT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShieldHeight(byte value) {
        // Calls a method
        metadata.set(MetadataDef.Shulker.SHIELD_HEIGHT, value);
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SHULKER_COLOR} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public @Nullable DyeColor getColor() {
        // Calls a method
        byte index = metadata.get(MetadataDef.Shulker.COLOR);
        // Branch: checks a condition
        if (index < 0) {
            // Returns a value to the caller
            return DyeColor.WHITE;
        // Branch: checks a condition
        } else if (index < 16) {
            // Returns a value to the caller
            return DYE_VALUES[index];
        // End of a block/expression
        }
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    /**
     * @deprecated use {@link net.minestom.server.component.DataComponents#SHULKER_COLOR} instead.
     */
    // Annotation for the following element
    @Deprecated
    // Start of a method/block
    public void setColor(@Nullable DyeColor value) {
        // Calls a method
        byte index = value == null ? (byte) 16 : (byte) value.ordinal();
        // Calls a method
        metadata.set(MetadataDef.Shulker.COLOR, index);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Annotation for the following element
    @SuppressWarnings("unchecked")
    // Start of a method/block
    protected <T> @Nullable T get(DataComponent<T> component) {
        // Branch: checks a condition
        if (component == DataComponents.SHULKER_COLOR)
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
        if (component == DataComponents.SHULKER_COLOR)
            // Calls a method
            setColor((DyeColor) value);
        // Alternative branch of the condition
        else super.set(component, value);
    // End of a block/expression
    }

// End of a block/expression
}
