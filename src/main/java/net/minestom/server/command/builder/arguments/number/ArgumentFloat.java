// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.number;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;

// Déclaration de type (classe/interface/enum/record)
public class ArgumentFloat extends ArgumentNumber<Float> {

    // Début d'une méthode/d'un bloc
    public ArgumentFloat(String id) {
        // Accès à l'objet courant/parent
        super(id, ArgumentParserType.FLOAT, Float::parseFloat, (s, radix) -> (float) Integer.parseInt(s, radix),
                // Instruction de code
                NetworkBuffer.FLOAT, Float::compare);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Float<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
