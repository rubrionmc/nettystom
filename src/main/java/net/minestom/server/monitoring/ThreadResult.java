// Déclaration du paquet de ce fichier
package net.minestom.server.monitoring;

// Déclaration de type (classe/interface/enum/record)
public class ThreadResult {

    // Instruction de code
    private final double cpuPercentage;
    // Instruction de code
    private final double userPercentage;
    // Instruction de code
    private final double waitedPercentage;
    // Instruction de code
    private final double blockedPercentage;

    // Instruction de code
    protected ThreadResult(double cpuPercentage,
                           // Instruction de code
                           double userPercentage,
                           // Instruction de code
                           double waitedPercentage,
                           // Début d'une méthode/d'un bloc
                           double blockedPercentage) {
        // Accès à l'objet courant/parent
        this.cpuPercentage = cpuPercentage;
        // Accès à l'objet courant/parent
        this.userPercentage = userPercentage;
        // Accès à l'objet courant/parent
        this.waitedPercentage = waitedPercentage;
        // Accès à l'objet courant/parent
        this.blockedPercentage = blockedPercentage;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double getCpuPercentage() {
        // Renvoie une valeur à l'appelant
        return cpuPercentage;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double getUserPercentage() {
        // Renvoie une valeur à l'appelant
        return userPercentage;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double getWaitedPercentage() {
        // Renvoie une valeur à l'appelant
        return waitedPercentage;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public double getBlockedPercentage() {
        // Renvoie une valeur à l'appelant
        return blockedPercentage;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
