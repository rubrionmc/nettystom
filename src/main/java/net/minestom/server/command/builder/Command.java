// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder;

// Import d'une classe nécessaire
import com.google.gson.JsonArray;
// Import d'une classe nécessaire
import com.google.gson.JsonObject;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.ArgumentWord;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.condition.CommandCondition;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;
// Import d'une classe nécessaire
import org.slf4j.Logger;
// Import d'une classe nécessaire
import org.slf4j.LoggerFactory;

// Import d'une classe nécessaire
import java.util.*;
// Import d'une classe nécessaire
import java.util.function.BiConsumer;
// Import d'une classe nécessaire
import java.util.function.BiFunction;
// Import d'une classe nécessaire
import java.util.function.Consumer;
// Import d'une classe nécessaire
import java.util.function.Function;
// Import d'une classe nécessaire
import java.util.stream.Stream;

/**
 * Represents a command which has suggestion/auto-completion.
 * <p>
 * The command works using a list of valid syntaxes.
 * For instance we could build the command
 * "/health set Notch 50" into multiple argument types "/health [set/add/remove] [username] [integer]"
 * <p>
 * All the default argument types can be found in {@link ArgumentType}
 * and the syntax be created/registered using {@link #addSyntax(CommandExecutor, Argument[])}.
 * <p>
 * If the command is executed with an incorrect syntax or without any argument, the default {@link CommandExecutor} will be called,
 * you can set it using {@link #setDefaultExecutor(CommandExecutor)}.
 * <p>
 * Before any syntax to be successfully executed the {@link CommandSender} needs to validated
 * the {@link CommandCondition} sets with {@link #setCondition(CommandCondition)} (ignored if null).
 * <p>
 * Some {@link Argument} could also require additional condition (eg: a number which need to be between 2 values),
 * in this case, if the whole syntax is correct but not the argument condition,
 * you can listen to its error code using {@link #setArgumentCallback(ArgumentCallback, Argument)} or {@link Argument#setCallback(ArgumentCallback)}.
 */
// Déclaration de type (classe/interface/enum/record)
public class Command {

    // Appelle une méthode
    private final static Logger LOGGER = LoggerFactory.getLogger(Command.class);

    // Instruction de code
    private final String name;
    // Instruction de code
    private final String[] aliases;
    // Instruction de code
    private final String[] names;

    // Instruction de code
    private @Nullable CommandExecutor defaultExecutor;
    // Instruction de code
    private @Nullable CommandCondition condition;

    // Instruction de code
    private final List<Command> subcommands;
    // Instruction de code
    private final List<CommandSyntax> syntaxes;

    /**
     * Creates a {@link Command} with a name and one or multiple aliases.
     *
     * @param name    the name of the command
     * @param aliases the command aliases
     * @see #Command(String)
     */
    // Début d'une méthode/d'un bloc
    public Command(String name, String... aliases) {
        // Accès à l'objet courant/parent
        this.name = name;
        // Accès à l'objet courant/parent
        this.aliases = aliases;
        // Accès à l'objet courant/parent
        this.names = Stream.concat(Arrays.stream(aliases), Stream.of(name)).toArray(String[]::new);

        // Accès à l'objet courant/parent
        this.subcommands = new ArrayList<>();
        // Accès à l'objet courant/parent
        this.syntaxes = new ArrayList<>();
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a {@link Command} with a name and no alias.
     *
     * @param name the name of the command
     * @see #Command(String, String...)
     */
    // Début d'une méthode/d'un bloc
    public Command(String name) {
        // Appelle une méthode
        this(name, new String[0]);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the {@link CommandCondition}.
     * <p>
     * It is called after the parsing and just before the execution no matter the syntax used and can be used to check permissions or
     * the {@link CommandSender} type.
     * <p>
     * Worth mentioning that the condition is also used to know if the command known from a player (at connection).
     *
     * @return the command condition, null if not any
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public CommandCondition getCondition() {
        // Renvoie une valeur à l'appelant
        return condition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the {@link CommandCondition}.
     *
     * @param commandCondition the new command condition, null to do not call anything
     * @see #getCondition()
     */
    // Début d'une méthode/d'un bloc
    public void setCondition(@Nullable CommandCondition commandCondition) {
        // Accès à l'objet courant/parent
        this.condition = commandCondition;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets an {@link ArgumentCallback}.
     * <p>
     * The argument callback is called when there's an error in the argument.
     *
     * @param callback the callback for the argument
     * @param argument the argument which get the callback
     */
    // Début d'une méthode/d'un bloc
    public void setArgumentCallback(ArgumentCallback callback, Argument<?> argument) {
        // Appelle une méthode
        argument.setCallback(callback);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void addSubcommand(Command command) {
        // Accès à l'objet courant/parent
        this.subcommands.add(command);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void addSubcommands(Command... commands) {
        // Boucle : répète un bloc
        for (Command command : commands) {
            // Appelle une méthode
            addSubcommand(command);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<Command> getSubcommands() {
        // Renvoie une valeur à l'appelant
        return Collections.unmodifiableList(subcommands);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds a new syntax in the command.
     * <p>
     * A syntax is simply a list of arguments and an executor called when successfully parsed.
     *
     * @param commandCondition the condition to use the syntax
     * @param executor         the executor to call when the syntax is successfully received
     * @param args             all the arguments of the syntax, the length needs to be higher than 0
     * @return the created {@link CommandSyntax syntaxes},
     * there can be multiple of them when optional arguments are used
     */
    // Instruction de code
    public Collection<CommandSyntax> addConditionalSyntax(@Nullable CommandCondition commandCondition,
                                                          // Instruction de code
                                                          CommandExecutor executor,
                                                          // Début d'une méthode/d'un bloc
                                                          Argument<?>... args) {
        // Check optional argument(s)
        // Affecte une valeur
        boolean hasOptional = false;
        // Début d'un bloc
        {
            // Boucle : répète un bloc
            for (Argument<?> argument : args) {
                // Embranchement : vérifie une condition
                if (argument.isOptional()) {
                    // Affecte une valeur
                    hasOptional = true;
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if (hasOptional && !argument.isOptional()) {
                    // Appelle une méthode
                    LOGGER.warn("Optional arguments are followed by a non-optional one, the default values will be ignored.");
                    // Affecte une valeur
                    hasOptional = false;
                    // Interrompt la boucle/le bloc
                    break;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Embranchement : vérifie une condition
        if (!hasOptional) {
            // Appelle une méthode
            final CommandSyntax syntax = new CommandSyntax(commandCondition, executor, args);
            // Accès à l'objet courant/parent
            this.syntaxes.add(syntax);
            // Renvoie une valeur à l'appelant
            return List.of(syntax);
        // Branche alternative de la condition
        } else {
            // Appelle une méthode
            List<CommandSyntax> optionalSyntaxes = new ArrayList<>();

            // the 'args' array starts by all the required arguments, followed by the optional ones
            // Appelle une méthode
            List<Argument<?>> requiredArguments = new ArrayList<>();
            // Appelle une méthode
            Map<String, Function<CommandSender, Object>> defaultValuesMap = new HashMap<>();
            // Affecte une valeur
            boolean optionalBranch = false;
            // Affecte une valeur
            int i = 0;
            // Boucle : répète un bloc
            for (Argument<?> argument : args) {
                // Affecte une valeur
                final boolean isLast = ++i == args.length;
                // Embranchement : vérifie une condition
                if (argument.isOptional()) {
                    // Set default value
                    // Appelle une méthode
                    defaultValuesMap.put(argument.getId(), (Function<CommandSender, Object>) argument.getDefaultValue());

                    // Embranchement : vérifie une condition
                    if (!optionalBranch && !requiredArguments.isEmpty()) {
                        // First optional argument, create a syntax with current cached arguments
                        // Affecte une valeur
                        final CommandSyntax syntax = new CommandSyntax(commandCondition, executor, defaultValuesMap,
                                // Appelle une méthode
                                requiredArguments.toArray(new Argument[0]));
                        // Appelle une méthode
                        optionalSyntaxes.add(syntax);
                        // Affecte une valeur
                        optionalBranch = true;
                    // Branche alternative de la condition
                    } else {
                        // New optional argument, save syntax with current cached arguments and save default value
                        // Affecte une valeur
                        final CommandSyntax syntax = new CommandSyntax(commandCondition, executor, defaultValuesMap,
                                // Appelle une méthode
                                requiredArguments.toArray(new Argument[0]));
                        // Appelle une méthode
                        optionalSyntaxes.add(syntax);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                requiredArguments.add(argument);
                // Embranchement : vérifie une condition
                if (isLast) {
                    // Create the last syntax
                    // Affecte une valeur
                    final CommandSyntax syntax = new CommandSyntax(commandCondition, executor, defaultValuesMap,
                            // Appelle une méthode
                            requiredArguments.toArray(new Argument[0]));
                    // Appelle une méthode
                    optionalSyntaxes.add(syntax);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Accès à l'objet courant/parent
            this.syntaxes.addAll(optionalSyntaxes);
            // Renvoie une valeur à l'appelant
            return optionalSyntaxes;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * Adds a new syntax without condition.
     *
     * @see #addConditionalSyntax(CommandCondition, CommandExecutor, Argument[])
     */
    // Début d'une méthode/d'un bloc
    public Collection<CommandSyntax> addSyntax(CommandExecutor executor, Argument<?>... args) {
        // Renvoie une valeur à l'appelant
        return addConditionalSyntax(null, executor, args);
    // Fin d'un bloc/d'une expression
    }

    /**
     * Creates a syntax from a formatted string.
     * <p>
     * Currently in beta as the format is not final.
     *
     * @param executor the syntax executor
     * @param format   the syntax format
     * @return the newly created {@link CommandSyntax syntaxes}.
     */
    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public Collection<CommandSyntax> addSyntax(CommandExecutor executor, String format) {
        // Renvoie une valeur à l'appelant
        return addSyntax(executor, ArgumentType.generate(format));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the main command's name.
     *
     * @return the main command's name
     */
    // Début d'une méthode/d'un bloc
    public String getName() {
        // Renvoie une valeur à l'appelant
        return name;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the command's aliases.
     *
     * @return the command aliases, can be null or empty
     */
    // Début d'une méthode/d'un bloc
    public String[] getAliases() {
        // Renvoie une valeur à l'appelant
        return aliases;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the possible names for this command.
     * <p>
     * Include {@link #getName()} and {@link #getAliases()}.
     *
     * @return this command names
     */
    // Début d'une méthode/d'un bloc
    public String[] getNames() {
        // Renvoie une valeur à l'appelant
        return names;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets the default {@link CommandExecutor} which is called when there is no argument
     * or if no corresponding syntax has been found.
     *
     * @return the default executor, null if not any
     * @see #setDefaultExecutor(CommandExecutor)
     */
    // Annotation pour l'élément suivant
    @Nullable
    // Début d'une méthode/d'un bloc
    public CommandExecutor getDefaultExecutor() {
        // Renvoie une valeur à l'appelant
        return defaultExecutor;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sets the default {@link CommandExecutor}.
     *
     * @param executor the new default executor, null to remove it
     * @see #getDefaultExecutor()
     */
    // Début d'une méthode/d'un bloc
    public void setDefaultExecutor(@Nullable CommandExecutor executor) {
        // Accès à l'objet courant/parent
        this.defaultExecutor = executor;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Gets all the syntaxes of this command.
     *
     * @return a collection containing all this command syntaxes
     * @see #addSyntax(CommandExecutor, Argument[])
     */
    // Début d'une méthode/d'un bloc
    public Collection<CommandSyntax> getSyntaxes() {
        // Renvoie une valeur à l'appelant
        return syntaxes;
    // Fin d'un bloc/d'une expression
    }

    /**
     * Called when a {@link CommandSender} executes this command before any syntax callback.
     * <p>
     * WARNING: the {@link CommandCondition} is not executed, and all the {@link CommandSyntax} are not checked,
     * this is called every time a {@link CommandSender} send a command which start by {@link #getName()} or {@link #getAliases()}.
     * <p>
     * Can be used if you wish to still suggest the player syntaxes but want to parse things mostly by yourself.
     *
     * @param sender  the {@link CommandSender}
     * @param context the UNCHECKED context of the command, some can be null even when unexpected
     * @param command the raw UNCHECKED received command
     */
    // Début d'une méthode/d'un bloc
    public void globalListener(CommandSender sender, @UnknownNullability CommandContext context, String command) {
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public Set<String> getSyntaxesStrings() {
        // Appelle une méthode
        Set<String> syntaxes = new HashSet<>();

        // Affecte une valeur
        Consumer<String> syntaxConsumer = syntaxString -> {
            // Boucle : répète un bloc
            for (String name : getNames()) {
                // Affecte une valeur
                final String syntax = name + StringUtils.SPACE + syntaxString;
                // Appelle une méthode
                syntaxes.add(syntax);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Accès à l'objet courant/parent
        this.subcommands.forEach(subcommand -> subcommand.getSyntaxesStrings().forEach(syntaxConsumer));

        // Accès à l'objet courant/parent
        this.syntaxes.forEach(commandSyntax -> syntaxConsumer.accept(commandSyntax.getSyntaxString()));

        // Renvoie une valeur à l'appelant
        return syntaxes;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    public String getSyntaxesTree() {
        // Appelle une méthode
        Node commandNode = new Node();
        // Appelle une méthode
        commandNode.names.addAll(Arrays.asList(getNames()));

        // current node, literal = returned node
        // Affecte une valeur
        BiFunction<Node, Set<String>, Node> findNode = (currentNode, literals) -> {

            // Boucle : répète un bloc
            for (Node node : currentNode.nodes) {
                // Affecte une valeur
                final var names = node.names;

                // Verify if at least one literal is shared
                // Appelle une méthode
                final boolean shared = names.stream().anyMatch(literals::contains);
                // Embranchement : vérifie une condition
                if (shared) {
                    // Appelle une méthode
                    names.addAll(literals);
                    // Renvoie une valeur à l'appelant
                    return node;
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }

            // Create a new node
            // Appelle une méthode
            Node node = new Node();
            // Appelle une méthode
            node.names.addAll(literals);
            // Appelle une méthode
            currentNode.nodes.add(node);
            // Renvoie une valeur à l'appelant
            return node;
        // Fin d'un bloc/d'une expression
        };

        // Affecte une valeur
        BiConsumer<CommandSyntax, Node> syntaxProcessor = (syntax, node) -> {
            // Appelle une méthode
            List<String> arguments = new ArrayList<>();
            // Affecte une valeur
            BiConsumer<Node, List<String>> addArguments = (n, args) -> {
                // Embranchement : vérifie une condition
                if (!args.isEmpty()) {
                    // Appelle une méthode
                    n.arguments.add(args);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            };

            // true if all following arguments are not part of
            // the branching plant (literals)
            // Affecte une valeur
            boolean branched = false;
            // Boucle : répète un bloc
            for (Argument<?> argument : syntax.getArguments()) {
                // Embranchement : vérifie une condition
                if (!branched) {
                    // Embranchement : vérifie une condition
                    if (argument instanceof ArgumentLiteral) {
                        // Appelle une méthode
                        final String literal = argument.getId();

                        // Appelle une méthode
                        addArguments.accept(node, arguments);
                        // Appelle une méthode
                        arguments = new ArrayList<>();

                        // Appelle une méthode
                        node = findNode.apply(node, Set.of(literal));
                        // Passe à l'itération suivante de la boucle
                        continue;
                    // Embranchement : vérifie une condition
                    } else if (argument instanceof ArgumentWord argumentWord) {
                        // Embranchement : vérifie une condition
                        if (argumentWord.hasRestrictions()) {
                            // Appelle une méthode
                            addArguments.accept(node, arguments);
                            // Appelle une méthode
                            arguments = new ArrayList<>();

                            // Appelle une méthode
                            node = findNode.apply(node, Set.of(argumentWord.getRestrictions()));
                            // Passe à l'itération suivante de la boucle
                            continue;
                        // Fin d'un bloc/d'une expression
                        }
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
                // Affecte une valeur
                branched = true;
                // Appelle une méthode
                arguments.add(argument.toString());
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            addArguments.accept(node, arguments);
        // Fin d'un bloc/d'une expression
        };

        // Subcommands
        // Accès à l'objet courant/parent
        this.subcommands.forEach(command -> {
            // Appelle une méthode
            final Node node = findNode.apply(commandNode, Set.of(command.getNames()));
            // Appelle une méthode
            command.getSyntaxes().forEach(syntax -> syntaxProcessor.accept(syntax, node));
        // Fin d'un bloc/d'une expression
        });

        // Syntaxes
        // Accès à l'objet courant/parent
        this.syntaxes.forEach(syntax -> syntaxProcessor.accept(syntax, commandNode));

        // Appelle une méthode
        JsonObject jsonObject = new JsonObject();
        // Appelle une méthode
        processNode(commandNode, jsonObject);
        // Renvoie une valeur à l'appelant
        return jsonObject.toString();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static boolean isValidName(Command command, String name) {
        // Boucle : répète un bloc
        for (String commandName : command.getNames()) {
            // Embranchement : vérifie une condition
            if (commandName.equals(name)) {
                // Renvoie une valeur à l'appelant
                return true;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return false;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private void processNode(Node node, JsonObject jsonObject) {
        // Affecte une valeur
        BiConsumer<String, Consumer<JsonArray>> processor = (s, consumer) -> {
            // Appelle une méthode
            JsonArray array = new JsonArray();
            // Appelle une méthode
            consumer.accept(array);
            // Embranchement : vérifie une condition
            if (!array.isEmpty()) {
                // Appelle une méthode
                jsonObject.add(s, array);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
        // Names
        // Appelle une méthode
        processor.accept("names", array -> node.names.forEach(array::add));
        // Nodes
        // Instruction de code
        processor.accept("nodes", array ->
                // Début d'une méthode/d'un bloc
                node.nodes.forEach(n -> {
                    // Appelle une méthode
                    JsonObject nodeObject = new JsonObject();
                    // Appelle une méthode
                    processNode(n, nodeObject);
                    // Appelle une méthode
                    array.add(nodeObject);
                // Instruction de code
                }));
        // Arguments
        // Instruction de code
        processor.accept("arguments", array ->
                // Instruction de code
                node.arguments.forEach(arguments ->
                        // Appelle une méthode
                        array.add(String.join(StringUtils.SPACE, arguments))));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class Node {
        // Appelle une méthode
        private final Set<String> names = new HashSet<>();
        // Appelle une méthode
        private final Set<Node> nodes = new HashSet<>();
        // Appelle une méthode
        private final List<List<String>> arguments = new ArrayList<>();
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
