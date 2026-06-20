

// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import net.minestom.server.MinecraftServer;
// Import d'une classe nécessaire
import org.jetbrains.annotations.ApiStatus;
// Import d'une classe nécessaire
import org.jetbrains.annotations.UnknownNullability;

// Import d'une classe nécessaire
import java.util.Collection;

/**
 * Represents the complete state of the server at a given moment.
 */
// Déclaration de type (classe/interface/enum/record)
public sealed interface ServerSnapshot extends Snapshot
        // Début d'une méthode/d'un bloc
        permits SnapshotImpl.Server {
    // Appelle une méthode
    Collection<InstanceSnapshot> instances();

    // Appelle une méthode
    Collection<EntitySnapshot> entities();

    // Annotation pour l'élément suivant
    @UnknownNullability EntitySnapshot entity(int id);

    // Annotation pour l'élément suivant
    @ApiStatus.Experimental
    // Début d'une méthode/d'un bloc
    static ServerSnapshot update() {
        // Renvoie une valeur à l'appelant
        return SnapshotUpdater.update(MinecraftServer.process());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
