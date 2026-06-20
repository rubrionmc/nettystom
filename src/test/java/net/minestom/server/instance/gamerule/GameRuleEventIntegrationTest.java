// Déclaration du paquet de ce fichier
package net.minestom.server.instance.gamerule;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerGameRulesRequestEvent;
// Import d'une classe nécessaire
import net.minestom.server.event.player.PlayerSetGameRulesEvent;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientSetGameRulesPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.client.play.ClientStatusPacket;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.GameRuleValuesPacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Assertions;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;
// Import d'une classe nécessaire
import java.util.Map;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class GameRuleEventIntegrationTest {

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void requestGameRuleValues(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 43, 0));
        // Appelle une méthode
        var response = connection.trackIncoming(GameRuleValuesPacket.class);
        // Appelle une méthode
        var event = env.listen(PlayerGameRulesRequestEvent.class);

        // Début d'une méthode/d'un bloc
        env.process().eventHandler().addListener(PlayerGameRulesRequestEvent.class, it -> {
            // Appelle une méthode
            it.getPlayer().sendPacket(new GameRuleValuesPacket(Map.of(GameRule.COMMAND_BLOCKS_WORK, "false")));
        // Fin d'un bloc/d'une expression
        });
        // Appelle une méthode
        event.followup();

        // Appelle une méthode
        player.addPacketToQueue(new ClientStatusPacket(ClientStatusPacket.Action.REQUEST_GAMERULE_VALUES));
        // Appelle une méthode
        player.interpretPacketQueue();
        // Instruction de code
        response.assertSingle(it ->
                // Appelle une méthode
                Assertions.assertEquals("false", it.values().get(GameRule.COMMAND_BLOCKS_WORK)));
    // Fin d'un bloc/d'une expression
    }

    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void setGameRuleValues(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var player = connection.connect(instance, new Pos(0, 43, 0));
        // Appelle une méthode
        var event = env.listen(PlayerSetGameRulesEvent.class);
        // Appelle une méthode
        var entry = new ClientSetGameRulesPacket.Entry(GameRule.COMMAND_BLOCKS_WORK, "false");
        // Appelle une méthode
        event.followup(it -> Assertions.assertEquals(entry, it.getRequestedRules().getFirst()));

        // Appelle une méthode
        player.addPacketToQueue(new ClientSetGameRulesPacket(List.of(entry)));
        // Appelle une méthode
        player.interpretPacketQueue();
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
