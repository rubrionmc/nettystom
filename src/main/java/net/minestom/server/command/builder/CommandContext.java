// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.NotNull;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.HashMap;
// Import d'une classe nécessaire
import java.util.Map;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.Supplier;

/**
 * Class used to retrieve argument data in a {@link CommandExecutor}.
 * <p>
 * All id are the one specified in the {@link Argument} constructor.
 * <p>
 * All methods are @{@link NotNull} in the sense that you should not have to verify their validity since if the syntax
 * is called, it means that all of its arguments are correct. Be aware that trying to retrieve an argument not present
 * in the syntax will result in a {@link NullPointerException}.
 */
// Déclaration de type (classe/interface/enum/record)
public class CommandContext {

    // Instruction de code
    private final String input;
    // Instruction de code
    private final String commandName;
    // Appelle une méthode
    protected Map<String, Object> args = new HashMap<>();
    // Appelle une méthode
    protected Map<String, String> rawArgs = new HashMap<>();
    // Instruction de code
    private CommandData returnData;

    // Début d'une méthode/d'un bloc
    public CommandContext(String input) {
        // Accès à l'objet courant/parent
        this.input = input;
        // Accès à l'objet courant/parent
        this.commandName = input.split(StringUtils.SPACE)[0];
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getInput() {
        // Renvoie une valeur à l'appelant
        return input;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getCommandName() {
        // Renvoie une valeur à l'appelant
        return commandName;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <T> T get(Argument<T> argument) {
        // Renvoie une valeur à l'appelant
        return get(argument.getId());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <T> T get(String identifier) {
        // Renvoie une valeur à l'appelant
        return (T) args.get(identifier);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <T> T getOrDefault(Argument<T> argument, T defaultValue) {
        // Renvoie une valeur à l'appelant
        return getOrDefault(argument.getId(), defaultValue);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public <T> T getOrDefault(String identifier, T defaultValue) {
        // Instruction de code
        T value;
        // Renvoie une valeur à l'appelant
        return (value = get(identifier)) != null ? value : defaultValue;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean has(Argument<?> argument) {
        // Renvoie une valeur à l'appelant
        return args.containsKey(argument.getId());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean has(String identifier) {
        // Renvoie une valeur à l'appelant
        return args.containsKey(identifier);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public @Nullable CommandData getReturnData() {
        // Renvoie une valeur à l'appelant
        return returnData;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setReturnData(@Nullable CommandData returnData) {
        // Accès à l'objet courant/parent
        this.returnData = returnData;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public Map<String, Object> getMap() {
        // Renvoie une valeur à l'appelant
        return args;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void copy(CommandContext context) {
        // Accès à l'objet courant/parent
        this.args = context.args;
        // Accès à l'objet courant/parent
        this.rawArgs = context.rawArgs;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getRaw(Argument<?> argument) {
        // Renvoie une valeur à l'appelant
        return rawArgs.get(argument.getId());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public String getRaw(String identifier) {
        // Renvoie une valeur à l'appelant
        return rawArgs.get(identifier);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setArg(String id, Object value, String rawInput) {
        // Accès à l'objet courant/parent
        this.args.put(id, value);
        // Accès à l'objet courant/parent
        this.rawArgs.put(id, rawInput);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void clear() {
        // Accès à l'objet courant/parent
        this.args.clear();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    protected void retrieveDefaultValues(@Nullable Map<String, Supplier<Object>> defaultValuesMap) {
        // Embranchement : vérifie une condition
        if (defaultValuesMap == null) return;
        // Boucle : répète un bloc
        for (var entry : defaultValuesMap.entrySet()) {
            // Appelle une méthode
            final String key = entry.getKey();
            // Embranchement : vérifie une condition
            if (!args.containsKey(key)) {
                // Appelle une méthode
                final var supplier = entry.getValue();
                // Accès à l'objet courant/parent
                this.args.put(key, supplier.get());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public boolean equals(Object o) {
        // Embranchement : vérifie une condition
        if (this == o) return true;
        // Embranchement : vérifie une condition
        if (!(o instanceof CommandContext that)) return false;
        // Renvoie une valeur à l'appelant
        return Objects.equals(input, that.input) &&
                // Instruction de code
                Objects.equals(commandName, that.commandName) &&
                // Instruction de code
                Objects.equals(args, that.args) &&
                // Instruction de code
                Objects.equals(rawArgs, that.rawArgs) &&
                // Appelle une méthode
                Objects.equals(returnData, that.returnData);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int hashCode() {
        // Renvoie une valeur à l'appelant
        return Objects.hash(input, commandName, args, rawArgs, returnData);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
