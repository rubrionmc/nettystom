// Déclaration du paquet de ce fichier
package net.minestom.server.entity.pathfinding;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Point;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Déclaration de type (classe/interface/enum/record)
public final class PPath {
    // Instruction de code
    private final Runnable onComplete;
    // Appelle une méthode
    private final List<PNode> nodes = new ArrayList<>();

    // Instruction de code
    private final double pathVariance;
    // Instruction de code
    private final double maxDistance;
    // Affecte une valeur
    private int index = 0;
    // Appelle une méthode
    private final AtomicReference<State> state = new AtomicReference<>(State.CALCULATING);

    // Début d'une méthode/d'un bloc
    public Point getNext() {
        // Embranchement : vérifie une condition
        if (index + 1 >= nodes.size()) return null;
        // Appelle une méthode
        var current = nodes.get(index + 1);
        // Renvoie une valeur à l'appelant
        return new Vec(current.x(), current.y(), current.z());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public void setState(PPath.State newState) {
        // Appelle une méthode
        state.set(newState);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public enum State {
        // Instruction de code
        CALCULATING,
        // Instruction de code
        FOLLOWING,
        // Instruction de code
        TERMINATING, TERMINATED, COMPUTED, BEST_EFFORT, INVALID
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    State getState() {
        // Renvoie une valeur à l'appelant
        return state.get();
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public List<PNode> getNodes() {
        // Renvoie une valeur à l'appelant
        return nodes;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PPath(double maxDistance, double pathVariance, Runnable onComplete) {
        // Accès à l'objet courant/parent
        this.onComplete = onComplete;
        // Accès à l'objet courant/parent
        this.maxDistance = maxDistance;
        // Accès à l'objet courant/parent
        this.pathVariance = pathVariance;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void runComplete() {
        // Embranchement : vérifie une condition
        if (onComplete != null) onComplete.run();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public String toString() {
        // Renvoie une valeur à l'appelant
        return nodes.toString();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable PNode.Type getCurrentType() {
        // Embranchement : vérifie une condition
        if (index >= nodes.size()) return null;
        // Appelle une méthode
        var current = nodes.get(index);
        // Renvoie une valeur à l'appelant
        return current.getType();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Nullable Point getCurrent() {
        // Embranchement : vérifie une condition
        if (index >= nodes.size()) return null;
        // Appelle une méthode
        var current = nodes.get(index);
        // Renvoie une valeur à l'appelant
        return new Vec(current.x(), current.y(), current.z());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void next() {
        // Embranchement : vérifie une condition
        if (index >= nodes.size()) return;
        // Instruction de code
        index++;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    double maxDistance() {
        // Renvoie une valeur à l'appelant
        return maxDistance;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    double pathVariance() {
        // Renvoie une valeur à l'appelant
        return pathVariance;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
