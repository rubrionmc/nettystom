// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collections;
// Import d'une classe nécessaire
import java.util.Iterator;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Set;
// Import d'une classe nécessaire
import java.util.concurrent.CopyOnWriteArraySet;

// Déclaration de type (classe/interface/enum/record)
final class RegistryTagImpl {

    // Déclaration de type (classe/interface/enum/record)
    record Empty() implements RegistryTag<Object> {
        // Appelle une méthode
        public static final Empty INSTANCE = new Empty();

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable TagKey<Object> key() {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean contains(RegistryKey<Object> value) {
            // Renvoie une valeur à l'appelant
            return false;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<RegistryKey<Object>> iterator() {
            // Renvoie une valeur à l'appelant
            return Collections.emptyIterator();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int size() {
            // Renvoie une valeur à l'appelant
            return 0;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    /**
     * A tag that is backed by a registry.
     */
    // Déclaration de type (classe/interface/enum/record)
    static final class Backed<T> implements RegistryTag<T> {
        // Instruction de code
        private final TagKey<T> key;
        // Affecte une valeur
        private final Set<RegistryKey<T>> entries = new CopyOnWriteArraySet<>();

        // Début d'une méthode/d'un bloc
        Backed(TagKey<T> key) {
            // Accès à l'objet courant/parent
            this.key = key;
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        public TagKey<T> key() {
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean contains(RegistryKey<T> value) {
            // Renvoie une valeur à l'appelant
            return entries.contains(value instanceof RegistryKeyImpl<T> key ? key : new RegistryKeyImpl<>(value.key()));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int size() {
            // Renvoie une valeur à l'appelant
            return entries.size();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<RegistryKey<T>> iterator() {
            // Renvoie une valeur à l'appelant
            return entries.iterator();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        void add(RegistryKey<T> key) {
            // Embranchement : vérifie une condition
            if (entries.add(key))
                // Appelle une méthode
                invalidate();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @ApiStatus.Internal
        // Début d'une méthode/d'un bloc
        void remove(RegistryKey<T> key) {
            // Embranchement : vérifie une condition
            if (entries.remove(key))
                // Appelle une méthode
                invalidate();
        // Fin d'un bloc/d'une expression
        }

        // Début d'une méthode/d'un bloc
        private void invalidate() {
            // Appelle une méthode
            var process = MinecraftServer.process();
            // Embranchement : vérifie une condition
            if (process == null) return;
            // Appelle une méthode
            process.connection().invalidateTags();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Direct<T>(List<RegistryKey<T>> keys) implements RegistryTag<T> {
        // Début d'une méthode/d'un bloc
        public Direct {
            // Appelle une méthode
            keys = List.copyOf(keys);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public @Nullable TagKey<T> key() {
            // Renvoie une valeur à l'appelant
            return null;
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public boolean contains(RegistryKey<T> value) {
            // Renvoie une valeur à l'appelant
            return keys.contains(value instanceof RegistryKeyImpl<T> key ? key : new RegistryKeyImpl<>(value.key()));
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Iterator<RegistryKey<T>> iterator() {
            // Renvoie une valeur à l'appelant
            return keys.iterator();
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public int size() {
            // Renvoie une valeur à l'appelant
            return keys.size();
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
