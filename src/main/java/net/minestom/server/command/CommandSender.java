// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.kyori.adventure.audience.Audience;
// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identified;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.tag.Taggable;

/**
 * Represents something which can send commands to the server.
 * <p>
 * Main implementations are {@link Player} and {@link ConsoleSender}.
 */
// Déclaration de type (classe/interface/enum/record)
public interface CommandSender extends Audience, Taggable, Identified {

    /**
     * Sends a raw string message.
     *
     * @param message the message to send
     */
    // Début d'une méthode/d'un bloc
    default void sendMessage(String message) {
        // Accès à l'objet courant/parent
        this.sendMessage(Component.text(message));
    // Fin d'un bloc/d'une expression
    }

    /**
     * Sends multiple raw string messages.
     *
     * @param messages the messages to send
     */
    // Début d'une méthode/d'un bloc
    default void sendMessage(String [] messages) {
        // Boucle : répète un bloc
        for (String message : messages) {
            // Appelle une méthode
            sendMessage(message);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
