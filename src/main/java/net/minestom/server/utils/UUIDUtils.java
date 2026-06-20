// Package declaration for this file
package net.minestom.server.utils;

// Import of a required class
import net.kyori.adventure.nbt.IntArrayBinaryTag;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.regex.Pattern;

/**
 * An utilities class for {@link UUID}.
 */
// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class UUIDUtils {
    // Calls a method
    public static final Pattern UNIQUE_ID_PATTERN = Pattern.compile("\\b[0-9a-f]{8}\\b-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-\\b[0-9a-f]{12}\\b");

    /**
     * Checks whether the {@code input} string is an {@link UUID}.
     *
     * @param input The input string to be checked
     * @return {@code true} if the input an unique identifier, otherwise {@code false}
     */
    // Start of a method/block
    public static boolean isUuid(String input) {
        // Returns a value to the caller
        return UNIQUE_ID_PATTERN.matcher(input).matches();
    // End of a block/expression
    }

    // Start of a method/block
    public static UUID fromNbt(IntArrayBinaryTag tag) {
        // Returns a value to the caller
        return intArrayToUuid(tag.value());
    // End of a block/expression
    }

    // Start of a method/block
    public static IntArrayBinaryTag toNbt(UUID uuid) {
        // Returns a value to the caller
        return IntArrayBinaryTag.intArrayBinaryTag(uuidToIntArray(uuid));
    // End of a block/expression
    }

    // Start of a method/block
    public static int[] uuidToIntArray(UUID uuid) {
        // Calls a method
        final long uuidMost = uuid.getMostSignificantBits();
        // Calls a method
        final long uuidLeast = uuid.getLeastSignificantBits();
        // Returns a value to the caller
        return new int[]{
                // Code statement
                (int) (uuidMost >> 32),
                // Code statement
                (int) uuidMost,
                // Code statement
                (int) (uuidLeast >> 32),
                // Code statement
                (int) uuidLeast
        // End of a block/expression
        };
    // End of a block/expression
    }

    // Start of a method/block
    public static UUID intArrayToUuid(int[] array) {
        // Calls a method
        final long uuidMost = (long) array[0] << 32 | array[1] & 0xFFFFFFFFL;
        // Calls a method
        final long uuidLeast = (long) array[2] << 32 | array[3] & 0xFFFFFFFFL;

        // Returns a value to the caller
        return new UUID(uuidMost, uuidLeast);
    // End of a block/expression
    }
// End of a block/expression
}
