// Package declaration for this file
package net.minestom.server.command.builder;

// Import of a required class
import com.google.gson.JsonArray;
// Import of a required class
import com.google.gson.JsonObject;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentLiteral;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentType;
// Import of a required class
import net.minestom.server.command.builder.arguments.ArgumentWord;
// Import of a required class
import net.minestom.server.command.builder.condition.CommandCondition;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;
// Import of a required class
import org.jetbrains.annotations.Nullable;
// Import of a required class
import org.jetbrains.annotations.UnknownNullability;
// Import of a required class
import org.slf4j.Logger;
// Import of a required class
import org.slf4j.LoggerFactory;

// Import of a required class
import java.util.*;
// Import of a required class
import java.util.function.BiConsumer;
// Import of a required class
import java.util.function.BiFunction;
// Import of a required class
import java.util.function.Consumer;
// Import of a required class
import java.util.function.Function;
// Import of a required class
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
// Type declaration (class/interface/enum/record)
public class Command {

    // Calls a method
    private final static Logger LOGGER = LoggerFactory.getLogger(Command.class);

    // Code statement
    private final String name;
    // Code statement
    private final String[] aliases;
    // Code statement
    private final String[] names;

    // Code statement
    private @Nullable CommandExecutor defaultExecutor;
    // Code statement
    private @Nullable CommandCondition condition;

    // Code statement
    private final List<Command> subcommands;
    // Code statement
    private final List<CommandSyntax> syntaxes;

    /**
     * Creates a {@link Command} with a name and one or multiple aliases.
     *
     * @param name    the name of the command
     * @param aliases the command aliases
     * @see #Command(String)
     */
    // Start of a method/block
    public Command(String name, String... aliases) {
        // Access to the current/parent object
        this.name = name;
        // Access to the current/parent object
        this.aliases = aliases;
        // Access to the current/parent object
        this.names = Stream.concat(Arrays.stream(aliases), Stream.of(name)).toArray(String[]::new);

        // Access to the current/parent object
        this.subcommands = new ArrayList<>();
        // Access to the current/parent object
        this.syntaxes = new ArrayList<>();
    // End of a block/expression
    }

    /**
     * Creates a {@link Command} with a name and no alias.
     *
     * @param name the name of the command
     * @see #Command(String, String...)
     */
    // Start of a method/block
    public Command(String name) {
        // Calls a method
        this(name, new String[0]);
    // End of a block/expression
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
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public CommandCondition getCondition() {
        // Returns a value to the caller
        return condition;
    // End of a block/expression
    }

    /**
     * Sets the {@link CommandCondition}.
     *
     * @param commandCondition the new command condition, null to do not call anything
     * @see #getCondition()
     */
    // Start of a method/block
    public void setCondition(@Nullable CommandCondition commandCondition) {
        // Access to the current/parent object
        this.condition = commandCondition;
    // End of a block/expression
    }

    /**
     * Sets an {@link ArgumentCallback}.
     * <p>
     * The argument callback is called when there's an error in the argument.
     *
     * @param callback the callback for the argument
     * @param argument the argument which get the callback
     */
    // Start of a method/block
    public void setArgumentCallback(ArgumentCallback callback, Argument<?> argument) {
        // Calls a method
        argument.setCallback(callback);
    // End of a block/expression
    }

    // Start of a method/block
    public void addSubcommand(Command command) {
        // Access to the current/parent object
        this.subcommands.add(command);
    // End of a block/expression
    }

    // Start of a method/block
    public void addSubcommands(Command... commands) {
        // Loop: repeats a block
        for (Command command : commands) {
            // Calls a method
            addSubcommand(command);
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Start of a method/block
    public List<Command> getSubcommands() {
        // Returns a value to the caller
        return Collections.unmodifiableList(subcommands);
    // End of a block/expression
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
    // Code statement
    public Collection<CommandSyntax> addConditionalSyntax(@Nullable CommandCondition commandCondition,
                                                          // Code statement
                                                          CommandExecutor executor,
                                                          // Start of a method/block
                                                          Argument<?>... args) {
        // Check optional argument(s)
        // Assigns a value
        boolean hasOptional = false;
        // Start of a block
        {
            // Loop: repeats a block
            for (Argument<?> argument : args) {
                // Branch: checks a condition
                if (argument.isOptional()) {
                    // Assigns a value
                    hasOptional = true;
                // End of a block/expression
                }
                // Branch: checks a condition
                if (hasOptional && !argument.isOptional()) {
                    // Calls a method
                    LOGGER.warn("Optional arguments are followed by a non-optional one, the default values will be ignored.");
                    // Assigns a value
                    hasOptional = false;
                    // Breaks out of the loop/block
                    break;
                // End of a block/expression
                }
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Branch: checks a condition
        if (!hasOptional) {
            // Calls a method
            final CommandSyntax syntax = new CommandSyntax(commandCondition, executor, args);
            // Access to the current/parent object
            this.syntaxes.add(syntax);
            // Returns a value to the caller
            return List.of(syntax);
        // Alternative branch of the condition
        } else {
            // Calls a method
            List<CommandSyntax> optionalSyntaxes = new ArrayList<>();

            // the 'args' array starts by all the required arguments, followed by the optional ones
            // Calls a method
            List<Argument<?>> requiredArguments = new ArrayList<>();
            // Calls a method
            Map<String, Function<CommandSender, Object>> defaultValuesMap = new HashMap<>();
            // Assigns a value
            boolean optionalBranch = false;
            // Assigns a value
            int i = 0;
            // Loop: repeats a block
            for (Argument<?> argument : args) {
                // Assigns a value
                final boolean isLast = ++i == args.length;
                // Branch: checks a condition
                if (argument.isOptional()) {
                    // Set default value
                    // Calls a method
                    defaultValuesMap.put(argument.getId(), (Function<CommandSender, Object>) argument.getDefaultValue());

                    // Branch: checks a condition
                    if (!optionalBranch && !requiredArguments.isEmpty()) {
                        // First optional argument, create a syntax with current cached arguments
                        // Assigns a value
                        final CommandSyntax syntax = new CommandSyntax(commandCondition, executor, defaultValuesMap,
                                // Calls a method
                                requiredArguments.toArray(new Argument[0]));
                        // Calls a method
                        optionalSyntaxes.add(syntax);
                        // Assigns a value
                        optionalBranch = true;
                    // Alternative branch of the condition
                    } else {
                        // New optional argument, save syntax with current cached arguments and save default value
                        // Assigns a value
                        final CommandSyntax syntax = new CommandSyntax(commandCondition, executor, defaultValuesMap,
                                // Calls a method
                                requiredArguments.toArray(new Argument[0]));
                        // Calls a method
                        optionalSyntaxes.add(syntax);
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Calls a method
                requiredArguments.add(argument);
                // Branch: checks a condition
                if (isLast) {
                    // Create the last syntax
                    // Assigns a value
                    final CommandSyntax syntax = new CommandSyntax(commandCondition, executor, defaultValuesMap,
                            // Calls a method
                            requiredArguments.toArray(new Argument[0]));
                    // Calls a method
                    optionalSyntaxes.add(syntax);
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Access to the current/parent object
            this.syntaxes.addAll(optionalSyntaxes);
            // Returns a value to the caller
            return optionalSyntaxes;
        // End of a block/expression
        }
    // End of a block/expression
    }

    /**
     * Adds a new syntax without condition.
     *
     * @see #addConditionalSyntax(CommandCondition, CommandExecutor, Argument[])
     */
    // Start of a method/block
    public Collection<CommandSyntax> addSyntax(CommandExecutor executor, Argument<?>... args) {
        // Returns a value to the caller
        return addConditionalSyntax(null, executor, args);
    // End of a block/expression
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
    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public Collection<CommandSyntax> addSyntax(CommandExecutor executor, String format) {
        // Returns a value to the caller
        return addSyntax(executor, ArgumentType.generate(format));
    // End of a block/expression
    }

    /**
     * Gets the main command's name.
     *
     * @return the main command's name
     */
    // Start of a method/block
    public String getName() {
        // Returns a value to the caller
        return name;
    // End of a block/expression
    }

    /**
     * Gets the command's aliases.
     *
     * @return the command aliases, can be null or empty
     */
    // Start of a method/block
    public String[] getAliases() {
        // Returns a value to the caller
        return aliases;
    // End of a block/expression
    }

    /**
     * Gets all the possible names for this command.
     * <p>
     * Include {@link #getName()} and {@link #getAliases()}.
     *
     * @return this command names
     */
    // Start of a method/block
    public String[] getNames() {
        // Returns a value to the caller
        return names;
    // End of a block/expression
    }

    /**
     * Gets the default {@link CommandExecutor} which is called when there is no argument
     * or if no corresponding syntax has been found.
     *
     * @return the default executor, null if not any
     * @see #setDefaultExecutor(CommandExecutor)
     */
    // Annotation for the following element
    @Nullable
    // Start of a method/block
    public CommandExecutor getDefaultExecutor() {
        // Returns a value to the caller
        return defaultExecutor;
    // End of a block/expression
    }

    /**
     * Sets the default {@link CommandExecutor}.
     *
     * @param executor the new default executor, null to remove it
     * @see #getDefaultExecutor()
     */
    // Start of a method/block
    public void setDefaultExecutor(@Nullable CommandExecutor executor) {
        // Access to the current/parent object
        this.defaultExecutor = executor;
    // End of a block/expression
    }

    /**
     * Gets all the syntaxes of this command.
     *
     * @return a collection containing all this command syntaxes
     * @see #addSyntax(CommandExecutor, Argument[])
     */
    // Start of a method/block
    public Collection<CommandSyntax> getSyntaxes() {
        // Returns a value to the caller
        return syntaxes;
    // End of a block/expression
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
    // Start of a method/block
    public void globalListener(CommandSender sender, @UnknownNullability CommandContext context, String command) {
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public Set<String> getSyntaxesStrings() {
        // Calls a method
        Set<String> syntaxes = new HashSet<>();

        // Assigns a value
        Consumer<String> syntaxConsumer = syntaxString -> {
            // Loop: repeats a block
            for (String name : getNames()) {
                // Assigns a value
                final String syntax = name + StringUtils.SPACE + syntaxString;
                // Calls a method
                syntaxes.add(syntax);
            // End of a block/expression
            }
        // End of a block/expression
        };

        // Access to the current/parent object
        this.subcommands.forEach(subcommand -> subcommand.getSyntaxesStrings().forEach(syntaxConsumer));

        // Access to the current/parent object
        this.syntaxes.forEach(commandSyntax -> syntaxConsumer.accept(commandSyntax.getSyntaxString()));

        // Returns a value to the caller
        return syntaxes;
    // End of a block/expression
    }

    // Annotation for the following element
    @ApiStatus.Experimental
    // Start of a method/block
    public String getSyntaxesTree() {
        // Calls a method
        Node commandNode = new Node();
        // Calls a method
        commandNode.names.addAll(Arrays.asList(getNames()));

        // current node, literal = returned node
        // Assigns a value
        BiFunction<Node, Set<String>, Node> findNode = (currentNode, literals) -> {

            // Loop: repeats a block
            for (Node node : currentNode.nodes) {
                // Assigns a value
                final var names = node.names;

                // Verify if at least one literal is shared
                // Calls a method
                final boolean shared = names.stream().anyMatch(literals::contains);
                // Branch: checks a condition
                if (shared) {
                    // Calls a method
                    names.addAll(literals);
                    // Returns a value to the caller
                    return node;
                // End of a block/expression
                }
            // End of a block/expression
            }

            // Create a new node
            // Calls a method
            Node node = new Node();
            // Calls a method
            node.names.addAll(literals);
            // Calls a method
            currentNode.nodes.add(node);
            // Returns a value to the caller
            return node;
        // End of a block/expression
        };

        // Assigns a value
        BiConsumer<CommandSyntax, Node> syntaxProcessor = (syntax, node) -> {
            // Calls a method
            List<String> arguments = new ArrayList<>();
            // Assigns a value
            BiConsumer<Node, List<String>> addArguments = (n, args) -> {
                // Branch: checks a condition
                if (!args.isEmpty()) {
                    // Calls a method
                    n.arguments.add(args);
                // End of a block/expression
                }
            // End of a block/expression
            };

            // true if all following arguments are not part of
            // the branching plant (literals)
            // Assigns a value
            boolean branched = false;
            // Loop: repeats a block
            for (Argument<?> argument : syntax.getArguments()) {
                // Branch: checks a condition
                if (!branched) {
                    // Branch: checks a condition
                    if (argument instanceof ArgumentLiteral) {
                        // Calls a method
                        final String literal = argument.getId();

                        // Calls a method
                        addArguments.accept(node, arguments);
                        // Calls a method
                        arguments = new ArrayList<>();

                        // Calls a method
                        node = findNode.apply(node, Set.of(literal));
                        // Continues to the next loop iteration
                        continue;
                    // Branch: checks a condition
                    } else if (argument instanceof ArgumentWord argumentWord) {
                        // Branch: checks a condition
                        if (argumentWord.hasRestrictions()) {
                            // Calls a method
                            addArguments.accept(node, arguments);
                            // Calls a method
                            arguments = new ArrayList<>();

                            // Calls a method
                            node = findNode.apply(node, Set.of(argumentWord.getRestrictions()));
                            // Continues to the next loop iteration
                            continue;
                        // End of a block/expression
                        }
                    // End of a block/expression
                    }
                // End of a block/expression
                }
                // Assigns a value
                branched = true;
                // Calls a method
                arguments.add(argument.toString());
            // End of a block/expression
            }
            // Calls a method
            addArguments.accept(node, arguments);
        // End of a block/expression
        };

        // Subcommands
        // Access to the current/parent object
        this.subcommands.forEach(command -> {
            // Calls a method
            final Node node = findNode.apply(commandNode, Set.of(command.getNames()));
            // Calls a method
            command.getSyntaxes().forEach(syntax -> syntaxProcessor.accept(syntax, node));
        // End of a block/expression
        });

        // Syntaxes
        // Access to the current/parent object
        this.syntaxes.forEach(syntax -> syntaxProcessor.accept(syntax, commandNode));

        // Calls a method
        JsonObject jsonObject = new JsonObject();
        // Calls a method
        processNode(commandNode, jsonObject);
        // Returns a value to the caller
        return jsonObject.toString();
    // End of a block/expression
    }

    // Start of a method/block
    public static boolean isValidName(Command command, String name) {
        // Loop: repeats a block
        for (String commandName : command.getNames()) {
            // Branch: checks a condition
            if (commandName.equals(name)) {
                // Returns a value to the caller
                return true;
            // End of a block/expression
            }
        // End of a block/expression
        }
        // Returns a value to the caller
        return false;
    // End of a block/expression
    }

    // Start of a method/block
    private void processNode(Node node, JsonObject jsonObject) {
        // Assigns a value
        BiConsumer<String, Consumer<JsonArray>> processor = (s, consumer) -> {
            // Calls a method
            JsonArray array = new JsonArray();
            // Calls a method
            consumer.accept(array);
            // Branch: checks a condition
            if (!array.isEmpty()) {
                // Calls a method
                jsonObject.add(s, array);
            // End of a block/expression
            }
        // End of a block/expression
        };
        // Names
        // Calls a method
        processor.accept("names", array -> node.names.forEach(array::add));
        // Nodes
        // Code statement
        processor.accept("nodes", array ->
                // Start of a method/block
                node.nodes.forEach(n -> {
                    // Calls a method
                    JsonObject nodeObject = new JsonObject();
                    // Calls a method
                    processNode(n, nodeObject);
                    // Calls a method
                    array.add(nodeObject);
                // Code statement
                }));
        // Arguments
        // Code statement
        processor.accept("arguments", array ->
                // Code statement
                node.arguments.forEach(arguments ->
                        // Calls a method
                        array.add(String.join(StringUtils.SPACE, arguments))));
    // End of a block/expression
    }

    // Start of a method/block
    private static final class Node {
        // Calls a method
        private final Set<String> names = new HashSet<>();
        // Calls a method
        private final Set<Node> nodes = new HashSet<>();
        // Calls a method
        private final List<List<String>> arguments = new ArrayList<>();
    // End of a block/expression
    }

// End of a block/expression
}
