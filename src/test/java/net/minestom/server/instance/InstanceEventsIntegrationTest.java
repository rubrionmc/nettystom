// Package declaration for this file
package net.minestom.server.instance;

// Import of a required class
import net.minestom.server.event.instance.InstanceRegisterEvent;
// Import of a required class
import net.minestom.server.event.instance.InstanceUnregisterEvent;
// Import of a required class
import net.minestom.testing.Env;
// Import of a required class
import net.minestom.testing.EnvTest;
// Import of a required class
import org.junit.jupiter.api.Test;

// Annotation for the following element
@EnvTest
// Type declaration (class/interface/enum/record)
public class InstanceEventsIntegrationTest {
    // Annotation for the following element
    @Test
    // Start of a method/block
    public void registerAndUnregisterInstance(Env env) {
        // Calls a method
        var registerListener = env.listen(InstanceRegisterEvent.class);
        // Calls a method
        var unregisterListener = env.listen(InstanceUnregisterEvent.class);

        // Calls a method
        registerListener.followup();
        // Calls a method
        Instance instance = env.process().instance().createInstanceContainer();

        // Calls a method
        unregisterListener.followup();
        // Calls a method
        env.destroyInstance(instance);
    // End of a block/expression
    }
// End of a block/expression
}
