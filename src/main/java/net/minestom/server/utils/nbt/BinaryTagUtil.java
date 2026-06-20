// Déclaration du paquet de ce fichier
package net.minestom.server.utils.nbt;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.*;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class BinaryTagUtil {
    // Affecte une valeur
    private static final BinaryTagType<?>[] TYPES = new BinaryTagType[]{
            // Instruction de code
            BinaryTagTypes.END,
            // Instruction de code
            BinaryTagTypes.BYTE,
            // Instruction de code
            BinaryTagTypes.SHORT,
            // Instruction de code
            BinaryTagTypes.INT,
            // Instruction de code
            BinaryTagTypes.LONG,
            // Instruction de code
            BinaryTagTypes.FLOAT,
            // Instruction de code
            BinaryTagTypes.DOUBLE,
            // Instruction de code
            BinaryTagTypes.BYTE_ARRAY,
            // Instruction de code
            BinaryTagTypes.STRING,
            // Instruction de code
            BinaryTagTypes.LIST,
            // Instruction de code
            BinaryTagTypes.COMPOUND,
            // Instruction de code
            BinaryTagTypes.INT_ARRAY,
            // Instruction de code
            BinaryTagTypes.LONG_ARRAY,
    // Fin d'un bloc/d'une expression
    };

    // Début d'une méthode/d'un bloc
    public static BinaryTagType<?> nbtTypeFromId(byte id) {
        // Appelle une méthode
        Check.argCondition(id < 0 || id >= TYPES.length, "Invalid NBT type id: " + id);
        // Renvoie une valeur à l'appelant
        return TYPES[id];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static Object nbtValueFromTag(BinaryTag tag) {
        // Renvoie une valeur à l'appelant
        return switch (tag) {
            // Embranchement multiple (switch/case)
            case ByteBinaryTag byteTag -> byteTag.value();
            // Embranchement multiple (switch/case)
            case ShortBinaryTag shortTag -> shortTag.value();
            // Embranchement multiple (switch/case)
            case IntBinaryTag intTag -> intTag.value();
            // Embranchement multiple (switch/case)
            case LongBinaryTag longTag -> longTag.value();
            // Embranchement multiple (switch/case)
            case FloatBinaryTag floatTag -> floatTag.value();
            // Embranchement multiple (switch/case)
            case DoubleBinaryTag doubleTag -> doubleTag.value();
            // Embranchement multiple (switch/case)
            case ByteArrayBinaryTag byteArrayTag -> byteArrayTag.value();
            // Embranchement multiple (switch/case)
            case StringBinaryTag stringTag -> stringTag.value();
            // Embranchement multiple (switch/case)
            case IntArrayBinaryTag intArrayTag -> intArrayTag.value();
            // Embranchement multiple (switch/case)
            case LongArrayBinaryTag longArrayTag -> longArrayTag.value();
            // Embranchement multiple (switch/case)
            default -> throw new UnsupportedOperationException("Unsupported NBT type: " + tag.getClass());
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private BinaryTagUtil() {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
