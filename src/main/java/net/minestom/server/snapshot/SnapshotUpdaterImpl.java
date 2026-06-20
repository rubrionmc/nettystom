

// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.IdentityHashMap;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Objects;
// Import d'une classe nécessaire
import java.util.concurrent.atomic.AtomicReference;

// Déclaration de type (classe/interface/enum/record)
final class SnapshotUpdaterImpl implements SnapshotUpdater {
    // Appelle une méthode
    private final IdentityHashMap<Snapshotable, AtomicReference<Snapshot>> referenceMap = new IdentityHashMap<>();
    // Instruction de code
    private IdentityHashMap<Snapshotable, AtomicReference<Snapshot>> readOnlyReferenceMap;
    // Appelle une méthode
    private List<Entry> queue = new ArrayList<>();

    // Début d'une méthode/d'un bloc
    static <T extends Snapshot> T update(Snapshotable snapshotable) {
        // Appelle une méthode
        var updater = new SnapshotUpdaterImpl();
        // Appelle une méthode
        var ref = updater.reference(snapshotable);
        // Appelle une méthode
        updater.update();
        // Renvoie une valeur à l'appelant
        return (T) ref.get();
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Override
    // Début d'une méthode/d'un bloc
    public <T extends Snapshot> AtomicReference<T> reference(Snapshotable snapshotable) {
        // Instruction de code
        AtomicReference<Snapshot> ref;
        // Very often the same snapshotable is referenced multiple times.
        // Affecte une valeur
        var readOnly = this.readOnlyReferenceMap;
        // Embranchement : vérifie une condition
        if (readOnly != null && (ref = readOnly.get(snapshotable)) != null) {
            // Renvoie une valeur à l'appelant
            return (AtomicReference<T>) ref;
        // Fin d'un bloc/d'une expression
        }
        // If this is a new snapshotable, we need to create a new reference.
        // Début d'une méthode/d'un bloc
        synchronized (this) {
            // Appelle une méthode
            ref = new AtomicReference<>();
            // Appelle une méthode
            var prev = referenceMap.putIfAbsent(snapshotable, ref);
            // Embranchement : vérifie une condition
            if (prev != null) return (AtomicReference<T>) prev;
            // Accès à l'objet courant/parent
            this.queue.add(new Entry(snapshotable, ref));
            // Renvoie une valeur à l'appelant
            return (AtomicReference<T>) ref;
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    record Entry(Snapshotable snapshotable, AtomicReference<Snapshot> ref) {
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    void update() {
        // Instruction de code
        List<Entry> temp;
        // Boucle : répète un bloc
        while (!(temp = new ArrayList<>(queue)).isEmpty()) {
            // Appelle une méthode
            queue = new ArrayList<>();
            // Appelle une méthode
            readOnlyReferenceMap = (IdentityHashMap<Snapshotable, AtomicReference<Snapshot>>) referenceMap.clone();
            // Début d'une méthode/d'un bloc
            temp.parallelStream().forEach(entry -> {
                // Affecte une valeur
                Snapshotable snap = entry.snapshotable;
                // Appelle une méthode
                entry.ref.set(Objects.requireNonNull(snap.updateSnapshot(this), "Snapshot must not be null after an update!"));
            // Fin d'un bloc/d'une expression
            });
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
