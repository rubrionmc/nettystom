// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.coordinate.Vec;
// Import d'une classe nécessaire
import net.minestom.server.entity.EntityType;
// Import d'une classe nécessaire
import net.minestom.server.tag.TagReadable;
// Import d'une classe nécessaire
import org.jetbrains.annotations.Nullable;

// Import d'une classe nécessaire
import java.util.Collection;
// Import d'une classe nécessaire
import java.util.UUID;

// Déclaration de type (classe/interface/enum/record)
public sealed interface EntitySnapshot extends Snapshot, TagReadable
        // Début d'une méthode/d'un bloc
        permits PlayerSnapshot, SnapshotImpl.Entity {
    // Appelle une méthode
    EntityType type();

    // Appelle une méthode
    UUID uuid();

    // Appelle une méthode
    int id();

    // Appelle une méthode
    Pos position();

    // Appelle une méthode
    Vec velocity();

    // Appelle une méthode
    InstanceSnapshot instance();

    // Appelle une méthode
    ChunkSnapshot chunk();

    // Appelle une méthode
    Collection<PlayerSnapshot> viewers();

    // Appelle une méthode
    Collection<EntitySnapshot> passengers();

    // Annotation pour l'élément suivant
    @Nullable EntitySnapshot vehicle();
// Fin d'un bloc/d'une expression
}
