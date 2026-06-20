// Déclaration du paquet de ce fichier
package net.minestom.server.utils.collection;

// Import d'une classe nécessaire
import net.minestom.server.ServerFlag;
// Import d'une classe nécessaire
import org.jctools.queues.MessagePassingQueue;
// Import d'une classe nécessaire
import org.jctools.queues.MpmcUnboundedXaddArrayQueue;
// Import d'une classe nécessaire
import org.jctools.queues.MpscArrayQueue;
// Import d'une classe nécessaire
import org.jctools.queues.MpscUnboundedXaddArrayQueue;
// Import d'une classe nécessaire
import org.jctools.queues.atomic.MpmcAtomicArrayQueue;
// Import d'une classe nécessaire
import org.jctools.queues.atomic.MpscAtomicArrayQueue;
// Import d'une classe nécessaire
import org.jctools.queues.atomic.MpscUnboundedAtomicArrayQueue;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;

// Annotation pour l'élément suivant
@ApiStatus.Internal
// Déclaration de type (classe/interface/enum/record)
public final class ConcurrentMessageQueues {

    // Début d'une méthode/d'un bloc
    public static <T> MessagePassingQueue<T> mpscArrayQueue(int capacity) {
        // Renvoie une valeur à l'appelant
        return ServerFlag.UNSAFE_COLLECTIONS ? new MpscArrayQueue<>(capacity) : new MpscAtomicArrayQueue<>(capacity);
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public static <T> MessagePassingQueue<T> mpscUnboundedArrayQueue(int chunkSize) {
        // Renvoie une valeur à l'appelant
        return ServerFlag.UNSAFE_COLLECTIONS ? new MpscUnboundedXaddArrayQueue<>(chunkSize) : new MpscUnboundedAtomicArrayQueue<>(chunkSize);
    // Fin d'un bloc/d'une expression
    }

    // Atomic is bounded; no unbounded atomic variant exists that is MPMC.
    // Début d'une méthode/d'un bloc
    public static <T> MessagePassingQueue<T> mpmcSpecialUnboundedArrayQueue(int value) {
        // Renvoie une valeur à l'appelant
        return ServerFlag.UNSAFE_COLLECTIONS ? new MpmcUnboundedXaddArrayQueue<>(value) : new MpmcAtomicArrayQueue<>(value);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}