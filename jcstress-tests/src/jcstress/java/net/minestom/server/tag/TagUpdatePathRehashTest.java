// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.openjdk.jcstress.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jcstress.infra.results.L_Result;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation pour l'élément suivant
@JCStressTest
// Annotation pour l'élément suivant
@Outcome(id = "2000", expect = ACCEPTABLE)
// Annotation pour l'élément suivant
@State
// Déclaration de type (classe/interface/enum/record)
public class TagUpdatePathRehashTest {
    // Appelle une méthode
    private static final Tag<Integer> TAG = Tag.Integer("key").path("path").defaultValue(0);

    // Affecte une valeur
    private static final int MAX_SIZE = 500;
    // Instruction de code
    private static final List<Tag<Integer>> TAGS;

    // Début d'une méthode/d'un bloc
    static {
        // Affecte une valeur
        List<Tag<Integer>> tags = new ArrayList<>();
        // Boucle : répète un bloc
        for (int i = 0; i < MAX_SIZE; i++) {
            // Appelle une méthode
            tags.add(Tag.Integer("key" + i).path("path"));
        // Fin d'un bloc/d'une expression
        }
        // Appelle une méthode
        TAGS = List.copyOf(tags);
    // Fin d'un bloc/d'une expression
    }

    // Appelle une méthode
    private final TagHandler handler = TagHandler.newHandler();

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor1() {
        // Boucle : répète un bloc
        for (int i = 0; i < 1000; i++) {
            // Appelle une méthode
            handler.updateTag(TAG, integer -> integer + 1);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor2() {
        // Boucle : répète un bloc
        for (int i = 0; i < 1000; i++) {
            // Appelle une méthode
            handler.updateTag(TAG, integer -> integer + 1);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor3() {
        // May be able to disturb actor1/2
        // Boucle : répète un bloc
        for (int i = 0; i < MAX_SIZE; i++) {
            // Appelle une méthode
            handler.setTag(TAGS.get(i), i);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Arbiter
    // Début d'une méthode/d'un bloc
    public void arbiter(L_Result r) {
        // Appelle une méthode
        r.r1 = handler.getTag(TAG);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}

