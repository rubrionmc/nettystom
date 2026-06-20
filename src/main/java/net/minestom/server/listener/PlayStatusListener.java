// Déclaration du paquet de ce fichier
package net.minestom.server.listener;

// Import d'une classe nécessaire
import net.minestom.server.entity.Player;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientStatusPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.StatisticsPacket;
// Import d'une classe nécessaire
import net.minestom.server.statistic.PlayerStatistic;

// Import d'une classe nécessaire
import java.util.ArrayList;
// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Déclaration de type (classe/interface/enum/record)
public class PlayStatusListener {

    // Début d'une méthode/d'un bloc
    public static void listener(ClientStatusPacket packet, Player player) {
        // Embranchement multiple (switch/case)
        switch (packet.action()) {
            // Embranchement multiple (switch/case)
            case PERFORM_RESPAWN -> player.respawn();
            // Embranchement multiple (switch/case)
            case REQUEST_STATS -> {
                // Appelle une méthode
                List<StatisticsPacket.Statistic> statisticList = new ArrayList<>();
                // Appelle une méthode
                final Map<PlayerStatistic, Integer> playerStatisticValueMap = player.getStatisticValueMap();
                // Boucle : répète un bloc
                for (var entry : playerStatisticValueMap.entrySet()) {
                    // Appelle une méthode
                    final PlayerStatistic playerStatistic = entry.getKey();
                    // Appelle une méthode
                    final int value = entry.getValue();
                    // Instruction de code
                    statisticList.add(new StatisticsPacket.Statistic(playerStatistic.getCategory(),
                            // Appelle une méthode
                            playerStatistic.getStatisticId(), value));
                // Fin d'un bloc/d'une expression
                }
                // Appelle une méthode
                StatisticsPacket statisticsPacket = new StatisticsPacket(statisticList);
                // Appelle une méthode
                player.sendPacket(statisticsPacket);
            // Fin d'un bloc/d'une expression
            }
            // Embranchement multiple (switch/case)
            case REQUEST_GAMERULE_VALUES -> PlayerSettingsMenuListener.requestGameRules(packet, player);
        // Fin d'un bloc/d'une expression
        }
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
