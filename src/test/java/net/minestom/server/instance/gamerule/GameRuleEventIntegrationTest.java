// Package declaration for this file
package net.minestom.server.instance.gamerule;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.event.player.PlayerGameRulesRequestEvent;
// Import of a required class
import net.minestom.server.event.player.PlayerSetGameRulesEvent;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientSetGameRulesPacket;
// Import of a required class
import net.minestom.server.network.packet.client.play.ClientStatusPacket;
// Import of a required class
import net.minestom.server.network.packet.server.play.GameRuleValuesPacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Assertions;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;
// Import of a required class
import java.util.Map;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class GameRuleEventIntegrationTest {

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void requestGameRuleValues(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 43, 0));
        // Calls a method
        var response = connection.trackIncoming(GameRuleValuesPacket.class);
        // Calls a method
        var event = env.listen(PlayerGameRulesRequestEvent.class);

        // Start of a method/block
        env.process().eventHandler().addListener(PlayerGameRulesRequestEvent.class, it -> {
            // Calls a method
            it.getPlayer().sendPacket(new GameRuleValuesPacket(Map.of(GameRule.COMMAND_BLOCKS_WORK, "false")));
        // End of a block/expression
        });
        // Calls a method
        event.followup();

        // Calls a method
        player.addPacketToQueue(new ClientStatusPacket(ClientStatusPacket.Action.REQUEST_GAMERULE_VALUES));
        // Calls a method
        player.interpretPacketQueue();
        // Code statement
        response.assertSingle(it ->
                // Calls a method
                Assertions.assertEquals("false", it.values().get(GameRule.COMMAND_BLOCKS_WORK)));
    // End of a block/expression
    }

    // Annotation for the following element
    @Test
    // Start of a method/block
    public void setGameRuleValues(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var player = connection.connect(instance, new Pos(0, 43, 0));
        // Calls a method
        var event = env.listen(PlayerSetGameRulesEvent.class);
        // Calls a method
        var entry = new ClientSetGameRulesPacket.Entry(GameRule.COMMAND_BLOCKS_WORK, "false");
        // Calls a method
        event.followup(it -> Assertions.assertEquals(entry, it.getRequestedRules().getFirst()));

        // Calls a method
        player.addPacketToQueue(new ClientSetGameRulesPacket(List.of(entry)));
        // Calls a method
        player.interpretPacketQueue();
    // End of a block/expression
    }
// End of a block/expression
}
