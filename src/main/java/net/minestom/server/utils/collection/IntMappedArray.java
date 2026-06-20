// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Import d'une classe nécessaire
import java.util.AbstractList;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.function.IntFunction;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class IntMappedArray<R> extends AbstractList<R> {
    // Instruction de code
    private final int[] elements;
    // Instruction de code
    private final IntFunction<R> function;

    // Début d'une méthode/d'un bloc
    public IntMappedArray(int[] elements, IntFunction<R> function) {
        // Accès à l'objet courant/parent
        this.elements = elements;
        // Accès à l'objet courant/parent
        this.function = function;
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public R get(int index) {
        // Affecte une valeur
        final int[] elements = this.elements;
        // Appelle une méthode
        Objects.checkIndex(index, elements.length);
        // Renvoie une valeur à l'appelant
        return function.apply(elements[index]);
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public int size() {
        // Renvoie une valeur à l'appelant
        return elements.length;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
