// Déclaration du paquet de ce fichier
package net.minestom.server.event.server;

// Import d'une classe nécessaire
import net.minestom.server.event.Event;
// Import d'une classe nécessaire
import net.minestom.server.monitoring.TickMonitor;

// Déclaration de type (classe/interface/enum/record)
public final class ServerTickMonitorEvent implements Event {
    // Instruction de code
    private final TickMonitor tickMonitor;

    // Début d'une méthode/d'un bloc
    public ServerTickMonitorEvent(TickMonitor tickMonitor) {
        // Accès à l'objet courant/parent
        this.tickMonitor = tickMonitor;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public TickMonitor getTickMonitor() {
        // Renvoie une valeur à l'appelant
        return tickMonitor;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
