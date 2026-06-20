// Déclaration du paquet de ce fichier
package net.minestom.server.registry;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.utils.Either;
// Import d'une classe nécessaire
import net.minestom.server.utils.validate.Check;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;

// Déclaration de type (classe/interface/enum/record)
final class RegistryNetworkTypes {

    // Déclaration de type (classe/interface/enum/record)
    record RegistryKeyImpl<T>(Registries.Selector<T> selector) implements NetworkBuffer.Type<RegistryKey<T>> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, RegistryKey<T> value) {
            // Appelle une méthode
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Appelle une méthode
            final var registry = selector.select(registries);
            // Appelle une méthode
            final int id = registry.getId(value);
            // Appelle une méthode
            Check.stateCondition(id == -1, "Key {0} is not registered in registry {1}", value, registry.key());
            // Appelle une méthode
            buffer.write(NetworkBuffer.VAR_INT, id);
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public RegistryKey<T> read(NetworkBuffer buffer) {
            // Appelle une méthode
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Appelle une méthode
            final var registry = selector.select(registries);
            // Appelle une méthode
            final int id = buffer.read(NetworkBuffer.VAR_INT);
            // Appelle une méthode
            final var key = registry.getKey(id);
            // Appelle une méthode
            Check.stateCondition(key == null, "Unknown id {0} for registry {1}", id - 1, registry.key());
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record HolderNetworkTypeImpl<T>(
            // Instruction de code
            Registries.Selector<T> selector,
            // Instruction de code
            NetworkBuffer.Type<T> registryNetworkType
    // Début d'une méthode/d'un bloc
    ) implements NetworkBuffer.Type<Holder<T>> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, Holder<T> value) {
            // Appelle une méthode
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Appelle une méthode
            final var registry = selector.select(registries);
            // Embranchement multiple (switch/case)
            switch (value.unwrap()) {
                // Embranchement multiple (switch/case)
                case Either.Left(RegistryKey<T> key) -> {
                    // Appelle une méthode
                    final int id = registry.getId(key);
                    // Appelle une méthode
                    Check.stateCondition(id == -1, "Key {0} is not registered in registry {1}", key, registry.key());
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.VAR_INT, id + 1);
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case Either.Right(T direct) -> {
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.VAR_INT, 0);
                    // Appelle une méthode
                    buffer.write(registryNetworkType, direct);
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public Holder<T> read(NetworkBuffer buffer) {
            // Appelle une méthode
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Appelle une méthode
            final var registry = selector.select(registries);
            // Appelle une méthode
            final int id = buffer.read(NetworkBuffer.VAR_INT);
            // Embranchement : vérifie une condition
            if (id == 0) //noinspection unchecked
                // Renvoie une valeur à l'appelant
                return (Holder<T>) buffer.read(registryNetworkType);

            // Appelle une méthode
            final var key = registry.getKey(id - 1);
            // Appelle une méthode
            Check.stateCondition(key == null, "Unknown id {0} for registry {1}", id - 1, registry.key());
            // Renvoie une valeur à l'appelant
            return key;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record RegistryTagImpl<T>(Registries.Selector<T> selector) implements NetworkBuffer.Type<RegistryTag<T>> {
        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public void write(NetworkBuffer buffer, RegistryTag<T> value) {
            // Embranchement multiple (switch/case)
            switch (value) {
                // Embranchement multiple (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Backed<T> backed -> {
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.VAR_INT, 0);
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.KEY, backed.key().key());
                // Fin d'un bloc/d'une expression
                }
                // Embranchement multiple (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Empty() -> buffer.write(NetworkBuffer.VAR_INT, 1);
                // Embranchement multiple (switch/case)
                case net.minestom.server.registry.RegistryTagImpl.Direct(var entries) -> {
                    // Appelle une méthode
                    final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
                    // Appelle une méthode
                    final var registry = selector.select(registries);
                    // Appelle une méthode
                    buffer.write(NetworkBuffer.VAR_INT, entries.size() + 1);
                    // Boucle : répète un bloc
                    for (RegistryKey<T> key : entries) {
                        // Appelle une méthode
                        final int id = registry.getId(key);
                        // Appelle une méthode
                        Check.stateCondition(id == -1, "Key {0} is not registered in registry {1}", key, registry.key());
                        // Appelle une méthode
                        buffer.write(NetworkBuffer.VAR_INT, id);
                    // Fin d'un bloc/d'une expression
                    }
                // Fin d'un bloc/d'une expression
                }
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }

        // Annotation pour l'élément suivant
        @Override
        // Début d'une méthode/d'un bloc
        public RegistryTag<T> read(NetworkBuffer buffer) {
            // Appelle une méthode
            final var registries = Objects.requireNonNull(buffer.registries(), "Buffer is missing registries");
            // Appelle une méthode
            final var registry = selector.select(registries);
            // Appelle une méthode
            int count = buffer.read(NetworkBuffer.VAR_INT) - 1;
            // Embranchement : vérifie une condition
            if (count < 0) {
                // Appelle une méthode
                final var key = buffer.read(NetworkBuffer.KEY);
                // Appelle une méthode
                final var tag = registry.getTag(key);
                // Appelle une méthode
                Check.stateCondition(tag == null, "No such tag {0} for registry {1}", key, registry.key());
                // Renvoie une valeur à l'appelant
                return tag;
            // Embranchement : vérifie une condition
            } else if (count == 0) {
                // Renvoie une valeur à l'appelant
                return RegistryTag.empty();
            // Branche alternative de la condition
            } else {
                // Affecte une valeur
                final List<RegistryKey<T>> keys = new ArrayList<>(count);
                // Boucle : répète un bloc
                for (int i = 0; i < count; i++) {
                    // Appelle une méthode
                    final int id = buffer.read(NetworkBuffer.VAR_INT);
                    // Appelle une méthode
                    final var key = registry.getKey(id);
                    // Appelle une méthode
                    Check.stateCondition(key == null, "Unknown id {0} for registry {1}", id, registry.key());
                    // Appelle une méthode
                    keys.add(key);
                // Fin d'un bloc/d'une expression
                }
                // Renvoie une valeur à l'appelant
                return new net.minestom.server.registry.RegistryTagImpl.Direct<>(keys);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
