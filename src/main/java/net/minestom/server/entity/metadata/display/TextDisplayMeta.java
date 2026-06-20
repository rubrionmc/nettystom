// Package declaration for this file
package net.minestom.server.entity.metadata.display;

// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.minestom.server.entity.Entity;
// Import of a required class
import net.minestom.server.entity.MetadataDef;
// Import of a required class
import net.minestom.server.entity.MetadataHolder;

// Type declaration (class/interface/enum/record)
public class TextDisplayMeta extends AbstractDisplayMeta {
    // Start of a method/block
    public TextDisplayMeta(Entity entity, MetadataHolder metadata) {
        // Access to the current/parent object
        super(entity, metadata);
    // End of a block/expression
    }

    // Start of a method/block
    public Component getText() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.TEXT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setText(Component value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.TEXT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getLineWidth() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.LINE_WIDTH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setLineWidth(int value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.LINE_WIDTH, value);
    // End of a block/expression
    }

    // Start of a method/block
    public int getBackgroundColor() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.BACKGROUND_COLOR);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBackgroundColor(int value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.BACKGROUND_COLOR, value);
    // End of a block/expression
    }

    // Start of a method/block
    public byte getTextOpacity() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.TEXT_OPACITY);
    // End of a block/expression
    }

    // Start of a method/block
    public void setTextOpacity(byte value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.TEXT_OPACITY, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isShadow() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.HAS_SHADOW);
    // End of a block/expression
    }

    // Start of a method/block
    public void setShadow(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.HAS_SHADOW, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isSeeThrough() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.IS_SEE_THROUGH);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSeeThrough(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.IS_SEE_THROUGH, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isUseDefaultBackground() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.USE_DEFAULT_BACKGROUND_COLOR);
    // End of a block/expression
    }

    // Start of a method/block
    public void setUseDefaultBackground(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.USE_DEFAULT_BACKGROUND_COLOR, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isAlignLeft() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.ALIGN_LEFT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAlignLeft(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.ALIGN_LEFT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isAlignRight() {
        // Returns a value to the caller
        return metadata.get(MetadataDef.TextDisplay.ALIGN_RIGHT);
    // End of a block/expression
    }

    // Start of a method/block
    public void setAlignRight(boolean value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.ALIGN_RIGHT, value);
    // End of a block/expression
    }

    // Start of a method/block
    public Alignment getAlignment() {
        // Returns a value to the caller
        return Alignment.fromId(metadata.get(MetadataDef.TextDisplay.ALIGNMENT));
    // End of a block/expression
    }

    // Start of a method/block
    public void setAlignment(Alignment value) {
        // Calls a method
        metadata.set(MetadataDef.TextDisplay.ALIGNMENT, (byte) value.ordinal());
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public enum Alignment {
        // Code statement
        CENTER,
        // Code statement
        LEFT,
        // Code statement
        RIGHT;

        // Calls a method
        private final static Alignment[] VALUES = values();

        // Start of a method/block
        private static Alignment fromId(int id) {
            // Branch: checks a condition
            if (id >= 0 && id < VALUES.length) {
                // Returns a value to the caller
                return VALUES[id];
            // End of a block/expression
            }
            // Returns a value to the caller
            return CENTER;
        // End of a block/expression
        }
    // End of a block/expression
    }

// End of a block/expression
}
