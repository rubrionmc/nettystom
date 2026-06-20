// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.ArgumentCallback;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandExecutor;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.suggestion.SuggestionCallback;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.function.BiFunction;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.function.Predicate;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * An argument is meant to be parsed when added into a {@link Command}'s syntax with {@link Command#addSyntax(CommandExecutor, Argument[])}.
 * <p>
 * You can create your own with your own special conditions.
 * <p>
 * Arguments are parsed using {@link #parse(CommandSender, String)}.
 *
 * @param <T> the type of this parsed argument
 */
// Déclaration de type (classe/interface/enum/record)
public abstract class Argument<T> {
    // Instruction de code
    private final String id;
    // Instruction de code
    protected final boolean allowSpace;
    // Instruction de code
    protected final boolean useRemaining;

    // Instruction de code
    private @Nullable ArgumentCallback callback;

    // Instruction de code
    private @Nullable Function<CommandSender, T> defaultValue;

    // Instruction de code
    private @Nullable SuggestionCallback suggestionCallback;
    // Instruction de code
    protected @Nullable SuggestionType suggestionType;

    /**
     * Creates a new argument.
     *
     * @param id           the id of the argument, used to retrieve the parsed value
     * @param allowSpace   true if the argument can/should have spaces in it
     * @param useRemaining true if the argument will always take the rest of the command arguments
     */
    // Début d'une méthode/d'un bloc
    public Argument(String id, boolean allowSpace, boolean useRemaining) {
        // Accès à l'objet courant/parent
        this.id = id;
        // Accès à l'objet courant/parent
        this.allowSpace = allowSpace;
        // Accès à l'objet courant/parent
        this.useRemaining = useRemaining;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new argument with {@code useRemaining} sets to false.
     *
     * @param id         the id of the argument, used to retrieve the parsed value
     * @param allowSpace true if the argument can/should have spaces in it
     */
    // Début d'une méthode/d'un bloc
    public Argument(String id, boolean allowSpace) {
        // Appelle une méthode
        this(id, allowSpace, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a new argument with {@code useRemaining} and {@code allowSpace} sets to false.
     *
     * @param id the id of the argument, used to retrieve the parsed value
     */
    // Début d'une méthode/d'un bloc
    public Argument(String id) {
        // Appelle une méthode
        this(id, false, false);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Parses an argument, using {@link Argument#getId()} as the input
     *
     * @param argument the argument, with the input as id
     * @param <T>      the result type
     * @return the parsed result
     * @throws ArgumentSyntaxException if the argument cannot be parsed due to a fault input (argument id)
     */
    // Début d'une méthode/d'un bloc
    public static <T> T parse(CommandSender sender, Argument<T> argument) throws ArgumentSyntaxException {
        // Renvoie une valeur à l'appelant
        return argument.parse(sender, argument.getId());
    // Fin d'un bloc/d'une expression
    }

    /**
     * Parses the given input, and throw an {@link ArgumentSyntaxException}
     * if the input cannot be converted to {@code T}
     *
     * @param input the argument to parse
     * @return the parsed argument
     * @throws ArgumentSyntaxException if {@code value} is not valid
     */
    // Appelle une méthode
    public abstract T parse(CommandSender sender, String input) throws ArgumentSyntaxException;

    // Appelle une méthode
    public abstract ArgumentParserType parser();

    // Début d'une méthode/d'un bloc
    public byte @Nullable [] nodeProperties() {
        // Renvoie une valeur à l'appelant
        return null;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable SuggestionType suggestionType() {
        // Renvoie une valeur à l'appelant
        return suggestionType;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the ID of the argument, showed in-game above the chat bar
     * and used to retrieve the data when the command is parsed in {@link net.minestom.server.command.builder.CommandContext}.
     *
     * @return the argument id
     */
    // Début d'une méthode/d'un bloc
    public String getId() {
        // Renvoie une valeur à l'appelant
        return id;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the argument can contain space.
     *
     * @return true if the argument allows space, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean allowSpace() {
        // Renvoie une valeur à l'appelant
        return allowSpace;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the argument always use all the remaining characters.
     * <p>
     * ex: /help I am a test - will always give you "I am a test"
     * if the first and single argument does use the remaining.
     *
     * @return true if the argument use all the remaining characters, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean useRemaining() {
        // Renvoie une valeur à l'appelant
        return useRemaining;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link ArgumentCallback} to check if the argument-specific conditions are validated or not.
     *
     * @return the argument callback, null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public ArgumentCallback getCallback() {
        // Renvoie une valeur à l'appelant
        return callback;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the {@link ArgumentCallback}.
     *
     * @param callback the argument callback, null to do not have one
     */
    // Début d'une méthode/d'un bloc
    public void setCallback(@Nullable ArgumentCallback callback) {
        // Accès à l'objet courant/parent
        this.callback = callback;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if the argument has any error callback.
     *
     * @return true if the argument has an error callback, false otherwise
     */
    // Début d'une méthode/d'un bloc
    public boolean hasErrorCallback() {
        // Renvoie une valeur à l'appelant
        return callback != null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets if this argument is 'optional'.
     * <p>
     * Optional means that this argument can be put at the end of a syntax
     * and obtains a default value ({@link #getDefaultValue()}).
     *
     * @return true if this argument is considered optional
     */
    // Début d'une méthode/d'un bloc
    public boolean isOptional() {
        // Renvoie une valeur à l'appelant
        return defaultValue != null;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public Function<CommandSender, T> getDefaultValue() {
        // Renvoie une valeur à l'appelant
        return defaultValue;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the default value supplier of the argument.
     * <p>
     * A non-null value means that the argument can be put at the end of a syntax
     * to act as an optional one.
     *
     * @param defaultValue the default argument value, null to make the argument non-optional
     * @return 'this' for chaining
     */
    // Début d'une méthode/d'un bloc
    public Argument<T> setDefaultValue(@Nullable Supplier<T> defaultValue) {
        // Accès à l'objet courant/parent
        this.defaultValue = defaultValue == null ? null : unused -> defaultValue.get();
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Argument<T> setDefaultValue(@Nullable Function<CommandSender, T> defaultValue) {
        // Accès à l'objet courant/parent
        this.defaultValue = defaultValue;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the default value supplier of the argument.
     *
     * @param defaultValue the default argument value
     * @return 'this' for chaining
     */
    // Début d'une méthode/d'un bloc
    public Argument<T> setDefaultValue(T defaultValue) {
        // Accès à l'objet courant/parent
        this.defaultValue = unused -> defaultValue;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the suggestion callback of the argument
     *
     * @return the suggestion callback of the argument, null if it doesn't exist
     * @see #setSuggestionCallback
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public SuggestionCallback getSuggestionCallback() {
        // Renvoie une valeur à l'appelant
        return suggestionCallback;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the suggestion callback (for dynamic tab completion) of this argument.
     * <p>
     * Note: This will not automatically filter arguments by user input.
     *
     * @param suggestionCallback The suggestion callback to set.
     * @return 'this' for chaining
     */
    // Début d'une méthode/d'un bloc
    public Argument<T> setSuggestionCallback(SuggestionCallback suggestionCallback) {
        // Accès à l'objet courant/parent
        this.suggestionCallback = suggestionCallback;
        // Accès à l'objet courant/parent
        this.suggestionType = SuggestionType.ASK_SERVER;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Check if the argument has a suggestion.
     *
     * @return If this argument has a suggestion.
     */
    // Début d'une méthode/d'un bloc
    public boolean hasSuggestion() {
        // Renvoie une valeur à l'appelant
        return suggestionType != null;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Maps this argument's output to another result.
     *
     * @param mapper The mapper to use (this argument's input = desired output)
     * @param <O>    The type of output expected.
     * @return A new ArgumentMap that can get this complex object type.
     */
    // Début d'une méthode/d'un bloc
    public <O> Argument<O> map(Function<T, O> mapper) {
        // Renvoie une valeur à l'appelant
        return new ArgumentMap<>(this, (p, i) -> mapper.apply(i));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <O> Argument<O> map(BiFunction<CommandSender, T, O> mapper) {
        // Renvoie une valeur à l'appelant
        return new ArgumentMap<>(this, mapper);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Maps this argument's output to another result.
     *
     * @param predicate the argument predicate
     * @return A new ArgumentMap that filters using this filterer.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public Argument<T> filter(Predicate<T> predicate) {
        // Renvoie une valeur à l'appelant
        return new ArgumentFilter<>(this, predicate);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (this == o) return true;
        // Embranchement : vérifie une condition
        if (o == null || getClass() != o.getClass()) return false;

        // Appelle une méthode
        Argument<?> argument = (Argument<?>) o;

        // Renvoie une valeur à l'appelant
        return id.equals(argument.id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return id.hashCode();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class ArgumentMap<I, O> extends Argument<O> {
        // Affecte une valeur
        public static final int INVALID_MAP = 555;
        // Instruction de code
        final Argument<I> argument;
        // Instruction de code
        final BiFunction<CommandSender, I, O> mapper;

        // Début d'une méthode/d'un bloc
        private ArgumentMap(Argument<I> argument, BiFunction<CommandSender, I, O> mapper) {
            // Accès à l'objet courant/parent
            super(argument.getId(), argument.allowSpace(), argument.useRemaining());
            // Embranchement : vérifie une condition
            if (argument.getSuggestionCallback() != null)
                // Accès à l'objet courant/parent
                this.setSuggestionCallback(argument.getSuggestionCallback());
            // Embranchement : vérifie une condition
            if (argument.getDefaultValue() != null)
                // Accès à l'objet courant/parent
                this.setDefaultValue(sender -> mapper.apply(sender, argument.getDefaultValue().apply(sender)));
            // Accès à l'objet courant/parent
            this.argument = argument;
            // Accès à l'objet courant/parent
            this.mapper = mapper;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public O parse(CommandSender sender, String input) throws ArgumentSyntaxException {
            // Appelle une méthode
            final I value = argument.parse(sender, input);
            // Appelle une méthode
            final O mappedValue = mapper.apply(sender, value);
            // Embranchement : vérifie une condition
            if (mappedValue == null)
                // Lève une exception
                throw new ArgumentSyntaxException("Couldn't be converted to map type", input, INVALID_MAP);
            // Renvoie une valeur à l'appelant
            return mappedValue;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ArgumentParserType parser() {
            // Renvoie une valeur à l'appelant
            return argument.parser();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public byte @Nullable [] nodeProperties() {
            // Renvoie une valeur à l'appelant
            return argument.nodeProperties();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class ArgumentFilter<T> extends Argument<T> {
        // Affecte une valeur
        public static final int INVALID_FILTER = 556;
        // Instruction de code
        final Argument<T> argument;
        // Instruction de code
        final Predicate<T> predicate;

        // Début d'une méthode/d'un bloc
        private ArgumentFilter(Argument<T> argument, Predicate<T> predicate) {
            // Accès à l'objet courant/parent
            super(argument.getId(), argument.allowSpace(), argument.useRemaining());
            // Embranchement : vérifie une condition
            if (argument.getSuggestionCallback() != null)
                // Accès à l'objet courant/parent
                this.setSuggestionCallback(argument.getSuggestionCallback());
            // Embranchement : vérifie une condition
            if (argument.getDefaultValue() != null)
                // Accès à l'objet courant/parent
                this.setDefaultValue(argument.getDefaultValue());
            // Accès à l'objet courant/parent
            this.argument = argument;
            // Accès à l'objet courant/parent
            this.predicate = predicate;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public T parse(CommandSender sender, String input) throws ArgumentSyntaxException {
            // Appelle une méthode
            final T result = argument.parse(sender, input);
            // Embranchement : vérifie une condition
            if (!predicate.test(result))
                // Lève une exception
                throw new ArgumentSyntaxException("Predicate failed", input, INVALID_FILTER);
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public ArgumentParserType parser() {
            // Renvoie une valeur à l'appelant
            return argument.parser();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public byte @Nullable [] nodeProperties() {
            // Renvoie une valeur à l'appelant
            return argument.nodeProperties();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
