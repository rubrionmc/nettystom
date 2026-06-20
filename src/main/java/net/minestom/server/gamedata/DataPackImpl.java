// Package declaration for this file
package net.minestom.server.gamedata;

// Import of a required class
import net.kyori.adventure.key.Key;

// Type declaration (class/interface/enum/record)
record DataPackImpl(Key key, boolean isSynced) implements DataPack {

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean isSynced() {
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }
// End of a block/expression
}
