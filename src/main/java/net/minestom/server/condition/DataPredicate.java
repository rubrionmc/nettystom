// Package declaration for this file
package net.minestom.server.condition;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.minestom.server.codec.Codec;

// Type declaration (class/interface/enum/record)
public interface DataPredicate {
    // Type declaration (class/interface/enum/record)
    record Noop(BinaryTag content) implements DataPredicate {

    // End of a block/expression
    }

    // TODO
    // Calls a method
    Codec<DataPredicate> NBT_TYPE = Codec.NBT.transform(Noop::new, value -> ((Noop) value).content);
// End of a block/expression
}
