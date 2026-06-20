// Déclaration du paquet de ce fichier
package net.minestom.server.network.socket;

// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.io.IOException;
// Import d'une classe nécessaire
import java.net.InetSocketAddress;
// Import d'une classe nécessaire
import java.net.UnixDomainSocketAddress;
// Import d'une classe nécessaire
import java.nio.file.Files;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;
// Import statique d'un membre
import static org.junit.jupiter.api.Assumptions.assumeTrue;

// Déclaration de type (classe/interface/enum/record)
public class ServerAddressTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inetAddressTest() throws IOException {
        // These like to fail on github actions
        // Appelle une méthode
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Appelle une méthode
        InetSocketAddress address = new InetSocketAddress("localhost", 25565);
        // Appelle une méthode
        var server = new Server();
        // Appelle une méthode
        server.init(address);
        // Appelle une méthode
        assertSame(address, server.socketAddress());
        // Appelle une méthode
        assertEquals(address.getHostString(), server.getAddress());
        // Appelle une méthode
        assertEquals(address.getPort(), server.getPort());

        // Appelle une méthode
        assertDoesNotThrow(server::start);
        // Appelle une méthode
        assertDoesNotThrow(server::stop);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void inetAddressDynamicTest() throws IOException {
        // These like to fail on github actions
        // Appelle une méthode
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Appelle une méthode
        InetSocketAddress address = new InetSocketAddress("localhost", 0);
        // Appelle une méthode
        var server = new Server();
        // Appelle une méthode
        server.init(address);
        // Appelle une méthode
        assertSame(address, server.socketAddress());
        // Appelle une méthode
        assertEquals(address.getHostString(), server.getAddress());
        // Appelle une méthode
        assertNotEquals(address.getPort(), server.getPort());

        // Appelle une méthode
        assertDoesNotThrow(server::start);
        // Appelle une méthode
        assertDoesNotThrow(server::stop);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void unixAddressTest() throws IOException {
        // These like to fail on github actions
        // Appelle une méthode
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Appelle une méthode
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of("minestom.sock");
        // Appelle une méthode
        var server = new Server();
        // Appelle une méthode
        server.init(address);
        // Appelle une méthode
        assertTrue(Files.exists(address.getPath()));
        // Appelle une méthode
        assertSame(address, server.socketAddress());
        // Appelle une méthode
        assertEquals("unix://" + address.getPath(), server.getAddress());
        // Appelle une méthode
        assertEquals(0, server.getPort());

        // Appelle une méthode
        assertDoesNotThrow(server::start);
        // Appelle une méthode
        assertDoesNotThrow(server::stop);
        // Appelle une méthode
        assertFalse(Files.exists(address.getPath()), "The socket file should be deleted");
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void noAddressTest() {
        // Appelle une méthode
        var server = new Server();
        // Appelle une méthode
        assertDoesNotThrow(server::stop);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
