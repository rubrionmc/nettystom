// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertTrue;

// Déclaration de type (classe/interface/enum/record)
public class SubcommandTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSubCommands() {
        // Appelle une méthode
        var manager = new CommandManager();

        // Appelle une méthode
        var parent = new Command("parent");
        // Appelle une méthode
        var child = new Command("child");

        // Appelle une méthode
        parent.addSubcommand(child);
        // Appelle une méthode
        manager.register(parent);

        // Appelle une méthode
        AtomicBoolean parentExecuted = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean childExecuted = new AtomicBoolean(false);

        // Appelle une méthode
        parent.setDefaultExecutor((sender, context) -> parentExecuted.set(true));
        // Appelle une méthode
        child.setDefaultExecutor((sender, context) -> childExecuted.set(true));

        // Appelle une méthode
        manager.executeServerCommand("parent child");

        // Appelle une méthode
        assertFalse(parentExecuted.get());
        // Appelle une méthode
        assertTrue(childExecuted.get());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testSubCommandConditions() {
        // Appelle une méthode
        var manager = new CommandManager();

        // Appelle une méthode
        var parent = new Command("parent");
        // Appelle une méthode
        var child = new Command("child");

        // Appelle une méthode
        parent.addSubcommand(child);
        // Appelle une méthode
        manager.register(parent);

        // Appelle une méthode
        AtomicBoolean parentConditionTriggered = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean childConditionTriggered = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean parentExecuted = new AtomicBoolean(false);
        // Appelle une méthode
        AtomicBoolean childExecuted = new AtomicBoolean(false);

        // Début d'une méthode/d'un bloc
        parent.setCondition((sender, commandString) -> {
            // Appelle une méthode
            parentConditionTriggered.set(true);
            // Renvoie une valeur à l'appelant
            return true; // Return true so the child's condition has a chance to get tested
        // Fin d'un bloc/d'une expression
        });
        // Début d'une méthode/d'un bloc
        child.setCondition((sender, commandString) -> {
            // Appelle une méthode
            childConditionTriggered.set(true);
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        parent.setDefaultExecutor((sender, context) -> parentExecuted.set(true));
        // Appelle une méthode
        child.setDefaultExecutor((sender, context) -> childExecuted.set(true));

        // Appelle une méthode
        manager.executeServerCommand("parent child");

        // Appelle une méthode
        assertTrue(parentConditionTriggered.get());
        // Appelle une méthode
        assertTrue(childConditionTriggered.get());
        // Appelle une méthode
        assertFalse(parentExecuted.get());
        // Appelle une méthode
        assertFalse(childExecuted.get());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
