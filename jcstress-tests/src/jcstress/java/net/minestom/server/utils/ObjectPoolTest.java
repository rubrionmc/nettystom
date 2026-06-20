// Déclaration du paquet de ce fichier
package net.minestom.server.utils;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.PacketVanilla;
// Import d'une classe nécessaire
import org.openjdk.jcstress.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jcstress.infra.results.L_Result;

// Import statique d'un membre
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation pour l'élément suivant
@JCStressTest
// Annotation pour l'élément suivant
@Outcome(id = "1", expect = ACCEPTABLE)
// Annotation pour l'élément suivant
@Outcome(id = "2", expect = ACCEPTABLE)
// Annotation pour l'élément suivant
@State
// Déclaration de type (classe/interface/enum/record)
public class ObjectPoolTest {
    // Affecte une valeur
    private final ObjectPool<NetworkBuffer> pool = PacketVanilla.PACKET_POOL;

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor1() {
        // Appelle une méthode
        var buffer = pool.get();
        // Appelle une méthode
        pool.add(buffer);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor2() {
        // Appelle une méthode
        var buffer = pool.get();
        // Appelle une méthode
        pool.add(buffer);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Arbiter
    // Début d'une méthode/d'un bloc
    public void arbiter(L_Result r) {
        // Appelle une méthode
        r.r1 = pool.count();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
