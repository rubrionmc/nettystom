// Package declaration for this file
package net.minestom.server.adventure;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.api.BinaryTagHolder;
// Import of a required class
import net.kyori.adventure.util.Codec;

// Import of a required class
import java.io.IOException;

// Type declaration (class/interface/enum/record)
public record BinaryTagHolderImpl(BinaryTag nbt) implements BinaryTagHolder {

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String string() {
        // Exception handling
        try {
            // Returns a value to the caller
            return MinestomAdventure.tagStringIO().asString(nbt);
        // Start of a method/block
        } catch (IOException e) {
            // Throws an exception
            throw new RuntimeException("Failed to convert BinaryTag to String", e);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T, DX extends Exception> T get(Codec<T, String, DX, ?> codec) throws DX {
        // Returns a value to the caller
        return codec.decode(string());
    // End of a block/expression
    }

// End of a block/expression
}
