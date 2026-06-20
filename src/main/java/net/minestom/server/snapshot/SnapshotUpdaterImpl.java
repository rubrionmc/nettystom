

// Package declaration for this file
package net.minestom.server.snapshot;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.IdentityHashMap;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Objects;
// Import of a required class
import java.util.concurrent.atomic.AtomicReference;

// Type declaration (class/interface/enum/record)
final class SnapshotUpdaterImpl implements SnapshotUpdater {
    // Calls a method
    private final IdentityHashMap<Snapshotable, AtomicReference<Snapshot>> referenceMap = new IdentityHashMap<>();
    // Code statement
    private IdentityHashMap<Snapshotable, AtomicReference<Snapshot>> readOnlyReferenceMap;
    // Calls a method
    private List<Entry> queue = new ArrayList<>();

    // Start of a method/block
    static <T extends Snapshot> T update(Snapshotable snapshotable) {
        // Calls a method
        var updater = new SnapshotUpdaterImpl();
        // Calls a method
        var ref = updater.reference(snapshotable);
        // Calls a method
        updater.update();
        // Returns a value to the caller
        return (T) ref.get();
    // End of a block/expression
    }

    // Annotation for the following element
    @Override
    // Start of a method/block
    public <T extends Snapshot> AtomicReference<T> reference(Snapshotable snapshotable) {
        // Code statement
        AtomicReference<Snapshot> ref;
        // Very often the same snapshotable is referenced multiple times.
        // Assigns a value
        var readOnly = this.readOnlyReferenceMap;
        // Branch: checks a condition
        if (readOnly != null && (ref = readOnly.get(snapshotable)) != null) {
            // Returns a value to the caller
            return (AtomicReference<T>) ref;
        // End of a block/expression
        }
        // If this is a new snapshotable, we need to create a new reference.
        // Start of a method/block
        synchronized (this) {
            // Calls a method
            ref = new AtomicReference<>();
            // Calls a method
            var prev = referenceMap.putIfAbsent(snapshotable, ref);
            // Branch: checks a condition
            if (prev != null) return (AtomicReference<T>) prev;
            // Access to the current/parent object
            this.queue.add(new Entry(snapshotable, ref));
            // Returns a value to the caller
            return (AtomicReference<T>) ref;
        // End of a block/expression
        }
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    record Entry(Snapshotable snapshotable, AtomicReference<Snapshot> ref) {
    // End of a block/expression
    }

    // Start of a method/block
    void update() {
        // Code statement
        List<Entry> temp;
        // Loop: repeats a block
        while (!(temp = new ArrayList<>(queue)).isEmpty()) {
            // Calls a method
            queue = new ArrayList<>();
            // Calls a method
            readOnlyReferenceMap = (IdentityHashMap<Snapshotable, AtomicReference<Snapshot>>) referenceMap.clone();
            // Start of a method/block
            temp.parallelStream().forEach(entry -> {
                // Assigns a value
                Snapshotable snap = entry.snapshotable;
                // Calls a method
                entry.ref.set(Objects.requireNonNull(snap.updateSnapshot(this), "Snapshot must not be null after an update!"));
            // End of a block/expression
            });
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
