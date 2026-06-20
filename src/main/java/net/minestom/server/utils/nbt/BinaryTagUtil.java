// Package declaration for this file
package net.minestom.server.utils.nbt;

// Import of a required class
import net.kyori.adventure.nbt.*;
// Import of a required class
import net.minestom.server.utils.validate.Check;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class BinaryTagUtil {
    // Assigns a value
    private static final BinaryTagType<?>[] TYPES = new BinaryTagType[]{
            // Code statement
            BinaryTagTypes.END,
            // Code statement
            BinaryTagTypes.BYTE,
            // Code statement
            BinaryTagTypes.SHORT,
            // Code statement
            BinaryTagTypes.INT,
            // Code statement
            BinaryTagTypes.LONG,
            // Code statement
            BinaryTagTypes.FLOAT,
            // Code statement
            BinaryTagTypes.DOUBLE,
            // Code statement
            BinaryTagTypes.BYTE_ARRAY,
            // Code statement
            BinaryTagTypes.STRING,
            // Code statement
            BinaryTagTypes.LIST,
            // Code statement
            BinaryTagTypes.COMPOUND,
            // Code statement
            BinaryTagTypes.INT_ARRAY,
            // Code statement
            BinaryTagTypes.LONG_ARRAY,
    // End of a block/expression
    };

    // Start of a method/block
    public static BinaryTagType<?> nbtTypeFromId(byte id) {
        // Calls a method
        Check.argCondition(id < 0 || id >= TYPES.length, "Invalid NBT type id: " + id);
        // Returns a value to the caller
        return TYPES[id];
    // End of a block/expression
    }

    // Start of a method/block
    public static Object nbtValueFromTag(BinaryTag tag) {
        // Returns a value to the caller
        return switch (tag) {
            // Multiple branching (switch/case)
            case ByteBinaryTag byteTag -> byteTag.value();
            // Multiple branching (switch/case)
            case ShortBinaryTag shortTag -> shortTag.value();
            // Multiple branching (switch/case)
            case IntBinaryTag intTag -> intTag.value();
            // Multiple branching (switch/case)
            case LongBinaryTag longTag -> longTag.value();
            // Multiple branching (switch/case)
            case FloatBinaryTag floatTag -> floatTag.value();
            // Multiple branching (switch/case)
            case DoubleBinaryTag doubleTag -> doubleTag.value();
            // Multiple branching (switch/case)
            case ByteArrayBinaryTag byteArrayTag -> byteArrayTag.value();
            // Multiple branching (switch/case)
            case StringBinaryTag stringTag -> stringTag.value();
            // Multiple branching (switch/case)
            case IntArrayBinaryTag intArrayTag -> intArrayTag.value();
            // Multiple branching (switch/case)
            case LongArrayBinaryTag longArrayTag -> longArrayTag.value();
            // Multiple branching (switch/case)
            default -> throw new UnsupportedOperationException("Unsupported NBT type: " + tag.getClass());
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    private BinaryTagUtil() {
    // End of a block/expression
    }
// End of a block/expression
}
