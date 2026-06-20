// Déclaration du paquet de ce fichier
package net.minestom.server.monitoring;

// Déclaration de type (classe/interface/enum/record)
public class TickMonitor {

    // Instruction de code
    private final double tickTime;
    // Instruction de code
    private final double acquisitionTime;

    // Début d'une méthode/d'un bloc
    public TickMonitor(double tickTime, double acquisitionTime) {
        // Accès à l'objet courant/parent
        this.tickTime = tickTime;
        // Accès à l'objet courant/parent
        this.acquisitionTime = acquisitionTime;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double getTickTime() {
        // Renvoie une valeur à l'appelant
        return tickTime;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double getAcquisitionTime() {
        // Renvoie une valeur à l'appelant
        return acquisitionTime;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
