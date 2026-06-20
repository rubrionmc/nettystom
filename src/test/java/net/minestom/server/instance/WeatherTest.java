// Déclaration du paquet de ce fichier
package net.minestom.server.instance;

// Import d'une classe nécessaire
import net.minestom.server.coordinate.Pos;
// Import d'une classe nécessaire
import net.minestom.server.network.packet.server.play.ChangeGameStatePacket;
// Import d'une classe nécessaire
import net.minestom.testing.Env;
// Import d'une classe nécessaire
import net.minestom.testing.EnvTest;
// Import d'une classe nécessaire
import org.junit.jupiter.api.Test;

// Import d'une classe nécessaire
import java.util.List;

// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertEquals;
// Import statique d'un membre
import static org.junit.jupiter.api.Assertions.assertFalse;

// Annotation pour l'élément suivant
@EnvTest
// Déclaration de type (classe/interface/enum/record)
public class WeatherTest {
    // Annotation pour l'élément suivant
    @Test
    // Début d'une méthode/d'un bloc
    public void weatherTest(Env env) {
        // Appelle une méthode
        var instance = env.createFlatInstance();

        // Defaults
        // Appelle une méthode
        Weather weather = instance.getWeather();
        // Appelle une méthode
        assertFalse(weather.isRaining());
        // Appelle une méthode
        assertEquals(0, weather.rainLevel());
        // Appelle une méthode
        assertEquals(0, weather.thunderLevel());

        // Appelle une méthode
        instance.setWeather(new Weather(1, 0.5f), 1);
        // Appelle une méthode
        instance.tick(0);

        // Weather sent on instance join
        // Appelle une méthode
        var connection = env.createConnection();
        // Appelle une méthode
        var tracker = connection.trackIncoming(ChangeGameStatePacket.class);
        // Appelle une méthode
        connection.connect(instance, new Pos(0, 0, 0));
        // Appelle une méthode
        tracker.assertCount(4);
        // Appelle une méthode
        List<ChangeGameStatePacket> packets = tracker.collect();
        // Appelle une méthode
        var state = packets.get(0);
        // Appelle une méthode
        assertEquals(ChangeGameStatePacket.Reason.BEGIN_RAINING, state.reason());

        // Appelle une méthode
        state = packets.get(1);
        // Appelle une méthode
        assertEquals(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, state.reason());
        // Appelle une méthode
        assertEquals(1, state.value());

        // Appelle une méthode
        state = packets.get(2);
        // Appelle une méthode
        assertEquals(ChangeGameStatePacket.Reason.THUNDER_LEVEL_CHANGE, state.reason());
        // Appelle une méthode
        assertEquals(0.5f, state.value());

        // Weather change while inside instance
        // Appelle une méthode
        var tracker2 = connection.trackIncoming(ChangeGameStatePacket.class);
        // Appelle une méthode
        instance.setWeather(new Weather(0, 0), 2);
        // Appelle une méthode
        instance.tick(0);
        // Appelle une méthode
        state = tracker2.collect().getFirst();
        // Appelle une méthode
        assertEquals(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, state.reason());
        // Appelle une méthode
        assertEquals(0.5f, state.value());
    // Fin d'un bloc/d'une expression
    }
// Fin d'un bloc/d'une expression
}
