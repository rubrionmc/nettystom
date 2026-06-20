// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import net.kyori.adventure.nbt.CompoundBinaryTag;
// Import d'une classe nécessaire
import org.openjdk.jcstress.annotations.*;
// Import d'une classe nécessaire
import org.openjdk.jcstress.infra.results.L_Result;

// Import d'une classe nécessaire
import java.util.Map;

// Import statique d'un membre
import static net.kyori.adventure.nbt.IntBinaryTag.intBinaryTag;
// Import statique d'un membre
import static org.openjdk.jcstress.annotations.Expect.ACCEPTABLE;

// Annotation pour l'élément suivant
@JCStressTest
// Annotation pour l'élément suivant
@Outcome(id = "actor1", expect = ACCEPTABLE)
// Annotation pour l'élément suivant
@Outcome(id = "actor2", expect = ACCEPTABLE)
// Annotation pour l'élément suivant
@State
// Déclaration de type (classe/interface/enum/record)
public class TagPathOverrideTest {
    // Appelle une méthode
    private static final Tag<Integer> TAG_PATH = Tag.Integer("key").path("path");

    // Appelle une méthode
    private final TagHandler handler = TagHandler.newHandler();

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor1() {
        // Appelle une méthode
        handler.setTag(TAG_PATH, 1);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Actor
    // Début d'une méthode/d'un bloc
    public void actor2() {
        // Appelle une méthode
        handler.setTag(TAG_PATH, 5);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Arbiter
    // Début d'une méthode/d'un bloc
    public void arbiter(L_Result r) {
        // Appelle une méthode
        var compound = handler.asCompound();
        // Embranchement : vérifie une condition
        if (compound.equals(CompoundBinaryTag.from(Map.of("path", CompoundBinaryTag.from(Map.of("key", intBinaryTag(1))))))) {
            // Affecte une valeur
            r.r1 = "actor1";
        // Embranchement : vérifie une condition
        } else if (compound.equals(CompoundBinaryTag.from(Map.of("path", CompoundBinaryTag.from(Map.of("key", intBinaryTag(5))))))) {
            // Affecte une valeur
            r.r1 = "actor2";
        // Branche alternative de la condition
        } else {
            // Affecte une valeur
            r.r1 = compound;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
