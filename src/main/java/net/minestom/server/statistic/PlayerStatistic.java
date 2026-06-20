// Déclaration du paquet de ce fichier
package net.minestom.server.statistic;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;

/**
 * Represents a single statistic in the "statistics" game menu.
 * <p>
 * You can retrieve the statistics map with {@link Player#getStatisticValueMap()} and modify it with your own values.
 */
// Déclaration de type (classe/interface/enum/record)
public class PlayerStatistic {
    // Instruction de code
    private final StatisticCategory category;
    // Instruction de code
    private final int statisticId;

    // Début d'une méthode/d'un bloc
    public PlayerStatistic(StatisticCategory category, int statisticId) {
        // Accès à l'objet courant/parent
        this.category = category;
        // Accès à l'objet courant/parent
        this.statisticId = statisticId;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public PlayerStatistic(StatisticType type) {
        // Appelle une méthode
        this(StatisticCategory.CUSTOM, type.id());
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public StatisticCategory getCategory() {
        // Renvoie une valeur à l'appelant
        return category;
    // Fin d'un bloc/d'une expression
    }

    // Début d'une méthode/d'un bloc
    public int getStatisticId() {
        // Renvoie une valeur à l'appelant
        return statisticId;
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
