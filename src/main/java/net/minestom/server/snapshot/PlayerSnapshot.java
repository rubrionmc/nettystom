// Déclaration du paquet de ce fichier
package net.minestom.server.snapshot;

// Import d'une classe nécessaire
import net.minestom.server.entity.GameMode;

// Déclaration de type (classe/interface/enum/record)
public sealed interface PlayerSnapshot extends EntitySnapshot
        // Début d'une méthode/d'un bloc
        permits SnapshotImpl.Player {
    // Appelle une méthode
    String username();

    // Appelle une méthode
    GameMode gameMode();
// Fin d'un bloc/d'une expression
}
