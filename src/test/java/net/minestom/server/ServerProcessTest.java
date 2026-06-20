// Déclaration du paquet de ce fichier
package net.minestom.server;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertThrows;
// Import statique d'un membre
import static org.junit.jupiter.api.Assumptions.assumeTrue;

// Déclaration de type (classe/interface/enum/record)
public class ServerProcessTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void init() {
        // These like to fail on github actions
        // Appelle une méthode
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Affecte une valeur
        AtomicReference<ServerProcess> process = new AtomicReference<>();
        // Appelle une méthode
        assertDoesNotThrow(() -> process.set(MinecraftServer.updateProcess()));
        // Appelle une méthode
        assertDoesNotThrow(() -> process.get().start(new InetSocketAddress("localhost", 25565)));
        // Appelle une méthode
        assertThrows(Exception.class, () -> process.get().start(new InetSocketAddress("localhost", 25566)));
        // Appelle une méthode
        assertDoesNotThrow(() -> process.get().stop());
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void tick() {
        // These like to fail on github actions
        // Appelle une méthode
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Appelle une méthode
        var process = MinecraftServer.updateProcess();
        // Appelle une méthode
        process.start(new InetSocketAddress("localhost", 25565));
        // Appelle une méthode
        var ticker = process.ticker();
        // Appelle une méthode
        assertDoesNotThrow(() -> ticker.tick(System.nanoTime()));
        // Appelle une méthode
        assertDoesNotThrow(process::stop);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
