// Package declaration for this file
package net.minestom.server.utils.collection;

// Import of a required class
import net.minestom.server.ServerFlag;
// Import of a required class
import org.jctools.queues.MessagePassingQueue;
// Import of a required class
import org.jctools.queues.MpmcUnboundedXaddArrayQueue;
// Import of a required class
import org.jctools.queues.MpscArrayQueue;
// Import of a required class
import org.jctools.queues.MpscUnboundedXaddArrayQueue;
// Import of a required class
import org.jctools.queues.atomic.MpmcAtomicArrayQueue;
// Import of a required class
import org.jctools.queues.atomic.MpscAtomicArrayQueue;
// Import of a required class
import org.jctools.queues.atomic.MpscUnboundedAtomicArrayQueue;
// Import of a required class
import org.jetbrains.annotations.ApiStatus;

// Annotation for the following element
@ApiStatus.Internal
// Type declaration (class/interface/enum/record)
public final class ConcurrentMessageQueues {

    // Start of a method/block
    public static <T> MessagePassingQueue<T> mpscArrayQueue(int capacity) {
        // Returns a value to the caller
        return ServerFlag.UNSAFE_COLLECTIONS ? new MpscArrayQueue<>(capacity) : new MpscAtomicArrayQueue<>(capacity);
    // End of a block/expression
    }

    // Start of a method/block
    public static <T> MessagePassingQueue<T> mpscUnboundedArrayQueue(int chunkSize) {
        // Returns a value to the caller
        return ServerFlag.UNSAFE_COLLECTIONS ? new MpscUnboundedXaddArrayQueue<>(chunkSize) : new MpscUnboundedAtomicArrayQueue<>(chunkSize);
    // End of a block/expression
    }

    // Atomic is bounded; no unbounded atomic variant exists that is MPMC.
    // Start of a method/block
    public static <T> MessagePassingQueue<T> mpmcSpecialUnboundedArrayQueue(int value) {
        // Returns a value to the caller
        return ServerFlag.UNSAFE_COLLECTIONS ? new MpmcUnboundedXaddArrayQueue<>(value) : new MpmcAtomicArrayQueue<>(value);
    // End of a block/expression
    }
// End of a block/expression
}