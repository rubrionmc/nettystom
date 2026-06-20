// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.parser;

// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.*;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.*;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentParticle;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentDouble;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentFloat;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeBlockPosition;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec2;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec3;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Locale;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.concurrent.ConcurrentHashMap;
// Import d'une classe nécessaire
import java.util.function.Function;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentParser {

    // Appelle une méthode
    private static final Map<String, Function<String, Argument<?>>> ARGUMENT_FUNCTION_MAP = new ConcurrentHashMap<>();

    // Début d'une méthode/d'un bloc
    static {
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("literal", ArgumentLiteral::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("boolean", ArgumentBoolean::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("integer", ArgumentInteger::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("double", ArgumentDouble::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("float", ArgumentFloat::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("string", ArgumentString::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("word", ArgumentWord::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("stringarray", ArgumentStringArray::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("command", ArgumentCommand::new);
        // TODO enum
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("color", ArgumentColor::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("time", ArgumentTime::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("particle", ArgumentParticle::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("resourcelocation", ArgumentResourceLocation::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("entitytype", ArgumentEntityType::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("blockstate", ArgumentBlockState::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("intrange", ArgumentIntRange::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("floatrange", ArgumentFloatRange::new);

        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("entity", s -> new ArgumentEntity(s).singleEntity(true));
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("entities", ArgumentEntity::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("player", s -> new ArgumentEntity(s).singleEntity(true).onlyPlayers(true));
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("players", s -> new ArgumentEntity(s).onlyPlayers(true));

        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("itemstack", ArgumentItemStack::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("component", ArgumentComponent::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("uuid", ArgumentUUID::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("nbt", ArgumentNbtTag::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("nbtcompound", ArgumentNbtCompoundTag::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("relativeblockposition", ArgumentRelativeBlockPosition::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("relativevec3", ArgumentRelativeVec3::new);
        // Appelle une méthode
        ARGUMENT_FUNCTION_MAP.put("relativevec2", ArgumentRelativeVec2::new);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public static Argument<?>[] generate(String format) {
        // Appelle une méthode
        List<Argument<?>> result = new ArrayList<>();

        // 0 = no state
        // 1 = inside angle bracket <>
        // Affecte une valeur
        int state = 0;
        // function to create an argument from its identifier
        // not null during state 1
        // Affecte une valeur
        Function<String, Argument<?>> argumentFunction = null;

        // Appelle une méthode
        StringBuilder builder = new StringBuilder();

        // test: Integer<name> String<hey>
        // Boucle : répète un bloc
        for (int i = 0; i < format.length(); i++) {
            // Appelle une méthode
            char c = format.charAt(i);

            // No state
            // Embranchement : vérifie une condition
            if (state == 0) {
                // Embranchement : vérifie une condition
                if (c == ' ') {
                    // Use literal as the default argument
                    // Appelle une méthode
                    final String argument = builder.toString();
                    // Embranchement : vérifie une condition
                    if (!argument.isEmpty()) {
                        // Appelle une méthode
                        result.add(new ArgumentLiteral(argument));
                        // Appelle une méthode
                        builder = new StringBuilder();
                    // Fin d'un bloc/d'une expression
                    }
                // Embranchement : vérifie une condition
                } else if (c == '<') {
                    // Retrieve argument type
                    // Appelle une méthode
                    final String argument = builder.toString();
                    // Appelle une méthode
                    argumentFunction = ARGUMENT_FUNCTION_MAP.get(argument.toLowerCase(Locale.ROOT));
                    // Embranchement : vérifie une condition
                    if (argumentFunction == null) {
                        // Lève une exception
                        throw new IllegalArgumentException("error invalid argument name: " + argument);
                    // Fin d'un bloc/d'une expression
                    }

                    // Appelle une méthode
                    builder = new StringBuilder();
                    // Affecte une valeur
                    state = 1;
                // Branche alternative de la condition
                } else {
                    // Append to builder
                    // Appelle une méthode
                    builder.append(c);
                // Fin d'un bloc/d'une expression
                }

                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

            // Inside bracket <>
            // Embranchement : vérifie une condition
            if (state == 1) {
                // Embranchement : vérifie une condition
                if (c == '>') {
                    // Appelle une méthode
                    final String param = builder.toString();
                    // TODO argument options
                    // Appelle une méthode
                    Argument<?> argument = argumentFunction.apply(param);
                    // Appelle une méthode
                    result.add(argument);

                    // Appelle une méthode
                    builder = new StringBuilder();
                    // Affecte une valeur
                    state = 0;
                // Branche alternative de la condition
                } else {
                    // Appelle une méthode
                    builder.append(c);
                // Fin d'un bloc/d'une expression
                }

                // Passe à l'itération suivante de la boucle
                continue;
            // Fin d'un bloc/d'une expression
            }

        // Fin d'un bloc/d'une expression
        }

        // Use remaining as literal if present
        // Embranchement : vérifie une condition
        if (state == 0) {
            // Appelle une méthode
            final String argument = builder.toString();
            // Embranchement : vérifie une condition
            if (!argument.isEmpty()) {
                // Appelle une méthode
                result.add(new ArgumentLiteral(argument));
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return result.toArray(Argument[]::new);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable
    // Instruction de code
    public static ArgumentResult validate(CommandSender sender,
                                          // Instruction de code
                                          Argument<?> argument,
                                          // Instruction de code
                                          Argument<?>[] arguments, int argIndex,
                                          // Début d'une méthode/d'un bloc
                                          String[] inputArguments, int inputIndex) {
        // Affecte une valeur
        final boolean end = inputIndex == inputArguments.length;
        // Embranchement : vérifie une condition
        if (end) // Stop if there is no input to analyze left
            // Renvoie une valeur à l'appelant
            return null;

        // the parsed argument value, null if incorrect
        // Affecte une valeur
        Object parsedValue = null;
        // the argument exception, null if the input is correct
        // Affecte une valeur
        ArgumentSyntaxException argumentSyntaxException = null;
        // true if the arg is valid, false otherwise
        // Affecte une valeur
        boolean correct = false;
        // The raw string value of the argument
        // Affecte une valeur
        String rawArg = null;

        // Embranchement : vérifie une condition
        if (argument.useRemaining()) {
            // Affecte une valeur
            final boolean hasArgs = inputArguments.length > inputIndex;
            // Verify if there is any string part available
            // Embranchement : vérifie une condition
            if (hasArgs) {
                // Appelle une méthode
                StringBuilder builder = new StringBuilder();
                // Argument is supposed to take the rest of the command input
                // Boucle : répète un bloc
                for (int i = inputIndex; i < inputArguments.length; i++) {
                    // Affecte une valeur
                    final String arg = inputArguments[i];
                    // Embranchement : vérifie une condition
                    if (!builder.isEmpty())
                        // Appelle une méthode
                        builder.append(StringUtils.SPACE);
                    // Appelle une méthode
                    builder.append(arg);
                // Fin d'un bloc/d'une expression
                }

                // Appelle une méthode
                rawArg = builder.toString();

                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    parsedValue = argument.parse(sender, rawArg);
                    // Affecte une valeur
                    correct = true;
                // Début d'une méthode/d'un bloc
                } catch (ArgumentSyntaxException exception) {
                    // Affecte une valeur
                    argumentSyntaxException = exception;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Branche alternative de la condition
        } else {
            // Argument is either single-word or can accept optional delimited space(s)
            // Appelle une méthode
            StringBuilder builder = new StringBuilder();
            // Boucle : répète un bloc
            for (int i = inputIndex; i < inputArguments.length; i++) {
                // Appelle une méthode
                builder.append(inputArguments[i]);

                // Appelle une méthode
                rawArg = builder.toString();

                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    parsedValue = argument.parse(sender, rawArg);

                    // Prevent quitting the parsing too soon if the argument
                    // does not allow space
                    // Affecte une valeur
                    final boolean lastArgumentIteration = argIndex + 1 == arguments.length;
                    // Embranchement : vérifie une condition
                    if (lastArgumentIteration && i + 1 < inputArguments.length) {
                        // Embranchement : vérifie une condition
                        if (!argument.allowSpace())
                            // Interrompt la boucle/le bloc
                            break;
                        // Appelle une méthode
                        builder.append(StringUtils.SPACE);
                        // Passe à l'itération suivante de la boucle
                        continue;
                    // Fin d'un bloc/d'une expression
                    }

                    // Affecte une valeur
                    correct = true;

                    // Affecte une valeur
                    inputIndex = i + 1;
                    // Interrompt la boucle/le bloc
                    break;
                // Début d'une méthode/d'un bloc
                } catch (ArgumentSyntaxException exception) {
                    // Affecte une valeur
                    argumentSyntaxException = exception;

                    // Embranchement : vérifie une condition
                    if (!argument.allowSpace()) {
                        // rawArg should be the remaining
                        // Boucle : répète un bloc
                        for (int j = i + 1; j < inputArguments.length; j++) {
                            // Affecte une valeur
                            final String arg = inputArguments[j];
                            // Embranchement : vérifie une condition
                            if (!builder.isEmpty())
                                // Appelle une méthode
                                builder.append(StringUtils.SPACE);
                            // Appelle une méthode
                            builder.append(arg);
                        // Fin d'un bloc/d'une expression
                        }
                        // Appelle une méthode
                        rawArg = builder.toString();
                        // Interrompt la boucle/le bloc
                        break;
                    // Fin d'un bloc/d'une expression
                    }
                    // Appelle une méthode
                    builder.append(StringUtils.SPACE);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        ArgumentResult argumentResult = new ArgumentResult();
        // Affecte une valeur
        argumentResult.argument = argument;
        // Affecte une valeur
        argumentResult.correct = correct;
        // Affecte une valeur
        argumentResult.inputIndex = inputIndex;
        // Affecte une valeur
        argumentResult.argumentSyntaxException = argumentSyntaxException;

        // Appelle une méthode
        argumentResult.useRemaining = argument.useRemaining();

        // Affecte une valeur
        argumentResult.rawArg = rawArg;

        // Affecte une valeur
        argumentResult.parsedValue = parsedValue;
        // Renvoie une valeur à l'appelant
        return argumentResult;
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public static class ArgumentResult {
        // Instruction de code
        public Argument<?> argument;
        // Instruction de code
        public boolean correct;
        // Instruction de code
        public int inputIndex;
        // Instruction de code
        public ArgumentSyntaxException argumentSyntaxException;

        // Instruction de code
        public boolean useRemaining;

        // Instruction de code
        public String rawArg;

        // If correct
        // Instruction de code
        public Object parsedValue;
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
