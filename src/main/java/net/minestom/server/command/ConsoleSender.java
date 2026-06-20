// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.kyori.adventure.pointer.Pointers;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;

/**
 * Represents the console when sending a command to the server.
 */
// Déclaration de type (classe/interface/enum/record)
public class ConsoleSender implements CommandSender {
    // Appelle une méthode
    private static final ComponentLogger LOGGER = ComponentLogger.logger(ConsoleSender.class);

    // Appelle une méthode
    private final TagHandler tagHandler = TagHandler.newHandler();

    // Appelle une méthode
    private final Identity identity = Identity.nil();
    // Affecte une valeur
    private final Pointers pointers = Pointers.builder()
            // Instruction de code
            .withStatic(Identity.UUID, this.identity.uuid())
            // Appelle une méthode
            .build();

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendMessage(String message) {
        // Appelle une méthode
        LOGGER.info(message);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public void sendMessage(Component message) {
        // Appelle une méthode
        LOGGER.info(message);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public TagHandler tagHandler() {
        // Renvoie une valeur à l'appelant
        return tagHandler;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Identity identity() {
        // Renvoie une valeur à l'appelant
        return this.identity;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public Pointers pointers() {
        // Renvoie une valeur à l'appelant
        return this.pointers;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
