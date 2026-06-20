// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Arrays;
// Import d'une classe nécessaire
import java.util.List;

// Déclaration de type (classe/interface/enum/record)
final class ObjectArrayImpl {
    // Déclaration de type (classe/interface/enum/record)
    static final class SingleThread<T> implements ObjectArray<T> {
        // Instruction de code
        private T[] array;
        // Affecte une valeur
        private int max = -1;

        // Début d'une méthode/d'un bloc
        SingleThread(int size) {
            //noinspection unchecked
            // Accès à l'objet courant/parent
            this.array = (T[]) new Object[size];
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @UnknownNullability T get(int index) {
            // Affecte une valeur
            final T[] array = this.array;
            // Renvoie une valeur à l'appelant
            return index < array.length ? array[index] : null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void set(int index, @Nullable T object) {
            // Embranchement : vérifie une condition
            if (object == null) {
                // Appelle une méthode
                remove(index);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            T[] array = this.array;
            // Embranchement : vérifie une condition
            if (index >= array.length) {
                // Affecte une valeur
                final int newLength = index * 2 + 1;
                // Accès à l'objet courant/parent
                this.array = array = Arrays.copyOf(array, newLength);
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            array[index] = object;
            // Accès à l'objet courant/parent
            this.max = Math.max(max, index);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void remove(int index) {
            // Affecte une valeur
            final T[] array = this.array;
            // Embranchement : vérifie une condition
            if (index >= array.length) return; // Will be null anyway
            // Affecte une valeur
            array[index] = null;
            // Now we need to backtrack the max index,
            // For example [0, 1, 2, null, 4] removing 4 requires us to backtrack past the null
            // Affecte une valeur
            final int max = this.max;
            // Embranchement : vérifie une condition
            if (max == index) {
                // Affecte une valeur
                int lastNotNull = max - 1;
                // Boucle : répète un bloc
                while (lastNotNull >= 0 && array[lastNotNull] == null) {
                    // Instruction de code
                    lastNotNull--;
                // Fin d'un bloc/d'une expression
                }
                // Accès à l'objet courant/parent
                this.max = lastNotNull;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void trim() {
            // Accès à l'objet courant/parent
            this.array = Arrays.copyOf(array, max + 1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @UnknownNullability T [] arrayCopy(Class<T> type) {
            //noinspection unchecked,rawtypes
            // Renvoie une valeur à l'appelant
            return (T[]) Arrays.<T, T>copyOf(array, max + 1, (Class) type.arrayType());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<T> toList() {
            // Trim the array to the maximum size, it internally will be copied regardless.
            // Appelle une méthode
            final T[] array = Arrays.copyOf(this.array, max + 1);
            // Renvoie une valeur à l'appelant
            return List.of(array);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    static final class Concurrent<T> implements ObjectArray<T> {
        // Instruction de code
        private volatile T[] array;
        // Affecte une valeur
        private volatile int max = -1;

        // Début d'une méthode/d'un bloc
        Concurrent(int size) {
            //noinspection unchecked
            // Accès à l'objet courant/parent
            this.array = (T[]) new Object[size];
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @UnknownNullability T get(int index) {
            // Affecte une valeur
            final T[] array = this.array;
            // Renvoie une valeur à l'appelant
            return index < array.length ? array[index] : null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public synchronized void set(int index, @Nullable T object) {
            // Embranchement : vérifie une condition
            if (object == null) {
                // Appelle une méthode
                remove(index);
                // Renvoie une valeur à l'appelant
                return;
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            T[] array = this.array;
            // Embranchement : vérifie une condition
            if (index >= array.length) {
                // Affecte une valeur
                final int newLength = index * 2 + 1;
                // Accès à l'objet courant/parent
                this.array = array = Arrays.copyOf(array, newLength);
            // Fin d'un bloc/d'une expression
            }
            // Affecte une valeur
            array[index] = object;
            // Accès à l'objet courant/parent
            this.max = Math.max(max, index);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public synchronized void remove(int index) {
            // Affecte une valeur
            final T[] array = this.array;
            // Embranchement : vérifie une condition
            if (index >= array.length) return; // Will be null anyway
            // Affecte une valeur
            array[index] = null;
            // Now we need to backtrack the max index,
            // For example [0, 1, 2, null, 4] removing 4 requires us to backtrack past the null
            // Affecte une valeur
            final int max = this.max;
            // Embranchement : vérifie une condition
            if (max == index) {
                // Affecte une valeur
                int lastNotNull = max - 1;
                // Boucle : répète un bloc
                while (lastNotNull >= 0 && array[lastNotNull] == null) {
                    // Instruction de code
                    lastNotNull--;
                // Fin d'un bloc/d'une expression
                }
                // Accès à l'objet courant/parent
                this.max = lastNotNull;
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public synchronized void trim() {
            // Accès à l'objet courant/parent
            this.array = Arrays.copyOf(array, max + 1);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @UnknownNullability T [] arrayCopy(Class<T> type) {
            //noinspection unchecked,rawtypes
            // Renvoie une valeur à l'appelant
            return (T[]) Arrays.<T, T>copyOf(array, max + 1, (Class) type.arrayType());
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public List<T> toList() {
            // Trim the array to the maximum size, it internally will be copied regardless.
            // Appelle une méthode
            final T[] array = Arrays.copyOf(this.array, this.max + 1);
            // Renvoie une valeur à l'appelant
            return List.of(array);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
