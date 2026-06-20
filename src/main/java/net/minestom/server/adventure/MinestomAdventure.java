// Package declaration for this file
package net.minestom.server.adventure;

// Import of a required class
import net.kyori.adventure.nbt.BinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import of a required class
import net.kyori.adventure.nbt.TagStringIO;
// Import of a required class
import net.kyori.adventure.nbt.api.BinaryTagHolder;
// Import of a required class
import net.kyori.adventure.text.Component;
// Import of a required class
import net.kyori.adventure.translation.GlobalTranslator;
// Import of a required class
import net.kyori.adventure.util.Codec;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.function.BiFunction;

/**
 * Adventure related constants, etc.
 */
// Type declaration (class/interface/enum/record)
public final class MinestomAdventure {
    /**
     * See {@link MinestomAdventure#tagStringIO()}
     */
    // Assigns a value
    private static final TagStringIO tagStringIO = TagStringIO.builder()
            // Code statement
            .emitHeterogeneousLists(true)
            // Code statement
            .acceptHeterogeneousLists(true)
            // Calls a method
            .build();

    /**
     * A codec to convert between strings and NBT.
     */
    // Code statement
    public static final Codec<CompoundBinaryTag, String, IOException, IOException> NBT_CODEC
            // Calls a method
            = Codec.codec(tagStringIO::asCompound, tagStringIO::asString);

    // todo: Need to properly add a translator interface so it can check for presence of a key for the flattener.
    // Assigns a value
    public static BiFunction<Component, Locale, Component> COMPONENT_TRANSLATOR = GlobalTranslator::render;

    // Calls a method
    private static Locale defaultLocale = Locale.getDefault();

    // Start of a method/block
    private MinestomAdventure() {
    // End of a block/expression
    }

    /**
     * Gets the {@link TagStringIO} instance used to convert SNBT.
     * This instance should be used for all Adventure related SNBT parsing and serialization.
     * Note: This instance of the {@link TagStringIO} is configured to accept and emit heterogeneous lists
     *
     * @return the tag string IO instance
     */
    // Start of a method/block
    public static TagStringIO tagStringIO() {
        // Returns a value to the caller
        return tagStringIO;
    // End of a block/expression
    }

    /**
     * Gets the default locale used to translate components when no overriding locale has been provided.
     *
     * @return the default locale
     */
    // Start of a method/block
    public static Locale getDefaultLocale() {
        // Returns a value to the caller
        return defaultLocale;
    // End of a block/expression
    }

    /**
     * Sets the default locale used to translate components when no overriding locale has been provided.
     *
     * @param defaultLocale the new default, or {@code null} to return to {@link Locale#getDefault()}
     */
    // Start of a method/block
    public static void setDefaultLocale(@Nullable Locale defaultLocale) {
        // Calls a method
        MinestomAdventure.defaultLocale = Objects.requireNonNullElseGet(defaultLocale, Locale::getDefault);
    // End of a block/expression
    }

    // Start of a method/block
    public static BinaryTagHolder wrapNbt(BinaryTag nbt) {
        // Returns a value to the caller
        return new BinaryTagHolderImpl(nbt);
    // End of a block/expression
    }

    // Start of a method/block
    public static BinaryTag unwrapNbt(BinaryTagHolder holder) {
        // Branch: checks a condition
        if (holder instanceof BinaryTagHolderImpl(BinaryTag nbt))
            // Returns a value to the caller
            return nbt;
        // Exception handling
        try {
            // Returns a value to the caller
            return holder.get(MinestomAdventure.NBT_CODEC);
        // Start of a method/block
        } catch (IOException e) {
            // Throws an exception
            throw new RuntimeException("Failed to unwrap BinaryTagHolder", e);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
