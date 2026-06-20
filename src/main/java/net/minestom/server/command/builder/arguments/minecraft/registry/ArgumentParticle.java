// Déclaration du paquet de ce fichier
package net.minestom.server.command.builder.arguments.minecraft.registry;

// Import d'une classe nécessaire
import net.minestom.server.command.ArgumentParserType;
// Import d'une classe nécessaire
import net.minestom.server.particle.Particle;

/**
 * Represents an argument giving a {@link Particle}.
 */
// Déclaration de type (classe/interface/enum/record)
public class ArgumentParticle extends ArgumentRegistry<Particle> {

    // Début d'une méthode/d'un bloc
    public ArgumentParticle(String id) {
        // Accès à l'objet courant/parent
        super(id);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public ArgumentParserType parser() {
        // Renvoie une valeur à l'appelant
        return ArgumentParserType.PARTICLE;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Particle getRegistry(String value) {
        // Renvoie une valeur à l'appelant
        return Particle.fromKey(value);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return String.format("Particle<%s>", getId());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
