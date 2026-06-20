// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft;

// Import d'une classe nécessaire
import net.kyori.adventure.key.InvalidKeyException;
// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.command.CommandSender;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.arguments.Argument;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.exception.ArgumentSyntaxException;
// Import d'une classe nécessaire
import net.minestom.server.instance.block.Block;
// Import d'une classe nécessaire
import net.minestom.server.utils.block.BlockUtils;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentBlockState extends Argument<Block> {

    // Affecte une valeur
    public static final int NO_BLOCK = 1;
    // Affecte une valeur
    public static final int INVALID_BLOCK = 2;
    // Affecte une valeur
    public static final int INVALID_PROPERTY = 3;
    // Affecte une valeur
    public static final int INVALID_PROPERTY_VALUE = 4;

    // Début d'une méthode/d'un bloc
    public ArgumentBlockState(String id) {
        // Accès à l'objet courant/parent
        super(id, true, false);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Block parse(CommandSender sender, String input) throws ArgumentSyntaxException {
        // Renvoie une valeur à l'appelant
        return staticParse(input);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.BLOCK_STATE;
    // Fin d'un bloc/d'une expression
    }

    /**
     * @deprecated use {@link Argument#parse(CommandSender, Argument)}
     */
    // Annotation pour l'élément suivant
    @Deprecated
    // Début d'une méthode/d'un bloc
    public static Block staticParse(String input) throws ArgumentSyntaxException {
        // Appelle une méthode
        final int nbtIndex = input.indexOf("[");
        // Embranchement : vérifie une condition
        if (nbtIndex == 0)
            // Lève une exception
            throw new ArgumentSyntaxException("No block type", input, NO_BLOCK);

        // Embranchement : vérifie une condition
        if (nbtIndex == -1) {
            // Only block name
            // Instruction de code
            Block block;
            // Gestion des exceptions
            try {
                // Appelle une méthode
                block = Block.fromKey(input);
            // Début d'une méthode/d'un bloc
            } catch (InvalidKeyException ignored) {
                // Affecte une valeur
                block = null;
            // Fin d'un bloc/d'une expression
            }
            // Embranchement : vérifie une condition
            if (block == null)
                // Lève une exception
                throw new ArgumentSyntaxException("Invalid block type", input, INVALID_BLOCK);
            // Renvoie une valeur à l'appelant
            return block;
        // Branche alternative de la condition
        } else {
            // Embranchement : vérifie une condition
            if (!input.endsWith("]"))
                // Lève une exception
                throw new ArgumentSyntaxException("Property list need to end with ]", input, INVALID_PROPERTY);
            // Block state
            // Appelle une méthode
            final String blockName = input.substring(0, nbtIndex);
            // Appelle une méthode
            Block block = Block.fromKey(blockName);
            // Embranchement : vérifie une condition
            if (block == null)
                // Lève une exception
                throw new ArgumentSyntaxException("Invalid block type", input, INVALID_BLOCK);

            // Compute properties
            // Appelle une méthode
            final String query = input.substring(nbtIndex);
            // Appelle une méthode
            final var propertyMap = BlockUtils.parseProperties(query);
            // Gestion des exceptions
            try {
                // Renvoie une valeur à l'appelant
                return block.withProperties(propertyMap);
            // Début d'une méthode/d'un bloc
            } catch (IllegalArgumentException e) {
                // Lève une exception
                throw new ArgumentSyntaxException("Invalid property values", input, INVALID_PROPERTY_VALUE);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("BlockState<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
