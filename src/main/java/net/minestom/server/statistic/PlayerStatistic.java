// Package declaration for this file
package net.minestom.server.statistic;

// Import of a required class
import net.minestom.server.entity.Player;

/**
 * Represents a single statistic in the "statistics" game menu.
 * <p>
 * You can retrieve the statistics map with {@link Player#getStatisticValueMap()} and modify it with your own values.
 */
// Type declaration (class/interface/enum/record)
public class PlayerStatistic {
    // Code statement
    private final StatisticCategory category;
    // Code statement
    private final int statisticId;

    // Start of a method/block
    public PlayerStatistic(StatisticCategory category, int statisticId) {
        // Access to the current/parent object
        this.category = category;
        // Access to the current/parent object
        this.statisticId = statisticId;
    // End of a block/expression
    }

    // Start of a method/block
    public PlayerStatistic(StatisticType type) {
        // Calls a method
        this(StatisticCategory.CUSTOM, type.id());
    // End of a block/expression
    }

    // Start of a method/block
    public StatisticCategory getCategory() {
        // Returns a value to the caller
        return category;
    // End of a block/expression
    }

    // Start of a method/block
    public int getStatisticId() {
        // Returns a value to the caller
        return statisticId;
    // End of a block/expression
    }
// End of a block/expression
}
