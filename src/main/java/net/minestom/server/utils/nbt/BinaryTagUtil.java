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
        // Embranchement : vérifie une condition
        if (tag instanceof ByteBinaryTag byteTag) {
            // Renvoie une valeur à l'appelant
            return byteTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof ShortBinaryTag shortTag) {
            // Renvoie une valeur à l'appelant
            return shortTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof IntBinaryTag intTag) {
            // Renvoie une valeur à l'appelant
            return intTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof LongBinaryTag longTag) {
            // Renvoie une valeur à l'appelant
            return longTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof FloatBinaryTag floatTag) {
            // Renvoie une valeur à l'appelant
            return floatTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof DoubleBinaryTag doubleTag) {
            // Renvoie une valeur à l'appelant
            return doubleTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof ByteArrayBinaryTag byteArrayTag) {
            // Renvoie une valeur à l'appelant
            return byteArrayTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof StringBinaryTag stringTag) {
            // Renvoie une valeur à l'appelant
            return stringTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof IntArrayBinaryTag intArrayTag) {
            // Renvoie une valeur à l'appelant
            return intArrayTag.value();
        // Embranchement : vérifie une condition
        } else if (tag instanceof LongArrayBinaryTag longArrayTag) {
            // Renvoie une valeur à l'appelant
            return longArrayTag.value();
        // Branche alternative de la condition
        } else {
            // Lève une exception
            throw new UnsupportedOperationException("Unsupported NBT type: " + tag.getClass());
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private BinaryTagUtil() {
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
