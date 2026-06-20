// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.Range;
// Import d'une classe nécessaire
import net.minestom.server.utils.StringUtils;
// Import d'une classe nécessaire
import net.minestom.server.utils.entity.EntityFinder;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.UUID;
// Import d'une classe nécessaire
import java.util.regex.Pattern;

/**
 * Represents the target selector argument.
 * <a href="https://minecraft.wiki/w/Target_selectors">Target selectors</a>
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentEntity extends Argument<EntityFinder> {

    // Affecte une valeur
    public static final int INVALID_SYNTAX = -2;
    // Affecte une valeur
    public static final int ONLY_SINGLE_ENTITY_ERROR = -3;
    // Affecte une valeur
    public static final int ONLY_PLAYERS_ERROR = -4;
    // Affecte une valeur
    public static final int INVALID_ARGUMENT_NAME = -5;
    // Affecte une valeur
    public static final int INVALID_ARGUMENT_VALUE = -6;

    // Appelle une méthode
    private static final Pattern USERNAME_PATTERN = Pattern.compile("[a-zA-Z0-9_]{1,16}");
    // Affecte une valeur
    private static final String SELECTOR_PREFIX = "@";
    // Appelle une méthode
    private static final List<String> SELECTOR_VARIABLES = Arrays.asList("@p", "@r", "@a", "@e", "@s", "@n");
    // Appelle une méthode
    private static final List<String> PLAYERS_ONLY_SELECTOR = Arrays.asList("@p", "@r", "@a", "@s");
    // Appelle une méthode
    private static final List<String> SINGLE_ONLY_SELECTOR = Arrays.asList("@p", "@r", "@s", "@n");
    // List with all the valid arguments
    // Affecte une valeur
    private static final List<String> VALID_ARGUMENTS = Arrays.asList(
            // Instruction de code
            "x", "y", "z",
            // Instruction de code
            "distance", "dx", "dy", "dz",
            // Instruction de code
            "scores", "tag", "team", "limit", "sort", "level", "gamemode", "name",
            // Instruction de code
            "x_rotation", "y_rotation", "type", "nbt", "advancements", "predicate");

    // List with all the easily parsable arguments which only require reading until a specific character (comma)
    // Affecte une valeur
    private static final List<String> SIMPLE_ARGUMENTS = Arrays.asList(
            // Instruction de code
            "x", "y", "z",
            // Instruction de code
            "distance", "dx", "dy", "dz",
            // Instruction de code
            "scores", "tag", "team", "limit", "sort", "level", "gamemode",
            // Instruction de code
            "x_rotation", "y_rotation", "type", "advancements", "predicate");

    // Instruction de code
    private boolean onlySingleEntity;
    // Instruction de code
    private boolean onlyPlayers;

    // Début d'une méthode/d'un bloc
    public ArgumentEntity(String id) {
        // Accès à l'objet courant/parent
        super(id, true);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ArgumentEntity singleEntity(boolean singleEntity) {
        // Accès à l'objet courant/parent
        this.onlySingleEntity = singleEntity;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public ArgumentEntity onlyPlayers(boolean onlyPlayers) {
        // Accès à l'objet courant/parent
        this.onlyPlayers = onlyPlayers;
        // Renvoie une valeur à l'appelant
        return this;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public EntityFinder parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Renvoie une valeur à l'appelant
        return staticParse(sender, input, onlySingleEntity, onlyPlayers);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.ENTITY;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public byte @Nullable [] nodeProperties() {
        // Renvoie une valeur à l'appelant
        return NetworkBuffer.makeArray(buffer -> {
            // Affecte une valeur
            byte mask = 0;
            // Embranchement : vérifie une condition
            if (this.isOnlySingleEntity()) {
                // Instruction de code
                mask |= 0x01;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (this.isOnlyPlayers()) {
                // Instruction de code
                mask |= 0x02;
            // Fin d'un bloc/d'une expression
            }
            // Appelle une méthode
            buffer.write(NetworkBuffer.BYTE, mask);
        // Fin d'un bloc/d'une expression
        });
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link Argument#parse(CommandSender, Argument)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Instruction de code
    public static EntityFinder staticParse(CommandSender sender, String input,
                                           // Début d'une méthode/d'un bloc
                                           boolean onlySingleEntity, boolean onlyPlayers) throws ArgumentSyntaxException {
        // Check for raw player name or UUID
        // Embranchement : vérifie une condition
        if (!input.contains(SELECTOR_PREFIX) && !input.contains(StringUtils.SPACE)) {

            // Check if the input is a valid UUID
            // Gestion des exceptions
            try {
                // Appelle une méthode
                final UUID uuid = UUID.fromString(input);
                // Renvoie une valeur à l'appelant
                return new EntityFinder()
                        // Instruction de code
                        .setTargetSelector(EntityFinder.TargetSelector.MINESTOM_UUID)
                        // Appelle une méthode
                        .setConstantUuid(uuid);
            // Début d'une méthode/d'un bloc
            } catch (IllegalArgumentException ignored) {
            // Fin d'un bloc/d'une expression
            }

            // Check if the input is a valid player name
            // Embranchement : vérifie une condition
            if (USERNAME_PATTERN.matcher(input).matches()) {
                // Renvoie une valeur à l'appelant
                return new EntityFinder()
                        // Instruction de code
                        .setTargetSelector(EntityFinder.TargetSelector.MINESTOM_USERNAME)
                        // Appelle une méthode
                        .setConstantName(input);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // The minimum size is always 2 (for the selector variable, ex: @p)
        // Embranchement : vérifie une condition
        if (input.length() < 2)
            // Lève une exception
            throw new ArgumentSyntaxException("Length needs to be > 1", input, INVALID_SYNTAX);

        // The target selector variable always start by '@'
        // Embranchement : vérifie une condition
        if (!input.startsWith(SELECTOR_PREFIX))
            // Lève une exception
            throw new ArgumentSyntaxException("Target selector needs to start with @", input, INVALID_SYNTAX);

        // Appelle une méthode
        final String selectorVariable = input.substring(0, 2);

        // Check if the selector variable used exists
        // Embranchement : vérifie une condition
        if (!SELECTOR_VARIABLES.contains(selectorVariable))
            // Lève une exception
            throw new ArgumentSyntaxException("Invalid selector variable", input, INVALID_SYNTAX);

        // Check if it should only select single entity and if the selector variable valid the condition
        // Embranchement : vérifie une condition
        if (onlySingleEntity && !SINGLE_ONLY_SELECTOR.contains(selectorVariable))
            // Lève une exception
            throw new ArgumentSyntaxException("Argument requires only a single entity", input, ONLY_SINGLE_ENTITY_ERROR);

        // Check if it should only select players and if the selector variable valid the condition
        // Embranchement : vérifie une condition
        if (onlyPlayers && !PLAYERS_ONLY_SELECTOR.contains(selectorVariable))
            // Lève une exception
            throw new ArgumentSyntaxException("Argument requires only players", input, ONLY_PLAYERS_ERROR);

        // Create the EntityFinder which will be used for the rest of the parsing
        // Affecte une valeur
        final EntityFinder entityFinder = new EntityFinder()
                // Appelle une méthode
                .setTargetSelector(toTargetSelector(selectorVariable));

        // The selector is a single selector variable which verify all the conditions
        // Embranchement : vérifie une condition
        if (input.length() == 2)
            // Renvoie une valeur à l'appelant
            return entityFinder;

        // START PARSING THE STRUCTURE
        // Appelle une méthode
        final String structure = input.substring(2);
        // Renvoie une valeur à l'appelant
        return parseStructure(sender, input, entityFinder, structure);
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static EntityFinder parseStructure(CommandSender sender,
                                               // Instruction de code
                                               String input,
                                               // Instruction de code
                                               EntityFinder entityFinder,
                                               // Début d'une méthode/d'un bloc
                                               String structure) throws ArgumentSyntaxException {
        // The structure isn't opened or closed properly
        // Embranchement : vérifie une condition
        if (!structure.startsWith("[") || !structure.endsWith("]"))
            // Lève une exception
            throw new ArgumentSyntaxException("Target selector needs to start and end with brackets", input, INVALID_SYNTAX);

        // Remove brackets
        // Appelle une méthode
        final String structureData = structure.substring(1, structure.length() - 1);
        //System.out.println("structure data: " + structureData);

        // Affecte une valeur
        String currentArgument = "";
        // Boucle : répète un bloc
        for (int i = 0; i < structureData.length(); i++) {
            // Appelle une méthode
            final char c = structureData.charAt(i);
            // Embranchement : vérifie une condition
            if (c == '=') {

                // Replace all unnecessary spaces
                // Appelle une méthode
                currentArgument = currentArgument.trim();

                // Embranchement : vérifie une condition
                if (!VALID_ARGUMENTS.contains(currentArgument))
                    // Lève une exception
                    throw new ArgumentSyntaxException("Argument name '" + currentArgument + "' does not exist", input, INVALID_ARGUMENT_NAME);

                // Appelle une méthode
                i = parseArgument(sender, entityFinder, currentArgument, input, structureData, i);
                // Affecte une valeur
                currentArgument = ""; // Reset current argument
            // Branche alternative de la condition
            } else {
                // Instruction de code
                currentArgument += c;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return entityFinder;
    // Fin d'un bloc/d'une expression
    }

    // Instruction de code
    private static int parseArgument(CommandSender sender,
                                     // Instruction de code
                                     EntityFinder entityFinder,
                                     // Instruction de code
                                     String argumentName,
                                     // Instruction de code
                                     String input,
                                     // Début d'une méthode/d'un bloc
                                     String structureData, int beginIndex) throws ArgumentSyntaxException {
        // Affecte une valeur
        final char comma = ',';
        // Appelle une méthode
        final boolean isSimple = SIMPLE_ARGUMENTS.contains(argumentName);

        // Affecte une valeur
        int finalIndex = beginIndex + 1;
        // Appelle une méthode
        StringBuilder valueBuilder = new StringBuilder();
        // Boucle : répète un bloc
        for (; finalIndex < structureData.length(); finalIndex++) {
            // Appelle une méthode
            final char c = structureData.charAt(finalIndex);

            // Command is parsed
            // Embranchement : vérifie une condition
            if (isSimple && c == comma)
                // Interrompt la boucle/le bloc
                break;

            // Appelle une méthode
            valueBuilder.append(c);
        // Fin d'un bloc/d'une expression
        }

        // Appelle une méthode
        final String value = valueBuilder.toString().trim();

        //System.out.println("value: " + value);
        // Embranchement multiple (switch/case)
        switch (argumentName) {
            // Embranchement multiple (switch/case)
            case "type": {
                // Appelle une méthode
                final boolean include = !value.startsWith("!");
                // Appelle une méthode
                final String entityName = include ? value : value.substring(1);
                // Appelle une méthode
                final EntityType entityType = EntityType.fromKey(entityName);
                // Embranchement : vérifie une condition
                if (entityType == null)
                    // Lève une exception
                    throw new ArgumentSyntaxException("Invalid entity name", input, INVALID_ARGUMENT_VALUE);
                // Appelle une méthode
                entityFinder.setEntity(entityType, include ? EntityFinder.ToggleableType.INCLUDE : EntityFinder.ToggleableType.EXCLUDE);
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case "gamemode": {
                // Appelle une méthode
                final boolean include = !value.startsWith("!");
                // Appelle une méthode
                final String gameModeName = include ? value : value.substring(1);
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final GameMode gameMode = GameMode.valueOf(gameModeName.toUpperCase());
                    // Appelle une méthode
                    entityFinder.setGameMode(gameMode, include ? EntityFinder.ToggleableType.INCLUDE : EntityFinder.ToggleableType.EXCLUDE);
                // Début d'une méthode/d'un bloc
                } catch (IllegalArgumentException e) {
                    // Lève une exception
                    throw new ArgumentSyntaxException("Invalid entity game mode", input, INVALID_ARGUMENT_VALUE);
                // Fin d'un bloc/d'une expression
                }
                // Interrompt la boucle/le bloc
                break;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case "limit":
                // Instruction de code
                int limit;
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    limit = Integer.parseInt(value);
                    // Appelle une méthode
                    entityFinder.setLimit(limit);
                // Début d'une méthode/d'un bloc
                } catch (NumberFormatException e) {
                    // Lève une exception
                    throw new ArgumentSyntaxException("Invalid limit number", input, INVALID_ARGUMENT_VALUE);
                // Fin d'un bloc/d'une expression
                }
                // Embranchement : vérifie une condition
                if (limit <= 0) {
                    // Lève une exception
                    throw new ArgumentSyntaxException("Limit must be positive", input, INVALID_ARGUMENT_VALUE);
                // Fin d'un bloc/d'une expression
                }
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "sort":
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    EntityFinder.EntitySort entitySort = EntityFinder.EntitySort.valueOf(value.toUpperCase());
                    // Appelle une méthode
                    entityFinder.setEntitySort(entitySort);
                // Début d'une méthode/d'un bloc
                } catch (IllegalArgumentException e) {
                    // Lève une exception
                    throw new ArgumentSyntaxException("Invalid entity sort", input, INVALID_ARGUMENT_VALUE);
                // Fin d'un bloc/d'une expression
                }
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "level":
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final Range.Int level = Argument.parse(sender, new ArgumentIntRange(value));
                    // Appelle une méthode
                    entityFinder.setLevel(level);
                // Début d'une méthode/d'un bloc
                } catch (ArgumentSyntaxException e) {
                    // Lève une exception
                    throw new ArgumentSyntaxException("Invalid level number", input, INVALID_ARGUMENT_VALUE);
                // Fin d'un bloc/d'une expression
                }
                // Interrompt la boucle/le bloc
                break;
            // Embranchement multiple (switch/case)
            case "distance":
                // Gestion des exceptions
                try {
                    // Appelle une méthode
                    final Range.Int distance = Argument.parse(sender, new ArgumentIntRange(value));
                    // Appelle une méthode
                    entityFinder.setDistance(distance);
                // Début d'une méthode/d'un bloc
                } catch (ArgumentSyntaxException e) {
                    // Lève une exception
                    throw new ArgumentSyntaxException("Invalid level number", input, INVALID_ARGUMENT_VALUE);
                // Fin d'un bloc/d'une expression
                }
                // Interrompt la boucle/le bloc
                break;
        // Fin d'un bloc/d'une expression
        }

        // Renvoie une valeur à l'appelant
        return finalIndex;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isOnlySingleEntity() {
        // Renvoie une valeur à l'appelant
        return onlySingleEntity;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public boolean isOnlyPlayers() {
        // Renvoie une valeur à l'appelant
        return onlyPlayers;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Embranchement : vérifie une condition
        if (onlySingleEntity) {
            // Embranchement : vérifie une condition
            if (onlyPlayers) {
                // Renvoie une valeur à l'appelant
                return String.format("Player<%s>", getId());
            // Fin d'un bloc/d'une expression
            }
            // Renvoie une valeur à l'appelant
            return String.format("Entity<%s>", getId());
        // Fin d'un bloc/d'une expression
        }
        // Embranchement : vérifie une condition
        if (onlyPlayers) {
            // Renvoie une valeur à l'appelant
            return String.format("Players<%s>", getId());
        // Fin d'un bloc/d'une expression
        }
        // Renvoie une valeur à l'appelant
        return String.format("Entities<%s>", getId());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static EntityFinder.TargetSelector toTargetSelector(String selectorVariable) {
        // Renvoie une valeur à l'appelant
        return switch (selectorVariable) {
            // Embranchement multiple (switch/case)
            case "@p" -> EntityFinder.TargetSelector.NEAREST_PLAYER;
            // Embranchement multiple (switch/case)
            case "@n" -> EntityFinder.TargetSelector.NEAREST_ENTITY;
            // Embranchement multiple (switch/case)
            case "@r" -> EntityFinder.TargetSelector.RANDOM_PLAYER;
            // Embranchement multiple (switch/case)
            case "@a" -> EntityFinder.TargetSelector.ALL_PLAYERS;
            // Embranchement multiple (switch/case)
            case "@e" -> EntityFinder.TargetSelector.ALL_ENTITIES;
            // Embranchement multiple (switch/case)
            case "@s" -> EntityFinder.TargetSelector.SELF;
            // Embranchement multiple (switch/case)
            default -> throw new IllegalStateException("Weird selector variable: " + selectorVariable);
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
