// Déclaration du paquet de ce fichier
package net.minestom.server.command;

// Import d'une classe nécessaire
import net.minestom.server.command.builder.Command;
// Import d'une classe nécessaire
import net.minestom.server.command.builder.CommandContext;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicBoolean;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class CommandTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testNames() {
        // Appelle une méthode
        Command command = new Command("name1", "name2", "name3");

        // Appelle une méthode
        assertEquals("name1", command.getName());
        // Appelle une méthode
        assertArrayEquals(new String[]{"name2", "name3"}, command.getAliases());

        // command#getNames does not have any order guarantee, so that cannot be relied on
        // Appelle une méthode
        assertEquals(Set.of("name1", "name2", "name3"), Set.of(command.getNames()));

        // Appelle une méthode
        assertTrue(Command.isValidName(command, "name1"));
        // Appelle une méthode
        assertTrue(Command.isValidName(command, "name2"));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void testGlobalListener() {
        // Appelle une méthode
        var manager = new CommandManager();

        // Appelle une méthode
        AtomicBoolean hasRun = new AtomicBoolean(false);

        // Affecte une valeur
        var command = new Command("command") {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void globalListener(CommandSender sender, CommandContext context, String command) {
                // Appelle une méthode
                hasRun.set(true);
                // Appelle une méthode
                context.setArg("key", "value", "value");
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };

        // Appelle une méthode
        manager.register(command);

        // Appelle une méthode
        AtomicBoolean checkSet = new AtomicBoolean(false);
        // Appelle une méthode
        command.setDefaultExecutor((sender, context) -> checkSet.set("value".equals(context.get("key"))));

        // Appelle une méthode
        manager.executeServerCommand("command");

        // Appelle une méthode
        assertTrue(hasRun.get());
        // Appelle une méthode
        assertTrue(checkSet.get());

    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
