// Déclaration du paquet de ce fichier
package net.minestom.server.tag;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Range;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.function.Consumer;

// Déclaration de type (classe/interface/enum/record)
sealed interface StaticIntMap<T extends @UnknownNullability Object> permits StaticIntMap.Array {

    // Appelle une méthode
    T get(@Range(from = 0, to = Integer.MAX_VALUE) int key);

    // Appelle une méthode
    void forValues(Consumer<T> consumer);

    // Appelle une méthode
    StaticIntMap<T> copy();

    // Methods potentially causing re-hashing

    // Appelle une méthode
    void put(@Range(from = 0, to = Integer.MAX_VALUE) int key, T value);

    // Appelle une méthode
    void remove(@Range(from = 0, to = Integer.MAX_VALUE) int key);

    // Appelle une méthode
    void updateContent(StaticIntMap<T> content);

    // Déclaration de type (classe/interface/enum/record)
    final class Array<T extends @UnknownNullability Object> implements StaticIntMap<T> {
        // Affecte une valeur
        private static final Object[] EMPTY_ARRAY = new Object[0];

        // Instruction de code
        private T[] array;

        // Début d'une méthode/d'un bloc
        public Array(T[] array) {
            // Accès à l'objet courant/parent
            this.array = array;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public Array() {
            //noinspection unchecked
            // Accès à l'objet courant/parent
            this.array = (T[]) EMPTY_ARRAY;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable T get(int key) {
            // Affecte une valeur
            final T[] array = this.array;
            // Renvoie une valeur à l'appelant
            return key < array.length ? array[key] : null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void forValues(Consumer<T> consumer) {
            // Affecte une valeur
            final T[] array = this.array;
            // Boucle : répète un bloc
            for (T value : array) {
                // Embranchement : vérifie une condition
                if (value != null) consumer.accept(value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public StaticIntMap<T> copy() {
            // Renvoie une valeur à l'appelant
            return new Array<>(array.clone());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void put(int key, T value) {
            // Affecte une valeur
            T[] array = this.array;
            // Embranchement : vérifie une condition
            if (key >= array.length) {
                // Appelle une méthode
                array = updateArray(Arrays.copyOf(array, key * 2 + 1));
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            array[key] = value;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void updateContent(StaticIntMap<T> content) {
            // Embranchement : vérifie une condition
            if (content instanceof StaticIntMap.Array<T> arrayMap) {
                // Appelle une méthode
                updateArray(arrayMap.array.clone());
            // Branche alternative de la condition
            } else {
                // Lève une exception
                throw new IllegalArgumentException("Invalid content type: " + content.getClass());
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void remove(int key) {
            // Affecte une valeur
            T[] array = this.array;
            // Embranchement : vérifie une condition
            if (key < array.length) array[key] = null;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        T[] updateArray(T[] result) {
            // Accès à l'objet courant/parent
            this.array = result;
            // Renvoie une valeur à l'appelant
            return result;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
