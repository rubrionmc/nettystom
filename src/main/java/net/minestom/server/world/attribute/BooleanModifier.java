// Déclaration du paquet de ce fichier
package net.minestom.server.world.attribute;

// Import d'une classe nécessaire
import net.minestom.server.codec.Codec;

// Annotation pour l'élément suivant
@FunctionalInterface
// Déclaration de type (classe/interface/enum/record)
non-sealed interface BooleanModifier extends EnvironmentAttribute.Modifier<Boolean, Boolean> {
    // Appelle une méthode
    BooleanModifier AND = (a, b) -> a && b;
    // Appelle une méthode
    BooleanModifier NAND = (a, b) -> !a || !b;
    // Appelle une méthode
    BooleanModifier OR = (a, b) -> a || b;
    // Appelle une méthode
    BooleanModifier NOR = (a, b) -> !a && !b;
    // Appelle une méthode
    BooleanModifier XOR = (a, b) -> a ^ b;
    // Appelle une méthode
    BooleanModifier XNOR = (a, b) -> a == b;

    // Annotation pour l'élément suivant
    @java.lang.Override
    // Début d'une méthode/d'un bloc
    default Codec<java.lang.Boolean> argumentCodec() {
        // Renvoie une valeur à l'appelant
        return Codec.BOOLEAN;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
