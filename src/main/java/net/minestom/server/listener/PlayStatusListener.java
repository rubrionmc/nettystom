// Package declaration for this file
package net.minestom.server.listener;

// Import of a required class
import net.minestom.server.entity.Player;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientStatusPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.StatisticsPacket;
// Import of a required class
import net.minestom.server.statistic.PlayerStatistic;

// Import of a required class
import java.util.ArrayList;
// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Type declaration (class/interface/enum/record)
public class PlayStatusListener {

    // Start of a method/block
    public static void listener(ClientStatusPacket packet, Player player) {
        // Multiple branching (switch/case)
        switch (packet.action()) {
            // Multiple branching (switch/case)
            case PERFORM_RESPAWN -> player.respawn();
            // Multiple branching (switch/case)
            case REQUEST_STATS -> {
                // Calls a method
                List<StatisticsPacket.Statistic> statisticList = new ArrayList<>();
                // Calls a method
                final Map<PlayerStatistic, Integer> playerStatisticValueMap = player.getStatisticValueMap();
                // Loop: repeats a block
                for (var entry : playerStatisticValueMap.entrySet()) {
                    // Calls a method
                    final PlayerStatistic playerStatistic = entry.getKey();
                    // Calls a method
                    final int value = entry.getValue();
                    // Code statement
                    statisticList.add(new StatisticsPacket.Statistic(playerStatistic.getCategory(),
                            // Calls a method
                            playerStatistic.getStatisticId(), value));
                // End of a block/expression
                }
                // Calls a method
                StatisticsPacket statisticsPacket = new StatisticsPacket(statisticList);
                // Calls a method
                player.sendPacket(statisticsPacket);
            // End of a block/expression
            }
            // Multiple branching (switch/case)
            case REQUEST_GAMERULE_VALUES -> PlayerSettingsMenuListener.requestGameRules(packet, player);
        // End of a block/expression
        }
    // End of a block/expression
    }
// End of a block/expression
}
