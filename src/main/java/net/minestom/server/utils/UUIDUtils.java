// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.IntArrayBinaryTag;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.regex.Pattern;

/**
 * An utilities class for {@link UUID}.
 */
// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class UUIDUtils {
    // Appelle une méthode
    public static final Pattern UNIQUE_ID_PATTERN = Pattern.compile("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b");

    /**
     * Checks whether the {@code input} string is an {@link UUID}.
     *
     * @param input The input string to be checked
     * @return {@code true} if the input an unique identifier, otherwise {@code false}
     */
    // Début d'une méthode/d'un bloc
    public static boolean isUuid(String input) {
        // Renvoie une valeur à l'appelant
        return UNIQUE_ID_PATTERN.matcher(input).matches();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static UUID fromNbt(IntArrayBinaryTag tag) {
        // Renvoie une valeur à l'appelant
        return intArrayToUuid(tag.value());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static IntArrayBinaryTag toNbt(UUID uuid) {
        // Renvoie une valeur à l'appelant
        return IntArrayBinaryTag.intArrayBinaryTag(uuidToIntArray(uuid));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static int[] uuidToIntArray(UUID uuid) {
        // Appelle une méthode
        final long uuidMost = uuid.getMostSignificantBits();
        // Appelle une méthode
        final long uuidLeast = uuid.getLeastSignificantBits();
        // Renvoie une valeur à l'appelant
        return new int[]{
                // Instruction de code
                (int) (uuidMost >> 32),
                // Instruction de code
                (int) uuidMost,
                // Instruction de code
                (int) (uuidLeast >> 32),
                // Instruction de code
                (int) uuidLeast
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static UUID intArrayToUuid(int[] array) {
        // Affecte une valeur
        final long uuidMost = (long) array[0] << 32 | array[1] & 0xFFFFFFFFL;
        // Affecte une valeur
        final long uuidLeast = (long) array[2] << 32 | array[3] & 0xFFFFFFFFL;

        // Renvoie une valeur à l'appelant
        return new UUID(uuidMost, uuidLeast);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
