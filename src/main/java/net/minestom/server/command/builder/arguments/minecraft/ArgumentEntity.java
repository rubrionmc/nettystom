// Package declaration for this file
package net.minestom.server.command.builder.arguments.minecraft;

// Import of a required class
import net.minestom.server.command.ArgumentParserType;
// Import of a required class
import net.minestom.server.command.CommandSender;
// Import of a required class
import net.minestom.server.command.builder.arguments.Argument;
// Import of a required class
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import of a required class
import net.minestom.server.entity.EntityType;
// Import of a required class
import net.minestom.server.entity.GameMode;
// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.utils.Range;
// Import of a required class
import net.minestom.server.utils.StringUtils;
// Import of a required class
import net.minestom.server.utils.entity.EntityFinder;
// Import of a required class
import org.jetbrains.annotations.Nullable;

// Import of a required class
import java.util.Arrays;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.UUID;
// Import of a required class
import java.util.regex.Pattern;

/**
 * Represents the target selector argument.
 * <a href="https://minecraft.wiki/w/Target_selectors">Target selectors</a>
 */
// Type declaration (class/interface/enum/record)
public class ArgumentEntity extends Argument<EntityFinder> {

    // Assigns a value
    public static final int INVALID_SYNTAX = -2;
    // Assigns a value
    public static final int ONLY_SINGLE_ENTITY_ERROR = -3;
    // Assigns a value
    public static final int ONLY_PLAYERS_ERROR = -4;
    // Assigns a value
    public static final int INVALID_ARGUMENT_NAME = -5;
    // Assigns a value
    public static final int INVALID_ARGUMENT_VALUE = -6;

    // Calls a method
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]{1,16}");
    // Assigns a value
    private static final String SELECTOR_PREFIX = "@";
    // Calls a method
    private static final List<String> SELECTOR_VARIABLES = Arrays.asList("@p", "@r", "@a", "@e", "@s", "@n");
    // Calls a method
    private static final List<String> PLAYERS_ONLY_SELECTOR = Arrays.asList("@p", "@r", "@a", "@s");
    // Calls a method
    private static final List<String> SINGLE_ONLY_SELECTOR = Arrays.asList("@p", "@r", "@s", "@n");
    // List with all the valid arguments
    // Assigns a value
    private static final List<String> VALID_ARGUMENTS = Arrays.asList(
            // Code statement
            "x", "y", "z",
            // Code statement
            "distance", "dx", "dy", "dz",
            // Code statement
            "scores", "tag", "team", "limit", "sort", "level", "gamemode", "name",
            // Code statement
            "x_rotation", "y_rotation", "type", "nbt", "advancements", "predicate");

    // List with all the easily parsable arguments which only require reading until a specific character (comma)
    // Assigns a value
    private static final List<String> SIMPLE_ARGUMENTS = Arrays.asList(
            // Code statement
            "x", "y", "z",
            // Code statement
            "distance", "dx", "dy", "dz",
            // Code statement
            "scores", "tag", "team", "limit", "sort", "level", "gamemode",
            // Code statement
            "x_rotation", "y_rotation", "type", "advancements", "predicate");

    // Code statement
    private boolean onlySingleEntity;
    // Code statement
    private boolean onlyPlayers;

    // Start of a method/block
    public ArgumentEntity(String id) {
        // Access to the current/parent object
        super(id, true);
    // End of a block/expression
    }

    // Start of a method/block
    public ArgumentEntity singleEntity(boolean singleEntity) {
        // Access to the current/parent object
        this.onlySingleEntity = singleEntity;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Start of a method/block
    public ArgumentEntity onlyPlayers(boolean onlyPlayers) {
        // Access to the current/parent object
        this.onlyPlayers = onlyPlayers;
        // Returns a value to the caller
        return this;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public EntityFinder parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Returns a value to the caller
        return staticParse(sender, input, onlySingleEntity, onlyPlayers);
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public ArgumentParserType parser() {
        // Returns a value to the caller
        return ArgumentParserType.ENTITY;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public byte @Nullable [] nodeProperties() {
        // Returns a value to the caller
        return NetworkBuffer.makeArray(buffer -> {
            // Assigns a value
            byte mask = 0;
            // Branch: checks a condition
            if (this.isOnlySingleEntity()) {
                // Code statement
                mask |= 0x01;
            // End of a block/expression
            }
            // Branch: checks a condition
            if (this.isOnlyPlayers()) {
                // Code statement
                mask |= 0x02;
            // End of a block/expression
            }
            // Calls a method
            buffer.write(NetworkBuffer.BYTE, mask);
        // End of a block/expression
        });
    // End of a block/expression
    }

    /**
     * @deprecated use {@link Argument#parse(CommandSender, Argument)}
     */
    // Annotation for the following element
    @Deprecated
    // Code statement
    public static EntityFinder staticParse(CommandSender sender, String input,
                                           // Start of a method/block
                                           boolean onlySingleEntity, boolean onlyPlayers) throws ArgumentSyntaxException {
        // Check for raw player name or UUID
        // Branch: checks a condition
        if (!input.contains(SELECTOR_PREFIX) && !input.contains(StringUtils.SPACE)) {

            // Check if the input is a valid UUID
            // Exception handling
            try {
                // Calls a method
                final UUID uuid = UUID.fromString(input);
                // Returns a value to the caller
                return new EntityFinder()
                        // Code statement
                        .setTargetSelector(EntityFinder.TargetSelector.MINESTOM_UUID)
                        // Calls a method
                        .setConstantUuid(uuid);
            // Start of a method/block
            } catch (IllegalArgumentException ignored) {
            // End of a block/expression
            }

            // Check if the input is a valid player name
            // Branch: checks a condition
            if (USERNAME_PATTERN.matcher(input).matches()) {
                // Returns a value to the caller
                return new EntityFinder()
                        // Code statement
                        .setTargetSelector(EntityFinder.TargetSelector.MINESTOM_USERNAME)
                        // Calls a method
                        .setConstantName(input);
            // End of a block/expression
            }
        // End of a block/expression
        }

        // The minimum size is always 2 (for the selector variable, ex: @p)
        // Branch: checks a condition
        if (input.length() < 2)
            // Throws an exception
            throw new ArgumentSyntaxException("Length needs to be > 1", input, INVALID_SYNTAX);

        // The target selector variable always start by '@'
        // Branch: checks a condition
        if (!input.startsWith(SELECTOR_PREFIX))
            // Throws an exception
            throw new ArgumentSyntaxException("Target selector needs to start with @", input, INVALID_SYNTAX);

        // Calls a method
        final String selectorVariable = input.substring(0, 2);

        // Check if the selector variable used exists
        // Branch: checks a condition
        if (!SELECTOR_VARIABLES.contains(selectorVariable))
            // Throws an exception
            throw new ArgumentSyntaxException("Invalid selector variable", input, INVALID_SYNTAX);

        // Check if it should only select single entity and if the selector variable valid the condition
        // Branch: checks a condition
        if (onlySingleEntity && !SINGLE_ONLY_SELECTOR.contains(selectorVariable))
            // Throws an exception
            throw new ArgumentSyntaxException("Argument requires only a single entity", input, ONLY_SINGLE_ENTITY_ERROR);

        // Check if it should only select players and if the selector variable valid the condition
        // Branch: checks a condition
        if (onlyPlayers && !PLAYERS_ONLY_SELECTOR.contains(selectorVariable))
            // Throws an exception
            throw new ArgumentSyntaxException("Argument requires only players", input, ONLY_PLAYERS_ERROR);

        // Create the EntityFinder which will be used for the rest of the parsing
        // Assigns a value
        final EntityFinder entityFinder = new EntityFinder()
                // Calls a method
                .setTargetSelector(toTargetSelector(selectorVariable));

        // The selector is a single selector variable which verify all the conditions
        // Branch: checks a condition
        if (input.length() == 2)
            // Returns a value to the caller
            return entityFinder;

        // START PARSING THE STRUCTURE
        // Calls a method
        final String structure = input.substring(2);
        // Returns a value to the caller
        return parseStructure(sender, input, entityFinder, structure);
    // End of a block/expression
    }

    // Code statement
    private static EntityFinder parseStructure(CommandSender sender,
                                               // Code statement
                                               String input,
                                               // Code statement
                                               EntityFinder entityFinder,
                                               // Start of a method/block
                                               String structure) throws ArgumentSyntaxException {
        // The structure isn't opened or closed properly
        // Branch: checks a condition
        if (!structure.startsWith("[") || !structure.endsWith("]"))
            // Throws an exception
            throw new ArgumentSyntaxException("Target selector needs to start and end with brackets", input, INVALID_SYNTAX);

        // Remove brackets
        // Calls a method
        final String structureData = structure.substring(1, structure.length() - 1);
        //System.out.println("structure data: " + structureData);

        // Assigns a value
        String currentArgument = "";
        // Loop: repeats a block
        for (int i = 0; i < structureData.length(); i++) {
            // Calls a method
            final char c = structureData.charAt(i);
            // Branch: checks a condition
            if (c == '=') {

                // Replace all unnecessary spaces
                // Calls a method
                currentArgument = currentArgument.trim();

                // Branch: checks a condition
                if (!VALID_ARGUMENTS.contains(currentArgument))
                    // Throws an exception
                    throw new ArgumentSyntaxException("Argument name '" + currentArgument + "' does not exist", input, INVALID_ARGUMENT_NAME);

                // Calls a method
                i = parseArgument(sender, entityFinder, currentArgument, input, structureData, i);
                // Assigns a value
                currentArgument = ""; // Reset current argument
            // Alternative branch of the condition
            } else {
                // Code statement
                currentArgument += c;
            // End of a block/expression
            }
        // End of a block/expression
        }

        // Returns a value to the caller
        return entityFinder;
    // End of a block/expression
    }

    // Code statement
    private static int parseArgument(CommandSender sender,
                                     // Code statement
                                     EntityFinder entityFinder,
                                     // Code statement
                                     String argumentName,
                                     // Code statement
                                     String input,
                                     // Start of a method/block
                                     String structureData, int beginIndex) throws ArgumentSyntaxException {
        // Assigns a value
        final char comma = ',';
        // Calls a method
        final boolean isSimple = SIMPLE_ARGUMENTS.contains(argumentName);

        // Assigns a value
        int finalIndex = beginIndex + 1;
        // Calls a method
        StringBuilder valueBuilder = new StringBuilder();
        // Loop: repeats a block
        for (; finalIndex < structureData.length(); finalIndex++) {
            // Calls a method
            final char c = structureData.charAt(finalIndex);

            // Command is parsed
            // Branch: checks a condition
            if (isSimple && c == comma)
                // Breaks out of the loop/block
                break;

            // Calls a method
            valueBuilder.append(c);
        // End of a block/expression
        }

        // Calls a method
        final String value = valueBuilder.toString().trim();

        //System.out.println("value: " + value);
        // Multiple branching (switch/case)
        switch (argumentName) {
            // Multiple branching (switch/case)
            case "type": {
                // Calls a method
                final boolean include = !value.startsWith("!");
                // Calls a method
                final String entityName = include ? value : value.substring(1);
                // Calls a method
                final EntityType entityType = EntityType.fromKey(entityName);
                // Branch: checks a condition
                if (entityType == null)
                    // Throws an exception
                    throw new ArgumentSyntaxException("Invalid entity name", input, INVALID_ARGUMENT_VALUE);
                // Calls a method
                entityFinder.setEntity(entityType, include ? EntityFinder.ToggleableType.INCLUDE : EntityFinder.ToggleableType.EXCLUDE);
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case "gamemode": {
                // Calls a method
                final boolean include = !value.startsWith("!");
                // Calls a method
                final String gameModeName = include ? value : value.substring(1);
                // Exception handling
                try {
                    // Calls a method
                    final GameMode gameMode = GameMode.valueOf(gameModeName.toUpperCase());
                    // Calls a method
                    entityFinder.setGameMode(gameMode, include ? EntityFinder.ToggleableType.INCLUDE : EntityFinder.ToggleableType.EXCLUDE);
                // Start of a method/block
                } catch (IllegalArgumentException e) {
                    // Throws an exception
                    throw new ArgumentSyntaxException("Invalid entity game mode", input, INVALID_ARGUMENT_VALUE);
                // End of a block/expression
                }
                // Breaks out of the loop/block
                break;
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case "limit":
                // Code statement
                int limit;
                // Exception handling
                try {
                    // Calls a method
                    limit = Integer.parseInt(value);
                    // Calls a method
                    entityFinder.setLimit(limit);
                // Start of a method/block
                } catch (NumberFormatException e) {
                    // Throws an exception
                    throw new ArgumentSyntaxException("Invalid limit number", input, INVALID_ARGUMENT_VALUE);
                // End of a block/expression
                }
                // Branch: checks a condition
                if (limit <= 0) {
                    // Throws an exception
                    throw new ArgumentSyntaxException("Limit must be positive", input, INVALID_ARGUMENT_VALUE);
                // End of a block/expression
                }
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "sort":
                // Exception handling
                try {
                    // Calls a method
                    EntityFinder.EntitySort entitySort = EntityFinder.EntitySort.valueOf(value.toUpperCase());
                    // Calls a method
                    entityFinder.setEntitySort(entitySort);
                // Start of a method/block
                } catch (IllegalArgumentException e) {
                    // Throws an exception
                    throw new ArgumentSyntaxException("Invalid entity sort", input, INVALID_ARGUMENT_VALUE);
                // End of a block/expression
                }
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "level":
                // Exception handling
                try {
                    // Calls a method
                    final Range.Int level = Argument.parse(sender, new ArgumentIntRange(value));
                    // Calls a method
                    entityFinder.setLevel(level);
                // Start of a method/block
                } catch (ArgumentSyntaxException e) {
                    // Throws an exception
                    throw new ArgumentSyntaxException("Invalid level number", input, INVALID_ARGUMENT_VALUE);
                // End of a block/expression
                }
                // Breaks out of the loop/block
                break;
            // Multiple branching (switch/case)
            case "distance":
                // Exception handling
                try {
                    // Calls a method
                    final Range.Int distance = Argument.parse(sender, new ArgumentIntRange(value));
                    // Calls a method
                    entityFinder.setDistance(distance);
                // Start of a method/block
                } catch (ArgumentSyntaxException e) {
                    // Throws an exception
                    throw new ArgumentSyntaxException("Invalid level number", input, INVALID_ARGUMENT_VALUE);
                // End of a block/expression
                }
                // Breaks out of the loop/block
                break;
        // End of a block/expression
        }

        // Returns a value to the caller
        return finalIndex;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isOnlySingleEntity() {
        // Returns a value to the caller
        return onlySingleEntity;
    // End of a block/expression
    }

    // Start of a method/block
    public boolean isOnlyPlayers() {
        // Returns a value to the caller
        return onlyPlayers;
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public String toString() {
        // Branch: checks a condition
        if (onlySingleEntity) {
            // Branch: checks a condition
            if (onlyPlayers) {
                // Returns a value to the caller
                return String.format("Player<%s>", getId());
            // End of a block/expression
            }
            // Returns a value to the caller
            return String.format("Entity<%s>", getId());
        // End of a block/expression
        }
        // Branch: checks a condition
        if (onlyPlayers) {
            // Returns a value to the caller
            return String.format("Players<%s>", getId());
        // End of a block/expression
        }
        // Returns a value to the caller
        return String.format("Entities<%s>", getId());
    // End of a block/expression
    }

    // Start of a method/block
    private static EntityFinder.TargetSelector toTargetSelector(String selectorVariable) {
        // Returns a value to the caller
        return switch (selectorVariable) {
            // Multiple branching (switch/case)
            case "@p" -> EntityFinder.TargetSelector.NEAREST_PLAYER;
            // Multiple branching (switch/case)
            case "@n" -> EntityFinder.TargetSelector.NEAREST_ENTITY;
            // Multiple branching (switch/case)
            case "@r" -> EntityFinder.TargetSelector.RANDOM_PLAYER;
            // Multiple branching (switch/case)
            case "@a" -> EntityFinder.TargetSelector.ALL_PLAYERS;
            // Multiple branching (switch/case)
            case "@e" -> EntityFinder.TargetSelector.ALL_ENTITIES;
            // Multiple branching (switch/case)
            case "@s" -> EntityFinder.TargetSelector.SELF;
            // Multiple branching (switch/case)
            default -> throw new IllegalStateException("Weird selector variable: " + selectorVariable);
        // End of a block/expression
        };
    // End of a block/expression
    }
// End of a block/expression
}
