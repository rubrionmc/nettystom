// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.Collections;
// Import d'une classe nécessaire
import java.util.IdentityHashMap;
// Import d'une classe nécessaire
import java.util.Set;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.*;

// Déclaration de type (classe/interface/enum/record)
public class ObjectPoolTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void pool() {
        // Affecte une valeur
        var pool = PacketVanilla.PACKET_POOL;
        // Appelle une méthode
        Set<NetworkBuffer> pooledBuffers = Collections.newSetFromMap(new IdentityHashMap<>());
        // Appelle une méthode
        pool.clear();

        // Appelle une méthode
        assertEquals(0, pool.count());
        // Appelle une méthode
        var buffer = pool.get();
        // Appelle une méthode
        pooledBuffers.add(buffer);

        // Appelle une méthode
        buffer = pool.get();
        // Appelle une méthode
        assertTrue(pooledBuffers.add(buffer));

        // Appelle une méthode
        pool.add(buffer);
        // Appelle une méthode
        assertEquals(1, pool.count());
        // Appelle une méthode
        buffer = pool.get();
        // Appelle une méthode
        assertEquals(0, pool.count());
        // Appelle une méthode
        assertFalse(pooledBuffers.add(buffer));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void autoClose() {
        // Affecte une valeur
        var pool = PacketVanilla.PACKET_POOL;
        // Appelle une méthode
        assertEquals(0, pool.count());
        // Gestion des exceptions
        try (var ignored = pool.hold()) {
            // Appelle une méthode
            assertEquals(0, pool.count());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(1, pool.count());

        // Gestion des exceptions
        try (var ignored = pool.hold()) {
            // Appelle une méthode
            assertEquals(0, pool.count());
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        assertEquals(1, pool.count());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
