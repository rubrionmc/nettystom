// Package declaration for this file
package net.minestom.server.network.packet.server.play;

// Import of a required class
import net.minestom.server.network.NetworkBuffer;
// Import of a required class
import net.minestom.server.network.NetworkBufferTemplate;
// Import of a required class
import net.minestom.server.network.packet.server.ServerPacket;
// Import of a required class
import net.minestom.server.statistic.StatisticCategory;

// Import of a required class
import java.util.List;

// Static import of a member
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Type declaration (class/interface/enum/record)
public record StatisticsPacket(List<Statistic> statistics) implements ServerPacket.Play {
    // Assigns a value
    public static final int MAX_ENTRIES = 16384;

    // Assigns a value
    public static final NetworkBuffer.Type<StatisticsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Code statement
            Statistic.SERIALIZER.list(MAX_ENTRIES), StatisticsPacket::statistics,
            // Code statement
            StatisticsPacket::new);

    // Start of a method/block
    public StatisticsPacket {
        // Calls a method
        statistics = List.copyOf(statistics);
    // End of a block/expression
    }

    // Type declaration (class/interface/enum/record)
    public record Statistic(StatisticCategory category, int statisticId, int value) {
        // Assigns a value
        public static final NetworkBuffer.Type<Statistic> SERIALIZER = NetworkBufferTemplate.template(
                // Code statement
                NetworkBuffer.Enum(StatisticCategory.class), Statistic::category,
                // Code statement
                VAR_INT, Statistic::statisticId,
                // Code statement
                VAR_INT, Statistic::value,
                // Code statement
                Statistic::new);
    // End of a block/expression
    }
// End of a block/expression
}
