// Déclaration du paquet de ce fichier
package net.minestom.server.network.debug;

// Import d'une classe nécessaire
import net.kyori.adventure.key.Key;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.registry.StaticProtocolObject;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;

// Déclaration de type (classe/interface/enum/record)
public sealed interface DebugSubscription<T> extends StaticProtocolObject<DebugSubscription<T>>, DebugSubscriptions permits DebugSubscriptionImpl {

    // Affecte une valeur
    NetworkBuffer.Type<DebugSubscription<?>> NETWORK_TYPE = NetworkBuffer.VAR_INT
            // Appelle une méthode
            .transform(DebugSubscription::fromId, DebugSubscription::id);

    // Appelle une méthode
    int id();

    // Appelle une méthode
    Key key();

    // Début d'une méthode/d'un bloc
    static @Nullable DebugSubscription<?> fromKey(String key) {
        // Renvoie une valeur à l'appelant
        return DebugSubscriptionImpl.NAMESPACES.get(key);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable DebugSubscription<?> fromKey(Key key) {
        // Renvoie une valeur à l'appelant
        return fromKey(key.asString());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static @Nullable DebugSubscription<?> fromId(int id) {
        // Renvoie une valeur à l'appelant
        return DebugSubscriptionImpl.IDS.get(id);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    static Collection<DebugSubscription<?>> values() {
        // Renvoie une valeur à l'appelant
        return DebugSubscriptionImpl.NAMESPACES.values();
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Event<T>(DebugSubscription<T> subscription, T value) {
        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Affecte une valeur
        public static final NetworkBuffer.Type<DebugSubscription.Event<?>> NETWORK_TYPE = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Event<?> value) {
                // Appelle une méthode
                buffer.write(DebugSubscription.NETWORK_TYPE, value.subscription);
                // Appelle une méthode
                ((DebugSubscriptionImpl<Object>) value.subscription).write(buffer, value.value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Event<?> read(NetworkBuffer buffer) {
                // Appelle une méthode
                var subscription = (DebugSubscriptionImpl<Object>) buffer.read(DebugSubscription.NETWORK_TYPE);
                // Appelle une méthode
                Object value = subscription.read(buffer);
                // Renvoie une valeur à l'appelant
                return new Event<>(subscription, value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Update<T>(DebugSubscription<T> subscription, @Nullable T value) {
        // Annotation pour l'élément suivant
        @SuppressWarnings("unchecked")
        // Affecte une valeur
        public static final NetworkBuffer.Type<DebugSubscription.Update<?>> NETWORK_TYPE = new NetworkBuffer.Type<>() {
            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public void write(NetworkBuffer buffer, Update<?> value) {
                // Appelle une méthode
                buffer.write(DebugSubscription.NETWORK_TYPE, value.subscription);
                // Appelle une méthode
                buffer.write(NetworkBuffer.BOOLEAN, value.value != null);
                // Embranchement : vérifie une condition
                if (value.value != null) ((DebugSubscriptionImpl<Object>) value.subscription).write(buffer, value.value);
            // Fin d'un bloc/d'une expression
            }

            // Annotation pour l'élément suivant
            @Override
            // Début d'une méthode/d'un bloc
            public Update<?> read(NetworkBuffer buffer) {
                // Appelle une méthode
                var subscription = (DebugSubscriptionImpl<Object>) buffer.read(DebugSubscription.NETWORK_TYPE);
                // Appelle une méthode
                boolean hasValue = buffer.read(NetworkBuffer.BOOLEAN);
                // Appelle une méthode
                Object value = hasValue ? subscription.read(buffer) : null;
                // Renvoie une valeur à l'appelant
                return new Update<>(subscription, value);
            // Fin d'un bloc/d'une expression
            }
        // Fin d'un bloc/d'une expression
        };
    // Fin d'un bloc/d'une expression
    }

// Fin d'un bloc/d'une expression
}
