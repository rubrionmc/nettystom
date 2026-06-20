// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.instance.light.Light;
// Import of a required class
import net.minestom.server.instance.palette.Palette;

// Type declaration (class/interface/enum/record)
public record Section(Palette blockPalette, Palette biomePalette, Light skyLight, Light blockLight) {
    // Start of a method/block
    public Section(Palette blockPalette, Palette biomePalette) {
        // Calls a method
        this(blockPalette, biomePalette, Light.sky(), Light.block());
    // End of a block/expression
    }

    // Start of a method/block
    public Section() {
        // Calls a method
        this(Palette.blocks(), Palette.biomes());
    // End of a block/expression
    }

    // Start of a method/block
    public void clear() {
        // Access to the current/parent object
        this.blockPalette.fill(0);
        // Access to the current/parent object
        this.biomePalette.fill(0);
    // End of a block/expression
    }

    // Start of a method/block
    public void invalidate() {
        // Access to the current/parent object
        this.skyLight.invalidate();
        // Access to the current/parent object
        this.blockLight.invalidate();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public Section clone() {
        // Calls a method
        final Light skyLight = Light.sky();
        // Calls a method
        final Light blockLight = Light.block();

        // Calls a method
        skyLight.set(this.skyLight.array());
        // Calls a method
        blockLight.set(this.blockLight.array());

        // Returns a value to the caller
        return new Section(this.blockPalette.clone(), this.biomePalette.clone(), skyLight, blockLight);
    // End of a block/expression
    }

    // Start of a method/block
    public void setSkyLight(byte[] copyArray) {
        // Access to the current/parent object
        this.skyLight.set(copyArray);
    // End of a block/expression
    }

    // Start of a method/block
    public void setBlockLight(byte[] copyArray) {
        // Access to the current/parent object
        this.blockLight.set(copyArray);
    // End of a block/expression
    }
// End of a block/expression
}
