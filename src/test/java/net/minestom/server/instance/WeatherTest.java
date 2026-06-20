// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.coordinate.Pos;
// Import of a required class
import net.minestom.server.network.packet.server.play.ChangeGameStatePacket;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Import of a required class
import java.util.List;

// Static import of a member
import static org.junit.jupiter.api.Assertions.assertEquals;
// Static import of a member
import static org.junit.jupiter.api.Assertions.assertFalse;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class WeatherTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void weatherTest(Env env) {
        // Calls a method
        var instance = env.createFlatInstance();

        // Defaults
        // Calls a method
        Weather weather = instance.getWeather();
        // Calls a method
        assertFalse(weather.isRaining());
        // Calls a method
        assertEquals(0, weather.rainLevel());
        // Calls a method
        assertEquals(0, weather.thunderLevel());

        // Calls a method
        instance.setWeather(new Weather(1, 0.5f), 1);
        // Calls a method
        instance.tick(0);

        // Weather sent on instance join
        // Calls a method
        var connection = env.createConnection();
        // Calls a method
        var tracker = connection.trackIncoming(ChangeGameStatePacket.class);
        // Calls a method
        connection.connect(instance, new Pos(0, 0, 0));
        // Calls a method
        tracker.assertCount(4);
        // Calls a method
        List<ChangeGameStatePacket> packets = tracker.collect();
        // Calls a method
        var state = packets.get(0);
        // Calls a method
        assertEquals(ChangeGameStatePacket.Reason.BEGIN_RAINING, state.reason());

        // Calls a method
        state = packets.get(1);
        // Calls a method
        assertEquals(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, state.reason());
        // Calls a method
        assertEquals(1, state.value());

        // Calls a method
        state = packets.get(2);
        // Calls a method
        assertEquals(ChangeGameStatePacket.Reason.THUNDER_LEVEL_CHANGE, state.reason());
        // Calls a method
        assertEquals(0.5f, state.value());

        // Weather change while inside instance
        // Calls a method
        var tracker2 = connection.trackIncoming(ChangeGameStatePacket.class);
        // Calls a method
        instance.setWeather(new Weather(0, 0), 2);
        // Calls a method
        instance.tick(0);
        // Calls a method
        state = tracker2.collect().getFirst();
        // Calls a method
        assertEquals(ChangeGameStatePacket.Reason.RAIN_LEVEL_CHANGE, state.reason());
        // Calls a method
        assertEquals(0.5f, state.value());
    // End of a block/expression
    }
// End of a block/expression
}
