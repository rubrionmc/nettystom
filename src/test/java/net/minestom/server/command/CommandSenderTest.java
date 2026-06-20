// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.kyori.adventure.identity.Identity;
// Import d'une classe nécessaire
import net.kyori.adventure.text.Component;
// Import d'une classe nécessaire
import net.kyori.adventure.text.format.NamedTextColor;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagHandler;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertNull;

// Déclaration de type (classe/interface/enum/record)
public class CommandSenderTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testMessageSending() {
        // Appelle une méthode
        SenderTest sender = new SenderTest();

        // Appelle une méthode
        assertNull(sender.getMostRecentMessage());

        // Appelle une méthode
        sender.sendMessage("Hey!!");
        // Appelle une méthode
        assertEquals(sender.getMostRecentMessage(), Component.text("Hey!!"));

        // Appelle une méthode
        sender.sendMessage(new String[]{"Message", "Sending", "Test"});
        // Appelle une méthode
        assertEquals(sender.getMostRecentMessage(), Component.text("Test"));

        // Appelle une méthode
        sender.sendMessage(Component.text("Message test!", NamedTextColor.GREEN));
        // Appelle une méthode
        assertEquals(sender.getMostRecentMessage(), Component.text("Message test!", NamedTextColor.GREEN));
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    private static final class SenderTest implements CommandSender {

        // Appelle une méthode
        private final TagHandler handler = TagHandler.newHandler();

        // Affecte une valeur
        private Component mostRecentMessage = null;

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public TagHandler tagHandler() {
            // Renvoie une valeur à l'appelant
            return handler;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void sendMessage(Component message) {
            // Affecte une valeur
            mostRecentMessage = message;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public @Nullable Component getMostRecentMessage() {
            // Renvoie une valeur à l'appelant
            return mostRecentMessage;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Identity identity() {
            // Renvoie une valeur à l'appelant
            return Identity.nil();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
