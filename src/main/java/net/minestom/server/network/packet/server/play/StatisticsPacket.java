// Déclaration du paquet de ce fichier
package net.minestom.server.network.packet.server.play;

// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBuffer;
// Import d'une classe nécessaire
import net.minestom.server.network.NetworkBufferTemplate;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.ServerPacket;
// Import d'une classe nécessaire
import net.minestom.server.statistic.StatisticCategory;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static net.minestom.server.network.NetworkBuffer.VAR_INT;

// Déclaration de type (classe/interface/enum/record)
public record StatisticsPacket(List<Statistic> statistics) implements ServerPacket.Play {
    // Affecte une valeur
    public static final int MAX_ENTRIES = 16384;

    // Affecte une valeur
    public static final NetworkBuffer.Type<StatisticsPacket> SERIALIZER = NetworkBufferTemplate.template(
            // Instruction de code
            Statistic.SERIALIZER.list(MAX_ENTRIES), StatisticsPacket::statistics,
            // Instruction de code
            StatisticsPacket::new);

    // Début d'une méthode/d'un bloc
    public StatisticsPacket {
        // Appelle une méthode
        statistics = List.copyOf(statistics);
    // Fin d'un bloc/d'une expression
    }

    // Déclaration de type (classe/interface/enum/record)
    public record Statistic(StatisticCategory category, int statisticId, int value) {
        // Affecte une valeur
        public static final NetworkBuffer.Type<Statistic> SERIALIZER = NetworkBufferTemplate.template(
                // Instruction de code
                NetworkBuffer.Enum(StatisticCategory.class), Statistic::category,
                // Instruction de code
                VAR_INT, Statistic::statisticId,
                // Instruction de code
                VAR_INT, Statistic::value,
                // Instruction de code
                Statistic::new);
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
