// Package declaration for this file
package net.minestom.server.command.builder.arguments;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.ArgumentCallback;
// Import of a required class
import net.minestom.server.command.builder.Command;
// Import of a required class
import net.minestom.server.command.builder.CommandExecutor;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.SuggestionType;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.command.builder.suggestion.SuggestionCallback;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.function.BiFunction;
// Import of a required class
import java.util.function.Function;
// Import of a required class
import java.util.function.Predicate;
// Import of a required class
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
// Type declaration (class/interface/enum/record)
public abstract class Argument<T> {
    // Code statement
    private final String id;
    // Code statement
    protected final boolean allowSpace;
    // Code statement
    protected final boolean useRemaining;

    // Code statement
    private @Nullable ArgumentCallback callback;

    // Code statement
    private @Nullable Function<CommandSender, T> defaultValue;

    // Code statement
    private @Nullable SuggestionCallback suggestionCallback;
    // Code statement
    protected @Nullable SuggestionType suggestionType;

    /**
     * Creates a new argument.
     *
     * @param id           the id of the argument, used to retrieve the parsed value
     * @param allowSpace   true if the argument can/should have spaces in it
     * @param useRemaining true if the argument will always take the rest of the command arguments
     */
    // Start of a method/block
    public Argument(String id, boolean allowSpace, boolean useRemaining) {
        // Access to the current/parent object
        this.id = id;
        // Access to the current/parent object
        this.allowSpace = allowSpace;
        // Access to the current/parent object
        this.useRemaining = useRemaining;
    // End of a block/expression
    }

    /**
     * Creates a new argument with {@code useRemaining} sets to false.
     *
     * @param id         the id of the argument, used to retrieve the parsed value
     * @param allowSpace true if the argument can/should have spaces in it
     */
    // Start of a method/block
    public Argument(String id, boolean allowSpace) {
        // Calls a method
        this(id, allowSpace, false);
    // End of a block/expression
    }

    /**
     * Creates a new argument with {@code useRemaining} and {@code allowSpace} sets to false.
     *
     * @param id the id of the argument, used to retrieve the parsed value
     */
    // Start of a method/block
    public Argument(String id) {
        // Calls a method
        this(id, false, false);
    // End of a block/expression
    }

    /**
     * Parses an argument, using {@link Argument#getId()} as the input
     *
     * @param argument the argument, with the input as id
     * @param <T>      the result type
     * @return the parsed result
     * @throws ArgumentSyntaxException if the argument cannot be parsed due to a fault input (argument id)
     */
    // Start of a method/block
    public static <T> T parse(CommandSender sender, Argument<T> argument) throws ArgumentSyntaxException {
        // Returns a value to the caller
        return argument.parse(sender, argument.getId());
    // End of a block/expression
    }

    /**
     * Parses the given input, and throw an {@link ArgumentSyntaxException}
     * if the input cannot be converted to {@code T}
     *
     * @param input the argument to parse
     * @return the parsed argument
     * @throws ArgumentSyntaxException if {@code value} is not valid
     */
    // Calls a method
    public abstract T parse(CommandSender sender, String input) throws ArgumentSyntaxException;

    // Calls a method
    public abstract ArgumentParserType parser();

    // Start of a method/block
    public byte @Nullable [] nodeProperties() {
        // Returns a value to the caller
        return null;
    // End of a block/expression
    }

    // Start of a method/block
    public @Nullable SuggestionType suggestionType() {
        // Returns a value to the caller
        return suggestionType;
    // End of a block/expression
    }

    /**
     * Gets the ID of the argument, showed in-game above the chat bar
     * and used to retrieve the data when the command is parsed in {@link net.minestom.server.command.builder.CommandContext}.
     *
     * @return the argument id
     */
    // Start of a method/block
    public String getId() {
        // Returns a value to the caller
        return id;
    // End of a block/expression
    }

    /**
     * Gets if the argument can contain space.
     *
     * @return true if the argument allows space, false otherwise
     */
    // Start of a method/block
    public boolean allowSpace() {
        // Returns a value to the caller
        return allowSpace;
    // End of a block/expression
    }

    /**
     * Gets if the argument always use all the remaining characters.
     * <p>
     * ex: /help I am a test - will always give you "I am a test"
     * if the first and single argument does use the remaining.
     *
     * @return true if the argument use all the remaining characters, false otherwise
     */
    // Start of a method/block
    public boolean useRemaining() {
        // Returns a value to the caller
        return useRemaining;
    // End of a block/expression
    }

    /**
     * Gets the {@link ArgumentCallback} to check if the argument-specific conditions are validated or not.
     *
     * @return the argument callback, null if not any
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public ArgumentCallback getCallback() {
        // Returns a value to the caller
        return callback;
    // End of a block/expression
    }

    /**
     * Sets the {@link ArgumentCallback}.
     *
     * @param callback the argument callback, null to do not have one
     */
    // Start of a method/block
    public void setCallback(@Nullable ArgumentCallback callback) {
        // Access to the current/parent object
        this.callback = callback;
    // End of a block/expression
    }

    /**
     * Gets if the argument has any error callback.
     *
     * @return true if the argument has an error callback, false otherwise
     */
    // Start of a method/block
    public boolean hasErrorCallback() {
        // Returns a value to the caller
        return callback != null;
    // End of a block/expression
    }

    /**
     * Gets if this argument is 'optional'.
     * <p>
     * Optional means that this argument can be put at the end of a syntax
     * and obtains a default value ({@link #getDefaultValue()}).
     *
     * @return true if this argument is considered optional
     */
    // Start of a method/block
    public boolean isOptional() {
        // Returns a value to the caller
        return defaultValue != null;
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public Function<CommandSender, T> getDefaultValue() {
        // Returns a value to the caller
        return defaultValue;
    // End of a block/expression
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
    // Start of a method/block
    public Argument<T> setDefaultValue(@Nullable Supplier<T> defaultValue) {
        // Access to the current/parent object
        this.defaultValue = defaultValue == null ? null : unused -> defaultValue.get();
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public Argument<T> setDefaultValue(@Nullable Function<CommandSender, T> defaultValue) {
        // Access to the current/parent object
        this.defaultValue = defaultValue;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Sets the default value supplier of the argument.
     *
     * @param defaultValue the default argument value
     * @return 'this' for chaining
     */
    // Start of a method/block
    public Argument<T> setDefaultValue(T defaultValue) {
        // Access to the current/parent object
        this.defaultValue = unused -> defaultValue;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Gets the suggestion callback of the argument
     *
     * @return the suggestion callback of the argument, null if it doesn't exist
     * @see #setSuggestionCallback
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public SuggestionCallback getSuggestionCallback() {
        // Returns a value to the caller
        return suggestionCallback;
    // End of a block/expression
    }

    /**
     * Sets the suggestion callback (for dynamic tab completion) of this argument.
     * <p>
     * Note: This will not automatically filter arguments by user input.
     *
     * @param suggestionCallback The suggestion callback to set.
     * @return 'this' for chaining
     */
    // Start of a method/block
    public Argument<T> setSuggestionCallback(SuggestionCallback suggestionCallback) {
        // Access to the current/parent object
        this.suggestionCallback = suggestionCallback;
        // Access to the current/parent object
        this.suggestionType = SuggestionType.ASK_SERVER;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    /**
     * Check if the argument has a suggestion.
     *
     * @return If this argument has a suggestion.
     */
    // Start of a method/block
    public boolean hasSuggestion() {
        // Returns a value to the caller
        return suggestionType != null;
    // End of a block/expression
    }

    /**
     * Maps this argument's output to another result.
     *
     * @param mapper The mapper to use (this argument's input = desired output)
     * @param <O>    The type of output expected.
     * @return A new ArgumentMap that can get this complex object type.
     */
    // Start of a method/block
    public <O> Argument<O> map(Function<T, O> mapper) {
        // Returns a value to the caller
        return new ArgumentMap<>(this, (p, i) -> mapper.apply(i));
    // End of a block/expression
    }

    // Start of a method/block
    public <O> Argument<O> map(BiFunction<CommandSender, T, O> mapper) {
        // Returns a value to the caller
        return new ArgumentMap<>(this, mapper);
    // End of a block/expression
    }

    /**
     * Maps this argument's output to another result.
     *
     * @param predicate the argument predicate
     * @return A new ArgumentMap that filters using this filterer.
     */
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public Argument<T> filter(Predicate<T> predicate) {
        // Returns a value to the caller
        return new ArgumentFilter<>(this, predicate);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public boolean equals(Object o) {
        // Branch: checks a condition
        if (this == o) return true;
        // Branch: checks a condition
        if (o == null || getClass() != o.getClass()) return false;

        // Calls a method
        Argument<?> argument = (Argument<?>) o;

        // Returns a value to the caller
        return id.equals(argument.id);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public int hashCode() {
        // Returns a value to the caller
        return id.hashCode();
    // End of a block/expression
    }

    // Start of a method/block
    private static final class ArgumentMap<I, O> extends Argument<O> {
        // Assigns a value
        public static final int INVALID_MAP = 555;
        // Code statement
        final Argument<I> argument;
        // Code statement
        final BiFunction<CommandSender, I, O> mapper;

        // Start of a method/block
        private ArgumentMap(Argument<I> argument, BiFunction<CommandSender, I, O> mapper) {
            // Access to the current/parent object
            super(argument.getId(), argument.allowSpace(), argument.useRemaining());
            // Branch: checks a condition
            if (argument.getSuggestionCallback() != null)
                // Access to the current/parent object
                this.setSuggestionCallback(argument.getSuggestionCallback());
            // Branch: checks a condition
            if (argument.getDefaultValue() != null)
                // Access to the current/parent object
                this.setDefaultValue(sender -> mapper.apply(sender, argument.getDefaultValue().apply(sender)));
            // Access to the current/parent object
            this.argument = argument;
            // Access to the current/parent object
            this.mapper = mapper;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public O parse(CommandSender sender, String input) throws ArgumentSyntaxException {
            // Calls a method
            final I value = argument.parse(sender, input);
            // Calls a method
            final O mappedValue = mapper.apply(sender, value);
            // Branch: checks a condition
            if (mappedValue == null)
                // Throws an exception
                throw new ArgumentSyntaxException("Couldn't be converted to map type", input, INVALID_MAP);
            // Returns a value to the caller
            return mappedValue;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ArgumentParserType parser() {
            // Returns a value to the caller
            return argument.parser();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public byte @Nullable [] nodeProperties() {
            // Returns a value to the caller
            return argument.nodeProperties();
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    private static final class ArgumentFilter<T> extends Argument<T> {
        // Assigns a value
        public static final int INVALID_FILTER = 556;
        // Code statement
        final Argument<T> argument;
        // Code statement
        final Predicate<T> predicate;

        // Start of a method/block
        private ArgumentFilter(Argument<T> argument, Predicate<T> predicate) {
            // Access to the current/parent object
            super(argument.getId(), argument.allowSpace(), argument.useRemaining());
            // Branch: checks a condition
            if (argument.getSuggestionCallback() != null)
                // Access to the current/parent object
                this.setSuggestionCallback(argument.getSuggestionCallback());
            // Branch: checks a condition
            if (argument.getDefaultValue() != null)
                // Access to the current/parent object
                this.setDefaultValue(argument.getDefaultValue());
            // Access to the current/parent object
            this.argument = argument;
            // Access to the current/parent object
            this.predicate = predicate;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public T parse(CommandSender sender, String input) throws ArgumentSyntaxException {
            // Calls a method
            final T result = argument.parse(sender, input);
            // Branch: checks a condition
            if (!predicate.test(result))
                // Throws an exception
                throw new ArgumentSyntaxException("Predicate failed", input, INVALID_FILTER);
            // Returns a value to the caller
            return result;
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public ArgumentParserType parser() {
            // Returns a value to the caller
            return argument.parser();
        // End of a block/expression
        }

        // Annotation for the following element
        @Override
        // Start of a method/block
        public byte @Nullable [] nodeProperties() {
            // Returns a value to the caller
            return argument.nodeProperties();
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
