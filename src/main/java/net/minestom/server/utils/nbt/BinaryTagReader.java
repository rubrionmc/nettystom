// Package declaration for this file
package net.minestom.server.utils.nbt;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTagType;
// Import of a required class
import net.kyori.adventure.nbt.BinaryTagTypes;

// Import of a required class
import java.io.DataInput;
// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.Map;

// Based on net.kyori.adventure.nbt.BinaryTagReaderImpl licensed under the MIT license.
// https://github.com/KyoriPowered/adventure/blob/main/4/nbt/src/main/java/net/kyori/adventure/nbt/BinaryTagReaderImpl.java
// Type declaration (class/interface/enum/record)
public class BinaryTagReader {

    // Start of a method/block
    static {
        // Code statement
        BinaryTagTypes.COMPOUND.id(); // Force initialization
    // End of a block/expression
    }

    // Code statement
    private final DataInput input;

    // Start of a method/block
    public BinaryTagReader(DataInput input) {
        // Access to the current/parent object
        this.input = input;
    // End of a block/expression
    }

    // Start of a method/block
    public BinaryTag readNameless() throws IOException {
        // Calls a method
        BinaryTagType<? extends BinaryTag> type = BinaryTagUtil.nbtTypeFromId(input.readByte());
        // Returns a value to the caller
        return type.read(input);
    // End of a block/expression
    }

    // Start of a method/block
    public Map.Entry<String, BinaryTag> readNamed() throws IOException {
        // Calls a method
        BinaryTagType<? extends BinaryTag> type = BinaryTagUtil.nbtTypeFromId(input.readByte());
        // Calls a method
        String name = input.readUTF();
        // Returns a value to the caller
        return Map.entry(name, type.read(input));
    // End of a block/expression
    }
// End of a block/expression
}
