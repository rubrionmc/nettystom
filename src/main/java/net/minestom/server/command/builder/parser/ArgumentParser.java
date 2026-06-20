// Package declaration for this file
package net.minestom.server.command.builder.parser;

// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.*;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.*;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
// Import of a required class
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentParticle;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentDouble;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentFloat;
// Import of a required class
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
// Import of a required class
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
// Import of a required class
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec2;
// Import of a required class
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec3;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Locale;
// Import of a required class
import java.util.Map;
// Import of a required class
import java.util.concurrent.ConcurrentHashMap;
// Import of a required class
import java.util.function.Function;

// Type declaration (class/interface/enum/record)
public class ArgumentParser {

    // Calls a method
    private static final Map<String, Function<String, Argument<?>>> ARGUMENT_FUNCTION_MAP = new ConcurrentHashMap<>();

    // Start of a method/block
    static {
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("literal", ArgumentLiteral::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("boolean", ArgumentBoolean::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("integer", ArgumentInteger::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("double", ArgumentDouble::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("float", ArgumentFloat::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("string", ArgumentString::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("word", ArgumentWord::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("stringarray", ArgumentStringArray::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("command", ArgumentCommand::new);
        // TODO enum
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("color", ArgumentColor::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("time", ArgumentTime::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("particle", ArgumentParticle::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("resourcelocation", ArgumentResourceLocation::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("entitytype", ArgumentEntityType::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("blockstate", ArgumentBlockState::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("intrange", ArgumentIntRange::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("floatrange", ArgumentFloatRange::new);

        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("entity", s -> new ArgumentEntity(s).singleEntity(true));
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("entities", ArgumentEntity::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("player", s -> new ArgumentEntity(s).singleEntity(true).onlyPlayers(true));
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("players", s -> new ArgumentEntity(s).onlyPlayers(true));

        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("itemstack", ArgumentItemStack::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("component", ArgumentComponent::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("uuid", ArgumentUUID::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("nbt", ArgumentNbtTag::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("nbtcompound", ArgumentNbtCompoundTag::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("relativeblockposition", ArgumentRelativeBlockPosition::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("relativevec3", ArgumentRelativeVec3::new);
        // Calls a method
        ARGUMENT_FUNCTION_MAP.put("relativevec2", ArgumentRelativeVec2::new);
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public static Argument<?>[] generate(String format) {
        // Calls a method
        List<Argument<?>> result = new ArrayList<>();

        // 0 = no state
        // 1 = inside angle bracket <>
        // Assigns a value
        int state = 0;
        // function to create an argument from its identifier
        // not null during state 1
        // Assigns a value
        Function<String, Argument<?>> argumentFunction = null;

        // Calls a method
        StringBuilder builder = new StringBuilder();

        // test: Integer<name> String<hey>
        // Loop: repeats a block
        for (int i = 0; i < format.length(); i++) {
            // Calls a method
            char c = format.charAt(i);

            // No state
            // Branch: checks a condition
            if (state == 0) {
                // Branch: checks a condition
                if (c == ' ') {
                    // Use literal as the default argument
                    // Calls a method
                    final String argument = builder.toString();
                    // Branch: checks a condition
                    if (!argument.isEmpty()) {
                        // Calls a method
                        result.add(new ArgumentLiteral(argument));
                        // Calls a method
                        builder = new StringBuilder();
                    // End of a block/expression
                    }
                // Branch: checks a condition
                } else if (c == '<') {
                    // Retrieve argument type
                    // Calls a method
                    final String argument = builder.toString();
                    // Calls a method
                    argumentFunction = ARGUMENT_FUNCTION_MAP.get(argument.toLowerCase(Locale.ROOT));
                    // Branch: checks a condition
                    if (argumentFunction == null) {
                        // Throws an exception
                        throw new IllegalArgumentException("error invalid argument name: " + argument);
                    // End of a block/expression
                    }

                    // Calls a method
                    builder = new StringBuilder();
                    // Assigns a value
                    state = 1;
                // Alternative branch of the condition
                } else {
                    // Append to builder
                    // Calls a method
                    builder.append(c);
                // End of a block/expression
                }

                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

            // Inside bracket <>
            // Branch: checks a condition
            if (state == 1) {
                // Branch: checks a condition
                if (c == '>') {
                    // Calls a method
                    final String param = builder.toString();
                    // TODO argument options
                    // Calls a method
                    Argument<?> argument = argumentFunction.apply(param);
                    // Calls a method
                    result.add(argument);

                    // Calls a method
                    builder = new StringBuilder();
                    // Assigns a value
                    state = 0;
                // Alternative branch of the condition
                } else {
                    // Calls a method
                    builder.append(c);
                // End of a block/expression
                }

                // Continues to the next loop iteration
                continue;
            // End of a block/expression
            }

        // End of a block/expression
        }

        // Use remaining as literal if present
        // Branch: checks a condition
        if (state == 0) {
            // Calls a method
            final String argument = builder.toString();
            // Branch: checks a condition
            if (!argument.isEmpty()) {
                // Calls a method
                result.add(new ArgumentLiteral(argument));
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return result.toArray(Argument[]::new);
    // End of a block/expression
    }

    // Annotation for the following element
    @Nullable
    // Code statement
    public static ArgumentResult validate(CommandSender sender,
                                          // Code statement
                                          Argument<?> argument,
                                          // Code statement
                                          Argument<?>[] arguments, int argIndex,
                                          // Start of a method/block
                                          String[] inputArguments, int inputIndex) {
        // Assigns a value
        final boolean end = inputIndex == inputArguments.length;
        // Branch: checks a condition
        if (end) // Stop if there is no input to analyze left
            // Returns a value to the caller
            return null;

        // the parsed argument value, null if incorrect
        // Assigns a value
        Object parsedValue = null;
        // the argument exception, null if the input is correct
        // Assigns a value
        ArgumentSyntaxException argumentSyntaxException = null;
        // true if the arg is valid, false otherwise
        // Assigns a value
        boolean correct = false;
        // The raw string value of the argument
        // Assigns a value
        String rawArg = null;

        // Branch: checks a condition
        if (argument.useRemaining()) {
            // Assigns a value
            final boolean hasArgs = inputArguments.length > inputIndex;
            // Verify if there is any string part available
            // Branch: checks a condition
            if (hasArgs) {
                // Calls a method
                StringBuilder builder = new StringBuilder();
                // Argument is supposed to take the rest of the command input
                // Loop: repeats a block
                for (int i = inputIndex; i < inputArguments.length; i++) {
                    // Assigns a value
                    final String arg = inputArguments[i];
                    // Branch: checks a condition
                    if (!builder.isEmpty())
                        // Calls a method
                        builder.append(StringUtils.SPACE);
                    // Calls a method
                    builder.append(arg);
                // End of a block/expression
                }

                // Calls a method
                rawArg = builder.toString();

                // Exception handling
                try {
                    // Calls a method
                    parsedValue = argument.parse(sender, rawArg);
                    // Assigns a value
                    correct = true;
                // Start of a method/block
                } catch (ArgumentSyntaxException exception) {
                    // Assigns a value
                    argumentSyntaxException = exception;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // Alternative branch of the condition
        } else {
            // Argument is either single-word or can accept optional delimited space(s)
            // Calls a method
            StringBuilder builder = new StringBuilder();
            // Loop: repeats a block
            for (int i = inputIndex; i < inputArguments.length; i++) {
                // Calls a method
                builder.append(inputArguments[i]);

                // Calls a method
                rawArg = builder.toString();

                // Exception handling
                try {
                    // Calls a method
                    parsedValue = argument.parse(sender, rawArg);

                    // Prevent quitting the parsing too soon if the argument
                    // does not allow space
                    // Assigns a value
                    final boolean lastArgumentIteration = argIndex + 1 == arguments.length;
                    // Branch: checks a condition
                    if (lastArgumentIteration && i + 1 < inputArguments.length) {
                        // Branch: checks a condition
                        if (!argument.allowSpace())
                            // Breaks out of the loop/block
                            break;
                        // Calls a method
                        builder.append(StringUtils.SPACE);
                        // Continues to the next loop iteration
                        continue;
                    // End of a block/expression
                    }

                    // Assigns a value
                    correct = true;

                    // Assigns a value
                    inputIndex = i + 1;
                    // Breaks out of the loop/block
                    break;
                // Start of a method/block
                } catch (ArgumentSyntaxException exception) {
                    // Assigns a value
                    argumentSyntaxException = exception;

                    // Branch: checks a condition
                    if (!argument.allowSpace()) {
                        // rawArg should be the remaining
                        // Loop: repeats a block
                        for (int j = i + 1; j < inputArguments.length; j++) {
                            // Assigns a value
                            final String arg = inputArguments[j];
                            // Branch: checks a condition
                            if (!builder.isEmpty())
                                // Calls a method
                                builder.append(StringUtils.SPACE);
                            // Calls a method
                            builder.append(arg);
                        // End of a block/expression
                        }
                        // Calls a method
                        rawArg = builder.toString();
                        // Breaks out of the loop/block
                        break;
                    // End of a block/expression
                    }
                    // Calls a method
                    builder.append(StringUtils.SPACE);
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Calls a method
        ArgumentResult argumentResult = new ArgumentResult();
        // Assigns a value
        argumentResult.argument = argument;
        // Assigns a value
        argumentResult.correct = correct;
        // Assigns a value
        argumentResult.inputIndex = inputIndex;
        // Assigns a value
        argumentResult.argumentSyntaxException = argumentSyntaxException;

        // Calls a method
        argumentResult.useRemaining = argument.useRemaining();

        // Assigns a value
        argumentResult.rawArg = rawArg;

        // Assigns a value
        argumentResult.parsedValue = parsedValue;
        // Returns a value to the caller
        return argumentResult;
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public static class ArgumentResult {
        // Code statement
        public Argument<?> argument;
        // Code statement
        public boolean correct;
        // Code statement
        public int inputIndex;
        // Code statement
        public ArgumentSyntaxException argumentSyntaxException;

        // Code statement
        public boolean useRemaining;

        // Code statement
        public String rawArg;

        // If correct
        // Code statement
        public Object parsedValue;
    // End of a block/expression
    }

// End of a block/expression
}
