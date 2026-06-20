// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.number;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentInteger extends ArgumentNumber<Integer> {

    // Début d'une méthode/d'un bloc
    public ArgumentInteger(String id) {
        // Accès à l'objet courant/parent
        super(id, ArgumentParserType.INTEGER, Integer::parseInt, Integer::parseInt,
                // Instruction de code
                NetworkBuffer.INT, Integer::compare);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Integer<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
