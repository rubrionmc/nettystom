// Package declaration for this file
package net.minestom.server.utils.nbt;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTagType;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTagTypes;

// Import of a required class
import java.io.DataOutput;
// Import of a required class
import java.io.IOException;

// Based on net.kyori.adventure.nbt.BinaryTagWriterImpl licensed under the MIT license.
// https://github.com/KyoriPowered/adventure/blob/main/4/nbt/src/main/java/net/kyori/adventure/nbt/BinaryTagWriterImpl.java
// Type declaration (class/interface/enum/record)
public class BinaryTagWriter {

    // Start of a method/block
    static {
        // Code statement
        BinaryTagTypes.COMPOUND.id(); // Force initialization
    // End of a block/expression
    }

    // Code statement
    private final DataOutput output;

    // Start of a method/block
    public BinaryTagWriter(DataOutput output) {
        // Access to the current/parent object
        this.output = output;
    // End of a block/expression
    }

    // Start of a method/block
    public void writeNameless(BinaryTag tag) throws IOException {
        //noinspection unchecked
        // Calls a method
        BinaryTagType<BinaryTag> type = (BinaryTagType<BinaryTag>) tag.type();
        // Calls a method
        output.writeByte(type.id());
        // Calls a method
        type.write(tag, output);
    // End of a block/expression
    }

    // Start of a method/block
    public void writeNamed(String name, BinaryTag tag) throws IOException {
        //noinspection unchecked
        // Calls a method
        BinaryTagType<BinaryTag> type = (BinaryTagType<BinaryTag>) tag.type();
        // Calls a method
        output.writeByte(type.id());
        // Calls a method
        output.writeUTF(name);
        // Calls a method
        type.write(tag, output);
    // End of a block/expression
    }
// End of a block/expression
}
