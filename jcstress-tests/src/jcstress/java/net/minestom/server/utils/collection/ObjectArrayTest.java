// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import org.openjdk.jcstress.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jcstress.infra.results.LL_Result;

// Import statique d'un membre
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation pour l'élément suivant
@JCStressTest
// Annotation pour l'élément suivant
@Outcome(id = "1, 2", expect = ACCEPTABLE)
// Annotation pour l'élément suivant
@State
// Déclaration de type (classe/interface/enum/record)
public class ObjectArrayTest {
    // Appelle une méthode
    private final ObjectArray<Integer> array = ObjectArray.concurrent();

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor1() {
        // Appelle une méthode
        array.set(255, 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor2() {
        // Appelle une méthode
        array.set(32_000, 2);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Arbiter
    // Début d'une méthode/d'un bloc
    public void arbiter(LL_Result r) {
        // Appelle une méthode
        r.r1 = array.get(255);
        // Appelle une méthode
        r.r2 = array.get(32_000);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
