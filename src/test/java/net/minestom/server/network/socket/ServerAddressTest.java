// Package declaration for this file
package net.minestom.server.network.socket;

// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.io.IOException;
// Import of a required class
import java.net.InetSocketAddress;
// Import of a required class
import java.net.UnixDomainSocketAddress;
// Import of a required class
import java.nio.file.Files;

// Static import of a member
import static org.junit.jupiter.api.Assertions.*;
// Static import of a member
import static org.junit.jupiter.api.Assumptions.assumeTrue;

// Type declaration (class/interface/enum/record)
public class ServerAddressTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inetAddressTest() throws IOException {
        // These like to fail on github actions
        // Calls a method
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Calls a method
        InetSocketAddress address = new InetSocketAddress("localhost", 25565);
        // Calls a method
        var server = new Server();
        // Calls a method
        server.init(address);
        // Calls a method
        assertSame(address, server.socketAddress());
        // Calls a method
        assertEquals(address.getHostString(), server.getAddress());
        // Calls a method
        assertEquals(address.getPort(), server.getPort());

        // Calls a method
        assertDoesNotThrow(server::start);
        // Calls a method
        assertDoesNotThrow(server::stop);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void inetAddressDynamicTest() throws IOException {
        // These like to fail on github actions
        // Calls a method
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Calls a method
        InetSocketAddress address = new InetSocketAddress("localhost", 0);
        // Calls a method
        var server = new Server();
        // Calls a method
        server.init(address);
        // Calls a method
        assertSame(address, server.socketAddress());
        // Calls a method
        assertEquals(address.getHostString(), server.getAddress());
        // Calls a method
        assertNotEquals(address.getPort(), server.getPort());

        // Calls a method
        assertDoesNotThrow(server::start);
        // Calls a method
        assertDoesNotThrow(server::stop);
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void unixAddressTest() throws IOException {
        // These like to fail on github actions
        // Calls a method
        assumeTrue(System.getenv("GITHUB_ACTIONS") == null);

        // Calls a method
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of("minestom.sock");
        // Calls a method
        var server = new Server();
        // Calls a method
        server.init(address);
        // Calls a method
        assertTrue(Files.exists(address.getPath()));
        // Calls a method
        assertSame(address, server.socketAddress());
        // Calls a method
        assertEquals("unix://" + address.getPath(), server.getAddress());
        // Calls a method
        assertEquals(0, server.getPort());

        // Calls a method
        assertDoesNotThrow(server::start);
        // Calls a method
        assertDoesNotThrow(server::stop);
        // Calls a method
        assertFalse(Files.exists(address.getPath()), "The socket file should be deleted");
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void noAddressTest() {
        // Calls a method
        var server = new Server();
        // Calls a method
        assertDoesNotThrow(server::stop);
    // End of a block/expression
    }
// End of a block/expression
}
