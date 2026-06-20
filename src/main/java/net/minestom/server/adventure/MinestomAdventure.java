// Déclaration du paquet de ce fichier
package net.minestom.server.adventure;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.BinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.TagStringIO;
// Import d'une classe nécessaire
import net.kyori.adventure.nbt.api.BinaryTagHolder;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.translation.GlobalTranslator;
// Import d'une classe nécessaire
import net.kyori.adventure.util.Codec;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.BiFunction;

/**
 * Adventure related constants, etc.
 */
// Déclaration de type (classe/interface/enum/record)
public final class MinestomAdventure {
    /**
     * See {@link MinestomAdventure#tagStringIO()}
     */
    // Affecte une valeur
    private static final TagStringIO tagStringIO = TagStringIO.builder()
            // Instruction de code
            .emitHeterogeneousLists(true)
            // Instruction de code
            .acceptHeterogeneousLists(true)
            // Appelle une méthode
            .build();

    /**
     * A codec to convert between strings and NBT.
     */
    // Instruction de code
    public static final Codec<CompoundBinaryTag, String, IOException, IOException> NBT_CODEC
            // Appelle une méthode
            = Codec.codec(tagStringIO::asCompound, tagStringIO::asString);

    // todo: Need to properly add a translator interface so it can check for presence of a key for the flattener.
    // Affecte une valeur
    public static BiFunction<Component, Locale, Component> COMPONENT_TRANSLATOR = GlobalTranslator::render;

    // Appelle une méthode
    private static Locale defaultLocale = Locale.getDefault();

    // Début d'une méthode/d'un bloc
    private MinestomAdventure() {
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link TagStringIO} instance used to convert SNBT.
     * This instance should be used for all Adventure related SNBT parsing and serialization.
     * Note: This instance of the {@link TagStringIO} is configured to accept and emit heterogeneous lists
     *
     * @return the tag string IO instance
     */
    // Début d'une méthode/d'un bloc
    public static TagStringIO tagStringIO() {
        // Renvoie une valeur à l'appelant
        return tagStringIO;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the default locale used to translate components when no overriding locale has been provided.
     *
     * @return the default locale
     */
    // Début d'une méthode/d'un bloc
    public static Locale getDefaultLocale() {
        // Renvoie une valeur à l'appelant
        return defaultLocale;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the default locale used to translate components when no overriding locale has been provided.
     *
     * @param defaultLocale the new default, or {@code null} to return to {@link Locale#getDefault()}
     */
    // Début d'une méthode/d'un bloc
    public static void setDefaultLocale(@Nullable Locale defaultLocale) {
        // Appelle une méthode
        MinestomAdventure.defaultLocale = Objects.requireNonNullElseGet(defaultLocale, Locale::getDefault);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static BinaryTagHolder wrapNbt(BinaryTag nbt) {
        // Renvoie une valeur à l'appelant
        return new BinaryTagHolderImpl(nbt);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static BinaryTag unwrapNbt(BinaryTagHolder holder) {
        // Embranchement : vérifie une condition
        if (holder instanceof BinaryTagHolderImpl(BinaryTag nbt))
            // Renvoie une valeur à l'appelant
            return nbt;
        // Gestion des exceptions
        try {
            // Renvoie une valeur à l'appelant
            return holder.get(MinestomAdventure.NBT_CODEC);
        // Début d'une méthode/d'un bloc
        } catch (IOException e) {
            // Lève une exception
            throw new RuntimeException("Failed to unwrap BinaryTagHolder", e);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
